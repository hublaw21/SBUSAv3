package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Program;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ProgramSelectActivity extends AppCompatActivity implements ProgramSelectItem {

    private FirebaseFirestore db;
    private CollectionReference skatersCollectionDb;
    private CollectionReference programCollectionDb;
    public TextView mTextViewName;
    public TextView mTextViewID;
    public Button mAddProgram;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    //private static List<Program> programs = new ArrayList<>();
    private List<Program> programs;
    private static List<Program> programList = new ArrayList<>();
    private static List documentIdList;
    public String mCurrentUserUID;
    public String selectedProgramID;
    public String selectedProgramID2;
    public String mSkaterName;
    private int position;
    public int programCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_select);


        mTextViewName = findViewById(R.id.textViewProgramSelectSkaterName);
        mTextViewID = findViewById(R.id.textViewProgramSelectTitle); // For checking only, eliminate from final
        mAddProgram = findViewById(R.id.newProgramButton);

        // Get current userID - for fetching if using Global Class
        GlobalClass globalClass = ((GlobalClass)getApplicationContext());
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
                addProgram();
            }
        });


        // Set up db, recycler and get initial list of programs
        init();
        getListPrograms();
        //initRecycler();
    }

    // Need to setup on click for selecting a program

    // Initialize database and recyclerview
    private void init() {
        // Access Firestore

        db = FirebaseFirestore.getInstance();
        skatersCollectionDb = db.collection("Skaters");
        programCollectionDb = db.collection("Programs");
    }

    private void initRecycler() {
        // Setup recyclerview for selecting program
        recyclerView = findViewById(R.id.recyclerViewProgramSelect);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramSelectAdapter(programs, this);
        //adapter = new ProgramSelectAdapter(programList, this); // using this causes duplicate entries in list
        recyclerView.setAdapter(adapter);
    }

    // Query and load skaters programs
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
                            //selectedProgramID = documentSnapshots.toString();
                            //Toast.makeText(getApplicationContext(), "Size: " + programCount, Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(), "Results: " + selectedProgramID, Toast.LENGTH_LONG).show();
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

    //Add new program
    public void addProgram(){
        //Call routine to set up new program
        Intent myIntent = new Intent(ProgramSelectActivity.this, AddProgramActivity.class);
        startActivity(myIntent);
    }

    //Get program selected
    @Override
    public void userItemClick(int position) {
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
        selectedProgramID = programList.get(position).getDocumentID();
        //selectedProgramID = programList.get(0).getDocumentID();
        //selectedProgramID2 = programList.get(1).getDocumentID();
        //Toast.makeText(this, "ID1: " + selectedProgramID + " ID2: " + selectedProgramID2 + " pos: " + position, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "ID: " + selectedProgramID, Toast.LENGTH_LONG).show();
        Intent intentBundle = new Intent(ProgramSelectActivity.this, ProgramViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userID",mCurrentUserUID);
        bundle.putString("programID",selectedProgramID);
        intentBundle.putExtras(bundle);
        startActivity(intentBundle);
        //Toast.makeText(this, "Clicked: " + programList.get(position).getDocumentID(), Toast.LENGTH_LONG).show();
    }
}
