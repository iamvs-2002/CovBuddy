package com.vabrodex.covbuddy.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vabrodex.covbuddy.Adapter.VaccineAdapter;
import com.vabrodex.covbuddy.Model.VaccineModel;
import com.vabrodex.covbuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class VaccineFragment extends Fragment {

    public VaccineFragment() {
        // Required empty public constructor
    }

    View view;
    EditText date,pin;
    TextView slot;
    Button search;
    String[] vaccines={"COVISHIELD","COVAXIN"};
    Spinner spin;
    String vaccine;
    String datecurrent;
    String pincode;
    private ArrayList<VaccineModel> arrayList;
    RecyclerView recyclerView;
    ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vaccine, container, false);


        initialize();
        adapterset();
        processSpinner();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataAPI();
            }
        });

        return view;
    }

    private void adapterset() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VaccineAdapter vaccineAdapter = new VaccineAdapter(arrayList);
        recyclerView.setAdapter(vaccineAdapter);
    }

    private void processSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,vaccines);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vaccine = vaccines[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initialize() {
        date = view.findViewById(R.id.date);
        pin = view.findViewById(R.id.pincode);
        spin = (Spinner) view.findViewById(R.id.vaccinespinner);
        recyclerView = view.findViewById(R.id.recyclerViewvaccine);
        search = view.findViewById(R.id.searchbtn);
        slot = view.findViewById(R.id.covidslotbook);
        imageView = view.findViewById(R.id.mycovidchatbotimg);

        date.setKeyListener(null);

        datecurrent = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        date.setText(datecurrent);

        slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.cowin.gov.in/home";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send/?phone=919013151515&text=Hi&app_absent=0";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }


    private void getDataAPI() {


        arrayList = new ArrayList<>();

        pincode = pin.getText().toString();
        if (pincode.isEmpty() || vaccine.isEmpty()){
            Toast.makeText(getActivity(), "Kindly provide the PIN code", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pincode.length()!=6){
            Toast.makeText(getActivity(), "Kindly provide the correct PIN code", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pincode+"&date="+datecurrent
                +"&vaccine="+vaccine.toUpperCase();

        //Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("sessions");

                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(getActivity(), "No slots available for "+vaccine.toUpperCase()+" vaccine "+" at PIN Code "+pincode+" on "+datecurrent, Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(getActivity(), "Kindly scroll down to view the search results", Toast.LENGTH_LONG).show();

                    for(int i = 0; i<jsonArray.length();i++){

                        JSONObject data = jsonArray.getJSONObject(i);

                        JSONArray x = data.getJSONArray("slots");
                        List<String> temp = new ArrayList<String>();
                        for(int j=0; j< x.length(); j++){
                            temp.add(x.getString(j));
                        }
                        int size = temp.size();
                        String[] slots = temp.toArray(new String[size]);

                        Log.e(String.valueOf(i),data.getString("name"));

                        arrayList.add(new VaccineModel(
                                data.getString("name"),
                                data.getString("pincode"),
                                data.getString("center_id"),
                                data.getString("fee_type"),
                                data.getString("available_capacity"),
                                data.getString("state_name"),
                                data.getString("from"),
                                data.getString("district_name"),
                                data.getString("block_name"),
                                data.getString("to"),
                                slots,
                                data.getString("address"),
                                data.getString("min_age_limit")
                        ));

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

    @Override
    public void onDetach() {
        super.onDetach();

    }
}