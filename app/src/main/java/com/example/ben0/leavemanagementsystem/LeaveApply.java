package com.example.ben0.leavemanagementsystem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LeaveApply extends AppCompatActivity {

    String saveLeave_url = "http://leavemaster.000webhostapp.com/saveLeave.php";

    public static String myId;

    //String myTutor = StudentSignup.myTutor;


    AlertDialog alertDialog;

    String days_url = "http://leavemaster.000webhostapp.com/dates.php";

    EditText fromDate, toDate, noDays, reason, description;
    public static String text = "";
    private int year1, month1, day1, year2, month2, day2;
    int dayCount = 0;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendars = Calendar.getInstance();

    String date1,date2;
    long diff = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_apply);





        fromDate = (EditText) findViewById(R.id.fromDate);
        noDays = (EditText) findViewById(R.id.noDays);
        reason = (EditText) findViewById(R.id.reason);
        description = (EditText) findViewById(R.id.description);

        description.setText(text);

        noDays.setEnabled(false);

//***************************from date********************************
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                fromDate.setText(sdf.format(myCalendar.getTime()));
                date1 = fromDate.getText().toString();
            }
        };

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LeaveApply.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // ************************for todate********************************************
        toDate = (EditText) findViewById(R.id.toDate);

        final DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int years, int months, int dayOfMonths) {
                myCalendars.set(Calendar.YEAR, years);
                myCalendars.set(Calendar.MONTH, months);
                myCalendars.set(Calendar.DAY_OF_MONTH, dayOfMonths);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                toDate.setText(sdf.format(myCalendars.getTime()));
                date2 = toDate.getText().toString();

                if(date1 != ""){
                   // noDays.setText(date1);
                    //Toast.makeText(LeaveApply.this, date1 +" "+ date2, Toast.LENGTH_SHORT).show();

                }

                try {
                    Date dates1 = sdf.parse(date1);
                    Date dates2 = sdf.parse(date2);
                    long diff = dates2.getTime() - dates1.getTime();
                    //Toast.makeText(LeaveApply.this, "Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS), Toast.LENGTH_SHORT).show();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    //Toast.makeText(LeaveApply.this, "Days: " + days, Toast.LENGTH_SHORT).show();
                    if(days < 0){
                        days = days * (-1);
                    }
                    noDays.setText(days + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LeaveApply.this, dates, myCalendars
                        .get(Calendar.YEAR), myCalendars.get(Calendar.MONTH),
                        myCalendars.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    public void submit(View view) {


        final String st_Date = fromDate.getText().toString();
        final String end_Date = toDate.getText().toString();
        final String num = noDays.getText().toString();
        final String reasons = reason.getText().toString();
        final String desc = description.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(LeaveApply.this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress_dialog);
        final Dialog dialog = builder.create();

        dialog.show();

        //Toast.makeText(LeaveApply.this, myId, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, saveLeave_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();

                        if(!response.equals("Error")){

                            AlertDialog.Builder db = new AlertDialog.Builder(LeaveApply.this);
                            db.setTitle("Status");
                            db.setMessage("Request Send Successfully!!");
                            db.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(LeaveApply.this, StudentActivity.class);
                                    startActivity(intent);
                                }
                            });
                            db.show();
                        }
                        else{
                            AlertDialog.Builder db = new AlertDialog.Builder(LeaveApply.this);
                            db.setTitle("Warning!!");
                            db.setMessage("something Went Wrong");
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
                Toast.makeText(LeaveApply.this, "Serve TimeOut", Toast.LENGTH_SHORT).show();

                //description.setText((CharSequence) error);

                Log.d("Error message", String.valueOf(error));

            }


        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("myId", myId);
                params.put("sDate", st_Date);
                params.put("eDate", end_Date);
                params.put("number", num);
                params.put("reason", reasons);
                params.put("desc", desc);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));


        MySingleton.getInstance(LeaveApply.this).addTorequestQueue(stringRequest);


    }





    //Format Update Functionssss********************************************************************


}

