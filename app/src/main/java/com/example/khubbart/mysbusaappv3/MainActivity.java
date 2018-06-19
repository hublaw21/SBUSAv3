package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button buttonMain01;
    Button buttonMainProfile;
    Button buttonMainGenerateSampleData;
    Button buttonMainProgramSelect;
    Button buttonMainLogOut;
    public String mCurrentUserUID;
    public String skaterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMain01 = (Button) findViewById(R.id.buttonMain01);
        buttonMainProfile = (Button) findViewById(R.id.buttonMainProfile);
        //buttonMainGenerateSampleData = (Button) findViewById(R.id.buttonMainSDG);
        buttonMainProgramSelect = (Button) findViewById(R.id.buttonMainProgramSelect);
        buttonMainLogOut = (Button) findViewById(R.id.buttonMainLogOut);

        // Retreive and set into variable the current users firebase UID
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserUID = currentFirebaseUser.getUid();
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        globalClass.setCurrentUserUID(mCurrentUserUID);
        //Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        buttonMain01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ElementLookupActivity.class);
                startActivity(myIntent);
            }
        });

        buttonMainProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBundle = new Intent(MainActivity.this, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID",mCurrentUserUID);
                intentBundle.putExtras(bundle);
                startActivity(intentBundle);
            }
        });

        /*
        //Use for temporary button to go to add sample data activity
        buttonMainGenerateSampleData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SampleDataGenerator.class);
                startActivity(myIntent);
            }
        });
        */

        buttonMainProgramSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBundle = new Intent(MainActivity.this, ProgramSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID",mCurrentUserUID);
                intentBundle.putExtras(bundle);
                startActivity(intentBundle);
            }
        });


        buttonMainLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });

    }

}
