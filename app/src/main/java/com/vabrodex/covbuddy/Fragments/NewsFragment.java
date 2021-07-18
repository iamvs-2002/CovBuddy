package com.vabrodex.covbuddy.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.vabrodex.covbuddy.Adapter.NewsAdapter;
import com.vabrodex.covbuddy.Model.NewsModel;
import com.vabrodex.covbuddy.R;


import java.util.ArrayList;
import java.util.List;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class NewsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<NewsModel> arrayList;
    ImageButton refresh;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        initialize();
        setadapter();
        getNewsDataAPI();



        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
                getNewsDataAPI();
            }
        });

        return view;
    }

    private void initialize() {
        recyclerView = view.findViewById(R.id.recyclerViewNews);
        refresh = view.findViewById(R.id.refresh);
    }
    private void setadapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NewsAdapter newsAdapter = new NewsAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(newsAdapter);
    }

    private void getNewsDataAPI() {

        arrayList = new ArrayList<>();

        NewsApiClient newsApiClient = new NewsApiClient("bdb21d43d6264f8195c06b39928a7fcc");

        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .q("covid-19")
                        .language("en")
                        .country("in")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {

                        List<Article> list = response.getArticles();



                        for(int i = 0; i<response.getArticles().size(); i++){

                            arrayList.add(new NewsModel(
                                    list.get(i).getTitle(),
                                    list.get(i).getPublishedAt(),
                                    list.get(i).getUrlToImage(),
                                    list.get(i).getUrl()
                            ));
                        }
                        setadapter();
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getActivity(), "Error: Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}