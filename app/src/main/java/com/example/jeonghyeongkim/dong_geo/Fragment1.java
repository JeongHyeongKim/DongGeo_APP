package com.example.jeonghyeongkim.dong_geo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class Fragment1 extends Fragment {

    private RecyclerView mCardview;
    private CardviewAdapter mAdapter;
   // private LinearLayoutManager mLayoutManager;
    private  StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    public static Context context;
    static public ArrayList<DonggeoData> data = new ArrayList<>();
    public JSONArray buffer;


    public Fragment1() {
        // Required empty public constructor
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        context = getActivity();
        Log.d("context!", String.valueOf(context));
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        long kakao_id = KakaoSignupActivity.get_kakao_id();
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        mCardview = (RecyclerView)view.findViewById(R.id.recyclerview);


        JSONObject jsonObject = MakeJson("0", "5");
        new PostData(context, jsonObject, new DonggeoDataCallback() {
            @Override
            public void onTaskDone(ArrayList<DonggeoData> donggeoData) {
                data = donggeoData;
                mCardview.setLayoutManager(mStaggeredGridLayoutManager);
                mAdapter = new CardviewAdapter(getContext(), data);
                mAdapter.setData(data);
                mCardview.setAdapter(mAdapter);
            }
        },null).execute("view_mypage_sale");


        return view;

    }


    private JSONObject MakeJson(String request_state, String user_id){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        try {
            jsonObject.put("request_state", request_state);
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }




}
