package com.dsm.wakeheart.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dsm.wakeheart.Activity.SettingsActivity;
import com.dsm.wakeheart.AlarmService;
import com.dsm.wakeheart.R;
import com.txusballesteros.SnakeView;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class MainFragment extends android.support.v4.app.Fragment {

    ImageView settingsBtn;
    Button onOff_Btn;
    RelativeLayout offLayout;
    RelativeLayout onLayout;
    Boolean sleep;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main,container,false);

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

        final SnakeView snakeView = (SnakeView) rootView.findViewById(R.id.snake);
        snakeView.setMinValue(0);
        snakeView.setMaxValue(200);
        snakeView.addValue(100);
        snakeView.addValue(50);
        snakeView.addValue(80);
        snakeView.addValue(20);
        snakeView.addValue(40);
        snakeView.addValue(130);
        snakeView.addValue(140);
        snakeView.addValue(90);
        snakeView.addValue(60);
        snakeView.addValue(70);
        snakeView.addValue(55);
        snakeView.addValue(75);



        sleep();

        return rootView;
    }

    private void sleep() {
        sleep = true;
        if(sleep==true){
            getActivity().startService(new Intent(getActivity(),AlarmService.class));

        }
    }


}
