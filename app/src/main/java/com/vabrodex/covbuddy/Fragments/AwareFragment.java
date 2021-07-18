package com.vabrodex.covbuddy.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vabrodex.covbuddy.Adapter.ViewPagerAdapter;
import com.vabrodex.covbuddy.R;

import java.util.Timer;
import java.util.TimerTask;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class AwareFragment extends Fragment {

    View view;
    ViewPager mViewPager;

    String[] anims = {"socialdist.json","washhands.json","wearmask.json","namaste.json","avoidcontact.json"};
    String[] texts = {"1. Maintain Social Distance","2. Wash your hands properly","3. Wear mask","4. Namaste : No shake hands","5. Avoid contact with sick people"};

    ViewPagerAdapter mViewPagerAdapter;


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; // time in milliseconds between successive task executions.

    TextView link1;
    TextView link2;
    ImageView ayush;

    public AwareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aware, container, false);

        link1 = view.findViewById(R.id.ayourvedapreventivemeasures);
        link2 = view.findViewById(R.id.postcovidmanagementprotocol);
        ayush = view.findViewById(R.id.ayushweb);

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.ayush.gov.in/docs/Ayurveda%20Preventive%20Measures%20for%20self%20care%20during%20%20COVID-19%20Pandemic.pdf";
                launchDetailActivity(url);
            }
        });

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.ayush.gov.in/docs/PostCOVID13092020.pdf";
                launchDetailActivity(url);
            }
        });

        ayush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.ayush.gov.in/";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        mViewPager = (ViewPager)view.findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPagerAdapter(getActivity(), anims,texts);
        mViewPager.setAdapter(mViewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == anims.length) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        return view;
    }

    private void launchDetailActivity(String url) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setPackage("com.google.android.youtube");
        startActivity(intent);

    }
}