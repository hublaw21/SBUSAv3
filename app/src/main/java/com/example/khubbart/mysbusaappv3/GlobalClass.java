package com.example.khubbart.mysbusaappv3;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;

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
/*
    //Get SOV Table 2018 - Three matched arrays
    public String[][] getSOV() {
        String[][] SOV_Table;
        SOVCode = resources.getStringArray(R.array.SOV_Code);
        SOVName = resources.getStringArray(R.array.SOV_Name);
        SOVBase = resources.getStringArray(R.array.SOV_Base);
        RequiredElementsKey = resources.getStringArray(R.array.requiredElementsKeyArray);
        RequiredElementsValue = resources.getIntArray(R.array.requiredElementsValueArray);
        return SOV_Table;
    }
*/
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
