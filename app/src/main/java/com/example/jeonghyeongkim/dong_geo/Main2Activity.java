package com.example.jeonghyeongkim.dong_geo;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewManager;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{
    private static Context context;
    String mJsonString;
    TextView user_count;
    TextView world_count;
    TextView request_count;
    TextView kakaonic;
    android.support.v7.widget.Toolbar toolbar;
    LinearLayout linear;

    String kakaoNickName;
    String kakaoimage;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.setContentView(R.layout.activity_main2);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linear = (LinearLayout)inflater.inflate(R.layout.overlay, null);
        //파라미터를 세팅해줌
        LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

       // if(isStart) {
            win.addContentView(linear, paramlinear);
       // }
        kakaonic=(TextView) findViewById(R.id.kakao_nick);
        user_count=(TextView)findViewById(R.id.user_count);
        world_count=(TextView)findViewById(R.id.world_count);
        request_count=(TextView)findViewById(R.id.request_count);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //네비게이션 헤더 아이디 표시
        final View headerView = navigationView.getHeaderView(0);
        TextView kakaoNickView = (TextView) headerView.findViewById(R.id.kakao_nick);

        Intent intent = getIntent();
        kakaoNickName = intent.getStringExtra("nickname");
        kakaoimage = intent.getStringExtra("kakaoimage");
        if(kakaoNickName != null) {
            Log.d("nickName", kakaoNickName);
            Log.d("nickName", kakaoimage);
            Toast.makeText(this, kakaoNickName + "님 환영합니다", Toast.LENGTH_SHORT).show();
            kakaoNickView.setText(kakaoNickName);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        final ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
                        URL url = new URL(kakaoimage);
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



        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    //    if(isStart) {
            get_query();
            post_query();
        //    isStart = false;
      //  }
        context = Main2Activity.this;
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
            // Handle the camera action
        } else if (id == R.id.nav_exchange) {
            Intent intent = new Intent(Main2Activity.this, KakaoInputActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_write_content) {
            Intent intent = new Intent(Main2Activity.this, WriteContentActivity.class);
            startActivity(intent);
        }else if (id ==  R.id.nav_search_content){
                Intent intent = new Intent(Main2Activity.this, SearchPostActivity.class);
                startActivity(intent);
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(Main2Activity.this, MypageActivity.class);
            intent.putExtra("nickname", kakaoNickName);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        //포지션-> 마커의 위치
        markerOptions.position(SEOUL);
        //마커의 해당 제목
        markerOptions.title("동아시아");
        //title 아래 추가되는 텍스트 (snippet은 하나만 가능)
        markerOptions.snippet("총 10개의 글");
        /*
        markerOptions.snippet("한국 KOR");
        markerOptions.snippet("일본 JPY");
        markerOptions.snippet("중국 CNY");
        markerOptions.snippet("홍콩 HKD");
        */
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin));
        //마커 표시
        map.addMarker(markerOptions);
        //카메라 마커위치로 옮김
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(1));

        LatLng BTN = new LatLng(21, 105);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(BTN);
        markerOptions1.title("동남아시아");
        markerOptions1.snippet("총 5개의 글");
        /*
        markerOptions1.snippet("베트남 VND");
        markerOptions1.snippet("필리핀 PHP");
        markerOptions1.snippet("브루나이 BND");
        markerOptions1.snippet("싱가포르 SGD");
        markerOptions1.snippet("말레이시아 MYR");
        markerOptions1.snippet("인도네시아 IDR");
        */
        markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin));
        map.addMarker(markerOptions1);

        LatLng USA = new LatLng(40.6643, 73.9385);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(USA);
        markerOptions2.title("북아메리카");
        markerOptions2.snippet("총 3개의 글");
        /*
        markerOptions2.snippet("미국 USD");
        markerOptions2.snippet("캐나다 CAD");
        */
        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin));
        map.addMarker(markerOptions2);

        LatLng EUR = new LatLng(52.5234051, 14.411399);
        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(EUR);
        markerOptions3.title("유럽");
        markerOptions3.snippet("총 11개의 글");
        /*
        markerOptions3.snippet("유럽연합 EUR");
        markerOptions3.snippet("러시아 RUB");
        markerOptions3.snippet("스위스 CHF");
        */
        markerOptions3.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin));
        map.addMarker(markerOptions3);

        LatLng AUS = new LatLng(-35.3069444, 149.1242972);
        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(AUS);
        markerOptions4.title("오세아니아");
        markerOptions4.snippet("총 4개의 글");
        /*
        markerOptions4.snippet("호주 AUD");
        markerOptions4.snippet("뉴질랜드 NZD");
        */
        markerOptions4.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin));
        map.addMarker(markerOptions4);

        //마커의 말풍선 클릭시 다른 액티비티로 넘어감 (임시페이지 -> 수정필요!)
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(Main2Activity.this, MypageActivity.class);
                startActivity(intent);
            }
        });
    }


    public void onclick(View v){
        //시작 뷰 투명하게하기
        switch (v.getId()){
            case R.id.finishOverlay:
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_trans);
                linear.startAnimation(anim);
                //시작 뷰 종료
                ((ViewManager)linear.getParent()).removeView(linear);
                break;
            case R.id.detailSearch:
                //검색 페이지로 이동
                Intent intent = new Intent(Main2Activity.this, SearchActivity.class);
                startActivity(intent);
        }
    }


//시작뷰 밑으로 내리기
//    public void slideToBottom(View view){
//        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
//        animate.setDuration(500);
//        animate.setFillAfter(true);
//        view.startAnimation(animate);
//        view.setVisibility(View.GONE);
//    }

    public void get_query() {
        GetData getData = new GetData(Main2Activity.this);
        getData.execute("http://beaconplus.co.kr/dong_geo/overlay_query.php");
    }

    public void post_query(){
        //PostData postData = new PostData(Main2Activity.this);
       // postData.execute("http://beaconplus.co.kr/dong_geo/check_permission.php", "3");
    }


    public static Context getContext() {
        return context;
    }


}




