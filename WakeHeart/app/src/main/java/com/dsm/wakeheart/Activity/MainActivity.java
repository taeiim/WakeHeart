package com.dsm.wakeheart.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.dsm.wakeheart.Arduino.MainService;
import com.dsm.wakeheart.DataManager;
import com.dsm.wakeheart.Fragment.Graph1Fragment;
import com.dsm.wakeheart.Fragment.Graph2Fragment;
import com.dsm.wakeheart.Fragment.HealthFragment;
import com.dsm.wakeheart.Fragment.HelperFragment;
import com.dsm.wakeheart.Fragment.MainFragment;
import com.dsm.wakeheart.R;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jp.wasabeef.blurry.Blurry;


public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {
    AHBottomNavigation bottomNavigation;
    public static Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();

        SplashActivity splashActivity = (SplashActivity) SplashActivity.splashActiviity;
        splashActivity.finish();

        mainActivity = MainActivity.this;

        Intent intent = new Intent(this, MainService.class);
        stopService(intent);

//        int p = 2;
//        Intent i = getIntent();
//        p = i.getExtras().getInt("position");
//        if(p != 2){
//
//        }


        String deviceName = DataManager.getDataManager().getData(this, "device name");
        if(DataManager.getDataManager().getData(this, "device name").isEmpty()){
            setOnBluetooth();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            for(BluetoothDevice device : bluetoothAdapter.getBondedDevices()){
                if(device.getName().equals(deviceName)){
                    connect(device, deviceName);
                    break;
                }
            }
        }
    }
    
    private BluetoothAdapter bluetoothAdapter;
    private void setOnBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            showToast("기기가 블루투스를 지원하지 않습니다");
            finish();
        }else{
            turnOnBlutooth();
        }
    }
    
    private void turnOnBlutooth(){
        if(!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 100);
        }else{
            setDevice();
        }
    }

    private void setDevice(){
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivityForResult(intent, 200);
    }

    private String deviceName = "";
    
    private void selectDevice(){
        final Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        if(devices.size() == 0){
            showToast("등록된 기기가 없습니다.");
            finish();
        }else{
            final List<String> list = new ArrayList<>();

            for(BluetoothDevice device : devices){
                list.add(device.getName());
            }
            
            final CharSequence[] items = list.toArray(new CharSequence[list.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("기기를 선택하세요")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String deviceName = list.get(i);
                            for(BluetoothDevice device : devices){
                                if(device.getName().equals(deviceName)){
                                    connect(device, deviceName);
                                    break;
                                }
                            }
                        }
                    });

            builder.create().show();
        }
    }

    private InputStream inputStream = null;

    public InputStream getInputStream() {
        return inputStream;
    }

    private void connect(BluetoothDevice device, String deviceName){
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
            inputStream = socket.getInputStream();

            DataManager.getDataManager().saveData(this, "device name", deviceName);

        } catch (IOException e) {
            e.printStackTrace();
            showToast("연결 중 오류가 발생했습니다.");
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                setDevice();
            }else{
                showToast("블루투스를 실행하여야 어플을 사용 할 수 있습니다.");
                finish();
            }
        }else{
            selectDevice();
        }
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void createNavItems() {
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_graph1, R.drawable.chart1_icon, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_graph2, R.drawable.chart2_icon, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_main, R.drawable.button_icon, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_health, R.drawable.health_icon, R.color.colorPrimary);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.tab_helper, R.drawable.helper_icon, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setCurrentItem(2);  //처음 시작 화면 main
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {
            Graph1Fragment graph1Fragment = new Graph1Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, graph1Fragment).commit();
        } else if (position == 1) {
            Graph2Fragment graph2Fragment = new Graph2Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, graph2Fragment).commit();
        } else if (position == 2) {
            MainFragment mainFragment = new MainFragment(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, mainFragment).commit();
        } else if (position == 3) {
            HealthFragment healthFragment = new HealthFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, healthFragment).commit();
        } else if (position == 4) {
            HelperFragment helperFragment = new HelperFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, helperFragment).commit();
        }
        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xxx", "onDestroy: " + "check" );

        Intent intent = new Intent(this, MainService.class);
        startService(intent);

        try {
            if(inputStream != null){
                inputStream.close();
            }
            Log.e("xxx", "onDestroy: " + "check2" );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
