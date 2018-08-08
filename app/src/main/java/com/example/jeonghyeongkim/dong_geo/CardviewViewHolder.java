package com.example.jeonghyeongkim.dong_geo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CardviewViewHolder extends RecyclerView.ViewHolder{

    public TextView currency;
    public TextView amount;
    public TextView converted;
    public TextView univertisty;


    public CardviewViewHolder(View itemView) {
        super(itemView);
        currency = (TextView) itemView.findViewById(R.id.currency);
        amount = (TextView) itemView.findViewById(R.id.amount);
        converted = (TextView) itemView.findViewById(R.id.converted);
        univertisty = (TextView) itemView.findViewById(R.id.university);
    }

}