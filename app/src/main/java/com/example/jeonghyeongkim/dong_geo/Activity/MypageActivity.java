package com.example.jeonghyeongkim.dong_geo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeonghyeongkim.dong_geo.Callback.DonggeoDataCallback;
import com.example.jeonghyeongkim.dong_geo.DonggeoData;
import com.example.jeonghyeongkim.dong_geo.KaKao.KakaoSignupActivity;
import com.example.jeonghyeongkim.dong_geo.MenuIntent;
import com.example.jeonghyeongkim.dong_geo.HttpRequest.PostData;
import com.example.jeonghyeongkim.dong_geo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity
        implements View.OnClickListener{

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

    MenuIntent menuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=MypageActivity.this;

        menuIntent = new MenuIntent(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuIntent);
        menuIntent.setKakaoNickView(navigationView);

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
                MypageBuyFragment mypageBuyFragment = new MypageBuyFragment();
                transaction.replace(R.id.fragment_container, mypageBuyFragment);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                MypageSellFragment mypageSellFragment = new MypageSellFragment();
                transaction.replace(R.id.fragment_container, mypageSellFragment);
                transaction.commit();
                break;
            case 3:
                // '프래그먼트2' 호출
                MypageDealFragment mypageDealFragment = new MypageDealFragment();
                transaction.replace(R.id.fragment_container, mypageDealFragment);
                transaction.commit();
                break;
            case 4:
                // '프래그먼트2' 호출
                MypageEndFragment mypageEndFragment = new MypageEndFragment();
                transaction.replace(R.id.fragment_container, mypageEndFragment);
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
