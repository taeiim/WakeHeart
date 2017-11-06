package com.dsm.wakeheart.Fragment;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dsm.wakeheart.Graph.DataProcessingService;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by admin on 2017-10-10.
 */

public class GraphService extends Service {
    ArrayList<Integer> al = new ArrayList<>(); //추가
    InputStream inputStream;
    OutputStream outputStream;
    private int readBufferPosition = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setStart() {
        Log.d("xxx", "setStart: " + getPreferences().getBoolean("isOn", false));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Notification.Builder no = new Notification.Builder(this);
        no.setContentTitle("blutooth 동작 중!!");
        no.setContentText("데이터를 받아오고 있습니다.");
        startForeground(1, no.build());

        Log.d("test", "MainService 가동");
//        inputStream = SetAndGetClass.getInstance().getBlutoothControl().getInputStream();
//        startListenForData(inputStream);

        SetHeartPrefThread setHeartPrefThread = new SetHeartPrefThread();
        setHeartPrefThread.start();

        setStart();
    }

    private void startListenForData(final InputStream inputStream) {
        final android.os.Handler handler = new android.os.Handler();

        final byte[] readBuffer = new byte[1024];

        Thread listenThread = new Thread(new Runnable() {
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
                                    int iData = Integer.parseInt(data); //추가
                                    al.add(iData); //추가

                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("xxx", data);
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        GraphService.this.onDestroy();
                    }
                }
            }
        });
        listenThread.start();

    }

    /**
     * SharedPreferences에 심박수 저장
     */
    class SetHeartPrefThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Log.d("test", "10분 대기 시작함");
                    Thread.sleep(600000); //10분
//                    Thread.sleep(10000);
                    synchronized (al) {
                        SharedPreferences.Editor heartEdit = getSharedPreferences("`", MODE_PRIVATE).edit();
                        // 리스트 사이즈 저장
                        heartEdit.remove("Rate_Size");
                        if (al.size() == 0) {
                            Log.d("test", "어레이 리스트에 값이 존재하지 않아 처리 생략");
                            continue;
                        }
                        heartEdit.putInt("Rate_Size", al.size());
                        Log.d("test", "어레이 사이즈 : " + getSharedPreferences("`", MODE_PRIVATE).getInt("Rate_Size", 0));
                        //리스트 값 저장
                        for (int i = 0; i < al.size(); i++) {
                            heartEdit.remove("Rate_" + i);
                            heartEdit.putInt("Rate_" + i, al.get(i));
                            Log.d("test", "동기화 중" + getSharedPreferences("`", MODE_PRIVATE).getInt("Rate_" + i, -1));
                        }
                        heartEdit.commit();

                        //심박수 리스트 초기화
                        al.clear();
                        Log.d("test", "동기화 끝 어레이리스트 사이즈 초기화 : " + al.size());
                    }

                    Intent intent = new Intent(getApplicationContext(), DataProcessingService.class);
                    Log.d("test", "지금부터 MainService -> dataProcService 인텐트 전달");
                    startService(intent);
                } catch (Exception e) {
                }
            }
        }
    }

    private SharedPreferences getPreferences() {
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