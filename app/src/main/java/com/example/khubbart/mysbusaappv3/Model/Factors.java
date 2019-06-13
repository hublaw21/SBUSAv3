package com.example.khubbart.mysbusaappv3.Model;

public class Factors {

    private String FactorID;
    private Double SegmentFactor;
    private Double SkatingSkillsFactor;
    private Double TransitionsFactor;
    private Double PerformanceFactor;
    private Double ChoreoFactor;
    private Double InterpretationFactor;
    private Double GeneralComponentFactor;
    private Double FallPenalty;

    // Required No Argument Constructor

    //Required No Argument Constructor
    Factors() {
        this.FactorID ="";
        this.SegmentFactor = 0.00;
        this.SkatingSkillsFactor = 0.00;
        this.TransitionsFactor = 0.00;
        this.PerformanceFactor = 0.00;
        this.ChoreoFactor = 0.00;
        this.InterpretationFactor = 0.00;
        this.GeneralComponentFactor = 0.00;
        this.FallPenalty = 0.00;
    }

    public Factors(String factorID, Double segmentFactor, Double skatingSkillsFactor, Double transitionsFactor, Double performanceFactor, Double choreoFactor, Double interpretationFactor, Double generalComponentFactor, Double fallPenalty) {
        FactorID = factorID;
        SegmentFactor = segmentFactor;
        SkatingSkillsFactor = skatingSkillsFactor;
        TransitionsFactor = transitionsFactor;
        PerformanceFactor = performanceFactor;
        ChoreoFactor = choreoFactor;
        InterpretationFactor = interpretationFactor;
        GeneralComponentFactor = generalComponentFactor;
        FallPenalty = fallPenalty;
    }

    public String getFactorID() {
        return FactorID;
    }

    public void setFactorID(String factorID) {
        FactorID = factorID;
    }

    public Double getSegmentFactor() {
        return SegmentFactor;
    }

    public void setSegmentFactor(Double segmentFactor) {
        SegmentFactor = segmentFactor;
    }

    public Double getSkatingSkillsFactor() {
        return SkatingSkillsFactor;
    }

    public void setSkatingSkillsFactor(Double skatingSkillsFactor) {
        SkatingSkillsFactor = skatingSkillsFactor;
    }

    public Double getTransitionsFactor() {
        return TransitionsFactor;
    }

    public void setTransitionsFactor(Double transitionsFactor) {
        TransitionsFactor = transitionsFactor;
    }

    public Double getPerformanceFactor() {
        return PerformanceFactor;
    }

    public void setPerformanceFactor(Double performanceFactor) {
        PerformanceFactor = performanceFactor;
    }

    public Double getChoreoFactor() {
        return ChoreoFactor;
    }

    public void setChoreoFactor(Double choreoFactor) {
        ChoreoFactor = choreoFactor;
    }

    public Double getInterpretationFactor() {
        return InterpretationFactor;
    }

    public void setInterpretationFactor(Double interpretationFactor) {
        InterpretationFactor = interpretationFactor;
    }

    public Double getGeneralComponentFactor() {
        return GeneralComponentFactor;
    }

    public void setGeneralComponentFactor(Double generalComponentFactor) {
        GeneralComponentFactor = generalComponentFactor;
    }

    public Double getFallPenalty() {
        return FallPenalty;
    }

    public void setFallPenalty(Double fallPenalty) {
        FallPenalty = fallPenalty;
    }
}