package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProgramScoringViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public TextView programDescriptionTextView;
    public TextView[] scoresTextView = new TextView[4];
    public TextView[] elementIDTextView = new TextView[13];
    public TextView[] elementBonusTextView = new TextView[13];
    public TextView[] elementTicTextView = new TextView[13];
    public TextView[] elementBaseTextView = new TextView[13];
    public TextView[] elementGOETextView = new TextView[13];
    public TextView[] elementScoreTextView = new TextView[13];
    public TextView[] componentFactorTextView = new TextView[5];
    public TextView[] componentRawTextView = new TextView[5];
    public TextView[] componentScoreTextView = new TextView[5];

    private List<ElementItem> elementScoreList = new ArrayList<>();

    public int progress = 50; // the midway point, zeroed.  Need to set up for adjustable
    public int resID;

    public String tempString;
    public String tempRowID;
    public String tempRowBonus;
    public String tempRowTic;
    public String tempRowBase;
    public String tempRowGOE;
    public String tempRowScore;
    public String mCurrentUserID;
    public String mCurrentProgramID;
    public String programDescription;
    public String[] elementCode = new String[13];
    public String[] elementBonus = new String[13];
    public String[] elementTic = new String[13];
    public Double[] elementBase = new Double[13];
    public Double[] elementGOE = new Double[13];
    public Double[] elementScore = new Double[13];
    public Double[] componentFactor = new Double[5];
    public Double[] factors = new Double[8];
    public Double[] componentScore = new Double[5];
    public String[] SOVCode;
    public String[] SOVName;
    public String[] SOVBase;
    public String[][] factorTable = new String[30][11];
    public List<Program> program = new ArrayList<>();
    public List<Elements> elements = new ArrayList<>();
    public ArrayList<ElementInfo> elementInfoList = new ArrayList<ElementInfo>();
    public int requiredElements;
    public int i;
    public int tempInt;
    public int tempBarId;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_scoring_view);

        //Set up GlobalClass for shared constants and methods
        globalClass = ((GlobalClass) getApplicationContext());

        db = FirebaseFirestore.getInstance();
        // Nset up arrays with view names, seekbars etc
        initializeVariables();

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
            init();  // Call the initiation method here, to ensure the IDs have been pulled
        }
    }

    //default listener for all seeker bars
    @Override
    public void onProgressChanged(SeekBar bar, int progressValue, boolean fromUser) {
        //Toast.makeText(getApplicationContext(), "SeekerBar Moved", Toast.LENGTH_SHORT).show();
        for (i = 0; i < requiredElements; i++) {
            if (bar == goeBar[i]) {
                //seekerBarUpdate(progressValue, i);
                tempString = String.valueOf(i);
                Log.i("*******************seeker bar matched:: ", tempString);
                calcElementScore(progressValue, i);
            }
        }
        Log.i("------------------finished loop: ", tempString);
        for (i = 0; i < 5; i++) {
            if (bar == compBar[i]) {
                compBarUpdate(progressValue, i);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar bar) {
        //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar bar) {
        //Update score upon stop
        //seekerBarStopUpdate();
        //textView00.setText("Covered: " + progress + "/" + );
        //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
        Log.i("-------------------Seeker Bar Stop", tempString);
    }

    // A private method to help us initialize our variables.
    private void initializeVariables() {
        scoresTextView[1] = findViewById(R.id.elementTotal);
        scoresTextView[2] = findViewById(R.id.componentTotal);
        scoresTextView[3] = findViewById(R.id.deductionsTotal);
        scoresTextView[0] = findViewById(R.id.segmentTotal);
        programDescriptionTextView = findViewById(R.id.textViewProgramDescription);

        //Get SOV Table 2018 - Three matched arrays
        Resources resources = getResources();
        SOVCode = resources.getStringArray(R.array.SOV_Code);
        SOVName = resources.getStringArray(R.array.SOV_Name);
        SOVBase = resources.getStringArray(R.array.SOV_Base);
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
        compBar[0].setOnSeekBarChangeListener(this);
        compBar[1].setOnSeekBarChangeListener(this);
        compBar[2].setOnSeekBarChangeListener(this);
        compBar[3].setOnSeekBarChangeListener(this);
        compBar[4].setOnSeekBarChangeListener(this);

        //Set view variables
        for (int i = 0; i < 13; i++) {
            if (i < 10) {
                tempRowID = "elementIdRow0" + i;
                tempRowBonus = "elementBonusRow0" + i;
                tempRowTic = "elementTicRow0" + i;
                tempRowBase = "elementBaseRow0" + i;
                tempRowGOE = "elementGOERow0" + i;
                tempRowScore = "elementScoreRow0" + i;
            } else {
                tempRowID = "elementIdRow" + i;
                tempRowBonus = "elementBonusRow" + i;
                tempRowTic = "elementTicRow" + i;
                tempRowBase = "elementBaseRow" + i;
                tempRowGOE = "elementGOERow" + i;
                tempRowScore = "elementScoreRow" + i;
            }

            resID = getResources().getIdentifier(tempRowID, "id", getPackageName());
            //Log.i("*******************tempRowID: ", tempRowID);
            elementIDTextView[i] = findViewById(resID);
            /*
            resID = getResources().getIdentifier(tempRowBonus, "id", getPackageName());
            elementBonusTextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempRowTic, "id", getPackageName());
            elementBonusTextView[i] = findViewById(resID);
            */
            resID = getResources().getIdentifier(tempRowBase, "id", getPackageName());
            elementBaseTextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempRowGOE, "id", getPackageName());
            elementGOETextView[i] = findViewById(resID);
            resID = getResources().getIdentifier(tempRowScore, "id", getPackageName());
            elementScoreTextView[i] = findViewById(resID);
        }
        //tempString = ""
        //resID = getResources().getIdentifier(tempString, "id", getPackageName());
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
        //componentScore[uCompNum] = tempDouble2 * componentFactor[uCompNum]*factors[6]; // factors[6] is general prgram component
        componentScore[uCompNum] = tempDouble2 * factors[uCompNum+1]*factors[6]; // factors[6] is general program component
        tempString = String.valueOf(componentScore[uCompNum]);
        componentScoreTextView[uCompNum].setText(tempString);
        tempString = String.valueOf(factors[uCompNum+1]);
        Log.i("-------------------compBarUpdate Factor Vaue: ", tempString);
        scoresTextView[2].setText(numberFormat.format(sumComponents()));
        scoresTextView[0].setText(numberFormat.format(sumSegment()));
    }


    //Update all info on Seeker Bar stop
    private void seekerBarStopUpdate() {
        /*
        for (i = 0; i < requiredElements; i++) {
            tempString = elementGOETextView[i].getText().toString();
            tempDouble1 = Double.parseDouble(tempString);
            elementGOE[i] = tempDouble1;
            elementScore[i] = elementBase[i] * (1 + (tempDouble1 / 10));
            tempString = numberFormat.format(elementScore[i]);
            elementScoreTextView[i].setText(tempString);
        }
        tempString = numberFormat.format(sumElements());
        scoresTextView[0].setText(tempString);
        sumSegment();
        */
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
                //public void onComplete(final DocumentSnapshot documentSnapshot) {
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Program qProgram = documentSnapshot.toObject(Program.class);
                        program.add(qProgram);
                        //mCompetitionNameTextView.setText(program.get(0).getCompetition());
                        String tempText = program.get(0).getLevel() + " " + program.get(0).getDiscipline() + " " + program.get(0).getSegment() + " Program";
                        programDescriptionTextView.setText(tempText);
                        programDescription = program.get(0).getLevel() + program.get(0).getDiscipline() + program.get(0).getSegment();
                        factors = globalClass.getFactors(programDescription);
                        progPointer = program.get(0).getLevel() + program.get(0).getDiscipline() + program.get(0).getSegment();
                        //Log.i("*******************programRef: ", mCurrentProgramID);
                        //Log.i("*******************progPointer: ", progPointer);
                        tempInt = Arrays.asList(RequiredElementsKey).indexOf(progPointer);
                        requiredElements = RequiredElementsValue[tempInt];
                        //mCompetitionDescriptionTextView.setText(tempText);
                        elementID = program.get(0).getElementsID();
                        tempString = String.valueOf(requiredElements);
                        Log.i("******************* required elements: ", tempString);
                        pullElements(elementID);
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
                    // Set initial totals
                    //tempString = numberFormat.format(sumElements());
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
            //tempString = String.valueOf(i);
            //Log.i("*******************factors: ", tempString);
            if (factors[i + 1] != null) {
                tempDouble2 = 5.0;
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
            Log.i("*******************pElementCode: ", pNum + " " + elementCode[pNum]);
            tempString = elementCode[pNum];
            elementIDTextView[pNum].setText(elementCode[pNum]);
            tempString = numberFormat.format(elementBase[pNum]);
            elementBaseTextView[pNum].setText(tempString);
            elementScoreTextView[pNum].setText(tempString);//Can initialize to both becasue GOE is initially 0
        } else {
            //Check for combo or change to unfound////
            if (pElementCode != null) checkForComboJump(pElementCode, pNum);
        }
        //scoresTextView[1].setText(numberFormat.format(sumElements()));
        //scoresTextView[0].setText(numberFormat.format(sumSegment()));

    }

    public void checkForComboJump(String cElementCode, int cNum) {
        //Toast.makeText(ProgramViewActivity.this, cElementCode, Toast.LENGTH_SHORT).show();
        tempTrimmedString = cElementCode.replaceAll("\\s+", ""); //Trim any spaces - CAREFUL, throws an error if null
        elementCodeCArray = tempTrimmedString.toCharArray();
        num = 0;
        eStart = 0;
        eEnd = 0;
        tempComboTotal = 0.0;
        for (i = 0; i < elementCodeCArray.length; i++) {
            tempString = String.copyValueOf(elementCodeCArray, i, 1);
            if (tempString.equals("+")) {
                //We have a combo
                eEnd = i; // IN substring, end position is not included in extract
                comboCode[num] = tempTrimmedString.substring(eStart, eEnd);
                int currentSOVIndex = Arrays.asList(SOVCode).indexOf(comboCode[num]);
                if (currentSOVIndex > 0)
                    tempComboTotal += Double.valueOf(Arrays.asList(SOVBase).get(currentSOVIndex));
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
        //scoresTextView[1].setText(numberFormat.format(sumElements()));
        //scoresTextView[0].setText(numberFormat.format(sumSegment()));

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
        //sumComponents = sumComponents*factors[6];  //should not need this here, being added at raw score to score level
        return sumComponents;
    }

    //A basic method to get total deduction value
    public Double sumDeductions() {
        double sumDeductions = 0;
            /*
            for (int i = 0; i < requiredElements; i++) {
                if (elementScore[i] != null) {
                    sumElements += elementScore[i];
                }
            }
            */
        return sumDeductions;
    }

    //A basic method to get total segment score
    public Double sumSegment() {
        Double sumSegment = sumElements() + sumComponents() - sumDeductions();
        return sumSegment;
    }


    public void calcElementScore(int progress, int position) {
        //Convert progress to raw GOE +/-
        Double tempGOE = (double) progress;
        elementGOE[position] = tempGOE / 10 - 5;
        //Ultimately, put raw GOE in slider thumb and the actual GOE score in elementGOE
        //Need to check for ChSq and Combo, which are not 10% increments
        elementScore[position] = elementBase[position] * (1 + elementGOE[position] / 10);
        //Update items
        elementGOETextView[position].setText(numberFormat.format(elementGOE[position]));
        elementScoreTextView[position].setText(numberFormat.format(elementScore[position]));
        scoresTextView[1].setText(numberFormat.format(sumElements()));
        scoresTextView[0].setText(numberFormat.format(sumSegment()));
    }

/*
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
    */
}

