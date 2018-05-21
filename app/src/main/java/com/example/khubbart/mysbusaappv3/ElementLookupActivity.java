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
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//import static com.example.khubbart.mysbusaappv3.Element.populateElementTable;

public class ElementLookupActivity extends AppCompatActivity {

    private ElementDatabase elementDatabase;
    private List<Element> elements;
    //public element;
    private TextView textViewElementLookup01;
    private TextView textViewElementLookupResult;
    private EditText edit_elementID;
    private EditText edit_elementName;
    private Integer rowCount = -1;
    private Button buttonSubmit;
    public String newElementID;
    public String newElementName;
    public Element element;
    public Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_lookup);
        textViewElementLookup01 = findViewById(R.id.textView2);
        textViewElementLookupResult = findViewById(R.id.textView3);
        edit_elementID = findViewById(R.id.edit_elementID);
        edit_elementName = findViewById(R.id.edit_elementName);
        buttonSubmit = findViewById(R.id.buttonSubmit);


        //whenever the activity is started, it reads data from database and stores it into local array list 'elements'
        final ElementDatabase elementDatabase = Room.databaseBuilder(getApplicationContext(),
                ElementDatabase.class,
                "element_table")
                .allowMainThreadQueries().build();


        // Test new Thread

        // New thread for getting the data - see Virtuooza add a room bookmark for adding recycler
        /*
        Runnable r = new Runnable() {
            @Override
            public void run() {
                elements = elementDatabase.elementDao().getAllElements();
                rowCount = elementDatabase.elementDao().rowCount();
                if (rowCount == 0) {
                    //populateElementTable();
                    //rowCount = db.elementDao().rowCount();
                    //elements = ("1F", "Single Flip");
                    //db.elementDao().insertAllElements(elements);
                }
                textViewElementLookup01.setText(String.valueOf(rowCount));
            }
        };

        Thread newThread= new Thread(r);
        newThread.start();
        */
        // End new Thread

        /*
        elementDatabase =ElementDatabase.getInstance(ElementLookupActivity.this);
        rowCount = elementDatabase.elementDao().rowCount();
        textViewElementLookup01.setText(String.valueOf(rowCount));
        */

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put new element into Room database test
                //rowCount = 3;
                rowCount = elementDatabase.elementDao().rowCount();
                textViewElementLookup01.setText(String.valueOf(rowCount));

                //newElementID = edit_elementID.getText().toString();
                //newElementName = edit_elementName.getText().toString();
                /*
                element.setElementID(newElementID);
                element.setElementName(newElementName);
                */
                // bad form to insert from main thread
                // this triggers crash: elementDatabase.elementDao().insertElement(element);
                //element = new Element(newElementID, newElementName);
                // create worker thread to insert data into database
                //new InsertElement(ElementLookupActivity.this, element).execute();

                // try to see what is in the database
                newElementID = edit_elementID.getText().toString();
                cursor = elementDatabase.elementDao().findElement(newElementID);
                if (cursor.moveToFirst()) {
                    newElementName = cursor.getString(cursor.getColumnIndex("elementName"));
                }
                rowCount = elementDatabase.elementDao().rowHitCount(newElementID);
                textViewElementLookup01.setText(String.valueOf(rowCount));
                textViewElementLookupResult.setText(newElementName);

            }
        });
    }


        /*
        // New thread for getting the data - see Virtuooza add a room bookmark for adding recycler
        Runnable r = new Runnable() {
            @Override
            public void run() {
                elements = db.elementDao().getAllElements();
                rowCount = db.elementDao().rowCount();
                if (rowCount == 0) {
                    //populateElementTable();
                    //rowCount = db.elementDao().rowCount();
                    //elements = ("1F", "Single Flip");
                    //db.elementDao().insertAllElements(elements);
                }
                textViewElementLookup01.setText(String.valueOf(rowCount));
            }
        };

        Thread newThread= new Thread(r);
        newThread.start();
    }
    */
    private static class InsertElement extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<ElementLookupActivity> activityReference;
        private Element element;

        // only retain a weak reference to the activity
        InsertElement(ElementLookupActivity context, Element element){
            activityReference = new WeakReference<>(context);
            this.element = element;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().elementDatabase.elementDao().insertElement(element);
            return true;
        }

        // onPostExecute runs on main thread
        /*
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(note,1);
            }
        }
        */

    }
}
