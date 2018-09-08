package com.example.khubbart.mysbusaappv3.ViewModels;

import android.arch.lifecycle.ViewModel;
import com.example.khubbart.mysbusaappv3.Model.TotalScores;

public class TotalScoresViewModel extends ViewModel {

    private TotalScores totalScores;
    private Double totalScoreSegment;
    private Double totalScoreElements;
    private Double totalScoreComponents;
    private Double totalScoreDeductions;

    public TotalScores getTotalScore() {
        return totalScores;
    }

    public void setTotalScores(Double totalScoreSegement, Double totalScoreElements, Double totalScoreComponents, Double totalScoreDeductions){
        this.totalScoreSegment = totalScoreSegement;
        this.totalScoreElements = totalScoreElements;
        this.totalScoreComponents = totalScoreComponents;
        this.totalScoreDeductions = totalScoreDeductions;
    }
}