package com.example.noticeapppro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    Context mContext;
    List<Student> list;
    FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

    public StudentListAdapter(List<Student> list , Context mContext) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.studentName.setText(list.get(position).getName());
        holder.studentEmail.setText(list.get(position).getEmail());
        holder.studentNumber.setText(list.get(position).getEnrollmentNo());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Student").document(list.get(position).getStudentId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            firebaseAuth.signInWithEmailAndPassword(list.get(position).getEmail(),list.get(position).getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

                                    firebaseUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(mContext, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(mContext, "Failed to Delete", Toast.LENGTH_SHORT).show();
                                            firebaseAuth.signOut();
                                        }
                                    });


                                }
                            });

                            Toast.makeText(mContext, "User Deleted Succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(mContext, "Failed To Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to Delete", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView studentName,studentEmail,studentNumber;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName=itemView.findViewById(R.id.studentName);
            studentEmail=itemView.findViewById(R.id.studentEmail);
            studentNumber=itemView.findViewById(R.id.studentNumber);
            imgDelete=itemView.findViewById(R.id.imgDelete);

        }
    }

}
