package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeonghyeongkim.dong_geo.OnRangeSeekbarChangeListener;
import com.example.jeonghyeongkim.dong_geo.OnRangeSeekbarFinalValueListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchPostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Context context;
    String[] exchangeRate = {
            "유럽연합 EUR",  "영국 GBP",  "스위스 CHF", "스웨덴 SEK", "체코 CZK", "덴마크 DKK", "노르웨이 NOK", "러시아 RUB", "폴란드 PLN", //유럽 코드 1
            "일본 JPY", "중국 CNY", "홍콩 HKD", "대만 TWD",  "몽골 MNT", "카자흐스탄 KZT", "인도 INR","파키스탄 PKR", // 동아시아 코드 2
            "태국 THB", "싱가포르 SGD", "말레이시아 MYR", "인도네시아 IDR",  "브루나이 BND", "베트남 VND",// 동남 아시아 코드 3
            "오만 OMR",  "터키 TRY", "이스라엘 ILS", "사우디아라비아 SAR", "쿠웨이트 KWD", "바레 BHD",  "아랍에미리트 AED", "요르단 JOD",  "카타르 QAR", //중동 코드 4
            "호주 AUD", "뉴질랜드 NZD", //오세아니아 코드 5
            "캐나다 CAD", "미국 USD", //북아메리카 코드 6
            "칠레 CLP", "브라질 BRL",  // 남아메리카 코드 7
            "이집트 EGP", "남아공 ZAR", // 아프리카 코드 8
    };
    String[] school_item = { "가톨릭대학교", "감리교신학대학교", "건국대학교", "경희대학교", "고려대학교", "광운대학교", "국민대학교",
            "덕성여자대학교", "동국대학교", "동덕여자대학교", "명지대학교", "삼육대학교", "상명대학교", "서강대학교",
            "서경대학교", "서울과학기술대학교", "서울교육대학교", "서울기독대학교", "서울대학교", "서울시립대학교",
            "서울여자대학교", "서울한영대학교", "성공회대학교", "성균관대학교", "성신여자대학교", "세종대학교", "숙명여자대학교",
            "숭실대학교", "연세대학교", "이화여자대학교", "장로회신학대학교", "중앙대학교", "총신대학교", "추계예술대학교",
            "한국성서대학교", "한국외국어대학교", "한국체육대학교", "한성대학교", "한양대학교", "홍익대학교"};

    EditText exchangeInput;
    EditText schoolInput;
    InputMethodManager im;

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


        exchangeInput = (EditText)findViewById(R.id.search_exchangeInput);
        schoolInput =  (EditText)findViewById(R.id.search_schoolInput);





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

        AutoCompleteTextView exchangeView = (AutoCompleteTextView) findViewById(R.id.search_exchangeInput);

        exchangeView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, exchangeRate));

        MultiAutoCompleteTextView schoolView =
                (MultiAutoCompleteTextView) findViewById(R.id.search_schoolInput);
        schoolView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        schoolView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, school_item));

        im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


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

        switch (v.getId()){
            case R.id.seachButton:
                try {
                    String exchange = exchangeInput.getText().toString().split(" ")[1];
//                int price = Integer.parseInt(priceInput.getText().toString());
                    String school = schoolInput.getText().toString().split(",")[0];

//                long now = System.currentTimeMillis();
//                Date date = new Date(now);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                final String getTime = sdf.format(date); // 현재 날짜 가져오기

                Toast.makeText(this, "통화 " + exchange + " 금액 " + minCost +"~" + maxCost + " 학교 " + school, Toast.LENGTH_LONG).show();
//                Log.i("write", "price" + price + "exchange" + exchange);

//                Intent intent = new Intent(SearchPostActivity.this,  SearchActivity.class);
//                intent.putExtra("from", "searchPostActivity");
//                startActivity(intent);
                context = SearchPostActivity.this;
                GetData getData = new GetData(SearchPostActivity.this,null);
                getData.execute("http://13.124.152.254/dong_geo/search_detail.php?request_state='0'&request_currency='"+exchange+"'&request_min='"+minCost+"'&request_max="+maxCost+"&request_university1='"+school+"'");

                }catch (Exception e){
                    Toast.makeText(this, "전부 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    public static Context getContext() {
        return context;
    }
}
