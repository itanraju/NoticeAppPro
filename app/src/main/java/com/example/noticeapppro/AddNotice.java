package com.example.noticeapppro;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNotice extends AppCompatActivity {

    ImageView noticeImg;
    EditText noticeTitle;
    Button btnAdd,select;

    Uri noticeImgUrl;
    String noticeByName;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RelativeLayout zz;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        noticeImg=findViewById(R.id.noticeImg);
        noticeTitle=findViewById(R.id.noticeTitle);
        btnAdd=findViewById(R.id.btnAdd);
        select=findViewById(R.id.select);
        zz=findViewById(R.id.zz);
        progressDialog=new ProgressDialog(AddNotice.this);

        Animation animation= AnimationUtils.loadAnimation(AddNotice.this,R.anim.fade_late);
        Animation animation1= AnimationUtils.loadAnimation(AddNotice.this,R.anim.scale_late);
        noticeImg.startAnimation(animation);
        noticeTitle.startAnimation(animation);
        btnAdd.startAnimation(animation);
        select.startAnimation(animation);
        zz.startAnimation(animation1);

        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);
        progressDialog.setMax(100);


        firebaseFirestore= FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(AddNotice.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddNotice.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                                .start(AddNotice.this);
                    }
                }
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noticeImgUrl!=null && noticeTitle!=null)
                {
                    progressDialog.show();


                    final String noticeById = firebaseAuth.getCurrentUser().getUid();
                    final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    final String mTitle=noticeTitle.getText().toString();
                    final String noticeId=currentDate+"_"+currentTime;

                     firebaseFirestore.collection("Staff").document(noticeById).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                         @Override
                         public void onSuccess(DocumentSnapshot documentSnapshot) {
                             noticeByName=documentSnapshot.getString("name");



                             storageReference.child("Notice").child(noticeId).putFile(noticeImgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                 @Override
                                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                     Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                                     while(!uri.isComplete());
                                     Uri uri12=uri.getResult();

                                     Notice notice=new Notice(noticeId,uri12.toString(),mTitle,currentDate,currentTime,noticeByName);

                                     firebaseFirestore.collection("Notice").document(noticeId).set(notice).addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {

                                             progressDialog.dismiss();
                                             Toast.makeText(AddNotice.this, "Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                                             Intent intent=new Intent(AddNotice.this,MainActivity.class);
                                             startActivity(intent);
                                             Animatoo.animateZoom(AddNotice.this);
                                             finish();
                                         }
                                     }).addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {

                                             progressDialog.dismiss();
                                             Toast.makeText(AddNotice.this, "Failed", Toast.LENGTH_SHORT).show();
                                         }
                                     });

                                 }
                             });


                         }
                     });



                     //Notice notice=new Notice(noticeId,noticeImgUrl.toString(),mTitle,currentDate,currentTime);

                    /*
                    storageReference.child("Notice").child(noticeId).putFile(noticeImgUrl).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful())
                            {
                                firebaseFirestore.collection("Notice").document(noticeId).set(notice).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddNotice.this, "Succesfully Added", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddNotice.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }
                    });
                     */



                }
                else
                {
                    Toast.makeText(AddNotice.this, "Select Notice And Set Title", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "File Succesfully Selected", Toast.LENGTH_SHORT).show();
                noticeImgUrl = result.getUri();
                noticeImg.setImageURI(noticeImgUrl);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "File Not Selected", Toast.LENGTH_SHORT).show();
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AddNotice.this,UploadPost.class);
        startActivity(intent);
        Animatoo.animateSlideRight(AddNotice.this);
        finish();
    }
}
