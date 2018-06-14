package com.example.khubbart.mysbusaappv3;

import android.support.annotation.NonNull;

public class ProgramSelectViewModel {
    //private String programSelectItemText;
    private String programSelectCompetitionText;
    private String programSelectDisciplineText;
    private String programSelectLevelText;
    private String programSelectSegmentText;

    public ProgramSelectViewModel(@NonNull String programSelectCompetitionText, String programSelectDisciplineText, String programSelectLevelText, String programSelectSegmentText){
    //public ProgramSelectViewModel(@NonNull final String programSelectItemText, String programSelectCompetitionText, String programSelectDisciplineText, String programSelectLevelText, String programSelectSegmentText){
            //setProgramSelectItemText(programSelectItemText);
        setProgramSelectCompetitionText(programSelectCompetitionText);
        setProgramSelectDisciplineText(programSelectDisciplineText);
        setProgramSelectLevelText(programSelectLevelText);
        setProgramSelectSegmentText(programSelectSegmentText);
    }

    /*
    public String getProgramSelectItemText() {
        return programSelectItemText;
    }

    public void setProgramSelectItemText(String programSelectItemText) {
        this.programSelectItemText = programSelectItemText;
    }
    */

    public String getProgramSelectCompetitionText() {
        return programSelectCompetitionText;
    }

    public void setProgramSelectCompetitionText(String programSelectCompetitionText) {
        this.programSelectCompetitionText = programSelectCompetitionText;
    }

    public String getProgramSelectDisciplineText() {
        return programSelectDisciplineText;
    }

    public void setProgramSelectDisciplineText(String programSelectDisciplineText) {
        this.programSelectDisciplineText = programSelectDisciplineText;
    }
    public String getProgramSelectLevelText() {
        return programSelectLevelText;
    }

    public void setProgramSelectLevelText(String programSelectLevelText) {
        this.programSelectLevelText = programSelectLevelText;
    }

    public String getProgramSelectSegmentText() {
        return programSelectSegmentText;
    }

    public void setProgramSelectSegmentText(String programSelectSegmentText) {
        this.programSelectSegmentText = programSelectSegmentText;
    }
}
