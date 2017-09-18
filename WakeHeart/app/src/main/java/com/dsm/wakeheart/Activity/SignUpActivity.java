package com.dsm.wakeheart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.dsm.wakeheart.Model.UserDTO;
import com.dsm.wakeheart.R;
import com.dsm.wakeheart.RestAPI;
import com.dsm.wakeheart.RestRequestHelper;
import com.dsm.wakeheart.Server.resource.APIUrl;
import com.dsm.wakeheart.Server.response.ResData;
import com.dsm.wakeheart.Server.service.SignService;
import com.google.gson.JsonObject;

import java.util.Map;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 8. 28..
 */

public class SignUpActivity extends AppCompatActivity {
    int gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText idEditText = (EditText) findViewById(R.id.idEditText);
        final EditText pwEditText = (EditText) findViewById(R.id.pwEditText);
        final RadioRealButtonGroup genderButtonGroup = (RadioRealButtonGroup) findViewById(R.id.genderBtnGroup);
        RadioRealButton genderMan = (RadioRealButton) findViewById(R.id.genderManBtn);
        RadioRealButton genderWoman = (RadioRealButton) findViewById(R.id.genderWomanBtn);
        final com.shawnlin.numberpicker.NumberPicker agePick = (com.shawnlin.numberpicker.NumberPicker) findViewById(R.id.age_number_picker);
        Button signupBtn = (Button) findViewById(R.id.signUpBtn);


        final RestAPI restAPI = new RestRequestHelper().getRetrofit();

        signupBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                genderButtonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
                    @Override
                    public void onClickedButton(RadioRealButton button, int position) {
                        if(position == 0)  {
                            gender = 0;
                        }else if(position == 1){
                            gender = 1;
                        }
                    }
                });
                int age = agePick.getValue();
                Retrofit builder = new Retrofit.Builder()
                        .baseUrl(APIUrl.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RestAPI restAPI = builder.create(RestAPI.class);
                Call<JsonObject> call = restAPI.signUp(id,pw,gender,age);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });

    }
}
