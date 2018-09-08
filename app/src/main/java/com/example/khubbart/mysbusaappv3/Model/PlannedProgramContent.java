package com.example.khubbart.mysbusaappv3.Model;

public class PlannedProgramContent {
    private String ProgramDocumentID;
    private String E00;
    private String E01;
    private String E02;
    private String E03;
    private String E04;
    private String E05;
    private String E06;
    private String E07;
    private String E08;
    private String E09;
    private String E10;
    private String E11;
    private String E12;

// Required No Argument Constructor
     PlannedProgramContent() {
         this.ProgramDocumentID = "";
         this.E00 =  null;
        this.E01 =  null;
        this.E02 =  null;
        this.E03 =  null;
        this.E04 =  null;
        this.E05 =  null;
        this.E06 =  null;
        this.E07 =   null;
         this.E08 =   null;
         this.E09 =  null;
        this.E10 =  null;
        this.E11 =  null;
        this.E12 = null;
    }

    PlannedProgramContent(String programDocumentID, String e00, String e01, String e02, String e03, String e04, String e05,
                          String e06, String e07, String e08, String e09, String e10, String e11, String e12) {
        this.ProgramDocumentID = programDocumentID;
        this.E00 = e00;
        this.E00 = e01;
        this.E00 = e02;
        this.E00 = e03;
        this.E00 = e04;
        this.E00 = e05;
        this.E00 = e06;
        this.E00 = e07;
        this.E00 = e08;
        this.E00 = e09;
        this.E00 = e10;
        this.E00 = e11;
        this.E00 = e12;
     }

    public String getProgramDocumentID() {
        return ProgramDocumentID;
    }

    public void setProgramDocumentID(String programDocumentID) {
        ProgramDocumentID = programDocumentID;
    }

    public String getE00() {return E00;}

    public void setE00(String e00) {E00 = e00;}

    public String getE01() {return E01;}

    public void setE01(String e01) {E01 = e01;}

    public String getE02() {
        return E02;
    }

    public void setE02(String e02) {
        E02 = e02;
    }

    public String getE03() {
        return E03;
    }

    public void setE03(String e03) {
        E03 = e03;
    }

    public String getE04() {
        return E04;
    }

    public void setE04(String e04) {
        E04 = e04;
    }

    public String getE05() {
        return E05;
    }

    public void setE05(String e05) {
        E05 = e05;
    }

    public String getE06() {
        return E06;
    }

    public void setE06(String e06) {
        E06 = e06;
    }

    public String getE07() {
        return E07;
    }

    public void setE07(String e07) {
        E07 = e07;
    }

    public String getE08() {
        return E08;
    }

    public void setE08(String e08) {
        E08 = e08;
    }

    public String getE09() {
        return E09;
    }

    public void setE09(String e09) {
        E09 = e09;
    }

    public String getE10() {
        return E10;
    }

    public void setE10(String e10) {
        E10 = e10;
    }

    public String getE11() {
        return E11;
    }

    public void setE11(String e11) {
        E11 = e11;
    }

    public String getE12() {
        return E12;
    }

    public void setE12(String e12) {
        E12 = e12;
    }
}
