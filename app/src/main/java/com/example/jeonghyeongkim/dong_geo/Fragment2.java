package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fragment2 extends Fragment { //삽니다

    private RecyclerView mCardview;
    private CardviewAdapter mAdapter;
    public static Context context;
    // private LinearLayoutManager mLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private int MAX_ITEM_COUNT = 50;

    public Fragment2() {
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

        ArrayList<DonggeoData> data = new ArrayList<>();

        int i = 0;
        while (i < MAX_ITEM_COUNT) {
            data.add(new DonggeoData( "USD", 1 , 1, "동덕여자대학교","1"));
            i++;
        }
        // mLayoutManager = new LinearLayoutManager(getContext());
        mCardview.setLayoutManager(mStaggeredGridLayoutManager);
        mAdapter = new CardviewAdapter(getContext(), data);
        mAdapter.setData(data);
        mCardview.setAdapter(mAdapter);


        return view;
    }


}
