package com.example.jeonghyeongkim.dong_geo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetData extends AsyncTask<String, Void, String> {

    String mJsonString;
    String errorString = null;
    ProgressDialog progressDialog;
    Context mcontext;

    public GetData(Context context) {
        this.mJsonString = mJsonString;
        this.errorString = errorString;
        this.progressDialog = progressDialog;
        this.mcontext = context;
    }


    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(mcontext,
                "Please Wait", null, true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        //one.setText(s);
        Log.d("overlay", "response  - " + s);

        if (s == null) {

            Toast.makeText(mcontext, "Fail", Toast.LENGTH_LONG);

        } else {

            mJsonString = s;

           if(mcontext == Main2Activity.getContext())
           {
               showResult(Main2Activity.getContext());
           }

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

    private void showResult(Context context){
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            JSONObject item = jsonArray.getJSONObject(0);
            String buffer_world_count=item.getString("world");
            String buffer_user_count=item.getString("user");
            String buffer_request_count=item.getString("request"); //json파싱 결과를 각 임시 변수에 삽입

            ((Main2Activity) context).user_count.setText(buffer_user_count+"명의 사용자");
            ((Main2Activity) context).world_count.setText(buffer_world_count+"개국");
            ((Main2Activity) context).request_count.setText(buffer_request_count+"개의 게시글");




        } catch (JSONException e){
            e.printStackTrace();
        }

    }




}