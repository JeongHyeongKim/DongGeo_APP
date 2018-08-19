package com.example.jeonghyeongkim.dong_geo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        //Returning the current tabs
        switch (position){
            case 0:
                FragmentBefore before = new FragmentBefore();
                return before;
            case 1:
                FragmentIng ing = new FragmentIng();
                return ing;
            case 2:
                FragmentEnd end = new FragmentEnd();
                return end;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
