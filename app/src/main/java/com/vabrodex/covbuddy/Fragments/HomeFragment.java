package com.vabrodex.covbuddy.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.vabrodex.covbuddy.R;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class HomeFragment extends Fragment {

    TextView totalcase,totaldeath,totalrecovered;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initialize();
        getDataApi();

        return view;
    }

    private void getDataApi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://akashraj.tech/corona/api";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("world_total");
                    totalcase.setText(jsonObject.getString("total_cases"));
                    totaldeath.setText(jsonObject.getString("total_deaths"));
                    totalrecovered.setText(jsonObject.getString("total_recovered"));
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
        requestQueue.add(stringRequest);
    }

    private void initialize() {
        totalcase = view.findViewById(R.id.totalcases);
        totaldeath = view.findViewById(R.id.totaldeaths);
        totalrecovered = view.findViewById(R.id.totalrecv);
    }
}