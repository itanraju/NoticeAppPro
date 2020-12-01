package com.example.noticeapppro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends AppCompatActivity {

    EditText edtEmail;
    Button btnSend;
    FirebaseAuth firebaseAuth;
    RelativeLayout qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth= FirebaseAuth.getInstance();
        edtEmail=findViewById(R.id.edtEmail);
        btnSend=findViewById(R.id.btnSend);
        qq=findViewById(R.id.qq);

        Animation animation= AnimationUtils.loadAnimation(ForgotPassword.this,R.anim.fade);
        Animation animation1= AnimationUtils.loadAnimation(ForgotPassword.this,R.anim.scale_fade);

        qq.startAnimation(animation1);
        edtEmail.startAnimation(animation);
        btnSend.startAnimation(animation);



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=edtEmail.getText().toString();

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgotPassword.this, "Check Your Mail", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ForgotPassword.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ForgotPassword.this,StudentLogin.class);
        startActivity(intent);
        Animatoo.animateSlideRight(ForgotPassword.this);

    }
}
