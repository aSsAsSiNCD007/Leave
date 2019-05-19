package com.example.ben0.leavemanagementsystem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {

    String tokengen_url = "http://leavemaster.000webhostapp.com/tokenSave.php";

    public static EditText id;
    Button button;
    AlertDialog dialog;
    Context context;
    String toks;

    String check_url = "http://leavemaster.000webhostapp.com/varify.php";
    AlertDialog.Builder builder;
    AlertDialog dialogue;

    public static String text = "";



    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        button = (Button) findViewById(R.id.btn_go);
        id = (EditText) findViewById(R.id.id_go);
        builder = new AlertDialog.Builder(MainActivity.this);
        //************OnClick******************************
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String id1;
                id1 = id.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //View view = getLayoutInflater().inflate(R.layout.progress);
                builder.setView(R.layout.progress_dialog);
                final Dialog dialog = builder.create();

                dialog.show();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, check_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                dialog.dismiss();


                                if(response.equals("Welcome")){

                                    StudentSignup.studId = id1;
                                    StudentLogin.studId = id1;
                                    StudentInbox.myId = id1;
                                    LeaveApply.myId = id1;

                                    MyFirebaseInstanceIDService.myId = id1;

                                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    //setContentView(R.layout.activity_main);
                                    Intent intent = new Intent(MainActivity.this, StudentLogin.class);
                                    startActivity(intent);

                                }

                                else if(response.equals("Welcome.")){

                                    StaffSignup.staffId = id1;
                                    StaffLogin.staffId = id1;
                                    StaffInbox.tutorId = id1;

                                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    //setContentView(R.layout.activity_main);
                                    Intent intent = new Intent(MainActivity.this, StaffLogin.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);*/
                                }


                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        Toast.makeText(MainActivity.this, "Error...Check Your Internet", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", id1);
                        return params;
                    }
                };//************Ending of String Request**************************


                MySingleton.getInstance(MainActivity.this).addTorequestQueue(stringRequest);
            }
        });

      }


    // ********** Code for BarCode reader ********************************************************
    public void barCode(View view){

        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultHandler());

        setContentView(scannerView);
        scannerView.startCamera();
    }

   /*@Override
   public void onPause(){
       super.onPause();
       scannerView.stopCamera();
   }*/

    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler{

        @Override
        public void handleResult(Result result){
            final String resultcode = result.getText();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //View view = getLayoutInflater().inflate(R.layout.progress);
            builder.setView(R.layout.progress_dialog);
            final Dialog dialog = builder.create();

            dialog.show();
            //Toast.makeText(MainActivity.this, resultcode, Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, check_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            dialog.dismiss();
                            if(response.equals("Welcome")){

                                StudentSignup.studId = resultcode;
                                StudentLogin.studId = resultcode;
                                StudentInbox.myId = resultcode;
                                LeaveApply.myId = resultcode;
                               MyFirebaseInstanceIDService.myId = resultcode;


                                Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                //setContentView(R.layout.activity_main);
                                Intent intent = new Intent(MainActivity.this, StudentLogin.class);
                                startActivity(intent);

                            }

                            else if(response.equals("Welcome.")){

                                StaffSignup.staffId = resultcode;
                                StaffLogin.staffId = resultcode;
                                StaffInbox.tutorId = resultcode;

                                Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                //setContentView(R.layout.activity_main);
                                Intent intent = new Intent(MainActivity.this, StaffLogin.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                                /*Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);*/
                            }

                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    dialog.dismiss();

                    Toast.makeText(MainActivity.this, "Error...Check Your Internet", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", resultcode);
                    return params;
                }
            };// end string request**********


            MySingleton.getInstance(MainActivity.this).addTorequestQueue(stringRequest);
        }
    }

    // ********* End of Bar code Reading *****************************************************

}
