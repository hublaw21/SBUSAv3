package com.example.khubbart.mysbusaappv3;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Competition;
import com.example.khubbart.mysbusaappv3.Model.Program;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddProgramActivity extends AppCompatActivity
        implements SelectSkaterLevelFragment.OnChangeSkaterLevelRadioButtonInteractionListener,
        SelectDisciplineFragment.OnChangeDisciplineRadioButtonInteractionListener,
        SelectSegmentFragment.OnChangeSegmentRadioButtonInteractionListener,
        ButtonBarFragment.ButtonBarInteractionListener {

    public String tempCode;
    public String mProgramDescription;
    public String mCurrentUserUID;
    public String mSkaterName;
    public String mCompetitionName;
    public String[] CompetitionsList;
    //public Program mProgram;
    public int mCompetitionNameIndex;
    public TextView textViewSkaterName;
    public TextView mTextViewProgramDescription;
    //public TextView mTextViewGetData;
    public AutoCompleteTextView actv;
    public int[] programIndexes = new int[4]; // 0-competition, 1-discipline, 2-level, 3-segment
    public String[] programDescription = new String[4]; // 0-competition, 1-discipline, 2-level, 3-segment
    public String[] competitionName;
    public String[] discipline = new String[3];
    public String[] level = new String[5];
    public String[] segment = new String[2];
    private FirebaseFirestore db;
    private CollectionReference programRef;
    private CollectionReference elementRef;
    public String mCurrentProgramDocumentID;
    public String mCurrentElementDocumentID;
    public Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
        textViewSkaterName = findViewById(R.id.textViewProgramAddSkaterName);
        mTextViewProgramDescription = findViewById(R.id.textViewProgramAddDescription);
        //mTextViewGetData = findViewById(R.id.textViewGetData);
        db = FirebaseFirestore.getInstance();
        programRef = db.collection("Programs");
        elementRef = db.collection("PlannedProgramContent");

        //Get array data from resources.  May want to change to online
        Resources resources = getResources();
        competitionName = resources.getStringArray(R.array.competitionNamesArray);
        discipline = resources.getStringArray(R.array.disciplines);
        level = resources.getStringArray(R.array.levels);
        segment = resources.getStringArray(R.array.segments);

        //Create an instance of ArrayAdapter containing competition names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_competition_name, competitionName);

        //Get instance of Autocomplete
        actv = findViewById(R.id.autoCompleteCompetitionName);
        actv.setThreshold(1);  //Will start from int characters
        actv.setAdapter(adapter);
        actv.setTextColor(Color.BLACK);

        // Get current userID - for fetching if using Global Class
        GlobalClass globalClass = ((GlobalClass) getApplicationContext());
        mCurrentUserUID = globalClass.getCurrentUserUID();
        mSkaterName = globalClass.getSkaterName();
        textViewSkaterName.setText(mSkaterName);


    }

    // Get Skater Discipline
    @Override
    //public void onChangeDisciplineRadioButtonInteraction(String tempCode) {
    public void onChangeDisciplineRadioButtonInteraction(int tempIndex) {
        // Get skater discipline
        programIndexes[1] = tempIndex;
        programDescription[1] = discipline[tempIndex];
        makeProgramDescription();
    }

    // Get Skater Level
    @Override
    public void onChangeSkaterLevelRadioButtonInteraction(int tempIndex) {
        // Get skater level
        programIndexes[2] = tempIndex;
        programDescription[2] = level[tempIndex];
        makeProgramDescription();
    }

    // Get Skater Segment
    @Override
    public void onChangeSegmentRadioButtonInteraction(int tempIndex) {
        // Get skater level
        programIndexes[3] = tempIndex;
        programDescription[3] = segment[tempIndex];
        makeProgramDescription();
    }

    // Listen for Button Bar
    @Override
    public String ButtonBarInteraction(String tempCodeReturned) {
        // Act of returned code
        //Toast.makeText(getApplicationContext(), "Button Bar Retrun: " + tempCode2, Toast.LENGTH_LONG).show();
        //mTextViewGetData.setText(tempCode2);
        switch (tempCodeReturned) {
            case "Cancel":
                //cancel and return
                finish();
                break;

            case "Maybe":
                //Probably hide this one, or make it return without save
                break;

            case "OK":
                //Save
                addProgram();
                break;

        }
        return tempCodeReturned;
    }

    //Make the description string
    public void makeProgramDescription() {
        mProgramDescription = "";
        mProgramDescription += level[programIndexes[2]] + " ";
        mProgramDescription += discipline[programIndexes[1]] + " ";
        mProgramDescription += segment[programIndexes[3]];
        if (mProgramDescription != null) {
            mTextViewProgramDescription.setText(mProgramDescription);
        } else {
            mTextViewProgramDescription.setText("Empty");
        }

    }

    //Method to add/update program
    private void addProgram() {
        //Need to verify an event has been selected
        mCompetitionName = actv.getText().toString();
        //Check to be sure competition has been elected
        mCompetitionNameIndex = mCompetitionName.length();
        //Toast.makeText(AddProgramActivity.this, "Length: " + mCompetitionNameIndex, Toast.LENGTH_SHORT).show();
        if (mCompetitionNameIndex >0){
            //Check to verify one of the actual comps, not just letters
            Toast.makeText(AddProgramActivity.this, "??Length: " + mCompetitionNameIndex, Toast.LENGTH_SHORT).show();
        } else {
            mCompetitionName = "Template"; // Eventually,point to the temaplte in the competitions collection
        }
        //Toast.makeText(AddProgramActivity.this, "Event Name imported: " + mCompetitionName, Toast.LENGTH_SHORT).show();
        //Create a blank element document so we can pull id to put into
        //if(mCompetitionNameIndex > 0) {
        //if(mCompetitionNameIndex == -1) mCompetitionName = "Template";
        final Map<String, Object> mProgram = new HashMap<>();
        mProgram.put("Competition", mCompetitionName);
        mProgram.put("Discipline", discipline[programIndexes[1]]);
        mProgram.put("Level", level[programIndexes[2]]);
        mProgram.put("Segment", segment[programIndexes[3]]);
        mProgram.put("elementsID", null);
        mProgram.put("userID", mCurrentUserUID);
        //programRef.document()
        //        .set(mProgram)
        programRef.add(mProgram)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Need to also set up an element document to avoid errors, but not until we can get the document ID for the new program
                        mCurrentProgramDocumentID = documentReference.getId();
                        Map<String, Object> mElement = new HashMap<>();
                        //mElement.put();
                        mElement.put("E00", null);
                        mElement.put("E01", null);
                        mElement.put("E02", null);
                        mElement.put("E03", null);
                        mElement.put("E04", null);
                        mElement.put("E05", null);
                        mElement.put("E06", null);
                        mElement.put("E07", null);
                        mElement.put("E08", null);
                        mElement.put("E09", null);
                        mElement.put("E10", null);
                        mElement.put("E11", null);
                        mElement.put("E12", null);
                        mElement.put("ProgramDocumentID", mCurrentProgramDocumentID);
                        elementRef.add(mElement)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                // Need to put element document reference into program document
                                @Override
                                public void onSuccess(DocumentReference elementDocumentReference) {
                                    mCurrentElementDocumentID = elementDocumentReference.getId();
                                    mProgram.put("elementsID", mCurrentElementDocumentID);
                                    programRef.document(mCurrentProgramDocumentID).update("elementsID", mCurrentElementDocumentID);
                                    //Toast.makeText(AddProgramActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    }
                });


                        Toast.makeText(AddProgramActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    }
                });

        //} else {
        //Toast.makeText(AddProgramActivity.this, "Unsuccessful - Select an Event", Toast.LENGTH_SHORT).show();
        //}

    }


}
