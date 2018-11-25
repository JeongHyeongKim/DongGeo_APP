package com.example.jeonghyeongkim.dong_geo;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ContinentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RadioGroup radioGroup;
    int conti_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i == R.id.radio1) {
                conti_num = 1;

            }
            else if (i == R.id.radio2) {
                conti_num = 2;

            }
            else if (i == R.id.radio3) {
                conti_num = 3;

            }
            else if (i == R.id.radio4) {
                conti_num = 4;

            }
            else if (i == R.id.radio5) {
                conti_num = 5;

            }
            else if (i == R.id.radio6) {
                conti_num = 6;

            }
            else if (i == R.id.radio7) {
                conti_num = 7;

            }
            else if (i == R.id.radio8) {
                conti_num = 8;

            }
        }
    };

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

        if (id == R.id.nav_main) {
            // Handle the camera action
        } else if (id == R.id.nav_exchange) {
            Intent intent = new Intent(ContinentActivity.this, KakaoInputActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_write_content) {
            Intent intent = new Intent(ContinentActivity.this, WriteContentActivity.class);
            startActivity(intent);
        }else if (id ==  R.id.nav_search_content){
            Intent intent = new Intent(ContinentActivity.this, SearchPostActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(ContinentActivity.this, MypageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(ContinentActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_conti:
                Intent intent = new Intent(ContinentActivity.this, SearchActivity.class);
                //db에 대륙번호를 보내는부분 추가하기
                PostData postData = new PostData(ContinentActivity.this, MakeJson(0, conti_num));
                postData.execute("http://13.124.152.254/dong_geo/search_continent.php");
                Toast.makeText(this, String.valueOf(conti_num), Toast.LENGTH_LONG).show();
                startActivity(intent);
        }
    }

    private JSONObject MakeJson(int request_state, int request_continent){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        try {
            jsonObject.put("request_state", request_state);
            jsonObject.put("request_continent", request_continent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
