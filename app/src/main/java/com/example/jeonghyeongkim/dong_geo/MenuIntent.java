package com.example.jeonghyeongkim.dong_geo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class MenuIntent implements NavigationView.OnNavigationItemSelectedListener {

    public Context context;
    long kakao_id = KakaoSignupActivity.get_kakao_id();
    Handler handler = new Handler();

    public MenuIntent(Context context){
        this.context = context;
    }

    private JSONObject MakeJson(String kakao_id){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        try {
            jsonObject.put("kakao_id", kakao_id); //대학 2,3은 현재 NULL로 테스트,
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void setKakaoNickView(NavigationView navigationView) {
        final View headerView = navigationView.getHeaderView(0);
        TextView kakaoNickView = (TextView) headerView.findViewById(R.id.kakao_nick);
        if (KakaoSignupActivity.get_kakao_nickname() != null) {
            kakaoNickView.setText(KakaoSignupActivity.get_kakao_nickname());

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
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
                    } catch (Exception e) {

                    }
                }
            });

            t.start();
        } else {
            kakaoNickView.setText("로그인을 해주세요");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //햄버거바 메뉴 아이디 수정
        if (id == R.id.nav_main) {
            Intent intent = new Intent(context, Main2Activity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_exchange) {
            JSONObject jsonObject = MakeJson(String.valueOf(kakao_id));
            new PostData(context, jsonObject, false, null,null).execute("/load_id.php");
        } else if (id == R.id.nav_write_content) {
            Intent intent = new Intent(context, WriteContentActivity.class);
            context.startActivity(intent);
        }else if (id ==  R.id.nav_search_content){
            Intent intent = new Intent(context, SearchPostActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(context, MypageActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout)((Activity)context).findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
