package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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


    //public List<Program> program;
    public List<Program> program = new ArrayList<>();
    public List<Elements> elements = new ArrayList<>();
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
    public TextView[] mElementIDTextView;
    public TextView[] mElementNameTextView;
    public TextView[] mElementBaseValueTextView;
    public String mCurrentUserID;
    public String mCurrentProgramID;
    public String elementID;
    public String mElementCode;
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
        mElementIDO0TextView = findViewById(R.id.elementRow00elementID);
        mElementNameO0TextView = findViewById(R.id.elementRow00elementName);
        mElementBaseValueO0TextView = findViewById(R.id.elementRow00elementBaseValue);
        mElementIDO1TextView = findViewById(R.id.elementRow01elementID);
        mElementNameO1TextView = findViewById(R.id.elementRow01elementName);
        mElementBaseValueO1TextView = findViewById(R.id.elementRow01elementBaseValue);
        mElementIDO2TextView = findViewById(R.id.elementRow02elementID);
        mElementNameO2TextView = findViewById(R.id.elementRow02elementName);
        mElementBaseValueO2TextView = findViewById(R.id.elementRow02elementBaseValue);

        mElementIDTextView[0] = findViewById(R.id.elementRow00elementID);
        mElementIDTextView[1] = findViewById(R.id.elementRow01elementID);
        mElementIDTextView[2] = findViewById(R.id.elementRow02elementID);

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

        Task<DocumentSnapshot> documentSnapshotTask = elementRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(final DocumentSnapshot documentSnapshot) {
                //initializing shared preferences to save results of query
                //SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                //SharedPreferences.Editor editor = sp.edit();
                Elements qElements = documentSnapshot.toObject(Elements.class);
                elements.add(qElements);
                int eCount = elements.size();
                for (int i = 0; i < eCount; i++) {
                    //It is easier to do this than to figure out dynamic getter name
                    switch (i) {
                        case 0:
                            mElementCode = elements.get(i).getE00();
                            break;
                        case 1:
                            mElementCode = elements.get(i).getE01();
                            break;
                        case 2:
                            mElementCode = elements.get(i).getE02();
                            break;
                        default:
                            mElementCode = null;
                            break;

                    }

                    // Once we have the correct elementcode, we can do the magic
                    if(mElementCode != null) {
                        int currentSOVIndex = Arrays.asList(SOVCode).indexOf(mElementCode);
                        mElementIDTextView[i].setText(mElementCode);
                        mElementNameTextView[i].setText(Arrays.asList(SOVName).get(currentSOVIndex));
                        mElementBaseValueTextView[i].setText(Arrays.asList(SOVBase).get(currentSOVIndex));
                    }
                }
            }
        });

    }

    private void showData() {

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
