package com.example.khubbart.mysbusaappv3;

import android.app.Application;

public class GlobalClass extends Application {

    // Use this to set user information

    //Upon login, establish the current users UID
    private String currentUserUID;
    public String getCurrentUserUID(){
        return currentUserUID;
    }
    public void setCurrentUserUID(String sCurrentUserUID){
        currentUserUID = sCurrentUserUID;
    }
}
