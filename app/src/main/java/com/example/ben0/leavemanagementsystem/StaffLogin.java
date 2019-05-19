package com.example.ben0.leavemanagementsystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class StaffLogin extends AppCompatActivity {

    String stafflogin_url = "http://leavemaster.000webhostapp.com/stafflogin.php";
    String tokengen_url = "http://leavemaster.000webhostapp.com/tokenSave.php";

    EditText etId, etPass;

    public static String staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);



        etId = (EditText) findViewById(R.id.etId);
        etPass = (EditText) findViewById(R.id.etPass);

        etId.setEnabled(false);
        etId.setText(staffId);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StaffLogin.this, MainActivity.class);
        startActivity(intent);
    }

    public void signUp(View view){
        Intent intent = new Intent(StaffLogin.this, StaffSignup.class);
        startActivity(intent);
    }

    public void loggedin (View view){
        /*Intent intent = new Intent(StaffLogin.this, StaffActivity.class);
        startActivity(intent);*/

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
        MySingleton.getInstance(StaffLogin.this).addTorequestQueue(string);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress_dialog);
        final Dialog dialog = builder.create();

        dialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, stafflogin_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();

                        if(response.equals("staff")){
                            Toast.makeText(StaffLogin.this, "Welcome", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StaffLogin.this, StaffActivity.class);
                            startActivity(intent);
                        }
                        else if(response.equals("HOD")){
                            Toast.makeText(StaffLogin.this, "Welcome", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StaffLogin.this, HodActivity.class);
                            startActivity(intent);
                        }

                        ///////////
                        else{
                            dialog.dismiss();
                            AlertDialog.Builder db = new AlertDialog.Builder(StaffLogin.this);
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
                Toast.makeText(StaffLogin.this, "Error", Toast.LENGTH_SHORT).show();

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


        MySingleton.getInstance(StaffLogin.this).addTorequestQueue(stringRequest);
    }



}
