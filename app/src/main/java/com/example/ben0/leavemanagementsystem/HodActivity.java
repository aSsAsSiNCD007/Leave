package com.example.ben0.leavemanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class HodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod);
    }

    public void events(View view){

        Intent intent = new Intent(HodActivity.this, EventsAdder.class);
        startActivity(intent);
    }
}
