package com.dsm.wakeheart.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dsm.wakeheart.Activity.SettingsActivity;
import com.dsm.wakeheart.AlarmService;
import com.dsm.wakeheart.Arduino.BluetoothControl;
import com.dsm.wakeheart.Arduino.MainService;
import com.dsm.wakeheart.Arduino.SetAndGetClass;
import com.dsm.wakeheart.R;
import com.txusballesteros.SnakeView;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class MainFragment extends android.support.v4.app.Fragment {

    ImageView settingsBtn;
    Button onOff_Btn;
    RelativeLayout offLayout;
    RelativeLayout onLayout;
    Boolean sleep;
    LinearLayout container;
    View rootView;
    AnimationDrawable animation;
    TextView textView;
    BluetoothControl blutoothControl;
    SnakeView snakeView;
    int readBufferPosition=0;
    LinkedList<Integer> datas = new LinkedList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main,container,false);


        //설정 버튼 누르면 설정 액티비티로 넘어감
        settingsBtn = (ImageView) rootView.findViewById(R.id.setting_icon);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        onOff_Btn = (Button) rootView.findViewById(R.id.OnOffButton);
        onOff_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offLayout = (RelativeLayout) rootView.findViewById(R.id.offLayout);
                offLayout.setVisibility(View.GONE);
                onLayout = (RelativeLayout) rootView.findViewById(R.id.onLayout);
                onLayout.setVisibility(View.VISIBLE);

            }
        });


        onLayout = (RelativeLayout) rootView.findViewById(R.id.onLayout);
        onLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offLayout = (RelativeLayout) rootView.findViewById(R.id.offLayout);
                offLayout.setVisibility(View.VISIBLE);

                onLayout.setVisibility(View.GONE);
            }
        });


        Log.d("xxx", "data is" + SetAndGetClass.getInstance().getInputStream());

        textView = (TextView) rootView.findViewById(R.id.bpmTextView);
        snakeView = (SnakeView) rootView.findViewById(R.id.snake);


        snakeView.setMinValue(30);
        snakeView.setMaxValue(100);

        if (SetAndGetClass.getInstance().getInputStream() == null) { //isOn이름을 가진 bool값을 받아온다. 기본값 false
            blutoothControl = new BluetoothControl(getActivity(), getContext());
            threadOn = true;
            startService(getActivity());
        }else{
            ListenThread = true;
            startListenForData(SetAndGetClass.getInstance().getInputStream(), textView, snakeView);
        }

        sleep();
        setBackgroundGradient();

        return rootView;
    }

    private boolean ListenThread = true;

    private void startListenForData(final InputStream inputStream, final TextView textView, final SnakeView snakeView) {
        final Handler handler = new Handler();
        final byte[] readBuffer = new byte[1024];
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (ListenThread){
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
                                        int dataInt = 0;
                                        dataInt = Integer.parseInt(data);
                                        readBufferPosition = 0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("data is! : ", data);
                                                Log.d("textView", "" + textView.toString() + snakeView.toString());
                                                textView.setText(data);
                                                int dataInt = 0;
                                                try {


                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                                if(dataInt >= 30 && dataInt <= 100){
                                                    snakeView.addValue(dataInt);
                                                }

//                                                Calendar calendar = Calendar.getInstance();
//                                                String strCal = calendar.getTime().toString();Log.d("date-----",strCal);
//                                                if(strCal.equals("Wed Sep 27 23:33:00 GMT+09:00 2017")){
//

//                                                }
                                            }
                                        });
                                        if(dataInt < 60){
                                            datas.add(dataInt);
                                            dataCals();
                                        }
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
                    if (blutoothControl.getInputStream() != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                SetAndGetClass.getInstance().setBlutoothControl(blutoothControl);
                                Intent intent = new Intent(context, MainService.class);
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
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }
    
    private void dataCals(){
        if(datas.size() > 3 ){
            if(datas.get(0) > datas.get(1)){
                if(datas.get(1) > datas.get(2)){
                  if(datas.get(2) > datas.get(3)){
                      if(datas.get(3)> datas.get(4)){
                          Intent intent = new Intent(getActivity(), AlarmService.class);
                          getActivity().startService(intent);
                          datas.clear();
                      }
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
        super.onDestroy();
        Log.d("xxx", "onDestroy: ");
        threadOn = false;
    }

    private void setBackgroundGradient() {
        container = (LinearLayout) rootView.findViewById(R.id.container);

         animation = (AnimationDrawable) container.getBackground();
        animation.setEnterFadeDuration(5000);
        animation.setExitFadeDuration(1000);

    }
    // Starting animation:- start the animation on onResume.
    @Override
    public void onResume() {
        super.onResume();
        if (animation != null && !animation.isRunning())
            animation.start();
    }

    // Stopping animation:- stop the animation on onPause.
    @Override
    public void onPause() {
        super.onPause();
        if (animation != null && animation.isRunning())
            animation.stop();
    }

    private void sleep() {

//        sleep = true;
//        if(sleep == true){
//            getActivity().startService(new Intent(getActivity(),AlarmService.class));
//        }

}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ListenThread = false;
    }

}
