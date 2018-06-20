package com.example.khubbart.mysbusaappv3.Model;

import com.google.firebase.firestore.Exclude;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Program {
    private String Competition;
    private String Discipline;
    private String Level;
    private String Segment;
    private String userID; // This must be inserted at creation to associate it with the proper skater
    private String elementsID; // Until Firestore supports subcolelction arrays, need to make this the id to the seperate collection
    //private String[] elements;
    //private Array elements;
    //private Array eTemp;
    @Exclude private String documentID;// The id for referencing the program document that should not be saved with it

    // Required No Argument Constructor

    public Program() {
        //String[] eTemp = new String[] {"E1", "E2"};
        //eTemp.add(0) = "E1";
        this.Competition = "Competition";
        this.Discipline = "Discipline";
        this.Level = "Level";
        this.Segment = "Segment";
        this.userID = "userID";
        this.elementsID = null;
        this.documentID = "Doc ID";

    }

    public Program(String Competition, String Discipline, String Level, String Segment, String userID, String elementsID, String documentID) {
        this.Competition = Competition;
        this.Discipline = Discipline;
        this.Level = Level;
        this.Segment = Segment;
        this.userID = userID;
        this.elementsID = elementsID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getElementsID() {
        return elementsID;
    }

    public void setElementsID(String elementsID) {
        this.elementsID = elementsID;
    }

    public String getDocumentID() {return documentID;}

    public void setDocumentID(String documentID) {this.documentID = documentID;}
}

