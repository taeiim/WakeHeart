package com.dsm.wakeheart.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.dsm.wakeheart.R;

/**
 * Created by parktaeim on 2017. 9. 26..
 */

public class SplashActivity extends Activity {
    public static Activity splashActiviity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },5000);

        splashActiviity = SplashActivity.this;

    }
}