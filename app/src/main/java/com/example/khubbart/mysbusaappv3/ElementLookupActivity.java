package com.example.khubbart.mysbusaappv3;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import static com.example.khubbart.mysbusaappv3.Element.populateElementTable;

public class ElementLookupActivity extends AppCompatActivity {

    private ElementDatabase elementDatabase;
    private List<Element> elements;
    private TextView textViewElementLookup01;
    private Integer rowCount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_lookup);
        textViewElementLookup01 = findViewById(R.id.textViewElementLookup01);

        //whenever the activity is started, it reads data from database and stores it into local array list 'elements'
        final ElementDatabase db = Room.databaseBuilder(getApplicationContext(),
                ElementDatabase.class,
                "element_table")
                .build();

        // New thread for getting the data - see Virtuooza add a room bookmark for adding recycler
        Runnable r = new Runnable(){
            @Override
            public void run() {
                elements = db.elementDao().getAllElements();
                rowCount = db.elementDao().rowCount();
                if (rowCount == 0){
                    //populateElementTable();
                    //rowCount = db.elementDao().rowCount();
                    elements = ("1A", "Single Axel");
                    elements = ("1F", "Single Flip");
                    db.elementDao().insertAllElements(elements);
                }
                textViewElementLookup01.setText(String.valueOf(rowCount));
            }
        };

        Thread newThread= new Thread(r);
        newThread.start();
    }
}
