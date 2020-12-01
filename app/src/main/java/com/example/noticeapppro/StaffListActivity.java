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

public class StaffListActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    List<Staff> staffList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);

        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.recyclerView);

        staffList=new ArrayList<>();

        firebaseFirestore.collection("Staff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : dsList) {

                        Staff staff=d.toObject(Staff.class);
                        staffList.add(staff);
                        Toast.makeText(StaffListActivity.this, "List loaded", Toast.LENGTH_SHORT).show();
                    }
                    initializeRecyclerView();
                }
                else {
                    Toast.makeText(StaffListActivity.this, "No Details Available", Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    public void initializeRecyclerView()
    {
        StaffListAdapter staffListAdapter = new StaffListAdapter(StaffListActivity.this,staffList);
        recyclerView.setAdapter(staffListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StaffListActivity.this));
    }
}
