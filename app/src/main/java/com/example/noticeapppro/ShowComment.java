package com.example.noticeapppro;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowComment extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    List<Comment> list;
    String collection;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);

        recyclerView=findViewById(R.id.recyclerComment);

        firebaseFirestore= FirebaseFirestore.getInstance();
        list=new ArrayList<>();

        collection=getIntent().getExtras().getString("collection","null");
        id=getIntent().getExtras().getString("document","null");

        showComment();


    }

    public void showComment()
    {

        firebaseFirestore.collection(collection).document(id).collection("Comment").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();



                    for (DocumentSnapshot d : dsList)
                    {
                        Comment comment=d.toObject(Comment.class);
                        list.add(comment);
                    }

                    initializeRecyclerView();

                }
                else
                {
                    Toast.makeText(ShowComment.this, "No Comments Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void initializeRecyclerView()
    {
        ShowCommentAdapter showCommentAdapter = new ShowCommentAdapter(ShowComment.this,list);
        recyclerView.setAdapter(showCommentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowComment.this));
    }

}
