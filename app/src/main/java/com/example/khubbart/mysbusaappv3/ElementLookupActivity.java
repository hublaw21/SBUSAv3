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

    /*
    public ElementDatabase elementDatabase;
    public List<Element> elements;
    public TextView textViewElementLookup01;
    public TextView textViewElementLookupResult;
    public TextView textView3;
    public EditText edit_elementID;
    public EditText edit_elementName;
    public Integer rowCount = -1;
    public Button buttonSubmit;
    public String newElementID;
    public Element element;
    public Cursor cursor;
    */
    public TextView textViewElementDetailName;
    public TextView textViewElementDetailBaseValue;
    public String elementCode;
    public String newElementName;
    public String newElementBaseValue;
    public RadioGroup rgJump;
    public RadioGroup rgRevs;
    public String jumpCode = "T";
    public String revCode = "1";
    public Integer i = 0;
    public Integer j;
    public String[][] elementTable = {
            {"Single Toeloop","1T","0.6","0.4","0.2","0.4","0.3","0","-0.1","-0.2","-0.3"},
            {"Single Salchow","1S","0.6","0.4","0.2","0.4","0.3","0","-0.1","-0.2","-0.3"},
            {"Single Loop","1Lo","0.6","0.4","0.2","0.5","0.4","0","-0.1","-0.2","-0.3"},
            {"Single Flip","1F","0.6","0.4","0.2","0.5","0.4","0.3","-0.1","-0.2","-0.3"},
            {"Single Lutz","1Lz","0.6","0.4","0.2","0.6","0.5","0.4","-0.1","-0.2","-0.3"},
            {"Single Axel","1A","0.6","0.4","0.2","1.1","0.8","0","-0.2","-0.4","-0.6"},
            {"Double Toeloop","2T","0.6","0.4","0.2","1.3","0.9","0","-0.2","-0.4","-0.6"},
            {"Double Salchow","2S","0.6","0.4","0.2","1.3","0.9","0","-0.2","-0.4","-0.6"},
            {"Double Loop","2Lo","0.9","0.6","0.3","1.8","1.3","0","-0.3","-0.6","-0.9"},
            {"Double Flip","2F","0.9","0.6","0.3","1.9","1.4","1.3","-0.3","-0.6","-0.9"},
            {"Double Lutz","2Lz","0.9","0.6","0.3","2.1","1.5","1.4","-0.3","-0.6","-0.9"},
            {"Double Axel","2A","1.5","1","0.5","3.3","2.3","0","-0.5","-1","-1.5"},
            {"Triple Toeloop","3T","2.1","1.4","0.7","4.3","3","0","-0.7","-1.4","-2.1"},
            {"Triple Salchow","3S","2.1","1.4","0.7","4.4","3.1","0","-0.7","-1.4","-2.1"},
            {"Triple Loop","3Lo","2.1","1.4","0.7","5.1","3.6","0","-0.7","-1.4","-2.1"},
            {"Triple Flip","3F","2.1","1.4","0.7","5.3","3.7","3.2","-0.7","-1.4","-2.1"},
            {"Triple Lutz","3Lz","2.1","1.4","0.7","6","4.2","3.6","-0.7","-1.4","-2.1"},
            {"Triple Axel","3A","3","2","1","8.5","5.9","0","-1","-2","-3"},
            {"Quad Toeloop","4T","3","2","1","10.3","8","0","-1.2","-2.4","-4"},
            {"Quad Salchow","4S","3","2","1","10.5","8.1","0","1.2","-2.4","-4"},
            {"Quad Loop","4Lo","3","2","1","12","8.4","0","1.2","-2.4","-4"},
            {"Quad Flip","4","3","2","1","12.3","8.6","8","1.2","-2.4","-4"},
            {"Quad Lutz","4Lz","3","2","1","13.6","9.5","8.4","1.2","-2.4","-4"},
            {"Quad Axel","4A","3.6","2.4","1.2","15","10.5","0","-1.2","-2.4","-4"},
            {"Upright Level B","USpB","1.5","1","0.5","1","0","0","-0.3","-0.6","-0.9"},
            {"Upright Level 1","USp1","1.5","1","0.5","1.2","0","0","-0.3","-0.6","-0.9"},
            {"Upright Level 2","USp2","1.5","1","0.5","1.5","0","0","-0.3","-0.6","-0.9"},
            {"Upright Level 3","USp3","1.5","1","0.5","1.9","0","0","-0.3","-0.6","-0.9"},
            {"Upright Level 4","USp4","1.5","1","0.5","2.4","0","0","-0.3","-0.6","-0.9"},
            {"Layback Level B","LSpB","1.5","1","0.5","1.2","0","0","-0.3","-0.6","-0.9"},
            {"Layback Level 1","LSp1","1.5","1","0.5","1.5","0","0","-0.3","-0.6","-0.9"},
            {"Layback Level 2","LSp2","1.5","1","0.5","1.9","0","0","-0.3","-0.6","-0.9"},
            {"Layback Level 3","LSp3","1.5","1","0.5","2.4","0","0","-0.3","-0.6","-0.9"},
            {"Layback Level 4","LSp4","1.5","1","0.5","2.7","0","0","-0.3","-0.6","-0.9"},
            {"Camel Level B","CSpB","1.5","1","0.5","1.1","0","0","-0.3","-0.6","-0.9"},
            {"Camel Level 1","CSp1","1.5","1","0.5","1.4","0","0","-0.3","-0.6","-0.9"},
            {"Camel Level 2","CSp2","1.5","1","0.5","1.8","0","0","-0.3","-0.6","-0.9"},
            {"Camel Level 3","CSp3","1.5","1","0.5","2.3","0","0","-0.3","-0.6","-0.9"},
            {"Camel Level 4","CSp4","1.5","1","0.5","2.6","0","0","-0.3","-0.6","-0.9"},
            {"Sit Level B","SSpB","1.5","1","0.5","1.1","0","0","-0.3","-0.6","-0.9"},
            {"Sit Level 1","SSp1","1.5","1","0.5","1.3","0","0","-0.3","-0.6","-0.9"},
            {"Sit Level 2","SSp2","1.5","1","0.5","1.6","0","0","-0.3","-0.6","-0.9"},
            {"Sit Level 3","SSp3","1.5","1","0.5","2.1","0","0","-0.3","-0.6","-0.9"},
            {"Sit Level 4","SSp4","1.5","1","0.5","2.5","0","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level B","FUSpB","1.5","1","0.5","1.5","1.1","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level 1","FUSp1","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level 2","FUSp2","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level 3","FUSp3","1.5","1","0.5","2.4","1.7","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level 4","FUSp4","1.5","1","0.5","2.9","2","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level B","FLSpB","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level 1","FLSp1","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level 2","FLSp2","1.5","1","0.5","2.4","1.7","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level 3","FLSp3","1.5","1","0.5","2.9","2","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level 4","FLSp4","1.5","1","0.5","3.2","2.2","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level B","FCSpB","1.5","1","0.5","1.6","1.1","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level 1","FCSp1","1.5","1","0.5","1.9","1.3","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level 2","FCSp2","1.5","1","0.5","2.3","1.6","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level 3","FCSp3","1.5","1","0.5","2.8","2","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level 4","FCSp4","1.5","1","0.5","3.2","2.2","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level B","FSSpB","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level 1","FSSp1","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level 2","FSSp2","1.5","1","0.5","2.3","1.6","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level 3","FSSp3","1.5","1","0.5","2.6","1.8","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level 4","FSSp4","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Upright Level B with One Change of Foot","CUSpB","1.5","1","0.5","1.5","1.1","0","-0.3","-0.6","-0.9"},
            {"Upright Level 1 with One Change of Foot","CUSp1","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Upright Level 2 with One Change of Foot","CUSp2","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Upright Level 3 with One Change of Foot","CUSp3","1.5","1","0.5","2.4","1.7","0","-0.3","-0.6","-0.9"},
            {"Upright Level 4 with One Change of Foot","CUSp4","1.5","1","0.5","2.9","2","0","-0.3","-0.6","-0.9"},
            {"Layback Level B with One Change of Foot","CLSpB","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Layback Level 1 with One Change of Foot","CLSp1","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Layback Level 2 with One Change of Foot","CLSp2","1.5","1","0.5","2.4","1.7","0","-0.3","-0.6","-0.9"},
            {"Layback Level 3 with One Change of Foot","CLSp3","1.5","1","0.5","2.9","2","0","-0.3","-0.6","-0.9"},
            {"Layback Level 4 with One Change of Foot","CLSp4","1.5","1","0.5","3.2","2.2","0","-0.3","-0.6","-0.9"},
            {"Camel Level B with One Change of Foot","CCSpB","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Camel Level 1 with One Change of Foot","CCSp1","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Camel Level 2 with One Change of Foot","CCSp2","1.5","1","0.5","2.3","1.6","0","-0.3","-0.6","-0.9"},
            {"Camel Level 3 with One Change of Foot","CCSp3","1.5","1","0.5","2.8","2","0","-0.3","-0.6","-0.9"},
            {"Camel Level 4 with One Change of Foot","CCSp4","1.5","1","0.5","3.2","2.2","0","-0.3","-0.6","-0.9"},
            {"Sit Level B with One Change of Foot","CSSpB","1.5","1","0.5","1.6","1.1","0","-0.3","-0.6","-0.9"},
            {"Sit Level 1 with One Change of Foot","CSSp1","1.5","1","0.5","1.9","1.3","0","-0.3","-0.6","-0.9"},
            {"Sit Level 2 with One Change of Foot","CSSp2","1.5","1","0.5","2.3","1.6","0","-0.3","-0.6","-0.9"},
            {"Sit Level 3 with One Change of Foot","CSSp3","1.5","1","0.5","2.6","1.8","0","-0.3","-0.6","-0.9"},
            {"Sit Level 4 with One Change of Foot","CSSp4","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Level B with Change of Position (2 Positions)","CoSp2pB","1.5","1","0.5","1.1","1","0","-0.3","-0.6","-0.9"},
            {"Level 1 with Change of Position (2 Positions)","CoSp2p1","1.5","1","0.5","1.3","1.1","0","-0.3","-0.6","-0.9"},
            {"Level 2 with Change of Position (2 Positions)","CoSp2p2","1.5","1","0.5","1.5","1.3","0","-0.3","-0.6","-0.9"},
            {"Level 3 with Change of Position (2 Positions)","CoSp2p3","1.5","1","0.5","1.8","1.5","0","-0.3","-0.6","-0.9"},
            {"Level 4 with Change of Position (2 Positions)","CoSp2p4","1.5","1","0.5","2.1","1.7","0","-0.3","-0.6","-0.9"},
            {"Level B with Change of Position (3 Positions)","CoSp3pB","1.5","1","0.5","1.5","1.1","0","-0.3","-0.6","-0.9"},
            {"Level 1 with Change of Position (3 Positions)","CoSp3p1","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Level 2 with Change of Position (3 Positions)","CoSp3p2","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Level 3 with Change of Position (3 Positions)","CoSp3p3","1.5","1","0.5","2.5","1.8","0","-0.3","-0.6","-0.9"},
            {"Level 4 with Change of Position (3 Positions)","CoSp3p4","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Level B with Change of Foot and Change of Position (2 Positions)","CCoSp2pB","1.5","1","0.5","1.5","1.1","0","-0.3","-0.6","-0.9"},
            {"Level 1 with Change of Foot and Change of Position (2 Positions)","CCoSp2p1","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Level 2 with Change of Foot and Change of Position (2 Positions)","CCoSp2p2","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Level 3 with Change of Foot and Change of Position (2 Positions)","CCoSp2p3","1.5","1","0.5","2.5","1.8","0","-0.3","-0.6","-0.9"},
            {"Level 4 with Change of Foot and Change of Position (2 Positions)","CCoSp2p4","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Level B with Change of Foot and Change of Position (3 Positions)","CCoSp3pB","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Level 1 with Change of Foot and Change of Position (3 Positions)","CCoSp3p1","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Level 2 with Change of Foot and Change of Position (3 Positions)","CCoSp3p2","1.5","1","0.5","2.5","1.8","0","-0.3","-0.6","-0.9"},
            {"Level 3 with Change of Foot and Change of Position (3 Positions)","CCoSp3p3","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Level 4 with Change of Foot and Change of Position (3 Positions)","CCoSp3p4","1.5","1","0.5","3.5","2.5","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level B with One Change of Foot","FCUSpB","1.5","1","0.5","1.5","1.1","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level 1 with One Change of Foot","FCUSp1","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level 2 with One Change of Foot","FCUSp2","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level 3 with One Change of Foot","FCUSp3","1.5","1","0.5","2.4","1.7","0","-0.3","-0.6","-0.9"},
            {"Flying Upright Level 4 with One Change of Foot","FCUSp4","1.5","1","0.5","2.9","2","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level B with One Change of Foot","FCLSpB","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level 1 with One Change of Foot","FCLSp1","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level 2 with One Change of Foot","FCLSp2","1.5","1","0.5","2.4","1.7","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level 3 with One Change of Foot","FCLSp3","1.5","1","0.5","2.9","2","0","-0.3","-0.6","-0.9"},
            {"Flying Layback Level 4 with One Change of Foot","FCLSp4","1.5","1","0.5","3.2","2.2","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level B with One Change of Foot","FCCSpB","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level 1 with One Change of Foot","FCCSp1","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level 2 with One Change of Foot","FCCSp2","1.5","1","0.5","2.3","1.6","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level 3 with One Change of Foot","FCCSp3","1.5","1","0.5","2.8","2","0","-0.3","-0.6","-0.9"},
            {"Flying Camel Level 4 with One Change of Foot","FCCSp4","1.5","1","0.5","3.2","2.2","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level B with One Change of Foot","FCSSpB","1.5","1","0.5","1.6","1.1","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level 1 with One Change of Foot","FCSSp1","1.5","1","0.5","1.9","1.3","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level 2 with One Change of Foot","FCSSp2","1.5","1","0.5","2.3","1.6","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level 3 with One Change of Foot","FCSSp3","1.5","1","0.5","2.6","1.8","0","-0.3","-0.6","-0.9"},
            {"Flying Sit Level 4 with One Change of Foot","FCSSp4","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Flying Level B with Change of Position (2 Positions)","FCoSp2pB","1.5","1","0.5","1.1","1","0","-0.3","-0.6","-0.9"},
            {"Flying Level 1 with Change of Position (2 Positions)","FCoSp2p1","1.5","1","0.5","1.3","1.1","0","-0.3","-0.6","-0.9"},
            {"Flying Level 2 with Change of Position (2 Positions)","FCoSp2p2","1.5","1","0.5","1.5","1.3","0","-0.3","-0.6","-0.9"},
            {"Flying Level 3 with Change of Position (2 Positions)","FCoSp2p3","1.5","1","0.5","1.8","1.5","0","-0.3","-0.6","-0.9"},
            {"Flying Level 4 with Change of Position (2 Positions)","FCoSp2p4","1.5","1","0.5","2.1","1.7","0","-0.3","-0.6","-0.9"},
            {"Flying Level B with Change of Position (3 Positions)","FCoSp3pB","1.5","1","0.5","1.5","1.1","0","-0.3","-0.6","-0.9"},
            {"Flying Level 1 with Change of Position (3 Positions)","FCoSp3p1","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Level 2 with Change of Position (3 Positions)","FCoSp3p2","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Level 3 with Change of Position (3 Positions)","FCoSp3p3","1.5","1","0.5","2.5","1.8","0","-0.3","-0.6","-0.9"},
            {"Flying Level 4 with Change of Position (3 Positions)","FCoSp3p4","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Flying Level B with Change of Foot and Change of Position (2 Positions)","FCCoSp2pB","1.5","1","0.5","1.5","1.1","0","-0.3","-0.6","-0.9"},
            {"Flying Level 1 with Change of Foot and Change of Position (2 Positions)","FCCoSp2p1","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Level 2 with Change of Foot and Change of Position (2 Positions)","FCCoSp2p2","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Level 3 with Change of Foot and Change of Position (2 Positions)","FCCoSp2p3","1.5","1","0.5","2.5","1.8","0","-0.3","-0.6","-0.9"},
            {"Flying Level 4 with Change of Foot and Change of Position (2 Positions)","FCCoSp2p4","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Flying Level B with Change of Foot and Change of Position (3 Positions)","FCCoSp3pB","1.5","1","0.5","1.7","1.2","0","-0.3","-0.6","-0.9"},
            {"Flying Level 1 with Change of Foot and Change of Position (3 Positions)","FCCoSp3p1","1.5","1","0.5","2","1.4","0","-0.3","-0.6","-0.9"},
            {"Flying Level 2 with Change of Foot and Change of Position (3 Positions)","FCCoSp3p2","1.5","1","0.5","2.5","1.8","0","-0.3","-0.6","-0.9"},
            {"Flying Level 3 with Change of Foot and Change of Position (3 Positions)","FCCoSp3p3","1.5","1","0.5","3","2.1","0","-0.3","-0.6","-0.9"},
            {"Flying Level 4 with Change of Foot and Change of Position (3 Positions)","FCCoSp3p4","1.5","1","0.5","3.5","2.5","0","-0.3","-0.6","-0.9"},
            {"Step Sequence Level B","StSqB","1.5","1","0.5","1.5","0","0","-0.3","-0.6","-0.9"},
            {"Step Sequence Level 1","StSq1","1.5","1","0.5","1.8","0","0","-0.3","-0.6","-0.9"},
            {"Step Sequence Level 2","StSq2","1.5","1","0.5","2.6","0","0","-0.5","-1","-1.5"},
            {"Step Sequence Level 3","StSq3","1.5","1","0.5","3.3","0","0","-0.7","-1.4","-2.1"},
            {"Step Sequence Level 4","StSq4","2.1","1.4","0.7","3.9","0","0","-0.7","-1.4","-2.1"},
            {"Choreo Sequence","ChSq","2.1","1.4","0.7","2","0","0","-0.5","-1","-1.5"},
            {"Group 1 Level B","1LiB","0.9","0.6","0.3","1","0","0","-0.3","-0.6","-0.9"},
            {"Group 1 Level 1","1Li1","0.9","0.6","0.3","1.1","0","0","-0.3","-0.6","-0.9"},
            {"Group 1 Level 2","1Li2","0.9","0.6","0.3","1.3","0","0","-0.3","-0.6","-0.9"},
            {"Group 1 Level 3","1Li3","0.9","0.6","0.3","1.5","0","0","-0.3","-0.6","-0.9"},
            {"Group 1 Level 4","1Li4","0.9","0.6","0.3","1.7","0","0","-0.3","-0.6","-0.9"},
            {"Group 2 Level B","2LiB","0.9","0.6","0.3","1.1","0","0","-0.3","-0.6","-0.9"},
            {"Group 2 Level 1","2Li1","0.9","0.6","0.3","1.6","0","0","-0.3","-0.6","-0.9"},
            {"Group 2 Level 2","2Li2","0.9","0.6","0.3","1.9","0","0","-0.3","-0.6","-0.9"},
            {"Group 2 Level 3","2Li3","0.9","0.6","0.3","2.4","0","0","-0.3","-0.6","-0.9"},
            {"Group 2 Level 4","2Li4","0.9","0.6","0.3","3","0","0","-0.3","-0.6","-0.9"},
            {"Group 3 Level B","3LiB","1.5","1","0.5","2.5","0","0","-0.5","-1","-1.5"},
            {"Group 3 Level 1","3Li1","1.5","1","0.5","3","0","0","-0.5","-1","-1.5"},
            {"Group 3 Level 2","3Li2","1.5","1","0.5","3.5","0","0","-0.5","-1","-1.5"},
            {"Group 3 Level 3","3Li3","1.5","1","0.5","4","0","0","-0.5","-1","-1.5"},
            {"Group 3 Level 4","3Li4","1.5","1","0.5","4.5","0","0","-0.5","-1","-1.5"},
            {"Group 4 Level B","4LiB","1.5","1","0.5","2.5","0","0","-0.5","-1","-1.5"},
            {"Group 4 Level 1","4Li1","1.5","1","0.5","3","0","0","-0.5","-1","-1.5"},
            {"Group 4 Level 2","4Li2","1.5","1","0.5","3.5","0","0","-0.5","-1","-1.5"},
            {"Group 4 Level 3","4Li3","1.5","1","0.5","4","0","0","-0.5","-1","-1.5"},
            {"Group 4 Level 4","4Li4","1.5","1","0.5","4.5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Toe Lasso Level B","5TLiB","1.5","1","0.5","4.5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Toe Lasso Level 1","5TLi1","1.5","1","0.5","5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Toe Lasso Level 2","5TLi2","1.5","1","0.5","5.5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Toe Lasso Level 3","5TLi3","1.5","1","0.5","6","0","0","-0.5","-1","-1.5"},
            {"Group 5 Toe Lasso Level 4","5TLi4","1.5","1","0.5","6.5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Step in Lasso Level B","5SLiB","1.5","1","0.5","4.5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Step in Lasso Level 1","5SLi1","1.5","1","0.5","5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Step in Lasso Level 2","5SLi2","1.5","1","0.5","5.5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Step in Lasso Level 3","5SLi3","1.5","1","0.5","6","0","0","-0.5","-1","-1.5"},
            {"Group 5 Step in Lasso Level 4","5SLi4","1.5","1","0.5","6.5","0","0","-0.5","-1","-1.5"},
            {"Group 5 Axel Lasso Level B","5ALiB","2.1","1.4","0.7","5","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Axel Lasso Level 1","5ALi1","2.1","1.4","0.7","5.5","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Axel Lasso Level 2","5ALi2","2.1","1.4","0.7","6","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Axel Lasso Level 3","5ALi3","2.1","1.4","0.7","6.5","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Axel Lasso Level 4","5ALi4","2.1","1.4","0.7","7","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Backwarde Lasso Level B","5BLiB","2.1","1.4","0.7","5","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Backwarde Lasso Level 1","5BLi1","2.1","1.4","0.7","5.5","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Backwarde Lasso Level 2","5BLi2","2.1","1.4","0.7","6","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Backwarde Lasso Level 3","5BLi3","2.1","1.4","0.7","6.5","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Backwarde Lasso Level 4","5BLi4","2.1","1.4","0.7","7","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Reverse Lasso Level B","5RLiB","2.1","1.4","0.7","5.5","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Reverse Lasso Level 1","5RLi1","2.1","1.4","0.7","6","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Reverse Lasso Level 2","5RLi2","2.1","1.4","0.7","6.5","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Reverse Lasso Level 3","5RLi3","2.1","1.4","0.7","7","0","0","-0.7","-1.4","-2.1"},
            {"Group 5 Reverse Lasso Level 4","5RLi4","2.1","1.4","0.7","7.5","0","0","-0.7","-1.4","-2.1"},
            {"Single Twist Level B","1TwB","0.6","0.4","0.2","0.9","0","0","-0.2","-0.4","-0.6"},
            {"Single Twist Level 1","1Tw1","0.6","0.4","0.2","1.1","0","0","-0.2","-0.4","-0.6"},
            {"Single Twist Level 2","1Tw2","0.6","0.4","0.2","1.3","0","0","-0.2","-0.4","-0.6"},
            {"Single Twist Level 3","1Tw3","0.6","0.4","0.2","1.5","0","0","-0.2","-0.4","-0.6"},
            {"Single Twist Level 4","1Tw4","0.6","0.4","0.2","1.7","0","0","-0.2","-0.4","-0.6"},
            {"Double Twist Level B","2TwB","0.9","0.6","0.3","2.9","0","0","-0.3","-0.6","-0.9"},
            {"Double Twist Level 1","2Tw1","0.9","0.6","0.3","3.1","0","0","-0.3","-0.6","-0.9"},
            {"Double Twist Level 2","2Tw2","0.9","0.6","0.3","3.4","0","0","-0.3","-0.6","-0.9"},
            {"Double Twist Level 3","2Tw3","0.9","0.6","0.3","3.7","0","0","-0.3","-0.6","-0.9"},
            {"Double Twist Level 4","2Tw4","0.9","0.6","0.3","4","0","0","-0.3","-0.6","-0.9"},
            {"Triple Twist Level B","3TwB","2.1","1.4","0.7","5","0","0","-0.7","-1.4","-2.1"},
            {"Triple Twist Level 1","3Tw1","2.1","1.4","0.7","5.4","0","0","-0.7","-1.4","-2.1"},
            {"Triple Twist Level 2","3Tw2","2.1","1.4","0.7","5.8","0","0","-0.7","-1.4","-2.1"},
            {"Triple Twist Level 3","3Tw3","2.1","1.4","0.7","6.2","0","0","-0.7","-1.4","-2.1"},
            {"Triple Twist Level 4","3Tw4","2.1","1.4","0.7","6.6","0","0","-0.7","-1.4","-2.1"},
            {"Quad Twist Level B","4TwB","3","2","1","7","0","0","-1","-2","-3"},
            {"Quad Twist Level 1","4Tw1","3","2","1","7.5","0","0","-1","-2","-3"},
            {"Quad Twist Level 2","4Tw2","3","2","1","8","0","0","-1","-2","-3"},
            {"Quad Twist Level 3","4Tw3","3","2","1","8.6","0","0","-1","-2","-3"},
            {"Quad Twist Level 4","4Tw4","3","2","1","9.1","0","0","-1","-2","-3"},
            {"Thrown Single ToeLoop","1TTh","0.9","0.6","0.3","1.1","0.9","0","-0.3","-0.6","-0.9"},
            {"Thrown Single Salchow","1STh","0.9","0.6","0.3","1.1","0.9","0","-0.3","-0.6","-0.9"},
            {"Thrown Single Loop","1LoTh","0.9","0.6","0.3","1.4","1","0","-0.3","-0.6","-0.9"},
            {"Thrown Single Flip","1FTh","0.9","0.6","0.3","1.4","1","0","-0.3","-0.6","-0.9"},
            {"Thrown SingleLutz","1LzTh","0.9","0.6","0.3","1.4","1","0","-0.3","-0.6","-0.9"},
            {"Thrown Single Axel","1ATh","1.5","1","0.5","2.2","1.5","0","-0.5","-1","-1.5"},
            {"Thrown Double Toeloop","2TTh","1.5","1","0.5","2.5","1.8","0","-0.5","-1","-1.5"},
            {"Thrown Double Salchow","2STh","1.5","1","0.5","2.5","1.8","0","-0.5","-1","-1.5"},
            {"Thrown Double Loop","2LoTh","1.5","1","0.5","2.8","2","0","-0.5","-1","-1.5"},
            {"Thrown Double Flip","2FTh","1.5","1","0.5","3","2.1","0","-0.5","-1","-1.5"},
            {"Thrown Double Lutz","2LzTh","1.5","1","0.5","3","2.1","0","-0.5","-1","-1.5"},
            {"Thrown Double Axel","2ATh","2.1","1.4","0.7","4","2.8","0","-0.7","-1.4","-2.1"},
            {"Thrown Triple Toeloop","3TTh","2.1","1.4","0.7","4.5","3.2","0","-0.7","-1.4","-2.1"},
            {"Thrown Triple Salchow","3STh","2.1","1.4","0.7","4.5","3.2","0","-0.7","-1.4","-2.1"},
            {"Thrown Triple Loop","3LoTh","2.1","1.4","0.7","5","3.5","0","-0.7","-1.4","-2.1"},
            {"Thrown Triple Flip","3FTh","2.1","1.4","0.7","5","3.5","0","-0.7","-1.4","-2.1"},
            {"Thrown Triple Lutz","3LzTh","2.1","1.4","0.7","5.5","3.9","0","-0.7","-1.4","-2.1"},
            {"Thrown Triple Axel","3ATh","3","2","1","7.7","5.4","0","-1","-2","-3"},
            {"Thrown Quad Toeloop","4TTh","3","2","1","8.2","5.7","0","-1","-2","-3"},
            {"Thrown Quad Salchow","4STh","3","2","1","8.2","5.7","0","-1","-2","-3"},
            {"Thrown Quad Loop","4LoTh","3","2","1","8.7","6.1","0","-1","-2","-3"},
            {"Thrown Quad Flip","4FTh","3","2","1","8.7","6.1","0","-1","-2","-3"},
            {"Thrown Quad Lutz","4LzTh","3","2","1","9","6.3","0","-1","-2","-3"},
            {"Forward Inside Level B","FiDsB","2.1","1.4","0.7","2.6","0","0","-0.7","-1.4","-2.1"},
            {"Forward Inside Level 1","FiDs1","2.1","1.4","0.7","2.8","0","0","-0.7","-1.4","-2.1"},
            {"Forward Inside Level 2","FiDs2","2.1","1.4","0.7","3","0","0","-0.7","-1.4","-2.1"},
            {"Forward Inside Level 3","FiDs3","2.1","1.4","0.7","3.2","0","0","-0.7","-1.4","-2.1"},
            {"Forward Inside Level 4","FiDs4","2.1","1.4","0.7","3.5","0","0","-0.7","-1.4","-2.1"},
            {"Backward Inside Level B","BiDsB","2.1","1.4","0.7","2.6","0","0","-0.7","-1.4","-2.1"},
            {"Backward Inside Level 1","BiDs1","2.1","1.4","0.7","2.8","0","0","-0.7","-1.4","-2.1"},
            {"Backward Inside Level 2","BiDs2","2.1","1.4","0.7","3","0","0","-0.7","-1.4","-2.1"},
            {"Backward Inside Level 3","BiDs3","2.1","1.4","0.7","3.2","0","0","-0.7","-1.4","-2.1"},
            {"Backward Inside Level 4","BiDs4","2.1","1.4","0.7","3.5","0","0","-0.7","-1.4","-2.1"},
            {"Forward Outside Level B","FoDsB","2.1","1.4","0.7","2.8","0","0","-0.7","-1.4","-2.1"},
            {"Forward Outside Level 1","FoDs1","2.1","1.4","0.7","3","0","0","-0.7","-1.4","-2.1"},
            {"Forward Outside Level 2","FoDs2","2.1","1.4","0.7","3.5","0","0","-0.7","-1.4","-2.1"},
            {"Forward Outside Level 3","FoDs3","2.1","1.4","0.7","4","0","0","-0.7","-1.4","-2.1"},
            {"Forward Outside Level 4","FoDs4","2.1","1.4","0.7","4.5","0","0","-0.7","-1.4","-2.1"},
            {"Backwards Outside Level B","BoDsB","2.1","1.4","0.7","2.8","0","0","-0.7","-1.4","-2.1"},
            {"Backwards Outside Level 1","BoDs1","2.1","1.4","0.7","3","0","0","-0.7","-1.4","-2.1"},
            {"Backwards Outside Level 2","BoDs2","2.1","1.4","0.7","3.5","0","0","-0.7","-1.4","-2.1"},
            {"Backwards Outside Level 3","BoDs3","2.1","1.4","0.7","4","0","0","-0.7","-1.4","-2.1"},
            {"Backwards Outside Level 4","BoDs4","2.1","1.4","0.7","4.5","0","0","-0.7","-1.4","-2.1"},
            {"Pivot Figure","PiF","2.1","1.4","0.7","2.2","0","0","-0.7","-1.4","-2.1"},
            {"Pair Spin Level B","PSpB","1.5","1","0.5","1.7","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Level 1","PSp1","1.5","1","0.5","2","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Level 2","PSp2","1.5","1","0.5","2.5","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Level 3","PSp3","1.5","1","0.5","3","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Level 4","PSp4","1.5","1","0.5","3.5","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (two positions) Level B","PCoSp2pB","1.5","1","0.5","1.8","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (two positions) Level 1","PCoSp2p1","1.5","1","0.5","2.1","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (two positions) Level 2","PCoSp2p2","1.5","1","0.5","2.5","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (two positions) Level 3","PCoSp2p3","1.5","1","0.5","3","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (two positions) Level 4","PcoSp2p4","1.5","1","0.5","3.5","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (three positions) Level B","PCoSp3pB","1.5","1","0.5","2.5","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (three positions) Level 1","PCoSp3p1","1.5","1","0.5","3","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (three positions) Level 2","PCoSp3p2","1.5","1","0.5","3.5","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (three positions) Level 3","PCoSp3p3","1.5","1","0.5","4","0","0","-0.3","-0.6","-0.9"},
            {"Pair Spin Combination (three positions) Level 4","PcoSp3p4","1.5","1","0.5","4.5","0","0","-0.3","-0.6","-0.9"},
        };
    public Integer elementTableRowCount = 274;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_lookup);
        //textViewElementLookup01 = findViewById(R.id.textView2);
        //textViewElementLookupResult = findViewById(R.id.textViewElementResult);
        //textView3 = findViewById(R.id.textView3);
        //edit_elementID = findViewById(R.id.edit_elementID);
        //edit_elementName = findViewById(R.id.edit_elementName);
        //buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewElementDetailName = findViewById(R.id.textViewElementDetailName);
        textViewElementDetailBaseValue = findViewById(R.id.elementDetailBaseValue);
        rgJump = findViewById(R.id.radioGroupJumps);
        rgRevs = findViewById(R.id.radioGroupRevs);
        //elementTableRowCount = getResources().getInteger(R.integer.elementTableRowCount);
        //whenever the activity is started, it reads data from database and stores it into local array list 'elements'
        /*
        final ElementDatabase elementDatabase = Room.databaseBuilder(getApplicationContext(),
                ElementDatabase.class,
                "element_table")
                .allowMainThreadQueries() // take out for final
                .build();
        */

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
                /*
                cursor = elementDatabase.elementDao().findElement(elementCode);
                if (cursor.moveToFirst()) {
                    newElementName = cursor.getString(cursor.getColumnIndex("elementName"));
                } else {
                    newElementName = "Not Found";
                }
                */
            }
        });

        /*
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
        */
    }
        public void updateElement() {
            elementCode = revCode + jumpCode;
            //textViewElementLookup01.setText(elementCode);
            i = 0;
            while(i < elementTableRowCount){
                if(elementTable[i][1].equals(elementCode)){
                    newElementName = elementTable[i][0];
                    textViewElementDetailName.setText(newElementName);
                    newElementBaseValue = elementTable[i][5];
                    textViewElementDetailBaseValue.setText(newElementBaseValue);
                    //i = elementTableRowCount;
                    //j=i;
                }
                i = i + 1;
            }
            //textViewElementLookup01.setText(String.valueOf(i));
            //textView3.setText(String.valueOf(j));

            //textViewElementLookup01.setText(String.valueOf(i));
           /*
            cursor = elementDatabase.elementDao().findElement(elementCode);
            if (cursor.moveToFirst()) {
                newElementName = "True";
                //newElementName = cursor.getString(cursor.getColumnIndex("elementName"));
            } else {
                newElementName = "Not Found";
            }
            */
            //textViewElementLookupResult.setText(newElementName);
        }


}
