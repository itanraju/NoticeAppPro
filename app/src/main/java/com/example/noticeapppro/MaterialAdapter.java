package com.example.noticeapppro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.Viewholder> {

    Context mContext;
    List<Material> list;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    String name;
    String id;
    String mId;
    String mPostId;

    public MaterialAdapter(Context mContext, List<Material> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.material_recycler_layout,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {



        holder.mTitle.setText("Title : "+list.get(position).getMaterialTitle());
        holder.mDate.setText(list.get(position).getMaterialDate());
        holder.mTime.setText(list.get(position).getMaterialTime());
        holder.mByName.setText("Posted By : "+list.get(position).getMaterialBy());


        Animation animation= AnimationUtils.loadAnimation(mContext,R.anim.recycler);
        holder.parentLayout.startAnimation(animation);

        holder.imgDelete.setEnabled(false);
        holder.imgDelete.setVisibility(View.INVISIBLE);

        if(MainActivity.isAdmin)
        {
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.imgDelete.setEnabled(true);
        }

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Study Material").document(list.get(position).getMaterialId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(mContext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(mContext, "Failed to Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1= AnimationUtils.loadAnimation(mContext,R.anim.button);
                holder.download.startAnimation(animation1);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getMaterialUrl()));
                mContext.startActivity(browserIntent);
            }
        });


        holder.mShowComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostId="Study Material";
                mId=list.get(position).getMaterialId();
                Intent intent=new Intent(mContext,ShowComment.class);
                intent.putExtra("collection",mPostId);
                intent.putExtra("document",mId);
                mContext.startActivity(intent);
                Animatoo.animateSlideLeft(mContext);
            }
        });



        holder.mShowLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPostId="Study Material";
                mId=list.get(position).getMaterialId();
                Intent intent=new Intent(mContext,ShowLike.class);
                intent.putExtra("collection",mPostId);
                intent.putExtra("document",mId);
                mContext.startActivity(intent);
                Animatoo.animateSlideLeft(mContext);
            }
        });


        holder.mAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1= AnimationUtils.loadAnimation(mContext,R.anim.button);
                holder.mAddComment.startAnimation(animation1);
                if (!holder.mComment.getText().toString().isEmpty())
                {
                    firebaseFirestore= FirebaseFirestore.getInstance();
                    firebaseAuth= FirebaseAuth.getInstance();

                    id=firebaseAuth.getCurrentUser().getUid();
                    SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("MyPref", 0);


                    firebaseFirestore.collection(pref.getString("user","Student")).document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            name=documentSnapshot.getString("name");


                            final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                            String commentText=holder.mComment.getText().toString();

                           Comment comment=new Comment(id,name,commentText,currentDate,currentTime);


                            firebaseFirestore.collection("Study Material").document(list.get(position).getMaterialId())
                                    .collection("Comment").
                                    document(id).set(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(mContext, "Comment Posted", Toast.LENGTH_SHORT).show();
                                    holder.mComment.setText("");

                                }
                            });
                        }
                    });
                }
                else
                {
                    Toast.makeText(mContext, "Please write a comment", Toast.LENGTH_SHORT).show();
                }
            }
        });




        holder.mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation1= AnimationUtils.loadAnimation(mContext,R.anim.button);
                holder.mLike.startAnimation(animation1);

                firebaseFirestore= FirebaseFirestore.getInstance();
                firebaseAuth= FirebaseAuth.getInstance();

                id=firebaseAuth.getCurrentUser().getUid();

                SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("MyPref", 0);


                firebaseFirestore.collection(pref.getString("user","Student")).document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        name=documentSnapshot.getString("name");


                        final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        Like like=new Like(id,name,currentDate,currentTime);


                        firebaseFirestore.collection("Study Material").document(list.get(position).getMaterialId())
                                .collection("Like").
                                document(id).set(like).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(mContext, "Liked", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

            }
        });






    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder
    {

        TextView mDate,mTime,mTitle;
        RelativeLayout parentLayout;
        ImageView mLike;
        EditText mComment;
        ImageView mAddComment;
        TextView mShowLike;
        TextView mShowComment;
        TextView mByName;
        ImageView download;
        ImageView imgDelete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            mDate=itemView.findViewById(R.id.mDate);
            download=itemView.findViewById(R.id.download);
            mTime=itemView.findViewById(R.id.mTime);
            parentLayout=itemView.findViewById(R.id.layout);
            mTitle=itemView.findViewById(R.id.mMaterialTitle);
            mLike=itemView.findViewById(R.id.btnLike);
            mComment=itemView.findViewById(R.id.noticeComment);
            mAddComment=itemView.findViewById(R.id.btnAddComment);
            mShowLike=itemView.findViewById(R.id.likeText);
            mShowComment=itemView.findViewById(R.id.commentText);
            mByName=itemView.findViewById(R.id.byName);

            imgDelete=itemView.findViewById(R.id.imgDelete);



        }
    }
}
