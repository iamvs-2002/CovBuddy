package com.vabrodex.covbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vabrodex.covbuddy.Classes.Preferences2;
import com.vabrodex.covbuddy.Fragments.AwareFragment;
import com.vabrodex.covbuddy.Fragments.BedFragment;
import com.vabrodex.covbuddy.Fragments.CountryFragment;
import com.vabrodex.covbuddy.Fragments.HelplineFragment;
import com.vabrodex.covbuddy.Fragments.HomeFragment;
import com.vabrodex.covbuddy.Fragments.HospitalFragment;
import com.vabrodex.covbuddy.Fragments.IndiaFragment;
import com.vabrodex.covbuddy.Fragments.NewsFragment;
import com.vabrodex.covbuddy.Fragments.SymptomsFragment;
import com.vabrodex.covbuddy.Fragments.VaccineFragment;

import hotchemi.android.rate.AppRate;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class MainActivity extends AppCompatActivity {

    TextView t1,t2,t3;
    private Preferences2 preferences;
    String urlforapp;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    NestedScrollView nestedScrollView;
    PopupWindow popupWindow;
    TextView privacypolicy;
    String privacypolicyurl;
    boolean connected;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AppRate.with(this)
//                // default 10
//                .setInstallDays(2)
//                // default 10
//                .setLaunchTimes(10)
//                // default 1
//                .setRemindInterval(3)
//                .monitor();
//        // Show a dialogue
//        // if meets conditions
//        AppRate.showRateDialogIfMeetsConditions(this);

        //checkAirplaneMode();

        connected = checkconnection();

        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setItemIconTintList(null);


        if(connected){

            initialize();
            toolbarSetup();
            bottomNavMenuSetup();
            preferences = new Preferences2(this);
            if (preferences.isFirstTimeLaunch()){
                preferences.setFirstTimeLaunch(false);
                launchVideoActivity();

                BubbleShowCaseBuilder o = new BubbleShowCaseBuilder(this); //Activity instance
                o.description("Click this icon to explore more services offered.");
                o.backgroundColor(getResources().getColor(R.color.white));
                o.textColor(R.color.black);
                //o.arrowPosition(BubbleShowCase.ArrowPosition.LEFT);
                o.closeActionImageResourceId(R.drawable.cancel);
                o.imageResourceId(R.drawable.menu);
                o.targetView(toolbar); //View to point out
                o.show(); //Display the ShowCase
            }

            privacypolicy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PrivacyPolicy");

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            privacypolicyurl = snapshot.getValue(String.class);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacypolicyurl));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Error: Please try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        else{
            Toast.makeText(this, "Kindly connect to the internet and restart the application", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkconnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    private void checkAirplaneMode() {
        if (isAirplaneModeOn(getApplicationContext())) {
            Toast.makeText(this, "Kindly disable the airplane mode", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    private void bottomNavMenuSetup() {
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        t1.setText("Worldwide");
                        t1.setTextSize(20);
                        t1.setVisibility(View.GONE);
                        t2.setText("Worldwide Summary");
                        t2.setTextSize(24);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        t2.setVisibility(View.VISIBLE);
                        t3.setVisibility(View.VISIBLE);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t3.setText("COVID-19 LIVE UPDATE");
                        t3.setTextSize(14);

                        t3.setTextColor(getResources().getColor(R.color.green));
                        Fragment homeFragment = new HomeFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction.replace(R.id.container,homeFragment).commit();
                        break;
                    case R.id.india:
                        t1.setVisibility(View.GONE);
                        t2.setText("India");
                        t2.setTextColor(getResources().getColor(R.color.black));
                        t2.setTextSize(24);
                        t2.setVisibility(View.VISIBLE);
                        t3.setVisibility(View.VISIBLE);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t3.setText("COVID-19 INDIA LIVE UPDATE");
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        //Toast.makeText(MainActivity.this, "India", Toast.LENGTH_SHORT).show();
                        Fragment indiafrag = new IndiaFragment();
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction2.replace(R.id.container,indiafrag).commit();
                        break;
                    case R.id.worldwide:
                        t2.setText("Worldwide");
                        t1.setVisibility(View.GONE);
                        t1.setTextSize(20);
                        t2.setTextSize(24);
                        t2.setVisibility(View.VISIBLE);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        t3.setVisibility(View.VISIBLE);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t3.setText("COVID-19 LIVE UPDATE");
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        //Toast.makeText(MainActivity.this, "Worldwide", Toast.LENGTH_SHORT).show();
                        Fragment worldfrag = new CountryFragment();
                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        fragmentTransaction3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction3.replace(R.id.container,worldfrag).commit();
                        break;
                    case R.id.symptioms:
                        t1.setVisibility(View.GONE);
                        t3.setVisibility(View.VISIBLE);
                        t3.setText("CORONAVIRUS SYMPTOMS");
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        t2.setText("Symptoms");
                        t2.setVisibility(View.VISIBLE);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t2.setTextSize(24);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        //Toast.makeText(MainActivity.this, "Symptoms", Toast.LENGTH_SHORT).show();
                        Fragment symptfrag = new SymptomsFragment();
                        FragmentManager fragmentManager4 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                        fragmentTransaction4.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction4.replace(R.id.container,symptfrag).commit();
                        break;
                }
                return false;
            }
        });
    }

    private void toolbarSetup() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeid:
                        t1.setText("Worldwide");
                        t1.setTextSize(20);
                        t1.setVisibility(View.GONE);
                        t2.setText("Worldwide Summary");
                        t2.setTextSize(24);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        t2.setVisibility(View.VISIBLE);
                        t3.setVisibility(View.VISIBLE);
                        t3.setText("COVID-19 LIVE UPDATE");
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        Fragment homeFragment = new HomeFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction.replace(R.id.container,homeFragment).addToBackStack(null).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.howtouse:
                        launchVideoActivity();
                        break;
                    case R.id.news:
                        t1.setText("Health");
                        t1.setTextSize(20);
                        t1.setVisibility(View.GONE);
                        t2.setText("News");
                        t2.setTextSize(24);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        t2.setVisibility(View.VISIBLE);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t3.setVisibility(View.VISIBLE);
                        t3.setText("INDIA LIVE UPDATE");
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        Fragment newsFragment = new NewsFragment();
                        FragmentManager fragmentManagern = getSupportFragmentManager();
                        FragmentTransaction fragmentTransactionn = fragmentManagern.beginTransaction();
                        fragmentTransactionn.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransactionn.replace(R.id.container,newsFragment).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.hospital:
                        t1.setVisibility(View.GONE);
                        t3.setVisibility(View.VISIBLE);
                        t3.setText("#IndiaFightsCorona");
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        t2.setText("Nearest Medical Services");
                        t2.setVisibility(View.VISIBLE);
                        t2.setTextSize(24);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        nestedScrollView.setNestedScrollingEnabled(false);
                        Fragment zoneFragment = new HospitalFragment();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction1.replace(R.id.container,zoneFragment).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.beds:
                        t1.setVisibility(View.GONE);
                        t3.setVisibility(View.VISIBLE);
                        t3.setText("COVID-19 INDIA LIVE UPDATE");
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        t2.setText("Beds Available");
                        t2.setVisibility(View.VISIBLE);
                        t2.setTextSize(24);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        Fragment bedFragment = new BedFragment();
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction2.replace(R.id.container,bedFragment).addToBackStack(null).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.vaccine:
                        t1.setVisibility(View.GONE);
                        t3.setVisibility(View.VISIBLE);
                        t3.setText("COVID-19 INDIA LIVE UPDATE");
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        t2.setText("Vaccination Slot Avalability");
                        t2.setVisibility(View.VISIBLE);
                        t2.setTextSize(22);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        Fragment vaccineFragment = new VaccineFragment();
                        FragmentManager fragmentManagerv = getSupportFragmentManager();
                        FragmentTransaction fragmentTransactionv = fragmentManagerv.beginTransaction();
                        fragmentTransactionv.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransactionv.replace(R.id.container,vaccineFragment).addToBackStack(null).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case R.id.helpline:
                        t1.setVisibility(View.GONE);
                        t3.setVisibility(View.VISIBLE);
                        t3.setText("#IndiaFightsCorona");
                        t3.setTextSize(14);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        t2.setText("Helpline Numbers");
                        t2.setVisibility(View.VISIBLE);
                        t2.setTextSize(24);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        Toast.makeText(MainActivity.this, "Click on the state to dial the helpline number", Toast.LENGTH_SHORT).show();
                        Fragment helplineFragment = new HelplineFragment();
                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        fragmentTransaction3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction3.replace(R.id.container,helplineFragment).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.share:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        shareIt();
                        break;
                    case R.id.aware:
                        t1.setVisibility(View.GONE);
                        t3.setVisibility(View.VISIBLE);
                        t3.setText("#IndiaFightsCorona");
                        t3.setTextSize(14);
                        nestedScrollView.setNestedScrollingEnabled(true);
                        t3.setTextColor(getResources().getColor(R.color.green));
                        t2.setText("Covid-19 Awareness");
                        t2.setVisibility(View.VISIBLE);
                        t2.setTextSize(24);
                        t2.setTextColor(getResources().getColor(R.color.black));
                        Fragment awareFragment = new AwareFragment();
                        FragmentManager fragmentManager4 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                        fragmentTransaction4.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction4.replace(R.id.container,awareFragment).addToBackStack(null).commit();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.about:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        openPopUpWindow();
                        break;
                }

                return false;
            }
        });
    }

    private void launchVideoActivity() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Video");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                urlforapp = snapshot.getValue(String.class);
                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlforapp));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);*/
                /*
                Intent x =(new Intent(MainActivity.this, DetailsActivity.class));
                x.putExtra("url",urlforapp);
                startActivity(x);*/

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlforapp)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: Please try again", Toast.LENGTH_SHORT).show();
            }
        });


    }

    TextView mail;
    TextView indiavideo;

    private void openPopUpWindow() {

        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup,null);

        mail = customView.findViewById(R.id.email);
        indiavideo = customView.findViewById(R.id.indiavideo);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={mail.getText().toString()};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Regarding "+getResources().getString(R.string.app_name)+" app");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail using"));
            }
        });

        indiavideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/mVioWT7uDRQ"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });

        popupWindow = new PopupWindow(customView, DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(drawerLayout, Gravity.CENTER, 0, 0);
        popupWindow.setHeight(700);
        popupWindow.setWidth(200);
        popupWindow.setFocusable(true);
        popupWindow.update();
    }

    String shareurl;
    private void shareIt() {

        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Share");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shareurl = snapshot.getValue(String.class);
                progressBar.setVisibility(View.GONE);
                if(shareurl.isEmpty()){
                    Toast.makeText(MainActivity.this, "Error: Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                ShareCompat.IntentBuilder.from(MainActivity.this)
                        .setType("text/plain")
                        .setChooserTitle("Share via")
                        .setText(shareurl)
                        .startChooser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialize() {
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_menu);
        collapsingToolbarLayout = findViewById(R.id.collLayout);
        navigationView = findViewById(R.id.navBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        nestedScrollView = findViewById(R.id.scrollview);

        privacypolicy = findViewById(R.id.privacypolicy);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (connected){
            t1.setText("Worldwide");
            t1.setTextSize(20);
            t1.setVisibility(View.GONE);
            t2.setText("Worldwide Summary");
            t2.setTextSize(24);
            t2.setTextColor(getResources().getColor(R.color.black));
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            t3.setText("COVID-19 LIVE UPDATE");
            nestedScrollView.setNestedScrollingEnabled(true);
            t3.setTextSize(14);
            t3.setTextColor(getResources().getColor(R.color.green));
            Fragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.container,homeFragment).commit();
        }
    }

    private long pressedTime;

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();

        /*Fragment homeFragment = new HomeFragment();

        if(homeFragment!=null && homeFragment.isResumed()){

        }
        else{
            t1.setText("Worldwide");
            t1.setTextSize(20);
            t1.setVisibility(View.GONE);
            t2.setText("Worldwide");
            t2.setTextSize(24);
            t2.setTextColor(getResources().getColor(R.color.black));
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            t3.setText("COVID-19 LIVE UPDATE");
            nestedScrollView.setNestedScrollingEnabled(true);
            t3.setTextSize(14);
            t3.setTextColor(getResources().getColor(R.color.green));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.container,homeFragment).commit();
        }*/
    }

}