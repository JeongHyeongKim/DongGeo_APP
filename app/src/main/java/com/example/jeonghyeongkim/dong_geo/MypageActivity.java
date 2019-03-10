package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private static Context context;
    String kakao_id = String.valueOf(KakaoSignupActivity.get_kakao_id()); //세션에서 로그인 일련번호 가져오기.
    TextView search_id;

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;
    private final int FRAGMENT4 = 4;

    private Button bt_tab1, bt_tab2, bt_tab3, bt_tab4;

    Intent intent;

    TextView kakaonic;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=MypageActivity.this;

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
            kakaonic.setText(KakaoSignupActivity.get_kakao_nickname());

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

        // 위젯에 대한 참조
        bt_tab1 = (Button)findViewById(R.id.bt_tab1_sell);
        bt_tab2 = (Button)findViewById(R.id.bt_tab2_buy);
        bt_tab3 = (Button)findViewById(R.id.bt_tab3_ing);
        bt_tab4 = (Button)findViewById(R.id.bt_tab4_finish);

        // 탭 버튼에 대한 리스너 연결
        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);
        bt_tab3.setOnClickListener(this);
        bt_tab4.setOnClickListener(this);
        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        callFragment(FRAGMENT1);

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
            Intent intent = new Intent(MypageActivity.this, Main2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exchange) {
            Intent intent = new Intent(MypageActivity.this, KakaoInputActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_write_content) {
            Intent intent = new Intent(MypageActivity.this, WriteContentActivity.class);
            startActivity(intent);
        } else if (id ==  R.id.nav_search_content){
                Intent intent = new Intent(MypageActivity.this, SearchPostActivity.class);
                startActivity(intent);

        } else if (id == R.id.nav_mypage) {
//            Intent intent = new Intent(MypageActivity.this, MypageActivity.class);
//            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(MypageActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onTerm(View view) {
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_tab1_sell :
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                callFragment(FRAGMENT1);
                break;

            case R.id.bt_tab2_buy :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT2);
                break;

            case R.id.bt_tab3_ing :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT3);
                break;
            case R.id.bt_tab4_finish :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT4);
                break;
        }

    }
    private void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
            case 1:
                // '프래그먼트1' 호출
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;
            case 3:
                // '프래그먼트2' 호출
                Fragment3 fragment3 = new Fragment3();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.commit();
                break;
            case 4:
                // '프래그먼트2' 호출
                Fragment4 fragment4 = new Fragment4();
                transaction.replace(R.id.fragment_container, fragment4);
                transaction.commit();
                break;
        }

    }

    private JSONObject MakeJson(String kakao_id){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        try {
            //jsonObject.put("kakao_nickname", kakao_nickname);
            jsonObject.put("kakao_id", kakao_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    //카카오 이메일 불러오기
    //public void post_query() {
     //   PostData postData = new PostData(MypageActivity.this, MakeJson(kakao_id), new DonggeoDataCallback() {
      //      @Override
       //     public void onTaskDone() {
        //    }
        //});
       // postData.execute("/load_id.php");
    //}
    public void check_permission(long id) {

        if (String.valueOf(id) == null) {
            Intent intent = new Intent(MypageActivity.this,KakaoSignupActivity.class); // 로그인 페이지 이동 이거였나...??
        } else {
            new PostData(MypageActivity.this, MakeJson(kakao_id), false, new DonggeoDataCallback() {
                @Override
                public void onTaskDone(ArrayList<DonggeoData> donggeoData) { //식별 아이디 만들지 않았을 때 동작!!!

                }
            },null).execute("load_id2"); //php만든 후 입력 예정
        } //
    }

    public static Context getContext() {
        return context;
    }
}
