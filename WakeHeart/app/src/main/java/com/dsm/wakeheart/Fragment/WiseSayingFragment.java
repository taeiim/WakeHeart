package com.dsm.wakeheart.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.dsm.wakeheart.Adapter.RecyclerViewAdapter;
import com.dsm.wakeheart.Model.WiseSayingItem;
import com.dsm.wakeheart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parktaeim on 2017. 9. 5..
 */

public class WiseSayingFragment extends Fragment {

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
//        ArrayList<WiseSayingItem> wiseSayingItems = new ArrayList<>();
        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));
        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));
        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));
        wiseSayingItems.add(new WiseSayingItem("인간은 스스로 믿는대로 된다","- 안톤 체호프"));
        wiseSayingItems.add(new WiseSayingItem("그저 살려고 태어난 게 아니다 의미 있는 인생을 만들려고 태어난 것이다","- 헬리스 브릿지스"));
        wiseSayingItems.add(new WiseSayingItem("오늘 당신은 평생의 목표에 도달하는 데 도움이 되는 무슨 일을 하였는가?","- 브라이언 트레이시"));



    }
}
