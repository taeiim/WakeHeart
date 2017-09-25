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


public class ActivityMain extends AppCompatActivity {

    TextView textView;
    BluetoothControl blutoothControl;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("xxx", "onCreate: " + getPreferences().getBoolean("isOn", false));
        if(!getPreferences().getBoolean("isOn", false)){ //isOn이름을 가진 bool값을 받아온다. 기본값 false
            blutoothControl = new BluetoothControl(this, ActivityMain.this);
            threadOn = true;
            startService(this);
        }
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
