package com.example.ben0.leavemanagementsystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

public class StudentSignup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    String findtutor_url = "http://leavemaster.000webhostapp.com/findtutor.php";
    String gettutor_url = "http://leavemaster.000webhostapp.com/gettutor.php";
    String studReg_url = "http://leavemaster.000webhostapp.com/studReg.php";

    AlertDialog alertDialog;

    EditText etId,etPass,etConfirm;
    public  static EditText staffid;
    String[] arr;
    String name;
    public static String studsId;
    public static String studId;
    public static String myTutor;
    Spinner spinDept, spinTutor;
    String item = "CS1";
    String tut = "Select";
    String tempid;
    String[] depts = new String[]{"Computer Science", "Mechanical", "Civil", "Electrical", "Electronics", "Information Technology"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        etId = (EditText) findViewById(R.id.signUpId);
        etPass = (EditText) findViewById(R.id.signUpPass);
        etConfirm = (EditText) findViewById(R.id.signUpConfirm);
        staffid = (EditText) findViewById(R.id.staffid);

        spinDept = (Spinner) findViewById(R.id.signUpDept);
        spinDept.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentSignup.this, android.R.layout.simple_spinner_item, depts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDept.setAdapter(adapter);
        etId.setEnabled(false);
        etId.setText(studId);

    }

    public void Register(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress_dialog);
        final Dialog dialog = builder.create();

        dialog.show();

        final String pass = etPass.getText().toString();
        String confirm = etConfirm.getText().toString();
        final String adno = etId.getText().toString();
        final String stId = staffid.getText().toString();
        final String dept = spinDept.getSelectedItem().toString();
        final String tutor = spinTutor.getSelectedItem().toString();

        myTutor = stId;

        if(pass.equals(confirm)){
            //Toast.makeText(StudentSignup.this, "Password Match", Toast.LENGTH_SHORT).show();
            StringRequest string = new StringRequest(Request.Method.POST, studReg_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            dialog.dismiss();
                            /*alertDialog = new AlertDialog.Builder(StudentSignup.this).create();
                            alertDialog.setTitle("Success!!");
                            alertDialog.setMessage("you are successfully registered into 'ASSIST ME'");*/

                            if(response.equals("welcome")){

                                AlertDialog.Builder db = new AlertDialog.Builder(StudentSignup.this);
                                db.setTitle("Welcome");
                                db.setMessage("You are successfully registed to 'ASSIST ME'");
                                db.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        StudentLogin.studId = adno;
                                        Intent intent = new Intent(StudentSignup.this, StudentLogin.class);
                                        startActivity(intent);
                                    }
                                });
                                db.show();
                            }
                            else{
                                dialog.dismiss();
                                AlertDialog.Builder db = new AlertDialog.Builder(StudentSignup.this);
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
                    Toast.makeText(StudentSignup.this, "Error..Try Again", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ad_no", adno);
                    params.put("dept", item);
                    params.put("tutor", stId);
                    params.put("pass", pass);
                    return params;
                }
            };
            MySingleton.getInstance(StudentSignup.this).addTorequestQueue(string);


        }
        else {
            Toast.makeText(StudentSignup.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
        }

    }
//*********************************onclick of spinner department****************************************************************
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        item = parent.getItemAtPosition(position).toString();
        //Toast.makeText(StudnteSignup.this, item, Toast.LENGTH_SHORT).show();
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
//********************************String Requist************************************************************************************
        StringRequest stringRequest = new StringRequest(Request.Method.POST, findtutor_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray array = null;
                        try {

                            array = new JSONArray(response);
                            ArrayList<String> list = new ArrayList<>();
                            int i=0;
                            while(i < array.length()) {

                                JSONObject o = array.getJSONObject(i);
                                //Toast.makeText(StudentSignup.this, o.getString("Name"), Toast.LENGTH_SHORT).show();
                                name = o.getString("Name");
                                list.add(name);
                                spinTutor = (Spinner) findViewById(R.id.signUpTutor);
       //******************************************Tutor fetching*********************************************************************************************
                                spinTutor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        tut = parent.getItemAtPosition(position).toString();
                                        //Toast.makeText(StudentSignup.this, tut, Toast.LENGTH_SHORT).show();
                                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, gettutor_url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {

                                                        try {
                                                            JSONObject object = new JSONObject(response);
                                                            String query = object.getString("Tutid");
                                                            //Toast.makeText(StudentSignup.this, query, Toast.LENGTH_SHORT).show();
                                                            staffid.setText(query);
                                                            staffid.setEnabled(false);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //Toast.makeText(StudentSignup.this, response, Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Toast.makeText(StudentSignup.this, "error" , Toast.LENGTH_SHORT).show();
                                            }
                                        }){
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("tut", tut);
                                                params.put("dep", item);
                                                return params;
                                            }
                                        };

                                        MySingleton.getInstance(StudentSignup.this).addTorequestQueue(stringRequest1);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
        //****************************end of tutor fetch*******************************************************************

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentSignup.this, android.R.layout.simple_spinner_item, list);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinTutor.setAdapter(adapter);
                                i++;
                           }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(StudentSignup.this, "Error on "+item, Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("dept", item);
                return params;
            }
        };

        MySingleton.getInstance(StudentSignup.this).addTorequestQueue(stringRequest);

//***********************************end of string requits*************************************************************************

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}
