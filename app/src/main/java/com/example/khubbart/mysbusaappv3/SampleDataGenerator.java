package com.example.khubbart.mysbusaappv3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.khubbart.mysbusaappv3.Model.Program;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SampleDataGenerator extends AppCompatActivity {

    private FirebaseFirestore db;

    public String mCompetition;
    public String mDiscipline;
    public String mLevel;
    public String mSegment;
    public String mSkaterUID; // This must be inserted at creation to associate it with the proper skater
    public String melements; // Should I list seperately or do an array?

    public TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_data_generator);
        textViewStatus = findViewById(R.id.textViewSDG);


        Map<String, Object>newProgram = new HashMap<>();
        db = FirebaseFirestore.getInstance();
        int i = 2;
        int max = 7;

        while (i < max){
            i++;
            mCompetition = "Competition #" + i;
            newProgram.put("Competition", mCompetition);
            newProgram.put("skaterUID","tmFYMjtTPLTL0z0vQPHIZgUldvK2");
            db.collection("Programs").document().set(newProgram);
            textViewStatus.setText(mCompetition);
        }

    }
}
