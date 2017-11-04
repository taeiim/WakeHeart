package com.dsm.wakeheart.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.dsm.wakeheart.Fragment.HelperFragment;
import com.dsm.wakeheart.Fragment.RestAreaFragment;
import com.dsm.wakeheart.Fragment.WiseSayingFragment;
import com.dsm.wakeheart.NaverTTS;
import com.dsm.wakeheart.R;
import com.skyfishjy.library.RippleBackground;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by parktaeim on 2017. 9. 24..
 */

public class AlarmCustomDialog extends Activity {

    private Button okButton;
    private Vibrator vibrator;
    MediaPlayer mediaPlayer;
    File f;
    MediaPlayer ttsPlayer;
    String getTTStext;
    String[] ttsText;
    private NaverTTSTask naverTTSTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alarm);

        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        SharedPreferences soundPref = getSharedPreferences("soundSwitch", MODE_PRIVATE);
        boolean isSoundChecked = soundPref.getBoolean("soundSwitch", true);
        SharedPreferences vibratePref = getSharedPreferences("vibrateSwitch", MODE_PRIVATE);
        boolean isVibrateChecked = vibratePref.getBoolean("vibrateSwitch", true);
        SharedPreferences ttsPref = getSharedPreferences("ttsSwitch",MODE_PRIVATE);

        Log.d("isShoundChecked", String.valueOf(isSoundChecked));
        if (isSoundChecked) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.setLooping(true);  //음악 무한 재생
//            mediaPlayer.start();  // 음악 재생

        }

        Log.d("isVibrateChecked", String.valueOf(isVibrateChecked));
        if (isVibrateChecked) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            vibrator.vibrate(500);
//            vibrator.vibrate(new long[]{100,1000,100,500,100,500,100,1000}, 0);
//            vibrator.vibrate(new long[]{1000, 1000}, 0);

        }

        Log.d("ttsPref onclick======",String.valueOf(ttsPref.getBoolean("ttsSwitch",true)));
        if(ttsPref.getBoolean("ttsSwitch",true)){
            SharedPreferences ttsTextPref = getSharedPreferences("ttsEditText",MODE_PRIVATE);
            getTTStext = ttsTextPref.getString("ttsEditText","null");

            Log.d("ttsText!!========",getTTStext);

            if(getTTStext.length() > 0){
                ttsText = new String[] {getTTStext};

                naverTTSTask = new NaverTTSTask();
                naverTTSTask.execute(ttsText);
            }else{
                Log.d("text length is ======","0ㅠㅠ!");
                return;
            }

        }


        okButton = (Button) findViewById(R.id.okButton);
        //확인 버튼 눌러 다이얼로그 끄기, 음악재생도 함께 종료
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();

                }
//                if (vibrator.hasVibrator()) {
//                    vibrator.cancel();
//                }

                ttsPlayer.stop();

                if (AccountManageActivity.position == 0) {  // 학생
                    Intent intent = new Intent(AlarmCustomDialog.this, MainActivity.class);
                    intent.putExtra("helper", "helper");
                    intent.putExtra("position", "0");
                    startActivity(intent);
                } else if (AccountManageActivity.position == 1) {  //운전자
                    Intent intent = new Intent(AlarmCustomDialog.this, MainActivity.class);
                    intent.putExtra("helper", "helper");
                    intent.putExtra("position", "1");
                    startActivity(intent);
                } else if (AccountManageActivity.position == 2) {  //일반
                    Intent intent = new Intent(AlarmCustomDialog.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private class NaverTTSTask extends AsyncTask<String[],Void ,String>{

        @Override
        protected String doInBackground(String[]... strings) {
            Log.d("naverttstask start",ttsText[0].toString());
            NaverTTS.main(ttsText);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
