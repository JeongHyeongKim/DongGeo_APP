package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private Context context;
    PostData postData;
    private String values = "";

    public TabPagerAdapter(FragmentManager fm, Context context, int tabCount) {
        super(fm);
        this.context = context;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        //Returning the current tabs
        switch (position){
            case 0:
                FragmentBefore before = new FragmentBefore();
                postData = new PostData(context, MakeJson(position));
                postData.execute("http://13.124.152.254/dong_geo/search_continent.php");
                return before;
            case 1:
                FragmentIng ing = new FragmentIng();
                postData = new PostData(context, MakeJson(position));
                postData.execute("http://13.124.152.254/dong_geo/search_continent.php");
                return ing;
            case 2:
                FragmentEnd end = new FragmentEnd();
                postData = new PostData(context, MakeJson(position));
                postData.execute("http://13.124.152.254/dong_geo/search_continent.php");
                return end;

            default:
                return null;
        }
    }

    private JSONObject MakeJson(int request_state){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        try {
            jsonObject.get("request_state");
            jsonObject.get("request_continent");
            Toast.makeText(context, String.valueOf(jsonObject.get("request_state")), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
