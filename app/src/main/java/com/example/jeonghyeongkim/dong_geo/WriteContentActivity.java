package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.response.model.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteContentActivity extends AppCompatActivity
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
    String[] school_item = { "동덕여자대학교", "충북대학교", "서울대학교", "연세대학교", "고려대학교", "경희대학교", "한양대학교", "이화여자대학교" };
    EditText exchangeInput;
    EditText priceInput;
    EditText schoolInput;
    InputMethodManager im;
    TextView kakaonic;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        exchangeInput = (EditText)findViewById(R.id.exchangeInput);
        priceInput = (EditText)findViewById(R.id.priceInput);
        schoolInput = (EditText)findViewById(R.id.schoolInput);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //네비게이션 헤더 kakao start
        kakaonic=(TextView) findViewById(R.id.kakao_nick);
        final View headerView = navigationView.getHeaderView(0);
        TextView kakaoNickView = (TextView) headerView.findViewById(R.id.kakao_nick);

        if(KakaoSignupActivity.get_kakao_nickname() != null) {
            kakaoNickView.setText(KakaoSignupActivity.get_kakao_nickname());
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        final ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
                        URL url = new URL(KakaoSignupActivity.get_kakao_image());
                        InputStream is = url.openStream();
                        final Bitmap bm = BitmapFactory.decodeStream(is);
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                imageView.setImageBitmap(bm);
                            }
                        });
                        imageView.setImageBitmap(bm);
                    } catch(Exception e){

                    }

                }
            });

            t.start();
        }
        else{
            kakaoNickView.setText("로그인을 해주세요");
        }
        //네비게이션 헤더 kakao end

        AutoCompleteTextView exchangeView = (AutoCompleteTextView) findViewById(R.id.exchangeInput);

        exchangeView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, exchangeRate));

        MultiAutoCompleteTextView schoolView =
                (MultiAutoCompleteTextView) findViewById(R.id.schoolInput);
        schoolView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        schoolView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, school_item));
        context = WriteContentActivity.this;
        im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.writeButton:
                String exchange = exchangeInput.getText().toString();
                //선택한 국가 대륙판단하기
                int continent = checkState(exchange);
                Log.d("continent", String.valueOf(continent));
                int amount = Integer.parseInt(priceInput.getText().toString());
                String school =  schoolInput.getText().toString(); //지금은 한개지만, 향후 여러개로 수정해야할듯


                long kakao_id=KakaoSignupActivity.get_kakao_id();

                JSONObject jsonObject=MakeJson( exchange, continent ,amount, school, String.valueOf(kakao_id)); // 인증값은 0으로 테스트함
                PostData postData = new PostData(WriteContentActivity.this, jsonObject);
                postData.execute("http://13.124.152.254/dong_geo/write_content.php");

                Toast.makeText(this, "통화 " + exchange + " 금액 " + amount + " 학교 " + school, Toast.LENGTH_LONG).show();
//                Log.i("write", "price" + price + "exchange" + exchange);
                break;
        }
    }

    private int checkState(String exchange) {

       if( exchange.equals("유럽연합 EUR") ||  exchange.equals("영국 GBP") ||  exchange.equals("스위스 CHF") ||  exchange.equals("스웨덴 SEK") ||  exchange.equals("체코 CZK") ||  exchange.equals("덴마크 DKK") ||  exchange.equals("노르웨이 NOK") ||  exchange.equals("러시아 RUB") ||  exchange.equals("폴란드 PLN") )
           return  1;//유럽 코드 1
       else if( exchange.equals("일본 JPY") ||  exchange.equals("중국 CNY") ||  exchange.equals("홍콩 HKD") ||  exchange.equals("대만 TWD") ||   exchange.equals("몽골 MNT") ||  exchange.equals("카자흐스탄 KZT") ||  exchange.equals("인도 INR") ||  exchange.equals("파키스탄 PKR"))
           return  2; // 북아시아 코드 2
       else if( exchange.equals("태국 THB") ||  exchange.equals("싱가포르 SGD") ||  exchange.equals("말레이시아 MYR") ||  exchange.equals("인도네시아 IDR") ||   exchange.equals("브루나이 BND") ||  exchange.equals("베트남 VND"))
           return  3;// 동남 아시아 코드 3
       else if( exchange.equals("오만 OMR") ||  exchange.equals("터키 TRY") ||  exchange.equals("이스라엘 ILS") ||  exchange.equals("사우디아라비아 SAR") ||  exchange.equals("쿠웨이트 KWD") ||  exchange.equals("바레 BHD") ||   exchange.equals("아랍에미리트 AED") ||  exchange.equals("요르단 JOD") ||  exchange.equals( "카타르 QAR"))
           return 4;//중동 코드 4
       else if ( exchange.equals("호주 AUD") ||  exchange.equals("뉴질랜드 NZD"))
           return 5;//오세아니아 코드 5
       else if (  exchange.equals("캐나다 CAD") ||  exchange.equals("미국 USD"))
           return 6;//북아메리카 코드 6
       else if( exchange.equals("칠레 CLP") ||  exchange.equals("브라질 BRL"))
           return 7;// 남아메리카 코드 7
       else if(  exchange.equals("이집트 EGP") ||  exchange.equals("남아공 ZAR"))
           return 8;// 아프리카 코드 8
       else
            return 0;

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
        getMenuInflater().inflate(R.menu.write_content, menu);
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

        if (id == R.id.nav_main) {
            Intent intent = new Intent(WriteContentActivity.this, Main2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exchange) {
            Intent intent = new Intent(WriteContentActivity.this, KakaoInputActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_write_content) {
//            Intent intent = new Intent(WriteContentActivity.this, WriteContentActivity.class);
//            startActivity(intent);
        } else if (id ==  R.id.nav_search_content){
            Intent intent = new Intent(WriteContentActivity.this, SearchPostActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(WriteContentActivity.this, MypageActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(WriteContentActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    } //

    private JSONObject MakeJson(String exchange, int continent, int amount,  String school, String kakao_id){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String getTime = sdf.format(date); // 현재 날짜 가져오기

        try {
            jsonObject.put("currency", exchange);
            jsonObject.put("continent", continent);
            jsonObject.put("amount", amount);
            jsonObject.put("university1", school);
            jsonObject.put("university2","NULL");
            jsonObject.put("university3","NULL");
            jsonObject.put("date", getTime);
            jsonObject.put("kakao_id", kakao_id); //대학 2,3은 현재 NULL로 테스트,
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static Context getContext() {
        return context;
    }

    public void onClickLinear(View view) {
        switch (view.getId()) {
            case R.id.linear:
                im.hideSoftInputFromWindow(schoolInput.getWindowToken(), 0);
                im.hideSoftInputFromWindow(exchangeInput.getWindowToken(), 0);
                im.hideSoftInputFromWindow(priceInput.getWindowToken(), 0);
                break;
        }
    }

} //class 중괄호
