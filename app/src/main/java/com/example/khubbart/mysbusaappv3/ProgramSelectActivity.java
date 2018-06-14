package com.example.khubbart.mysbusaappv3;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.khubbart.mysbusaappv3.Model.Program;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class ProgramSelectActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference crDb;
    private CollectionReference programCollectionDb;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Program> programs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_select);

        // Access Firestore
        db = FirebaseFirestore.getInstance();
        crDb = db.collection("Skaters");
        programCollectionDb = db.collection("Programs");

        // Get current user UID
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        final String mCurrentUserUID = globalClass.getCurrentUserUID();


        // Setup recyclerview for selecting program
        recyclerView = findViewById(R.id.recyclerViewProgramSelect);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramSelectAdapter(programs);
        recyclerView.setAdapter(adapter);
    }

    // Need to setup on click for selecting a program

    // Query and load skaters programs
    private void initFirestore() {
        Query query = programCollectionDb.whereEqualTo("skaterUID", mCurrentUserUID);
        FirestoreRecyclerOptions<Program> response = new FirestoreRecyclerOptions.Builder<Program>()
                .setQuery(query, Program.class)
                .build();
    }
}
