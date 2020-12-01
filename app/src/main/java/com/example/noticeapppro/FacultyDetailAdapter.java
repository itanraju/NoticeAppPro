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

public class FacultyDetailAdapter extends RecyclerView.Adapter<FacultyDetailAdapter.ViewHolder> {

    Context mContext;
    List<FacultyDetail> list;

    public FacultyDetailAdapter(Context mContext, List<FacultyDetail> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fac_detail_recycler_layout,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.post.setText(list.get(position).getPost());
        holder.experience.setText(list.get(position).getExperience());
        holder.skills.setText(list.get(position).getSkills());

        Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.recycler_show);
        holder.eee.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,post,experience,skills;
        RelativeLayout eee;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            post=itemView.findViewById(R.id.post);
            skills=itemView.findViewById(R.id.skills);
            eee=itemView.findViewById(R.id.eee);
            experience= itemView.findViewById(R.id.experience);

        }
    }


}
