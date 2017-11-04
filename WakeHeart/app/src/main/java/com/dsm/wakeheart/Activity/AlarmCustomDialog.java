package com.dsm.wakeheart.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alarm);

        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        SharedPreferences soundPref = getSharedPreferences("soundSwitch",MODE_PRIVATE);
        boolean isSoundChecked = soundPref.getBoolean("soundSwitch",true);
        SharedPreferences vibratePref = getSharedPreferences("vibrateSwitch",MODE_PRIVATE);
        boolean isVibrateChecked = vibratePref.getBoolean("vibrateSwitch",true);

        Log.d("isShoundChecked",String.valueOf(isSoundChecked));
        if (isSoundChecked) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.setLooping(true);  //음악 무한 재생
            mediaPlayer.start();  // 음악 재생

        }

        Log.d("isVibrateChecked",String.valueOf(isVibrateChecked));
        if (isVibrateChecked) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//            vibrator.vibrate(500);
//            vibrator.vibrate(new long[]{100,1000,100,500,100,500,100,1000}, 0);
            vibrator.vibrate(new long[]{1000,1000}, 0);

        }

        startTTS();

        final MediaPlayer ttsPlayer = new MediaPlayer();
        try {
            ttsPlayer.setDataSource(f.getAbsolutePath());
            ttsPlayer.prepare();
            ttsPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        okButton = (Button) findViewById(R.id.okButton);
        //확인 버튼 눌러 다이얼로그 끄기, 음악재생도 함께 종료
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();

                }
                if(vibrator.hasVibrator()){
                    vibrator.cancel();
                }

                if(ttsPlayer.isPlaying()){
                    ttsPlayer.stop();
                }

                if (AccountManageActivity.position == 0) {  // 학생
                    Intent intent = new Intent(AlarmCustomDialog.this, MainActivity.class);
                    intent.putExtra("helper","helper");
                    intent.putExtra("position", "0");
                    startActivity(intent);
                } else if (AccountManageActivity.position == 1) {  //운전자
                    Intent intent = new Intent(AlarmCustomDialog.this, MainActivity.class);
                    intent.putExtra("helper","helper");
                    intent.putExtra("position", "1");
                    startActivity(intent);
                } else if(AccountManageActivity.position == 2){  //일반
                    Intent intent = new Intent(AlarmCustomDialog.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void startTTS(){
        final String clientId = "oPDj1y9Xk6B_IakJkQ0J";//애플리케이션 클라이언트 아이디값";
        final String clientSecret = "HYjkQGafvO";//애플리케이션 클라이언트 시크릿값";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String text = URLEncoder.encode("만나서 반갑습니다.", "UTF-8"); // 13자
                    String apiURL = "https://openapi.naver.com/v1/voice/tts.bin";
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    // post request
                    String postParams = "speaker=mijin&speed=0&text=" + text;
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(postParams);
                    wr.flush();
                    wr.close();
                    int responseCode = con.getResponseCode();
                    BufferedReader br;
                    if(responseCode==200) { // 정상 호출
                        InputStream is = con.getInputStream();
                        int read = 0;
                        byte[] bytes = new byte[1024];
                        // 랜덤한 이름으로 mp3 파일 생성
                        String tempname = Long.valueOf(new Date().getTime()).toString();
                        f = new File(tempname + ".mp3");
                        f.createNewFile();
                        OutputStream outputStream = new FileOutputStream(f);
                        while ((read =is.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }
                        is.close();
                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = br.readLine()) != null) {
                            response.append(inputLine);
                        }
                        br.close();
                        System.out.println(response.toString());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();

    }

}
