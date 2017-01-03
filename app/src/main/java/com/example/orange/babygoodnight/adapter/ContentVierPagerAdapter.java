package com.example.orange.babygoodnight.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * content 内容管理器
 * Created by Orange on 2017/1/3.
 */

public class ContentVierPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    public ContentVierPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
