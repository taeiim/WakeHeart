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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<WiseSayingItem> wiseSayingItems = new ArrayList<>();

    String result;
    JsonArray jsonAr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_helper_wisesaying, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);

        adapter = new RecyclerViewAdapter(getActivity(), wiseSayingItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(APIUrl.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestAPI restAPI = builder.create(RestAPI.class);
        Call<JsonObject> call = restAPI.wiseSaying();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("response----", response.body().toString());

                JsonObject jsonObject= response.body();
                try {
                    jsonAr=jsonObject.getAsJsonArray("phrase");
                }
                catch (JsonIOException e) {
                    e.printStackTrace();
                }

                Log.d("jsonArray-----",jsonAr.toString());

//                JsonArray jsonArray = response.body().getAsJsonArray();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("t",t.toString());
            }
        });


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
//        //1~10
//        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
//        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
//        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));
//        wiseSayingItems.add(new WiseSayingItem("굳은 결심은 가장 유용한 지식이다.","- 나폴레옹"));
//        wiseSayingItems.add(new WiseSayingItem("10분 뒤와 10년 후를 동시에 생각하라.","- 피터 드러커"));
//        wiseSayingItems.add(new WiseSayingItem("성공을 확신하는 것이 성공에의 첫걸음이다.","- 로버트 슐러"));
//        wiseSayingItems.add(new WiseSayingItem("사람은 스스로 운명을 만든다.","- 세네카"));
//        wiseSayingItems.add(new WiseSayingItem("일을 하는데 있어서 언제 시작할까 생각하는 것은 그만큼 떄를 늦추는 것이다.","- 칼라일"));
//        wiseSayingItems.add(new WiseSayingItem("실패란 보다 현명하게 다시 시작할 수 있는 기회다.","- 헨리 포드"));
//        wiseSayingItems.add(new WiseSayingItem("고뇌에 지는 것은 수치가 아니다. 쾌락에 지는 것이야말로 수치다.","- 파스칼"));
//
//        //11~20
//        wiseSayingItems.add(new WiseSayingItem("늦게 시작하는 것을 두려워 말고, 하다 중단하는 것을 두려워하라","- 중국속담"));
//        wiseSayingItems.add(new WiseSayingItem("사람에게 필요한 것은 행동이다.","- 브론테"));
//        wiseSayingItems.add(new WiseSayingItem("인간이 뜻을 세우는데 있어서 늦은 떄는 없다.","- 볼드윈"));
//        wiseSayingItems.add(new WiseSayingItem("고통을 주지 않는 것은 쾌락도 주지 않는다.","- 몽테뉴"));
//        wiseSayingItems.add(new WiseSayingItem("교육은 노후를 위한 최상의 양식이다.","- 아리스토텔레스"));
//        wiseSayingItems.add(new WiseSayingItem("천재는 1프로의 염감과 99프로의 노력으로 이루어진다.","- 에디슨"));
//        wiseSayingItems.add(new WiseSayingItem("배우려고 하는 학생은 부끄러워해서는 안 된다.","- 히레르"));
//        wiseSayingItems.add(new WiseSayingItem("가장 유능한 사람은 가장 배움에 힘쓰는 사람이다.","- 괴테"));
//        wiseSayingItems.add(new WiseSayingItem("노력하는 데 있어서 이득을 바라지 마라","- 도교"));
//        wiseSayingItems.add(new WiseSayingItem("배우고 때때로 익히면 또한 즐겁지 아니한가?","- 공자"));
//
//        //21~30
//        wiseSayingItems.add(new WiseSayingItem("같은 실수를 두려워하되 새로운 실수를 두려워하지 말라. 실수는 곧 경험이다.","- '어떤하루'중"));
//        wiseSayingItems.add(new WiseSayingItem("변명 중에서도 가장 어리석고 못난 변명은 '시간이 없어서'라는 변명이다.","- 에디슨"));
//        wiseSayingItems.add(new WiseSayingItem("실패란 성공이란 진로를 알려주는 나침반이다.","- 데니스 윌트리"));
//        wiseSayingItems.add(new WiseSayingItem("자기가 행복하다는 것을 알지 못하기 때문에 불행한 것","- 도스토예프스키"));
//        wiseSayingItems.add(new WiseSayingItem("지금까지 어떤 좋은 기회가 없었던 사람은 하나도 없다.","- 모옴"));
//        wiseSayingItems.add(new WiseSayingItem("세상의 어떤 것도 그대의 정직과 성실만큼 그대를 돕는 것은 없다.","- 벤자민 프랭클린"));
//        wiseSayingItems.add(new WiseSayingItem("내일 무엇을 해야 할지 모르는 사람은 불행하다.","- 고리키"));
//        wiseSayingItems.add(new WiseSayingItem("시간은 목숨이고 우리는 그 목숨을 쓰면서 살아간다.","- 미상"));
//        wiseSayingItems.add(new WiseSayingItem("짧은 인생은 시간의 낭비에 의해 더욱 짧아진다.","- 존슨"));
//        wiseSayingItems.add(new WiseSayingItem("나는 생각한다. 그러므로 존재한다.","- 르네 데카르트"));

    }

}
