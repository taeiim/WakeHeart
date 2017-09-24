package com.dsm.wakeheart.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsm.wakeheart.R;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by parktaeim on 2017. 8. 28..
 */

public class LoginActivity extends AppCompatActivity {
    Context context;
    private AppCompatEditText input_id;
    private AppCompatEditText input_pw;
    private Button loginBtn;
    String id;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login();

        TextView goSignUp = (TextView) findViewById(R.id.goSignUpTextView);

        //회원가입으로
        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login() {
        input_id = (AppCompatEditText) findViewById(R.id.input_id);
        input_pw = (AppCompatEditText) findViewById(R.id.input_pw);

        id = input_id.getText().toString();
        pw = input_pw.getText().toString();

        loginBtn = (Button) findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                //id,pw가 입력되지 않았으면
//                if(id==null || id.length() ==0 ){
//                    Toast.makeText(LoginActivity.this,"아이디를 입력해주세요!",Toast.LENGTH_SHORT).show();
//                    return;
//                }else if(pw == null || pw.length()==0){
//                    Toast.makeText(LoginActivity.this,"비밀번호를 입력해주세요!",Toast.LENGTH_SHORT).show();
//                    return;
//                }

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }


}


