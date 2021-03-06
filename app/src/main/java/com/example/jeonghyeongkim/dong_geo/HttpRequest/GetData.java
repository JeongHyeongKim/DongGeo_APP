package com.example.jeonghyeongkim.dong_geo.HttpRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.jeonghyeongkim.dong_geo.Activity.MainActivity;
import com.example.jeonghyeongkim.dong_geo.Activity.SearchBuyFragment;
import com.example.jeonghyeongkim.dong_geo.Activity.SearchByContinent;
import com.example.jeonghyeongkim.dong_geo.Activity.SearchByValue;
import com.example.jeonghyeongkim.dong_geo.Activity.SearchResult;
import com.example.jeonghyeongkim.dong_geo.Activity.SplashActivity;
import com.example.jeonghyeongkim.dong_geo.Callback.ExchangeDataCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetData extends AsyncTask<String, Void, String> {

    private ExchangeDataCallback exchangeDataCallback;
    String mJsonString;
    String errorString = null;
    ProgressDialog progressDialog;
    Context mcontext;
    JSONObject jsonObject;
    public int length = 0;
    JSONArray jsonArray;


    public GetData(Context context, ExchangeDataCallback exchangeDataCallback) {
        this.mJsonString = mJsonString;
        this.errorString = errorString;
        this.progressDialog = progressDialog;
        this.mcontext = context;
        this.exchangeDataCallback = exchangeDataCallback;
    }

    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog = ProgressDialog.show(mcontext,
  //              "Please Wait", null, true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        progressDialog.dismiss();
        Log.d("php_get", "response  - " + s);

        if (s == null) {
            Toast.makeText(mcontext, "Fail", Toast.LENGTH_LONG);
        } else {
            mJsonString = s;
            Log.d("continent_result", "s" + s);
            if(mcontext == MainActivity.getContext()){
               showResult(MainActivity.getContext());
            }
            else if(mcontext == SearchByContinent.getContext()){
                Log.d("continent_result", "continent_onPost");
                showResult(SearchByContinent.getContext());
            }
            else if(mcontext == SearchByValue.getContext()){
                Log.d("searchpost_result", "searchPost");
                showResult(SearchByValue.getContext());
            }
            else if(mcontext == SearchBuyFragment.context){
                Log.d("continent_result", "before_fragment");
                showResult(SearchBuyFragment.context);
            }
            else if (mcontext==SplashActivity.getContext()){
                try {
                    exchange_rate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            if(context == MainActivity.getContext()) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                String buffer_world_count = jsonObject1.getString("world");
                String buffer_user_count = jsonObject1.getString("user");
                String buffer_request_count = jsonObject1.getString("request"); //json파싱 결과를 각 임시 변수에 삽입

                ((MainActivity) context).user_count.setText(buffer_user_count + "명의 사용자");
                ((MainActivity) context).world_count.setText(buffer_world_count + "개국");
                ((MainActivity) context).request_count.setText(buffer_request_count + "개의 게시글");
            }else if(context == SearchByContinent.getContext()){
                jsonArray = jsonObject.getJSONArray("result");
                setJsonArray(jsonArray);
//                ((ContinentActivity) context).jsonArray = jsonArray;
                Intent intent = new Intent(context, SearchResult.class);
                intent.putExtra("jsonArray", jsonArray.toString());
                context.startActivity(intent);
//                length = jsonArray.length();
//                for(int i = 0; i< jsonArray.length(); i++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                    String continent_id = jsonObject1.getString("id");
//                    String continent_currency = jsonObject1.getString("currency");
//                    String continent_amount = jsonObject1.getString("amount");
//                    String continent_uni1 = jsonObject1.getString("uni1");
//
//                    String request_state = jsonObject1.getString("state");
//                    String request_continent = jsonObject1.getString("continent");

//                    ((SearchActivity)context).jsonObject = jsonObject1;
//                    ((SearchActivity) context).state = request_state;
//                    ((SearchActivity) context).continent = request_continent;
//
//
//                    Log.d("continent_result2", request_state);
//                    Log.d("continent_result2", request_continent);
//
//                    Log.d("continent_result", continent_id);
//                    Log.d("continent_result", continent_currency);
//                    Log.d("continent_result", continent_amount);
//                    Log.d("continent_result", continent_uni1);
//                }

            } else if(context == SearchByValue.getContext()) {
                jsonArray = jsonObject.getJSONArray("result");
                setJsonArray(jsonArray);
//                ((ContinentActivity) context).jsonArray = jsonArray;
                Intent intent = new Intent(context, SearchResult.class);
                intent.putExtra("jsonArray", jsonArray.toString());
                context.startActivity(intent);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void exchange_rate() throws JSONException {
        JSONArray jsonArray = new JSONArray(mJsonString);

        exchangeDataCallback.onTaskDone(jsonArray);


    }


    public void setJsonArray(JSONArray jsonArray){
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        Log.d("Tab3", String.valueOf(jsonArray));
        return jsonArray;
    }

}