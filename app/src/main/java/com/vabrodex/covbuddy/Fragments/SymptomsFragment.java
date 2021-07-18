package com.vabrodex.covbuddy.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vabrodex.covbuddy.R;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class SymptomsFragment extends Fragment {

    TextView a,b,c,d,e,f,g;
    TextView title;
    View view;
    Button x,y;

    public SymptomsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_symptoms, container, false);

        initialize();
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.setTextColor(getResources().getColor(R.color.black));
                y.setTextColor(getResources().getColor(R.color.gray));
                a.setText(getResources().getString(R.string.dryCough));
                b.setText(getResources().getString(R.string.fever_tiredness));
                c.setText(getResources().getString(R.string.aches_and_pains));
                d.setText(getResources().getString(R.string.runny_nose));
                e.setText(getResources().getString(R.string.sore_throat_or_diarrhea));
                f.setText(getResources().getString(R.string.breathlessness));
                g.setText(getResources().getString(R.string.headache));
                title.setText(getResources().getString(R.string.covidsym));

            }
        });
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.setTextColor(getResources().getColor(R.color.gray));
                y.setTextColor(getResources().getColor(R.color.black));
                a.setText(getResources().getString(R.string.dryCoughhindi));
                b.setText(getResources().getString(R.string.feverhindi));
                c.setText(getResources().getString(R.string.acheshindi));
                d.setText(getResources().getString(R.string.runnynosehindi));
                e.setText(getResources().getString(R.string.sorethroathindi));
                f.setText(getResources().getString(R.string.breathlessnesshindi));
                g.setText(getResources().getString(R.string.headachehindi));
                title.setText(getResources().getString(R.string.covidsymhindi));
            }
        });

        return view;
    }

    private void initialize() {
        a=view.findViewById(R.id.drycough);
        b=view.findViewById(R.id.fever);
        c=view.findViewById(R.id.achesandpains);
        d=view.findViewById(R.id.runnynose);
        e=view.findViewById(R.id.sorethroat);
        f=view.findViewById(R.id.breathlessness);
        g=view.findViewById(R.id.headache);

        x=view.findViewById(R.id.symptomenglish);
        y=view.findViewById(R.id.symptomhindi);
        title=view.findViewById(R.id.symptomtitle);

    }
}