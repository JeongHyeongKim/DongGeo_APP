package com.example.jeonghyeongkim.dong_geo;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
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
    String mJsonString;
    TextView user_count;
    TextView world_count;
    TextView request_count;
    android.support.v7.widget.Toolbar toolbar;
    LinearLayout linear;
    //시작 창
    Window win = getWindow();
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

        win.addContentView(linear, paramlinear);

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

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        get_query();
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

//    public void onClick(View v){
//        Intent intent;
//        switch (v.getId()){
//            case R.id.kakao_login_button:
//                Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }

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
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(Main2Activity.this, MypageActivity.class);
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







    private void showResult(){
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            JSONObject item = jsonArray.getJSONObject(0);
            String buffer_world_count=item.getString("world");
            String buffer_user_count=item.getString("user");
            String buffer_request_count=item.getString("request"); //json파싱 결과를 각 임시 변수에 삽입

            user_count.setText(buffer_user_count+"명의 사용자");
            world_count.setText(buffer_world_count+"개국");
            request_count.setText(buffer_request_count+"개의 게시글");




        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void get_query() {
        GetData getData = new GetData();
        getData.execute("http://beaconplus.co.kr/dong_geo/overlay_query.php");
    }




    private class GetData extends AsyncTask<String, Void, String> {
        String errorString = null;
        ProgressDialog progressDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Main2Activity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //one.setText(s);
            Log.d("overlay", "response  - " + s);

            if (s == null) {

                Toast.makeText(Main2Activity.this, "Fail", Toast.LENGTH_LONG);

            } else {

                mJsonString = s;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String serverURL = strings[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("phptest_request_overlay", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d("overlay_query", "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }

}
