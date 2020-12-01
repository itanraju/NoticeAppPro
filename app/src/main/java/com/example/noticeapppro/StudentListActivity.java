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

public class StudentListActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<Student> studentList;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        studentList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        firebaseFirestore= FirebaseFirestore.getInstance();



        studentList=new ArrayList<>();

        firebaseFirestore.collection("Student").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : dsList) {

                        Student student=d.toObject(Student.class);
                        studentList.add(student);
                        Toast.makeText(StudentListActivity.this, "List loaded", Toast.LENGTH_SHORT).show();
                    }
                    initializeRecyclerView();
                }
                else {
                    Toast.makeText(StudentListActivity.this, "No Details Available", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }

    public void initializeRecyclerView()
    {
        StudentListAdapter studentListAdapter = new StudentListAdapter(studentList, StudentListActivity.this);
        recyclerView.setAdapter(studentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentListActivity.this));
    }
}
