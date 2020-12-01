package com.example.noticeapppro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class UploadPost extends AppCompatActivity {


    Button addNotice,addMaterial,addReport;
    RelativeLayout aaaaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        addMaterial=findViewById(R.id.addMaterial);
        addNotice=findViewById(R.id.addNotice);
        addReport=findViewById(R.id.addReport);
        aaaaa=findViewById(R.id.aaaaa);


        Animation animation= AnimationUtils.loadAnimation(UploadPost.this,R.anim.fade_late);
        Animation animation1= AnimationUtils.loadAnimation(UploadPost.this,R.anim.scale_late);
        addReport.startAnimation(animation);
        addMaterial.startAnimation(animation);
        addNotice.startAnimation(animation);
        aaaaa.startAnimation(animation1);


        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UploadPost.this, AddNotice.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(UploadPost.this);
                finish();
            }
        });

        addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UploadPost.this,AddMaterial.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(UploadPost.this);
                finish();

            }
        });

        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UploadPost.this,AddReport.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(UploadPost.this);
                finish();
            }
        });

    }

}
