package com.dsm.wakeheart.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.dsm.wakeheart.R;
import com.skyfishjy.library.RippleBackground;

/**
 * Created by parktaeim on 2017. 9. 24..
 */

public class AlarmCustomDialog extends Activity {

    private Button okButton;
    private Vibrator vibrator;
     MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alarm);

        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        if(AlarmManageActivity.isSoundChecked == true){
            mediaPlayer = MediaPlayer.create(this,R.raw.music);
            mediaPlayer.setLooping(true);  //음악 무한 재생
            mediaPlayer.start();  // 음악 재생
        }

        if(AlarmManageActivity.isVibrateChecked == true){
            vibrator.vibrate(new long[]{100,1000,100,500,100,500,100,1000}, 0);
        }

        okButton = (Button) findViewById(R.id.okButton);
        //확인 버튼 눌러 다이얼로그 끄기, 음악재생도 함께 종료
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                mediaPlayer.stop();
                vibrator.cancel();
            }
        });

    }

}
