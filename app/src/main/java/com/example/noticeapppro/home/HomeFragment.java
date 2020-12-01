package com.example.noticeapppro.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noticeapppro.Notice;
import com.example.noticeapppro.NoticeAdapter;
import com.example.noticeapppro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    RecyclerView recyclerViewNotice;

    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    List<Notice> noticeList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        noticeList=new ArrayList<>();
        recyclerViewNotice=root.findViewById(R.id.recyclerViewNotice);
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseFirestore= FirebaseFirestore.getInstance();
        loadNotice();
        return root;
    }

    public void loadNotice()
    {
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
                    Toast.makeText(getContext(), "No Notice Available", Toast.LENGTH_SHORT).show(); }
            }}); }

    public void initializeRecyclerView()
    {
        NoticeAdapter recyclerAdapter = new NoticeAdapter(getContext(),noticeList);
        recyclerViewNotice.setAdapter(recyclerAdapter);
        recyclerViewNotice.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}