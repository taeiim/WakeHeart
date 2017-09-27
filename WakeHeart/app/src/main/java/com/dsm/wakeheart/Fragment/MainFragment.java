package com.dsm.wakeheart.Fragment;

import android.app.Fragment;
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

import com.dsm.wakeheart.Activity.SettingsActivity;
import com.dsm.wakeheart.AlarmService;
import com.dsm.wakeheart.Arduino.BluetoothControl;
import com.dsm.wakeheart.Arduino.MainService;
import com.dsm.wakeheart.Arduino.SetAndGetClass;
import com.dsm.wakeheart.R;
import com.txusballesteros.SnakeView;

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

        sleep();
        setBackgroundGradient();

        return rootView;
    }

    private void setBackgroundGradient() {
        container = (LinearLayout) rootView.findViewById(R.id.container);

         animation = (AnimationDrawable) container.getBackground();
        animation.setEnterFadeDuration(6000);
        animation.setExitFadeDuration(2000);

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
        sleep = true;
        if(sleep == true){
            getActivity().startService(new Intent(getActivity(),AlarmService.class));
        }
    }


}
