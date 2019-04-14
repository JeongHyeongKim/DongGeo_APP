package com.example.jeonghyeongkim.dong_geo.Callback;

import com.example.jeonghyeongkim.dong_geo.DonggeoData;

import java.util.ArrayList;

public interface DonggeoDataCallback {
    public void onTaskDone(ArrayList<DonggeoData> donggeoData);
    //콜백으로 원하는 뷰에다가 전달해줄거임!


}
