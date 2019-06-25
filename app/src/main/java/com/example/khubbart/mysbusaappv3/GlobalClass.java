package com.example.khubbart.mysbusaappv3;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.ElementInfo;
import com.example.khubbart.mysbusaappv3.Model.Factors;
import com.example.khubbart.mysbusaappv3.Model.Programv2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalClass extends Application {

    // Use this activity to save application wide data that rarely change, but is not always the same, such as user information

    //Upon login, establish the current user's UID
    //Need to have skater create a username, save in profile, use as lead into all ids.  Call skaterID globally

    public FirebaseFirestore db;
    public DocumentReference programRef;
    private DocumentReference elementRef;
    public Programv2 rCurrentProgram;
    public int currentSOVIndex;
    public String elementName = "Code";
    public Double elementBaseValue = 0.0;
    public String tempTrimmedString;
    public char[] elementCodeCArray;
    public String tempString;
    public String[] SOVCode;
    public String[] SOVName;
    public String[] SOVBase;
    public String[] RequiredElementsKey;
    public int[] RequiredElementsValue;
    public int i;
    public int j;
    public int tempInt;
    public int tempIndex;
    public Double tempBaseValue;
    public String elementInfo;
    public String jumpFlag;
    public Boolean tempFlag;

    //Skaterid for all documents
    private String gSkaterID;

    public String getgSkaterID() {
        return gSkaterID;
    }

    public void setgSkaterID(String mSkaterID) {
        gSkaterID = mSkaterID;
    }


    //currentUserID for all
    private String currentUserUID;

    public String getCurrentUserUID() {
        return currentUserUID;
    }

    public void setCurrentUserUID(String sCurrentUserUID) {
        currentUserUID = sCurrentUserUID;
    }

    //Programid for current selected program
    private String currentProgramID;

    public String getCurrentProgramID() {
        return currentProgramID;
    }

    public void setCurrentProgramID(String tCurrentProgramID) {
        currentProgramID = tCurrentProgramID;
    }

    //Skater/User data
    private String skaterName;

    public String getSkaterName() {
        return skaterName;
    }

    public void setSkaterName(String sSkaterName) {
        skaterName = sSkaterName;
    }

    //currentProgram
    private Programv2 gCurrentProgram;

    public Programv2 getGCurrentProgram() {
        return gCurrentProgram;
    }

    public void setGCurrentProgram(Programv2 sCurrentProgram) {
        tempString = "     ";
        //Log.i(" ", tempString);
        //Log.i("ProgDesc-Set ", sCurrentProgram.getProgramDescription());
        //Log.i(" ", tempString);
        gCurrentProgram = sCurrentProgram;
    }



    /*
    This works!!
        //A basic method to get total component value
        public Double sumComponents(Double compScore[]) {
            int i;
            double sumComponents = 0;
            String tempString;
            NumberFormat numberFormat2D = new DecimalFormat("###.00");
            for (i=0; i<5; i++){
                if(compScore[i] != null) sumComponents += compScore[i];
            }
            //tempString = numberFormat2D.format(sumComponents);
            //scoresTextView[1].setText(tempString);
            return sumComponents;
        }
        */

    //Get SOV Base and Element Name for element code
    //public ElementInfo elementInfoLookUp(String elementCode) {
    public String elementInfoLookUp(String elementCode) {
        SOVCode = getResources().getStringArray(R.array.SOV_Code);
        SOVName = getResources().getStringArray(R.array.SOV_Name);
        SOVBase = getResources().getStringArray(R.array.SOV_Base);
        currentSOVIndex = Arrays.asList(SOVCode).indexOf(elementCode);
        // Need to add error checker for code not found
        if (currentSOVIndex > 0) { //We have found a matching element for the code
            elementName = Arrays.asList(SOVName).get(currentSOVIndex);
            elementBaseValue = Double.valueOf(Arrays.asList(SOVBase).get(currentSOVIndex));
            //elementInfo.setElementCode(elementCode);
            //elementInfo.setElementName(elementName);
            //elementInfo.setElementBaseValue(elementBaseValue);
            elementInfo = elementCode + "," + elementName + "," + Arrays.asList(SOVBase).get(currentSOVIndex);
        } else {  //Check for combo or change to unfound////
            if (elementCode != null) { //check for combo jump
                tempTrimmedString = elementCode.replaceAll("\\s+", ""); //Trim any spaces - CAREFUL, throws an error if null
                elementCodeCArray = tempTrimmedString.toCharArray();
                j = elementCodeCArray.length;
                for (i = 0; i < j; i++) {
                    tempString = String.copyValueOf(elementCodeCArray, i, 1);
                    if (tempString.equals("+")) {
                        //We have a combo - But handled value elsewhere for using this to just get val,ue of one element and will need to call it in cmbo check
                        //Set to empty values
                        //elementInfo.setElementCode("Combo");
                        //elementInfo.setElementName("");
                        //elementInfo.setElementBaseValue(0.0);
                        elementInfo = "Combo, ,0.0";
                        i = j; //Jump to end once we find the "+" sign
                    }
                }
            } else {
                //Set to empty values
                //elementInfo.setElementCode("Code");
                //elementInfo.setElementName("");
                //elementInfo.setElementBaseValue(0.0);
                elementInfo = "Combo, ,0.0";
            }
        }
        return elementInfo;
    }
    public Double CalcElementBaseValue (String eCode){
        tempIndex = ElementIndexLookUp(eCode.trim());
        if (tempIndex > -1) {
            if (tempIndex < 999) {
                //Valid, non-combo element
                tempBaseValue = ElementBaseLookUp(tempIndex);
            } else {
                //Combo Jump
                tempBaseValue = calcComboJump(eCode, Boolean.TRUE);
            }
        } else {
            tempBaseValue = 0.00;
        }
        return tempBaseValue;
    }

    public int ElementIndexLookUp(String elementCode) {
        SOVCode = getResources().getStringArray(R.array.SOV_Code);
        currentSOVIndex = Arrays.asList(SOVCode).indexOf(elementCode);
        //Log.i("SOVindex", String.valueOf(currentSOVIndex));
        if (currentSOVIndex < 0) {
            if (elementCode.length() < 2) {
                //THis is a blank, will throw an error if we check it
            } else {
                //There is something in elementCode
                tempString = ElementCheckForCombo(elementCode);
                if (tempString.equals("Combo")) {
                    //Flag a combo jump
                    currentSOVIndex = 999;
                } else {
                    //Flag an unrecognized element, may not require any information
                }
            }
        }
        // Need to add error checker for code not found
        return currentSOVIndex;
    }

    public String ElementNameLookUp(int elementIndex) {
        SOVName = getResources().getStringArray(R.array.SOV_Name);
        elementName = Arrays.asList(SOVName).get(elementIndex);
        return elementName;
    }

    public Double ElementBaseLookUp(int elementIndex) {
        SOVBase = getResources().getStringArray(R.array.SOV_Base);
        elementBaseValue = Double.valueOf(Arrays.asList(SOVBase).get(currentSOVIndex));
        return elementBaseValue;
    }

    public String ElementCheckForCombo(String elementCode) {
        //if (elementCode != null) { //check for combo jump
        tempTrimmedString = elementCode.replaceAll("\\s+", ""); //Trim any spaces - CAREFUL, throws an error if null
        elementCodeCArray = tempTrimmedString.toCharArray();
        j = elementCodeCArray.length;
        for (i = 0; i < j; i++) {
            tempString = String.copyValueOf(elementCodeCArray, i, 1);
            if (tempString.equals("+")) {
                //We have a combo - But handled value elsewhere for using this to just get value of one element and will need to call it in cmbo check
                jumpFlag = "Combo";
                i = j; //Jump to end once we find the "+" sign
            } else {
                jumpFlag = "Invalid Element";
            }
        }
        //Log.i("jumpFlag",jumpFlag);
        return jumpFlag;
    }

    public Double calcComboJump(String cElementCode, Boolean cFlag) {
        //It should already be determiend to be a combo when sent to this method
        //Toast.makeText(ProgramViewActivity.this, cElementCode, Toast.LENGTH_SHORT).show();
        tempTrimmedString = cElementCode.replaceAll("\\s+", ""); //Trim any spaces - CAREFUL, throws an error if null
        elementCodeCArray = tempTrimmedString.toCharArray();
        int num = 0;
        int eStart = 0;
        int eEnd = 0;
        int sOVIndex;
        Double jumpMax = 0.00;
        Double tempComboTotal = 0.0;
        String comboCode[] = new String[4];
        for (int j = 0; j < elementCodeCArray.length; j++) {
            tempString = String.copyValueOf(elementCodeCArray, j, 1);
            if (tempString.equals("+")) {
                //We have a combo
                eEnd = j; // In substring, end position is not included in extract
                comboCode[num] = tempTrimmedString.substring(eStart, eEnd);
                //Log.i("*************comboCode ", comboCode[num]);
                //int currentSOVIndex = Arrays.asList(SOVCode).indexOf(comboCode[num]);
                //int currentSOVIndex = globalClass.ElementIndexLookUp(comboCode[num]);
                sOVIndex = ElementIndexLookUp(comboCode[num]);
                if (sOVIndex > 0) {
                    tempComboTotal += ElementBaseLookUp(sOVIndex);
                    //Check for max jump value
                    if (ElementBaseLookUp(sOVIndex) > jumpMax){
                        jumpMax = ElementBaseLookUp(sOVIndex);
                    }
                }
                num = num + 1;
                eStart = j + 1;
            }
        }
        //Add last item of combo
        comboCode[num] = tempTrimmedString.substring(eStart); // This should take it thru the end
        sOVIndex = ElementIndexLookUp(comboCode[num]);
        if (sOVIndex > 0) {
            tempComboTotal += ElementBaseLookUp(sOVIndex);
            //Check for max jump value
            if (ElementBaseLookUp(sOVIndex) > jumpMax){
                jumpMax = ElementBaseLookUp(sOVIndex);
            }
        }
        if (cFlag){
            //True Bollean means combo value
            return tempComboTotal;
        } else {
            //False Boolean means base or GOE
            return jumpMax;
        }
    }


    //Determine Required Elements
    public int calcRequiredElements(String programType) {
        //Given programType of LevelDisciplineSegemt will return maximum number of elements
        Resources resources = getResources();
        String[] requiredElements;
        char[] reqEleLine;
        int eCount;
        int totalElements = 0;
        requiredElements = resources.getStringArray(R.array.RequiredElements2019);
        int len = requiredElements.length;
        for (int i = 0; i < len; i++) {
            //Find proper string
            if (requiredElements[i].contains(programType)) {
                reqEleLine = requiredElements[i].toCharArray();
                int len2 = reqEleLine.length;
                int k = 0;
                String tempChar;
                tempString = "";
                for (int j = 0; j < len2; j++) {
                    tempChar = String.copyValueOf(reqEleLine, j, 1);
                    if (tempChar.equals(",")) {
                        if (TextUtils.isDigitsOnly(tempString)) {
                            eCount = Integer.parseInt(tempString);
                            totalElements = totalElements + eCount;
                        }
                        tempString = "";
                    } else {
                        tempString = tempString + tempChar;
                    }
                }
            }
        }
        return totalElements;
    }

    //Get Factor Table
    public Double[] getFactors(String tProgramDescription) {
        //0 - Segment factor
        //1 - Skating skills
        Resources resources = getResources();
        String tempString;
        String[] factorTemp;
        Double[] factors = new Double[8];
        String[][] factorTable = new String[30][11];
        ArrayList<String> factorTablePointer = new ArrayList<String>();
        factorTemp = resources.getStringArray(R.array.Factors);
        int len = factorTemp.length;
        for (int i = 0; i < len; i++) {
            //parse factor table from resources intp usable form and return
            char[] factorLine = factorTemp[i].toCharArray();
            int len2 = factorLine.length;
            int k = 0;
            String tempChar;
            tempString = "";
            for (int j = 0; j < len2; j++) {
                tempChar = String.copyValueOf(factorLine, j, 1);
                if (tempChar.equals(",")) {
                    factorTable[i][k] = tempString;
                    if (k == 0) {
                        factorTablePointer.add(tempString); // Needed for indexof
                    }
                    tempString = "";
                    k = k + 1;
                } else {
                    tempString = tempString + tempChar;
                }
            }
            factorTable[i][k] = tempString; // Need to capture last item
        }
        int progDescrPointer = factorTablePointer.indexOf(tProgramDescription);
        for (int i = 0; i < 8; i++) {
            factors[i] = Double.valueOf(factorTable[progDescrPointer][i + 1]);
            tempString = String.valueOf(i) + " " + String.valueOf(factors[i]);
            //Log.i("********factors: ", tempString);
        }
        return factors;
    }

    //Pull program information using programID
    public void retrieveCurrentProgram(String rCurrentProgramID) {
        //Get a program from database
        //Log.i("retriev = ID: ", rCurrentProgramID);
        db = FirebaseFirestore.getInstance();
        DocumentReference programRef = db.collection("Programs").document(rCurrentProgramID);
        programRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    rCurrentProgram = document.toObject(Programv2.class);
                    //Log.i("retriev - Desv ", rCurrentProgram.getProgramDescription());

                    //Save current program to use in all pages.
                    setGCurrentProgram(rCurrentProgram);
                }
            }
        });
    }

    //Pull program information using programID
    public Programv2 fetchCurrentProgram(String rCurrentProgramID) {
        //Get a program from database
        db = FirebaseFirestore.getInstance();
        DocumentReference programRef = db.collection("Programs").document(rCurrentProgramID);
        programRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    rCurrentProgram = document.toObject(Programv2.class);
                }
            }
        });
        //Log.i("Current", String.valueOf(rCurrentProgram.getElements()));
        return rCurrentProgram;
    }


    //Change the Element Code Button with a Dialog
    public void changeButtonDialog(final int tNum) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //LayoutInflater inflater = getLayoutInflater();
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
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
