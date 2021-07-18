package com.vabrodex.covbuddy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vabrodex.covbuddy.DetailsActivity;
import com.vabrodex.covbuddy.Model.NewsModel;
import com.vabrodex.covbuddy.R;

import java.util.ArrayList;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<NewsModel> arrayList;
    private Context mContext;
    private String url;

    public NewsAdapter(Context context, ArrayList<NewsModel> arrayList) {
        this.arrayList = arrayList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.e("COORRECT","\n\n\n\n\n\n\n\n\n\n Hello \n\n\n\n");

        NewsModel newsModel = arrayList.get(position);
        holder.Headline.setText(newsModel.getHeadline());
        holder.Date.setText(newsModel.getDate());
        Picasso.get().load(newsModel.getUrltoImage()).into(holder.pic);
        url = newsModel.getUrl();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x =(new Intent(mContext, DetailsActivity.class));
                x.putExtra("url",newsModel.getUrl());
                mContext.startActivity(x);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList!=null?arrayList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Headline, Date;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Headline = itemView.findViewById(R.id.headlinenews);
            Date = itemView.findViewById(R.id.datenews);
            pic = itemView.findViewById(R.id.imagenews);
        }
    }
}
