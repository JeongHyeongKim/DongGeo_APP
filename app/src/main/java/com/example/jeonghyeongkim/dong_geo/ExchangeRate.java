package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangeRate {
    SharedPreferences sh;
    SharedPreferences.Editor editor;
    private Context context;
    private JSONArray raw_data;

    public ExchangeRate(SharedPreferences sh, SharedPreferences.Editor editor, Context context) {
        this.sh = sh;
        this.editor = editor;
        this.context = context;

    }

    public String getDate() { //
        sh=context.getSharedPreferences("exchange",0);
        String date = sh.getString("date","null");

        return date;

    }

    public void save_rate(JSONArray data){
        raw_data=data;
        sh=context.getSharedPreferences("exchange",0);
        editor = sh.edit();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD-hh:mm a");
        String getTime = sdf.format(date); // 현재 날짜 가져오기
        editor.putString("date", getTime);
        //for end
        for (int i=0;i<raw_data.length();i++) {
            try {
                JSONObject tmp = (JSONObject) raw_data.get(i);
                String name = (String) tmp.get("name");
                name = name.substring(0, 3);



                Log.d("name",name);
                Double rate = (Double) tmp.get("rate");
                rate = Double.parseDouble(String.format("%.2f", rate));
                Log.d("rate", String.valueOf(rate));
                editor.putFloat(name, Float.parseFloat(String.valueOf(rate)));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.commit();
    }

    public float get_rate(String exchange)
    {
        Log.d("context_mypage", String.valueOf(context));
        sh=context.getSharedPreferences("exchange",0);
        Float rate = sh.getFloat(exchange,0);
        Log.d("rate_usd", String.valueOf(rate));
        return rate;
    }
}
