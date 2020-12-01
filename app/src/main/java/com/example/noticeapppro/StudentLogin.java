package com.example.noticeapppro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentLogin extends AppCompatActivity {


    EditText edtEmail, edtPass;
    Button btnLogIn;
    TextView btnRegister,btnRegisterStaff,forgotPass;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> staffList;
    public static String user;
    int i;
    ProgressDialog progressDialog;
    RelativeLayout qq;

    String adminEmail,adminpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegisterStaff = findViewById(R.id.btnRegisterStaff);
        qq=findViewById(R.id.qq);
        forgotPass=findViewById(R.id.forgotPass);

        staffList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Animation animation= AnimationUtils.loadAnimation(StudentLogin.this,R.anim.fade);
        Animation animation1= AnimationUtils.loadAnimation(StudentLogin.this,R.anim.scale_fade);
        edtEmail.startAnimation(animation);
        edtPass.startAnimation(animation);
        btnLogIn.startAnimation(animation);
        btnRegisterStaff.startAnimation(animation);
        btnRegister.startAnimation(animation);
        qq.startAnimation(animation1);
        forgotPass.startAnimation(animation);

        progressDialog=new ProgressDialog(StudentLogin.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Logging In");
        progressDialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);

        loadStaff();
        getAdminEmail();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = edtEmail.getText().toString();
                final String pass = edtPass.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()) {

                    progressDialog.show();

                    if(email.equals(adminEmail) && pass.equals(adminpassword))
                    {
                        firebaseAuth.signInWithEmailAndPassword(adminEmail,adminpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()) {
                                    MainActivity.isAdmin=true;
                                    editor.putString("user", "Admin");
                                    editor.commit();
                                    Intent intent = new Intent(StudentLogin.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(StudentLogin.this, "Failed to Log In", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {

                                    if (staffList.contains(email) && firebaseAuth.getCurrentUser().isEmailVerified()) {
                                        editor.putString("user", "Staff");
                                        editor.commit();
                                        progressDialog.dismiss();
                                        Toast.makeText(StudentLogin.this, "Log In Succesfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(StudentLogin.this, MainActivity.class);
                                        startActivity(intent);
                                        Animatoo.animateZoom(StudentLogin.this);
                                        finish();
                                    } else if (!staffList.contains(email) && firebaseAuth.getCurrentUser().isEmailVerified()) {
                                        editor.putString("user", "Student");
                                        editor.commit();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(StudentLogin.this, MainActivity.class);
                                        startActivity(intent);
                                        Animatoo.animateZoom(StudentLogin.this);
                                        finish();
                                    }



                                } else {

                                    firebaseAuth.signOut();
                                    progressDialog.dismiss();
                                    Toast.makeText(StudentLogin.this, "Verify Your Email", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(StudentLogin.this, "Failed To Log In", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                else
                {
                    Toast.makeText(StudentLogin.this, "Fill All The Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentLogin.this,ForgotPassword.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(StudentLogin.this);
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StudentLogin.this, StudentRegister.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(StudentLogin.this);

            }
        });

        btnRegisterStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=edtPass.getText().toString();

                if(key.equals("staff@123")) {

                    Intent intent = new Intent(StudentLogin.this,StaffRegister.class);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(StudentLogin.this);
                }
                else
                {
                    Toast.makeText(StudentLogin.this, "You are not Staff Member", Toast.LENGTH_SHORT).show();
                }

            }
        });


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

                    }
                    i = 1;

                } else {
                    i = 0;
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

    public String getAdminEmail()
    {
        firebaseFirestore.collection("Admin").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();


                    for (DocumentSnapshot d : dsList) {

                        adminEmail=d.getString("email");
                        adminpassword=d.getString("password");

                    }


                }

            }
        });
        return adminEmail;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}





