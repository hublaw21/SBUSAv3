package com.example.khubbart.mysbusaappv3.Model;

public class Competition {
    private String Name;
    private String Location;
    private String Date;
    private String Host;

    // Required No Argument Constructor

    public Competition(){
        this.Name = "Name";
        this.Location = "Location";
        this.Date = "Date";
        this.Host = "Host";
    }

    public Competition(String Name, String Location, String Date, String Host) {
        this.Name = Name;
        this.Location = Location;
        this.Date = Date;
        this.Host = Host;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }
}
