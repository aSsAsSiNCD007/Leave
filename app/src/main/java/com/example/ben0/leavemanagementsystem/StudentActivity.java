package com.example.ben0.leavemanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
    }

    public void applyLeave(View view){
        Intent intent = new Intent(StudentActivity.this, LeaveApply.class);
        startActivity(intent);
    }

    public void inBox(View view){
        Intent intent = new Intent(StudentActivity.this, StudentInbox.class);
        startActivity(intent);
    }

    public void status(View view){
        Intent intent = new Intent(StudentActivity.this, Statistics.class);
        startActivity(intent);
    }

    public void calender(View view){

        Intent intent = new Intent(StudentActivity.this, AcademicCalender.class);
        startActivity(intent);
    }

    public void events(View view){
        Intent intent = new Intent(StudentActivity.this, Events.class);
        startActivity(intent);
    }

    public void feedBack(View view){
        Intent intent = new Intent(StudentActivity.this, Feedback.class);
        startActivity(intent);
    }
}
