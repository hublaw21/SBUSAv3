package com.example.khubbart.mysbusaappv3;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import com.example.khubbart.mysbusaappv3.Model.ElementInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class GlobalClass extends Application {

    // Use this activity to save application wide data that rarely change, but is not always the same, such as user information

    //Upon login, establish the current user's UID
    private String currentUserUID;

    public String getCurrentUserUID() {
        return currentUserUID;
    }

    public void setCurrentUserUID(String sCurrentUserUID) {
        currentUserUID = sCurrentUserUID;
    }

    //Skater/User data
    private String skaterName;

    public String getSkaterName() {
        return skaterName;
    }

    public void setSkaterName(String sSkaterName) {
        skaterName = sSkaterName;
    }

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
    public String elementInfo;


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

    public int ElementIndexLookUp(String elementCode) {
        SOVCode = getResources().getStringArray(R.array.SOV_Code);
        currentSOVIndex = Arrays.asList(SOVCode).indexOf(elementCode);
        // Need to add error checker for code not found
        return currentSOVIndex;
    }

    public String ElementNameLookUp(int elementIndex) {
        SOVName = getResources().getStringArray(R.array.SOV_Name);
        elementName = Arrays.asList(SOVName).get(currentSOVIndex);
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
                        elementInfo = "Combo";
                        i = j; //Jump to end once we find the "+" sign
                    }
                }
        return elementInfo;
    }


    //Get Factor Table
    public Double[] getFactors(String tProgramDescription) {
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
            Log.i("*******************factors: ", tempString);
        }
        return factors;
    }
}
