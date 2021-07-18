package com.vabrodex.covbuddy.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vabrodex.covbuddy.Model.CountryModel;
import com.vabrodex.covbuddy.R;

import java.util.ArrayList;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private ArrayList<CountryModel> arrayList;

    public CountryAdapter(ArrayList<CountryModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_case,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CountryModel countryModel = arrayList.get(position);
        holder.countryName.setText(countryModel.getCountryName());
        holder.countryCase.setText(countryModel.getCountryCase());

    }

    @Override
    public int getItemCount() {
        return arrayList!=null?arrayList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryName, countryCase;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryCase = itemView.findViewById(R.id.countrycase);
            countryName = itemView.findViewById(R.id.countryname);

        }
    }
}
