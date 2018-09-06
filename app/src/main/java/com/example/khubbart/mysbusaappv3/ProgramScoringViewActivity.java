package com.example.khubbart.mysbusaappv3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.khubbart.mysbusaappv3.Model.ElementInfo;
import com.example.khubbart.mysbusaappv3.Model.ElementItem;
import com.example.khubbart.mysbusaappv3.Model.Elements;
import com.example.khubbart.mysbusaappv3.Model.Program;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramScoringViewActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener,
        AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    public TextView programDescriptionTextView;
    public TextView[] scoresTextView = new TextView[4];
    public TextView[] elementIDTextView = new TextView[13];
    public Button[] elementTicButton = new Button[13];
    public ToggleButton[] elementBonusButton = new ToggleButton[13];
    public TextView[] elementTicTextView = new TextView[13];
    public TextView[] elementBaseTextView = new TextView[13];
    public TextView[] elementGOETextView = new TextView[13];
    public TextView[] elementScoreTextView = new TextView[13];
    public TextView[] componentFactorTextView = new TextView[5];
    public TextView[] componentRawTextView = new TextView[5];
    public TextView[] componentScoreTextView = new TextView[5];

    private List<ElementItem> elementScoreList = new ArrayList<>();
    public Spinner[] ticSpinner = new Spinner[13];

    public int progress = 50; // the midway point, zeroed.  Need to set up for adjustable
    public int resID;
    public Button[] ticDialogButton = new Button[6];

    public String tempString;
    public String tempRowID;
    public String tempRowBonus;
    public String tempRowTic;
    public String tempRowBase;
    public String tempRowGOE;
    public String tempRowScore;
    public Button tempButton;
    public String mCurrentUserID;
    public String mCurrentProgramID;
    public String programDescription;
    public String[] elementCode = new String[13];
    public boolean[] elementBonusButtonStatus = new boolean[13];
    public String[] elementTic = new String[13];
    public String[] ticArray = new String[6];
    public Double[] elementBase = new Double[13];
    public Double[] jumpBase = new Double[13];
    public Double[] elementGOE = new Double[13];
    public int[] elementTicIndex = new int[13];
    public Double[] elementBonus = new Double[13]; // Calculate and add in the value when loading the element
    public Double[] elementScore = new Double[13];
    public Double[] componentFactor = new Double[5];
    public Double[] factors = new Double[8];
    public Double[] componentScore = new Double[5];
    public String[] SOVCode;
    public String[] SOVName;
    public String[] SOVBase;
    public List<Program> program = new ArrayList<>();
    public List<Elements> elements = new ArrayList<>();
    public ArrayList<ElementInfo> elementInfoList = new ArrayList<ElementInfo>();
    public int requiredElements;
    public int i;
    public int tempInt;
    public int tempBarId;
    public int buttonPointer;
    public int num;
    public int eStart;
    public int eEnd;
    public String progPointer;
    public String[] RequiredElementsKey;
    public int[] RequiredElementsValue;
    public double elementTotal;
    public double componentTotal;
    public double deductionTotal;
    public double segmentTotal;
    public String tempTrimmedString;
    public Double tempComboTotal;
    public Double tempDouble1;
    public Double tempDouble2;
    public Double tempBase;
    public Double tempGOE;
    public SeekBar[] goeBar = new SeekBar[13];
    public SeekBar[] compBar = new SeekBar[5];
    public int[] goeBarId = new int[13];

    public String[] comboCode = new String[4];
    public char[] elementCodeCArray;

    public GlobalClass globalClass;

    NumberFormat numberFormat = new DecimalFormat("###.00");
    NumberFormat numberFormatGOE = new DecimalFormat("#.0");

    private FirebaseFirestore db;
    private DocumentReference programRef;
    private DocumentReference elementRef;
    private String elementID;

    private GOESeekBar bar;
    private TextView textView;
    private int oldLocation = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_scoring_view);

        /*
        //Testing Seekerbar with thumbtext
        bar = (GOESeekBar) findViewById(R.id.seekBarComponentSkills);
        textView = (TextView) findViewById(R.id.text);
        bar.setMax(100);
        bar.setProgress(0);
        //bar.setThumb(ProgramScoringViewActivity.this.getResources().getDrawable(R.drawable.small_bronze_fly));
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                p.addRule(RelativeLayout.ABOVE, seekBar.getId());
                Rect thumbRect = bar.getSeekBarThumb().getBounds();
                p.setMargins(
                        thumbRect.centerX(), 0, 0, 0);
                textView.setLayoutParams(p);
                textView.setText(String.valueOf(progress) + " ft.");
            }
        });
        //End seekerbar Testing
        */

        //Set up GlobalClass for shared constants and methods
        globalClass = ((GlobalClass)

                getApplicationContext());

        db = FirebaseFirestore.getInstance();

        // Nset up arrays with view names, seekbars etc
        initializeVariables();

        //Get the userID and programID from sending activity
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle.isEmpty())

        {
            // deal with empty bundle
        } else

        {
            // get the info
            mCurrentUserID = extrasBundle.getString("userID");
            mCurrentProgramID = extrasBundle.getString("programID");

            //Might want to implement as a separate thread
            init();  // Call the initiation method here, to ensure the IDs have been pulled
        }
    }

    //default listener for all buttons
    @Override
    public void onClick(View view) {
        //Determine which button was pushed
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
            //Check/Verify tic button clicked
            if (findViewById(tempInt) == elementTicButton[i]) {
                tempString = String.valueOf(tempInt);
                Log.i("*******************ticClick ", tempString);
                tempInt = 0; // have to reset or alert dialog buttons cause a loop
                ticDialog(i);
            }
        }
    }

    //default listener for all seeker bars
    @Override
    public void onProgressChanged(SeekBar bar, int progressValue, boolean fromUser) {
        for (i = 0; i < requiredElements; i++) {
            if (bar == goeBar[i]) {
                calcElementGOE(progressValue, i);
            }
        }
        for (i = 0; i < 5; i++) {
            if (bar == compBar[i]) {
                compBarUpdate(progressValue, i);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar bar) {
        //No action needed on starting to track
    }

    @Override
    public void onStopTrackingTouch(SeekBar bar) {
        //No update needed, the last progress should have covered it
    }

    // A private method to help us initialize our variables.
    private void initializeVariables() {
        scoresTextView[0] = findViewById(R.id.segmentTotal);
        scoresTextView[1] = findViewById(R.id.elementTotal);
        scoresTextView[2] = findViewById(R.id.componentTotal);
        scoresTextView[3] = findViewById(R.id.deductionsTotal);
        programDescriptionTextView = findViewById(R.id.textViewProgramDescription);

        //Get SOV Table 2018 - Three matched arrays
        Resources resources = getResources();
        SOVCode = resources.getStringArray(R.array.SOV_Code);
        SOVName = resources.getStringArray(R.array.SOV_Name);
        SOVBase = resources.getStringArray(R.array.SOV_Base);
        ticArray = resources.getStringArray(R.array.tics);
        RequiredElementsKey = resources.getStringArray(R.array.requiredElementsKeyArray);
        RequiredElementsValue = resources.getIntArray(R.array.requiredElementsValueArray);

        //default listener for all seeker bars
        for (i = 0; i < 13; i++) {
            if (i < 10) {
                tempString = "seekBarElement0" + i;
            } else {
                tempString = "seekBarElement" + i;
            }
            resID = getResources().getIdentifier(tempString, "id", getPackageName());
            goeBar[i] = (SeekBar) this.findViewById(resID);
            goeBar[i].setOnSeekBarChangeListener(this);
        }

        compBar[0] = this.findViewById(R.id.seekBarComponentSkills);
        compBar[2] = this.findViewById(R.id.seekBarComponentPerformance);
        compBar[1] = this.findViewById(R.id.seekBarComponentTransitions);
        compBar[3] = this.findViewById(R.id.seekBarComponentComposition);
        compBar[4] = this.findViewById(R.id.seekBarComponentInterpretation);
        //compBar[0].setOnSeekBarChangeListener(this);
        compBar[1].setOnSeekBarChangeListener(this);
        compBar[2].setOnSeekBarChangeListener(this);
        compBar[3].setOnSeekBarChangeListener(this);
        compBar[4].setOnSeekBarChangeListener(this);

        //Set view variables
        for (int i = 0; i < 13; i++) {
            if (i < 10) {
                tempRowID = "elementIdRow0" + i;
                tempRowBonus = "elementBonusToggle0" + i;
                tempRowTic = "elementTicButton0" + i;
                tempRowBase = "elementBaseRow0" + i;
                tempRowGOE = "elementGOERow0" + i;
                tempRowScore = "elementScoreRow0" + i;
            } else {
                tempRowID = "elementIdRow" + i;
                tempRowBonus = "elementBonusToggle" + i;
                tempRowTic = "elementTicButton" + i;
                tempRowBase = "elementBaseRow" + i;
                tempRowGOE = "elementGOERow" + i;
                tempRowScore = "elementScoreRow" + i;
            }

            resID = getResources().getIdentifier(tempRowID, "id", getPackageName());
            elementIDTextView[i] = findViewById(resID);

            resID = getResources().getIdentifier(tempRowBonus, "id", getPackageName());
            elementBonusButton[i] = findViewById(resID);
            elementBonusButton[i].setOnClickListener(this);

            resID = getResources().getIdentifier(tempRowTic, "id", getPackageName());
            elementTicButton[i] = findViewById(resID);
            elementTicButton[i].setOnClickListener(this);

            resID = getResources().getIdentifier(tempRowBase, "id", getPackageName());
            elementBaseTextView[i] = findViewById(resID);

            resID = getResources().getIdentifier(tempRowGOE, "id", getPackageName());
            elementGOETextView[i] = findViewById(resID);

            resID = getResources().getIdentifier(tempRowScore, "id", getPackageName());
            elementScoreTextView[i] = findViewById(resID);
        }
        //tempString = ""
        componentRawTextView[0] = findViewById(R.id.componentSkillsValue);
        componentRawTextView[1] = findViewById(R.id.componentTransitionsValue);
        componentRawTextView[2] = findViewById(R.id.componentPerformanceValue);
        componentRawTextView[3] = findViewById(R.id.componentCompositionValue);
        componentRawTextView[4] = findViewById(R.id.componentInterpretationValue);
        componentScoreTextView[0] = findViewById(R.id.componentSkillsScore);
        componentScoreTextView[1] = findViewById(R.id.componentTransitionsScore);
        componentScoreTextView[2] = findViewById(R.id.componentPerformanceScore);
        componentScoreTextView[3] = findViewById(R.id.componentCompositionScore);
        componentScoreTextView[4] = findViewById(R.id.componentInterpretationScore);
        componentFactorTextView[0] = findViewById(R.id.componentSkillsFactor);
        componentFactorTextView[1] = findViewById(R.id.componentTransitionsFactor);
        componentFactorTextView[2] = findViewById(R.id.componentPerformanceFactor);
        componentFactorTextView[3] = findViewById(R.id.componentCompositionFactor);
        componentFactorTextView[4] = findViewById(R.id.componentInterpretationFactor);
    }

    //Update info based on seeker bar - elements
    private void seekerBarUpdate(int uProgress, int uElenum) {
        tempDouble1 = (double) uProgress;
        tempString = String.valueOf((tempDouble1 - 50) / 10);
        elementGOETextView[uElenum].setText(tempString);
    }

    //Update info based on seeker bar - components
    private void compBarUpdate(int uProgress, int uCompNum) {
        tempDouble1 = (double) uProgress;
        tempDouble2 = tempDouble1 / 10;
        tempString = String.valueOf(tempDouble2);
        componentRawTextView[uCompNum].setText(tempString);
        componentFactor[uCompNum] = factors[uCompNum];
        componentScore[uCompNum] = tempDouble2 * factors[uCompNum + 1] * factors[6]; // factors[6] is general program component
        tempString = String.valueOf(componentScore[uCompNum]);
        componentScoreTextView[uCompNum].setText(tempString);
        tempString = String.valueOf(factors[uCompNum + 1]);
        scoresTextView[2].setText(numberFormat.format(sumComponents()));
        scoresTextView[0].setText(numberFormat.format(sumSegment()));
    }

    // Initialize database, pull program and elements
    public void init() {
        if (mCurrentProgramID == null) {
            // Deal with new/null program
        } else {
            //Get program basics
            programRef = db.collection("Programs").document(mCurrentProgramID);
            programRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Program qProgram = documentSnapshot.toObject(Program.class);
                        program.add(qProgram);
                        String tempText = program.get(0).getLevel() + " " + program.get(0).getDiscipline() + " " + program.get(0).getSegment() + " Program";
                        programDescriptionTextView.setText(tempText);
                        programDescription = program.get(0).getLevel() + program.get(0).getDiscipline() + program.get(0).getSegment();
                        factors = globalClass.getFactors(programDescription);
                        progPointer = program.get(0).getLevel() + program.get(0).getDiscipline() + program.get(0).getSegment();
                        tempInt = Arrays.asList(RequiredElementsKey).indexOf(progPointer);
                        requiredElements = RequiredElementsValue[tempInt];
                        elementID = program.get(0).getElementsID();
                        tempString = String.valueOf(requiredElements);
                        pullElements(elementID); // Needs to be here because cannot pull elements until program is pulled from database
                    }
                }
            });
        }
    }

    ;

    public void pullElements(String mElementsID) {
        elementRef = db.collection("Elements").document(mElementsID);
        elementRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Elements qElements = documentSnapshot.toObject(Elements.class);
                    elements.add(qElements);
                    elementCode[0] = elements.get(0).getE00();
                    elementCode[1] = elements.get(0).getE01();
                    elementCode[2] = elements.get(0).getE02();
                    elementCode[3] = elements.get(0).getE03();
                    elementCode[4] = elements.get(0).getE04();
                    elementCode[5] = elements.get(0).getE05();
                    elementCode[6] = elements.get(0).getE06();
                    elementCode[7] = elements.get(0).getE07();
                    elementCode[8] = elements.get(0).getE08();
                    elementCode[9] = elements.get(0).getE09();
                    elementCode[10] = elements.get(0).getE10();
                    elementCode[11] = elements.get(0).getE11();
                    elementCode[12] = elements.get(0).getE12();
                    for (int i = 0; i < requiredElements; i++) {
                        if (elementCode[i] != null) {
                            pullElementInfo(elementCode[i], i);
                        }
                    }
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
                scoresTextView[1].setText(numberFormat.format(sumElements()));
            }
        });
        //Set initial comp scores too
        for (int i = 0; i < 5; i++) {
            if (factors[i + 1] != null) {
                tempDouble2 = 5.0; // This could be an option for users
                tempString = String.valueOf(tempDouble2);
                componentRawTextView[i].setText(tempString);
                componentScore[i] = tempDouble2 * factors[i + 1] * factors[6]; // comp factors start at 1, factors[6] is general program component
                tempString = String.valueOf(componentScore[i]);
                componentScoreTextView[i].setText(tempString);
                componentFactorTextView[i].setText(numberFormat.format(factors[i + 1]));
            }
        }
        scoresTextView[0].setText(numberFormat.format(sumSegment()));
        scoresTextView[2].setText(numberFormat.format(sumComponents()));
    }

    public void pullElementInfo(String pElementCode, int pNum) {
        int currentSOVIndex = Arrays.asList(SOVCode).indexOf(pElementCode);
        // Need to add error checker for code not found
        if (currentSOVIndex > 0) {
            elementBase[pNum] = Double.valueOf(Arrays.asList(SOVBase).get(currentSOVIndex));
            elementScore[pNum] = elementBase[pNum];
            elementCode[pNum] = pElementCode;
            elementGOE[pNum] = 0.0; //Must initialize a value
            jumpBase[pNum] = 0.0; //Must initialize a value
            tempString = elementCode[pNum];
            elementIDTextView[pNum].setText(elementCode[pNum]);
            tempString = numberFormat.format(elementBase[pNum]);
            elementBaseTextView[pNum].setText(tempString);
            elementScoreTextView[pNum].setText(tempString);//Can initialize to both because GOE is initially 0
            tempString = String.valueOf(currentSOVIndex);
            Log.i("**************SOV Index: ", tempString);
            if (currentSOVIndex > 23) {
                //Hide and disables tic and bonus notation
                elementTicButton[pNum].setText("");
                elementTicIndex[pNum] = 0;
                elementBonusButton[pNum].setText("");
                elementBonusButton[pNum].setEnabled(false); // Disable buttons for non-jumps.   Jumps are 0-24 in array
                elementTicButton[pNum].setEnabled(false); // Disable buttons for non-jumps.   Jumps are 0-24 in array
            } else {
                //Initiate tic button for jumps
                elementTicButton[pNum].setText(ticArray[0]);
                elementTicIndex[pNum] = 0;
                elementBonusButton[pNum].setEnabled(true); // Enable buttons for non-jumps.   Jumps are 0-24 in array
                elementTicButton[pNum].setEnabled(true);
            }
        } else {
            //Check for combo or change to unfound////
            if (pElementCode != null) checkForComboJump(pElementCode, pNum);
        }
    }

    public void checkForComboJump(String cElementCode, int cNum) {
        tempTrimmedString = cElementCode.replaceAll("\\s+", ""); //Trim any spaces - CAREFUL, throws an error if null
        elementCodeCArray = tempTrimmedString.toCharArray();
        num = 0;
        eStart = 0;
        eEnd = 0;
        tempComboTotal = 0.0;
        jumpBase[cNum] = 0.0;
        for (i = 0; i < elementCodeCArray.length; i++) {
            tempString = String.copyValueOf(elementCodeCArray, i, 1);
            if (tempString.equals("+")) {
                //We have a combo
                eEnd = i; // In substring, end position is not included in extract
                comboCode[num] = tempTrimmedString.substring(eStart, eEnd);
                int currentSOVIndex = Arrays.asList(SOVCode).indexOf(comboCode[num]);
                if (currentSOVIndex > 0)
                    tempDouble1 = Double.valueOf(Arrays.asList(SOVBase).get(currentSOVIndex));
                if (tempDouble1 > jumpBase[cNum])
                    jumpBase[cNum] = tempDouble1; // Save the highest base in a combo for GOE calc
                tempComboTotal += tempDouble1;
                num = num + 1;
                eStart = i + 1;
            }
        }
        if (tempComboTotal > 0) {
            //Add last item of combo
            comboCode[num] = tempTrimmedString.substring(eStart); // This should take it thru the end
            int currentSOVIndex = Arrays.asList(SOVCode).indexOf(comboCode[num]);
            if (currentSOVIndex > 0)
                tempComboTotal += Double.valueOf(Arrays.asList(SOVBase).get(currentSOVIndex));
            elementCode[cNum] = tempTrimmedString;
            elementBase[cNum] = tempComboTotal;
            elementScore[cNum] = tempComboTotal;
            elementGOE[cNum] = 0.0; //Must initialize a value

        } else {
            //Add info for non-combo and not found code
            elementCode[cNum] = null;
        }
        elementIDTextView[cNum].setText(elementCode[cNum]);
        elementBaseTextView[cNum].setText(numberFormat.format(elementBase[cNum]));
    }

    //A basic method to get total technical value
    public Double sumElements() {
        double sumElements = 0;
        for (int qe = 0; qe < requiredElements; qe++) {
            if (elementScore[qe] != null) {
                sumElements += elementScore[qe];
            }
        }
        return sumElements;
    }

    //A basic method to get total component value
    public Double sumComponents() {
        double sumComponents = 0;
        for (int qc = 0; qc < 5; qc++) {
            if (componentScore[qc] != null) {
                sumComponents += componentScore[qc];
            }
        }
        return sumComponents;
    }

    //A basic method to get total deduction value
    public Double sumDeductions() {
        double sumDeductions = 0;
        //Add here
        return sumDeductions;
    }

    //A basic method to get total segment score
    public Double sumSegment() {
        Double sumSegment = sumElements() + sumComponents() - sumDeductions();
        scoresTextView[0].setText(numberFormat.format(sumSegment)); //Update overall score
        return sumSegment;
    }

    //Simple method to update GOE value on seekerbar change
    public void calcElementGOE(int progress, int position) {
        //Convert progress to raw GOE +/-
        Double tempGOE = (double) progress;
        tempGOE = (tempGOE / 10 - 5) / 10; // Convert progress on scale of 0-100 to -5 to 5
        //Check for combo/sequence, which only get GOE against highest element
        if (jumpBase[position] > 0) {
            elementGOE[position] = jumpBase[position] * tempGOE;
        } else {
            elementGOE[position] = elementBase[position] * tempGOE;
        }
        //Need to check for ChSq and Combo, which are not 10% increments
        //Update items
        elementGOETextView[position].setText(numberFormat.format(elementGOE[position]));
        calcElementScore(position);
    }

    //Method to calculate an individual element's score
    public void calcElementScore(int position) {
        //Check for bonus
        if (elementBonusButtonStatus[position]) {
            elementScore[position] = 1.1 * elementBase[position] + elementGOE[position];
        } else {
            elementScore[position] = elementBase[position] + elementGOE[position];
        }
        //Update items
        elementScoreTextView[position].setText(numberFormat.format(elementScore[position])); //Update specific element's score
        scoresTextView[1].setText(numberFormat.format(sumElements())); //Update elements total score
        sumSegment();
    }

    //Do I need these adapter views?
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    //Dialog for element tics and other errors
    public void ticDialog(final int tNum) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_tics, null);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        ticDialogButton[0] = dialogView.findViewById(R.id.tic_element_none_button);
        ticDialogButton[1] = dialogView.findViewById(R.id.tic_element_check_button);
        ticDialogButton[2] = dialogView.findViewById(R.id.tic_element_edge_button);
        ticDialogButton[3] = dialogView.findViewById(R.id.tic_element_downgrade_button);
        ticDialogButton[4] = dialogView.findViewById(R.id.tic_element_double_downgrade_button);
        ticDialogButton[5] = dialogView.findViewById(R.id.tic_element_cancel_button);

        tempString = String.valueOf(tNum);
        Log.i("********DIALOG*********** ", tempString);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set none button click listener
        ticDialogButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                changeTicButton(tNum, 0);
            }
        });

        // Set check button click listener
        ticDialogButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                changeTicButton(tNum, 1);
            }

        });

        // Set edge button click listener
        ticDialogButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                changeTicButton(tNum, 2);
            }
        });

        // Set downgrade button click listener
        ticDialogButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                changeTicButton(tNum, 3);
            }
        });
        // Set doubledowngrade button click listener
        ticDialogButton[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                changeTicButton(tNum, 4);
            }
        });
        // Set cancel button click listener
        ticDialogButton[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Display the custom alert dialog on interface
        dialog.show();
    }

    public void changeTicButton(int eleNum, int ticNum) {
        // Change the tic button after selecting dialog
        if (eleNum < 10) {
            tempString = "elementTicButton0" + eleNum;
        } else {
            tempString = "elementTicButton" + eleNum;
        }
        elementTicIndex[eleNum] = ticNum;
        resID = getResources().getIdentifier(tempString, "id", getPackageName());
        tempButton = findViewById(resID);
        tempString = ticArray[elementTicIndex[eleNum]];
        if (tempButton != null) tempButton.setText(tempString);
        //Recalculate Base and GOE Value
        tempInt = Arrays.asList(SOVCode).indexOf(elementCode[eleNum]); //Get SOV index of current element
        if (tempInt > 0)
            tempBase = Double.valueOf(Arrays.asList(SOVBase).get(tempInt)); //Get Base Value from SOV
        //Need to check combo jumps here, might change which is highest, so might change GOE
        if (jumpBase[eleNum] > 0) {
            tempBase = jumpBase[eleNum];
            tempDouble1 = elementBase[eleNum] - jumpBase[eleNum]; //Saving value of combo without biggest jump
        }
        tempGOE = elementGOE[eleNum] / tempBase; //This is GOE ratio only for recalculating (saving progress point)

        switch (ticNum) {
            case 2:
                tempBase = Math.round(tempBase * 75.0) / 100.0; //Need to round to two digits
                if (jumpBase[eleNum] > 0) jumpBase[eleNum] = tempBase;
                break;
            case 3:
                tempBase = Math.round(tempBase * 75.0) / 100.0; //Need to round to two digits
                if (jumpBase[eleNum] > 0) jumpBase[eleNum] = tempBase;
                break;
            default:
                if (jumpBase[eleNum] > 0) {
                    //Need to see about restoring jumpbase when turning off downgrade
                    checkForComboJump(elementCode[eleNum], eleNum);
                }
                break;
        }
        //Do these for all, so if we reset to no tic, it goes to basic value
        elementGOE[eleNum] = Math.round(tempBase * tempGOE * 100.0) / 100.0;
        elementGOETextView[eleNum].setText(numberFormat.format(elementGOE[eleNum]));
        //tempString = String.valueOf(tempBase);
        //Toast.makeText(this, "We have a Combo " + tempString, Toast.LENGTH_SHORT).show();

        //
        if (jumpBase[eleNum] > 0) {
            elementBase[eleNum] = tempDouble1 + tempBase;
        } else {
            elementBase[eleNum] = tempBase;
        }
        elementBaseTextView[eleNum].setText(numberFormat.format(elementBase[eleNum])); //Update specific element's base value

        // Recalculate element score
        calcElementScore(eleNum);
    }
}