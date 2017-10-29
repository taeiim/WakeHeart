package com.dsm.wakeheart.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dsm.wakeheart.Model.LoginObjectModel;
import com.dsm.wakeheart.Network.APIUrl;
import com.dsm.wakeheart.R;
import com.dsm.wakeheart.Network.RestAPI;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 8. 28..
 */

public class LoginActivity extends AppCompatActivity {
    Context context;
    private TextInputEditText input_id;
    private TextInputEditText input_pw;
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
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //SplashActivity 종료
        SplashActivity splashActivity = (SplashActivity) SplashActivity.splashActiviity;
        splashActivity.finish();

    }

    private void login() {
        input_id = (TextInputEditText) findViewById(R.id.input_id);
        input_pw = (TextInputEditText) findViewById(R.id.input_pw);

        loginBtn = (Button) findViewById(R.id.LoginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //id,pw가 입력되지 않았으면
                if (input_id == null || input_id.length() == 0) {
                    Toast.makeText(LoginActivity.this, "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (input_pw == null || input_pw.length() == 0) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                id = input_id.getText().toString();
                pw = input_pw.getText().toString();


                Retrofit builder = new Retrofit.Builder()
                        .baseUrl(APIUrl.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                LoginObjectModel loginObject = new LoginObjectModel(id,pw);
                RestAPI restAPI = builder.create(RestAPI.class);
                Call<JsonObject> call = restAPI.logIn(loginObject);

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("response code ====",String.valueOf(response.code()));
                        if(response.code() == 200){
                            Log.d("response==", response.body().toString());
                            String token = response.body().getAsJsonPrimitive("access_token").getAsString();
                            Log.d("token === ", token.toString());

                            SharedPreferences sharedPreferences = getSharedPreferences("token pref",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token",token);
                            editor.commit();

                            Collection<?> collection = sharedPreferences.getAll().values();
                            Log.d("after login pref ----",collection.toString());


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, id + "님 환영합니다!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });


//                Retrofit builder = new Retrofit.Builder()
//                        .baseUrl(APIUrl.API_BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                RestAPI restAPI = builder.create(RestAPI.class);
//                Call<JsonObject> call = restAPI.logIn(id,pw);
//
//                call.enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                        String stringResponse = response.body().toString();
//                        Log.i("response----------",stringResponse);
//
//                        JsonElement jsonElement = response.body().getAsJsonPrimitive("success");
//                        Log.d("jsonElement-----------",jsonElement.toString());
//                        if(jsonElement.toString().equals("true")){
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                            Toast.makeText(LoginActivity.this,id+"님 환영합니다!",Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                    }
//                });

            }
        });


    }


}


