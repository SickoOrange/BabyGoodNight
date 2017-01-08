package com.example.orange.babygoodnight.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.orange.babygoodnight.R;
import com.example.orange.babygoodnight.adapter.ContentVierPagerAdapter;
import com.example.orange.babygoodnight.bean.SongBean;
import com.example.orange.babygoodnight.custumview.HeaderVideoView;
import com.example.orange.babygoodnight.fragment.PlayListFragment;
import com.example.orange.babygoodnight.service.MyPlayServiceInterface;
import com.example.orange.babygoodnight.service.PlayService;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    public MyPlayServiceInterface iPlayer;
    private MyPlayServiceConnection myConnection;



    //保存的音乐信息bean 列表
    public static ArrayList<SongBean> songBeenList = new ArrayList<>();

    public MyPlayServiceInterface getiPlayer() {
        if (iPlayer != null) {
            return iPlayer;
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        //UtilsTool.setStatusBar(ContentActivity.this);
        playHeadView();
        //混合绑定音乐service 实现音乐播放
        bindMyMusicService();
        //初始化一些空间
        initSongBean();
        initView();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
    }

    private void initSongBean() {
        for (int i = 0; i < 20; i++) {
            SongBean songBean = new SongBean();
            songBean.setTitle("RWBY");
            songBean.setDescription("by Monty Oum for Rooster Teeth");
            songBean.setImageUri(R.drawable.rwby);
            songBean.setUri(Uri.parse("android.resource://" + this.getPackageName() + "/" + R
                    .raw.rwby));
            songBeenList.add(songBean);

        }

    }

    private void bindMyMusicService() {
        myConnection = new MyPlayServiceConnection();
        Intent intent = new Intent(ContentActivity.this, PlayService.class);
        startService(intent);
        bindService(intent, myConnection, BIND_AUTO_CREATE);
    }

    private void playHeadView() {
        HeaderVideoView splashVideoView = (HeaderVideoView) findViewById(R.id.header_Video);
        Uri uri_1 = Uri.parse("android.resource://" + getPackageName() + "/" + R
                .raw.header);
        splashVideoView.playVideo(uri_1, false);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getHeight() / 2) {
                    collapsingToolbarLayout.setTitle("侧耳倾听，细心聆听您的宝贝");
                } else {
                    collapsingToolbarLayout.setTitle("侧耳倾听，细心聆听您的宝贝 ");
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

    public class MyPlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iPlayer = (PlayService.MyBinder) iBinder;
            //iPlayer.callPlayMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(myConnection);
        super.onDestroy();
    }
}
