package com.example.khubbart.mysbusaappv3.Model;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Programv2 {
    private String Discipline;
    private String Level;
    private String Segment;
    private String UserID;
    private String ProgramDescription;
    private List<String> Elements; // Until Firestore supports subcollection arrays, need to make this the id to the seperate collection

    // Required No Argument Constructor

    public Programv2() {
        this.Discipline = "Discipline";
        this.Level = "Level";
        this.Segment = "Segment";
        this.UserID = "UserID";
        this.ProgramDescription=null;
        this.Elements = null;

    }

    public Programv2(String userID, String programDescription, String Discipline, String Level, String Segment, List<String> elements) {
        this.Discipline = Discipline;
        this.Level = Level;
        this.Segment = Segment;
        this.Elements = elements;
        this.ProgramDescription = programDescription;
        this.UserID = userID;
    }

    public String getDiscipline() {
        return Discipline;
    }

    public void setDiscipline(String discipline) {
        Discipline = discipline;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getSegment() {
        return Segment;
    }

    public void setSegment(String segment) {
        Segment = segment;
    }

    public List<String> getElements() {
        return Elements;
    }

    public void setElements(List<String> elements) {
        Elements = elements;
    }

    public String getUserID() {return UserID;}

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getProgramDescription() {
        return ProgramDescription;
    }

    public void setProgramDescription(String programDescription) {
        ProgramDescription = programDescription;
    }
}

