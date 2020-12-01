package com.example.noticeapppro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowLikeAdapter extends RecyclerView.Adapter<ShowLikeAdapter.ViewHolder>{


    Context mContext;
    List<Like> list;

    public ShowLikeAdapter(Context mContext, List<Like> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.showlike_recycler_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mName.setText(list.get(position).getName());

        Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.recycler_show);
        holder.ss.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mName;
        RelativeLayout ss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mName=itemView.findViewById(R.id.showLikeName);
            ss=itemView.findViewById(R.id.ss);


        }
    }
}
