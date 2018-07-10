package com.example.khubbart.mysbusaappv3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AddProgramActivity extends AppCompatActivity
    implements SelectSkaterLevelFragment.OnChangeSkaterLevelRadioButtonInteractionListener,
        SelectDisciplineFragment.OnChangeDisciplineRadioButtonInteractionListener,
        SelectSegmentFragment.OnChangeSegmentRadioButtonInteractionListener,
        ButtonBarFragment.ButtonBarInteractionListener{

    public String tempCode;
    public String mLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
    }

    // Get Skater Discipline
    @Override
    public void onChangeDisciplineRadioButtonInteraction(String tempCode) {
        // Get skater disipline
        mLevel = tempCode;
    }

    // Get Skater Level
    @Override
    public void onChangeSkaterLevelRadioButtonInteraction(String tempCode) {
       // Get skater level
        mLevel = tempCode;
    }

    // Get Skater Segment
    @Override
    public void onChangeSegmentRadioButtonInteraction(String tempCode) {
        // Get skater level
        mLevel = tempCode;
    }

    // Liaten for Button Bar
    @Override
    public String ButtonBarInteraction(String tempCode) {
        // Act of returned code
        Toast.makeText(getApplicationContext(), "Button Bar Retrun: " + tempCode, Toast.LENGTH_LONG).show();
        return tempCode;
    }


}
