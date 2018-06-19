package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Program;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProgramViewActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference skatersCollectionDb;
    private DocumentReference programRef;

    //public List<Program> program;
    public List<Program> program = new ArrayList<>();

    //private List<Program> qProgram;
    //private static List<Program> qProgram = new ArrayList<>();

    public TextView mCompetitionNameTextView;
    public TextView mCompetitionDescriptionTextView;
    public TextView mElementIDO1TextView;
    public TextView mElementNameO1TextView;
    public TextView mElementBaseValueO1TextView;
    public TextView mElementIDO2TextView;
    public TextView mElementNameO2TextView;
    public TextView mElementBaseValueO2TextView;
    public String mCurrentUserID;
    public String mCurrentProgramID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_view);
        mCompetitionNameTextView = findViewById(R.id.textViewCompetition);
        mCompetitionDescriptionTextView = findViewById(R.id.textViewProgramDescription);
        mElementIDO1TextView = findViewById(R.id.elementRow01elementID);
        mElementNameO1TextView = findViewById(R.id.elementRow01elementName);
        mElementBaseValueO1TextView = findViewById(R.id.elementRow01elementBaseValue);
        mElementIDO2TextView = findViewById(R.id.elementRow02elementID);
        mElementNameO2TextView = findViewById(R.id.elementRow02elementName);
        mElementBaseValueO2TextView = findViewById(R.id.elementRow02elementBaseValue);

        //Get the userID and programID
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle.isEmpty()) {
            // deal with empty bundle
        } else {
            // get the info
            mCurrentUserID = extrasBundle.getString("userID");
            mCurrentProgramID = extrasBundle.getString("programID");
            //Toast.makeText(getApplicationContext(), mCurrentUserID + " " + mCurrentProgramID, Toast.LENGTH_LONG).show();
            init();
        }
        /*
        mCompetitionNameTextView.setText(program.get(0).getCompetition());
        String eString = program.get(0).getElements().toString();
        mCompetitionDescriptionTextView.setText(eString);
        */

        // Get specific program info
        //init();
    }

    // Need to setup on click for selecting a program

    // Initialize database and recyclerview
    public void init() {
        db = FirebaseFirestore.getInstance();
        //skatersCollectionDb = db.collection("Skaters");
        programRef = db.collection("Programs").document(mCurrentProgramID);
        programRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Program qProgram = documentSnapshot.toObject(Program.class);
                program.add(qProgram);
                mCompetitionNameTextView.setText(program.get(0).getCompetition());
                String eString = program.get(0).getElements().toString();
                mCompetitionDescriptionTextView.setText(eString);
//mCompetitionDescriptionTextView.setText(program.getLevel() + " " + program.getDiscipline() + " " + program.getSegment() + " Program");
                //String eString = Array.toString(program.getElements());
                //mCompetitionDescriptionTextView.setText.(program.getElements());
                /*
                mElementIDO1TextView.setText(program.getCompetition());
                mElementNameO1TextView.setText(program.getCompetition());
                mElementBaseValueO1TextView.setText(program.getCompetition());
                mElementIDO2TextView.setText(program.getCompetition());
                mElementIDO1TextView.setText(program.getCompetition());
                mElementIDO1TextView.setText(program.getCompetition());
                mElementIDO1TextView.setText(program.getCompetition());
                */
            }
                    //(program.getCompetition());
            //String eString = Array.toString(program.getElements());
            //mCompetitionDescriptionTextView.setText.(program.getElements());
        });

    }

    private void showData(){

    }
}
