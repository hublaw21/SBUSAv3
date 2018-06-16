package com.example.khubbart.mysbusaappv3.Model;

import com.google.firebase.firestore.Exclude;

public class Program {
    private String Competition;
    private String Discipline;
    private String Level;
    private String Segment;
    private String skaterUID; // This must be inserted at creation to associate it with the proper skater
    private String elements; // SHould I list seperately or do an array?
    @Exclude private String documentID;// The id for referencing the program document that should not be saved with it

    // Required No Argument Constructor

    public Program() {
        this.Competition = "Competition";
        this.Discipline = "Discipline";
        this.Level = "Level";
        this.Segment = "Segnment";
        this.skaterUID = "skaterUID";
        this.elements = "elements";
        this.documentID = "Doc ID";

    }

    public Program(String Competition, String Discipline, String Level, String Segment, String skaterUID, String elements, String documentID) {
        this.Competition = Competition;
        this.Discipline = Discipline;
        this.Level = Level;
        this.Segment = Segment;
        this.skaterUID = skaterUID;
        this.elements = elements;
        this.documentID = documentID;
    }

    public String getCompetition() {
        return Competition;
    }

    public void setCompetition(String competition) {
        Competition = competition;
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

    public String getSkaterUID() {
        return skaterUID;
    }

    public void setSkaterUID(String skaterUID) {
        this.skaterUID = skaterUID;
    }

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }

    public String getDocumentID() {return documentID;}

    public void setDocumentID(String documentID) {this.documentID = documentID;}
}

