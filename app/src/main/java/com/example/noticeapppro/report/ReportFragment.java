package com.example.noticeapppro.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noticeapppro.R;
import com.example.noticeapppro.Report;
import com.example.noticeapppro.ReportAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ReportFragment extends Fragment {

    private ReportViewModel mViewModel;

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    List<Report> reportList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.report_fragment, container, false);

        recyclerView=root.findViewById(R.id.recyclerViewReport);
        firebaseFirestore= FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        reportList=new ArrayList<>();


        loadReport();


        return root;
    }

    public void loadReport()
    {

        firebaseFirestore.collection("Attendence Report").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();


                    for (DocumentSnapshot d : dsList)
                    {
                        Report report=d.toObject(Report.class);
                        reportList.add(report);
                    }
                    Collections.reverse(reportList);
                    initializeRecyclerView();


                }
                else
                {
                    Toast.makeText(getContext(), "No Attendence Report Available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

   public void initializeRecyclerView()
    {
        ReportAdapter recyclerAdapter = new ReportAdapter(getContext(),reportList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    }