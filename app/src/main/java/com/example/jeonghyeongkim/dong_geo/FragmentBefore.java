package com.example.jeonghyeongkim.dong_geo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentBefore extends Fragment {

    private RecyclerView mCardview;
    private CardviewAdapter mAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private int MAX_ITEM_COUNT = 0;
    public static Context context;
    String continent_currency;
    String continent_amount;
    String continent_uni1;
    JSONArray jsonArray;

    public FragmentBefore(){
        super();
    }
    @SuppressLint("ValidFragment")
    public FragmentBefore(JSONArray jsonArray) {
        super();
        this.jsonArray = jsonArray;
        Log.d("Tab5", String.valueOf(jsonArray));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.before_fragment, container, false);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        mCardview = (RecyclerView)view.findViewById(R.id.recyclerview);

        ArrayList<DonggeoData> data = new ArrayList<>();

        context = getActivity().getApplicationContext();
        int i = 0;
        try {
            MAX_ITEM_COUNT = jsonArray.length();
            while (i < MAX_ITEM_COUNT) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                continent_currency = jsonObject1.getString("currency");
                continent_amount = jsonObject1.getString("amount");
                continent_uni1 = jsonObject1.getString("uni1");
                data.add(n`ew DonggeoData( continent_currency, Integer.parseInt(continent_amount), Integer.parseInt(continent_amount), continent_uni1));
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mCardview.setLayoutManager(mStaggeredGridLayoutManager);
        mAdapter = new CardviewAdapter(getContext(), data);
        mAdapter.setData(data);
        mCardview.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
    public Context getContext() {
        return context;
    }
}
