package com.example.ben0.leavemanagementsystem;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;

/**
 * Created by altha on 4/23/2019.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    String statusSave_url = "http://leavemaster.000webhostapp.com/saveStatus.php";

    private List<ListItem> listItems;
    private Context context;
    static String id,reqId;
    CardView cardView;


    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ListItem listItem = listItems.get(position);

        holder.textViewreqId.setText(listItem.getReqid());
        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.textViewstDate.setText(listItem.getStDate());
        holder.textViewendDate.setText(listItem.getEndDate());
        holder.textViewnumDays.setText(listItem.getNumDays());
        holder.textViewreason.setText(listItem.getReason());
        holder.textViewdescription.setText(listItem.getDescription());

        final int currentPos = position;
        final ListItem list = listItems.get(position);



        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //View view = getLayoutInflater().inflate(R.layout.progress);
                builder.setView(R.layout.progress_dialog);
                final Dialog dialog = builder.create();
                dialog.show();*/
                id = (String) holder.textViewDesc.getText().toString();
                reqId = (String) holder.textViewreqId.getText().toString();
                //Toast.makeText(context, id, Toast.LENGTH_SHORT).show();



                final String status = "Approved";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, statusSave_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //dialog.dismiss();
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", id);
                        params.put("rid", reqId);
                        params.put("status", status);

                        return params;
                    }
                };


                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                MySingleton.getInstance(context).addTorequestQueue(stringRequest);

                delete(list);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Reject Clicked", Toast.LENGTH_SHORT).show();

                id = (String) holder.textViewDesc.getText().toString();
                reqId = (String) holder.textViewreqId.getText().toString();

                final String status = "Rejected";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, statusSave_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //dialog.dismiss();
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", id);
                        params.put("rid", reqId);
                        params.put("status", status);

                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));


                MySingleton.getInstance(context).addTorequestQueue(stringRequest);

                delete(list);

            }
        });

        holder.forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Forwarded", Toast.LENGTH_SHORT).show();
               //delete(list);
            }
        });

    }

    private void delete(ListItem list) {

        int pos = listItems.indexOf(list);
        listItems.remove(pos);
        notifyItemRemoved(pos);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewstDate;
        public TextView textViewendDate;
        public TextView textViewnumDays;
        public TextView textViewreason;
        public TextView textViewdescription;
        public TextView textViewreqId;
        ImageView approve, reject, forward;
        public CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewreqId = (TextView) itemView.findViewById(R.id.reqId);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewstDate = (TextView) itemView.findViewById(R.id.textViewstart);
            textViewendDate = (TextView) itemView.findViewById(R.id.textViewend);
            textViewnumDays = (TextView) itemView.findViewById(R.id.textViewdays);
            textViewreason = (TextView) itemView.findViewById(R.id.textViewreason);
            textViewdescription = (TextView) itemView.findViewById(R.id.textViewdescr);
            approve = (ImageView) itemView.findViewById(R.id.imageApprove);
            reject = (ImageView) itemView.findViewById(R.id.imageReject);
            forward = (ImageView) itemView.findViewById(R.id.imageForward);
            cardView = (CardView) itemView.findViewById(R.id.cardMon);
        }
    }
}
