package com.example.ben0.leavemanagementsystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StaffSignup extends AppCompatActivity {

    String staffsignup_url = "http://leavemaster.000webhostapp.com/staffsignup.php";

    String[] depts = new String[]{"Computer Science", "Mechanical", "Civil", "Electrical", "Electronics", "Information Technology"};
    EditText etId, etPass, etConfirm;
    Spinner spinDept;
    String item;

    public static String staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_signup);

        etId = (EditText) findViewById(R.id.signUpId);
        etPass = (EditText) findViewById(R.id.signUpPass);
        etConfirm = (EditText) findViewById(R.id.signUpConfirm);

        spinDept = (Spinner) findViewById(R.id.signUpDept);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StaffSignup.this, android.R.layout.simple_spinner_item, depts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDept.setAdapter(adapter);
        etId.setEnabled(false);
        etId.setText(staffId);

    }

    public void Register(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress_dialog);
        final Dialog dialog = builder.create();

        dialog.show();
        final String pass = etPass.getText().toString();
        String confirm = etConfirm.getText().toString();
        final String staffId = etId.getText().toString();
        item = spinDept.getSelectedItem().toString();
        if(item.equals("Computer Science")){
            item = "CS1";
        }
        else if(item.equals("Mechanical")){
            item = "ME1";
        }
        else if(item.equals("Civil")){
            item = "CE1";
        }
        else if(item.equals("Electrical")){
            item = "EEE1";
        }
        else if(item.equals("Electronics")){
            item = "EC1";
        }
        else if(item.equals("Information Technology")){
            item = "IT1";
        }

        if(pass.equals(confirm)){
            //something



            StringRequest string = new StringRequest(Request.Method.POST, staffsignup_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            /*alertDialog = new AlertDialog.Builder(StudentSignup.this).create();
                            alertDialog.setTitle("Success!!");
                            alertDialog.setMessage("you are successfully registered into 'ASSIST ME'");*/

                            if(response.equals("welcome")){

                                AlertDialog.Builder db = new AlertDialog.Builder(StaffSignup.this);
                                db.setTitle("Welcome");
                                db.setMessage("You are successfully registed to 'ASSIST ME'");
                                db.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        StaffLogin.staffId = staffId;
                                        Intent intent = new Intent(StaffSignup.this, StaffLogin.class);
                                        startActivity(intent);
                                    }
                                });
                                db.show();
                            }
                            else{
                                dialog.dismiss();
                                AlertDialog.Builder db = new AlertDialog.Builder(StaffSignup.this);
                                db.setTitle("Warning!!");
                                db.setMessage("Check your details Again");
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
                    Toast.makeText(StaffSignup.this, "Error..Try Again", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("staff_no", staffId);
                    params.put("dept", item);
                    params.put("pass", pass);
                    return params;
                }
            };
            MySingleton.getInstance(StaffSignup.this).addTorequestQueue(string);


        }
        else{
            Toast.makeText(StaffSignup.this, "Password Doen't Match", Toast.LENGTH_SHORT).show();
        }
    }
}
