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
import android.widget.Button;
import android.widget.EditText;
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
public class ProgramSelectActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference skatersCollectionDb;
    private CollectionReference programCollectionDb;
    public TextView mTextViewName;
    public TextView mTextViewID;
    public TextView textViewPrograms[] = new TextView[10];
    public Button mAddProgram;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    //private static List<Program> programs = new ArrayList<>();
    //private List<Program> programs;
    private static List<Programv2> programList = new ArrayList<>();
    private List<Programv2> programs;
    private List<Programv2> programv2List;
    private static List documentIdList;
    public ArrayList<Programv2> programsv2 = new ArrayList<>();
    public String mCurrentUserUID;
    public String selectedProgramID;
    public String[] programName = new String[8];
    public String[] usersPrograms = new String[10];
    public String mSkaterName;
    public String tempString;
    private int position;
    public int programCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_select);

        mTextViewName = findViewById(R.id.textViewProgramSelectSkaterName);
        mTextViewID = findViewById(R.id.textViewProgramSelectTitle); // For checking only, eliminate from final
        mAddProgram = findViewById(R.id.newProgramButton);
        textViewPrograms[0] = findViewById(R.id.textViewProgram8);
        textViewPrograms[1] = findViewById(R.id.textViewProgram1);
        textViewPrograms[2] = findViewById(R.id.textViewProgram2);
        textViewPrograms[3] = findViewById(R.id.textViewProgram3);
        textViewPrograms[4] = findViewById(R.id.textViewProgram4);
        textViewPrograms[5] = findViewById(R.id.textViewProgram5);
        textViewPrograms[6] = findViewById(R.id.textViewProgram6);
        textViewPrograms[7] = findViewById(R.id.textViewProgram7);
        //textViewPrograms[8] = findViewById(R.id.textViewProgram8);

        // Get current userID - for fetching if using Global Class
        GlobalClass globalClass = ((GlobalClass) getApplicationContext());
        mCurrentUserUID = globalClass.getCurrentUserUID();
        mSkaterName = globalClass.getSkaterName();
        mTextViewName.setText(mSkaterName);

        //Toast.makeText(this, "Name: " + mSkaterName, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Name: " + mSkaterName + " UID: " + mCurrentUserUID, Toast.LENGTH_SHORT).show();

        /*
        //Get the userID
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle.isEmpty()) {
            // deal with empty bundle
        } else {
            // get the UID
            mCurrentUserUID = extrasBundle.getString("userID");
        }
        */

        mAddProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addProgram();
            }
        });


        // Set up db, recycler and get initial list of programs
        init();
        getListPrograms();
        textViewPrograms[0].setText("Baseball");
        //tempString = programsv2.get(0).getUserID();
        textViewPrograms[1].setText(tempString);
    }

    // Initialize database and recyclerview
    private void init() {
        // Access Firestore
        db = FirebaseFirestore.getInstance();
        skatersCollectionDb = db.collection("Skaters");
        programCollectionDb = db.collection("Programs");
    }

    /*
    private void initRecycler() {
        // Setup recyclerview for selecting program
        recyclerView = findViewById(R.id.recyclerViewProgramSelect);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramSelectAdapter(programs, this);
        //adapter = new ProgramSelectAdapter(programList, this); // using this causes duplicate entries in list
        recyclerView.setAdapter(adapter);
    }
    */

    // Query and load skaters programs
    private void getListPrograms() {
        //query
        Query query = programCollectionDb.whereEqualTo("UserID", mCurrentUserUID);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot>task) {
                //usersPrograms = querySnapshot.docs.map(doc = > doc.data());
                for (QueryDocumentSnapshot document : task.getResult()){
                    //programs.add(document.toObject(Programv2.class));
                    //programsv2.add(programs);
                    programsv2.add(document.toObject(Programv2.class));
                    programCount = programsv2.size();
                    tempString = "Program Count = " + String.valueOf(programCount);
                    tempString = "String " + programsv2.get(0).getUserID();
                    textViewPrograms[1].setText(tempString);
                    textViewPrograms[2].setText(programsv2.get(0).getUserID());
                    //programv2List.add(Programv2 programs.getUserID());
                    //programv2List.add(programs);
                }
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


    /*
    private void getListPrograms() {
        //query
        programCollectionDb.whereEqualTo("userID", mCurrentUserUID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            // Handle empty
                            Toast.makeText(getApplicationContext(), "Error getting data but Success!!!", Toast.LENGTH_LONG).show();
                        } else {
                            //this works
                            programs = documentSnapshots.toObjects(Program.class); // load for adapter
                            // load for selecting
                            for (DocumentSnapshot documentSnapshot : documentSnapshots){
                                Program program = documentSnapshot.toObject(Program.class);
                                program.setDocumentID(documentSnapshot.getId());
                                programList.add(program);
                            }
                            programCount = documentSnapshots.size();
                            initRecycler(); //call here to force it to load the first time to activity
                            // Trying to save DocumentID for programs
                        }
                    }
                    // Add persistent data to sharedpreferences
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });
    }
    */
    /*
                //Add new program
        public void addProgram () {
            //Call routine to set up new program
            Intent myIntent = new Intent(ProgramSelectActivity.this, AddProgramActivity.class);
            startActivity(myIntent);
        }

        //Get program selected
        @Override
        public void userItemClick ( int position){

            //Send to program view activity with data
        /*
        Intent intentBundle = new Intent(ProgramSelectActivity.this, ProgramViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userID",mCurrentUserUID);
        bundle.putString("userID",mCurrentUserUID);
        intentBundle.putExtras(bundle);
        startActivity(intentBundle);
        */
            //this.position = position;
            //selectedProgramID = programList.get(position).getDocumentID();
            //selectOptionButtonDialog(selectedProgramID);
        /*
        //selectedProgramID = programList.get(0).getDocumentID();
        //selectedProgramID2 = programList.get(1).getDocumentID();
        //Toast.makeText(this, "ID1: " + selectedProgramID + " ID2: " + selectedProgramID2 + " pos: " + position, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "ID: " + selectedProgramID, Toast.LENGTH_LONG).show();
        Intent intentBundle = new Intent(ProgramSelectActivity.this, ProgramScoringViewActivity.class);
        //Intent intentBundle = new Intent(ProgramSelectActivity.this, ProgramViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userID",mCurrentUserUID);
        bundle.putString("programID",selectedProgramID);
        intentBundle.putExtras(bundle);
        startActivity(intentBundle);
        //Toast.makeText(this, "Clicked: " + programList.get(position).getDocumentID(), Toast.LENGTH_LONG).show();
        */
        //}

        //Choose to Edit or Score
        public void selectOptionButtonDialog ( final String selectedProgramID2){
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
            editButton.setOnClickListener(new View.OnClickListener() {
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
            scoreButton.setOnClickListener(new View.OnClickListener() {
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

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // Display the custom alert dialog on interface
            dialog.show();
        }
    }
