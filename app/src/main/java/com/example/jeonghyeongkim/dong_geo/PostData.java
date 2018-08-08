package com.example.jeonghyeongkim.dong_geo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit.http.POST;

public class PostData extends AsyncTask<String, Void, String>{

        String errorString = null;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Main2Activity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //one.setText(s);
            Log.d("overlay", "response  - " + s);

            if (s == null) {

                Toast.makeText(Main2Activity.this, "Fail", Toast.LENGTH_LONG);

            } else {

                mJsonString = s;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String serverURL = strings[0];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("phptest_request_overlay", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();
                return sb.toString().trim();


            } catch (Exception e) {
                Log.d("overlay_query", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }

