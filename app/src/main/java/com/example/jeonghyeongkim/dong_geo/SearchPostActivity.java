package com.example.jeonghyeongkim.dong_geo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeonghyeongkim.dong_geo.OnRangeSeekbarChangeListener;
import com.example.jeonghyeongkim.dong_geo.OnRangeSeekbarFinalValueListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchPostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Number minCost;
    Number maxCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.search_rangeSeekbar3);

// get min and max text view
        final TextView tvMin = (TextView)findViewById(R.id.textMin);
        final TextView tvMax = (TextView)findViewById(R.id.textMax);

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                minCost = minValue;
                tvMax.setText(String.valueOf(maxValue));
                maxCost = maxValue;
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_post_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //햄버거바 메뉴 아이디 수정
        if (id == R.id.nav_main) {
            // Handle the camera action
        } else if (id == R.id.nav_exchange) {
            Intent intent = new Intent(SearchPostActivity.this, KakaoInputActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_write_content) {
            Intent intent = new Intent(SearchPostActivity.this, WriteContentActivity.class);
            startActivity(intent);
        } else if (id ==  R.id.nav_search_content) {
            Intent intent = new Intent(SearchPostActivity.this, SearchPostActivity.class);
            startActivity(intent);
        }  else if (id ==  R.id.nav_search_content){
//                Intent intent = new Intent(SearchPostActivity.this, SearchPostActivity.class);
//                startActivity(intent);
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(SearchPostActivity.this, MypageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(SearchPostActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onClick(View v){
        EditText exchangeInput = (EditText)findViewById(R.id.search_exchangeInput);
        EditText schoolInput = (EditText)findViewById(R.id.search_schoolInput);

        switch (v.getId()){
            case R.id.seachButton:
                String exchange = exchangeInput.getText().toString();
//                int price = Integer.parseInt(priceInput.getText().toString());

                String school =  schoolInput.getText().toString();

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                final String getTime = sdf.format(date); // 현재 날짜 가져오기

                Toast.makeText(this, "통화 " + exchange + " 금액 " + minCost +"~" + maxCost + " 학교 " + school, Toast.LENGTH_LONG).show();
//                Log.i("write", "price" + price + "exchange" + exchange);
                break;
        }
    }
}
