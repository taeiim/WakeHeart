package com.dsm.wakeheart.Activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dsm.wakeheart.R;

import java.util.Collection;

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

                SharedPreferences pref = getSharedPreferences("token pref", MODE_PRIVATE);
                Collection<?> collection = pref.getAll().values();

                Log.d("splash pref ----",collection.toString());

                if(collection.toString().equals("[]") == false){
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }else if(collection.toString().equals("[]")){
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

            }
        },5000);

        finish();
    }
}