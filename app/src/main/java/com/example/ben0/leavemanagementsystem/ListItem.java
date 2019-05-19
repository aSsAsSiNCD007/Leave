package com.example.ben0.leavemanagementsystem;


import android.text.Html;
import android.widget.ImageView;

public class ListItem {

    private String reqid;
    private String head;
    private String desc;
    private String stDate;
    private String endDate;
    private String numDays;
    private String reason;
    private String description;

    public String getReqid() {
        return reqid;
    }

    public String getStDate() {
        return stDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getNumDays() {
        return numDays;
    }

    public String getReason() {
        return reason;
    }

    public String getDescription() {
        return description;
    }

    public ListItem(String reqid, String head, String desc, String stDate, String endDate, String numDays, String reason, String description) {
        this.reqid = reqid;
        this.head = head;
        this.desc = desc;
        this.stDate = stDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.reason = reason;
        this.description = description;
    }



    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }
}
