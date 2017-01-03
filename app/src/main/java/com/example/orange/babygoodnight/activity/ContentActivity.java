package com.example.orange.babygoodnight.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.orange.babygoodnight.R;
import com.example.orange.babygoodnight.adapter.ContentVierPagerAdapter;
import com.example.orange.babygoodnight.custumview.HeaderVideoView;
import com.example.orange.babygoodnight.fragment.PlayListFragment;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        //UtilsTool.setStatusBar(ContentActivity.this);
        HeaderVideoView splashVideoView = (HeaderVideoView) findViewById(R.id.header_Video);
        Uri uri_1 = Uri.parse("android.resource://" + getPackageName() + "/" + R
                .raw.header);
        splashVideoView.playVideo(uri_1, false);
        initView();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));


    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);
        AppBarLayout appBarLayout= (AppBarLayout) findViewById(R.id.appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getHeight() / 2) {
                    collapsingToolbarLayout.setTitle("倾耳倾听，细心倾听您的宝贝");
                } else {
                    collapsingToolbarLayout.setTitle("倾耳倾听，细心倾听您的宝贝 ");
                }
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.content_viewpager);
        initFragmentDataList(3);
        viewPager.setAdapter(new ContentVierPagerAdapter(getSupportFragmentManager(),
                mFragmentList));
        tabLayout.setupWithViewPager(viewPager);
        setTabIconAndTitle();
    }

    private void initFragmentDataList(int tabCount) {
        for (int i = 0; i < tabCount; i++) {
            mFragmentList.add(new PlayListFragment());
        }
    }

    private void setTabIconAndTitle() {

        int tabCount = tabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            switch (i) {
                case 0:
                    tabLayout.getTabAt(0).setText("睡眠音乐");
                    break;
                case 1:
                    tabLayout.getTabAt(1).setText("倾耳倾听");
                    break;
                case 2:
                    tabLayout.getTabAt(2).setText("睡眠追踪");
                    break;

            }
        }
    }
}
