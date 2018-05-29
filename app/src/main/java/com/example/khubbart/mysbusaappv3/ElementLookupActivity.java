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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class ElementLookupActivity extends AppCompatActivity implements
        SelectJumpFragment.OnChangeJumpRadioButtonInteractionListener,
        SelectRevolutionsFragment.OnChangeRevolutionsRadioButtonInteractionListener,
        AdapterView.OnItemSelectedListener {

    public TextView textViewItemSelected;
    public TextView textViewElementDetailName;
    public TextView textViewElementDetailBaseValue;
    public TextView[] textViewElementDetailGOE = new TextView[11];
    public String elementCode;
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
    public Integer currentSOVIndex;
    public Integer currentElementTypeIndex = 0; //This should be the same as the spinner position
    public String currentElementName;
    public String currentElementBase;
    public Double currentBase;
    public Double factorGOE;
    public Double[] currentGOE = new Double[11];
    public String[] currentGOEString = new String[11];
    //public Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_lookup);


        //Sett up spinner for selecting element type
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
        textViewElementDetailBaseValue = findViewById(R.id.elementDetailBaseValue);
        for(i=0; i<11; i++){
            textViewID = "elementDetailGOE" + i;
            if (i != 5) {
                // Do not do for 5, that is base value
                resID = getResources().getIdentifier(textViewID, "id", getPackageName());
                textViewElementDetailGOE[i] = findViewById(resID);
            }
        }
    }

    //Trading out fragments
    private void FragmentChange(Integer currentElementTypeIndex){
        // get fragment manager, start transaction
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment[] fragment = new Fragment[4];
        //Toast.makeText(getApplicationContext(), "Item number: " + position, Toast.LENGTH_LONG).show();
        // add
        /*if(position == 2){
            fragment = new SelectSpinNameFragment();
        }
        */
        switch (currentElementTypeIndex) {
            case 0:
                // No element selected
                break;
            case 1:
                //Jumps
                fragment[0] = new EmptyFragment();
                ft = fm.beginTransaction();
                ft.replace(R.id.container0, fragment[0]);
                ft.commit();

                fragment[1] = new SelectJumpFragment();
                ft = fm.beginTransaction();
                ft.replace(R.id.container1, fragment[1]);
                ft.commit();

                fragment[2] = new SelectRevolutionsFragment();
                ft = fm.beginTransaction();
                ft.replace(R.id.container2, fragment[2]);
                ft.commit();

                fragment[3] = new EmptyFragment();
                ft = fm.beginTransaction();
                ft.replace(R.id.container3, fragment[3]);
                ft.commit();
                break;
            case 2:
                //Spins
                fragment[1] = new SelectSpinNameFragment();
                ft = fm.beginTransaction();
                ft.replace(R.id.container0, fragment[1]);
                ft.commit();

                fragment[1] = new SelectLevelFragment();
                ft = fm.beginTransaction();
                ft.replace(R.id.container1, fragment[1]);
                ft.commit();

                fragment[2] = new SelectSpinSwitchesFragment();
                ft = fm.beginTransaction();
                ft.replace(R.id.container2, fragment[2]);
                ft.commit();

                fragment[3] = new EmptyFragment();
                ft = fm.beginTransaction();
                ft = fm.beginTransaction();
                ft.replace(R.id.container3, fragment[3]);
                ft.commit();
                break;
            default:
                //no good select
        }

        // alternatively add it with a tag
        // trx.add(R.id.your_placehodler, new YourFragment(), "detail");
        //ft.commit();

        // replace
        //FragmentTransaction ft = fm.beginTransaction();
        //ft.replace(R.id.container01, new SelectSpinFragment());
        //ft.commit();
        /*
        // remove
        Fragment fragment = fm.findFragmentById(R.id.container01);
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
        */
    };

    //
    @Override
    public void onChangeRevolutionsRadioButtonInteraction(int id) {
        switch (id) {
            case R.id.radioButtonRevs1:
                revCode = "1";
                break;
            case R.id.radioButtonRevs2:
                revCode = "2";
                break;
            case R.id.radioButtonRevs3:
                revCode = "3";
                break;
            case R.id.radioButtonRevs4:
                revCode = "4";
                break;
        }
        updateElement();
    }

    @Override
    public void onChangeJumpRadioButtonInteraction(int id) {
        switch (id) {
            case R.id.radioButtonJump1:
                jumpCode = "T";
                break;
            case R.id.radioButtonJump2:
                jumpCode = "S";
                break;
            case R.id.radioButtonJump3:
                jumpCode = "F";
                break;
            case R.id.radioButtonJump4:
                jumpCode = "Lo";
                break;
            case R.id.radioButtonJump5:
                jumpCode = "Lz";
                break;
            case R.id.radioButtonJump6:
                jumpCode = "A";
                break;
        }
        updateElement();
    }

    public void updateElement() {
        // Will need to create switch for each element type to do unique elementCode build for each
        if (TextUtils.isEmpty(revCode)) revCode = "1";
        if (TextUtils.isEmpty(jumpCode)) jumpCode = "T";
        elementCode = revCode + jumpCode;
        currentSOVIndex = Arrays.asList(SOVCode).indexOf(elementCode);
        // Need to add error checker for code not found
        currentElementName = Arrays.asList(SOVName).get(currentSOVIndex);
        currentElementBase = Arrays.asList(SOVBase).get(currentSOVIndex);
        textViewElementDetailName.setText(currentElementName + " - " + elementCode);
        textViewElementDetailBaseValue.setText(currentElementBase);

        currentBase = Double.valueOf(currentElementBase);
        factorGOE = -.5;
        for (i = 0; i < 11; i++) {
            if (i != 5){
                // Do not do for 5, that is base value
                currentGOE[i] = factorGOE * currentBase;
                currentGOEString[i] = String.format("%.2f", currentGOE[i]);
                textViewElementDetailGOE[i].setText(currentGOEString[i]);
            };
            factorGOE = factorGOE + .1;
        }
        // end of switch area
    }

    //Spinner Action
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Spinner action
        //String elementType = parent.getItemAtPosition(position).toString(); - Incase you want to pull the text from spinner
        currentElementTypeIndex = position;
        FragmentChange(currentElementTypeIndex);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //Spinner action - no selection
    }
}
