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

public class StaffRegister extends AppCompatActivity {


    EditText edtName,edtPass,edtEmail,edtNumber;
    Button btnRegister;
    TextView btnBack;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String staffId;
    RelativeLayout qqqq;
    EditText edtConfirmPass;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_register);


        edtEmail=findViewById(R.id.edtEmail);
        edtName=findViewById(R.id.edtName);
        edtPass=findViewById(R.id.edtPass);
        edtNumber=findViewById(R.id.edtNumber);
        btnRegister=findViewById(R.id.btnRegister);
        btnBack=findViewById(R.id.btnBack);
        qqqq=findViewById(R.id.qqqq);
        edtConfirmPass=findViewById(R.id.edtConfirmPass);
        progressDialog=new ProgressDialog(StaffRegister.this);


        Animation animation= AnimationUtils.loadAnimation(StaffRegister.this,R.anim.fade);
        Animation animation1= AnimationUtils.loadAnimation(StaffRegister.this,R.anim.scale_fade);
        edtEmail.startAnimation(animation);
        edtName.startAnimation(animation);
        edtPass.startAnimation(animation);
        edtNumber.startAnimation(animation);
        btnRegister.startAnimation(animation);
        btnBack.startAnimation(animation);
        qqqq.startAnimation(animation1);
        progressDialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading");


        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String email=edtEmail.getText().toString();
                final String pass=edtPass.getText().toString();
                final String name=edtName.getText().toString();
                final String number=edtNumber.getText().toString();
                final String cpass=edtConfirmPass.getText().toString();


                if(!email.isEmpty() && !pass.isEmpty() && !name.isEmpty() && !number.isEmpty() && !cpass.isEmpty()) {


                    if (pass.equals(cpass))
                    {

                        if(pass.length()>7)
                        {
                            progressDialog.show();
                            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {

                                        staffId = firebaseAuth.getCurrentUser().getUid();
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Staff staff = new Staff(email, pass, name, number, staffId);
                                                    firebaseFirestore.collection("Staff").document(staffId).set(staff).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(StaffRegister.this, "Check your mail", Toast.LENGTH_SHORT).show();
                                                            Intent intent=new Intent(StaffRegister.this,StudentLogin.class);
                                                            startActivity(intent);
                                                            Animatoo.animateZoom(StaffRegister.this);
                                                            finish();
                                                            
                                                        }
                                                    });
                                                }
                                                else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(StaffRegister.this, "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(StaffRegister.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(StaffRegister.this, "Password must have 8 characters", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else
                    {
                        Toast.makeText(StaffRegister.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                    }



                }
                else
                {
                    Toast.makeText(StaffRegister.this, "Please Fill All The Field", Toast.LENGTH_SHORT).show();
                }


            }
        });





        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StaffRegister.this,StudentLogin.class);
                startActivity(intent);
                Animatoo.animateSlideRight(StaffRegister.this);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent intent=new Intent(StaffRegister.this,StudentLogin.class);
        startActivity(intent);
        Animatoo.animateSlideRight(StaffRegister.this);
        finish();
    }
}
