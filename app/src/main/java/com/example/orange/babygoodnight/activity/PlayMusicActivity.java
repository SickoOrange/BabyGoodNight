package com.example.orange.babygoodnight.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.orange.babygoodnight.R;
import com.example.orange.babygoodnight.service.MyPlayServiceInterface;
import com.example.orange.babygoodnight.utils.UtilsTool;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar play_activity_toolbar;
    private static ImageView btn_play;
    private ImageView btn_next;
    private ImageView btn_prev;
    private ImageView btn_loop;
    private ImageView btn_listen;
    private CircleImageView music_image;
    private ImageView music_image_wrapper;
    private TextView tv_play_time_start;
    private TextView tv_play_time_end;
    private static SeekBar seekBar;
    private static int position = 0;
    private static int duration = 0;
    private MyPlayServiceInterface iPlayer;
    private static boolean isPlaying = false;

    public static Handler handler = new Handler(new Handler.Callback() {


        @Override
        public boolean handleMessage(Message message) {
            switch (message.arg1) {
                case 0:
                    //更新进度条
                    Bundle data = message.getData();
                    int[] musicParams = data.getIntArray("params");
                    currentPosition = musicParams[0];
                    duration = musicParams[1];
                    //isPlaying = data.getBoolean("isPlaying");
                    seekBar.setProgress(currentPosition);
                    seekBar.setMax(duration);
                    break;
                case 1:
                    //音乐播放完毕自动循环
                    isPlaying = false;
                    btn_play.setImageResource(R.drawable.play_btn_play_selector);
                    //进度条清空
                    // seekBar.setProgress(0);
                    break;
            }
            return false;
        }
    });

    private static int currentPosition;
    private boolean refreshIcon;
    private int refreshCurrenturation;
    private int refreshDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        UtilsTool.setStatusBar(PlayMusicActivity.this);
        Intent sourceIntent = getIntent();
        Log.e("a", "aaaaaaa");
        //获取需要播放的音乐的position跟执行播放的代理
        position = sourceIntent.getIntExtra("index", -1);
        Serializable type = sourceIntent.getSerializableExtra("iPlayer");
        if (type instanceof MyPlayServiceInterface) {
            iPlayer = (MyPlayServiceInterface) type;
        }

        initView();
    }

    private void initView() {
        play_activity_toolbar = (Toolbar) findViewById(R.id.play_activity_toolbar);
        play_activity_toolbar.setTitle("hello wolrd");
        setSupportActionBar(play_activity_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //初始化控件
        btn_play = (ImageView) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(this);
        btn_next = (ImageView) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_prev = (ImageView) findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(this);
        btn_loop = (ImageView) findViewById(R.id.btn_loop);
        btn_loop.setOnClickListener(this);
        btn_listen = (ImageView) findViewById(R.id.btn_listen);
        btn_listen.setOnClickListener(this);

        music_image = (CircleImageView) findViewById(R.id.music_image);
        music_image_wrapper = (ImageView) findViewById(R.id.music_image_wrapper);

        tv_play_time_start = (TextView) findViewById(R.id.tv_play_time_start);
        tv_play_time_end = (TextView) findViewById(R.id.tv_play_time_end);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        //seekBar.setProgress(0);
        // seekBar.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //// TODO: 2017/1/8 拖动进度条 可以选择music的播放时间点
                int progress = seekBar.getProgress();
                iPlayer.callSetSeekToPosition(progress);


            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
               /* if (!isPlaying) {
                    //判断是否已经播放后暂停了
                    Log.e("A",""+seekBar.getProgress());
                    if (seekBar.getProgress() > 1&&seekBar.getProgress()!=1000) {
                        //恢复播放
                        Log.e("A","恢复播放");
                        iPlayer.callRePlayMusic();
                        isPlaying = true;
                        btn_play.setImageResource(R.drawable.play_btn_pause_selector);
                    } else {
                        //设置数据源给service
                        Log.e("A","设置新数据源");
                        Uri uri = ContentActivity.songBeenList.get(position).getUri();
                        getSupportActionBar().setTitle(ContentActivity.songBeenList.get(0).getTitle());
                        getSupportActionBar().setSubtitle(ContentActivity.songBeenList.get(0).getDescription());
                        iPlayer.callSetMusicSourcePath(uri);
                        iPlayer.callPlayMusic();
                        isPlaying = true;
                        // seekBar.setEnabled(true);
                        btn_play.setImageResource(R.drawable.play_btn_pause_selector);
                        // TODO: 2017/1/8 动画旋转问题， 时间同步问题
                        music_image.setImageResource(ContentActivity.songBeenList.get(position).getImageUri());
                        UtilsTool.setMusicImageAnimation(music_image);
                        UtilsTool.setMusicImageAnimation(music_image_wrapper);
                    }

                } else {
                    //正在播放音乐
                    Log.e("A","暂停播放");
                    iPlayer.callPauseMusic();
                    isPlaying = false;
                    btn_play.setImageResource(R.drawable.play_btn_play_selector);


                }*/
                if (iPlayer.isPlaying()) {
                    iPlayer.callPauseMusic();
                    btn_play.setImageResource(R.drawable.play_btn_play_selector);
                } else {
                    if (seekBar.getProgress() >= 0 && seekBar.getProgress() < refreshDuration) {
                        iPlayer.callSetSeekToPosition(seekBar.getProgress());
                    }
                    iPlayer.callPlayMusic();
                    btn_play.setImageResource(R.drawable.play_btn_pause_selector);
                }

                break;

            case R.id.btn_next:
                position+=1;
                System.out.println("position="+position);
                // TODO: 2017/1/8 注意歌曲越界的问题
                Uri uri = ContentActivity.songBeenList.get(position).getUri();
                getSupportActionBar().setTitle(ContentActivity.songBeenList.get(0).getTitle());
                getSupportActionBar().setSubtitle(ContentActivity.songBeenList.get(0).getDescription());
                iPlayer.callSetMusicSourcePath(uri);
                iPlayer.callPlayMusic();
                btn_play.setImageResource(R.drawable.play_btn_pause_selector);
                break;

            case R.id.btn_prev:
                // TODO: 2017/1/8  
                break;

            case R.id.btn_loop:
                //// TODO: 2017/1/8
                break;

            case R.id.btn_listen:
                // TODO: 2017/1/8  
                break;

            case android.R.id.home:
                break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(PlayMusicActivity.this, ContentActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        //super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        refreshIcon = iPlayer.isPlaying();


        // System.out.println(refreshIcon+"  "+refreshCurrenturation+"  "+ refreshDuration);
        boolean RefreshFlag = getIntent().getBooleanExtra("RefreshFlag", false);
        if (RefreshFlag) {
            //恢复界面
            Log.e("A", "3");

            getSupportActionBar().setTitle(ContentActivity.songBeenList.get(0).getTitle());
            getSupportActionBar().setSubtitle(ContentActivity.songBeenList.get(0).getDescription());
            // TODO: 2017/1/9 恢复时间标签的显示

            if (refreshIcon) {
                btn_play.setImageResource(R.drawable.play_btn_pause_selector);
            } else {
                btn_play.setImageResource(R.drawable.play_btn_play_selector);
            }
        } else {
            //重新设置数据源 播放新的歌在button中做
            Log.e("A", "4");
            iPlayer.callFinishMusic();

            Uri uri = ContentActivity.songBeenList.get(position).getUri();
            getSupportActionBar().setTitle(ContentActivity.songBeenList.get(0).getTitle());
            getSupportActionBar().setSubtitle(ContentActivity.songBeenList.get(0).getDescription());
            iPlayer.callSetMusicSourcePath(uri);
            btn_play.setImageResource(R.drawable.play_btn_play_selector);
        }

        refreshCurrenturation = iPlayer.callGetCurrentDuration();
        refreshDuration = iPlayer.callGetDuration();
        seekBar.setProgress(refreshCurrenturation);
        seekBar.setMax(refreshDuration);
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
