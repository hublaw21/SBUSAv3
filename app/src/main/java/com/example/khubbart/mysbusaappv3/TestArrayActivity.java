package com.example.khubbart.mysbusaappv3;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class TestArrayActivity extends AppCompatActivity{

    public TextView mTextViewPartA;
    public TextView mTextViewPartB;
    public String[] importStep1;
    public String[] importStep2;
    public String[] importStep3;
    public String[] importStep4;
    public String[][] importComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_2d_array);
        super.onCreate(savedInstanceState);
        mTextViewPartA = findViewById(R.id.textView1);
        mTextViewPartB = findViewById(R.id.textView2);

        Resources resources = getResources();
        importStep1 = resources.getStringArray(R.array.competitionNamesArray);
        importStep2 = resources.getStringArray(R.array.competitionLocationArray);
        importStep3 = resources.getStringArray(R.array.competitionDatesArray);
        importStep4 = resources.getStringArray(R.array.competitionsHostArray);
        //importStep2{0][0] = importStep1;
        mTextViewPartA.setText(importStep1[20]);
        mTextViewPartB.setText(importStep1[21]);

        //Create an instance of ArrayAdapter containing competition names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_competition_name, importStep1);
        //Get instance of Autocomplete
        AutoCompleteTextView actv = findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);  //Will start from int characters
        actv.setAdapter(adapter);
        actv.setTextColor(Color.RED);

    }

}
