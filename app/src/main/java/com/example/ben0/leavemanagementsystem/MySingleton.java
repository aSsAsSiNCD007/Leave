package com.example.ben0.leavemanagementsystem;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by altha on 23-02-2019.
 */

public class MySingleton {

    private static MySingleton mySingleton;
    private static RequestQueue requestQueue;
    private static Context context;

    public MySingleton(Context ctx){
        context = ctx;
        requestQueue = getRequestQueue();
    }

    //***********For return the Instance************
    public static synchronized MySingleton getInstance(Context ctx){
        if(mySingleton == null){
            mySingleton = new MySingleton(ctx);
        }
        return mySingleton;
    }

    //*****FOR Return THE Request Queue***************
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    //**********For adding Requistes into the Request Queue**********
    public <T>void addTorequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
