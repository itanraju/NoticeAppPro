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

public class MaterialListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    List<Material> materialList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);

        recyclerView = findViewById(R.id.recyclerView);
        firebaseFirestore= FirebaseFirestore.getInstance();
        materialList=new ArrayList<>();

        firebaseFirestore.collection("Study Material").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : dsList) {
                        Material material=d.toObject(Material.class);
                        materialList.add(material);
                    }
                    Collections.reverse(materialList);
                    initializeRecyclerView();
                }
                else {

                    Toast.makeText(MaterialListActivity.this, "No Notice Available", Toast.LENGTH_SHORT).show(); }

            }
        });


    }

    public void initializeRecyclerView()
    {
        MaterialAdapter recyclerAdapter = new MaterialAdapter(MaterialListActivity.this,materialList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MaterialListActivity.this));
    }
}
