package com.example.jeonghyeongkim.dong_geo.KaKao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jeonghyeongkim.dong_geo.Activity.SearchByContinent;
import com.example.jeonghyeongkim.dong_geo.MenuIntent;
import com.example.jeonghyeongkim.dong_geo.HttpRequest.PostData;
import com.example.jeonghyeongkim.dong_geo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class KakaoIDInputActivity extends AppCompatActivity {


    private static Context context;
    long kakao_id = KakaoSignupActivity.get_kakao_id(); //세션에서 로그인 일련번호 가져오기.
    MenuIntent menuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuIntent = new MenuIntent(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuIntent);
        menuIntent.setKakaoNickView(navigationView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onClick(View v){
        EditText kakaoInput = (EditText)findViewById(R.id.kakaoInput);

        switch (v.getId()){
            case R.id.writeButton:
                String search_id = kakaoInput.getText().toString();
                Toast.makeText(this, "카카오 아이디 : " + search_id, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(KakaoIDInputActivity.this, SearchByContinent.class);
                new PostData(KakaoIDInputActivity.this, MakeJson(kakao_id, search_id), false, null,null).execute("http://13.124.152.254/dong_geo/update_id.php");
                //postData.execute("http://13.124.152.254/dong_geo/update_id.php");
                startActivity(intent);
                break;
        }
    }

    private JSONObject MakeJson(long kakao_id, String id){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        try {
            //jsonObject.put("kakao_nickname", kakao_nickname);
            jsonObject.put("kakao_id", kakao_id);
            jsonObject.put("search_id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static Context getContext() {
        return context;
    }

}
