package com.vabrodex.covbuddy.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vabrodex.covbuddy.Classes.Preferences3;
import com.vabrodex.covbuddy.Location.JsonParser;
import com.vabrodex.covbuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class HospitalFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    View view;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    double currentLatitude=0;
    double currentLongitude=0;
    Location mylocation;
    SupportMapFragment mapFragment;
    ImageButton oxygen,hospital,ambulance,test;
    Preferences3 preferences;

    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;

    public HospitalFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hospital, container, false);

        oxygen = view.findViewById(R.id.gotogooglemapforoxygen);
        hospital = view.findViewById(R.id.gotogooglemapforhospital);
        ambulance = view.findViewById(R.id.gotogooglemapforambulance);
        test = view.findViewById(R.id.gotogooglemapforcovid);


        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLatitude!=0 && currentLongitude!=0){
                    opengooglemapsforhospital();
                }
            }
        });
        oxygen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLatitude!=0 && currentLongitude!=0){
                    opengooglemapsforoxygen();
                }
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLatitude!=0 && currentLongitude!=0){
                    opengooglemapsfortests();
                }
            }
        });
        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLatitude!=0 && currentLongitude!=0){
                    opengooglemapsforambulance();
                }
            }
        });

        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            setUpGoogleClient();
        }

        BubbleShowCaseBuilder o = new BubbleShowCaseBuilder(getActivity()); //Activity instance
        o.title("Medical Oxygen Suppliers"); //Any title for the bubble view
        o.description("Click this icon to find the nearest medical oxygen suppliers.");
        o.textColor(R.color.black);
        o.backgroundColor(getResources().getColor(R.color.white));
        o.imageResourceId(R.drawable.oxygenmask);
        o.closeActionImageResourceId(R.drawable.cancel);
        o.targetView(oxygen); //View to point out
        //o.show(); //Display the ShowCase

        BubbleShowCaseBuilder t = new BubbleShowCaseBuilder(getActivity()); //Activity instance
        t.title("Covid-19 Test Centers"); //Any title for the bubble view
        t.description("Click this icon to find the nearest covid-19 test centers.");
        t.textColor(R.color.black);
        t.backgroundColor(getResources().getColor(R.color.white));
        t.closeActionImageResourceId(R.drawable.cancel);
        t.imageResourceId(R.drawable.covidtest);
        t.targetView(test); //View to point out
        //t.show(); //Display the ShowCase

        BubbleShowCaseBuilder a = new BubbleShowCaseBuilder(getActivity()); //Activity instance
        a.title("Ambulance Services"); //Any title for the bubble view
        a.description("Click this icon to find the nearest ambulance services.");
        a.textColor(R.color.black);
        a.backgroundColor(getResources().getColor(R.color.white));
        a.imageResourceId(R.drawable.ambulances);
        a.closeActionImageResourceId(R.drawable.cancel);
        a.targetView(ambulance); //View to point out
        //a.show(); //Display the ShowCase

        BubbleShowCaseBuilder h = new BubbleShowCaseBuilder(getActivity()); //Activity instance
        h.title("Hospitals"); //Any title for the bubble view
        h.description("Click this icon to find the nearest hospitals.");
        h.textColor(R.color.black);
        h.closeActionImageResourceId(R.drawable.cancel);
        h.backgroundColor(getResources().getColor(R.color.white));
        h.imageResourceId(R.drawable.doctorbag);
        h.targetView(hospital); //View to point out
        //h.show(); //Display the ShowCase

        preferences = new Preferences3(getActivity());
        if (preferences.isFirstTimeLaunch()){
            preferences.setFirstTimeLaunch(false);
            new BubbleShowCaseSequence()
                    .addShowCase(h)
                    .addShowCase(o)
                    .addShowCase(t)
                    .addShowCase(a)
                    .show();
        }

        return view;
    }

    private void opengooglemapsforhospital() {
        Uri gmmIntentUri = Uri.parse(String.format(Locale.ENGLISH,"geo:%f,%f?q=hospitals+near+me", currentLatitude, currentLongitude));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
        else {
            Toast.makeText(getActivity(), "Kindly install Google Maps", Toast.LENGTH_SHORT).show();
        }

        /*String uri = "geo:"+ currentLatitude + "," + currentLongitude +"?q=hospitals+near+me";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));*/
    }
    private void opengooglemapsforambulance() {
        Uri gmmIntentUri = Uri.parse(String.format(Locale.ENGLISH,"geo:%f,%f?q=ambulance+services+near+me", currentLatitude, currentLongitude));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
        else {
            Toast.makeText(getActivity(), "Kindly install Google Maps", Toast.LENGTH_SHORT).show();
        }

        /*String uri = "geo:"+ currentLatitude + "," + currentLongitude +"?q=hospitals+near+me";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));*/
    }
    private void opengooglemapsforoxygen() {
        Uri gmmIntentUri = Uri.parse(String.format(Locale.ENGLISH,"geo:%f,%f?q=medical+oxygen+near+me", currentLatitude, currentLongitude));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
        else {
            Toast.makeText(getActivity(), "Kindly install Google Maps", Toast.LENGTH_SHORT).show();
        }

        /*String uri = "geo:"+ currentLatitude + "," + currentLongitude +"?q=hospitals+near+me";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));*/
    }
    private void opengooglemapsfortests() {
        Uri gmmIntentUri = Uri.parse(String.format(Locale.ENGLISH,"geo:%f,%f?q=covid+test+centers+near+me", currentLatitude, currentLongitude));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
        else {
            Toast.makeText(getActivity(), "Kindly install Google Maps", Toast.LENGTH_SHORT).show();
        }

        /*String uri = "geo:"+ currentLatitude + "," + currentLongitude +"?q=hospitals+near+me";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));*/
    }


    private void setUpGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(),0,this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        /*

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                LatLng latLng = marker.getPosition();
                Toast.makeText(getActivity(), "Showing the best route to "+marker.getTitle(), Toast.LENGTH_SHORT).show();

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latLng.latitude, latLng.longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getActivity().startActivity(intent);
                return true;
            }
        });*/

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                /*BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pinh);
//
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title("Destination");
//                markerOptions.icon(icon);
//                mMap.addMarker(markerOptions);
//
//                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                    @Override
//                    public boolean onMarkerClick(Marker marker) {
//
//                        return true;
//                    }
//                });*/
//
//                LatLng t = latLng;
//                String url = "https://www.google.com/maps/dir/?api=1&destination=" + t.latitude + "," + t.longitude + "&travelmode=driving";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermissions();
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermission  = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);

            if (!listPermission.isEmpty()){
                ActivityCompat.requestPermissions(getActivity(),
                        listPermission.toArray(new String[listPermission.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            }

        }
        else {
            getMyLocation();
            //opengooglemaps();
            //getNearbyHealthServices();
            //getNearbyCenters();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED){
            getMyLocation();
            //opengooglemaps();
            //getNearbyHealthServices();
            //getNearbyCenters();

        }
        else {
            checkPermissions();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Kindly check your internet connection", Toast.LENGTH_SHORT).show();
        checkPermissions();
    }

    @Override
    public void onLocationChanged(Location location) {

        mMap.clear();

        mylocation = location;
        if (mylocation!=null){
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pin);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude,currentLongitude),16.0f));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(currentLatitude,currentLongitude));
            markerOptions.title("Me");
            markerOptions.visible(true);
            markerOptions.icon(icon);

            mMap.addMarker(markerOptions);
            //opengooglemaps();
            //getNearbyHealthServices();
            //getNearbyCenters();

        }

    }

    String accesstoken = "";
    String tokentype = "";
    private void getNearbyHealthServices() {


        String clientid = "xxxx";
        String clientsecret = "xxxx";

        String url = "https://outpost.mapmyindia.com/api/security/oauth/token?grant_type=client_credentials&client_id="+clientid+"&client_secret="+clientsecret;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject respObj = new JSONObject(response);
                            accesstoken = respObj.getString("access_token");
                            tokentype = respObj.getString("token_type");
                            Toast.makeText(getActivity(), accesstoken, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: Please try again", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accept", "application/json");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);


        //now getting the places
        if (tokentype!=null && accesstoken!=null)
            getData();
        else
            Toast.makeText(getActivity(), "Empty", Toast.LENGTH_SHORT).show();
    }

    private void getData() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "https://atlas.mapmyindia.com/api/places/nearby/json?keywords=Hospital&refLocation="+String.valueOf(currentLatitude)+","+String.valueOf(currentLongitude)+"&sortBy=dist:asc&page=1&region=IND&radius=3000";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("suggestedLocations");

                    Toast.makeText(getActivity(), jsonArray.length(), Toast.LENGTH_SHORT).show();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        String latitude = data.getString("latitude");
                        String longitude = data.getString("longitude");

                        Log.i("Location1",latitude+" "+longitude);

                        String name = data.getString("placeName");
                        String address = jsonObject.getString("placeAddress");
                        String mobile = jsonObject.getString("mobileNo");


                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pinh);

                        LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(name);
                        markerOptions.snippet(address+", "+mobile);
                        markerOptions.icon(icon);

                        mMap.addMarker(markerOptions);
                    }

                    Toast.makeText(getActivity(), jsonArray.length(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: Please try again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accept", "application/json");
                params.put("Authorization", tokentype+" "+accesstoken);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void getMyLocation(){
        if(mGoogleApiClient!=null) {
            if (mGoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(10*60*1000);
                    locationRequest.setFastestInterval(5*60*1000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(mGoogleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(getActivity(),
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {


                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(mGoogleApiClient);


                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(getActivity(),
                                                REQUEST_CHECK_SETTINGS_GPS);


                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }


                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });

                }
            }
        }
    }

    private void getNearbyHospitals() {


        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
                "location="+String.valueOf(currentLatitude)+","+String.valueOf(currentLongitude)+
                "&radius=1000"+
                "&type=hospital"+
                "&key="+"AIzaSyALN4V99U0iNrZeMWf37MjCAgA1fV5WoKs";

        Log.e("URL",url);

        new PlaceTask().execute(url);//
        /*
        Log.e("     URL         ",url);

        Object[] dataTransfer = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);*/
    }

    private void getNearbyCenters() {

        String url = "https://api.sit.co-vin.in/api/v2/appointment/centers/findByLatLong?lat="+String.valueOf(currentLatitude)+"&long="+String.valueOf(currentLongitude);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("centers");

                    if (jsonArray.length() == 0) {
                        Toast.makeText(getActivity(), "No centers available near you", Toast.LENGTH_SHORT).show();
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject data = jsonArray.getJSONObject(i);

                        String latitude = data.getString("lat");
                        String longitude = data.getString("long");
                        String centername = data.getString("name");
                        String district = data.getString("district_name");
                        String state = data.getString("state_name");
                        String pin = data.getString("pincode");

                        String from = data.getString("from");
                        String to = data.getString("to");

                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pinh);

                        LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(centername+", "+district+", "+state+", "+pin);
                        markerOptions.snippet("Timings: "+from+" - "+to);
                        markerOptions.icon(icon);

                        mMap.addMarker(markerOptions);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;

            try {
                data = getUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String getUrl(String string) throws IOException {
        URL url = new URL(string);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return  data;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();

            List<HashMap<String,String>> hashMapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                hashMapList = jsonParser.parseOutput(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return hashMapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //mMap.clear();

            for (int i=0; i<hashMaps.size(); i++){

                HashMap<String,String> hashMapList = hashMaps.get(i);

                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");

                LatLng latLng = new LatLng(lat,lng);

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pinh);

                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                options.icon(icon);
                mMap.addMarker(options);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }
}