package com.example.ben0.leavemanagementsystem;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffInbox extends AppCompatActivity {

    String leaveFetch_url = "http://leavemaster.000webhostapp.com/leaveFetch.php";

    public static String tutorId;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;




    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_inbox);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();



        loadRecyclerViewData();


    }

    //***************************************

    private void loadRecyclerViewData() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress_dialog);
        final Dialog dialog = builder.create();

        dialog.show();

        StringRequest string = new StringRequest(Request.Method.POST, leaveFetch_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                        JSONArray array = null;

                        try {
                            array = new JSONArray(response);
                            int i=0;
                            while(i < array.length()) {

                                JSONObject o = array.getJSONObject(i);


                                //Toast.makeText(StudentSignup.this, o.getString("Name"), Toast.LENGTH_SHORT).show();

                                ListItem item = new ListItem(
                                        o.getString("Req_Id"),
                                        o.getString("Name"),
                                        o.getString("MyId"),
                                        o.getString("StDate"),
                                        o.getString("EndDate"),
                                        o.getString("NumDays"),
                                        o.getString("Reason"),
                                        o.getString("Description")

                                );

                                listItems.add(item);

                                adapter = new MyAdapter(listItems, getApplicationContext());
                                recyclerView.setAdapter(adapter);
                                i++;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(StaffInbox.this, "Error..Try Again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tut_Id", tutorId);

                return params;
            }
        };
        MySingleton.getInstance(StaffInbox.this).addTorequestQueue(string);


    }

    //********************************

    /*private void setDialog(boolean show){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress_dialog);
        Dialog dialog = builder.create();

        if (show) {

            dialog.show();
        }
        else
            dialog.dismiss();
    }*/
}
