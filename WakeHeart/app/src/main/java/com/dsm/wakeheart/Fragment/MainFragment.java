package com.dsm.wakeheart.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.Toast;

import com.dsm.wakeheart.Activity.MainActivity;
import com.dsm.wakeheart.Activity.SettingsActivity;
import com.dsm.wakeheart.AlarmService;
import com.dsm.wakeheart.Arduino.BluetoothControl;
import com.dsm.wakeheart.Arduino.MainService;
import com.dsm.wakeheart.Arduino.SetAndGetClass;
import com.dsm.wakeheart.R;
import com.txusballesteros.SnakeView;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by parktaeim on 2017. 8. 25..
 */


@SuppressLint("ValidFragment")
public class MainFragment extends android.support.v4.app.Fragment {

    ImageView settingsBtn;
    Button onOff_Btn;
    RelativeLayout offLayout;
    RelativeLayout onLayout;
    LinearLayout container;
    View rootView;
    AnimationDrawable animation;
    TextView textView;
    SnakeView snakeView;
    LinkedList<Float> datas = new LinkedList<>();


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
                onLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                activity.finish();
                            }
                        },1000);
                    }
                });
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

        textView = (TextView) rootView.findViewById(R.id.bpmTextView);
        snakeView = (SnakeView) rootView.findViewById(R.id.snake);


        snakeView.setMinValue(30);
        snakeView.setMaxValue(110);
        setBackgroundGradient();

        listenBeats();

        return rootView;
    }

    MainActivity activity;
    public MainFragment(MainActivity activity){
        this.activity = activity;
    }

    private void listenBeats(){
        final Timer timer = new Timer();
        final Handler hadler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputStream inputStream = activity.getInputStream();
                if(inputStream == null){

                }else{
                    try {
                        if(inputStream.available() > 0){
                            byte[] readBuffer = new byte[inputStream.available()];
                            inputStream.read(readBuffer);
                            final String data = new String(readBuffer);
                            Log.e("data!", data);
                            final Float fData = Float.parseFloat(data);
                            if(fData >= 30 && fData <= 110){
                                hadler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(fData < 100 ){
                                            datas.add(fData);
                                        }
                                        textView.setText(data);
                                        snakeView.addValue(fData);
                                        dataCals();
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000, 1000);

    }

    private SharedPreferences getPreferences() {
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }
    
    private void dataCals(){
        if(datas.size() > 4 ){
            if(datas.get(1) > datas.get(2)){
                if(datas.get(2) > datas.get(3)){
                  if(datas.get(3) > datas.get(4)){
                          Intent intent = new Intent(getActivity(), AlarmService.class);
                          getActivity().startService(intent);
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
