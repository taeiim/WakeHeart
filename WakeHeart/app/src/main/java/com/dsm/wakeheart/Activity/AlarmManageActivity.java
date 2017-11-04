package com.dsm.wakeheart.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    private Switch ttsSwitch;
    private EditText ttsEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmmanage);

        back_icon = (ImageView) findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences ttsTextPref = getSharedPreferences("ttsEditText",MODE_PRIVATE);
                SharedPreferences.Editor ttsEditor = getSharedPreferences("ttsEditText",MODE_PRIVATE).edit();
                ttsEditor.putString(ttsEditText.getText().toString(),"null");
                ttsEditor.putString(ttsEditText.getText().toString(),"null");

                Log.d("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",ttsTextPref.getString("ttsEditText","zzz"));

                finish();
            }
        });

        ttsEditText = (EditText) findViewById(R.id.ttsEditText);
        SharedPreferences ttsTextPref = getSharedPreferences("ttsEditText",MODE_PRIVATE);
        ttsEditText.setText(ttsTextPref.getString("ttsEditText",""));
        Log.d("ㅋㅋㅋㅋㅋ",ttsTextPref.getString("ttsEditText","zzz"));

        setSwitch();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences ttsTextPref = getSharedPreferences("ttsEditText",MODE_PRIVATE);
        SharedPreferences.Editor ttsEditor = getSharedPreferences("ttsEditText",MODE_PRIVATE).edit();
        ttsEditor.putString(ttsEditText.getText().toString(),"null");

        Log.d("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",ttsTextPref.getString("ttsEditText","zzz"));

    }

    private void setSwitch() {
        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        vibrateSwitch = (Switch) findViewById(R.id.vibrateSwitch);
        ttsSwitch = (Switch) findViewById(R.id.ttsSwitch);

        SharedPreferences soundPref = getSharedPreferences("soundSwitch",MODE_PRIVATE);
        SharedPreferences vibratePref = getSharedPreferences("vibrateSwitch",MODE_PRIVATE);
        SharedPreferences ttsSwitchPref = getSharedPreferences("ttsSwitch",MODE_PRIVATE);


        soundSwitch.setChecked(soundPref.getBoolean("soundSwitch",true));
        vibrateSwitch.setChecked(vibratePref.getBoolean("vibrateSwitch",true));
        ttsSwitch.setChecked(ttsSwitchPref.getBoolean("ttsSwitch",false));
        if(ttsSwitchPref.getBoolean("ttsSwitch",false)){
            ttsEditText.setVisibility(View.VISIBLE);
        }


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

        ttsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("ttsSwitch", MODE_PRIVATE).edit();
                editor.putBoolean("ttsSwitch", isChecked);
                editor.commit();

                if(isChecked){
                    ttsEditText.setVisibility(View.VISIBLE);
                }else {
                    ttsEditText.setVisibility(View.GONE);
                }
            }
        });

    }


}
