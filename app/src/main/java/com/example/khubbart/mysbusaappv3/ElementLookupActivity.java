package com.example.khubbart.mysbusaappv3;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Arrays;

public class ElementLookupActivity extends AppCompatActivity implements
        SelectJumpFragment.OnChangeJumpRadioButtonInteractionListener,
        SelectRevolutionsFragment.OnChangeRevolutionsRadioButtonInteractionListener,
        SelectSpinNameFragment.OnChangeSpinNameRadioButtonInteractionListener,
        SelectLevelFragment.OnChangeLevelRadioButtonInteractionListener,
        SelectFlyingSwitchFragment.OnChangeFlyingSwitchInteractionListener,
        SelectFootChangeSwitchFragment.OnChangeFootChangeSwitchInteractionListener,
        SelectGroupFragment.OnChangeGroupRadioButtonInteractionListener,
        SelectLiftNameFragment.OnChangeLiftNameRadioButtonInteractionListener,
        SelectDeathSpiralInOutSwitchFragment.OnChangeInOutSwitchInteractionListener,
        SelectDeathSpiralFrontBackSwitchFragment.OnChangeFrontBackSwitchInteractionListener,
        SelectPairSpinNameFragment.OnChangePairSpinNameRadioButtonInteractionListener,
        SelectStepNameFragment.OnChangeStepNameRadioButtonInteractionListener
        //, AdapterView.OnItemSelectedListener
{

    public ToggleButton selectDisciplineToggleButton;
    public ToggleButton elementTypeToggleButton[] = new ToggleButton[7];
    public int elementTypePointer = 0; // Use to select element type, preset to jump
    public TableRow trDiscipline;

    public TextView textViewElementDetailName;
    public TextView textViewElementCode;
    public TextView textViewElementDetailBaseValue;
    public TextView[] textViewElementDetailGOE = new TextView[11];
    public String elementCode;
    public String[] elementCodeParts = new String[4];
    public String[] SOVCode;
    public String[] SOVName;
    public String[] SOVBase;
    public int i = 0;
    public int j;
    public int tempNum;
    public String tempString;
    public RadioGroup tempRadioGroup;
    public RadioButton tempRadioButton;
    public Integer resID;
    public String textViewID;
    public String containerID;
    public Integer currentSOVIndex;
    public Integer currentElementTypeIndex = 0;
    public String currentElementName;
    public String currentElementBase;
    public Double currentBase;
    public Double factorGOE;
    public Double[] currentGOE = new Double[11];
    public String[] currentGOEString = new String[11];
    public Fragment[] fragment = new Fragment[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_lookup);

        trDiscipline = findViewById(R.id.pairsElementsRow);

        //Get SOV Table 2018 - Three matched arrays
        Resources resources = getResources();
        SOVCode = resources.getStringArray(R.array.SOV_Code);
        SOVName = resources.getStringArray(R.array.SOV_Name);
        SOVBase = resources.getStringArray(R.array.SOV_Base);

        // Set up Element Type toggle buttons - in array
        elementTypeToggleButton[0] = findViewById(R.id.toggleButtonJump);
        elementTypeToggleButton[1] = findViewById(R.id.toggleButtonSpin);
        elementTypeToggleButton[2] = findViewById(R.id.toggleButtonSteps);
        elementTypeToggleButton[3] = findViewById(R.id.toggleButtonLift);
        elementTypeToggleButton[4] = findViewById(R.id.toggleButtonTwist);
        elementTypeToggleButton[5] = findViewById(R.id.toggleButtonThrow);
        elementTypeToggleButton[6] = findViewById(R.id.toggleButtonSpiral);

        //Set up Discipline toggle buttons
        selectDisciplineToggleButton = (ToggleButton) findViewById(R.id.toggleButtonDiscipline);
        selectDisciplineToggleButton.setChecked(true); // set the current state of a toggle button
        selectDisciplineToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectDisciplineToggleButton.isChecked()) {
                    //Singles
                    //int resID = getResources().getIdentifier("pairsElementRow"tempString, "id", getPackageName());
                    trDiscipline.setVisibility(View.GONE);
                    if (elementTypePointer > 2) { // Reset pointer if changing to singles with pairs element selected
                        elementTypeToggleButton[elementTypePointer].setChecked(false);
                        elementTypePointer = 0;
                        elementTypeToggleButton[elementTypePointer].setChecked(true);
                    }
                } else {
                    //Pairs
                    trDiscipline.setVisibility(View.VISIBLE);
                }
            }
        });


        elementTypeToggleButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementTypePointer = 0;
                ElementTypeChange(elementTypePointer);
            }
        });

        elementTypeToggleButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementTypePointer = 1;
                ElementTypeChange(elementTypePointer);
            }
        });

        elementTypeToggleButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementTypePointer = 2;
                ElementTypeChange(elementTypePointer);
            }
        });

        elementTypeToggleButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementTypePointer = 3;
                ElementTypeChange(elementTypePointer);
            }
        });

        elementTypeToggleButton[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementTypePointer = 4;
                ElementTypeChange(elementTypePointer);
            }
        });

        elementTypeToggleButton[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementTypePointer = 5;
                ElementTypeChange(elementTypePointer);
            }
        });

        elementTypeToggleButton[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementTypePointer = 6;
                ElementTypeChange(elementTypePointer);
            }
        });


        // Set up textviews for element detail fragment
        textViewElementDetailName = findViewById(R.id.textViewElementDetailName);
        textViewElementCode = findViewById(R.id.textViewElementCode);
        textViewElementDetailBaseValue = findViewById(R.id.elementDetailBaseValue);
        for (i = 0; i < 11; i++) {
            textViewID = "elementDetailGOE" + i;
            if (i != 5) {
                // Do not do for 5, that is base value
                resID = getResources().getIdentifier(textViewID, "id", getPackageName());
                textViewElementDetailGOE[i] = findViewById(resID);
            }
        }
    }


    //Trading out fragments
    private void FragmentChange(Integer currentElementTypeIndex) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft;
        for (i = 0; i < 4; i++) {
            ft = fm.beginTransaction();
            containerID = "container" + i;
            resID = getResources().getIdentifier(containerID, "id", getPackageName());
            ft.replace(resID, fragment[i]);
            ft.commit();
        }
        // Call an update to clear screen of any old data/seed new element data
        updateElement();
    }

    // Get Jump Name
    @Override
    public void onChangeJumpRadioButtonInteraction(String tempCode) {
        elementCodeParts[3] = tempCode;
        // Probably add an if to check for throw and then add 'Th'
        if (currentElementTypeIndex == 5) elementCodeParts[3] += "Th";
        updateElement();
    }

    // Get Number of Revolutions
    @Override
    public void onChangeRevolutionsRadioButtonInteraction(String tempCode) {
        elementCodeParts[2] = tempCode;
        if (currentElementTypeIndex == 4) {
            elementCodeParts[2] += "Tw";
        }
        updateElement();
    }

    // Get Spin Name
    @Override
    public void onChangeSpinNameRadioButtonInteraction(String tempCode) {
        elementCodeParts[2] = tempCode;
        updateElement();
    }

    // Get Level
    @Override
    public void onChangeLevelRadioButtonInteraction(String tempCode) {
        if (elementCodeParts[1] != "5" && currentElementTypeIndex == 3) {
            elementCodeParts[2] = "Li"; //In case Group 5 name is checked
        }
        elementCodeParts[3] = tempCode;
        updateElement();
    }

    // Flying Switch Listener
    @Override
    public void onChangeFlyingSwitchInteraction(boolean isFlying) {
        if (isFlying) {
            elementCodeParts[0] = "F";
        } else {
            elementCodeParts[0] = "";
        }
        updateElement();
    }

    // Foot Change Switch Listener
    @Override
    public void onChangeFootChangeSwitchInteraction(boolean isFootChange) {
        if (isFootChange) {
            elementCodeParts[1] = "C";
        } else {
            elementCodeParts[1] = "";
        }
        updateElement();
    }

    // Get Group
    @Override
    public void onChangeGroupRadioButtonInteraction(String tempCode) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft = fm.beginTransaction();
        elementCodeParts[1] = tempCode;
        if (elementCodeParts[1] != "5") {
            elementCodeParts[2] = "Li"; //In case Group 5 name is checked
            fragment[1] = new EmptyFragment();
            resID = getResources().getIdentifier("container1", "id", getPackageName());
            ft.replace(resID, fragment[1]);
            ft.commit();

        } else {
            fragment[1] = new SelectLiftNameFragment();
            resID = getResources().getIdentifier("container1", "id", getPackageName());
            ft.replace(resID, fragment[1]);
            ft.commit();
        }
        updateElement();
    }

    // Get Lift Name
    @Override
    public void onChangeLiftNameRadioButtonInteraction(String tempCode) {
        elementCodeParts[2] = tempCode;
        updateElement();
    }

    // In Out Switch Listener
    @Override
    public void onChangeInOutSwitchInteraction(boolean isOut) {
        if (isOut) {
            elementCodeParts[2] = "oDs";
        } else {
            elementCodeParts[2] = "iDs";
        }
        updateElement();
    }

    // Front Back Switch Listener
    @Override
    public void onChangeFrontBackSwitchInteraction(boolean isBack) {
        if (isBack) {
            elementCodeParts[1] = "B";
        } else {
            elementCodeParts[1] = "F";
        }
        updateElement();
    }

    // Get Pair Spin Name
    @Override
    public void onChangePairSpinNameRadioButtonInteraction(String tempCode) {
        elementCodeParts[2] = tempCode;
        if (elementCodeParts[2] == "PiF") elementCodeParts[3] = "";
        updateElement();
    }

    // Get Step Name
    @Override
    public void onChangeStepNameRadioButtonInteraction(String tempCode) {
        if (tempCode == "StSq") {
            if (elementCodeParts[3] == "") {
                elementCodeParts[3] = "B"; // Set new level value when changing from Choreo to Steps
                tempRadioGroup = findViewById(R.id.radioGroupLevels);
                tempRadioGroup.check(R.id.radioButtonLevelB);
            }
        } else {
            elementCodeParts[3] = ""; // Clear level value since ChSq has no levels
        }
        elementCodeParts[2] = tempCode;
        updateElement();
    }


    public void updateElement() {

        elementCode = elementCodeParts[0] + elementCodeParts[1] + elementCodeParts[2] + elementCodeParts[3];
        currentSOVIndex = Arrays.asList(SOVCode).indexOf(elementCode);
        // Need to add error checker for code not found
        if (currentSOVIndex < 0) {
            //Error
            //Toast.makeText(getApplicationContext(), "elementCode: **" + elementCode + "** currentSOVIndex: " + currentSOVIndex, Toast.LENGTH_LONG).show();
        } else {
            currentElementBase = Arrays.asList(SOVBase).get(currentSOVIndex);
            currentElementName = Arrays.asList(SOVName).get(currentSOVIndex);
            textViewElementDetailName.setText(currentElementName);
            textViewElementCode.setText(elementCode);
            textViewElementDetailBaseValue.setText(currentElementBase);

            currentBase = Double.valueOf(currentElementBase);
            factorGOE = -.5;
            for (i = 0; i < 11; i++) {
                if (i != 5) {
                    // Do not do for 5, that is base value
                    currentGOE[i] = factorGOE * currentBase;
                    currentGOEString[i] = String.format("%.2f", currentGOE[i]);
                    textViewElementDetailGOE[i].setText(currentGOEString[i]);
                }
                ;
                factorGOE = factorGOE + .1;
            }
        }
    }


    //Element Toggles Change Action
    //@Override
    public void ElementTypeChange(int elementTypePointer) {
        //Change toggle buttons
        elementTypeToggleButton[currentElementTypeIndex].setChecked(false); // Turn off previous toggle
        elementTypeToggleButton[elementTypePointer].setChecked(true); // Turn on new toggle, just inc case re-clicked the same button
        currentElementTypeIndex = elementTypePointer;
        if (selectDisciplineToggleButton.isChecked()) {
            trDiscipline.setVisibility(View.GONE);  //Close pairs elements, if necessary
        }
        //prepare fragments and allow for default to avoid crashes
        Arrays.fill(fragment, new EmptyFragment()); // set all cells to empty
        switch (currentElementTypeIndex) {
            case 0:
                // Jumps
                elementCodeParts[0] = "";
                elementCodeParts[1] = "";
                elementCodeParts[2] = "1";
                elementCodeParts[3] = "T";
                fragment[0] = new SelectRevolutionsFragment();
                fragment[1] = new SelectJumpFragment();
                fragment[2] = new EmptyFragmentRight();
                fragment[3] = new EmptyFragmentRight();
                break;

            case 1:
                // Spins
                elementCodeParts[0] = "";
                elementCodeParts[1] = "";
                elementCodeParts[2] = "USp";
                elementCodeParts[3] = "B";
                fragment[0] = new SelectFlyingSwitchFragment();
                fragment[1] = new SelectSpinNameFragment();
                fragment[2] = new SelectLevelFragment();
                fragment[3] = new EmptyFragment();
                break;

            case 2:
                //Footwork
                elementCodeParts[0] = "";
                elementCodeParts[1] = "";
                elementCodeParts[2] = "StSq";
                elementCodeParts[3] = "B";
                fragment[0] = new SelectStepNameFragment();
                fragment[1] = new SelectLevelFragment();
                fragment[2] = new EmptyFragment();
                fragment[3] = new EmptyFragment();
                break;

            case 3:
                // Lifts
                elementCodeParts[0] = "";
                elementCodeParts[1] = "1";
                elementCodeParts[2] = "Li";
                elementCodeParts[3] = "B";
                fragment[0] = new SelectGroupFragment();
                fragment[1] = new SelectLiftNameFragment();
                fragment[2] = new SelectLevelFragment();
                fragment[3] = new EmptyFragment();
                break;

            case 4:
                // Twists
                elementCodeParts[0] = "";
                elementCodeParts[1] = "";
                elementCodeParts[2] = "1Tw";
                elementCodeParts[3] = "B";
                fragment[0] = new SelectRevolutionsFragment();
                fragment[1] = new SelectLevelFragment();
                fragment[2] = new EmptyFragment();
                fragment[3] = new EmptyFragment();
                break;

            case 5:
                // Throws
                elementCodeParts[0] = "";
                elementCodeParts[1] = "";
                elementCodeParts[2] = "1";
                elementCodeParts[3] = "T";
                fragment[0] = new SelectRevolutionsFragment();
                fragment[1] = new SelectJumpFragment();
                fragment[2] = new EmptyFragment();
                fragment[3] = new EmptyFragment();
                break;

            case 6:
                // Death Spirals
                elementCodeParts[0] = "";
                elementCodeParts[1] = "F";
                elementCodeParts[2] = "iDs";
                elementCodeParts[3] = "B";
                fragment[0] = new SelectDeathSpiralFrontBackSwitchFragment();
                fragment[1] = new SelectLevelFragment();
                fragment[2] = new EmptyFragmentLeft();
                fragment[3] = new EmptyFragmentLeft();

                break;

            case 7:
                // Pairs Spins - news ro rewrite for toggles
                elementCodeParts[0] = "";
                elementCodeParts[1] = "";
                elementCodeParts[2] = "PSp";
                elementCodeParts[3] = "B";
                fragment[0] = new EmptyFragment();
                fragment[1] = new EmptyFragment();
                fragment[2] = new SelectPairSpinNameFragment();
                fragment[3] = new SelectLevelFragment();

                break;

            default:
                // add the rest as coded
        }
        FragmentChange(currentElementTypeIndex);
    }
}
