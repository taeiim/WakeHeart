package com.dsm.wakeheart.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dsm.wakeheart.R;
import com.txusballesteros.SnakeView;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class MainFragment extends android.support.v4.app.Fragment {

    Button onOff_Btn;
    RelativeLayout offLayout;
    RelativeLayout onLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main,container,false);


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
        snakeView.setMaxValue(150);

        return rootView;
    }
}
