package com.dsm.wakeheart.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dsm.wakeheart.R;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

/**
 * Created by parktaeim on 2017. 9. 19..
 */

public class SignUpSuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupsuccess);

        final KonfettiView viewKonfetti = (KonfettiView) findViewById(R.id.viewKonfetti);

        viewKonfetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewKonfetti.build()
                        .addColors(Color.rgb(250,70,152), Color.rgb(230,0,0),Color.rgb(175,135,251),Color.rgb(16,207,163),Color.rgb(255,203,19))
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.RECT, Shape.CIRCLE)
                        .addSizes(new Size(12,5f))
                        .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                        .stream(300, 5000L);
            }
        });

    }


}
