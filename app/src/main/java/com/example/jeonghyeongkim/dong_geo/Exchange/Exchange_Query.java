package com.example.jeonghyeongkim.dong_geo.Exchange;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.TimeZone.LONG;

/**
 * Created by jeonghyeongkim on 2018. 1. 22..
 */

public class Exchange_Query extends Activity{ // 미완성입니다~ 공공api이용한 파싱
    String authkey = ""; // 인증키
    //String searchdate=getDay(); // 현재 날짜 불러옴
    String exchange; // manana api사용시 입력할 통화코드

    String mJsonString;
    //GetData task = new GetData();
    String cur_unit;
    String cur_nm;
    String deal_bas_r;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetData getData = new GetData();
        getData.execute("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="+authkey+"&data=AP01");//searchdate는 디폴츠로 현재시
        //getData.execute("http://api.manana.kr/exchange/rate/KRW/"+exchange+"json");
    }

   /* public String getDay() { // 현재날짜 불러옴
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        final String getTime = sdf.format(date);
        return getTime;
    }*/




    private void showResult(){ // 형태가 object이므로 object파싱을 한다
        try {
            JSONArray jsonArray = new JSONArray(mJsonString);
            Integer list_count =jsonArray.length();
            JSONObject jsonObject =jsonArray.getJSONObject(2); // 호주달러 가져올거임

            cur_unit = jsonObject.getString("cur_unit"); //국가통화코드
            cur_unit = jsonObject.getString("cur_nm"); // 통화 이름 한글 번역
            deal_bas_r = jsonObject.getString("deal_bas_r"); // 환율
            Log.i("json_parsing",cur_unit);
            Log.i("json_parsing", cur_nm);
            Log.i("json_parsing", deal_bas_r);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }


    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Exchange_Query.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //one.setText(s);
            Log.d("exchange_Query", "response  - " + s);

            if (s == null){

                Toast.makeText(Exchange_Query.this, "Fail", Toast.LENGTH_LONG);

            }
            else {

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
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
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


/* AED=아랍에미리트 디르함 1        공공 api지원리스트
   ATS=오스트리아 실링 2
   AUD=호주 달러 3
   BEF=벨기에 프랑 4
   BHD=바레인 디나르 5
   CAD=캐나다 달러 6
   CHF=스위스 프랑 7
   CNH=중국 위안 8
   DEM=독일 마르크 9
   DDK=덴마크 크로네 10
   ESP(100)=스페인 페세타 11
   EUR=유로 12
   FIM=핀란드 마르카 13
   FRF=프랑스 프랑 14
   GBP=영국 파운드 15
   HKD=홍콩 달러 16
   IDR(100) = 인도네시아 루피아 17
   ITL(100) = 이탈리아 리라 18
   JPY(100) = 일본 엔 19
   KRW 20
   KWD = 쿠웨이트 디나르 21
   MYR = 말레이시아 링기트 22S
   NLG = 네덜란드 길더 23
   NOK = 노르웨이 크로네 24
   NZD = 뉴질렌드 달러 25
   SAR = 사우디 리얄 26
   SEK = 스웨덴 크로나 27
   SGD = 싱가포르 달러 28
   THB = 태국 바트 29
   USD 30
   XOF = 씨에프에이 프랑 31
   */
/* 동거에서 지원하는 국가 리스트
유럽연합, 일본, 중국, 홍콩, 대만
영국,오만, 캐나다, 스위스, 스웨덴,
호주, 뉴질랜드, 체코, 칠레, 터키,
모골, 이스라엘, 덴마크, 노르웨이,
사우디아라비아, 쿠웨이트, 바레인,
아랍에미리트, 요르단, 이집트, 태국,
싱가포르,말레이시아, 인도네시아
카타르, 카자흐스탄, 브루나이,인도
파키스탄, 방글라데시,필리핀, 멕시코
브라질,베트남,남아공,러시아,헝가리
폴란드
 */
