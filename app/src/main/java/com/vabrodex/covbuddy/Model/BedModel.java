package com.vabrodex.covbuddy.Model;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class BedModel {
    private String stateName;
    private String beds;

    public BedModel(){

    }

    public BedModel(String stateName, String beds) {
        this.stateName = stateName;
        this.beds = beds;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }
}
