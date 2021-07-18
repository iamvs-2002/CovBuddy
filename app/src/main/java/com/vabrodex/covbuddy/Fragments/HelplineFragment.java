package com.vabrodex.covbuddy.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vabrodex.covbuddy.Adapter.HelplineAdapter;
import com.vabrodex.covbuddy.Model.HelplineModel;
import com.vabrodex.covbuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class HelplineFragment extends Fragment {

    View view;
    TextView h;
    private ArrayList<HelplineModel> arrayList;
    RecyclerView recyclerView;
    RelativeLayout indianhelp;

    public HelplineFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_helpline, container, false);

        intialize();
        getDataAPI();
        adapterset();
        getStateDataAPI();

        indianhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";

                s = h.getText().toString();

                if(s.contains(",")){
                    String[] temp = s.split(",");
                    s = temp[0];
                }

                if(s.charAt(0)=='+'){
                    s = getPhoneNumber(s);
                }

                Intent callIntent = new Intent(Intent.ACTION_DIAL);

                callIntent.setData(Uri.parse("tel:"+s));//format of number

                try{
                    v.getContext().startActivity(callIntent);
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "Error: Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private static String getPhoneNumber(String contact) {
        String[] phoneNumber = contact.split("-");
        String phone="";
        for(int i=0;i<phoneNumber.length;i++) {
            phone+=phoneNumber[i];
        }

        phone.replaceAll(" ","");

        return phone;
    }

    private void adapterset() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HelplineAdapter helplineAdapter = new HelplineAdapter(arrayList);
        recyclerView.setAdapter(helplineAdapter);
    }

    private void getStateDataAPI() {
        String url = "https://api.rootnet.in/covid19-in/contacts";

        arrayList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("contacts").getJSONArray("regional");

                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        arrayList.add(new HelplineModel(data.getString("loc"),data.getString("number")));
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
        String url = "https://api.rootnet.in/covid19-in/contacts";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("data").getJSONObject("contacts").getJSONObject("primary");
                    h.setText(jsonObject.getString("number"));
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
        h = view.findViewById(R.id.indiahelpnum);
        recyclerView = view.findViewById(R.id.recyclerViewhelp);
        indianhelp = view.findViewById(R.id.indiannum);
    }
}