package com.dsm.wakeheart.Arduino;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;


public class MainService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setStart(){
        Log.d("xxx", "setStart: " + getPreferences().getBoolean("isOn", false));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Notification.Builder no = new Notification.Builder(this);
        no.setContentTitle("blutooth 동작 중!!");
        no.setContentText("데이터를 받아오고 있습니다.");
        startForeground(1, no.build());

        inputStream = SetAndGetClass.getInstance().getBlutoothControl().getInputStream();
        startListenForData(inputStream);

        setStart();
    }

    InputStream inputStream;
    OutputStream outputStream;


    private int readBufferPosition = 0;

    private void startListenForData(final InputStream inputStream){
        final android.os.Handler handler = new android.os.Handler();

        final byte[] readBuffer = new byte[1024];

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    try{
                        int byteAvailable = inputStream.available();
                        if(byteAvailable > 0){
                            byte[] packetBytes = new byte[byteAvailable];
                            inputStream.read(packetBytes);

                            for(int i=0;i<byteAvailable;i++){
                                byte b = packetBytes[i];
                                if(b == 'e'){
                                    byte[] encodeBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodeBytes, 0, encodeBytes.length);

                                    final String data = new String(encodeBytes);
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("xxx", data);
                                        }
                                    });
                                }else{
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        MainService.this.onDestroy();
                    }
                }
            }
        }).start();
    }

    private SharedPreferences getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferences().edit().remove("isOn");
        getPreferences().edit().putBoolean("isOn", false);
        getPreferences().edit().commit();
    }
}
