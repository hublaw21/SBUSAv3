package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;

public class ProgramSelectActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference skatersCollectionDb;
    private CollectionReference programCollectionDb;
    public TextView mTextViewName;
    public TextView mTextViewID;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static List<Program> programs = new ArrayList<>();
    private static List<Program> programList = new ArrayList<>();
    public String mCurrentUserUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_select);
        mTextViewName = findViewById(R.id.textViewProgramSelectName);
        mTextViewID = findViewById(R.id.textViewProgramSelectTitle); // For checking only, eliminate from final

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

        // Get current user UID
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        mCurrentUserUID = globalClass.getCurrentUserUID();
        //mTextViewID.setText(mCurrentUserUID); // For checking only, eliminate from final
    }

    private void initRecycler(){
        // Setup recyclerview for selecting program
        recyclerView = findViewById(R.id.recyclerViewProgramSelect);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramSelectAdapter(programs);
        recyclerView.setAdapter(adapter);
    }

    // Query and load skaters programs
    private void getListPrograms() {
        //Query query = programCollectionDb.whereEqualTo("skaterUID", mCurrentUserUID)
        programCollectionDb.whereEqualTo("skaterUID", mCurrentUserUID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            // Handle empty
                            Toast.makeText(getApplicationContext(), "Error getting data but Success!!!", Toast.LENGTH_LONG).show();
                        } else {
                            //List<Program> programList = new ArrayList<>();
                            programs = documentSnapshots.toObjects(Program.class);
                            programList.addAll(programs);
                            // So how do I get this to recycler?
                            initRecycler();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
