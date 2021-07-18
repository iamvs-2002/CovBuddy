package com.vabrodex.covbuddy.Model;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class HelplineModel {
    private String stateName;
    private String helplinenum;

    public HelplineModel(String stateName, String helplinenum) {
        this.stateName = stateName;
        this.helplinenum = helplinenum;
    }

    public HelplineModel() {
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getHelplinenum() {
        return helplinenum;
    }

    public void setHelplinenum(String helplinenum) {
        this.helplinenum = helplinenum;
    }
}
