package com.dsm.wakeheart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dsm.wakeheart.R;
import com.dsm.wakeheart.RestAPI;
import com.dsm.wakeheart.RestRequestHelper;
import com.dsm.wakeheart.Server.resource.APIUrl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
    int gender=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText idEditText = (EditText) findViewById(R.id.idEditText);
        final EditText pwEditText = (EditText) findViewById(R.id.pwEditText);
        final EditText pwCheckEditText = (EditText) findViewById(R.id.pwCheckEditText);
        final RadioRealButtonGroup genderButtonGroup = (RadioRealButtonGroup) findViewById(R.id.genderBtnGroup);
        RadioRealButton genderMan = (RadioRealButton) findViewById(R.id.genderManBtn);
        RadioRealButton genderWoman = (RadioRealButton) findViewById(R.id.genderWomanBtn);
        final com.shawnlin.numberpicker.NumberPicker agePick = (com.shawnlin.numberpicker.NumberPicker) findViewById(R.id.age_number_picker);
        Button signupBtn = (Button) findViewById(R.id.signUpBtn);


        final RestAPI restAPI = new RestRequestHelper().getRetrofit();

        // 성별 체크 한 값 가져와서 gender에 저장 (0:남자, 1:여자)
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
        signupBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //edittext에 작성한 id,pw,pwcheck 값 가져오기
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                String pwCheck = pwCheckEditText.getText().toString();



                //edittext에 id나 pw,pwcheck가 비어있거나 성별이 선택되지 않았을때 토스트 띄워줌.
                if(id==null || id.length() ==0 ){
                    Toast.makeText(SignUpActivity.this,"아이디가 없어요!",Toast.LENGTH_SHORT).show();
                    return;
                }else if(pw == null || pw.length()==0){
                    Toast.makeText(SignUpActivity.this,"비밀번호가 비었어요!",Toast.LENGTH_SHORT).show();
                    return;
                }else if(pwCheck == null || pwCheck.length() ==0){
                    Toast.makeText(SignUpActivity.this,"비밀번호를 한번 더 체크해주세요!",Toast.LENGTH_SHORT).show();
                    return;
                }else if(gender==2){
                    Toast.makeText(SignUpActivity.this,"성별을 선택해 주세요 !",Toast.LENGTH_SHORT).show();
                    return;
                }

                //나이 값 age 에 저장
                int age = agePick.getValue();

                //비밀번호와 비밀번호 체크 값이 같으면
                if(pw.equals(pwCheck)){
                    //Retrofit
                    Retrofit builder = new Retrofit.Builder()
                            .baseUrl(APIUrl.API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RestAPI restAPI = builder.create(RestAPI.class);
                    Call<JsonObject> call = restAPI.signUp(id,pw,gender,age);

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonElement jsonElement = response.body().getAsJsonPrimitive("success");
                            Log.d("JsonElement ----------",jsonElement.toString());
                            if(jsonElement.toString().equals("true")){
                                Intent intent = new Intent(SignUpActivity.this,SignUpSuccessActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });

                } else{
                    Toast.makeText(SignUpActivity.this,"비밀번호와 비밀번호 체크 값이 다릅니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }
}
