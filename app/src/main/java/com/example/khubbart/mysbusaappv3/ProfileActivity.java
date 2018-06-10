package com.example.khubbart.mysbusaappv3;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentChange.Type;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

import static io.fabric.sdk.android.Fabric.TAG;

public class ProfileActivity extends Activity {

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
        final GlobalClass globalClass = (GlobalClass)getApplicationContext();
        final String mCurrentUserUID = globalClass.getCurrentUserUID();

        mTextViewName = findViewById(R.id.textViewName);
        mTextViewName.setText(mCurrentUserUID);
        mTextViewClub = findViewById(R.id.textViewClub);
        mTextViewCoach = findViewById(R.id.textViewCoach);
        mTextVieweMail = findViewById(R.id.textVieweMail);

        // try to pull in User data
        Query query = crDb.whereEqualTo("skaterUID", mCurrentUserUID);
        query.get().addOnCompleteListener(<QuerySnapshot>);
        if (querySnapshot.empty) {
                            List<skaters> skatersList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                Skaters s = doc.toObject(Skater.class);
                                s.setId(doc.getId());
                                eventList.add(e);
                            }
                            //do something with list of pojos retrieved

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });
    }

    private void ReadUserProfileData(){
        CollectionReference user = db.collection("")
    }

}
