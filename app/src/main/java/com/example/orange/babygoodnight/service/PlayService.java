package com.example.orange.babygoodnight.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.orange.babygoodnight.activity.PlayMusicActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 服务，用于初始化音频播放 以及一些相关的操作
 * Created by Orange on 2017/1/8.
 */

public class PlayService extends Service {

    private MediaPlayer mediaPlayer;
    private Uri uri;
    private Timer timer;
    MyBinder myBinder;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (myBinder != null) {
            return myBinder;
        }
        myBinder=new MyBinder();
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        //此方法在start以及bind service的时候都会启动
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //音乐播放完毕自动循环
                System.out.println("onCompletion");
                Message message = Message.obtain();

                //歌曲结束
                message.arg1 = 1;
                PlayMusicActivity.handler.sendMessage(message);
                if (timer != null) {
                    timer.cancel();
                }
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                System.out.println("onPrepared");
                //每次设置loop只会循环一次 所以每次在准备阶段就要让它循环下去

                //mediaPlayer.setLooping(true);
            }
        });
        mediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(MediaPlayer mediaPlayer, TimedText timedText) {
                System.out.println("TimedText");
                System.out.println("text>" + timedText.getText());
            }
        });


        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void playMusic() {
        mediaPlayer.start();
        updateSeekBar();
    }

    public void rePlayMusic() {
        mediaPlayer.start();
    }

    private void updateSeekBar() {
        timer = new Timer();
        final int duration = mediaPlayer.getDuration();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int currentPosition = mediaPlayer.getCurrentPosition();
                // boolean isPlaying = mediaPlayer.isPlaying();
                Message message = Message.obtain();
                message.arg1 = 0;
                Bundle bundle = new Bundle();
                bundle.putIntArray("params", new int[]{currentPosition, duration});
                //bundle.putBoolean("isPlaying", isPlaying);
                message.setData(bundle);
                PlayMusicActivity.handler.sendMessage(message);
            }
        };
        timer.schedule(task, 300, 1000);
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }



    public void setMusicSourcePath(Uri uri) {
        this.uri = uri;
    }

    public void setSeekToPosition(int progress) {

        mediaPlayer.seekTo(progress);
    }

    public class MyBinder extends Binder implements MyPlayServiceInterface {

        @Override
        public void callPlayMusic() {
                playMusic();
        }


        @Override
        public void callPauseMusic() {
            pauseMusic();
        }


        @Override
        public void callFinishMusic() {
            finishMusic();
        }

        @Override
        public void callRePlayMusic() {
            rePlayMusic();
        }

        @Override
        public void callSetMusicSourcePath(Uri path) {
            mediaPlayer.reset();
            setMusicSourcePath(path);
            try {
                mediaPlayer.setDataSource(getApplicationContext(),uri);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void callSetSeekToPosition(int progress) {
            setSeekToPosition(progress);

        }

        @Override
        public int callGetCurrentDuration() {
            return mediaPlayer.getCurrentPosition();
        }

        @Override
        public int callGetDuration() {
            return mediaPlayer.getDuration();
        }

        @Override
        public boolean isPlaying(){
            return mediaPlayer.isPlaying();
        }


    }

    private void finishMusic() {
        //mediaPlayer.reset();
        mediaPlayer.stop();
        uri = null;
        if (timer != null) {
            timer.cancel();
        }
    }

}
