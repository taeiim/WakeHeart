package com.dsm.wakeheart.Arduino;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothControl {

    private static final String TAG = "BluetoothService";
    private static final int REQUEST_ENABLE_BT = 10;

    private BluetoothAdapter bluetoothAdapter;
    private Activity activity;
    private Context context;

    public BluetoothControl(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        checkEnableBluetooth();
    }

    public void checkEnableBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            Toast.makeText(activity.getApplicationContext(), "기기가 블루투스를 지원합니다.", Toast.LENGTH_LONG).show();
            checkOnOffBlutooth();
        } else {
            Toast.makeText(activity.getApplicationContext(), "기기가 블루투스를 지원하지 않습니다", Toast.LENGTH_LONG).show();


//            activity.finish();
        }
    }

    private void checkOnOffBlutooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBlutoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBlutoothIntent, REQUEST_ENABLE_BT);
            selectDevice();
        } else {
            selectDevice();
        }
    }

    public void selectDevice() {
        final Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();


        if (bluetoothDevices.size() == 0) {
            Toast.makeText(activity.getApplicationContext(), "페어링된 기기가 없습니다.", Toast.LENGTH_LONG).show();
            activity.finish();
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("장치 선택");
        final List<String> deviceList = new ArrayList<String>();
        for (BluetoothDevice device : bluetoothDevices) {
            deviceList.add(device.getName());
        }

        final CharSequence[] items = deviceList.toArray(new CharSequence[deviceList.size()]);

        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                connectToSelectedDevice(getDeviceFromDevices(deviceList.get(i), bluetoothDevices));
            }
        });

        dialog.create().show();

    }

    private void connectToSelectedDevice(BluetoothDevice selectDevice) {

        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            BluetoothSocket bluetoothSocket = selectDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();

            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();

            //startListenForData(inputStream);
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
//            activity.finish();
        }
    }

    private OutputStream outputStream;
    private InputStream inputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void sendData(String msg) {
        String temp = msg + "\n";
        Log.d(TAG, "sendData: " + outputStream);
        try {
            outputStream.write(temp.getBytes());
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), "데이터 전송중 오류가 발생",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            activity.finish();
        }
    }

    private BluetoothDevice getDeviceFromDevices(String deviceName, Set<BluetoothDevice> devices) {
        for (BluetoothDevice device : devices) {
            if (deviceName.equals(device.getName())) {
                return device;
            }
        }
        return null;
    }


}
