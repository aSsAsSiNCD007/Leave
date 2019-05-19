package com.example.ben0.leavemanagementsystem;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import static com.example.ben0.leavemanagementsystem.R.id.recyclerView;

public class StudentInbox extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItemStudent> listItems;

    String statusFetch_url = "http://leavemaster.000webhostapp.com/statusFetch.php";
    public static String myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_inbox);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();



        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress_dialog);
        final Dialog dialog = builder.create();

        dialog.show();

        StringRequest string = new StringRequest(Request.Method.POST, statusFetch_url,
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

                                ListItemStudent item = new ListItemStudent(
                                        o.getString("ReqId"),
                                        o.getString("Id"),
                                        o.getString("Status"),
                                        o.getString("Sdate"),
                                        o.getString("Edate"),
                                        o.getString("Reason")

                                );

                                listItems.add(item);

                                adapter = new NewMyAdapter(listItems, getApplicationContext());
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
                Toast.makeText(StudentInbox.this, "Error..Try Again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", myId);

                return params;
            }
        };
        MySingleton.getInstance(StudentInbox.this).addTorequestQueue(string);
    }
}
