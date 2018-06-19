package com.example.khubbart.mysbusaappv3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Skater;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.List;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;

public class ProfileActivity extends AppCompatActivity {

    //private DatabaseReference db;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;

    public TextView mTextViewName;
    public TextView mTextViewClub;
    public TextView mTextViewCoach;
    public TextView mTextVieweMail;
    public String mCurrentUserUID;
    private static List<Skater> skaterIDList = new ArrayList<>();
    public String skaterID;
    public int skaterCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mTextViewName = findViewById(R.id.textViewName);
        mTextViewClub = findViewById(R.id.textViewClub);
        mTextViewCoach = findViewById(R.id.textViewCoach);
        mTextVieweMail = findViewById(R.id.textVieweMail);

        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Skaters");

        // Get current userID - for fetching if using Global Class
        /*
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        final String mCurrentUserUID = globalClass.getCurrentUserUID();
        */

        //Get the userID
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle.isEmpty()) {
            // deal with empty bundle, we shouldn't get here.
        } else {
            // get the UID
            mCurrentUserUID = extrasBundle.getString("userID");
        }

        // Find and load the skater's info
        findSkaterID();
    }

        /*
        Query query = collectionReference.whereEqualTo("userID", mCurrentUserUID);
        DocumentReference user = collectionReference.document(query.get());
        //query.get().then(function(querySnapshot) {
        //DocumentReference user = query.get();

        // It will be easier to 'set' each new data entry to the userID and then use it as the overall identifier for all related items, except how do I do multiple programs?

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

        */

    // Get skaterID based on userID
    private void findSkaterID() {
        collectionReference.whereEqualTo("userID", mCurrentUserUID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            // Handle empty
                            Toast.makeText(getApplicationContext(), "Error getting data but Success!!!", Toast.LENGTH_LONG).show();
                        } else {
                            skaterCount = documentSnapshots.size();//Should only return 1 skater, double checking
                            if (skaterCount == 1) {
                                // Capture Skater ID - still have to cycle even for one
                                // REMEMBER userID != skaterID
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    Skater skatersTemp = documentSnapshot.toObject(Skater.class);
                                    skatersTemp.setUserID(documentSnapshot.getId()); //We are capturing the id of the skater document, but storing it very temporarily like the userID
                                    skaterIDList.add(skatersTemp);
                                }
                                skaterID = skaterIDList.get(0).getUserID(); //
                                //skaterID = skaterIDList.get(0).toString();
                                //Toast.makeText(getApplicationContext(), "Skater ID: " + skaterID, Toast.LENGTH_LONG).show();
                                //Toast.makeText(getApplicationContext(), "Skater ID: " + skaterID, Toast.LENGTH_LONG).show();
                                pullSkaterInfo();
                            } else {
                                //Problem - Too many skaters returned
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Pull in User data
    // This works for pulling document date when you kow the name
    private void pullSkaterInfo() {
        DocumentReference skaterInfoRef = collectionReference.document(skaterID);
        skaterInfoRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
}

