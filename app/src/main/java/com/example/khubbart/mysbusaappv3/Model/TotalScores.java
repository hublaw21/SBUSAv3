package com.example.khubbart.mysbusaappv3.Model;

public class TotalScores {
    private Double TotalScoreSegment;
    private Double TotalScoreElements;
    private Double TotalScoreComponents;
    private Double TotalScoreDeductions;

    //Required No Argument Constructor
    TotalScores(){
        this.TotalScoreSegment = 0.0;
        this.TotalScoreElements = 0.0;
        this.TotalScoreComponents = 0.0;
        this.TotalScoreDeductions = 0.0;
    }

    public TotalScores (Double totalScoreSegement, Double totalScoreElements, Double totalScoreComponents, Double totalScoreDeductions){
        this.TotalScoreSegment = totalScoreSegement;
        this.TotalScoreElements = totalScoreElements;
        this.TotalScoreComponents = totalScoreComponents;
        this.TotalScoreDeductions = totalScoreDeductions;
    }

    public Double getTotalScoreSegment() {
        return TotalScoreSegment;
    }

    public void setTotalScoreSegment(Double totalScoreSegment) {
        TotalScoreSegment = totalScoreSegment;
    }

    public Double getTotalScoreElements() {
        return TotalScoreElements;
    }

    public void setTotalScoreElements(Double totalScoreElements) {
        TotalScoreElements = totalScoreElements;
    }

    public Double getTotalScoreComponents() {
        return TotalScoreComponents;
    }

    public void setTotalScoreComponents(Double totalScoreComponents) {
        TotalScoreComponents = totalScoreComponents;
    }

    public Double getTotalScoreDeductions() {
        return TotalScoreDeductions;
    }

    public void setTotalScoreDeductions(Double totalScoreDeductions) {
        TotalScoreDeductions = totalScoreDeductions;
    }
}
