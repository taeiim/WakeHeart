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
import android.widget.Toast;

import com.dsm.wakeheart.Activity.SignUpActivity;
import com.dsm.wakeheart.Activity.SignUpSuccessActivity;
import com.dsm.wakeheart.Adapter.RecyclerViewAdapter;
import com.dsm.wakeheart.Model.WiseSayingItem;
import com.dsm.wakeheart.R;
import com.dsm.wakeheart.RestAPI;
import com.dsm.wakeheart.Server.resource.APIUrl;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
    private ArrayList<WiseSayingItem> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_helper_wisesaying,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);

        adapter = new RecyclerViewAdapter(getActivity(),arrayList);
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

        retrofit = new Retrofit.Builder().baseUrl(APIUrl.API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        restAPI = retrofit.create(RestAPI.class);
//
//        Call<JsonObject> call = restAPI.wiseSaying();
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
////                Log.d("json-------------------",response.body().getAsJsonArray("pharse").toString());
//                JsonArray jsonObject =response.body().getAsJsonArray("phrase");
//                JsonArray jsonElements = jsonObject.getAsJsonArray();
//                arrayList = getArrayList(jsonElements);
////                adapter = new RecyclerViewAdapter(getActivity(),arrayList);
////                recyclerView.setAdapter(adapter);
//
//                Log.d(jsonObject.toString(), "jsonArrayGet---------");
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });
//



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

    private ArrayList<WiseSayingItem> getArrayList(JsonArray jsonElements) {
        ArrayList<WiseSayingItem> arrayList = new ArrayList<>();
        for(int i=0;i<jsonElements.size();i++){
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);
            String wiseSaying = jsonObject.getAsJsonPrimitive("wiseSaying").getAsString();
            String author = jsonObject.getAsJsonPrimitive("author").getAsString();

            arrayList.add(new WiseSayingItem(wiseSaying,author));
        }
        return arrayList;
    }


}
