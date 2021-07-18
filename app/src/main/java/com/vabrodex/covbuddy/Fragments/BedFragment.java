package com.vabrodex.covbuddy.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vabrodex.covbuddy.Adapter.BedAdapter;
import com.vabrodex.covbuddy.Model.BedModel;
import com.vabrodex.covbuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class BedFragment extends Fragment {

    TextView tb,th;
    View view;
    private ArrayList<BedModel> arrayList;
    RecyclerView recyclerView;
    private HashMap<String,String> map;

    public BedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bed, container, false);
        intialize();
        //getDataAPI();
        adapterset();
        //getStateDataAPI();
        fillArray();

        return view;
    }

    private void fillArray() {

        arrayList = new ArrayList<>();

        map = new HashMap<>();
        map.put("Delhi","https://coronabeds.jantasamvad.org/");
        map.put("Bengaluru","https://bbmpgov.com/chbms");
        map.put("Nashik","http://covidcbrs.nmc.gov.in/home/searchHosptial");
        map.put("Pune","https://covidpune.com");
        map.put("Assam","https://covid19.assam.gov.in/ ");
        map.put("Dadra and Nagar Haveli and Daman and Diu","https://covidfacility.dddgov.in/");
        map.put("Navi Mumbai","https://nmmchealthfacilities.com/HospitalInfo/showhospitalist");
        map.put("Kerala","https://covid19jagratha.kerala.nic.in/home/addHospitalDashBoard");
        map.put("Jharkhand","https://www.jharkhand.gov.in/Home/Covid19Dashboard");
        map.put("Gujarat","https://gujcovid19.gujarat.gov.in/");
        map.put("Ladakh","https://covid.ladakh.gov.in/");
        map.put("Himachal Pradesh","http://www.nrhmhp.gov.in/content/covid-health-facilities");
        map.put("Odisha","https://statedashboard.odisha.gov.in/");
        map.put("Meghalaya","https://meghealth.in/MeghCare.html");
        map.put("Puducherry","https://covid19dashboard.py.gov.in/");
        map.put("Punjab","https://punjab.gov.in/");
        map.put("Sikkim","https://www.covid19sikkim.org/");
        map.put("Uttar Pradesh","http://dgmhup.gov.in/EN/covid19bedtrack");
        map.put("Andhra Pradesh","http://dashboard.covid19.ap.gov.in/ims/hospbed_reports/");
        map.put("Haryana","https://coronaharyana.in/");
        map.put("Madhya Pradesh","http://sarthak.nhmmp.gov.in/covid/facility-bed-occupancy-dashboard/");
        map.put("Rajasthan","https://covidinfo.rajasthan.gov.in/COVID19HOSPITALBEDSSTATUSSTATE.aspx");
        map.put("Tamil Nadu","https://stopcorona.tn.gov.in/beds.php");
        map.put("Telangana","http://164.100.112.24/SpringMVC/Hospital_Beds_Statistic_Bulletin_citizen.htm");
        map.put("Uttarakhand","https://covid19.uk.gov.in/bedssummary.aspx");
        map.put("West Bengal","https://excise.wb.gov.in/chms/Portal_Default.aspx");


        for(Map.Entry m : map.entrySet()){
            arrayList.add(new BedModel(String.valueOf(m.getKey()),String.valueOf(m.getValue())));
        }
        adapterset();

    }

    private void adapterset() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BedAdapter bedAdapter = new BedAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(bedAdapter);
    }

    private void getStateDataAPI() {
        String url = "https://api.rootnet.in/covid19-in/hospitals/beds";

        arrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("regional");

                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        arrayList.add(new BedModel(data.getString("state"),data.getString("totalBeds")));
                    }
                    adapterset();


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

    private void getDataAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://api.rootnet.in/covid19-in/hospitals/beds";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("data").getJSONObject("summary");
                    th.setText(jsonObject.getString("totalHospitals"));
                    tb.setText(jsonObject.getString("totalBeds"));
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

        requestQueue.add(stringRequest);
    }

    private void intialize() {
        th = view.findViewById(R.id.totalhospitals);
        tb = view.findViewById(R.id.totalbeds);
        recyclerView = view.findViewById(R.id.recyclerViewbed);

    }
}