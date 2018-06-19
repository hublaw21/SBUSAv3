package com.example.khubbart.mysbusaappv3.Model;

public class Skater {
    private String Name;
    private String Club;
    private String Coach;
    private String email;
    private String userID;

    // Required No Argument Constructor

    public Skater(){
        this.Name = "Name";
        this.Club = "Club";
        this.Coach = "Coach";
        this.email = "email";
        this.userID = "userID";
    }

    public Skater(String Name, String Club, String Coach, String email, String userID) {
        this.Name = Name;
        this.Club = Club;
        this.Coach = Coach;
        this.email = email;
        this.userID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getClub() {
        return Club;
    }

    public void setClub(String club) {
        this.Club = club;
    }

    public String getCoach() {
        return Coach;
    }

    public void setCoach(String Coach) {
        this.Coach = Coach;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
