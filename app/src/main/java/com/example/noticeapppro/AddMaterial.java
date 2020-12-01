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

public class AddMaterial extends AppCompatActivity {

    Button btnSelect, btnUpload;
    TextView materialName;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    EditText materialTitle;

    Intent dataFile;
    String mUrl;
    String mMaterialByName;
    RelativeLayout z;
    boolean fileSelected=false;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        btnSelect = findViewById(R.id.btnSelect);
        btnUpload = findViewById(R.id.btnUpload);
        materialName = findViewById(R.id.materialName);
        materialTitle = findViewById(R.id.materialTitle);
        z=findViewById(R.id.z);
        progressDialog=new ProgressDialog(AddMaterial.this);

        Animation animation= AnimationUtils.loadAnimation(AddMaterial.this,R.anim.fade_late);
        Animation animation1= AnimationUtils.loadAnimation(AddMaterial.this,R.anim.scale_late);
        btnUpload.startAnimation(animation);
        btnSelect.startAnimation(animation);
        materialTitle.startAnimation(animation);
        materialName.startAnimation(animation);
        z.startAnimation(animation1);

        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);



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

                if(fileSelected && !materialTitle.getText().toString().isEmpty())
                {
                    progressDialog.show();

                    final String mTitle=materialTitle.getText().toString();
                    final String mMaterialById=firebaseAuth.getCurrentUser().getUid();

                    final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    final String mId=currentDate+"_"+currentTime;

                    firebaseFirestore.collection("Staff").document(mMaterialById).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            mMaterialByName=documentSnapshot.getString("name");
                            storageReference.child("Study Material").child(mId).putFile(dataFile.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                                    while(!uri.isComplete());
                                    Uri uri12=uri.getResult();
                                    mUrl=uri12.toString();
                                    Material material=new Material(currentDate+"_"+currentTime,mTitle,mUrl,currentDate,currentTime,mMaterialByName);
                                    firebaseFirestore.collection("Study Material").document(mId).set(material).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(AddMaterial.this, "Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(AddMaterial.this,MainActivity.class);
                                            startActivity(intent);
                                            Animatoo.animateZoom(AddMaterial.this);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(AddMaterial.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddMaterial.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else
                {
                    Toast.makeText(AddMaterial.this, "Select Material and Set Title", Toast.LENGTH_SHORT).show();
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
            materialName.setText(selectedFileURI.getLastPathSegment());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AddMaterial.this,UploadPost.class);
        startActivity(intent);
        Animatoo.animateSlideRight(AddMaterial.this);
        finish();
    }
}
