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

public class ShowCommentAdapter extends RecyclerView.Adapter<ShowCommentAdapter.ViewHolder> {

    Context mContext;
    List<Comment> list;

    public ShowCommentAdapter(Context mContext, List<Comment> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.showcomment_recycler_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mName.setText(list.get(position).getName());
        holder.mCommentText.setText(list.get(position).getComment());

        Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.recycler_show);
        holder.sss.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mName;
        TextView mCommentText;
        RelativeLayout sss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            mName=itemView.findViewById(R.id.showCommentName);
            mCommentText=itemView.findViewById(R.id.showCommentText);
            sss=itemView.findViewById(R.id.sss);

        }
    }


}
