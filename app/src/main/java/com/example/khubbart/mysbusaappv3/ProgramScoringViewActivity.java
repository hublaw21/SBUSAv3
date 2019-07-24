package com.example.khubbart.mysbusaappv3;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.khubbart.mysbusaappv3.Model.ElementInfo;
import com.example.khubbart.mysbusaappv3.Model.ElementItem;
import com.example.khubbart.mysbusaappv3.Model.Factors;
import com.example.khubbart.mysbusaappv3.Model.PlannedProgramContent;
import com.example.khubbart.mysbusaappv3.Model.Program;
import com.example.khubbart.mysbusaappv3.Model.Programv2;
import com.example.khubbart.mysbusaappv3.ViewModels.TotalScoresViewModel;
import com.facebook.internal.CollectionMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProgramScoringViewActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener,
        AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    public TextView textViewProgramDescription;
    public TextView textViewProgramInfo;
    public TextView[] scoresTextView = new TextView[4];
    public TextView[] elementIDTextView = new TextView[13];
    public Button[] elementTicButton = new Button[13];
    public Button resetGOEButton;
    public Button resetComponentsButton;
    public ToggleButton[] elementBonusButton = new ToggleButton[13];
    public TextView[] elementTicTextView = new TextView[13];
    public TextView[] textViewElementBase = new TextView[13];
    public TextView[] textViewGOEScore = new TextView[13];
    public TextView[] textViewElementScore = new TextView[13];
    public TextView[] componentFactorTextView = new TextView[5];
    public TextView[] componentRawTextView = new TextView[5];
    public TextView[] componentScoreTextView = new TextView[5];
    private CollectionReference programCollectionDb;
    public TextView mTextViewName;
    public String currentUserUID;
    public String currentSkaterName;
    public String currentProgramID;

    private List<ElementItem> elementScoreList = new ArrayList<>();
    public List<Factors> programFactors = new ArrayList<>();
    public Spinner[] ticSpinner = new Spinner[13];

    public int progress = 50; // the midway point, zeroed.  Need to set up for adjustable
    public int resID;
    public Button[] ticDialogButton = new Button[6];
    public Button[] elementCodeButton = new Button[13];

    public String tempString;
    public String tempRowBarElement;
    public String tempRowID;
    public String tempRowCodeButton;
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
    public Programv2 currentProgram;
    public String[] currentProgramElements = new String[13];
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
    //public List<Program> program = new ArrayList<>();
    public List<Programv2> program = new ArrayList<>();
    public List<String> elementList = new ArrayList<>();
    public List<PlannedProgramContent> elements = new ArrayList<>();
    public ArrayList<ElementInfo> elementInfoList = new ArrayList<ElementInfo>();
    public int requiredElements;
    public int i;
    public int tempInt;
    public int tempBarId;
    public int buttonPointer;
    public int num;
    public int eStart;
    public int eEnd;
    public int tempIndex;
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
    public ViewModel totalScoreViewModel;

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

        totalScoreViewModel = ViewModelProviders.of(this).get(TotalScoresViewModel.class);
        setContentView(R.layout.activity_program_scoring_view);
        textViewProgramDescription = findViewById(R.id.textViewProgramDescription);
        textViewProgramInfo = findViewById(R.id.textViewProgramInfo);
        resetGOEButton = findViewById(R.id.resetGOEButton);
        resetComponentsButton = findViewById(R.id.resetComponentsButton);
        resetGOEButton.setOnClickListener(this);
        resetComponentsButton.setOnClickListener(this);

        //Set up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Get the programID for the selected program sent from select activity
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //Do nothing
        } else {
            tempString = "programID";
            currentProgramID = extras.getString("programID");
        }

        //Set up GlobalClass and retrieve shared constants and methods
        globalClass = ((GlobalClass) getApplicationContext());
        db = FirebaseFirestore.getInstance();
        currentProgram = globalClass.fetchCurrentProgram(currentProgramID);
        currentUserUID = globalClass.getCurrentUserUID();
        currentSkaterName = globalClass.getSkaterName();
        currentProgram.getElements().toArray(currentProgramElements);
        //tempString = currentProgram.getLevel() + currentProgram.getDiscipline() + currentProgram.getSegment();
        requiredElements = globalClass.calcRequiredElements(currentProgram.getLevel() + currentProgram.getDiscipline() + currentProgram.getSegment());
        factors = globalClass.getFactors(currentProgram.getLevel() + currentProgram.getDiscipline() + currentProgram.getSegment());
        textViewProgramDescription.setText(currentProgram.getProgramDescription());
        textViewProgramInfo.setText(currentProgram.getLevel() + " " + currentProgram.getDiscipline() + " " + currentProgram.getSegment());

        //Get current SOV Table - Three matched arrays
        Resources resources = getResources();
        SOVCode = resources.getStringArray(R.array.SOV_Code);
        SOVName = resources.getStringArray(R.array.SOV_Name);
        SOVBase = resources.getStringArray(R.array.SOV_Base);

        //Get diffent type of tics
        ticArray = resources.getStringArray(R.array.tics);

        // Set up arrays with view names, seekbars etc
        initializeVariables();
    }

    //default listener for all buttons
    @Override
    public void onClick(View view) {
        //Determine which button was pushed
        buttonPointer = 0;
        tempInt = view.getId();
        //check for GOE reset
        if (findViewById(tempInt) == resetGOEButton) {
            resetGOE();
            }
        //check for ComponentsGOE reset
        if (findViewById(tempInt) == resetComponentsButton) {
            initializeComps();
        }
        scoresTextView[2].setText(numberFormat.format(sumComponents()));

        //Log.i("tempInt",String.valueOf(tempInt));
        for (i = 0; i < requiredElements; i++) {
            //Check for bonus button clicked
            if (findViewById(tempInt) == elementBonusButton[i]) {
                buttonPointer = i;
                elementBonusButtonStatus[i] = elementBonusButton[i].isChecked();
                if (elementBonusButtonStatus[i]) {
                    //Update base value TEXTVIEW ONLY
                    tempString = numberFormat.format(elementBase[i] * 1.1);
                    textViewElementBase[i].setText(tempString);
                } else {
                    tempString = numberFormat.format(elementBase[i]);
                    textViewElementBase[i].setText(tempString);
                }
                calcElementScore(buttonPointer); //Update element's score
            }
            //Check/Verify Element Code Button clicked
            if (findViewById(tempInt) == elementCodeButton[i]) {
                tempString = String.valueOf(tempInt);
                changeButtonDialog(i);
                //Log.i("Button Change",tempString);
                //Call Change Button Dialog from Global
                /*
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                ElementButtonChangeDialogCaller elementButtonChangeDialogCaller =
                        ElementButtonChangeDialogCaller.newInstance(i
                        );
                elementButtonChangeDialogCaller.show(fm, "dialog");
                */
                /*
                GeneralDialogFragment generalDialogFragment =
                        GeneralDialogFragment.newInstance("title", "message");
                generalDialogFragment.show(getSupportFragmentManager(),"dialog");
                */
            }
            //Check/Verify tic button clicked
            if (findViewById(tempInt) == elementTicButton[i]) {
                tempString = String.valueOf(tempInt);
                tempInt = 0; // have to reset or alert dialog buttons cause a loop
                ticDialog(i);
            }
        }
    }

    //default listener for all seeker bars
    @Override
    public void onProgressChanged(SeekBar bar, int progressValue, boolean fromUser) {
        //maybe do a switch here instead?  Or peel off the digit and do by logic, avoid the loops
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

        //Set up element rows
        for (i = 0; i < requiredElements; i++) {
            tempRowBarElement = "seekBarElement" + String.format("%02d", i);
            tempRowID = "elementRow" + String.format("%02d", i);
            tempRowCodeButton = "elementCodeButton" + String.format("%02d", i);
            tempRowBonus = "elementBonusToggle" + String.format("%02d", i);
            tempRowTic = "elementTicButton" + String.format("%02d", i);
            tempRowBase = "elementBaseRow" + String.format("%02d", i);
            tempRowGOE = "elementGOERow" + String.format("%02d", i);
            tempRowScore = "elementScoreRow" + String.format("%02d", i);

            //Set up GOE Bar
            resID = getResources().getIdentifier(tempRowBarElement, "id", getPackageName());
            goeBar[i] = (SeekBar) this.findViewById(resID);
            goeBar[i].setOnSeekBarChangeListener(this);

            //Set up element code button
            resID = getResources().getIdentifier(tempRowCodeButton, "id", getPackageName());
            elementCodeButton[i] = findViewById(resID);
            if (currentProgramElements[i].length() < 2) {
                //No element code entered yet
                tempString = "Add";
                elementBase[i] = 0.00;
            } else {
                tempString = currentProgramElements[i];
                elementBase[i] = globalClass.CalcElementBaseValue(currentProgramElements[i]);
            }
            elementCodeButton[i].setText(tempString);
            elementCodeButton[i].setOnClickListener(this);

            //Set up bonus button
            resID = getResources().getIdentifier(tempRowBonus, "id", getPackageName());
            elementBonusButton[i] = findViewById(resID);
            elementBonusButton[i].setOnClickListener(this);

            //Set up tic button
            resID = getResources().getIdentifier(tempRowTic, "id", getPackageName());
            elementTicButton[i] = findViewById(resID);
            elementTicButton[i].setOnClickListener(this);

            //Set up base value
            resID = getResources().getIdentifier(tempRowBase, "id", getPackageName());
            textViewElementBase[i] = findViewById(resID);
            tempString = numberFormat.format(elementBase[i]);
            textViewElementBase[i].setText(tempString);

            //Set up total score
            resID = getResources().getIdentifier(tempRowScore, "id", getPackageName());
            textViewElementScore[i] = findViewById(resID);
            tempString = numberFormat.format(elementBase[i]); //Can initialize to both because GOE is initially 0
            textViewElementScore[i].setText(tempString);

            //Save values for calculating
            elementScore[i] = elementBase[i];
            elementGOE[i] = 0.0; //Must initialize a value
            jumpBase[i] = 0.0; //Must initialize a value.  This is the base value of highest vakue jump for GOE of combo
            //Do I need elementCode?
            //elementCode[pNum] = pElementCode;

            //Set up GOE value
            resID = getResources().getIdentifier(tempRowGOE, "id", getPackageName());
            textViewGOEScore[i] = findViewById(resID);
            textViewGOEScore[i].setText(numberFormat.format(elementGOE[i]));

            //Set or hide tic and bonus buttons
            tempIndex = globalClass.ElementIndexLookUp(currentProgramElements[i].trim());
            // Need to add error checker for code not found
            //Log.i("tempIndex",String.valueOf(tempIndex));
            if (tempIndex < 0) { //No element
                disableTicButton(i);
            } else {
                if (tempIndex < 24) { // jump
                    enableTicButton(i);
                } else {
                    if (tempIndex < 999) { //non-jump
                        disableTicButton(i);
                    } else { //combo jump
                        enableTicButton(i);
                        jumpBase[i] = globalClass.calcComboJump(currentProgramElements[i], Boolean.FALSE);
                    }
                }
            }
        }
        //Hide the unused element rows
        for (i = requiredElements; i < 13; i++) {
            tempRowID = "elementRow" + String.format("%02d", i);
            resID = getResources().getIdentifier(tempRowID, "id", getPackageName());
            TableRow tr = findViewById(resID);
            tr.setVisibility(View.GONE);
        }


        //Set up components scoring items
        compBar[0] = this.findViewById(R.id.seekBarComponentSkills);

        compBar[2] = this.

                findViewById(R.id.seekBarComponentPerformance);

        compBar[1] = this.

                findViewById(R.id.seekBarComponentTransitions);

        compBar[3] = this.

                findViewById(R.id.seekBarComponentComposition);

        compBar[4] = this.

                findViewById(R.id.seekBarComponentInterpretation);

        compBar[0].

                setOnSeekBarChangeListener(this);

        compBar[1].

                setOnSeekBarChangeListener(this);

        compBar[2].

                setOnSeekBarChangeListener(this);

        compBar[3].

                setOnSeekBarChangeListener(this);

        compBar[4].

                setOnSeekBarChangeListener(this);

        componentRawTextView[0] =

                findViewById(R.id.componentSkillsValue);

        componentRawTextView[1] =

                findViewById(R.id.componentTransitionsValue);

        componentRawTextView[2] =

                findViewById(R.id.componentPerformanceValue);

        componentRawTextView[3] =

                findViewById(R.id.componentCompositionValue);

        componentRawTextView[4] =

                findViewById(R.id.componentInterpretationValue);

        componentScoreTextView[0] =

                findViewById(R.id.componentSkillsScore);

        componentScoreTextView[1] =

                findViewById(R.id.componentTransitionsScore);

        componentScoreTextView[2] =

                findViewById(R.id.componentPerformanceScore);

        componentScoreTextView[3] =

                findViewById(R.id.componentCompositionScore);

        componentScoreTextView[4] =

                findViewById(R.id.componentInterpretationScore);

        componentFactorTextView[0] =

                findViewById(R.id.componentSkillsFactor);

        componentFactorTextView[1] =

                findViewById(R.id.componentTransitionsFactor);

        componentFactorTextView[2] =

                findViewById(R.id.componentPerformanceFactor);

        componentFactorTextView[3] =

                findViewById(R.id.componentCompositionFactor);

        componentFactorTextView[4] =

                findViewById(R.id.componentInterpretationFactor);

        initializeComps();
        //And now get program info
        //6-24 do I still need this?
        //init();
    }


    //Set initial comp scores too
    //I won't know factor values until we know what program
    public void initializeComps() {
        for (int i = 0; i < 5; i++) {
            if (factors[i + 1] != null) {
                tempDouble2 = 5.0; // This could be an option for users
                tempString = String.valueOf(tempDouble2);
                componentRawTextView[i].setText(tempString);
                componentScore[i] = tempDouble2 * factors[i + 1] * factors[6]; // comp factors start at 1, factors[6] is general program component
                tempString = String.valueOf(componentScore[i]);
                componentScoreTextView[i].setText(tempString);
                componentFactorTextView[i].setText(numberFormat.format(factors[i + 1]));
                compBar[i].setProgress(50);
            }
        }
        scoresTextView[0].setText(numberFormat.format(sumSegment()));
        scoresTextView[1].setText(numberFormat.format(sumElements()));
        scoresTextView[2].setText(numberFormat.format(sumComponents()));
    }

    public void resetGOE() {
        for (int i = 0; i < requiredElements; i++) {
            elementGOE[i] = 0.0; // This could be an option for users
            textViewGOEScore[i].setText(numberFormat.format(elementGOE[i]));
            elementGOE[i] = tempDouble2;
            goeBar[i].setProgress(50);
        }
        scoresTextView[0].setText(numberFormat.format(sumSegment()));
        scoresTextView[1].setText(numberFormat.format(sumElements()));
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
        textViewGOEScore[position].setText(numberFormat.format(elementGOE[position]));
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
        textViewElementScore[position].setText(numberFormat.format(elementScore[position])); //Update specific element's score
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

    public void disableTicButton(int pos) {
        elementTicButton[pos].setText("");
        elementTicIndex[pos] = 0;
        elementBonusButton[pos].setText("");
        elementBonusButton[pos].setEnabled(false); // Disable buttons for non-jumps.   Jumps are 0-24 in array
        elementTicButton[pos].setEnabled(false); // Disable buttons for non-jumps.   Jumps are 0-24 in array
    }

    public void enableTicButton(int pos) {
        //Initiate tic button for jumps
        elementTicButton[pos].setText(ticArray[0]);
        elementTicIndex[pos] = 0;
        elementBonusButton[pos].setEnabled(true); // Enable buttons for non-jumps.   Jumps are 0-24 in array
        elementTicButton[pos].setEnabled(true);
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

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set button click listener
        ticDialogButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                changeTicButton(tNum, 0);
            }

            ;
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
        //Identify which button was pushed
        tempString = "elementTicButton" + String.format("%02d", eleNum);
        elementTicIndex[eleNum] = ticNum;
        resID = getResources().getIdentifier(tempString, "id", getPackageName());
        tempButton = findViewById(resID);
        //Change the tic indicator
        tempString = ticArray[elementTicIndex[eleNum]];
        if (tempButton != null) tempButton.setText(tempString);
        //Recalculate Base and GOE Value
        tempIndex = globalClass.ElementIndexLookUp(currentProgramElements[eleNum].trim());//Get SOV index of current element
        if (tempIndex > 0) {
            if (tempIndex == 999) {
                //Is a combo jump
                //Need to check combo jumps here, might change which is highest, so might change GOE
                //if (jumpBase[eleNum] > 0) {
                    tempBase = jumpBase[eleNum]; //jumpBase is the base value of highest jump
                    tempDouble1 = globalClass.calcComboJump(currentProgramElements[eleNum].trim(),Boolean.TRUE);//Get base for combo jump
                    tempDouble1 = tempDouble1 - jumpBase[eleNum]; //Saving value of combo without biggest jump
                //}
            } else {
                tempBase = globalClass.ElementBaseLookUp(tempIndex); //Get Base Value from SOV
            }
        }

        tempGOE = elementGOE[eleNum] / tempBase; //This is GOE ratio only for recalculating (saving progress point)

        switch (ticNum) {
            //Should make a global varibale set for the ratios, so ewasy to change year to year
            case 0:
                //No tics
                break;

            case 1:
                //Review - no score change needed
                break;

            case 2:
                //edge
                tempBase = Math.round(tempBase * 75.0) / 100.0; //Need to round to two digits
                if (jumpBase[eleNum] > 0) jumpBase[eleNum] = tempBase;
                break;

            case 3:
                //downgrade
                tempBase = Math.round(tempBase * 75.0) / 100.0; //Need to round to two digits
                if (jumpBase[eleNum] > 0) jumpBase[eleNum] = tempBase;
                break;

            case 4:
                //double downgrade
                break;
            case 5:
                //return, no change
                break;

            default:
                //Why am I doing this? 7-24-19
                if (jumpBase[eleNum] > 0) {
                    //Need to see about restoring jumpbase when turning off downgrade
                    jumpBase[eleNum] = globalClass.calcComboJump(elementCode[eleNum], Boolean.FALSE);
                }
                break;
        }
        //Do these for all, so if we reset to no tic, it goes to basic value
        elementGOE[eleNum] = Math.round(tempBase * tempGOE * 100.0) / 100.0;
        textViewGOEScore[eleNum].setText(numberFormat.format(elementGOE[eleNum]));
        //tempString = String.valueOf(tempBase);
        //Toast.makeText(this, "We have a Combo " + tempString, Toast.LENGTH_SHORT).show();

        //Set element base for scoring
        if (jumpBase[eleNum] > 0) {
            elementBase[eleNum] = tempDouble1 + tempBase;
        } else {
            elementBase[eleNum] = tempBase;
        }
        textViewElementBase[eleNum].setText(numberFormat.format(elementBase[eleNum])); //Update specific element's base value

        // Recalculate element score
        calcElementScore(eleNum);
    }

    public void changeButtonDialog(final int dialogButtonPointer) {
        // Build an AlertDialog
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
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
        final android.support.v7.app.AlertDialog dialog = builder.create();

        // Set submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tElementCode = editTextElementCode.getText().toString();
                currentProgramElements[dialogButtonPointer] = tElementCode;
                currentProgram.setElements(Arrays.asList(currentProgramElements));
                elementCodeButton[dialogButtonPointer].setText(tElementCode); //Reset code on butto
                elementBase[dialogButtonPointer] = globalClass.CalcElementBaseValue(tElementCode.trim()); //get new base value
                calcElementScore(dialogButtonPointer);
                tempString = numberFormat.format(elementBase[dialogButtonPointer]);
                textViewElementBase[dialogButtonPointer].setText(tempString);
                //sumSegment(); //recalculate score
                dialog.dismiss();
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
                    Intent intentBundle = new Intent(ProgramEditActivity.this, ElementLookupActivity.class);
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

    //Set up the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //Inflate the menu; this adds item to the action
        //bar if its present
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //Selecting menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_element_lookup:
                // User chose the "Element Lookup" item in the toolbar, call the activity
                Intent intentBundle = new Intent(ProgramScoringViewActivity.this, ElementLookupActivity.class);
                startActivity(intentBundle);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}