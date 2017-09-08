package com.dsm.wakeheart.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dsm.wakeheart.Activity.SettingsActivity;
import com.dsm.wakeheart.R;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class HealthFragment extends android.support.v4.app.Fragment {

    View rootView;
    ImageView manImage;
    ImageView womanImage;
    RelativeLayout imgLayout;
    ImageView settingsBtn;

    int bpm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_health,container,false);

        setImage();
        setBPM();

        //설정 버튼 누르면 설정 액티비티로 넘어감
        settingsBtn = (ImageView) rootView.findViewById(R.id.setting_icon);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

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
