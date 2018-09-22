package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class KakaoInputActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static Context context;
    long kakao_id = KakaoSignupActivity.get_kakao_id(); //세션에서 로그인 일련번호 가져오기.
    String search_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //햄버거바 메뉴 아이디 수정
        if (id == R.id.nav_main) {
            Intent intent = new Intent(KakaoInputActivity.this, Main2Activity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_exchange) {
            Intent intent = new Intent(KakaoInputActivity.this, KakaoInputActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_write_content) {
            Intent intent = new Intent(KakaoInputActivity.this, WriteContentActivity.class);
            startActivity(intent);
        } else if (id ==  R.id.nav_search_content){
                Intent intent = new Intent(KakaoInputActivity.this, SearchPostActivity.class);
                startActivity(intent);

        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(KakaoInputActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(KakaoInputActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v){
        EditText kakaoInput = (EditText)findViewById(R.id.kakaoInput);

        switch (v.getId()){
            case R.id.writeButton:
                String search_id = kakaoInput.getText().toString();
                Toast.makeText(this, "카카오 아이디 : " + search_id, Toast.LENGTH_LONG).show();

                break;
        }
    }

    private JSONObject MakeJson(long kakao_id, String id){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        id = search_id;

        try {
            //jsonObject.put("kakao_nickname", kakao_nickname);
            jsonObject.put("kakao_id", kakao_id);
            jsonObject.put("search_id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //카카오 이메일 불러오기
    public void post_query() {
        PostData postData = new PostData(KakaoInputActivity.this, MakeJson(kakao_id, search_id));
        postData.execute("update_id.php");
    }

    public static Context getContext() {
        return context;
    }

}
