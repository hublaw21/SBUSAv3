package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Competition;
import com.example.khubbart.mysbusaappv3.Model.Program;
import com.example.khubbart.mysbusaappv3.Model.Programv2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddProgramActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    Button buttonSaveProgram;
    public String tempCode;
    public String tempString;
    public String selectedItem;
    public String selectedCompetition;
    public String selectedProgram;
    public String selectedDiscipline;
    public String selectedLevel;
    public String selectedSegment;
    public String currentProgramDescription;
    public String programID;
    public String currentUserUID;
    public String currentProgramID;
    public String skaterID;
    public String mSkaterName;
    public String mCompetitionName;
    public String[] CompetitionsList;
    //public Program mProgram;
    public int mCompetitionNameIndex;
    public TextView textViewSkaterName;
    public TextView mTextViewProgramDescription;
    public EditText editTextProgramDescriptionView;
    //public TextView mTextViewGetData;
    public AutoCompleteTextView actv;
    public int[] programIndexes = new int[4]; // 0-competition, 1-discipline, 2-level, 3-segment
    public String[] programDescription = new String[4]; // 0-competition, 1-discipline, 2-level, 3-segment
    public String[] competitionName;
    public String[] discipline = new String[3];
    public String[] level = new String[5];
    public String[] segment = new String[2];
    public String[] programName = new String[8];
    private FirebaseFirestore db;
    private CollectionReference programRef;
    private CollectionReference elementRef;
    public String mCurrentProgramDocumentID;
    public String mCurrentElementDocumentID;
    public Resources resources;
    public RadioGroup mRGDiscipline;
    public Intent myIntent;
    public GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);

        //Set up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarAddProgram);
        setSupportActionBar(myToolbar);

        buttonSaveProgram = findViewById(R.id.buttonSaveProgram);

        textViewSkaterName = findViewById(R.id.textViewProgramAddSkaterName);
        editTextProgramDescriptionView = findViewById(R.id.editTextProgramDescription);
        //Do I need this or just do it when I need to pull it?
        editTextProgramDescriptionView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                currentProgramDescription = editTextProgramDescriptionView.getText().toString();
                return false;
            }
        });

        //mTextViewProgramDescription = findViewById(R.id.textViewProgramAddDescription);
        //mTextViewGetData = findViewById(R.id.textViewGetData);
        globalClass = ((GlobalClass) getApplicationContext());

        db = FirebaseFirestore.getInstance();
        programRef = db.collection("Programs");
        //elementRef = db.collection("PlannedProgramContent");

        //Get array data from resources.  May want to change to online
        Resources resources = getResources();
        //competitionName = resources.getStringArray(R.array.competitionNamesArray);
        discipline = resources.getStringArray(R.array.disciplines);
        selectedDiscipline = discipline[0];
        level = resources.getStringArray(R.array.levels);
        selectedLevel = level[0];
        segment = resources.getStringArray(R.array.segments);
        selectedSegment = segment[0];
        //programName = resources.getStringArray(R.array.programNames);
        //selectedProgram = programName[0];
        currentProgramDescription = "New";

        //Create an instance of ArrayAdapter containing competition names
        /* For now, don't worry about names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_competition_name, competitionName);
        */
        // Set Spinners
        Spinner spinnerDiscipline = findViewById(R.id.spinnerDiscipline);
        spinnerDiscipline.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerDisciplineAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, discipline); // Creating adapter for spinner
        spinnerDisciplineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Drop down layout style - list view with radio button
        spinnerDiscipline.setAdapter(spinnerDisciplineAdapter); // attaching data adapter to spinner

        Spinner spinnerLevel = findViewById(R.id.spinnerLevel);
        spinnerLevel.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerLevelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, level); // Creating adapter for spinner
        spinnerLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Drop down layout style - list view with radio button
        spinnerLevel.setAdapter(spinnerLevelAdapter); // attaching data adapter to spinner

        Spinner spinnerSegment = findViewById(R.id.spinnerSegment);
        spinnerSegment.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerSegmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, segment); // Creating adapter for spinner
        spinnerSegmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Drop down layout style - list view with radio button
        spinnerSegment.setAdapter(spinnerSegmentAdapter); // attaching data adapter to spinner

        /*
        Spinner spinnerProgramName = findViewById(R.id.spinnerProgramName);
        spinnerSegment.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerProgramAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, programName); // Creating adapter for spinner
        spinnerSegmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Drop down layout style - list view with radio button
        spinnerProgramName.setAdapter(spinnerProgramAdapter); // attaching data adapter to spinner
        */

        //Get instance of Autocomplete
        /*
        actv = findViewById(R.id.autoCompleteCompetitionName);
        actv.setThreshold(1);  //Will start from int characters
        actv.setAdapter(adapter);
        actv.setTextColor(Color.BLACK);
        */
        // Get current userID - for fetching if using Global Class
        GlobalClass globalClass = ((GlobalClass) getApplicationContext());
        currentUserUID = globalClass.getCurrentUserUID();
        //currentProgramID = globalClass.getCurrentProgramID();
        mSkaterName = globalClass.getSkaterName();
        textViewSkaterName.setText(mSkaterName);

        // Set up listener for Discipline Radio Button
        /*
        mRGDiscipline = view.findViewById(R.id.radioGroupDisciplines);

        mRGDiscipline.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = mRGDiscipline.getCheckedRadioButtonId();
                onRadioButtonChanged(selectedId);  //I only need this for manipulations before returning info
            }
        });
        */
        buttonSaveProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do what you need to do to save it
                //check for a name
                if (currentProgramDescription.length() < 5) {
                    //How about a longer name
                    Toast.makeText(AddProgramActivity.this, "That's kind of a short description, let's add a little to it.", Toast.LENGTH_LONG).show();
                } else {
                    saveProgram();
                }
            }
        });

    }

    // Get Skater Discipline
    /*
    @Override
    //public void onChangeDisciplineRadioButtonInteraction(String tempCode) {
    public void onChangeDisciplineRadioButtonInteraction(int tempIndex) {
        // Get skater discipline
        programIndexes[1] = tempIndex;
        programDescription[1] = discipline[tempIndex];
        makeProgramDescription();
    }
    */
    @Override
    public void onItemSelected(AdapterView<?> spinnerUsed, View view, int position, long id) {
        // On selecting a spinner item, set value to string, as we will be using the text
        selectedItem = spinnerUsed.getItemAtPosition(position).toString();

        // Assign to the proper variable for naming convention
        switch (spinnerUsed.getId()) {
            case R.id.spinnerDiscipline:
                selectedDiscipline = selectedItem;
                break;
            case R.id.spinnerLevel:
                selectedLevel = selectedItem;
                break;
            case R.id.spinnerSegment:
                selectedSegment = selectedItem;
                break;
            //case R.id.spinnerProgramName:
            //selectedProgram = selectedItem;
            //    break;
        }
        // Showing selected spinner item
        //Toast.makeText(spinnerUsed.getContext(), "Selected: " + selectedLevel + " " + selectedDiscipline+ " " + selectedSegment, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    /*
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
    */
    /*
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
    */
    // Method to add all in one prgram with inegrated id
    private void saveProgram() {
        //Test to be sure all fields entered
        //Generate name using naming convention of USerID + Program # + level + discipline + segment
        //currentProgramID = currentUserUID+"Program "+currentProgramID.substring(3)+" "+selectedLevel+" " + selectedDiscipline+" "+selectedSegment;
        //Set programID
        /* Only use if I am including the compeition name
        selectedCompetition = actv.getText().toString();
        programID = mCurrentUserUID+selectedProgram+selectedDiscipline+selectedLevel+selectedSegment;
        */
        //map of program
        //Create 13 empty elements, so we can pull by that and leave spaces while working
        Programv2 programv2 = new Programv2(
                currentUserUID,
                currentProgramDescription,
                selectedDiscipline,
                selectedLevel,
                selectedSegment,
                Arrays.asList(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ")
        );
        //Add to Firestore
        //programRef.document(programID).set(programv2)
        programRef.document()
                .set(programv2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddProgramActivity.this, "Program Saved", Toast.LENGTH_SHORT).show();
                        //Get new programsID and save to global or else send back to select activity
                        /*
                        currentProgramID = documentReference.getId();
                        globalClass.setCurrentProgramID(currentProgramID);
                        //Go to editing
                        myIntent = new Intent(AddProgramActivity.this, ProgramEditActivity.class);
                        */
                        myIntent = new Intent(AddProgramActivity.this, ProgramSelectActivity.class);
                        startActivity(myIntent);
                    }
                });

    }

    /*
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
*/
    //Set up the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //Inflate the menu; this adds item to the action
        //bar if its present
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //Selecting menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_element_lookup:
                // User chose the "Element Lookup" item in the toolbar, call the activity
                Intent intentBundle = new Intent(AddProgramActivity.this, ElementLookupActivity.class);
                startActivity(intentBundle);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
