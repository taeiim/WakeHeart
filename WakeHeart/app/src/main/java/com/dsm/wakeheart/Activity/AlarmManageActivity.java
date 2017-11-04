package com.dsm.wakeheart.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.dsm.wakeheart.R;

/**
 * Created by parktaeim on 2017. 9. 8..
 */

public class AlarmManageActivity extends AppCompatActivity {
    ImageView back_icon;
    private Switch soundSwitch;
    private Switch vibrateSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmmanage);

        back_icon = (ImageView) findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setSwitch();
    }

    private void setSwitch() {
        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        vibrateSwitch = (Switch) findViewById(R.id.vibrateSwitch);

        SharedPreferences soundPref = getSharedPreferences("soundSwitch",MODE_PRIVATE);
        SharedPreferences vibratePref = getSharedPreferences("vibrateSwitch",MODE_PRIVATE);

        soundSwitch.setChecked(soundPref.getBoolean("soundSwitch",true));
        vibrateSwitch.setChecked(vibratePref.getBoolean("vibrateSwitch",true));

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("soundSwitch", MODE_PRIVATE).edit();
                editor.putBoolean("soundSwitch", isChecked);
                editor.commit();


            }
        });

        vibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("vibrateSwitch", MODE_PRIVATE).edit();
                editor.putBoolean("vibrateSwitch", isChecked);
                editor.commit();


            }
        });
    }


}
