package com.example.jeonghyeongkim.dong_geo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

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
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;



public class PostData extends AsyncTask<String, Void, String> {
    private DonggeoDataCallback donggeoDataCallback;


    String mJsonString;
    String errorString = null;
    ProgressDialog progressDialog;
    Context mcontext;
    JSONObject get_object;
    SharedPreferences sh;


    ArrayList<DonggeoData> donggeoData = new ArrayList<>();
    public static String parsed_response = "";

    public PostData(Context context, JSONObject object, DonggeoDataCallback donggeoDataCallback) {

        this.errorString = errorString;
        //this.progressDialog = progressDialog;
        this.mcontext = context;
        Log.d("Post_class_context", String.valueOf(this.mcontext));
        this.get_object = object;
        this.donggeoDataCallback = donggeoDataCallback;

        //Log.d("continent_result", String.valueOf(this.get_object));
    } // 콜백으로 인해서 바꿈...

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog = ProgressDialog.show(mcontext,
        //        "Please Wait", null, true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //progressDialog.dismiss();
        //if (donggeoDataCallback!=null) donggeoDataCallback.onTaskDone(donggeoData);


        Log.d("check", "response_post  - " + s);

        if (s == null) {
            //Log.d("fai")
            Toast.makeText(mcontext, "Fail", Toast.LENGTH_LONG);

        } else {

            mJsonString = s;
            Log.d("check", "response_post  - " + mJsonString);
            Log.d("kakao_load", "s" + s);

            if(mcontext == Main2Activity.getContext())
            {
                showResult(Main2Activity.getContext(), mJsonString);
            }
            else if(mcontext == MypageActivity.getContext()){

                Log.d("fragment","it work!");
                showResult();
                //return donggeoData;
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
                    Log.e("input_result", line);
                    sb.append(line);
                    break;
                }
                Log.e("input_result", line);
                in.close();
                Log.e("input_result", sb.toString());
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

    public void showResult(Context context, String data_line){
        Log.d("continent_result", "showResult before try");
        try{
            if(context == Main2Activity.getContext()) {
                JSONObject jsonObject = new JSONObject(data_line);
                JSONObject parsed_Object = jsonObject.getJSONObject("result");

                parsed_response = parsed_Object.getString("search_id");
                //Log.d("kakao_load", buffer_response);
                if (!parsed_response.equals("null")) {
                    Intent intent = new Intent(context, ContinentActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, KakaoInputActivity.class);
                    context.startActivity(intent);
                }
            }
            else if (context==MypageActivity.getContext()){
                JSONObject jsonObject = new JSONObject(data_line);
                JSONObject parsed_Object = jsonObject.getJSONObject("result");

                parsed_response = parsed_Object.getString("response");

                if(parsed_response=="fail"){ //등록되지 않은 유저!!!!!!!
                    Intent intent = new Intent(context, KakaoInputActivity.class); //   + 안내메시지
                    context.startActivity(intent);
                }
            }


        } catch (JSONException e){
            e.printStackTrace();
        }

    }
    public void showResult(){ // 컨텍스트 null일때 -> 마이페이지 띄우는거
        Log.d("continent_result", "showResult before try");
        try{
                Log.d("jsonjson", mJsonString);



                SaveExchangeRate saveExchangeRate = new SaveExchangeRate(sh,null,mcontext);

                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray object_to_array = jsonObject.getJSONArray("result");
                Log.d("jsonparsing is ready","ready");
                Log.d("array_length", String.valueOf(object_to_array.length()));
                for(int i=0;i<object_to_array.length();i++){
                    JSONObject tmp= (JSONObject)object_to_array.get(i);


                    String state = (String) tmp.get("state"); //fragment -> GlobalApplication 취급이기 때문에 팝니다 삽니다 여기서 구분지어야하나?
                    Log.d("parsing_state",state);

                    String currency = (String) tmp.get("currency");
                    Log.d("parsing_currency",currency);

                    String amount = (String) tmp.get("amount");
                    Log.d("parsing_amount",amount);
                    float rate = saveExchangeRate.get_rate(currency);
                    rate=rate*Float.valueOf(amount);
                    String uni1 = (String) tmp.get("uni1");
                    Log.d("parsing_uni1",uni1);

                    donggeoData.add(new DonggeoData( currency, parseInt(amount) , (int)rate, uni1)); //currency, amount, converted_amount, university1

            }
            Log.d("donggeoData", String.valueOf(donggeoData.get(0).getAmount()));
            if (donggeoDataCallback!=null) donggeoDataCallback.onTaskDone(donggeoData);
        } catch (JSONException e){
            e.printStackTrace();
        }

    }


}
