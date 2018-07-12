package com.example.khubbart.mysbusaappv3;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AddProgramActivity extends AppCompatActivity
    implements SelectSkaterLevelFragment.OnChangeSkaterLevelRadioButtonInteractionListener,
        SelectDisciplineFragment.OnChangeDisciplineRadioButtonInteractionListener,
        SelectSegmentFragment.OnChangeSegmentRadioButtonInteractionListener,
        ButtonBarFragment.ButtonBarInteractionListener{

    public String tempCode;
    public String mProgramDescription;
    public String mLevel;
    public TextView mTextViewTest;
    public TextView mTextViewProgramDescription;
    public int[] programIndexes = new int[4]; // 0-competition, 1-discipline, 2-level, 3-segment
    public String[] programDescription = new String[4]; // 0-competition, 1-discipline, 2-level, 3-segment
    public String[] discipline = new String[3];
    public String[] level = new String[5];
    public String[] segment = new String[2];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
        mTextViewTest = findViewById(R.id.textViewTest);
        mTextViewProgramDescription = findViewById(R.id.textViewProgramAddDescription);
        mTextViewTest.setText("tempCode2");
        Resources resources = getResources();
        discipline = resources.getStringArray(R.array.disciplines);
        level = resources.getStringArray(R.array.levels);
        segment = resources.getStringArray(R.array.segments);

    }

    // Get Skater Discipline
    @Override
    //public void onChangeDisciplineRadioButtonInteraction(String tempCode) {
    public void onChangeDisciplineRadioButtonInteraction(int tempIndex) {
        // Get skater discipline
        programIndexes[1] = tempIndex;
        programDescription[1] = discipline[tempIndex];
        makeProgramDescription();
    }

    // Get Skater Level
    @Override
    public void onChangeSkaterLevelRadioButtonInteraction(int tempIndex) {
       // Get skater level
        programIndexes[2] = tempIndex;
        programDescription[2] = level[tempIndex];
        makeProgramDescription();
    }

    // Get Skater Segment
    @Override
    public void onChangeSegmentRadioButtonInteraction(int tempIndex) {
        // Get skater level
        programIndexes[3] = tempIndex;
        programDescription[3] = segment[tempIndex];
        makeProgramDescription();
    }

    // Listen for Button Bar
    @Override
    public String ButtonBarInteraction(String tempCode2) {
        // Act of returned code
        Toast.makeText(getApplicationContext(), "Button Bar Retrun: " + tempCode2, Toast.LENGTH_LONG).show();
        return tempCode2;
        //mTextViewTest.setText(tempCode2);
    }

    //Make the description string
    public void makeProgramDescription(){
        mProgramDescription = "";
        mProgramDescription += level[programIndexes[2]] + " ";
        mProgramDescription += discipline[programIndexes[1]] + " ";
        mProgramDescription += segment[programIndexes[3]];
        if(mProgramDescription != null) {
            mTextViewProgramDescription.setText(mProgramDescription);
        } else {
            mTextViewProgramDescription.setText("Empty");
        }

    }

}
