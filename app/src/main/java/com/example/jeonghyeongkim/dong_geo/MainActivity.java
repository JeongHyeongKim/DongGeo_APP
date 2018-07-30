package com.example.jeonghyeongkim.dong_geo;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jeonghyeongkim.dong_geo.Exchange.Exchange_Query_manana;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    Button button;
    android.support.v7.widget.Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private Button btnShowNavigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.manana);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //drawer = new DrawerBuilder().withActivity(this).withToolbar(toolbar).build();

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        //navigationView = (NavigationView) findViewById(R.id.navigationView);

    }

    public void manana_Clicked(View v){
        Intent intent = new Intent(MainActivity.this, Exchange_Query_manana.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        //포지션-> 마커의 위치
        markerOptions.position(SEOUL);
        //마커의 해당 제목
        markerOptions.title("서울");
        //title 아래 추가되는 텍스트
        markerOptions.snippet("KOR");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.flagb));
        //마커 표시
        map.addMarker(markerOptions);
        //카메라 마커위치로 옮김
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(1));

        LatLng USA = new LatLng(40.6643, 73.9385);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(USA);
        markerOptions1.title("미국");
        markerOptions1.snippet("USD");
        markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.flagb));
        map.addMarker(markerOptions1);

        LatLng EUR = new LatLng(52.5234051, 14.411399);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(EUR);
        markerOptions2.title("유럽연합");
        markerOptions2.snippet("EUR");
        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.flag));
        map.addMarker(markerOptions2);

        Log.i("asdf1", "asdf1");

    }


}
