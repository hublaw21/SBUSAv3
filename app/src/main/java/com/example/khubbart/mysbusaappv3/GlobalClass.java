package com.example.khubbart.mysbusaappv3;

import android.app.Application;
import android.content.res.Resources;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GlobalClass extends Application {

    public Resources resources = getResources();
    public String[] SOVCode;
    public String[] SOVName;
    public String[] SOVBase;


    // Use this activity to save application wide data that rarely change, but is not always the same, such as user information

    //Upon login, establish the current user's UID
    private String currentUserUID;
    public String getCurrentUserUID(){
        return currentUserUID;
    }
    public void setCurrentUserUID(String sCurrentUserUID){
        currentUserUID = sCurrentUserUID;
    }

    //Skater/User data
    private String skaterName;
    public String getSkaterName(){
        return skaterName;
    }
    public void setSkaterName(String sSkaterName){
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
    public String[][] getFactors() {
        String[] factorTemp;
        String[][] factor_Table;
        String tLevel, tDiscipline, tSegment, tempString1, tempString2;
        int tSegmentFactor, tSS, tTR, tPE, tCO, tIN, tGCF, tFall;
        factorTemp = resources.getStringArray(R.array.Factors);
        int len = factorTemp.length;
        for (int i =0; i<len; i++){
            //parse factor table from resources intp usable form and return
        }
        return factor_Table;
    }


}
