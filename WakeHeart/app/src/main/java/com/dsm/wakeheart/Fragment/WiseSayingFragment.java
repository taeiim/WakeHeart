package com.dsm.wakeheart.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsm.wakeheart.R;

/**
 * Created by parktaeim on 2017. 9. 5..
 */

public class WiseSayingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_helper_wisesaying,container,false);
        return rootView;
    }
}
