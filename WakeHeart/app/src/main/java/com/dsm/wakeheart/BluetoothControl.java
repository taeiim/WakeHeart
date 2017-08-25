package com.dsm.wakeheart;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by root1 on 2017. 8. 3..
 */

public class BluetoothControl {

    private static final String TAG = "BluetoothService";
    private static final int REQUEST_ENABLE_BT = 10;

    private BluetoothAdapter bluetoothAdapter;
    private Activity activity;

    public BluetoothControl(Activity activity){
        this.activity = activity;
        checkEnableBluetooth();
    }

    public void checkEnableBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter != null){
            checkOnOffBluetooth();
        }else{
            Toast.makeText(activity.getApplicationContext(), "기기가 블루투스를 지원하지 않습니다", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

    private void checkOnOffBluetooth(){
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
        }else{
            selectDevice();
        }
    }

    public void selectDevice(){
        final Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();

        if(bluetoothDevices.size() == 0){
            Toast.makeText(activity.getApplicationContext(), "페어링된 기기가 없습니다.", Toast.LENGTH_LONG).show();
            activity.finish();
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(activity.getApplicationContext());
        dialog.setTitle("장치 선택");
        final List<String> deviceList = new ArrayList<String>();
        for(BluetoothDevice device : bluetoothDevices){
            deviceList.add(device.getName());
        }

        final CharSequence[] items = deviceList.toArray(new CharSequence[deviceList.size()]);

        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                connectToSelectedDevice(getDeviceFromDevices(deviceList.get(i), bluetoothDevices));
            }
        });

    }

    private void connectToSelectedDevice(BluetoothDevice selectDevice){

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try{
            BluetoothSocket bluetoothSocket = selectDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();

            outputStream = bluetoothSocket.getOutputStream();
            InputStream inputStream = bluetoothSocket.getInputStream();

            startListenForData(inputStream);

        }catch(Exception e){
            Toast.makeText(activity.getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

    OutputStream outputStream;

    public void sendData(String msg){
        String temp = msg + "\n";
        try{
            outputStream.write(temp.getBytes());
        }catch (Exception e){
            Toast.makeText(activity.getApplicationContext(), "데이터 전송중 오류가 발생",
                    Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

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
                                if(b == '\n'){
                                    byte[] encodeBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodeBytes, 0, encodeBytes.length);

                                    final String data = new String(encodeBytes, Charset.forName("US-ASCII"));
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d(TAG, "run: " + data);
                                            //여기에 쓰세요 data
                                        }
                                    });
                                }else{
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(activity.getApplicationContext(), "데이터 수신 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                        activity.finish();
                    }
                }
            }
        }).start();

    }

    private BluetoothDevice getDeviceFromDevices(String deviceName, Set<BluetoothDevice> devices){
        for(BluetoothDevice device : devices){
            if(deviceName.equals(device.getName())){
                return device;
            }
        }
        return null;
    }


}
