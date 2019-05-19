package com.example.ben0.leavemanagementsystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class StudentLogin extends AppCompatActivity {
    EditText etId, etPass;
    public static String studId;
    String studlogin_url = "http://leavemaster.000webhostapp.com/studLogin.php";
    String tokengen_url = "http://leavemaster.000webhostapp.com/tokenSave.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);




        etId = (EditText) findViewById(R.id.et_id);
        etPass = (EditText) findViewById(R.id.et_pass);

        etId.setEnabled(false);
        etId.setText(studId);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StudentLogin.this, MainActivity.class);
        startActivity(intent);
    }

    public void signUp(View view){

        Intent intent = new Intent(StudentLogin.this, StudentSignup.class);
        startActivity(intent);
    }

    public void loggedin(View view){

        final String id = etId.getText().toString();
        final String pass = etPass.getText().toString();


        StringRequest string = new StringRequest(Request.Method.POST, tokengen_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken());
                params.put("myId", id);
                //params.put("theId", myId);

                return params;
            }
        };
        MySingleton.getInstance(StudentLogin.this).addTorequestQueue(string);

       /* AlertDialog.Builder db = new AlertDialog.Builder(StudentLogin.this);
        db.setTitle("Checking");
        db.setMessage("Please wait while Authenticating");
        db.setCancelable(true);
        db.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        db.show();*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress_dialog);
        final Dialog dialog = builder.create();

        dialog.show();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, studlogin_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();

                        if(response.equals("welcome")){
                            Toast.makeText(StudentLogin.this, "Welcome", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StudentLogin.this, StudentActivity.class);
                            startActivity(intent);
                        }
                        else{
                            dialog.dismiss();
                            AlertDialog.Builder db = new AlertDialog.Builder(StudentLogin.this);
                            db.setTitle("Login Failed");
                            db.setMessage("Check your ID or Password");
                            db.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            db.show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(StudentLogin.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("pass", pass);
                return params;
            }
        };


        MySingleton.getInstance(StudentLogin.this).addTorequestQueue(stringRequest);


    }
}
