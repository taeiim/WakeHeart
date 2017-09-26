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


public class ActivityMain extends AppCompatActivity {

    TextView textView;
    BluetoothControl blutoothControl;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        Log.d("xxx", "onCreate: " + getPreferences().getBoolean("isOn", false));
        if(!getPreferences().getBoolean("isOn", false)){ //isOn이름을 가진 bool값을 받아온다. 기본값 false
            blutoothControl = new BluetoothControl(this, ActivityMain.this);
            threadOn = true;
            startService(this);
        }

        TextView textview = (TextView) findViewById(R.id.bpmTextView);
        textview.setText("" + blutoothControl.getInputStream());
        final SnakeView snakeView = (SnakeView) findViewById(R.id.snake);
        snakeView.setMinValue(30);
        snakeView.setMaxValue(100);

        InputStream inputStream= blutoothControl.getInputStream();
        String bpmString = getStringFromInputStream(inputStream);
        int bpmNum = Integer.parseInt(bpmString);
        snakeView.addValue(bpmNum);
    }

    //InputStream으로 온 BPM String으로 변환
    private static String getStringFromInputStream(InputStream inputStream) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    Thread thread;

    private boolean threadOn = true;

    private void startService(final Context context){
        final Handler handler = new Handler();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadOn){
                    Log.d("xxx", "hello");
                    Log.d("xxx", ""+blutoothControl.getInputStream());
                    if(blutoothControl.getInputStream() != null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getPreferences().edit().remove("isOn");
                                getPreferences().edit().commit();
                                getPreferences().edit().putBoolean("isOn",true);
                                Log.d("xxx", "run: " + getPreferences().edit().commit());
                                SetAndGetClass.getInstance().setBlutoothControl(blutoothControl);
                                Intent intent = new Intent(context, MainService.class);
                                startService(intent);
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

    private SharedPreferences getPreferences(){
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
