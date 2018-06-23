package com.example.khubbart.mysbusaappv3.Model;

public class ElementInfo {

    private String ElementCode;
    private String ElementName;
    private String ElementBaseValue;

    //Required No Argument Constructor
    ElementInfo(){
        this.ElementCode = " ";
        this.ElementName = " ";
        this.ElementBaseValue = " ";
    }

    public ElementInfo(String elementCode, String elementName, String elementBaseValue){
        this.ElementCode = elementCode;
        this.ElementName = elementName;
        this.ElementBaseValue = elementBaseValue;
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

    public String getElementBaseValue() {
        return ElementBaseValue;
    }

    public void setElementBaseValue(String elementBaseValue) {
        ElementBaseValue = elementBaseValue;
    }
}
