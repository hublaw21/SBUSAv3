package com.example.khubbart.mysbusaappv3;

import android.app.Application;

public class GlobalClass extends Application {

    // Use this activity to save application wide data that rarely change, but is not always the same, such as user information

    //Upon login, establish the current user's UID
    private String currentUserUID;
    public String getCurrentUserUID(){
        return currentUserUID;
    }
    public void setCurrentUserUID(String sCurrentUserUID){
        currentUserUID = sCurrentUserUID;
    }

    //Skater/User data
    private String skaterName;
    public String getSkaterName(){
        return skaterName;
    }
    public void setSkaterName(String sSkaterName){
        skaterName = sSkaterName;
    }

}
