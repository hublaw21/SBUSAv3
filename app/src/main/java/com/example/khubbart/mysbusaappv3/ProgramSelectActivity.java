package com.example.khubbart.mysbusaappv3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.khubbart.mysbusaappv3.Model.Program;

import java.util.List;

public class ProgramSelectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Program> programs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_select);

        // Setup recyclerview for slecting program
        recyclerView = findViewById(R.id.recyclerViewProgramSelect);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramSelectAdapter(programs);
        recyclerView.setAdapter(adapter);
    }

    // Need to setup on click for selecting a program
}
