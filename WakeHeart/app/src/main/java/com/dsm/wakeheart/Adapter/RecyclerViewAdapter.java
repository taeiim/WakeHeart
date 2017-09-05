package com.dsm.wakeheart.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsm.wakeheart.Model.WiseSayingItem;
import com.dsm.wakeheart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parktaeim on 2017. 9. 5..
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<WiseSayingItem> items = new ArrayList<>();

    public RecyclerViewAdapter(Context context,ArrayList<WiseSayingItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wisesaying_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.wiseSayingTextView.setText(items.get(position).wiseSaying);
        holder.authorTextView.setText(items.get(position).author);
    }

    @Override
    public int getItemCount() {
       return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView wiseSayingTextView;
        TextView authorTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            wiseSayingTextView = (TextView) itemView.findViewById(R.id.wiseSayingTextView);
            authorTextView = (TextView) itemView.findViewById(R.id.authorTextView);
        }
    }
}
