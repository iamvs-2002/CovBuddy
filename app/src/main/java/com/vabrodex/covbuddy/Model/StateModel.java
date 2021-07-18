package com.vabrodex.covbuddy.Model;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class StateModel {
    private String stateName;
    private String stateCase;

    public StateModel(String stateName, String stateCase) {
        this.stateName = stateName;
        this.stateCase = stateCase;
    }

    public StateModel(){

    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCase() {
        return stateCase;
    }

    public void setStateCase(String stateCase) {
        this.stateCase = stateCase;
    }
}
