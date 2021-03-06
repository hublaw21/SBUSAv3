package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Skater;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button buttonMain01;
    Button buttonMainProfile;
    Button buttonMainGenerateSampleData;
    Button buttonEditProgram;
    Button buttonScoreProgram;
    Button buttonMainLogOut;
    public TextView textViewSkaterName;
    public String mCurrentUserUID;
    public String mUID;
    public String mSkaterName;
    public String mSkaterID;
    private FirebaseFirestore db;
    private CollectionReference skatersCollectionDb;
    public Skater skater;
    public String mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMain01 = (Button) findViewById(R.id.buttonMain01);
        buttonMainProfile = (Button) findViewById(R.id.buttonMainProfile);
        //buttonMainGenerateSampleData = (Button) findViewById(R.id.buttonMainSDG);
        buttonEditProgram = (Button) findViewById(R.id.buttonEditProgram);
        //buttonScoreProgram = (Button) findViewById(R.id.buttonScoreProgram);
        buttonMainLogOut = (Button) findViewById(R.id.buttonMainLogOut);
        textViewSkaterName = findViewById(R.id.textViewSkaterName);

        gatherUserData();

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


        //Use for temporary button to go to add sample data activity
        /*
        buttonMainGenerateSampleData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(MainActivity.this, SampleDataGenerator.class);
                Intent myIntent = new Intent(MainActivity.this, TestArrayActivity.class);
                startActivity(myIntent);
            }
        });
        */

        /*
        buttonMainGenerateSampleData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ProgramScoringViewActivity.class);
                startActivity(myIntent);
            }
        });
        */

        buttonEditProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBundle = new Intent(MainActivity.this, ProgramSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID",mCurrentUserUID);
                intentBundle.putExtras(bundle);
                startActivity(intentBundle);
            }
        });

        /*
        buttonScoreProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBundle = new Intent(MainActivity.this, ProgramSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID",mCurrentUserUID);
                intentBundle.putExtras(bundle);
                startActivity(intentBundle);
            }
        });
        */


        buttonMainLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        // Get current userID - for fetching if using Global Class
        GlobalClass globalClass = ((GlobalClass)getApplicationContext());
        mCurrentUserUID = globalClass.getCurrentUserUID();
        mSkaterName = globalClass.getSkaterName();
        //Toast.makeText(this, "Name: " + mSkaterName, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Resumed - Name: " + mSkaterName + " UID: " + mCurrentUserUID, Toast.LENGTH_SHORT).show();

    }

    //Save name and basic data for Global Use
    public void gatherUserData(){
        final GlobalClass globalClass = ((GlobalClass)getApplicationContext());
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mUID = currentFirebaseUser.getUid();
        globalClass.setCurrentUserUID(mUID);  //Saves master User ID

        // Get and save the users core data
        db = FirebaseFirestore.getInstance();
        skatersCollectionDb = db.collection("Skaters");
        DocumentReference docRef = skatersCollectionDb.document(mUID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                skater = documentSnapshot.toObject(Skater.class);
                mName = skater.getName();
                globalClass.setSkaterName(mName);
                textViewSkaterName.setText(mName);
                //Get skaterID, crucial
                //mSkaterID = skater.getClub();
                //textViewSkaterName.setText(mSkaterID);
                //globalClass.setgSkaterID(mSkaterID);

            }
        });
        //Toast.makeText(this, "skaterID: " + mSkaterID + mName+mUID, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Login - Name: " + mName + " UID: " + mUID, Toast.LENGTH_SHORT).show();
    }
}
