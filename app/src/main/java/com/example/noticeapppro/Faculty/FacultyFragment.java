package com.example.noticeapppro.Faculty;

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

import com.example.noticeapppro.FacultyDetail;
import com.example.noticeapppro.FacultyDetailAdapter;
import com.example.noticeapppro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {

    private FacultyViewModel mViewModel;

    RecyclerView recyclerView;
    List<FacultyDetail> list;

    FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.faculty_fragment, container, false);

        firebaseFirestore= FirebaseFirestore.getInstance();

        recyclerView=root.findViewById(R.id.recyclerView);
        list=new ArrayList<>();


    /* list.add(new FacultyDetail("Ms. Nipa Mandaliya","Assistant Professor","Experience : 7 Years MCA","Andrioid, Data and File Structure, Java"));
        list.add(new FacultyDetail("Mr. Sagar Vala","Assistant Professor","Experience : 7 Years MCA",".Net Technology, Java, I.T.Projrct Mgmt"));
        list.add(new FacultyDetail("Mr. Kevin Parekh","Assistant Professor","Experience : 3 Years I.T.Engineer","Web Designing and PHP, Business Communication, English Language"));
        list.add(new FacultyDetail("Mr. Jagan Pandya","Assistant Professor","Experience : 7 Years MCA","Desktop Publishing, Web Disgning, Java"));
        list.add(new FacultyDetail("Mr. Bhavesh Hariyani","Assistant Professor","Experience : 6 Years MCA","PHP, Web Designing and Development, Statistics"));
        list.add(new FacultyDetail("Mr. Arpit Parekh","Assistant Professor","Experience : 9 Years Msc.IT","SAD,OOAD, Scripting Languages"));
        list.add(new FacultyDetail("Mr. Vipul Tejani","Assistant Professor","Experience : 6 Years MCA","Java, Php, Oracle"));
        list.add(new FacultyDetail("Mr. Nirav Dave","Assistant Professor","Experience : 7 Years MCA","Android, Data and File Structure, Java"));
        list.add(new FacultyDetail("Mr. Gaurang Bhatt","Assistant Professor","Experience : 15 Years MCA","Java Technology, DBMS, Android"));
        list.add(new FacultyDetail("Mr. Nirav Shah","Assistant Professor","Experience : 9 Years MCA","Asp.net, Hardwere & Networking, Databse"));
        list.add(new FacultyDetail("Mr. Parag Makawana","Assistant Professor","Experience : 10 Years MCA","Web Designing, Database, .Net Technology"));
        list.add(new FacultyDetail("Mr. Parthik Patel","Assistant Professor","Experience : 11 Years MCA","C Language, .Net Technology, Java"));
        list.add(new FacultyDetail("Mr. Madhav Dave","Assistant Professor","Experience : 10 Years MCA","c++, oop Concepts, Animation"));
        list.add(new FacultyDetail("Mr. Tejas Pandya","Assistant Professor","Experience : 11 Years MCA","Computer Organization, Web Designing, Java Technology"));
        list.add(new FacultyDetail("Mr. Bhavesh Dhandhukiya","Assistant Professor","Experience : 12 Years B.E.(IT)","Computer Graphics, Operating System, Networking, Linux"));
        list.add(new FacultyDetail("Dr. Kalpesh Gundigara","Assistant Professor","Experience : 23 Years Ph.D.MCA","C, Database Management, Data Warehousing, Data Mining"));



    for(int i=0;i<list.size();i++)
        {
            final int j=i;
            FacultyDetail facultyDetail=new FacultyDetail(list.get(i).getName(),list.get(i).getPost(),list.get(i).getExperience(),list.get(i).getSkills());
            firebaseFirestore.collection("Staff").add(facultyDetail).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getContext(), list.get(j).getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


     */


        firebaseFirestore.collection("Staff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : dsList) {
                        FacultyDetail facultyDetail=d.toObject(FacultyDetail.class);
                        list.add(facultyDetail);
                    }
                    initializeRecyclerView();
                }
                else {
                    Toast.makeText(getContext(), "No Details Available", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }

    public void initializeRecyclerView()
    {
        FacultyDetailAdapter facultyDetailAdapter = new FacultyDetailAdapter(getContext(),list);
        recyclerView.setAdapter(facultyDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
