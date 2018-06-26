package com.example.khubbart.mysbusaappv3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.ElementInfo;
import com.example.khubbart.mysbusaappv3.Model.Elements;
import com.example.khubbart.mysbusaappv3.Model.Program;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramViewActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private DocumentReference programRef;
    private DocumentReference elementRef;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String ELEMENT_ID_KEY = "elementIdKey";
    public List<Program> program = new ArrayList<>();
    public List<Elements> elements = new ArrayList<>();
    public ArrayList<ElementInfo> elementInfoList = new ArrayList<ElementInfo>();

    public int requiredElements;
    public int resID;
    public int i;
    public int num;

    public Button elementButton[] = new Button[13];
    public Button tempButton;
    public TextView mCompetitionNameTextView;
    public TextView mCompetitionDescriptionTextView;
    public TextView mTechnicalTotalTextView;
    public TextView textViewForTesting;
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
    public double technicalTotal;
    public String tempString;
    public String tempRowID;
    public String tempRowName;
    public String tempRowBase;
    public String tempButtonString;
    public String tElementCode;

    NumberFormat numberFormat = new DecimalFormat("###.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_view);
        mCompetitionNameTextView = findViewById(R.id.textViewCompetition);
        mCompetitionDescriptionTextView = findViewById(R.id.textViewProgramDescription);
        mTechnicalTotalTextView = findViewById(R.id.technicalTotal);
        requiredElements = 12; // For final version, this must be imported with program to establish how many rows to hide
        db = FirebaseFirestore.getInstance();

        elementButton[0] = findViewById(R.id.buttonRow00);
        elementButton[1] = findViewById(R.id.buttonRow01);


        //Set view variables
        for (int i = 0; i < 13; i++) {
            if (i < 10) {
                tempRowID = "elementRow0" + i + "elementID";
                tempRowName = "elementRow0" + i + "elementName";
                tempRowBase = "elementRow0" + i + "elementBaseValue";
                tempButtonString = "buttonRow0" + i;
            } else {
                tempRowID = "elementRow" + i + "elementID";
                tempRowName = "elementRow" + i + "elementName";
                tempRowBase = "elementRow" + i + "elementBaseValue";
                tempButtonString = "buttonRow" + i;
            }
            resID = getResources().getIdentifier(tempRowID, "id", getPackageName());
            mElementIDTextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempRowName, "id", getPackageName());
            mElementNameTextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempRowBase, "id", getPackageName());
            mElementBaseValueTextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempButtonString, "id", getPackageName());
            elementButton[i] = findViewById(resID);
        }

        //Load SOV
        getSOV();

        //Get the userID and programID from sending activity
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle.isEmpty()) {
            // deal with empty bundle
        } else {
            // get the info
            mCurrentUserID = extrasBundle.getString("userID");
            mCurrentProgramID = extrasBundle.getString("programID");
            //Toast.makeText(getApplicationContext(), mCurrentUserID + " " + mCurrentProgramID, Toast.LENGTH_LONG).show();
            init();  // Call the initiation method here, to ensure the IDs have been pulled
        }

        //Try adding a button and have this implement in the onclick method to see if the values are making it back to the main thread.
        //Set up button listeners for changing elements

        elementButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showPickElementDialog();
            }
        });

        elementButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 1 Clicked", Toast.LENGTH_LONG).show();
            }
        });
        elementButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 0 Clicked", Toast.LENGTH_LONG).show();
            }
        });

        elementButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 1 Clicked", Toast.LENGTH_LONG).show();
            }
        });
        elementButton[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 0 Clicked", Toast.LENGTH_LONG).show();
            }
        });

        elementButton[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 1 Clicked", Toast.LENGTH_LONG).show();
            }
        });
        elementButton[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 0 Clicked", Toast.LENGTH_LONG).show();
            }
        });

        elementButton[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 1 Clicked", Toast.LENGTH_LONG).show();
            }
        });
        elementButton[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 0 Clicked", Toast.LENGTH_LONG).show();
            }
        });

        elementButton[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 1 Clicked", Toast.LENGTH_LONG).show();
            }
        });

        elementButton[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 0 Clicked", Toast.LENGTH_LONG).show();
            }
        });

        elementButton[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 1 Clicked", Toast.LENGTH_LONG).show();
            }
        });
        elementButton[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Element Button 0 Clicked", Toast.LENGTH_LONG).show();
            }
        });

        //Set popups for adding/changing elements
    }


    // Initialize database
    public void init() {
        // Do I neeed these?
        final SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        //Get program basics
        programRef = db.collection("Programs").document(mCurrentProgramID);
        Task<DocumentSnapshot> documentSnapshotTask = programRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(final DocumentSnapshot documentSnapshot) {
                Program qProgram = documentSnapshot.toObject(Program.class);
                program.add(qProgram);
                mCompetitionNameTextView.setText(program.get(0).getCompetition());
                String tempText = program.get(0).getLevel() + " " + program.get(0).getDiscipline() + " " + program.get(0).getSegment() + " Program";
                mCompetitionDescriptionTextView.setText(tempText);
                String elementID = program.get(0).getElementsID();
                editor.putString(ELEMENT_ID_KEY, elementID);
                editor.apply();
                getElements();
            }
        });
    }

    public void getElements() {

        final SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        //Get elements by documentID
        elementID = sp.getString(ELEMENT_ID_KEY, null);
        if (elementID != null) {
            //Do nothing, element's documentID found
        } else {
            textViewForTesting.setText("No elementID value");
        }

        elementRef = db.collection("Elements").document(elementID);

        elementRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
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
                    technicalTotal = 0;
                    for (int i = 0; i < requiredElements; i++) {
                        if (mElementCode[i] != null) {
                            // Get the index to look up the element
                            int currentSOVIndex = Arrays.asList(SOVCode).indexOf(mElementCode[i]);
                            //Ultimately, need to make the elementID a button, or make the row a button
                            if (currentSOVIndex > 0) {
                                mElementName = Arrays.asList(SOVName).get(currentSOVIndex);
                                mElementBaseValue = Arrays.asList(SOVBase).get(currentSOVIndex);
                                technicalTotal += Double.valueOf(mElementBaseValue);
                                num = i;
                                tElementCode = mElementCode[i];
                                changeButton(num, tElementCode);
                                //mElementIDTextView[i].setText(mElementCode[i]);
                                mElementNameTextView[i].setText(Arrays.asList(SOVName).get(currentSOVIndex));
                                mElementBaseValueTextView[i].setText(Arrays.asList(SOVBase).get(currentSOVIndex));
                                elementInfoList.add(new ElementInfo(mElementCode[i], mElementName, mElementBaseValue));
                            } else {
                                //Add info for unentered required elements
                            }

                        } else {
                            // Put buttons into blanks rows
                            if (i < 10) {
                                tempString = "elementRow0" + i;
                            } else {
                                tempString = "elementRow" + i;
                            }
                            int resID = getResources().getIdentifier(tempString, "id", getPackageName());
                            TableRow tr = findViewById(resID);
                            // put button in here
                        }
                    }
                    // Set the initial totalTechnical
                    tempString = "Total Base Value: " + numberFormat.format(technicalTotal);
                    mTechnicalTotalTextView.setText(tempString);
                    // Hide rows not used for the program
                    for (i = requiredElements; i < 13; i++) {
                        if (i < 10) {
                            tempString = "elementRow0" + i;
                        } else {
                            tempString = "elementRow" + i;
                        }
                        int resID = getResources().getIdentifier(tempString, "id", getPackageName());
                        TableRow tr = findViewById(resID);
                        tr.setVisibility(View.GONE);
                    }
                }
            }
        });
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

    public void changeButton(int num, String tElementCode) {
        // Change the button text to match element code
        if (num < 10) {
            tempString = "buttonRow0" + num;
        } else {
            tempString = "buttonRow" + num;
        }
        int resID = getResources().getIdentifier(tempString, "id", getPackageName());
        tempButton = findViewById(resID);
        if (tempButton != null) tempButton.setText(tElementCode);
        // put button in here
    }

    /*
    private void showPickElementDialog() {
        String[] singleChoiceItems = getResources().getStringArray(R.array.elementTypes);
        int itemSelected = 0;
        new AlertDialog.Builder(this)
                .setTitle("Select Element Type")
                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        Toast.makeText(getApplicationContext(), "index: " + selectedIndex, Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show();
    }
    */

}
