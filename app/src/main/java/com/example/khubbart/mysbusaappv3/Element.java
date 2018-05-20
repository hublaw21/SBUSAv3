package com.example.khubbart.mysbusaappv3;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "element_table")

public class Element {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "elementID")
    private String elementID;
    @ColumnInfo(name = "elementName")
    private String elementName;

    public Element(String elementID, String elementName) {
        this.elementID = elementID;
        this.elementName = elementName;
    }

    public String getElementID() {return elementID;}
    public  void setElementID(String elementID) { this.elementID = elementID;}
    public String getElementName() {return elementName;}
    public  void setElementName(String elementName) { this.elementName = elementName;}

    public static Element[] populateElementTable(){
        return new Element[]{
                new Element("1T", "Single Toe"),
                new Element("1S", "Single Salchow"),
                new Element("1F", "Single Flip"),
                new Element("1A", "Single Axel")
        };
    }
}
