package com.dsm.wakeheart.Arduino;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.dsm.wakeheart.R;
import com.txusballesteros.SnakeView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class BluetoothMain extends AppCompatActivity {

    TextView textView;
    BluetoothControl blutoothControl;
    SnakeView snakeView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        Log.d("xxx", "onCreate: " + getPreferences().getBoolean("isOn", false));
        if (!getPreferences().getBoolean("isOn", false)) { //isOn이름을 가진 bool값을 받아온다. 기본값 false
            blutoothControl = new BluetoothControl(this, BluetoothMain.this);
            threadOn = true;
            startService(this);
        }

        textView = (TextView) findViewById(R.id.bpmTextView);
        snakeView = (SnakeView) findViewById(R.id.snake);



//        textview.setText("" + blutoothControl.getInputStream());

        snakeView.setMinValue(30);
        snakeView.setMaxValue(100);

//        InputStream inputStream = blutoothControl.getInputStream();
//        String bpmString = getStringFromInputStream(inputStream);
//        int bpmNum = Integer.parseInt(bpmString);
//        snakeView.addValue(bpmNum);
    }


    int readBufferPosition=0;

    private void startListenForData(final InputStream inputStream, final TextView textView,final SnakeView snakeView) {
        final Handler handler = new Handler();
        final byte[] readBuffer = new byte[1024];
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        int byteAvailable = inputStream.available();
                        if (byteAvailable > 0) {
                            byte[] packetBytes = new byte[byteAvailable];
                            inputStream.read(packetBytes);
                            for (int i = 0; i < byteAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == 'e') {
                                    byte[] encodeBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodeBytes, 0, encodeBytes.length);
                                    final String data = new String(encodeBytes);
                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("xxx", data);
                                            textView.setText(data);
                                            int dataInt = Integer.parseInt(data);
                                            snakeView.addValue(dataInt);
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    Thread thread;

    private boolean threadOn = true;

    private void startService(final Context context) {
        final Handler handler = new Handler();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadOn) {
                    Log.d("xxx", "hello");
                    Log.d("xxx", "" + blutoothControl.getInputStream());
                    if (blutoothControl.getInputStream() != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("xxx", "run: " + getPreferences().edit().commit());
                                SetAndGetClass.getInstance().setBlutoothControl(blutoothControl);
                                Intent intent = new Intent(context, MainService.class);
                                //startService(intent);

                                startListenForData(blutoothControl.getInputStream(),textView,snakeView);
                            }
                        });
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    private SharedPreferences getPreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("xxx", "onDestroy: ");
        threadOn = false;
    }
}
