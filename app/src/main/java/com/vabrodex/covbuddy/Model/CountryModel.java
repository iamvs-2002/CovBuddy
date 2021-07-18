package com.vabrodex.covbuddy.Model;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class CountryModel {
    private String countryName;
    private String countryCase;

    public CountryModel(){

    }

    public CountryModel(String countryName, String countryCase) {
        this.countryName = countryName;
        this.countryCase = countryCase;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCase() {
        return countryCase;
    }

    public void setCountryCase(String countryCase) {
        this.countryCase = countryCase;
    }
}
