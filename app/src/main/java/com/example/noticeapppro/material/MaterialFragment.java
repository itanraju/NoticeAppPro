package com.example.noticeapppro.material;

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

import com.example.noticeapppro.Material;
import com.example.noticeapppro.MaterialAdapter;
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

public class MaterialFragment extends Fragment {

    private MaterialViewModel mViewModel;

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    List<Material> materialList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.material_fragment, container, false);

        recyclerView=root.findViewById(R.id.recyclerViewMaterial);
        firebaseFirestore= FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        materialList=new ArrayList<>();



        loadMaterial();
        return root;

    }


    public void loadMaterial()
    {
        firebaseFirestore.collection("Study Material").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : dsList)
                    {
                        Material material=d.toObject(Material.class);
                        materialList.add(material);
                    }
                    Collections.reverse(materialList);
                    initializeRecyclerView();

                }
                else
                {
                    Toast.makeText(getContext(), "No Study Material Available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void initializeRecyclerView()
    {
        MaterialAdapter recyclerAdapter = new MaterialAdapter(getContext(),materialList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }



}
