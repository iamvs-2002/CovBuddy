package com.vabrodex.covbuddy.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vabrodex.covbuddy.Adapter.StateAdapter;
import com.vabrodex.covbuddy.Model.StateModel;
import com.vabrodex.covbuddy.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class IndiaFragment extends Fragment {

    TextView tc,td,tr,lastupdate;
    View view;
    int c=0;
    int d=0;
    int r=0;
    private ArrayList<StateModel> arrayList;
    RecyclerView recyclerView;

    PieChart pieChart;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_india, container, false);
        intialize();
        getDataAPI();
        adapterset();
        getStateDataAPI();

        setPieData();

        return view;
    }

    private void setPieData() {

        pieChart.addPieSlice(
                new PieModel(
                        "Total Cases",
                        Integer.parseInt(tc.getText().toString()),
                        Color.parseColor("#FF000000")));
        pieChart.addPieSlice(
                new PieModel(
                        "Total Deaths",
                        Integer.parseInt(td.getText().toString()),
                        Color.parseColor("#8B0000")));
        pieChart.addPieSlice(
                new PieModel(
                        "Total Recovered",
                        Integer.parseInt(tr.getText().toString()),
                        Color.parseColor("#008000")));

        pieChart.animate();
        pieChart.update();

        pieChart.startAnimation();

    }

    private void adapterset() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StateAdapter stateAdapter = new StateAdapter(arrayList);
        recyclerView.setAdapter(stateAdapter);
    }

    private void getStateDataAPI() {
        String url = "https://api.rootnet.in/covid19-in/stats/latest";

        arrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("regional");

                    for(int i = 0; i<jsonArray.length()-1;i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        arrayList.add(new StateModel(data.getString("loc"),data.getString("totalConfirmed")));
                    }
                    adapterset();


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

    private void getDataAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://api.rootnet.in/covid19-in/stats/latest";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("data").getJSONObject("summary");
                    tc.setText(jsonObject.getString("total"));
                    c = Integer.parseInt(jsonObject.getString("total"));
                    td.setText(jsonObject.getString("deaths"));
                    d = Integer.parseInt(jsonObject.getString("deaths"));
                    tr.setText(jsonObject.getString("discharged"));
                    r = Integer.parseInt(jsonObject.getString("discharged"));

                    setPieData();

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

    private void intialize() {
        tc = view.findViewById(R.id.totalcasesindia);
        td = view.findViewById(R.id.totaldeathsindia);
        tr = view.findViewById(R.id.totalrecvindia);
        recyclerView = view.findViewById(R.id.recyclerView);
        pieChart = view.findViewById(R.id.piechart);
        linearLayout = view.findViewById(R.id.chartlayout);

    }
}