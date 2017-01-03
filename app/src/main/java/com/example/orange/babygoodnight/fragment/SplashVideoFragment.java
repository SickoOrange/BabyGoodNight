package com.example.orange.babygoodnight.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orange.babygoodnight.R;
import com.example.orange.babygoodnight.custumview.SplashVideoView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Orange on 2017/1/1.
 */

public class SplashVideoFragment extends Fragment {
    private SplashVideoView splashVideoView;
    private Uri uri = null;
    private ArrayList<Uri> uriList = new ArrayList<>();
    private boolean volumeMode=false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Uri uri_1 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R
                .raw.guide_1);
        Uri uri_2 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R
                .raw.guide_2);
        Uri uri_3 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R
                .raw.guide_3);
        uriList.add(uri_1);
        uriList.add(uri_2);
        uriList.add(uri_3);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (splashVideoView == null) {
            int index = getArguments().getInt("index");
            splashVideoView = new SplashVideoView(getContext());
            System.out.println("index:" + index);
            switch (index) {
                case 0:
                    uri = uriList.get(0);
                    volumeMode=false;
                    break;
                case 1:
                    uri = uriList.get(1);
                    volumeMode=false;
                    break;
                case 2:
                    uri = uriList.get(2);
                    volumeMode=false;
                    break;
                case 3:
                    //APP不是第一次启动
                    Random random = new Random();
                    int randomIndex = Math.abs(random.nextInt()) % 3;
                    uri = uriList.get(randomIndex);
                    volumeMode=true;
                    break;
                default:
                    break;
            }


        }
        splashVideoView.playVideo(uri,volumeMode);
        return splashVideoView;
    }
}
