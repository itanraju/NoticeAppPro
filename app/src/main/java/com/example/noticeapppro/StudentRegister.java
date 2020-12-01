package com.example.noticeapppro;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StudentRegister extends AppCompatActivity {


    EditText edtName,edtEmail,edtPass,edtNumber,edtConfirmPass;
    Button btnRegister;
    TextView btnBack;
    FirebaseAuth firebaseAuth;
    RelativeLayout qqq;
    FirebaseFirestore firebaseFirestore;
    String id;
    String pass,email,name,number,cPass;
    ProgressDialog progressDialog;
    ArrayList studentList;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        edtName=findViewById(R.id.edtName);
        edtEmail=findViewById(R.id.edtEmail);
        edtPass=findViewById(R.id.edtPass);
        edtNumber=findViewById(R.id.edtNumber);
        btnRegister=findViewById(R.id.btnRegister);
        btnBack=findViewById(R.id.btnBack);
        qqq=findViewById(R.id.qqq);
        edtConfirmPass=findViewById(R.id.edtConfirmPass);

        studentList=new ArrayList();



        progressDialog=new ProgressDialog(StudentRegister.this);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);
        progressDialog.setMax(100);

        Animation animation= AnimationUtils.loadAnimation(StudentRegister.this,R.anim.fade);
        Animation animation1= AnimationUtils.loadAnimation(StudentRegister.this,R.anim.scale_fade);
        edtName.startAnimation(animation);
        edtEmail.startAnimation(animation);
        edtPass.startAnimation(animation);
        edtNumber.startAnimation(animation);
        btnRegister.startAnimation(animation);
        btnBack.startAnimation(animation);
        qqq.startAnimation(animation1);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=edtEmail.getText().toString();
                pass=edtPass.getText().toString();
                name=edtName.getText().toString();
                number=edtNumber.getText().toString();
                cPass=edtConfirmPass.getText().toString();

                if(!email.isEmpty() && !pass.isEmpty() && !name.isEmpty() && !number.isEmpty() && !cPass.isEmpty()) {

                    if (pass.equals(cPass))
                    {
                        if (pass.length()>7)
                        {
                            progressDialog.show();
                            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {

                                        id = firebaseAuth.getCurrentUser().getUid();
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                   Student student = new Student(email, pass, name, number, id);
                                                    firebaseFirestore.collection("Student").document(id).set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(StudentRegister.this, "Check your mail", Toast.LENGTH_SHORT).show();
                                                            Intent intent=new Intent(StudentRegister.this,StudentLogin.class);
                                                            startActivity(intent);
                                                            Animatoo.animateZoom(StudentRegister.this);
                                                            finish();
                                                        }
                                                    });
                                                }
                                                else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(StudentRegister.this, "Sending email Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(StudentRegister.this, "Creating user Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(StudentRegister.this, "Password must have 8 characters", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(StudentRegister.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(StudentRegister.this, "Please Fill All The Field", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentRegister.this, StudentLogin.class);
                startActivity(intent);
                Animatoo.animateSlideRight(StudentRegister.this);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(StudentRegister.this,StudentLogin.class);
        startActivity(intent);
        Animatoo.animateSlideRight(StudentRegister.this);
        finish();
    }


}