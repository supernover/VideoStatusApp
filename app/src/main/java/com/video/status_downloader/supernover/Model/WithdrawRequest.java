package com.video.status_downloader.supernover.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class WithdrawRequest {
    private String phoneNumber;
    private int amount;
    private String uid = FirebaseAuth.getInstance().getUid();
    private Map<String, String> createdAt = ServerValue.TIMESTAMP;
    private long timestamp;
    private long isDone = 0;

    public Map<String, String> getCreatedAt() {
        return createdAt;
    }

    public String getDate(){
        Date date = new Date(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return dateFormat.format(date);
    }

    public void setCreatedAt(Map<String, String> createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getIsDone() {
        return isDone;
    }

    public void setIsDone(long isDone) {
        this.isDone = isDone;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
