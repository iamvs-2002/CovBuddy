package com.vabrodex.covbuddy.Location;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * Made By: Vaibhav Singhal
 * Dated: 16-06-2021
 * */

public class DownloadUrl {

    public String readUrl(String myurl) throws IOException{

        String data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;



        try{
            URL url = new URL(myurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            String line = "";
            while ((line=br.readLine())!=null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();
        }
        catch (Exception e){
            Log.i("Error",e.getMessage());
        }
        finally {
            if(inputStream != null)
                inputStream.close();
            assert httpURLConnection != null;
            httpURLConnection.disconnect();
        }
        return data;

        /*
        OkHttpClient client= new OkHttpClient();
        Request request = new Request.Builder()
                .url(myurl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }*/
    }

}
