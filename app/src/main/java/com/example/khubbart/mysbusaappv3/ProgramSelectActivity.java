package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Program;
import com.example.khubbart.mysbusaappv3.Model.Programv2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

//public class ProgramSelectActivity extends AppCompatActivity implements ProgramSelectItem {
public class ProgramSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db;
    private CollectionReference skatersCollectionDb;
    private CollectionReference programCollectionDb;
    private DocumentReference docRef;
    public TextView mTextViewName;
    public TextView mTextViewID;
    public TextView textViewPrograms[] = new TextView[10];
    public RadioButton buttonSelectProgram[] = new RadioButton[8];
    public Button cancel;
    public Button edit;
    public Button score;
    public Button mAddProgram;
    public RadioGroup radioGroupPrograms;
    public GlobalClass globalClass;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    //private static List<Programv2> programList = new ArrayList<>();
    private List<Programv2> programs;
    private List<Programv2> programv2List;
    private static List documentIdList;
    public ArrayList<Programv2> programsv2 = new ArrayList<>();
    //public ArrayList programIDList = new ArrayList();
    public List<String> programIDList = new ArrayList<>();
    public String mCurrentUserUID;
    public String currentProgramID;
    public String[] programName = new String[8];
    public String[] usersPrograms = new String[10];
    public String mSkaterName;
    public String tempString;
    private int position;
    public int i;
    public int programCount;
    public int resID;
    public int programLimit;
    public int buttonID;
    public Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_select);

        // Access Firestore
        db = FirebaseFirestore.getInstance();
        skatersCollectionDb = db.collection("Skaters");
        programCollectionDb = db.collection("Programs");

        //Set up basic view stuff
        mTextViewName = findViewById(R.id.textViewProgramSelectSkaterName);
        mTextViewID = findViewById(R.id.textViewProgramSelectTitle); // For checking only, eliminate from final
        cancel = findViewById(R.id.buttonCancel);
        edit = findViewById(R.id.buttonAddEdit);
        score = findViewById(R.id.buttonScore);
        cancel.setOnClickListener(this);
        edit.setOnClickListener(this);
        score.setOnClickListener(this);

        //Set up Radio Group and listener
        radioGroupPrograms = findViewById(R.id.radioGroupPrograms);
        radioGroupPrograms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int resID) {
                //Set it in Global, need to do here, not when selecting score because it is too fast
                int testCount = resID; //It throws an error if I try to compare directly
                if (testCount < programCount) {
                    //If an actual program
                    currentProgramID = programIDList.get(resID);
                    globalClass.retrieveCurrentProgram(currentProgramID);  //This pulls the current program and saves it for use in the App
                } else {
                    //Last option is a new program, which does not have an ID
                    currentProgramID = "New" + programCount;
                    //Send it to Add program
                    myIntent = new Intent(ProgramSelectActivity.this, AddProgramActivity.class);
                    startActivity(myIntent);
                }
                globalClass.setCurrentProgramID(currentProgramID);
            }
        });
        // Get current userID and skater name
        globalClass = ((GlobalClass) getApplicationContext());
        mCurrentUserUID = globalClass.getCurrentUserUID();
        mSkaterName = globalClass.getSkaterName();
        mTextViewName.setText(mSkaterName);

        getListPrograms();
    }

    //Handle click of buttons
    @Override
    public void onClick(View view) {
        resID = radioGroupPrograms.getCheckedRadioButtonId();
        //Could use switch for all of them, but coding may work
        switch (view.getId()) {
            case R.id.buttonCancel:
                // do nothing for now
                Toast.makeText(this, "currentProgramID: " + currentProgramID, Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonAddEdit:
                // check for whether add or edit
                int testCount = resID; //It throws an error if I try to compare directly
                if (testCount < programCount) {
                    //If an actual program
                    //globalClass.retrieveCurrentProgram(currentProgramID);  //This pulls the current program and saves it for use in the App
                    myIntent = new Intent(ProgramSelectActivity.this, ProgramViewActivity.class);
                } else {
                    //Last option is a new program, which does not have an ID
                    currentProgramID = "New" + programCount;
                    myIntent = new Intent(ProgramSelectActivity.this, AddProgramActivity.class);
                }
                myIntent.putExtra("programID",currentProgramID);
                startActivity(myIntent);

                break;
            case R.id.buttonScore:
                globalClass.retrieveCurrentProgram(currentProgramID);  //This pulls the current program and saves it for use in the App
                myIntent = new Intent(ProgramSelectActivity.this, ProgramScoringViewActivity.class);
                startActivity(myIntent);

                // Retrieve and save programID
                //getSelectedProgram(resID);
                //Toast.makeText(this, currentProgramID, Toast.LENGTH_SHORT).show();
                break;
        }
        //Toast.makeText(this, "Button " + resID + "Clicked", Toast.LENGTH_SHORT).show();
    }

    // Query and load skaters programs
    private void getListPrograms() {
        //query
        Query query = programCollectionDb.whereEqualTo("userID", mCurrentUserUID);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    programsv2.add(document.toObject(Programv2.class));
                    programIDList.add(document.getId()); //Need this for accessing the proper program
                    //Toast.makeText(getApplicationContext(), document.getId() + "** => " + document.getData(), Toast.LENGTH_LONG).show();
                }
                programCount = programsv2.size();
                if (programCount > 7) {
                    programLimit = 8;
                } else {
                    programLimit = programCount;
                }

                for (i = 0; i < programLimit; i++) {  //Remember array starts at 0, but we always have one button
                    tempString = programsv2.get(i).getProgramDescription() + " "
                            + programsv2.get(i).getLevel() + " "
                            + programsv2.get(i).getDiscipline() + " "
                            + programsv2.get(i).getSegment();
                    makeRadioButton(i, tempString);
                    tempString = programsv2.get(i).getElements().toString();
                    //Log.i("****ProgramID", document.getId());
                    Log.i("SelFetched",tempString);
                }
                if (programCount < 8) makeRadioButton(programCount, "Add a Program");
            }

            ;


            /*
        public void onSuccess(QuerySnapshot querySnapshot) {
                usersPrograms = querySnapshot.docs.map(doc => doc.data());
                    //this works
                    //usersPrograms = querySnapshots.docs.map(Programv2.class (querySnapshots) {
                    //return documentSnapshot.data();
                    //    }); // load for adapter
                    // load for selecting

                            for (DocumentSnapshot document : task.getResult()){
                                usersPrograms = querySnapshot.docs.map(doc => doc.data())
                                //List<String> list = (List<String>) document.get("UserID");
                                //Programv2 userPrograms = (Programv2) documentSnapshot.getData();
                                //program.setDocumentID(documentSnapshot.getId()); -- I don;t think I need this now
                                //programList.add(program);
                            }

                    //programCount = querySnapshots.size();
                    //initRecycler(); //call here to force it to load the first time to activity
                    // Trying to save DocumentID for programs
                }
            });
            */

            //Set into textViews
            //textViewPrograms[0].setText(usersPrograms[0])
            //textViewPrograms[0].setText("Baseball");
        });
    }

    // Pull program by selected program
    private void getSelectedProgram(int Pos) {
        //Should be able to generate program id from data saved in Programv2
        //tempString = programIDList.get(Pos);
        tempString = currentProgramID;
        /*
        tempString = programsv2.get(Pos).getUserID()
                + programsv2.get(Pos).getProgramDescription()
                + programsv2.get(Pos).getLevel()
                + programsv2.get(Pos).getDiscipline()
                + programsv2.get(Pos).getSegment();
        tempString = "ZXY7IcjKivaqLGk4mJ8ykVMPcT13Alt01SeniorMenFree";
        */
        docRef = programCollectionDb.document(tempString);
        final int sPos = Pos;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String test = "ZXY7IcjKivaqLGk4mJ8ykVMPcT13Alt01SeniorMenFree";
                    if (tempString == test) {
                        Toast.makeText(getApplicationContext(), "Match", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), "No Match", Toast.LENGTH_LONG).show();
                        Log.i("ProgramID ==> ", tempString);
                    }
                    //Toast.makeText(getApplicationContext(), document.getId() + " => " + document.getData(), Toast.LENGTH_LONG).show();
                    if (document != null) {
                        //Grab program data
                        currentProgramID = document.getId();
                        //Set it in Global
                        globalClass.setCurrentProgramID(currentProgramID);
                    } else {
                        //No document found
                        currentProgramID = "not found";
                    }
                }
            }
        });
        // go to scoring page
        Intent myIntent = new Intent(ProgramSelectActivity.this, ProgramScoringViewActivity.class);
        startActivity(myIntent);
    }

    public void makeRadioButton(int i, String tempString) {
        buttonSelectProgram[i] = (RadioButton) getLayoutInflater().inflate(R.layout.button_custom_style, null); //had to inflate to be able to reference the button style
        buttonSelectProgram[i].setId(i);
        buttonSelectProgram[i].setText(tempString);
        if (i == 0) buttonSelectProgram[i].setChecked(true);
        radioGroupPrograms.addView(buttonSelectProgram[i]);
    }

    //Choose to Edit or Score
    public void selectOptionButtonDialog(final String selectedProgramID2) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_select_program, null);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        Button editButton = (Button) dialogView.findViewById(R.id.dialog_edit_program_button);
        Button scoreButton = (Button) dialogView.findViewById(R.id.dialog_score_program_button);
        Button cancelButton = (Button) dialogView.findViewById(R.id.dialog_cancel_button);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set add/edit button click listener
        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                //go to edit page
                Intent intentBundle = new Intent(ProgramSelectActivity.this, ProgramViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", mCurrentUserUID);
                bundle.putString("programID", selectedProgramID2);
                intentBundle.putExtras(bundle);
                startActivity(intentBundle);
            }
        });

        // Set score button click listener
        scoreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/cancel the alert dialog
                dialog.dismiss();
                // go to scoring page
                Intent intentBundle = new Intent(ProgramSelectActivity.this, ProgramScoringViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", mCurrentUserUID);
                bundle.putString("programID", selectedProgramID2);
                intentBundle.putExtras(bundle);
                startActivity(intentBundle);
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
    }
}
