package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class CardviewAdapter extends RecyclerView.Adapter<CardviewViewHolder> {
    private ArrayList<DonggeoData> cardviewData;
    private Context mContext;

    public void setData(ArrayList<DonggeoData> list){
        cardviewData = list;
    }

    public CardviewAdapter(Context mContext, ArrayList<DonggeoData> cardviewData) {
        this.mContext = mContext;
        this.cardviewData = cardviewData;

    }

    @Override
    public CardviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

// 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);

        CardviewViewHolder holder = new CardviewViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CardviewViewHolder holder, int position) {
        DonggeoData data = cardviewData.get(position);

        holder.currency.setText(data.getCurrency());
        holder.amount.setText(String.valueOf(data.getAmount()));
        holder.converted.setText(String.valueOf(data.getConverted()));
        holder.univertisty.setText(data.getUnivertisty());

        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, DetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardviewData.size();
    }

}
