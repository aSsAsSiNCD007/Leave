package com.example.ben0.leavemanagementsystem;

/**
 * Created by altha on 4/24/2019.
 */

public class ListItemStudent {

    private String reqid;
    private String id;
    private String status;
    private String sDate;
    private String eDate;
    private String reason;

    public ListItemStudent(String reqid, String id, String status, String sDate, String eDate, String reason) {
        this.reqid = reqid;
        this.id = id;
        this.status = status;
        this.sDate = sDate;
        this.eDate = eDate;
        this.reason = reason;
    }

    public String getsDate() {
        return sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public String getReason() {
        return reason;
    }

    public String getReqid() {
        return reqid;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
