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

public class ReportListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    List<Report> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);


        recyclerView = findViewById(R.id.recyclerView);
        firebaseFirestore= FirebaseFirestore.getInstance();
        reportList=new ArrayList<>();


        firebaseFirestore.collection("Attendence Report").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : dsList) {
                        Report report=d.toObject(Report.class);
                        reportList.add(report);
                    }
                    Collections.reverse(reportList);
                    initializeRecyclerView();
                }
                else {

                    Toast.makeText(ReportListActivity.this, "No Notice Available", Toast.LENGTH_SHORT).show(); }

            }
        });


    }

    public void initializeRecyclerView()
    {
        ReportAdapter recyclerAdapter = new ReportAdapter(ReportListActivity.this,reportList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReportListActivity.this));
    }
}
