package com.example.khubbart.mysbusaappv3;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class ProfileActivity extends Activity {

    public TextView textViewUserID;
    // public String mCurrentUserUID = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Get current user UID
        final GlobalClass globalClass = (GlobalClass)getApplicationContext();
        final String mCurrentUserUID = globalClass.getCurrentUserUID();

        textViewUserID = findViewById(R.id.textViewFirstName);

        textViewUserID.setText(mCurrentUserUID);
    }

}
