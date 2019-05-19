package com.example.ben0.leavemanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;



import java.util.HashMap;
import java.util.Map;

public class StaffActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
    }

    public void applyLeave(View view){
        Intent intent = new Intent(StaffActivity.this, LeaveApply.class);
        startActivity(intent);
    }


    public void inBox(View view){

        Intent intent = new Intent(StaffActivity.this, StaffInbox.class);
        startActivity(intent);

    }
    public void status(View view){
        Intent intent = new Intent(StaffActivity.this, Statistics.class);
        startActivity(intent);
    }

    public void calender(View view){

        Intent intent = new Intent(StaffActivity.this, AcademicCalender.class);
        startActivity(intent);
    }

    public void events(View view){
        Intent intent = new Intent(StaffActivity.this, Events.class);
        startActivity(intent);
    }

    public void feedBack(View view){
        Intent intent = new Intent(StaffActivity.this, Feedback.class);
        startActivity(intent);
    }
}
