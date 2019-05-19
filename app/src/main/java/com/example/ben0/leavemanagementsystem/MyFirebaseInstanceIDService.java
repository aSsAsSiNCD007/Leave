package com.example.ben0.leavemanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by altha on 3/29/2019.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static String myId;

    String tokengen_url = "http://leavemaster.000webhostapp.com/tokenSave.php";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d("haii", "Refreshed Token" + token);

        registerToken(token);


    }

    private void registerToken(final String token) {

        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);



        /*StringRequest string = new StringRequest(Request.Method.POST, tokengen_url,
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
                //params.put("theId", myId);

                return params;
            }
        };
        MySingleton.getInstance(MyFirebaseInstanceIDService.this).addTorequestQueue(string);*/



    }

}
