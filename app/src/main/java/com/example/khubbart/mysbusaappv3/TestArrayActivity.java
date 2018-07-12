package com.example.khubbart.mysbusaappv3;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TestArrayActivity extends AppCompatActivity{

    public String tempCode;
    public TextView mTextViewPartA;
    public TextView mTextViewPartB;
    public String[] importStep1 = new String[3];
    public String[][] importStep2 = new String[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_2d_array);
        super.onCreate(savedInstanceState);
        mTextViewPartA = findViewById(R.id.textView1);
        mTextViewPartB = findViewById(R.id.textView2);

        Resources resources = getResources();
        importStep1 = resources.getStringArray(R.array.test2DArray);
        //importStep2{0][] = importStep1;
        mTextViewPartA.setText(importStep1[0]);
        mTextViewPartB.setText(importStep1[1]);

    }

}
