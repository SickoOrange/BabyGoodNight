package com.example.orange.babygoodnight.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.orange.babygoodnight.fragment.SplashVideoFragment;

import java.util.ArrayList;

/**
 * Created by Orange on 2017/1/1.
 */

public class SplashViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<SplashVideoFragment> mFragmentlist;

    public SplashViewPagerAdapter(FragmentManager fm, int Count) {
        super(fm);
        mFragmentlist = new ArrayList<SplashVideoFragment>();
        if (Count==3) {
            for (int i = 0; i < Count; i++) {
                SplashVideoFragment splashVideoFragment = new SplashVideoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("index", i);
                splashVideoFragment.setArguments(bundle);
                mFragmentlist.add(splashVideoFragment);
            }
        }else {
            SplashVideoFragment splashVideoFragment = new SplashVideoFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", 3);
            splashVideoFragment.setArguments(bundle);
            mFragmentlist.add(splashVideoFragment);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentlist.size();
    }
}
