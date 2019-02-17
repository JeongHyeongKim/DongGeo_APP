package com.example.jeonghyeongkim.dong_geo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardviewAdapter extends RecyclerView.Adapter<CardviewViewHolder> {
    private ArrayList<DonggeoData> cardviewData;
    private Context mContext;
    private String buffer;

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
        final DonggeoData data = cardviewData.get(position);


        holder.currency.setText(data.getCurrency());
        holder.amount.setText(String.valueOf(data.getAmount()));
        holder.converted.setText(String.valueOf(data.getConverted()));
        holder.univertisty.setText(data.getUnivertisty());

        holder.cvItem.setOnClickListener(new View.OnClickListener() { // php서버에서 불러온 글 id가 필요할거같음. 그러므로 DonggeoData 클래스에 id값 필요할듯
            @Override
            public void onClick(View view) {

                new PostData(null, MakeJson(data.getId()), null, new StringDataCallback() {
                    @Override
                    public void onTaskDone(String non_parsing_result) {
                        buffer=non_parsing_result;
                        Log.d("raw_data_adaapter", buffer);
                        Intent intent = new Intent(mContext,DetailActivity.class);
                        intent.putExtra("raw_data",buffer);
                        mContext.startActivity(intent);
                    }
                }).execute("view_comment"); //php만든 후 입력 예정
                //mContext.startActivity(new Intent(mContext, DetailActivity.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return cardviewData.size();
    }

    private JSONObject MakeJson(String request_id){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        try {
            jsonObject.put("request_id", request_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}