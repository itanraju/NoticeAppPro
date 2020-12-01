package com.example.noticeapppro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPanel extends AppCompatActivity {

    Button btnStaffList,btnStudentList,btnReport,btnMaterial,btnNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        btnStaffList=findViewById(R.id.btnStaffList);
        btnStudentList=findViewById(R.id.btnStudentList);
        btnReport=findViewById(R.id.btnReport);
        btnMaterial=findViewById(R.id.btnMaterial);
        btnNotice=findViewById(R.id.btnNotice);

        btnStaffList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminPanel.this, StaffListActivity.class);
                startActivity(intent);


            }
        });

        btnStudentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminPanel.this, StudentListActivity.class);
                startActivity(intent);

            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminPanel.this,ReportListActivity.class);
                startActivity(intent);



            }
        });

        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminPanel.this,NoticeListActivity.class);
                startActivity(intent);

            }
        });

        btnMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminPanel.this,MaterialListActivity.class);
                startActivity(intent);

            }
        });

    }
}
