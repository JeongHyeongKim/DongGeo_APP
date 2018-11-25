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
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.Thread.sleep;

public class PostData extends AsyncTask<String, Void, String> {

    String mJsonString;
    String errorString = null;
    ProgressDialog progressDialog;
    Context mcontext;
    JSONObject get_object;
    public static String buffer_response = "";
    static String buffer_result ="";


    public PostData(Context context, JSONObject object) {
        this.mJsonString = mJsonString;
        this.errorString = errorString;
        this.progressDialog = progressDialog;
        this.mcontext = context;
        this.get_object = object;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(mcontext,
                "Please Wait", null, true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();

        Log.d("check", "response  - " + s);

        if (s == null) {

            Toast.makeText(mcontext, "Fail", Toast.LENGTH_LONG);

        } else {

            mJsonString = s;
            Log.e("post_text",mJsonString);

            if(mcontext == Main2Activity.getContext())
            {
                showResult(Main2Activity.getContext());
                get_buffer_response();
            }


        }

    }

    @Override
    protected String doInBackground(String... strings) {
        String serverURL = strings[0];
        try{
            URL url = new URL(serverURL);
            JSONObject postDataParams = get_object;

            Log.e("params",postDataParams.toString());

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(getPostDataString(postDataParams));
            //Log.e("asdf", getPostDataString(postDataParams));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responseCode=httpURLConnection.getResponseCode();
            Log.e("post_response", String.valueOf(responseCode));

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                httpURLConnection.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {
                    Log.e("input", line);
                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

            }
            else {
                return new String("false : "+responseCode);
            }
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }


    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private void showResult(Context context){
        try{
//            JSONObject jsonObject = new JSONObject(mJsonString);
//            JSONArray jsonArray = jsonObject.getJSONArray("result");
//            JSONObject item = jsonArray.getJSONObject(0);

            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");


            //String buffer_search_id = jsonObject1.getString("search_id");
            //((MypageActivity) context).search_id.setText(buffer_search_id);

//            String buffer_request_count=jsonObject1.getString("response");
//            Log.e("asdff",buffer_request_count);

            buffer_response = jsonObject1.getString("response");
            Log.e("abc", buffer_response);
            //((Main2Activity) context).result = buffer_response;


        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public static String get_buffer_response() {
        return buffer_response;
    }

}
