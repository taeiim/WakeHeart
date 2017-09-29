package com.dsm.wakeheart.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dsm.wakeheart.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import info.hoang8f.widget.FButton;

/**
 * Created by parktaeim on 2017. 9. 8..
 */

public class AccountManageActivity extends AppCompatActivity {
    ImageView back_icon;
    private LinearLayout infoView;
    private LinearLayout changeInfoView;
    private MaterialSpinner ageSpinner;
    private int age;

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



    }



    private void setView() {
        infoView = (LinearLayout) findViewById(R.id.infoView);
        changeInfoView = (LinearLayout) findViewById(R.id.changeInfoView);
        FButton changeBtn = (FButton) findViewById(R.id.changeButton);
        FButton okBtn = (FButton) findViewById(R.id.okButton);
        ageSpinner = (MaterialSpinner) findViewById(R.id.ageSpinner);

        //spinner에 1~100 숫자 세팅(나이)

        ageSpinner.setItems(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,
        51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100);

        //spinner에서 나이 선택된 값을 age에 넣기
        ageSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                age = position+1;
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
