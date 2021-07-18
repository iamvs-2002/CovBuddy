package com.vabrodex.covbuddy.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vabrodex.covbuddy.Adapter.CountryAdapter;
import com.vabrodex.covbuddy.Model.CountryModel;
import com.vabrodex.covbuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class CountryFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<CountryModel> arrayList;

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_country, container, false);

        initialize();
        setadapter();
        getCountryDataAPI();
        return view;
    }

    private void setadapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CountryAdapter countryAdapter = new CountryAdapter(arrayList);
        recyclerView.setAdapter(countryAdapter);
    }

    private void getCountryDataAPI() {
        String url = "https://akashraj.tech/corona/api";

        arrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("countries_stat");

                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        arrayList.add(new CountryModel(data.getString("country_name"),data.getString("cases")));
                    }
                    setadapter();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: Please try again", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void initialize() {
        recyclerView = view.findViewById(R.id.recyclerViewCountry);
    }
}