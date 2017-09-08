package com.dsm.wakeheart.Activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dsm.wakeheart.Adapter.SettingsRecyclerViewAdapter;
import com.dsm.wakeheart.Model.SettingsItem;
import com.dsm.wakeheart.R;

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

        back_icon = (ImageView) findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void setUpRecyclerView() {
        context = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.settingsRecyclerView);
        recyclerView.setHasFixedSize(true);

        ArrayList items = new ArrayList<>();
        items.add(new SettingsItem(R.drawable.person_icon,"계정관리"));
        items.add(new SettingsItem(R.drawable.music_icon,"음악설정"));
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new SettingsRecyclerViewAdapter(items,context);
        recyclerView.setAdapter(adapter);
    }
}
