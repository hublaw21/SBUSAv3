package com.example.khubbart.mysbusaappv3.Model;

public class Program {
    private String Competition;
    private String Discipline;
    private String Level;
    private String Segment;
    private String skaterUID; // This must be inserted at creation to associate it with the proper skater
    private String elements; // SHould I list seperately or do an array?

    // Required No Argument Constructor

    public Program() {
        this.Competition = "Competition";
        this.Discipline = "Discipline";
        this.Level = "Level";
        this.Segment = "Segnment";
        this.skaterUID = "skaterUID";
        this.elements = "elements";
    }

    public Program(String Competition, String Discipline, String Level, String Segment, String skaterUID, String elements) {
        this.Competition = Competition;
        this.Discipline = Discipline;
        this.Level = Level;
        this.Segment = Segment;
        this.skaterUID = skaterUID;
        this.elements = elements;
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
}

