package com.example.orange.babygoodnight.bean;

import android.net.Uri;

/**
 * 保存歌曲信息的bean对象
 * Created by Orange on 2017/1/8.
 */

public class SongBean {
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }

    private String Title;
    private String Description;
    private Uri uri;
    private int imageUri;

}
