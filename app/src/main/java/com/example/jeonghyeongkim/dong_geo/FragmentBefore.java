package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentBefore extends Fragment {

    private RecyclerView mCardview;
    private CardviewAdapter mAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private int MAX_ITEM_COUNT = 10;
    private static Context context;

    public FragmentBefore() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.before_fragment, container, false);

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        mCardview = (RecyclerView)view.findViewById(R.id.recyclerview);

        ArrayList<DonggeoData> data = new ArrayList<>();

        int i = 0;
        while (i < MAX_ITEM_COUNT) {
            data.add(new DonggeoData( "USD", i , i, "동덕여자대학교"));
            i++;
        }

        mCardview.setLayoutManager(mStaggeredGridLayoutManager);
        mAdapter = new CardviewAdapter(getContext(), data);
        mAdapter.setData(data);
        mCardview.setAdapter(mAdapter);

        return view;
    }


}
