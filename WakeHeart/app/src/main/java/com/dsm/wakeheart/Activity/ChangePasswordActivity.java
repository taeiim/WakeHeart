package com.dsm.wakeheart.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsm.wakeheart.Network.APIUrl;
import com.dsm.wakeheart.Network.RestAPI;
import com.dsm.wakeheart.R;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.Collection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 9. 9..
 */

public class ChangePasswordActivity extends AppCompatActivity {
    ImageView back_icon;
    private EditText pwEditText;
    private EditText rePwEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        back_icon = (ImageView) findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        EditText currentPWEditText = (EditText) findViewById(R.id.currentPWEditText);
        pwEditText = (EditText) findViewById(R.id.passwordEditText);
        rePwEditText = (EditText) findViewById(R.id.rePasswordEditText);
        FButton pwChangeBtn = (FButton) findViewById(R.id.PWchangeButton);

        pwChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pwEditText.getText().toString().equals(rePwEditText.getText().toString()) == true) {

                    final SweetAlertDialog dialog = new SweetAlertDialog(ChangePasswordActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("비밀번호 변경")
                            .setContentText("정말 비밀번호를 변경하시겠어요?")
                            .setConfirmText("예")
                            .setCancelText("아니오");
                    dialog.show();

                    dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                        }
                    });

                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dialog.cancel();

                            Log.d("retrofit before","no start~~");

                            Retrofit builder = new Retrofit.Builder()
                                    .baseUrl(APIUrl.API_BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

//                            OkHttpClient httpClient = new OkHttpClient.Builder()
//                                    .addInterceptor(new Interceptor() {
//                                        @Override
//                                        public Response intercept(Chain chain) throws IOException {
//                                            Request.Builder ongoing = chain.request().newBuilder();
//                                            ongoing.addHeader("Accept", "application/json;versions=1");
//                                            if (isUserLoggedIn()) {
//                                                ongoing.addHeader("Authorization", getToken());
//                                            }
//                                            return chain.proceed(ongoing.build());
//                                        }
//                                    })
//                                    .build();

                            Log.d("retrofit build ====","yeah~");

                            SharedPreferences prefs = getSharedPreferences("token pref", MODE_PRIVATE);
                            Collection<?> collection = prefs.getAll().values();

                            Log.d("pwAct pref ----",collection.toString());

                            String result = collection.toString();
                            Log.d("result ----",result);

                            result = result.substring(1);
                            result = result.substring(0,result.length()-1);
                            Log.d("result substring ----",result);

//                            String result = prefs.getString("a", "0"); //키값, 디폴트값

                            String pw = pwEditText.getText().toString();
                            Log.d("result=="+result,"pw=="+pw);

                            RestAPI restAPI = builder.create(RestAPI.class);
                            Log.d("retrofit create ====","yeah~");

                            String header = "JWT "+result;
                            Call<Void> call = restAPI.changePw(header, pw);
                            Log.d("1 ==",header);
                            Log.d("2==",pw);

                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Log.d("retrofit start ====","yeah~");
                                    Log.d("response code ===",String.valueOf(response.code()));
                                    if (response.code() == 201) {
                                        new SweetAlertDialog(ChangePasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                .setContentText("비밀번호가 변경되었습니다.")
                                                .setConfirmText("확인").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.cancel();
                                            }
                                        }).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }

                    });

                } else {
                    Toast.makeText(ChangePasswordActivity.this, "비밀번호와 비밀번호 확인 값이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}