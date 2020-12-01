package com.example.noticeapppro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddReport extends AppCompatActivity {

    Button btnSelect, btnUpload;
    TextView reportName;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    EditText reportTitle;

    Intent dataFile;
    String mUrl;
    String mReportByName;
    RelativeLayout zzz;
    boolean fileSelected;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        btnSelect = findViewById(R.id.btnSelect);
        btnUpload = findViewById(R.id.btnUpload);
        reportName = findViewById(R.id.reportName);
        reportTitle = findViewById(R.id.reportTitle);
        zzz=findViewById(R.id.zzz);
        progressDialog=new ProgressDialog(AddReport.this);

        Animation animation= AnimationUtils.loadAnimation(AddReport.this,R.anim.fade_late);
        Animation animation1= AnimationUtils.loadAnimation(AddReport.this,R.anim.scale_late);
        btnSelect.startAnimation(animation);
        btnUpload.startAnimation(animation);
        reportTitle.startAnimation(animation);
        reportName.startAnimation(animation);
        zzz.startAnimation(animation1);

        progressDialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fileSelected && !reportTitle.getText().toString().isEmpty())
                {

                    progressDialog.show();


                    final String mTitle=reportTitle.getText().toString();

                    final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    final String mId=currentDate+"_"+currentTime;
                    final String mReportById=firebaseAuth.getCurrentUser().getUid();

                    firebaseFirestore.collection("Staff").document(mReportById).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            mReportByName=documentSnapshot.getString("name");

                            storageReference.child("Attendence Report").child(mId).putFile(dataFile.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                                    while(!uri.isComplete());
                                    Uri uri12=uri.getResult();

                                    mUrl=uri12.toString();

                                    Report report=new Report(mId,mTitle,mUrl,currentDate,currentTime,mReportByName);

                                    firebaseFirestore.collection("Attendence Report").document(mId).set(report).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            progressDialog.dismiss();
                                            Toast.makeText(AddReport.this, "Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(AddReport.this,MainActivity.class);
                                            startActivity(intent);
                                            Animatoo.animateZoom(AddReport.this);
                                            finish();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressDialog.dismiss();
                                            Toast.makeText(AddReport.this, "Failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    Toast.makeText(AddReport.this, "Failed", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });







                }
                else
                {
                    Toast.makeText(AddReport.this, "Select File and Set Title", Toast.LENGTH_SHORT).show();
                }

            }
        });
        
        
        



    }

    public void selectFile() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("*/*");
        startActivityForResult(i, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Toast.makeText(this, "Succesfully Selected", Toast.LENGTH_SHORT).show();
            dataFile=data;
            fileSelected=true;
            Uri selectedFileURI = data.getData();
            reportName.setText(selectedFileURI.getLastPathSegment());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AddReport.this,UploadPost.class);
        startActivity(intent);
        Animatoo.animateSlideRight(AddReport.this);
        finish();
    }



}
