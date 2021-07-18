package com.vabrodex.covbuddy.Model;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class VaccineModel {
    private String name;
    private String pincode;
    private String center_id;
    private String fee_type;
    private String available_capacity;
    private String state_name;
    private String from;
    private String district_name;
    private String block_name;
    private String to;
    private String[] slots;
    private String address;
    private String min_age_limit;

    public VaccineModel(){

    }

    public VaccineModel(String name, String pincode, String center_id, String fee_type, String available_capacity, String state_name, String from, String district_name, String block_name, String to, String[] slots, String address, String min_age_limit) {
        this.name = name;
        this.pincode = pincode;
        this.center_id = center_id;
        this.fee_type = fee_type;
        this.available_capacity = available_capacity;
        this.state_name = state_name;
        this.from = from;
        this.district_name = district_name;
        this.block_name = block_name;
        this.to = to;
        this.slots = slots;
        this.address = address;
        this.min_age_limit = min_age_limit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMin_age_limit() {
        return min_age_limit;
    }

    public void setMin_age_limit(String min_age_limit) {
        this.min_age_limit = min_age_limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getAvailable_capacity() {
        return available_capacity;
    }

    public void setAvailable_capacity(String available_capacity) {
        this.available_capacity = available_capacity;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String[] getSlots() {
        return slots;
    }

    public void setSlots(String[] slots) {
        this.slots = slots;
    }
}
