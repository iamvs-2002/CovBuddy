package com.vabrodex.covbuddy.Model;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class NewsModel {
    private String Headline;
    private String Date;
    private String urltoImage;
    private String url;

    public NewsModel() {
    }

    public NewsModel(String headline, String date, String urltoImage, String url) {
        Headline = headline;
        Date = date;
        this.urltoImage = urltoImage;
        this.url = url;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUrltoImage() {
        return urltoImage;
    }

    public void setUrltoImage(String urltoImage) {
        this.urltoImage = urltoImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
