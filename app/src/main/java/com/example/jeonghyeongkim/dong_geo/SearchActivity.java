package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean isDragged;
    TextView kakaonic;
    Handler handler = new Handler();
//    public static JSONObject jsonObject;
    public String state = "";
    public String continent = "";
    public JSONArray jsonArray;
    TabPagerAdapter pagerAdapter;
    static Context context;

    MenuIntent menuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuIntent = new MenuIntent(this);

        context = SearchActivity.this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuIntent);
        menuIntent.setKakaoNickView(navigationView);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("거래전"));
        tabLayout.addTab(tabLayout.newTab().setText("거래중"));
        tabLayout.addTab(tabLayout.newTab().setText("거래끝"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Initializing ViewPager
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        Intent intent = getIntent();
        String  temp= intent.getStringExtra("jsonArray");

        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), SearchActivity.this, tabLayout.getTabCount());
        try {
            jsonArray = new JSONArray(temp);
            Log.d("Tab", String.valueOf(jsonArray));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pagerAdapter.setJsonArray(jsonArray);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING)
                    isDragged= true;
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(getClass().getName(),"onPageSelected : "+tab);

                if (!isDragged) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                isDragged = false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() != viewPager.getCurrentItem()) {
                    viewPager.setCurrentItem(tab.getPosition());
                }
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

    public void setJsonArray(JSONArray jsonArray){
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public static Context getContext() {
        return context;
    }
}
