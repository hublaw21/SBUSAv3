package com.example.khubbart.mysbusaappv3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.khubbart.mysbusaappv3.Model.Skater;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.FinalizablePhantomReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;

public class ProfileActivity extends AppCompatActivity {

    //private DatabaseReference db;
    private FirebaseFirestore db;
    private CollectionReference crDb;

    public TextView mTextViewName;
    public TextView mTextViewClub;
    public TextView mTextViewCoach;
    public TextView mTextVieweMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = FirebaseFirestore.getInstance();
        crDb = db.collection("Skaters");
        // Skaters skaters = null;  create a model for the data and use it throughout the project

        // Get current user UID
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        final String mCurrentUserUID = globalClass.getCurrentUserUID();

        mTextViewName = findViewById(R.id.textViewName);
        mTextViewName.setText(mCurrentUserUID);
        mTextViewClub = findViewById(R.id.textViewClub);
        mTextViewCoach = findViewById(R.id.textViewCoach);
        mTextVieweMail = findViewById(R.id.textVieweMail);

        mTextViewName.setText(mCurrentUserUID);

        // try to pull in User data
        /* This works for pulling document date when you kow the name
        DocumentReference user = crDb.document("cXkIdHMfu7TpX8GpUIBy");
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    Skater skater = null;
                    if (doc.exists()) {
                        skater = doc.toObject(Skater.class);
                        mTextViewName.setText(skater.getName());
                        mTextViewClub.setText(skater.getClub());
                        mTextViewCoach.setText(skater.getCoach());
                        mTextVieweMail.setText(skater.getEmail());
                    } else {
                        mTextViewName.setText("Not Found");
                    }
                }
            }
        });
        */
        // Try to query for this users userUID in ther skatrer profile
        Query query = crDb.whereEqualTo("skaterUID", mCurrentUserUID);
        DocumentReference user = crDb.document(query.get());
        //query.get().then(function(querySnapshot) {
        //DocumentReference user = query.get();

        // It will be easier to 'set' each new data entry to the skaterUID and then use it as the overall identifier for all related items, except how do I do multiple programs?

        // Works, don't touch
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    Skater skater = null;
                    if (doc.exists()) {
                        skater = doc.toObject(Skater.class);
                        mTextViewName.setText(skater.getName());
                        mTextViewClub.setText(skater.getClub());
                        mTextViewCoach.setText(skater.getCoach());
                        mTextVieweMail.setText(skater.getEmail());
                    } else {
                        mTextViewName.setText("Not Found");
                    }
                }
            }
        });

    }
    /*
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot doc = task.getResult();
            Skater skater = null;
            if (doc.exists()) {
                skater = doc.toObject(Skater.class);
                mTextViewName.setText(skater.getName());
                mTextViewClub.setText(skater.getClub());
            } else {
                mTextViewName.setText("Not Found");
            }
        }
    }
    */
}

