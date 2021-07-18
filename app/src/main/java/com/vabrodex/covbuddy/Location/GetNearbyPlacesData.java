package com.vabrodex.covbuddy.Location;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vabrodex.covbuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

//needs a different soln
public class GetNearbyPlacesData extends AsyncTask<Object,String,String> {
    String googlePlacesData;
    GoogleMap googleMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {
        googleMap = (GoogleMap) objects[0];
        url = (String)objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return all nearby places
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {

        //markers on all the nearby places
        try {
            JSONObject parentObject = new JSONObject(s);
            JSONArray resultArray = parentObject.getJSONArray("results");

            for (int i=0; i<resultArray.length(); i++){
                JSONObject jsonObject = resultArray.getJSONObject(i);
                JSONObject locationObj  = jsonObject.getJSONObject("geometry").getJSONObject("location");

                String latitude = locationObj.getString("lat");
                String longitude = locationObj.getString("lng");

                Log.i("Location1",latitude+" "+longitude);

                String name = jsonObject.getString("name");
                String vicinity = jsonObject.getString("vicinity");

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pinh);

                LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(name+", "+vicinity);
                markerOptions.icon(icon);

                googleMap.addMarker(markerOptions);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
