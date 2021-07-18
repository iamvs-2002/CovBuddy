package com.vabrodex.covbuddy.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vabrodex.covbuddy.Model.HelplineModel;
import com.vabrodex.covbuddy.R;

import java.util.ArrayList;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class HelplineAdapter extends RecyclerView.Adapter<HelplineAdapter.ViewHolder> {

    private ArrayList<HelplineModel> arrayList;

    public HelplineAdapter(ArrayList<HelplineModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HelplineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_helpline,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HelplineModel helplineModel = arrayList.get(position);
        holder.stateName.setText(helplineModel.getStateName());
        holder.stateHelp.setText(helplineModel.getHelplinenum());

    }

    @Override
    public int getItemCount() {
        return arrayList!=null?arrayList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stateName, stateHelp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stateHelp = itemView.findViewById(R.id.helpstatenumber);
            stateName = itemView.findViewById(R.id.helpstatename);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "";

                    s = stateHelp.getText().toString();

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
                        Toast.makeText(v.getContext(), "Error: Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
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
}
