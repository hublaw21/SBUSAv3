package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.ElementInfo;
import com.example.khubbart.mysbusaappv3.Model.PlannedProgramContent;
import com.example.khubbart.mysbusaappv3.Model.Program;
import com.example.khubbart.mysbusaappv3.Model.Programv2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramViewActivity extends AppCompatActivity implements
        View.OnClickListener{

    private FirebaseFirestore db;
    private DocumentReference programRef;
    private DocumentReference elementRef;
    private String elementID;

    public List<Program> program = new ArrayList<>();
    public List<PlannedProgramContent> elements = new ArrayList<>();
    public ArrayList<ElementInfo> elementInfoList = new ArrayList<ElementInfo>();

    public int requiredElements;
    public int resID;
    public int i;
    public int tempInt;
    public int num;
    public int eStart;
    public int eEnd;
    public int tempElementIndex;
    public String currentUserUID;
    public String currentProgramID;
    public String currentSkaterName;
    public Programv2 currentProgram;

    public Button elementButton[] = new Button[13];
    public Button saveButton;
    public Button tempButton;
    public TextView mCompetitionNameTextView;
    public TextView textViewProgramDescription;
    public TextView mTechnicalTotalTextView;
    public TextView[] mElementIDTextView = new TextView[13];
    public TextView[] mElementNameTextView = new TextView[13];
    public TextView[] mElementBaseValueTextView = new TextView[13];
    public String mCurrentUserID;
    public String mCurrentProgramID;
    public String[] mElementCode = new String[13];
    public String[] mElementName = new String[13];
    public Double[] mElementBaseValue = new Double[13];
    public String[] RequiredElementsKey;
    public int[] RequiredElementsValue;
    public double technicalTotal;
    public String progPointer;
    public String tempString;
    public String tempTrimmedString;
    public Double tempComboTotal;
    public String tempRowID;
    public String tempRowName;
    public String tempRowBase;
    public String tempButtonString;
    public String tempElementButton;
    public String rawElementInfo;
    public String[] comboCode = new String[4];
    public char[] elementCodeCArray;
    public GlobalClass globalClass;
    public ElementInfo elementInfo;

    NumberFormat numberFormat = new DecimalFormat("###.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_view);
        mCompetitionNameTextView = findViewById(R.id.textViewCompetition);
        textViewProgramDescription = findViewById(R.id.textViewProgramDescription);
        mTechnicalTotalTextView = findViewById(R.id.technicalTotal);
        saveButton = findViewById(R.id.buttonSaveProgram);

        //Set up GlobalClass and retrieve shared constants and methods
        globalClass = ((GlobalClass) getApplicationContext());
        db = FirebaseFirestore.getInstance();
        currentUserUID = globalClass.getCurrentUserUID();
        currentProgramID = globalClass.getCurrentProgramID();
        currentSkaterName = globalClass.getSkaterName();
        //This is trowing an error because we do not get the value of current ProgramID fast enough
        currentProgram = globalClass.retrieveCurrentProgram(globalClass.getCurrentProgramID());

        tempString = currentProgram.getProgramDescription();
        textViewProgramDescription.setText(tempString);

        /*
        //Get everything set up
        initializeVariable();  //Set view variables
        getSOV(); //Load SOV
        prepareButtons(); //Set up the buttions, define one click listener for all
        requiredElements = 12; // For final version, this must be imported with program to establish how many rows to hide
        */
        //Might still need this
        //final LinearLayout relativeLayout = findViewById(R.id.dialog_change_element); // For element change dialog

    /*
        //Get the userID and programID from sending activity
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle.isEmpty()) {
            // deal with empty bundle
        } else {
            // get the info
            mCurrentUserID = extrasBundle.getString("userID");
            mCurrentProgramID = extrasBundle.getString("programID");
            //Log.i("*******************programID: ", mCurrentProgramID);

            //Might want to implement as a separate thread
            init(mCurrentProgramID);  // Call the initiation method here, to ensure the IDs have been pulled
        }
        */

        //Set up button listeners for changing elements

        elementButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonDialog(0);
            }
        });

        elementButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButtonDialog(1);
            }
        });

        elementButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 2;
                changeButtonDialog(num);
            }
        });

        elementButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 3;
                changeButtonDialog(num);
            }
        });
        elementButton[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 4;
                changeButtonDialog(num);
            }
        });

        elementButton[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 5;
                changeButtonDialog(num);
            }
        });
        elementButton[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 6;
                changeButtonDialog(num);
            }
        });

        elementButton[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 7;
                changeButtonDialog(num);
            }
        });
        elementButton[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 8;
                changeButtonDialog(num);
            }
        });

        elementButton[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 9;
                changeButtonDialog(num);
            }
        });

        elementButton[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 10;
                changeButtonDialog(num);
            }
        });

        elementButton[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 11;
                changeButtonDialog(num);
            }
        });
        elementButton[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 12;
                changeButtonDialog(num);
            }
        });

        //Setup Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save elements to firestore
                UpdateElements();
            }
        });
    }

    //Prep buttons
    public void prepareButtons() {
        //Set up button listeners for changing elements
        for (i = 0; i < 13; i++) {
            //Ultimtately, oly set up for number of required elements
            if (i < 10) {
                tempString = "buttonRow0" + i;
            } else {
                tempString = "buttonRow" + i;
            }
            resID = getResources().getIdentifier(tempString, "id", getPackageName());
            elementButton[i] = findViewById(resID);
            elementButton[i].setOnClickListener(this);
        }
        //Setup Save Button
        saveButton.setOnClickListener(this);
    }

    //default listener for all buttons
    @Override
    public void onClick(View view) {
        //Determine which button was pushed
        /*
        buttonPointer = 0;
        tempInt = view.getId();
        for (i = 0; i < requiredElements; i++) {
            //Check for bonus button clicked
            if (findViewById(tempInt) == elementBonusButton[i]) {
                buttonPointer = i;
                elementBonusButtonStatus[i] = elementBonusButton[i].isChecked();
                if (elementBonusButtonStatus[i]) {
                    //Update base value TEXTVIEW ONLY
                    tempString = numberFormat.format(elementBase[i] * 1.1);
                    elementBaseTextView[i].setText(tempString);
                } else {
                    tempString = numberFormat.format(elementBase[i]);
                    elementBaseTextView[i].setText(tempString);
                }
                calcElementScore(buttonPointer); //Update element's score
            }
            //Check/Verify Element Code Button clicked
            if (findViewById(tempInt) == elementCodeButton[i]) {
                tempString = String.valueOf(tempInt);
                Log.i("******elementCodeClick ", tempString);
                //Call Change Button Dialog from Global
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                ElementButtonChangeDialogCaller elementButtonChangeDialogCaller =
                        ElementButtonChangeDialogCaller.newInstance(i);
                elementButtonChangeDialogCaller.show(fm, "dialog");
            }
            */
        //Samples
        elementButton[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 12;
                changeButtonDialog(num);
            }
        });

        //Setup Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save elements to firestore
                UpdateElements();
            }
        });
    }
    // Initialize database, pull program and elements
    public void init(String iCurrentProgramID) {
        // Do I need these?
        //final SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        //final SharedPreferences.Editor editor = sp.edit();
        //if (iCurrentProgramID == null) {
        if (mCurrentProgramID == null) {
            // Deal with new/null program
        } else {
            //Get program basics
            programRef = db.collection("Programs").document(mCurrentProgramID);
            Log.i("*******programID: ", mCurrentProgramID);
            //Task<DocumentSnapshot> documentSnapshotTask = programRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            //programRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            //programRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            programRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                //public void onComplete(final DocumentSnapshot documentSnapshot) {
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Program qProgram = documentSnapshot.toObject(Program.class);
                        program.add(qProgram);
                        mCompetitionNameTextView.setText(program.get(0).getCompetition());
                        String tempText = program.get(0).getLevel() + " " + program.get(0).getDiscipline() + " " + program.get(0).getSegment() + " Program";
                        progPointer = program.get(0).getLevel() + program.get(0).getDiscipline() + program.get(0).getSegment();
                        //Log.i("*******************programRef: ", mCurrentProgramID);
                        //Log.i("*******************progPointer: ", progPointer);
                        tempInt = Arrays.asList(RequiredElementsKey).indexOf(progPointer);
                        requiredElements = RequiredElementsValue[tempInt];
                        textViewProgramDescription.setText(tempText);
                        elementID = program.get(0).getElementsID();
                        //Log.i("*******************elementID: ", elementID);
                        pullElements(elementID);
                    }
                }
            });
        }
    }

    ;

    public void initializeVariable() {
        //Set view variables
        for (int i = 0; i < 13; i++) {
            if (i < 10) {
                tempRowID = "elementRow0" + i + "elementID";
                tempRowName = "elementRow0" + i + "elementName";
                tempRowBase = "elementRow0" + i + "elementBaseValue";
                tempElementButton = "buttonRow0" + i;
            } else {
                tempRowID = "elementRow" + i + "elementID";
                tempRowName = "elementRow" + i + "elementName";
                tempRowBase = "elementRow" + i + "elementBaseValue";
                tempElementButton = "buttonRow" + i;
            }
            resID = getResources().getIdentifier(tempRowID, "id", getPackageName());
            mElementIDTextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempRowName, "id", getPackageName());
            mElementNameTextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempRowBase, "id", getPackageName());
            mElementBaseValueTextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempElementButton, "id", getPackageName());
            elementButton[i] = findViewById(resID);
        }
    }

    public void pullElements(String mElementsID) {
        elementRef = db.collection("Elements").document(mElementsID);
        elementRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    PlannedProgramContent qPlannedProgramContent = documentSnapshot.toObject(PlannedProgramContent.class);
                    elements.add(qPlannedProgramContent);
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
                    for (int i = 0; i < requiredElements; i++) {
                        Log.i("****elementCode", mElementCode[i]);
                        if (mElementCode[i] != null) {
                            tempElementIndex = globalClass.ElementIndexLookUp(mElementCode[i]);
                            if (tempElementIndex > 0) {
                                //Get element information for typical (non-combo) elements
                                elementInfo = new ElementInfo();
                                elementInfo.setRawElementCode(mElementCode[i]);
                                elementInfo.setElementSOVIndex(tempElementIndex);
                                elementInfo.setElementCode(mElementCode[i]);
                                elementInfo.setElementName(globalClass.ElementNameLookUp(tempElementIndex));
                                elementInfo.setElementBaseValue(globalClass.ElementBaseLookUp(tempElementIndex));
                                changeButton(i, elementInfo.getElementCode());
                                mElementNameTextView[i].setText(elementInfo.getElementName());
                                tempString = numberFormat.format(elementInfo.getElementBaseValue());
                                mElementBaseValueTextView[i].setText(tempString);
                            } else {
                                //Check for combo jump
                                tempString = globalClass.ElementCheckForCombo(mElementCode[i]);
                                if (tempString == "Combo") {
                                    //Parse combo element
                                    checkForComboJump(mElementCode[i], i);
                                }
                            }
                        } else {
                            elementInfo = new ElementInfo();
                            elementInfo.setRawElementCode("");
                            elementInfo.setElementSOVIndex(0);
                            elementInfo.setElementCode("Code");
                            elementInfo.setElementName("");
                            elementInfo.setElementBaseValue(0.0);

                        }
                        //Set the element information into the array for use
                        elementInfoList.add(elementInfo);
                    }
                    // Set the initial totalTechnical
                    tempString = "Total Base Value: " + numberFormat.format(sumTech());
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

    private void UpdateElements() {
        //elementRef should still be good
        elementRef.update("E00", mElementCode[0]);
        elementRef.update("E01", mElementCode[1]);
        elementRef.update("E02", mElementCode[2]);
        elementRef.update("E03", mElementCode[3]);
        elementRef.update("E04", mElementCode[4]);
        elementRef.update("E05", mElementCode[5]);
        elementRef.update("E06", mElementCode[6]);
        elementRef.update("E07", mElementCode[7]);
        elementRef.update("E08", mElementCode[8]);
        elementRef.update("E09", mElementCode[9]);
        elementRef.update("E10", mElementCode[10]);
        elementRef.update("E11", mElementCode[11]);
        elementRef.update("E12", mElementCode[12])
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProgramViewActivity.this, "Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*
    public void getSOV() {
        //Get SOV Table 2018 - Three matched arrays
        Resources resources = getResources();
        SOVCode = resources.getStringArray(R.array.SOV_Code);
        SOVName = resources.getStringArray(R.array.SOV_Name);
        SOVBase = resources.getStringArray(R.array.SOV_Base);
        RequiredElementsKey = resources.getStringArray(R.array.requiredElementsKeyArray);
        RequiredElementsValue = resources.getIntArray(R.array.requiredElementsValueArray);
    }
    */
    public void getSOV() {
        Resources resources = getResources();
        RequiredElementsKey = resources.getStringArray(R.array.requiredElementsKeyArray);
        RequiredElementsValue = resources.getIntArray(R.array.requiredElementsValueArray);
    }


    /*
    public void pullElementInfo(String pElementCode, int pNum) {
        int currentSOVIndex = Arrays.asList(SOVCode).indexOf(pElementCode);
        // Need to add error checker for code not found
        if (currentSOVIndex > 0) {
            mElementName[pNum] = Arrays.asList(SOVName).get(currentSOVIndex);
            mElementBaseValue[pNum] = Double.valueOf(Arrays.asList(SOVBase).get(currentSOVIndex));
            mElementCode[pNum] = pElementCode;
            changeButton(pNum, pElementCode);
            mElementNameTextView[pNum].setText(mElementName[pNum]);
            tempString = numberFormat.format(mElementBaseValue[pNum]);
            mElementBaseValueTextView[pNum].setText(tempString);
            elementInfoList.add(new ElementInfo(mElementCode[pNum], mElementName[pNum], mElementBaseValue[pNum]));
        } else {
            //Check for combo or change to unfound////
            Log.i("*******************pElementCode: ", pElementCode);
            if (pElementCode != null) checkForComboJump(pElementCode, pNum);
            //Add info for unentered required elements
            //elementInfoList.add(new ElementInfo(mElementCode[num], mElementName, mElementBaseValue)); // Do I keep adding or replace, do I even need if I am putting in array?
        }
        tempString = "Total Base Value: " + numberFormat.format(sumTech());
        mTechnicalTotalTextView.setText(tempString);
    }
    */

    public void checkForComboJump(String cElementCode, int cNum) {
        //Toast.makeText(ProgramViewActivity.this, cElementCode, Toast.LENGTH_SHORT).show();
        tempTrimmedString = cElementCode.replaceAll("\\s+", ""); //Trim any spaces - CAREFUL, throws an error if null
        elementCodeCArray = tempTrimmedString.toCharArray();
        num = 0;
        eStart = 0;
        eEnd = 0;
        tempComboTotal = 0.0;
        for (int j = 0; j < elementCodeCArray.length; j++) {
            tempString = String.copyValueOf(elementCodeCArray, j, 1);
            if (tempString.equals("+")) {
                //We have a combo
                eEnd = j; // IN substring, end position is not included in extract
                comboCode[num] = tempTrimmedString.substring(eStart, eEnd);
                Log.i("*************comboCode ", comboCode[num]);
                //int currentSOVIndex = Arrays.asList(SOVCode).indexOf(comboCode[num]);
                int currentSOVIndex = globalClass.ElementIndexLookUp(comboCode[num]);
                if (currentSOVIndex > 0)
                    tempComboTotal += globalClass.ElementBaseLookUp(currentSOVIndex);
                Log.i("*******tempTotal", numberFormat.format(tempComboTotal));
                num = num + 1;
                eStart = j + 1;
            }
        }
        if (tempComboTotal > 0) {
            //Add last item of combo
            comboCode[num] = tempTrimmedString.substring(eStart); // This should take it thru the end
            int currentSOVIndex = globalClass.ElementIndexLookUp(comboCode[num]);
            if (currentSOVIndex > 0) {
                tempComboTotal += globalClass.ElementBaseLookUp(currentSOVIndex);
                //Save element information to listarray
                elementInfo = new ElementInfo();
                elementInfo.setRawElementCode(cElementCode);
                elementInfo.setElementSOVIndex(tempElementIndex);
                elementInfo.setElementCode("Combo");
                elementInfo.setElementName(tempTrimmedString);
                elementInfo.setElementBaseValue(tempComboTotal);
                //Set up display information
                changeButton(cNum, elementInfo.getElementCode());
                mElementNameTextView[cNum].setText(elementInfo.getElementName());
                tempString = numberFormat.format(elementInfo.getElementBaseValue());
                mElementBaseValueTextView[cNum].setText(tempString);
            }
        } else { //Add info for non-combo and not found code
            mElementCode[cNum] = null;
            mElementName[cNum] = cElementCode + " not found";
            cElementCode = "Add";
            changeButton(cNum, cElementCode);
            mElementBaseValueTextView[cNum].setText("0.00");
            changeButton(cNum, cElementCode);
            mElementNameTextView[cNum].setText(mElementName[cNum]);
            mElementBaseValueTextView[cNum].setText(numberFormat.format(mElementBaseValue[cNum]));
        }
    }


    //A basic method to get total technical value
    public Double sumTech() {
        double sumTech = 0;
        for (int i = 0; i < requiredElements; i++) {
            if (elementInfoList.get(i).getElementBaseValue() != null) {
                sumTech += elementInfoList.get(i).getElementBaseValue();
            }
        }
        return sumTech;
    }

    public void changeButton(int cNum, String tElementCode) {
        // Change the button text to match element code
        if (cNum < 10) {
            tempString = "buttonRow0" + cNum;
        } else {
            tempString = "buttonRow" + cNum;
        }
        int resID = getResources().getIdentifier(tempString, "id", getPackageName());
        tempButton = findViewById(resID);
        if (tempButton != null) tempButton.setText(tElementCode);
        // put button in here
    }

    public void changeButtonDialog(final int tNum) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_change_element, null);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        Button submitButton = (Button) dialogView.findViewById(R.id.dialog_change_element_submit_button);
        Button lookupButton = (Button) dialogView.findViewById(R.id.dialog_change_element_lookup_button);
        Button cancelButton = (Button) dialogView.findViewById(R.id.dialog_change_element_cancel_button);
        final EditText editTextElementCode = (EditText) dialogView.findViewById(R.id.edittext_change_element_code);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                String tElementCode = editTextElementCode.getText().toString();
                //int ttNum = tNum; // needs to track element number from list
                //pullElementInfo(tElementCode, tNum); - 9/10/18 - I will need this
            }
        });

        // Set lookup button click listener
        lookupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/cancel the alert dialog
                dialog.dismiss();
                // Add lookup routine here
                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.activity_element_lookup, null);
                    builder.setCancelable(false);
                    builder.setView(dialogView);
                    Intent intentBundle = new Intent(ProgramViewActivity.this, ElementLookupActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("eleNum",num);
                    intentBundle.putExtras(bundle);
                    startActivity(intentBundle);
                    */
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
