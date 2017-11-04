package com.dsm.wakeheart.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dsm.wakeheart.Network.APIUrl;
import com.dsm.wakeheart.Network.RestAPI;
import com.dsm.wakeheart.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Collection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 9. 8..
 */

public class AccountManageActivity extends AppCompatActivity {
    ImageView back_icon;
    private LinearLayout infoView;
    private LinearLayout changeInfoView;
    private MaterialSpinner ageSpinner;
    private int age;
    FButton okBtn;
    public static int position = 0;  // 0:학생 / 1:운전자 / 2:일반

    int changePosition;
    String changeGenderStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountmanage);

        back_icon = (ImageView) findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setView();

        RadioRealButtonGroup positionRadioRealButtonGroup = (RadioRealButtonGroup) findViewById(R.id.positionrBtnGroup);
        RadioRealButtonGroup genderRadioButtonGroup = (RadioRealButtonGroup) findViewById(R.id.account_genderBtnGroup);

        positionRadioRealButtonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                changePosition = position;
            }
        });

        genderRadioButtonGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                if(position == 0){
                    changeGenderStr = "M";

                }else if(position == 1){
                    changeGenderStr = "F";
                }
            }
        });

        final int changeAge = ageSpinner.getSelectedIndex() + 1;
        Log.d("changePositionInt",String.valueOf(changePosition));
        Log.d("changeGenderInt",String.valueOf(changeGenderStr));

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final SweetAlertDialog dialog = new SweetAlertDialog(AccountManageActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("내 정보 변경")
                        .setContentText("정말 내 정보를 변경하시겠어요?")
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

                        Log.d("retrofit before", "no start~~");

                        Retrofit builder = new Retrofit.Builder()
                                .baseUrl(APIUrl.API_BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        Log.d("retrofit build ====", "yeah~");

                        SharedPreferences prefs = getSharedPreferences("token pref", MODE_PRIVATE);
                        Collection<?> collection = prefs.getAll().values();

                        Log.d("pwAct pref ----", collection.toString());

                        String result = collection.toString();
                        Log.d("result ----", result);

                        result = result.substring(1);
                        result = result.substring(0, result.length() - 1);
                        Log.d("result substring ----", result);

//                            String result = prefs.getString("a", "0"); //키값, 디폴트값


                        RestAPI restAPI = builder.create(RestAPI.class);
                        Log.d("retrofit create ====", "yeah~");

                        String header = "JWT " + result;
                        Call<Void> call = restAPI.changeInfo(header, changePosition,changeGenderStr,changeAge);
                        Log.d("header ==", header);
                        Log.d("changeposition",String.valueOf(changePosition));
                        Log.d("changeGender",changeGenderStr);
                        Log.d("changeAge",String.valueOf(changeAge));

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.d("retrofit start ====", "yeah~");
                                Log.d("response code ===", String.valueOf(response.code()));
                                if (response.code() == 201) {
                                    new SweetAlertDialog(AccountManageActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setContentText("내 정보가 변경되었습니다.")
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

            }
        });


    }


    private void setView() {
        infoView = (LinearLayout) findViewById(R.id.infoView);
        changeInfoView = (LinearLayout) findViewById(R.id.changeInfoView);
        FButton changeBtn = (FButton) findViewById(R.id.changeButton);
        ageSpinner = (MaterialSpinner) findViewById(R.id.ageSpinner);
        okBtn = (FButton) findViewById(R.id.okButton);

        //spinner에 1~100 숫자 세팅(나이)

        ageSpinner.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100);

        //spinner에서 나이 선택된 값을 age에 넣기
        ageSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                age = position + 1;
            }
        });

        infoView.setVisibility(View.VISIBLE);
        changeInfoView.setVisibility(View.GONE);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoView.setVisibility(View.GONE);
                changeInfoView.setVisibility(View.VISIBLE);
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoView.setVisibility(View.VISIBLE);
                changeInfoView.setVisibility(View.GONE);
            }
        });
    }
}
