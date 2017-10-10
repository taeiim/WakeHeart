package com.dsm.wakeheart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsm.wakeheart.Model.RestAreaItem;
import com.dsm.wakeheart.R;

import java.util.ArrayList;

/**
 * Created by parktaeim on 2017. 9. 26..
 */

public class RestAreaRecyclerViewAdapter extends RecyclerView.Adapter<RestAreaRecyclerViewAdapter.ViewHolder> {
    Context context;
    private ArrayList<RestAreaItem> arrayList = new ArrayList<>();

    public RestAreaRecyclerViewAdapter(Context context, ArrayList<RestAreaItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public RestAreaRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restarea_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.restAreaName.setText(arrayList.get(position).getRestAreaName());
        holder.routeName.setText(arrayList.get(position).getRouteName());
        holder.distance.setText(arrayList.get(position).getDistance());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView restAreaName ;
        TextView routeName;
        TextView distance;

        public ViewHolder(View itemView) {
            super(itemView);
            restAreaName = (TextView) itemView.findViewById(R.id.restAreaNameTextView);
            routeName = (TextView) itemView.findViewById(R.id.routeNameTextView);
            distance = (TextView) itemView.findViewById(R.id.distanceTextView);
        }
    }
}
