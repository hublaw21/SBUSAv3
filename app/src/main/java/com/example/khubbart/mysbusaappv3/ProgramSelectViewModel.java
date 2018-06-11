package com.example.khubbart.mysbusaappv3;

import android.support.annotation.NonNull;

public class ProgramSelectViewModel {
    private String programSelectItemText;

    public ProgramSelectViewModel(@NonNull final String programSelectItemText){
        setProgramSelectItemText(programSelectItemText);
    }

    public String getProgramSelectItemText() {
        return programSelectItemText;
    }

    public void setProgramSelectItemText(String programSelectItemText) {
        this.programSelectItemText = programSelectItemText;
    }
}
