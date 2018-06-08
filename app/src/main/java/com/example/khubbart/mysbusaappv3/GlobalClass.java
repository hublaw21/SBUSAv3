package com.example.khubbart.mysbusaappv3;

import android.app.Application;

public class GlobalClass extends Application {

    //Upon login, establish the current users UID
    private String currentUserUID;
    public String getCurrentUserUID(){
        return currentUserUID;
    }
    public void setCurrentUserUID(String sCurrentUserUID){
        currentUserUID = sCurrentUserUID;
    }
}
