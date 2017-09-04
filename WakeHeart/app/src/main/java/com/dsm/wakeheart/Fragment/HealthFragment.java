package com.dsm.wakeheart.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dsm.wakeheart.R;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class HealthFragment extends android.support.v4.app.Fragment {

    View rootView;
    ImageView manImage;
    ImageView womanImage;
    RelativeLayout imgLayout;
    int bpm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_health,container,false);


        setImage();


        setBPM();
        return rootView;
    }

    private void setBPM(){
        bpm = 90;

    }
    private void setImage(){
        manImage= (ImageView) rootView.findViewById(R.id.man_img);
        womanImage = (ImageView) rootView.findViewById(R.id.woman_img);
        imgLayout = (RelativeLayout) rootView.findViewById(R.id.imageLayout);

        womanImage.setVisibility(View.GONE);

        manImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manImage.setVisibility(View.GONE);
                womanImage.setVisibility(View.VISIBLE);
            }
        });

        womanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manImage.setVisibility(View.VISIBLE);
                womanImage.setVisibility(View.GONE);
            }
        });

    }

}
