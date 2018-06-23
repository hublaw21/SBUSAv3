package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.ElementInfo;
import com.example.khubbart.mysbusaappv3.Model.Elements;
import com.example.khubbart.mysbusaappv3.Model.Program;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.GeneratedMessageLite;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramViewActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference skatersCollectionDb;
    private DocumentReference programRef;
    private CollectionReference elementCollection;
    private DocumentReference elementRef;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String ELEMENT_ID_KEY = "elementIdKey";

    public RecyclerView recyclerView;


    //public List<Program> program;
    public List<Program> program = new ArrayList<>();
    public List<Elements> elements = new ArrayList<>();
    public ArrayList<ElementInfo> elementInfoList = new ArrayList<ElementInfo>();
    //public List<ElementArray> elementArrayList = new ArrayList<>();

    //private List<Program> qProgram;
    //private static List<Program> qProgram = new ArrayList<>();

    public TextView mCompetitionNameTextView;
    public TextView mCompetitionDescriptionTextView;
    public TextView textViewForTesting;
    public TextView mElementIDO0TextView;
    public TextView mElementNameO0TextView;
    public TextView mElementBaseValueO0TextView;
    public TextView mElementIDO1TextView;
    public TextView mElementNameO1TextView;
    public TextView mElementBaseValueO1TextView;
    public TextView mElementIDO2TextView;
    public TextView mElementNameO2TextView;
    public TextView mElementBaseValueO2TextView;
    public TextView[] mElementIDTextView = new TextView[13];
    public TextView[] mElementNameTextView = new TextView[13];
    public TextView[] mElementBaseValueTextView = new TextView[13];
    public String mCurrentUserID;
    public String mCurrentProgramID;
    public String elementID;
    public String[] mElementCode = new String[13];
    public String mElementName;
    public String mElementBaseValue;
    public String[] SOVCode;
    public String[] SOVName;
    public String[] SOVBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_view);
        mCompetitionNameTextView = findViewById(R.id.textViewCompetition);
        mCompetitionDescriptionTextView = findViewById(R.id.textViewProgramDescription);
        textViewForTesting = findViewById(R.id.textViewForTesting);

        // Initialize Recyclerview for element info
        ElementInfoArrayAdapter elementInfoArrayAdapter = new ElementInfoArrayAdapter(R.layout.activity_program_view, elementInfoList);
        recyclerView = findViewById(R.id.elementinfo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(elementInfoArrayAdapter);

        /*
        mElementIDTextView[0] = findViewById(R.id.elementRow00elementID);
        mElementIDTextView[1] = findViewById(R.id.elementRow01elementID);
        mElementIDTextView[2] = findViewById(R.id.elementRow02elementID);
        mElementIDTextView[3] = findViewById(R.id.elementRow03elementID);
        mElementIDTextView[4] = findViewById(R.id.elementRow04elementID);
        mElementIDTextView[5] = findViewById(R.id.elementRow05elementID);
        mElementIDTextView[6] = findViewById(R.id.elementRow06elementID);
        mElementIDTextView[7] = findViewById(R.id.elementRow07elementID);
        mElementIDTextView[8] = findViewById(R.id.elementRow08elementID);
        mElementIDTextView[9] = findViewById(R.id.elementRow09elementID);
        mElementIDTextView[10] = findViewById(R.id.elementRow10elementID);
        mElementIDTextView[11] = findViewById(R.id.elementRow11elementID);
        mElementIDTextView[12] = findViewById(R.id.elementRow12elementID);

        mElementNameTextView[0] = findViewById(R.id.elementRow00elementName);
        mElementNameTextView[1] = findViewById(R.id.elementRow01elementName);
        mElementNameTextView[2] = findViewById(R.id.elementRow02elementName);
        mElementNameTextView[3] = findViewById(R.id.elementRow03elementName);
        mElementNameTextView[4] = findViewById(R.id.elementRow04elementName);
        mElementNameTextView[5] = findViewById(R.id.elementRow05elementName);
        mElementNameTextView[6] = findViewById(R.id.elementRow06elementName);
        mElementNameTextView[7] = findViewById(R.id.elementRow07elementName);
        mElementNameTextView[8] = findViewById(R.id.elementRow08elementName);
        mElementNameTextView[9] = findViewById(R.id.elementRow09elementName);
        mElementNameTextView[10] = findViewById(R.id.elementRow10elementName);
        mElementNameTextView[11] = findViewById(R.id.elementRow11elementName);
        mElementNameTextView[12] = findViewById(R.id.elementRow12elementName);

        mElementBaseValueTextView[0] = findViewById(R.id.elementRow00elementBaseValue);
        mElementBaseValueTextView[1] = findViewById(R.id.elementRow01elementBaseValue);
        mElementBaseValueTextView[2] = findViewById(R.id.elementRow02elementBaseValue);
        mElementBaseValueTextView[3] = findViewById(R.id.elementRow03elementBaseValue);
        mElementBaseValueTextView[4] = findViewById(R.id.elementRow04elementBaseValue);
        mElementBaseValueTextView[5] = findViewById(R.id.elementRow05elementBaseValue);
        mElementBaseValueTextView[6] = findViewById(R.id.elementRow06elementBaseValue);
        mElementBaseValueTextView[7] = findViewById(R.id.elementRow07elementBaseValue);
        mElementBaseValueTextView[8] = findViewById(R.id.elementRow08elementBaseValue);
        mElementBaseValueTextView[9] = findViewById(R.id.elementRow09elementBaseValue);
        mElementBaseValueTextView[10] = findViewById(R.id.elementRow10elementBaseValue);
        mElementBaseValueTextView[11] = findViewById(R.id.elementRow11elementBaseValue);
        mElementBaseValueTextView[12] = findViewById(R.id.elementRow12elementBaseValue);
        */

        db = FirebaseFirestore.getInstance();
        //Load SOV
        getSOV();

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

    // Initialize database and ?recyclerview
    public void init() {
        //db = FirebaseFirestore.getInstance();
        final SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        //skatersCollectionDb = db.collection("Skaters");
        //Get program basics
        programRef = db.collection("Programs").document(mCurrentProgramID);
        Task<DocumentSnapshot> documentSnapshotTask = programRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(final DocumentSnapshot documentSnapshot) {
                //initializing shared preferences to save results of query
                //SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                //SharedPreferences.Editor editor = sp.edit();
                Program qProgram = documentSnapshot.toObject(Program.class);
                program.add(qProgram);
                mCompetitionNameTextView.setText(program.get(0).getCompetition());
                String tempText = program.get(0).getLevel() + " " + program.get(0).getDiscipline() + " " + program.get(0).getSegment() + " Program";
                mCompetitionDescriptionTextView.setText(tempText);
                String elementID = program.get(0).getElementsID();
                //textViewForTesting.setText(elementID);
                //Save persistent data
                editor.putString(ELEMENT_ID_KEY, elementID);
                editor.apply();
                getElements();
            }
        });
        //initializing shared preferences to save results of query
        //SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        /*
        elementID = sp. getString(ELEMENT_ID_KEY, null);
        if (elementID != null) {
            textViewForTesting.setText(elementID);
        } else {
            textViewForTesting.setText("No elementID value");
        }
        // Get the element details
        elementCollection = db.collection("Elements");
        */

        //ElementArray elementArray = program.get(0).getElements().toString();
        //mCompetitionDescriptionTextView.setText(eString);
        //Flatten this by creating an elements collection with the program id as a searchable field plus the elements, then just do a second pull
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

    public void getElements() {
        final SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        //Get program basics
        elementID = sp.getString(ELEMENT_ID_KEY, null);
        if (elementID != null) {
            textViewForTesting.setText(elementID);
        } else {
            textViewForTesting.setText("No elementID value");
        }
        elementRef = db.collection("Elements").document(elementID);

        //DocumentSnapshot documentSnapshot = elementRef.get();
        //This works

        Task<DocumentSnapshot> documentSnapshotTask = elementRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(final DocumentSnapshot documentSnapshot) {
                //initializing shared preferences to save results of query
                //SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                //SharedPreferences.Editor editor = sp.edit();

                Elements qElements = documentSnapshot.toObject(Elements.class);
                elements.add(qElements);
                int eCount = 0;
                mElementCode[0] = elements.get(0).getE00();
                mElementCode[1] = elements.get(0).getE01();
                mElementCode[2] = elements.get(0).getE02();
                mElementCode[3] = elements.get(0).getE03();
                mElementCode[4] = elements.get(0).getE04();
                mElementCode[5] = elements.get(0).getE05();
                mElementCode[6] = elements.get(0).getE06();
                mElementCode[7] = elements.get(0).getE07();
                mElementCode[8] = elements.get(0).getE08();
                mElementCode[9] = elements.get(0).getE09();
                mElementCode[10] = elements.get(0).getE10();
                mElementCode[11] = elements.get(0).getE11();
                mElementCode[12] = elements.get(0).getE12();
                for (int i = 0; i < 13; i++) {
                    if (mElementCode[i] != null) {
                        int currentSOVIndex = Arrays.asList(SOVCode).indexOf(mElementCode[i]);
                        mCompetitionDescriptionTextView.setText("In Cycle");
                        //mElementIDTextView[i].setText(mElementCode[i]);
                        if (currentSOVIndex > 0) {
                            mElementName  = Arrays.asList(SOVName).get(currentSOVIndex);
                            mElementBaseValue = Arrays.asList(SOVBase).get(currentSOVIndex);
                            //mElementNameTextView[i].setText(Arrays.asList(SOVName).get(currentSOVIndex));
                            //mElementBaseValueTextView[i].setText(Arrays.asList(SOVBase).get(currentSOVIndex));
                        }
                        elementInfoList.add(new ElementInfo(mElementCode[i], mElementName, mElementBaseValue));
                        recyclerView.setAdapter(elementInfoArrayAdapter);

                    } else {
                        //mElementIDTextView[i].setText("Code");
                        //mElementNameTextView[i].setText(" ");
                        //mElementBaseValueTextView[i].setText(" ");
                    }

                }
            }

        });


    }

    private void showData(String mElementCode, int i) {
        int currentSOVIndex = Arrays.asList(SOVCode).indexOf(mElementCode);
        mElementIDTextView[i].setText(String.valueOf(currentSOVIndex));
    }

    public void getSOV() {
        //Get SOV Table 2018 - Three matched arrays
        Resources resources = getResources();
        SOVCode = resources.getStringArray(R.array.SOV_Code);
        SOVName = resources.getStringArray(R.array.SOV_Name);
        SOVBase = resources.getStringArray(R.array.SOV_Base);
    }

    public void getElementInfo(String mElementCode) {
        int currentSOVIndex = Arrays.asList(SOVCode).indexOf(mElementCode);
        // Need to add error checker for code not found
        if (currentSOVIndex < 0) {
            //Error
            //Toast.makeText(getApplicationContext(), "elementCode: **" + elementCode + "** currentSOVIndex: " + currentSOVIndex, Toast.LENGTH_LONG).show();
        } else {
            String currentElementBase = Arrays.asList(SOVBase).get(currentSOVIndex);
            String currentElementName = Arrays.asList(SOVName).get(currentSOVIndex);
        }
    }

}
