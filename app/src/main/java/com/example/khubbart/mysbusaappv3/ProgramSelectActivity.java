package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Programv2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

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
    public Button delete;
    public Button edit;
    public Button score;
    public Button add;
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
    public String line1;
    public String line2;

    //Be able to scale button backgrounds when creating programatically
    //public ScaleDrawable (Drawable drawable, int gravity, float scaleWidth, float scaleHeight);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_select);

        //Set up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Access Firestore
        db = FirebaseFirestore.getInstance();
        skatersCollectionDb = db.collection("Skaters");
        programCollectionDb = db.collection("Programs");

        //Set up basic view stuff
        mTextViewName = findViewById(R.id.textViewProgramSelectSkaterName);
        mTextViewID = findViewById(R.id.textViewProgramSelectTitle); // For checking only, eliminate from final
        edit = findViewById(R.id.buttonEdit);
        score = findViewById(R.id.buttonScore);
        delete = findViewById(R.id.buttonDelete);
        add = findViewById(R.id.buttonAddProgram);
        edit.setOnClickListener(this);
        score.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);

        //Set up Radio Group and listener
        radioGroupPrograms = findViewById(R.id.radioGroupPrograms);
        radioGroupPrograms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int resID) {
                //Set it in Global, need to do here, not when selecting score because it is too fast
                //int testCount = resID; //It throws an error if I try to compare directly
                //if (testCount < programCount) {
                    //If an actual program
                    currentProgramID = programIDList.get(resID);
                    //Probably do not need the next line anymore
                    globalClass.retrieveCurrentProgram(currentProgramID);  //This pulls the current program and saves it for use in the App
                /*} else {
                    //Last option is a new program, which does not have an ID
                    currentProgramID = "New" + programCount;
                    //Send it to Add program
                    myIntent = new Intent(ProgramSelectActivity.this, AddProgramActivity.class);
                    startActivity(myIntent);
                }
                */
                //ProgramID gets passed withintent, but saving it just in case
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
            case R.id.buttonDelete:
                // do nothing for now
                //Toast.makeText(this, "currentProgramID: " + currentProgramID, Toast.LENGTH_SHORT).show();
                break;

            case R.id.buttonEdit:
                currentProgramID = programIDList.get(resID);
                myIntent = new Intent(ProgramSelectActivity.this, ProgramEditActivity.class);
                myIntent.putExtra("programID", currentProgramID);
                startActivity(myIntent);
                break;

            case R.id.buttonAddProgram:
                //Toast.makeText(this, "Program Add Button: " + currentProgramID, Toast.LENGTH_SHORT).show();
                currentProgramID = "New" + programCount;
                myIntent = new Intent(ProgramSelectActivity.this, AddProgramActivity.class);
                myIntent.putExtra("programID", currentProgramID);
                startActivity(myIntent);
                break;

            case R.id.buttonScore:
                //globalClass.retrieveCurrentProgram(currentProgramID);  //This pulls the current program and saves it for use in the App
                currentProgramID = programIDList.get(resID);
                myIntent = new Intent(ProgramSelectActivity.this, ProgramScoringViewActivity.class);
                myIntent.putExtra("programID", currentProgramID);
                startActivity(myIntent);
                break;
        }
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

                String multi = "&#10;";
                for (i = 0; i < programLimit; i++) {  //Remember array starts at 0, but we always have one button
                    line1 = programsv2.get(i).getProgramDescription();
                    line2 = programsv2.get(i).getLevel() + " "
                            + programsv2.get(i).getDiscipline() + " "
                            + programsv2.get(i).getSegment();
                    makeRadioButton(i, line1, line2);
                    //tempString = programsv2.get(i).getElements().toString();
                    //Log.i("****ProgramID", document.getId());
                    //Log.i("SelFetched",tempString);
                }
                //if (programCount < 8) makeRadioButton(programCount, "Add a Program", "");
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
                        //Log.i("ProgramID ==> ", tempString);
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

    public void makeRadioButton(int i, String bLine1, String bLine2) {
        buttonSelectProgram[i] = (RadioButton) getLayoutInflater().inflate(R.layout.button_custom_style, null); //had to inflate to be able to reference the button style
        buttonSelectProgram[i].setId(i);
        //Set up margins
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(15, 15, 15, 15);
        buttonSelectProgram[i].setLayoutParams(params);
        //Two lines is getting a litte busy and too big
        //tempString = bLine1 + "<br/>" + bLine2;
        //buttonSelectProgram[i].setText(Html.fromHtml(tempString));
        buttonSelectProgram[i].setText(line1);
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
                Intent intentBundle = new Intent(ProgramSelectActivity.this, ProgramEditActivity.class);
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
                Intent intentBundle = new Intent(ProgramSelectActivity.this, ElementLookupActivity.class);
                startActivity(intentBundle);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
