package com.example.orange.babygoodnight.service;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Orange on 2017/1/8.
 */

public interface MyPlayServiceInterface extends Serializable {
    void callPlayMusic();

    void callPauseMusic();


    void callFinishMusic();

    void callRePlayMusic();

    void callSetMusicSourcePath(Uri path);

    void callSetSeekToPosition(int progress);
}
