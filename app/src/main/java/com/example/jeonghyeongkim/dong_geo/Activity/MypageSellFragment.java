package com.example.jeonghyeongkim.dong_geo.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeonghyeongkim.dong_geo.Callback.DonggeoDataCallback;
import com.example.jeonghyeongkim.dong_geo.CardviewAdapter;
import com.example.jeonghyeongkim.dong_geo.DonggeoData;
import com.example.jeonghyeongkim.dong_geo.HttpRequest.PostData;
import com.example.jeonghyeongkim.dong_geo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MypageSellFragment extends Fragment { //삽니다

    private RecyclerView mCardview;
    private CardviewAdapter mAdapter;
    public static Context context;
    static public ArrayList<DonggeoData> data = new ArrayList<>();
    // private LinearLayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private int MAX_ITEM_COUNT = 50;

    public MypageSellFragment() {
        // Required empty public constructor
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        context = getActivity();
        Log.d("context!", String.valueOf(context));

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        mCardview = (RecyclerView)view.findViewById(R.id.recyclerview2);



        JSONObject jsonObject = MakeJson("0", "7");
        new PostData(context, jsonObject, false, new DonggeoDataCallback() {
            @Override
            public void onTaskDone(ArrayList<DonggeoData> donggeoData) {
                data = donggeoData;
                mCardview.setLayoutManager(mStaggeredGridLayoutManager);
                mAdapter = new CardviewAdapter(getContext(), data);
                mAdapter.setData(data);
                mCardview.setAdapter(mAdapter);
            }
        },null).execute("copy_view_mypage_buy");



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
