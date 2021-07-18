package com.vabrodex.covbuddy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vabrodex.covbuddy.DetailsActivity;
import com.vabrodex.covbuddy.Model.BedModel;
import com.vabrodex.covbuddy.R;

import java.util.ArrayList;

/*
* Made By: Vaibhav Singhal
* Dated: 16-06-2021
* */

public class BedAdapter extends RecyclerView.Adapter<BedAdapter.ViewHolder> {

    private ArrayList<BedModel> arrayList;
    private Context mContext;


    public BedAdapter(Context context, ArrayList<BedModel> arrayList) {
        this.arrayList = arrayList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bed,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BedModel bedModel = arrayList.get(position);
        holder.stateName.setText(bedModel.getStateName());
        //holder.statebed.setText(bedModel.getBeds());

        holder.statebed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x =(new Intent(mContext, DetailsActivity.class));
                x.putExtra("url",bedModel.getBeds());
                mContext.startActivity(x);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList!=null?arrayList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stateName, statebed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statebed = itemView.findViewById(R.id.beds);
            stateName = itemView.findViewById(R.id.statebedname);

        }
    }
}
