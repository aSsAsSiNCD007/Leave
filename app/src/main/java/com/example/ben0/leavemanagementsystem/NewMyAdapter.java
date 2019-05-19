package com.example.ben0.leavemanagementsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by altha on 4/24/2019.
 */

public class NewMyAdapter extends RecyclerView.Adapter<NewMyAdapter.ViewHolder> {

    String statusSet_url = "http://leavemaster.000webhostapp.com/StatusSet.php";

    private List<ListItemStudent> listItems;
    private Context context;

    String id, reqId;

    public NewMyAdapter(List<ListItemStudent> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public NewMyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, parent, false);
        return new NewMyAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final NewMyAdapter.ViewHolder holder, final int position) {

        final ListItemStudent listItemStudent = listItems.get(position);

        holder.textViewreqId.setText(listItemStudent.getReqid());
        holder.textViewId.setText(listItemStudent.getId());
        holder.textViewstStatus.setText(listItemStudent.getStatus());
        holder.textViewSdate.setText(listItemStudent.getsDate());
        holder.textViewEdate.setText(listItemStudent.geteDate());
        holder.textViewReason.setText(listItemStudent.getReason());
        holder.delete.setOnClickListener(new View.OnClickListener() {

            final int currentPos = position;
            final ListItemStudent list = listItems.get(position);

            @Override
            public void onClick(View v) {


                id = (String) holder.textViewId.getText().toString();
                reqId = (String) holder.textViewreqId.getText().toString();
                //Toast.makeText(context, id, Toast.LENGTH_SHORT).show();



                final String status = "Visited";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, statusSet_url,
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


    }

    private void delete(ListItemStudent list) {

        int pos = listItems.indexOf(list);
        listItems.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewreqId;
        public TextView textViewId;
        public TextView textViewstStatus;
        public TextView textViewSdate;
        public TextView textViewEdate;
        public TextView textViewReason;
        public ImageView delete;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewreqId = (TextView) itemView.findViewById(R.id.reqId);
            textViewId = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewstStatus = (TextView) itemView.findViewById(R.id.textViewStatus);
            textViewSdate = (TextView) itemView.findViewById(R.id.textViewstart);
            textViewEdate = (TextView) itemView.findViewById(R.id.textViewend);
            textViewReason = (TextView) itemView.findViewById(R.id.textViewReason);
            delete = (ImageView) itemView.findViewById(R.id.imageDelete);


        }
    }
}
