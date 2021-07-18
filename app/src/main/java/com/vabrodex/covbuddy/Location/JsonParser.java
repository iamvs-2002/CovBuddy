package com.vabrodex.covbuddy.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class JsonParser {
    private HashMap<String,String> parseJsonObj(JSONObject jsonObject){
        HashMap<String,String> data = new HashMap<>();
        try{
            String name = jsonObject.getString("name");
            String latitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat");
            String longitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng");

            data.put("name",name);
            data.put("lat",latitude);
            data.put("lng",longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray){
        List<HashMap<String,String>> dataList = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                HashMap<String,String> data = parseJsonObj((JSONObject) jsonArray.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return dataList;
    }

    public List<HashMap<String,String>> parseOutput(JSONObject jsonObject){
        JSONArray jsonArray = null;

        try {
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parseJsonArray(jsonArray);
    }
}