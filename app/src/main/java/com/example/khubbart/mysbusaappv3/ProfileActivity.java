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

public class ProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference collectionReference;

    public TextView mTextViewName;
    public TextView mTextViewClub;
    public TextView mTextViewCoach;
    public TextView mTextVieweMail;
    public String mCurrentUserUID;
    public String mUID;
    private static List<Skater> skaterIDList = new ArrayList<>();
    public String skaterID;
    public String mSkaterName;
    public int skaterCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mTextViewName = findViewById(R.id.textViewName);
        mTextViewClub = findViewById(R.id.textViewClub);
        mTextViewCoach = findViewById(R.id.textViewCoach);
        mTextVieweMail = findViewById(R.id.textVieweMail);

        // Get current userID - for fetching if using Global Class
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Skaters");
        GlobalClass globalClass = ((GlobalClass)getApplicationContext());
        mCurrentUserUID = globalClass.getCurrentUserUID();
        mSkaterName = globalClass.getSkaterName();
        pullSkaterInfo(mCurrentUserUID);
    }

    // Pull in User data
    // This works for pulling document date when you kow the name
    private void pullSkaterInfo(String mUID) {
        DocumentReference skaterInfoRef = collectionReference.document(mUID);
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
    }
}

