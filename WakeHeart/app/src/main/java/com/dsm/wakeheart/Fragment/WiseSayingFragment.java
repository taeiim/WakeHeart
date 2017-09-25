package com.dsm.wakeheart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.dsm.wakeheart.Activity.SignUpActivity;
import com.dsm.wakeheart.Activity.SignUpSuccessActivity;
import com.dsm.wakeheart.Adapter.RecyclerViewAdapter;
import com.dsm.wakeheart.Model.WiseSayingItem;
import com.dsm.wakeheart.R;
import com.dsm.wakeheart.RestAPI;
import com.dsm.wakeheart.Server.resource.APIUrl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 9. 5..
 */

public class WiseSayingFragment extends Fragment {

    Retrofit retrofit;
    RestAPI restAPI;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<WiseSayingItem> wiseSayingItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_helper_wisesaying,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);

        adapter = new RecyclerViewAdapter(getActivity(),wiseSayingItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addData();
    }

    private void addData() {

//        Retrofit builder = new Retrofit.Builder()
//                .baseUrl(APIUrl.API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RestAPI restAPI = builder.create(RestAPI.class);
//        Call<Boolean> call = restAPI.signUp(id,pw,gender,age);
//
//        Intent intent = new Intent(SignUpActivity.this,SignUpSuccessActivity.class);
//        startActivity(intent);
//        finish();
//
//        call.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
//            }
//        });



//        Retrofit builder = new Retrofit.Builder()
//                .baseUrl(APIUrl.WISE_SAYING_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RestAPI restAPI = builder.create(RestAPI.class);
//        Call<Void> call = restAPI.wiseSaying()


        retrofit = new Retrofit.Builder().baseUrl(APIUrl.API_BASE_URL).build();
        restAPI = retrofit.create(RestAPI.class);

        Call<List<WiseSayingItem>> call = restAPI.wiseSaying();

        call.enqueue(new Callback<List<WiseSayingItem>>() {
            @Override
            public void onResponse(Call<List<WiseSayingItem>> call, Response<List<WiseSayingItem>> response) {
                List<WiseSayingItem> wiseSaying = response.body();

                Log.i("명언 : ",response.body().toString());
            }

            @Override
            public void onFailure(Call<List<WiseSayingItem>> call, Throwable t) {

            }
        });

//        ArrayList<WiseSayingItem> wiseSayingItems = new ArrayList<>();
//        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
//        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
//        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));
//        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
//        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
//        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));
//        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
//        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
//        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));
//        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
//        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
//        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));
//


    }
}
