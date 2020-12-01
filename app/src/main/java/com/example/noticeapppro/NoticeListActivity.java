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
import java.util.Collections;
import java.util.List;

public class NoticeListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    List<Notice> noticeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        recyclerView = findViewById(R.id.recyclerView);
        firebaseFirestore= FirebaseFirestore.getInstance();
        noticeList=new ArrayList<>();


        firebaseFirestore.collection("Notice").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : dsList) {
                        Notice notice=d.toObject(Notice.class);
                        noticeList.add(notice);
                    }
                    Collections.reverse(noticeList);
                    initializeRecyclerView();
                }
                else {

                    Toast.makeText(NoticeListActivity.this, "No Notice Available", Toast.LENGTH_SHORT).show(); }

            }
        });





    }

    public void initializeRecyclerView()
    {
        NoticeAdapter recyclerAdapter = new NoticeAdapter(NoticeListActivity.this,noticeList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoticeListActivity.this));
    }



}
