package com.video.status_downloader.supernover.Model;

public class RequestPayment {
    String name;
    String email;
    String number;
    String paytmnumber;
    String amount;
    String date;
    int balance;

    public RequestPayment() {
    }

    public RequestPayment(String name, String email, String number, String paytmnumber, String amount, String date, int balance) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.paytmnumber = paytmnumber;
        this.amount = amount;
        this.date = date;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPaytmnumber() {
        return paytmnumber;
    }

    public void setPaytmnumber(String paytmnumber) {
        this.paytmnumber = paytmnumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
