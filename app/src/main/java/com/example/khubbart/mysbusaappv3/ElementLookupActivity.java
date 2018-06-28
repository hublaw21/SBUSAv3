package com.example.khubbart.mysbusaappv3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
        SelectStepNameFragment.OnChangeStepNameRadioButtonInteractionListener,
        AdapterView.OnItemSelectedListener {

    public ToggleButton selectDisciplineToggleButton;

    public TextView textViewItemSelected;
    public TextView textViewElementDetailName;
    public TextView textViewElementCode;
    public TextView textViewElementDetailBaseValue;
    public TextView[] textViewElementDetailGOE = new TextView[11];
    public String elementCode;
    public String[] elementCodeParts = new String[4];
    public String newElementName;
    public String itemSelected;
    public String jumpCode = "T";
    public String revCode = "1";
    public String[] SOVCode;
    public String[] SOVName;
    public String[] SOVBase;
    public Integer i = 0;
    public Integer resID;
    public String textViewID;
    public String containerID;
    public Integer currentSOVIndex;
    public Integer currentElementTypeIndex = 0; //This should be the same as the spinner position
    public String currentElementName;
    public String currentElementBase;
    public Double currentBase;
    public Double factorGOE;
    public Double[] currentGOE = new Double[11];
    public String[] currentGOEString = new String[11];
    public Fragment[] fragment = new Fragment[4];
    public Boolean isFlying;
    public String tempCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_lookup);

        //Set up Discipline toggle buttons
        selectDisciplineToggleButton = (ToggleButton) findViewById(R.id.toggleButtonDiscipline);
        selectDisciplineToggleButton.setChecked(true); // set the current state of a toggle button
        selectDisciplineToggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(selectDisciplineToggleButton.isChecked()){
                    //Singles
                    //int resID = getResources().getIdentifier("pairsElementRow"tempString, "id", getPackageName());
                    TableRow tr = findViewById(R.id.pairsElementsRow);
                    tr.setVisibility(View.GONE);
                } else {
                    //Pairs
                    TableRow tr = findViewById(R.id.pairsElementsRow);
                    tr.setVisibility(View.VISIBLE);
                }
            }

        });

        //NEED TO WRITE NEW LOGIC for toggle buttons instead of spinner.

        //Set up spinner for selecting element type
        Spinner spinElementTypeSelect = findViewById(R.id.spinnerElementTypeSelect);
        spinElementTypeSelect.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.elementTypes, R.layout.my_spinner_style);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinElementTypeSelect.setAdapter(adapter);

        //Get SOV Table 2018 - Three matched arrays
        Resources resources = getResources();
        SOVCode = resources.getStringArray(R.array.SOV_Code);
        SOVName = resources.getStringArray(R.array.SOV_Name);
        SOVBase = resources.getStringArray(R.array.SOV_Base);

        // Set up textviews for element detail fragment
        //textViewItemSelected = findViewById(R.id.textViewSelected);
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

    //Top Button Row - Discipline
    // Discipline Toggles (x2) Listener
    /*
    @Override
    public void onChangeSinglesSwitchInteraction(boolean isSingles) {
        if (isSingles) {
            //Turn off pairs button
            elementCodeParts[0] = "F";
        } else {
            elementCodeParts[0] = "";
        }
        updateElement();
    }
    */

    //Trading out fragments
    private void FragmentChange(Integer currentElementTypeIndex) {
        // get fragment manager, start transaction
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //Toast.makeText(getApplicationContext(), "Item number: " + position, Toast.LENGTH_LONG).show();
        // add

        for (i = 0; i < 4; i++) {
            ft = fm.beginTransaction();
            containerID = "container" + i;
            resID = getResources().getIdentifier(containerID, "id", getPackageName());
            //ft.replace(R.id.container1, fragment[1]); // this needs to do all of them.
            ft.replace(resID, fragment[i]);
            //ft[i].replace(getResources().getIdentifier(containerID, "id", getPackageName()), fragment[i]);
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
        if(currentElementTypeIndex == 6) elementCodeParts[3] += "Th";
        updateElement();
    }

    // Get Number of Revolutions
    @Override
    public void onChangeRevolutionsRadioButtonInteraction(String tempCode) {
        elementCodeParts[2] = tempCode;
        if(currentElementTypeIndex == 5) elementCodeParts[2] += "Tw";
        updateElement();

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
        if (elementCodeParts[1] != "5" && currentElementTypeIndex == 4) elementCodeParts[2] = "Li"; //In case Group 5 name is checked
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
        elementCodeParts[1] = tempCode;
        if (elementCodeParts[1] != "5") elementCodeParts[2] = "Li"; //In case Group 5 name is checked
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
        if(elementCodeParts[2] == "PiF")elementCodeParts[3] = "";
        updateElement();
    }
    // Get Step Name
    @Override
    public void onChangeStepNameRadioButtonInteraction(String tempCode) {
        if(elementCodeParts[2] == "StSq") elementCodeParts[3]="";
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

    //Spinner Action
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Spinner action
        //String elementType = parent.getItemAtPosition(position).toString(); - In case you want to pull the text from spinner
        if (currentElementTypeIndex != position) {
            //prepare fragments and allow for default to avoid crashes
            Arrays.fill(fragment, new EmptyFragment()); // set all cells to empty
            switch (position) {
                case 0:
                    // No further action needed
                    break;
                case 1:
                    // Jumps
                    elementCodeParts[0] = "";
                    elementCodeParts[1] = "";
                    elementCodeParts[2] = "1";
                    elementCodeParts[3] = "T";
                    fragment[0] = new EmptyFragmentLeft();
                    fragment[1] = new SelectJumpFragment();
                    fragment[2] = new SelectRevolutionsFragment();
                    fragment[3] = new EmptyFragmentRight();
                    break;

                case 2:
                    // Spins
                    elementCodeParts[0] = "";
                    elementCodeParts[1] = "";
                    elementCodeParts[2] = "USp";
                    elementCodeParts[3] = "B";
                    fragment[0] = new EmptyFragment();
                    fragment[1] = new SwithContainerFragment();
                    fragment[2] = new SelectSpinNameFragment();
                    fragment[3] = new SelectLevelFragment();
                    break;

                case 3:
                    //Footwork
                    elementCodeParts[0] = "";
                    elementCodeParts[1] = "";
                    elementCodeParts[2] = "StSq";
                    elementCodeParts[3] = "B";
                    fragment[0] = new EmptyFragment();
                    fragment[1] = new EmptyFragment();
                    fragment[2] = new SelectStepNameFragment();
                    fragment[3] = new SelectLevelFragment();
                    break;

                case 4:
                    // Lifts
                    elementCodeParts[0] = "";
                    elementCodeParts[1] = "1";
                    elementCodeParts[2] = "Li";
                    elementCodeParts[3] = "B";
                    fragment[0] = new EmptyFragmentLeft();
                    fragment[1] = new SelectGroupFragment();
                    fragment[2] = new SelectLiftNameFragment();
                    fragment[3] = new SelectLevelFragment();
                    break;

                case 5:
                    // Twists
                    elementCodeParts[0] = "";
                    elementCodeParts[1] = "";
                    elementCodeParts[2] = "1Tw";
                    elementCodeParts[3] = "B";
                    fragment[0] = new EmptyFragmentLeft();
                    fragment[1] = new EmptyFragmentRight();
                    fragment[2] = new SelectRevolutionsFragment();
                    fragment[3] = new SelectLevelFragment();
                    break;

                case 6:
                    // Throws
                    elementCodeParts[0] = "";
                    elementCodeParts[1] = "";
                    elementCodeParts[2] = "1";
                    elementCodeParts[3] = "T";
                    fragment[0] = new EmptyFragmentLeft();
                    fragment[1] = new SelectJumpFragment();
                    fragment[2] = new SelectRevolutionsFragment();
                    fragment[3] = new EmptyFragmentRight();
                    break;

                case 7:
                    // Pairs Spins
                    elementCodeParts[0] = "";
                    elementCodeParts[1] = "";
                    elementCodeParts[2] = "PSp";
                    elementCodeParts[3] = "B";
                    fragment[0] = new EmptyFragment();
                    fragment[1] = new EmptyFragment();
                    fragment[2] = new SelectPairSpinNameFragment();
                    fragment[3] = new SelectLevelFragment();

                    break;

                case 8:
                    // Death Spirals
                    elementCodeParts[0] = "";
                    elementCodeParts[1] = "F";
                    elementCodeParts[2] = "iDs";
                    elementCodeParts[3] = "B";
                    fragment[0] = new EmptyFragmentLeft();
                    fragment[1] = new SelectDeathSpiralFrontBackSwitchFragment();
                    fragment[2] = new SelectDeathSpiralInOutSwitchFragment();
                    fragment[3] = new SelectLevelFragment();
                    break;

                default:
                    // add the rest as coded
            }
            currentElementTypeIndex = position;
            FragmentChange(currentElementTypeIndex);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //Spinner action - no selection
    }

    ;
}
