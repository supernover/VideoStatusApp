package com.video.status_downloader.supernover.Model;

public class UserInformation {

    String email;
    String password;
    String number;
    String name;
    int rupees;
    int points;

    public UserInformation() {
    }

    public UserInformation(String email, String password, String number, String name,int points) {
        this.email = email;
        this.name = name;
        this.number = number;

      //  this.rupees = rupees;
        this.password = password;
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



   // public int getRupees() {
   //     return rupees;
  //  }

  //  public void setRupees(int rupees) {
     //   this.rupees = rupees;
   // }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
