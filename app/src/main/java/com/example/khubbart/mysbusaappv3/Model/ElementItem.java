package com.example.khubbart.mysbusaappv3.Model;

import java.util.function.BinaryOperator;

public class ElementItem {
    public String elementID;
    public boolean ticToggle;
    public Double elementBase;
    public boolean bonusToggle;
    public Double elementGOEbar;
    public Double elementGOE;
    public Double elementScore;

    //Required No Argument Constructor
    ElementItem(){
        this.elementID = "";
        this.ticToggle = false;
        this.elementBase = 0.0;
        this.bonusToggle = false;
        this.elementGOEbar = 0.0;
        this.elementGOE = 0.0;
        this.elementScore = 0.0;
    }

    public ElementItem(String elementID, boolean ticToggle, Double elementBase, boolean bonusToggle, Double elementGOEbar, Double elementGOE, Double elementScore) {
        this.elementID = elementID;
        this.ticToggle = ticToggle;
        this.elementBase = elementBase;
        this.bonusToggle = bonusToggle;
        this.elementGOEbar = elementGOEbar;
        this.elementGOE = elementGOE;
        this.elementScore = elementScore;
    }

    public String getElementID() {
        return elementID;
    }

    public void setElementID(String elementID) {
        this.elementID = elementID;
    }

    public boolean isTicToggle() {
        return ticToggle;
    }

    public void setTicToggle(boolean ticToggle) {
        this.ticToggle = ticToggle;
    }

    public Double getElementBase() {
        return elementBase;
    }

    public void setElementBase(Double elementBase) {
        this.elementBase = elementBase;
    }

    public boolean isBonusToggle() {
        return bonusToggle;
    }

    public void setBonusToggle(boolean bonusToggle) {
        this.bonusToggle = bonusToggle;
    }

    public Double getElementGOEbar() {
        return elementGOEbar;
    }

    public void setElementGOEbar(Double elementGOEbar) {
        this.elementGOEbar = elementGOEbar;
    }

    public Double getElementGOE() {
        return elementGOE;
    }

    public void setElementGOE(Double elementGOE) {
        this.elementGOE = elementGOE;
    }

    public Double getElementScore() {
        return elementScore;
    }

    public void setElementScore(Double elementScore) {
        this.elementScore = elementScore;
    }
}
