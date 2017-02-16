package com.example.orange.babygoodnight.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orange.babygoodnight.R;
import com.example.orange.babygoodnight.activity.ContentActivity;
import com.example.orange.babygoodnight.activity.PlayMusicActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Orange on 2017/1/3.
 */

public class PlayListFragment extends Fragment {
    private View view;
    private List<String> mDatas;
    private static int songPosition = -1;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.playlist_fragment_layout, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            initData();
            recyclerView.setAdapter(new MyViewAdapter());
            return view;
        }
        return view;
    }


    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            mDatas.add("Recycler View Item" + i);
        }
    }

    @Override
    public void onResume() {
        ContentActivity contentActivity = (ContentActivity) getActivity();
        int back_id = contentActivity.back_id;
        if (back_id != -1) {
            System.out.println("back_id:" + back_id);
            // TODO: 2017/1/10 根据返回值 标记recyclerview中哪一首歌被播放?
        }

        super.onResume();
    }

    class MyViewAdapter extends RecyclerView.Adapter<MyLittleViewHolder> {


        @Override
        public MyLittleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyLittleViewHolder myLittleViewHolder = new MyLittleViewHolder(LayoutInflater.from
                    (getContext()).inflate(R.layout.song_item, parent, false));
            return myLittleViewHolder;
        }

        @Override
        public void onBindViewHolder(MyLittleViewHolder holder, int position) {
            holder.song_Title.setText(ContentActivity.songBeenList.get(position).getTitle());
            holder.song_Description.setText(ContentActivity.songBeenList.get(position)
                    .getDescription());
            holder.song_imageView.setImageResource(ContentActivity.songBeenList.get(position)
                    .getImageUri());

        }

        @Override
        public int getItemCount() {
            return ContentActivity.songBeenList.size();
        }
    }

    private class MyLittleViewHolder extends RecyclerView.ViewHolder {
        TextView song_Title;
        TextView song_Description;
        LinearLayout song_root;
        CircleImageView song_imageView;

        public MyLittleViewHolder(View itemView) {
            super(itemView);
            song_Title = (TextView) itemView.findViewById(R.id.song_Title);
            song_Description = (TextView) itemView.findViewById(R.id.song_Description);
            song_root = (LinearLayout) itemView.findViewById(R.id.song_root);
            song_imageView = (CircleImageView) itemView.findViewById(R.id.song_circleImage);
            //给Item整个设置监听事件
            // TODO: 2017/1/8 给item设置点击效果
            song_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //判断之前的播放页面是否存在 如果存在则杀死掉 同时关掉service正在播放的歌曲
                    Intent hasActivity = new Intent();
                    hasActivity.setClassName(getContext().getPackageName(), "PlayMusicActivity");
                    if (getContext().getPackageManager().resolveActivity(hasActivity, 0) == null) {
                        Log.e("A", "不存在这个activity");
                    } else {
                        Log.e("A", "存在这个activity");
                    }


                    Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
                    ContentActivity activity = (ContentActivity) getActivity();
                    // Bundle bundle = new Bundle();
                    int position = getAdapterPosition();
                    //将点击的item的position已经contentactivity传递给播放页面

                    intent.putExtra("index", position);
                    intent.putExtra("iPlayer", activity.getiPlayer());

                    System.out.println(songPosition);

                    if (songPosition == -1 || songPosition != position) {
                        //set song resource
                        Log.e("A", "1");
                        intent.putExtra("noRefreshFlag", true);
                    }

                    if (songPosition == position) {
                        //打开的是相同页面 恢复页面播放器
                        intent.putExtra("noRefreshFlag", false);
                        Log.e("A", "2");
                    }

                    startActivity(intent);
                    songPosition = position;

                }
            });


        }
    }
}
