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
import android.widget.TextView;

public class ContinentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CheckBox check1;
    CheckBox check2;
    CheckBox check3;
    CheckBox check4;
    CheckBox check5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String conti_num = "";

        check1 = (CheckBox)findViewById(R.id.check1);
        check2 = (CheckBox)findViewById(R.id.check2);
        check3 = (CheckBox)findViewById(R.id.check3);
        check4 = (CheckBox)findViewById(R.id.check4);
        check5 = (CheckBox)findViewById(R.id.check5);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (check1.isChecked()) {
            conti_num += 1;
        }

        if (check2.isChecked()) {
            conti_num += 2;
        }

        if (check3.isChecked()) {
            conti_num += 3;
        }

        if (check4.isChecked()) {
            conti_num += 4;
        }

        if (check5.isChecked()) {
            conti_num += 5;
        }
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
                //intent.putExtra("conti_num", conti_num);
                startActivity(intent);
        }
    }
}
