package com.example.jeonghyeongkim.dong_geo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.jeonghyeongkim.dong_geo.Callback.ExchangeDataCallback;
import com.example.jeonghyeongkim.dong_geo.ExchangeRate;
import com.example.jeonghyeongkim.dong_geo.HttpRequest.GetData;
import com.example.jeonghyeongkim.dong_geo.R;

import org.json.JSONArray;

public class SplashActivity extends Activity {
    private static Context context;
    SharedPreferences exchange_rate;
    SharedPreferences.Editor editor;
    JSONArray buffer;
    ExchangeRate save_exchange_rate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        context=SplashActivity.this;
        GetData getData = (GetData) new GetData(SplashActivity.this, new ExchangeDataCallback() {
            @Override
            public void onTaskDone(JSONArray jsonArray) {
                buffer=jsonArray; //총 42개국, 저장할 정보 -> 저장하는 현재 시간과 파싱한 name 정보의 3~5인덱스 값!!
                save_exchange_rate=new ExchangeRate(exchange_rate,editor,SplashActivity.this);
                save_exchange_rate.save_rate(buffer);
//                save_exchange_rate.get_rate("USD");
            }
        }).execute("");
        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000); //불편하니까 개발때는 500으로 ㄱㄱ
    }
    public static Context getContext() {

        return context;
    }


}
