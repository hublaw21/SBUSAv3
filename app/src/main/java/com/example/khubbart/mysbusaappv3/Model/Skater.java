package com.example.khubbart.mysbusaappv3.Model;

public class Skater {
    private String Name;
    private String Club;
    private String Coach;
    private String email;
    private String skaterUID;

    // Required No Argument Constructor

    public Skater(){
        this.Name = "Name";
        this.Club = "Club";
        this.Coach = "Coach";
        this.email = "email";
        this.skaterUID = "skaterUID";
    }

    public Skater(String Name, String Club, String Coach, String email, String skaterUID) {
        this.Name = Name;
        this.Club = Club;
        this.Coach = Coach;
        this.email = email;
        this.skaterUID = skaterUID;
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

    public String getSkaterUID() {
        return skaterUID;
    }

    public void setSkaterUID(String skaterUID) {
        this.skaterUID = skaterUID;
    }
}
