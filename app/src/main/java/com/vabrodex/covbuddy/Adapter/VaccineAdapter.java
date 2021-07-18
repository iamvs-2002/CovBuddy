package com.vabrodex.covbuddy.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vabrodex.covbuddy.Model.VaccineModel;
import com.vabrodex.covbuddy.R;

import java.util.ArrayList;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.ViewHolder> {

    private ArrayList<VaccineModel> arrayList;

    public VaccineAdapter(ArrayList<VaccineModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VaccineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccines,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VaccineModel vaccineModel = arrayList.get(position);
        holder.state.setText(vaccineModel.getState_name());
        holder.district.setText(vaccineModel.getDistrict_name());
        holder.block.setText(vaccineModel.getBlock_name());
        holder.center.setText(vaccineModel.getCenter_id());
        holder.hospital.setText(vaccineModel.getName());
        holder.pin.setText(vaccineModel.getPincode());
        holder.capacity.setText(vaccineModel.getAvailable_capacity());
        holder.timings.setText(vaccineModel.getFrom() +" - "+vaccineModel.getTo());
        holder.fee.setText(vaccineModel.getFee_type());

        int l = vaccineModel.getSlots().length;
        String slot = vaccineModel.getSlots()[0];
        for(int i =1;i<l;i++){
            slot = slot +", "+vaccineModel.getSlots()[i];
        }
        holder.slots.setText(slot);
        holder.address.setText(vaccineModel.getAddress());
        holder.minage.setText(vaccineModel.getMin_age_limit());


    }

    @Override
    public int getItemCount() {
        return arrayList!=null?arrayList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView state,district,block,center,hospital,pin,capacity,timings,fee,slots,address,minage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.vacstatename);
            district = itemView.findViewById(R.id.vacdistname);
            block = itemView.findViewById(R.id.vacblockname);
            center = itemView.findViewById(R.id.vaccenterid);
            hospital = itemView.findViewById(R.id.vachospitalname);
            pin = itemView.findViewById(R.id.vacpincode);
            capacity = itemView.findViewById(R.id.vaccap);
            timings = itemView.findViewById(R.id.vactimings);
            fee = itemView.findViewById(R.id.vacfee);
            slots = itemView.findViewById(R.id.vacslots);
            address = itemView.findViewById(R.id.vacaddress);
            minage = itemView.findViewById(R.id.vacagelimit);
        }
    }
}
