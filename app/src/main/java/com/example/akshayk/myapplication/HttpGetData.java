package com.example.akshayk.myapplication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by akshayk on 22/11/2016.
 */

public class HttpGetData extends AsyncTask  {
    private String result;
    private URL url = null;
    private HttpURLConnection urlConnection = null;

    private String line = null;
    ArrayList<String> list;
    private String server_response;
    String urlResult;

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            url = new URL("");
            urlConnection = (HttpURLConnection) url.openConnection();
            server_response = readStream(urlConnection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
                System.out.println("Inside readstream");


            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}

