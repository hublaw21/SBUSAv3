package com.example.khubbart.mysbusaappv3;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.PlannedProgramContent;
import com.example.khubbart.mysbusaappv3.Model.Program;
import com.example.khubbart.mysbusaappv3.Model.Programv2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.khubbart.mysbusaappv3.R.id.editTextEditProgramDescription;

public class ProgramEditActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FirebaseFirestore db;
    public List<Program> program = new ArrayList<>();
    public List<PlannedProgramContent> elements = new ArrayList<>();
    //public ArrayList<ElementInfo> elementInfoList = new ArrayList<ElementInfo>();
    //public List<String> elementList = new ArrayList<>();
    public String[] currentProgramElements = new String[13];
    //public String[] tempStringArray = new String[13];
    public int[] currentProgramElementIndex = new int[13];
    public String[] currentProgramElementNames = new String[13];
    public Double[] currentProgramElementValues = new Double[13];

    public int requiredElements;
    public int resID;
    public int i;
    public int tempInt;
    public int buttonPointer;
    public String currentUserUID;
    public String currentProgramID;
    public String currentSkaterName;
    public String currentProgramDescription;
    public Programv2 currentProgram;

    public Button elementButton[] = new Button[13];
    public Button saveButton;
    public TextView textViewCompetitionName;
    public TextView textViewProgramDescription;
    public TextView textViewTechnicalTotal;
    public TextView[] textViewElementID = new TextView[13];
    public TextView[] textViewElementName = new TextView[13];
    public TextView[] textViewElementBaseValue = new TextView[13];
    public EditText editTextEditProgramDescription;
    public String tempString;
    public String tempRowID;
    public String tempRowName;
    public String tempRowBase;
    public String tempElementButton;
    public GlobalClass globalClass;
    public Intent myIntent;
    public DecimalFormat decimalFormat = new DecimalFormat("###.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_program_edit);

        //Set up toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarEditProgram);
        setSupportActionBar(myToolbar);

        textViewTechnicalTotal = findViewById(R.id.technicalTotal);
        saveButton = findViewById(R.id.buttonSaveProgram);
        editTextEditProgramDescription = findViewById(R.id.editTextEditProgramDescription);
        editTextEditProgramDescription.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                currentProgramDescription = editTextEditProgramDescription.getText().toString();
                return false;
            }
        });

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
        tempString = currentProgram.getLevel() + currentProgram.getDiscipline() + currentProgram.getSegment();
        requiredElements = globalClass.calcRequiredElements(tempString);
        currentProgramDescription = currentProgram.getProgramDescription();
        editTextEditProgramDescription.setText(currentProgramDescription);

        //Set up button listeners for changing elements
        for (i = 0; i < 13; i++) {
            if (i < requiredElements) {
                tempRowName = "elementRow" + String.format("%02d", i) + "elementName";
                tempRowBase = "elementRow" + String.format("%02d", i) + "elementBaseValue";
                tempElementButton = "buttonRow" + String.format("%02d", i);
                //Get the ids for the row items
                resID = getResources().getIdentifier(tempRowName, "id", getPackageName());
                textViewElementName[i] = findViewById(resID);
                resID = getResources().getIdentifier(tempRowBase, "id", getPackageName());
                textViewElementBaseValue[i] = findViewById(resID);
                resID = getResources().getIdentifier(tempElementButton, "id", getPackageName());
                elementButton[i] = findViewById(resID);
                elementButton[i].setOnClickListener(this);
            } else {
                //Hide unused rows
                tempString = "elementRow" + String.format("%02d", i);
                resID = getResources().getIdentifier(tempString, "id", getPackageName());
                TableRow tr = findViewById(resID);
                tr.setVisibility(View.GONE);
            }
        }
        loadElements();

        //Setup Save Button
        saveButton.setOnClickListener(this);
        textViewTechnicalTotal.setText("Total Base Value: " + decimalFormat.format(sumTech()));
    }

    //Load initial elements from current program
    public void loadElements() {
        //Check for anything in element code and process
        for (i = 0; i < requiredElements; i++) {
            Log.i("loadE", currentProgramElements[i]);
            if (currentProgramElements[i].length() > 1) {
                //send to make row
                elementRowUpdate(currentProgramElements[i].trim(), i);
            } else {
                elementButton[i].setText("Add");
                currentProgramElementNames[i] = "Select an element";
                currentProgramElementValues[i] = 0.00;
            }
        }
    }


    //Lookups for elements
    public void elementRowUpdate(String eCode, int eNum) {
        currentProgramElements[eNum] = eCode;
        currentProgramElementIndex[eNum] = globalClass.ElementIndexLookUp(eCode.trim());
        if (currentProgramElementIndex[eNum] > -1) {
            if (currentProgramElementIndex[eNum] < 999) {
                //Valid, non-combo element
                currentProgramElementNames[eNum] = globalClass.ElementNameLookUp(currentProgramElementIndex[eNum]);
                currentProgramElementValues[eNum] = globalClass.ElementBaseLookUp(currentProgramElementIndex[eNum]);
            } else {
                //Combo Jump
                currentProgramElementNames[eNum] = "Combo - " + eCode;
                currentProgramElementValues[eNum] = globalClass.calcComboJump(eCode, Boolean.TRUE);
            }
        } else {
            currentProgramElementNames[eNum] = "Select an element";
            currentProgramElementValues[eNum] = 0.00;
        }
        //Update the appropriate textviews
        elementButton[eNum].setText(currentProgramElements[eNum]);
        textViewElementName[eNum].setText(currentProgramElementNames[eNum]);
        textViewElementBaseValue[eNum].setText(String.valueOf(currentProgramElementValues[eNum]));
        //tempString = "Total Base Value: " + String.format("%02d", sumTech());
        tempString = "Total Base Value: " + decimalFormat.format(sumTech());
        textViewTechnicalTotal.setText(tempString);
    }


    //default listener for all buttons
    @Override
    public void onClick(View view) {
        //Determine which button was pushed
        tempInt = view.getId();
        //Check for save button
        if (findViewById(tempInt) == saveButton) {
            //Save button action
            UpdateElements();
        } else {
            //Act on clicked element button
            //1st, get the row number from button name
            buttonPointer = 0;
            for (i = 0; i < requiredElements; i++) {
                if (findViewById(tempInt) == elementButton[i]) {
                    buttonPointer = i;
                    i = 13; //Just speed it up a little
                }
            }
            //Now work on button
            changeButtonDialog(buttonPointer);
        }
    }

    private void UpdateElements() {
        //currentProgram is the Programv2 modelled instance from Firestore, we need to add updates of elements
        DocumentReference programRef = db.collection("Programs").document(currentProgramID);
        programRef.update("elements", currentProgram.getElements());
        programRef.update("programDescription", currentProgramDescription)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProgramEditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //A basic method to get total technical value
    public Double sumTech() {
        double sumTech = 0;
        for (int i = 0; i < requiredElements; i++) {
            //Log.i("sumtech", String.valueOf(i));
            if (currentProgramElementValues[i] != null) sumTech += currentProgramElementValues[i];
        }
        return sumTech;
    }

    public void changeButtonDialog(final int dialogButtonPointer) {
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
                String tElementCode = editTextElementCode.getText().toString();
                updateButton(dialogButtonPointer, tElementCode);
                /*
                currentProgramElements[dialogButtonPointer] = tElementCode;
                currentProgram.setElements(Arrays.asList(currentProgramElements));
                elementRowUpdate(tElementCode, dialogButtonPointer);
                */
                dialog.dismiss();
            }
        });

        // Set lookup button click listener
        lookupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send to the Element Lookup Activity
                myIntent = new Intent(ProgramEditActivity.this, ElementLookupActivity.class);
                //myIntent.putExtra("elementNumber", dialogButtonPointer);
                //The buttonDialogPointer is the requestCode returned in results, so we know which element
                startActivityForResult(myIntent, dialogButtonPointer);
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

    //Handle an element code returned from lookup
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = getIntent().getExtras();
        // Check to be sure we have something
        if (extras == null) {
            //Do nothing
        } else {
            String tElementCode = data.getStringExtra("elementCode");
            Toast.makeText(ProgramEditActivity.this, "Element Code " + tElementCode, Toast.LENGTH_LONG).show();
            updateButton(requestCode, tElementCode);
        }
    }

    //@Override
    //Update the button after changing element code
    protected void updateButton(int elementNumber, String elementCode) {
        currentProgramElements[elementNumber] = elementCode;
        currentProgram.setElements(Arrays.asList(currentProgramElements));
        elementRowUpdate(elementCode, elementNumber);
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
                Intent intentBundle = new Intent(ProgramEditActivity.this, ElementLookupActivity.class);
                startActivity(intentBundle);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
