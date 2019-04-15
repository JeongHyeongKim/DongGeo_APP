package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.example.jeonghyeongkim.dong_geo.Activity.SearchBuyFragment;
import com.example.jeonghyeongkim.dong_geo.Activity.SearchDealFragment;
import com.example.jeonghyeongkim.dong_geo.Activity.SearchEndFragment;
import com.example.jeonghyeongkim.dong_geo.HttpRequest.GetData;
import com.example.jeonghyeongkim.dong_geo.HttpRequest.PostData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    public static Context context;
    PostData postData;
    GetData getData;
    String state = "";
    String continent = "";
    private String values = "";
    JSONArray jsonArray;

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
                SearchBuyFragment before = new SearchBuyFragment(jsonArray);
//                getData = new GetData(context);
//                state = ((SearchActivity) context).state;
//                continent = ((SearchActivity) context).continent;
//                getData.execute("http://13.124.152.254/dong_geo/search_continent.php?request_state=" + state + "&request_continent=" + continent);
                return before;
            case 1:
                SearchDealFragment ing = new SearchDealFragment();
//                postData = new PostData(context, MakeJson());
//                postData.execute("http://13.124.152.254/dong_geo/search_continent.php");
                return ing;
            case 2:
                SearchEndFragment end = new SearchEndFragment();
//                postData = new PostData(context, MakeJson());
//                postData.execute("http://13.124.152.254/dong_geo/search_continent.php");
                return end;

            default:
                return null;
        }
    }

    private JSONObject MakeJson(){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        try {
            jsonObject.get("state");
            jsonObject.get("continent");
            Toast.makeText(context, String.valueOf(jsonObject.get("state")), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        Log.d("Tab2", String.valueOf(jsonArray));
    }
}
