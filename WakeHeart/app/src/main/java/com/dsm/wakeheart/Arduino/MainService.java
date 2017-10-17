package com.dsm.wakeheart.Arduino;

import android.app.Notification;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dsm.wakeheart.AlarmService;
import com.dsm.wakeheart.DataManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class MainService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Notification.Builder no = new Notification.Builder(this);
        no.setContentTitle("blutooth 동작 중!!");
        no.setContentText("데이터를 받아오고 있습니다.");
        startForeground(1, no.build());


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device : devices){
            Log.d("xxx", "" + device.getName() + " : "+ DataManager.getDataManager().getData(this, "device name"));
            if(device.getName().equals(DataManager.getDataManager().getData(this, "device name"))){
                connect(device);
                return START_REDELIVER_INTENT;
            }
        }

        return START_REDELIVER_INTENT;
    }

    private BluetoothSocket socket;

    private void connect(BluetoothDevice device){

        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {

            socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();

            inputStream = socket.getInputStream();


            startListenForData();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    InputStream inputStream;

    LinkedList<Float> datas = new LinkedList<>();

    Timer timer;

    private void startListenForData(){
        final Handler handler = new Handler();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if(inputStream.available() > 0){
                        byte[] readBuffer = new byte[inputStream.available()];
                        inputStream.read(readBuffer);
                        final String data = new String(readBuffer);
                        Log.e("service data", data);
                        final Float fData = Float.parseFloat(data);
                        if(fData >= 30 && fData <= 110){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(fData < 100 ){
                                        datas.add(fData);
                                    }
                                    dataCals();
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 500, 1000);
    }

    private void dataCals(){
        if(datas.size() > 4 ){
            if(datas.get(1) > datas.get(2)){
                if(datas.get(2) > datas.get(3)){
                    if(datas.get(3) > datas.get(4)){
                        Intent intent = new Intent(this, AlarmService.class);
                        startService(intent);
                        datas.clear();
                    }else {
                        datas.clear();
                    }
                }else{
                    datas.clear();
                }
            }else {
                datas.clear();
            }
        }
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        try {
            if(inputStream != null){
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
