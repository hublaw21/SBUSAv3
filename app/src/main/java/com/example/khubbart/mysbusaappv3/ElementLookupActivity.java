package com.example.khubbart.mysbusaappv3;

import android.arch.persistence.room.Room;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ElementLookupActivity extends AppCompatActivity {

    public ElementDatabase elementDatabase;
    public List<Element> elements;
    public TextView textViewElementLookup01;
    public TextView textViewElementLookupResult;
    public EditText edit_elementID;
    public EditText edit_elementName;
    public Integer rowCount = -1;
    public Button buttonSubmit;
    public String newElementID;
    public String newElementName;
    public String elementCode;
    public Element element;
    public Cursor cursor;
    public RadioGroup rgJump;
    public RadioGroup rgRevs;
    public String jumpCode = "T";
    public String revCode = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_lookup);
        textViewElementLookup01 = findViewById(R.id.textView2);
        textViewElementLookupResult = findViewById(R.id.textViewElementResult);
        edit_elementID = findViewById(R.id.edit_elementID);
        //edit_elementName = findViewById(R.id.edit_elementName);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        rgJump = findViewById(R.id.radioGroupJumps);
        rgRevs = findViewById(R.id.radioGroupRevs);

        //whenever the activity is started, it reads data from database and stores it into local array list 'elements'
        final ElementDatabase elementDatabase = Room.databaseBuilder(getApplicationContext(),
                ElementDatabase.class,
                "element_table")
                .allowMainThreadQueries() // take out for final
                .build();

        rgJump.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
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
        });

        rgRevs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
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
                cursor = elementDatabase.elementDao().findElement(elementCode);
                if (cursor.moveToFirst()) {
                    newElementName = cursor.getString(cursor.getColumnIndex("elementName"));
                } else {
                    newElementName = "Not Found";
                }

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowCount = elementDatabase.elementDao().rowCount();
                textViewElementLookup01.setText(String.valueOf(rowCount));
                // Looks up element code, returns and and puts into view  on  submit
                newElementID = edit_elementID.getText().toString();
                cursor = elementDatabase.elementDao().findElement(newElementID);
                if (cursor.moveToFirst()) {
                    newElementName = cursor.getString(cursor.getColumnIndex("elementName"));
                } else {
                    newElementName = "Not Found";
                }
                rowCount = elementDatabase.elementDao().rowHitCount(newElementID);
                textViewElementLookup01.setText(String.valueOf(rowCount));
                textViewElementLookupResult.setText(newElementName);
            }
        });
    }
        public void updateElement(){
            elementCode = revCode + jumpCode;
            textViewElementLookupResult.setText(elementCode);
           /*
            cursor = elementDatabase.elementDao().findElement(elementCode);
            if (cursor.moveToFirst()) {
                newElementName = "True";
                //newElementName = cursor.getString(cursor.getColumnIndex("elementName"));
            } else {
                newElementName = "Not Found";
            }
            */
            textViewElementLookupResult.setText(newElementName);
        }

}
