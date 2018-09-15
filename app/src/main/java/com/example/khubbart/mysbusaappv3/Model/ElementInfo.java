package com.example.khubbart.mysbusaappv3.Model;

public class ElementInfo {

    private String RawElementCode; //What is read from database
    private int ElementSOVIndex;
    private String ElementCode;
    private String ElementName;
    private Double ElementBaseValue;

    //Required No Argument Constructor
    public ElementInfo(){
        this.RawElementCode = " ";
        this.ElementSOVIndex = 0;
        this.ElementCode = " ";
        this.ElementName = " ";
        this.ElementBaseValue = 0.0;
    }

    public ElementInfo(String rawElementCode, int elementSOVIndex, String elementCode, String elementName, Double elementBaseValue){
        this.RawElementCode = rawElementCode;
        this.ElementSOVIndex = elementSOVIndex;
        this.ElementCode = elementCode;
        this.ElementName = elementName;
        this.ElementBaseValue = elementBaseValue;
    }

    public String getRawElementCode() {
        return RawElementCode;
    }

    public void setRawElementCode(String rawElementCode) {
        RawElementCode = rawElementCode;
    }

    public int getElementSOVIndex() {
        return ElementSOVIndex;
    }

    public void setElementSOVIndex(int elementSOVIndex) {
        ElementSOVIndex = elementSOVIndex;
    }

    public String getElementCode() {
        return ElementCode;
    }

    public void setElementCode(String elementCode) {
        ElementCode = elementCode;
    }

    public String getElementName() {
        return ElementName;
    }

    public void setElementName(String elementName) {
        ElementName = elementName;
    }

    public Double getElementBaseValue() {
        return ElementBaseValue;
    }

    public void setElementBaseValue(Double elementBaseValue) {
        ElementBaseValue = elementBaseValue;
    }
}
