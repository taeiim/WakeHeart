package com.dsm.wakeheart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dsm.wakeheart.Model.UserDTO;
import com.dsm.wakeheart.R;
import com.dsm.wakeheart.RestAPI;
import com.dsm.wakeheart.RestRequestHelper;
import com.dsm.wakeheart.Server.response.ResData;
import com.dsm.wakeheart.Server.service.SignService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by parktaeim on 2017. 8. 28..
 */

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        RestAPI restAPI = new RestRequestHelper().getRetrofit();
        SignService.getRetrofit(getApplicationContext()).in("","").enqueue(new Callback<ResData>() {
            @Override
            public void onResponse(Call<ResData> call, Response<ResData> response) {
                if(response.body().res){
                    
                }else {

                }
            }

            @Override
            public void onFailure(Call<ResData> call, Throwable t) {

            }
        });
    }
}
