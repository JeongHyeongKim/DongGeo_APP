package com.example.jeonghyeongkim.dong_geo.Exchange;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.jeonghyeongkim.dong_geo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by jeonghyeongkim on 2018. 1. 23..
 */

public class Exchange_Query_manana extends Activity { // 문제없음, 나라를 선택해서 그 나라의 통화코드를 적으면 됨!, 다만 100엔 단위처럼 그런건 100 곱해주면됨
    String exchange; // manana api사용시 입력할 통화코드
    String mJsonString;
    String rate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_current_rate);

    }


    private void showResult() { // 형태가 array이므로 array파싱을 한다
        try {
            JSONArray jsonArray = new JSONArray(mJsonString);
            //Integer list_count = jsonArray.length();
            JSONObject jsonObject = jsonArray.getJSONObject(0); // 배열하나므로 인덱스는 0만 필요
            rate = jsonObject.getString("rate"); //환율
            Toast toast = Toast.makeText(Exchange_Query_manana.this, rate, Toast.LENGTH_LONG);
            toast.show();

            Log.i("json_parsing", rate); // 로그캣으로 확인할거임
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }


    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Exchange_Query_manana.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //one.setText(s);
            Log.d("exchange_Query", "response  - " + s);

            if (s == null) {

                Toast.makeText(Exchange_Query_manana.this, "Fail", Toast.LENGTH_LONG);

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
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("phptest_Exchange_Query", "response code - " + responseStatusCode);

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

                Log.d("Exchange_Query", "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }

}




/*
유럽연합EUR, 일본JPY, 중국CNY, 홍콩HKD, 대만TWD
영국GBP,오만OMR, 캐나다CAD, 스위스CHF, 스웨덴SEK,
호주AUD, 뉴질랜드NZD, 체코CZK, 칠레CLP, 터키TRY,
몽골MNT, 이스라엘ILS, 덴마크DKK, 노르웨이NOK,
사우디아라비아SAR, 쿠웨이트KWD, 바레BHD,
아랍에미리트AED, 요르단JOD, 이집트EGP, 태국THB,
싱가포르SGD,말레이시아MYR, 인도네시아IDR
카타르QAR, 카자흐스탄KZT, 브루나이BND,인도INR
파키스탄PKR, 방글라데시BNT,필리핀PHP, 멕시코MXN
브라질BRL,베트남VND,남아공ZAR,러시아RUB,헝가리HUF
폴란드PLN
 */

