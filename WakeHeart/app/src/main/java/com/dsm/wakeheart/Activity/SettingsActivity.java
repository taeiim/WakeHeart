package com.dsm.wakeheart.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dsm.wakeheart.Adapter.SettingsRecyclerViewAdapter;
import com.dsm.wakeheart.Model.SettingsItem;
import com.dsm.wakeheart.R;
import com.dsm.wakeheart.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by parktaeim on 2017. 9. 8..
 */


public class SettingsActivity extends AppCompatActivity{

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ImageView back_icon ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setUpRecyclerView();

        //뒤로가기
        back_icon = (ImageView) findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position==0){  //내 정보
                    Intent intent0 = new Intent(SettingsActivity.this,AccountManageActivity.class);
                    startActivity(intent0);
                }else if(position == 1){  // 비밀번호 변경
                    Intent intent1 = new Intent(SettingsActivity.this,ChangePasswordActivity.class);
                    startActivity(intent1);
                }else if(position == 2){  // 음악 설정
                    Intent intent2 = new Intent(SettingsActivity.this,MusicManageActivity.class);
                    startActivity(intent2);
                }else if(position == 3){  // 로그아웃
                    new AlertDialog.Builder(SettingsActivity.this)
                            .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent i = new Intent(SettingsActivity.this, LoginActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            })
                            .show();


                }
            }

        }));

    }

    //recyclerView Setting
    private void setUpRecyclerView() {
        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.settingsRecyclerView);
        recyclerView.setHasFixedSize(true);

        ArrayList items = new ArrayList<>();
        //Setting item in RecyclerView
        items.add(new SettingsItem(R.drawable.person_icon,"내 정보"));
        items.add(new SettingsItem(R.drawable.password_icon,"비밀번호 변경"));
        items.add(new SettingsItem(R.drawable.music_icon,"음악설정"));
        items.add(new SettingsItem(R.drawable.out_icon ,"로그아웃"));
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new SettingsRecyclerViewAdapter(items,context);
        recyclerView.setAdapter(adapter);

        //구분선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
