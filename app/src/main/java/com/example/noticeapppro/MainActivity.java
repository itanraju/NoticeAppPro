package com.example.noticeapppro;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static String detect;
    FirebaseAuth firebaseAuth;
    int i;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> staffList;
    FloatingActionButton fab;
    String userNameText;
    String userEmailText;
    RelativeLayout navHeader;
    TextView navUsername,navUserEmail;
    int j;
    public static boolean isAdmin=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        fab = findViewById(R.id.fab);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        staffList=new ArrayList<>();



        fab.setEnabled(false);
        fab.setVisibility(View.INVISIBLE);


        if(isAdmin){}
        else {
            loadStaff();
        }


        if(isAdmin)
        {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
        }
        else {


            firebaseFirestore.collection("Staff").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userNameText = documentSnapshot.getString("name");
                    userEmailText = documentSnapshot.getString("email");
                    navUsername.setText(userNameText);
                    navUserEmail.setText(userEmailText);


                    if (userNameText == null && userEmailText == null) {
                        firebaseFirestore.collection("Student").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                userNameText = documentSnapshot.getString("name");
                                userEmailText = documentSnapshot.getString("email");

                                navUsername.setText(userNameText);
                                navUserEmail.setText(userEmailText);


                            }
                        });
                    }

                }
            });

        }




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAdmin)
                {

                    Intent intent=new Intent(MainActivity.this,AdminPanel.class);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(MainActivity.this);

                }
                else {
                    Intent intent = new Intent(MainActivity.this, UploadPost.class);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(MainActivity.this);
                }
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.fac_detail)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.userName);
        navUserEmail =(TextView) headerView.findViewById(R.id.userEmail);
        navHeader=(RelativeLayout)headerView.findViewById(R.id.navheader);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.log_out)
        {
            firebaseAuth.signOut();
            Intent intent=new Intent(MainActivity.this,StudentLogin.class);
            startActivity(intent);
            Animatoo.animateZoom(MainActivity.this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





    public int loadStaff()
    {
        firebaseFirestore.collection("Staff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();


                    for (DocumentSnapshot d : dsList) {
                        staffList.add(d.toObject(Staff.class).getEmail());


                        if(staffList.contains(firebaseAuth.getCurrentUser().getEmail()))
                        {
                            fab.setEnabled(true);
                            fab.setVisibility(View.VISIBLE);
                            Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                            fab.startAnimation(animation);
                            i=1;
                        }
                        else
                        {
                            i=0;


                        }
                    }


                } else {


                }
            }
        });

        if(i==1)
        {
            return 1;
        }
        else
        {
            return 0;
        }

    }
}
