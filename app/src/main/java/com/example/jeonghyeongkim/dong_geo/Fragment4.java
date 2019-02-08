package com.example.jeonghyeongkim.dong_geo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.jeonghyeongkim.dong_geo.Fragment1.context;

public class Fragment4 extends Fragment { //끝이요

    private RecyclerView mCardview;
    private CardviewAdapter mAdapter;
    static public ArrayList<DonggeoData> data = new ArrayList<>();
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;



    public Fragment4() {
        // Required empty public constructor
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment4, container, false);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        mCardview = (RecyclerView)view.findViewById(R.id.recyclerview4);


        JSONObject jsonObject = MakeJson("0", "12");
        new PostData(context, jsonObject, new DonggeoDataCallback() {
            @Override
            public void onTaskDone(ArrayList<DonggeoData> donggeoData) {
                data = donggeoData;
                mCardview.setLayoutManager(mStaggeredGridLayoutManager);
                mAdapter = new CardviewAdapter(getContext(), data);
                mAdapter.setData(data);
                mCardview.setAdapter(mAdapter);
            }
        }).execute("view_mypage_sale");


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
