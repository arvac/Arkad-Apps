package com.example.irir1.myapplication.models;

public class registro {
    private String idc;
    private String corte;
    private String lect;


    public registro(String idc, String corte, String lect) {
        this.idc = idc;
        this.corte = corte;
        this.lect = lect;

    }

    public String getCorte() {
        return corte;
    }

    public String getLect() {
        return lect;
    }

    public String getIdc(){
        return idc;
    }


}
