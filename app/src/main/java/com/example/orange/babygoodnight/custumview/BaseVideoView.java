package com.example.orange.babygoodnight.custumview;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Orange on 2017/1/2.
 */

public class BaseVideoView extends VideoView {
    public BaseVideoView(Context context) {
        super(context);
    }

    public BaseVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void playVideo(Uri uri, final boolean volumeMode) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri can not be null");
        }

        setVideoURI(uri);
        start();
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                if (!volumeMode) {
                    mp.setVolume(0f, 0f);
                }
            }
        });
        setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });

    }
}
