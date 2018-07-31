package com.example.khubbart.mysbusaappv3.Model;

public class ElementInfo {

    private String ElementCode;
    private String ElementName;
    private Double ElementBaseValue;

    //Required No Argument Constructor
    ElementInfo(){
        this.ElementCode = " ";
        this.ElementName = " ";
        this.ElementBaseValue = 0.0;
    }

    public ElementInfo(String elementCode, String elementName, Double elementBaseValue){
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

    public Double getElementBaseValue() {
        return ElementBaseValue;
    }

    public void setElementBaseValue(Double elementBaseValue) {
        ElementBaseValue = elementBaseValue;
    }
}
