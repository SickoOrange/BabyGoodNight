package com.example.orange.babygoodnight.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.orange.babygoodnight.R;
import com.example.orange.babygoodnight.adapter.SplashViewPagerAdapter;
import com.example.orange.babygoodnight.utils.UtilsTool;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ImageView> mDotsList;
    private int lastIndex;
    private Button start;
    private ViewPager viewPager;
    private LinearLayout dotsContainer;
    private boolean isFirstStart;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置statusbar 全透明 不要忘记设置APP NO Title
        //setStatusBar();
        UtilsTool.setStatusBar(MainActivity.this);
        //判断是否第一次启动APP， 如果第一次启动 则展示引导页 否则随机展示一个video
        sharedPreferences = getSharedPreferences("APP_DEFAUT_PREFERENCES",
                MODE_PRIVATE);
        isFirstStart = sharedPreferences.getBoolean("isFirstStart", true);

        initView();

    }

    private void initView() {
        start = (Button) findViewById(R.id.start_button);
        start.setOnClickListener(this);
        dotsContainer = (LinearLayout) findViewById(R.id.dotsContainer);
        ImageView dot_1 = (ImageView) findViewById(R.id.dot_1);
        ImageView dot_2 = (ImageView) findViewById(R.id.dot_2);
        ImageView dot_3 = (ImageView) findViewById(R.id.dot_3);
        dot_1.setSelected(true);
        mDotsList = new ArrayList<>();
        mDotsList.add(dot_1);
        mDotsList.add(dot_2);
        mDotsList.add(dot_3);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        //设置适配器
        if (isFirstStart) {
            System.out.println("第一次启动");
            sharedPreferences.edit().putBoolean("isFirstStart", false).apply();
            viewPager.setAdapter(new SplashViewPagerAdapter(getSupportFragmentManager(), 3));
            //设置Viewpager滑动监听
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int
                        positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mDotsList.get(position).setSelected(true);
                    mDotsList.get(lastIndex).setSelected(false);
                    lastIndex = position;
                    //如果到达最后一页 显示按钮 隐藏Dots
                    System.out.println(position);
                    if (position == 2) {
                        start.setVisibility(View.VISIBLE);
                        //dotsContainer.setVisibility(View.INVISIBLE);
                    } else {
                        start.setVisibility(View.INVISIBLE);
                        //dotsContainer.setVisibility(View.VISIBLE);
                    }


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            System.out.println("不是第一次启动");
            viewPager.setAdapter(new SplashViewPagerAdapter(getSupportFragmentManager(), 1));
            dotsContainer.setVisibility(View.INVISIBLE);
            start.setVisibility(View.VISIBLE);
            //给start按钮增加渐进动画效果
            AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
            alphaAnimation.setDuration(6000);
            start.setAnimation(alphaAnimation);
        }


    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                // TODO: 2017/1/1 跳转页面 并且增加跳转动画效果
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}
