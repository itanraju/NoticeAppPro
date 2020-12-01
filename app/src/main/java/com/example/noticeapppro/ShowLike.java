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

public class ShowLike extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore;
    List<Like> list;
    RecyclerView recyclerView;
    String collection,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_like);

        recyclerView=findViewById(R.id.recycl);

        firebaseFirestore= FirebaseFirestore.getInstance();
        list=new ArrayList<>();

        collection=getIntent().getExtras().getString("collection","null");
        id=getIntent().getExtras().getString("document","null");

        loadShowLike();
    }

    public void loadShowLike()
    {
        firebaseFirestore.collection(collection).document(id).collection("Like").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : dsList)
                    {
                        Like like=d.toObject(Like.class);
                        list.add(like);
                    }
                    initializeRecyclerView();


                }
                else
                {
                    Toast.makeText(ShowLike.this, "No Likes Available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void initializeRecyclerView()
    {
       ShowLikeAdapter showLikeAdapter = new ShowLikeAdapter(ShowLike.this,list);
        recyclerView.setAdapter(showLikeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowLike.this));
    }
}
