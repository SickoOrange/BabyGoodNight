package com.example.orange.babygoodnight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orange.babygoodnight.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 2017/1/3.
 */

public class PlayListFragment extends Fragment {
    private View view;
    private List<String> mDatas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.playlist_fragment_layout, container, false);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
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

    class MyViewAdapter extends RecyclerView.Adapter<MyLittleViewHolder> {


        @Override
        public MyLittleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyLittleViewHolder myLittleViewHolder = new MyLittleViewHolder(LayoutInflater.from
                    (getContext()).inflate(R.layout.playlist_item, parent, false));
            return myLittleViewHolder;
        }

        @Override
        public void onBindViewHolder(MyLittleViewHolder holder, int position) {
            holder.playlist_item_tv.setText(mDatas.get(position));

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    private class MyLittleViewHolder extends RecyclerView.ViewHolder {
        TextView playlist_item_tv;

        public MyLittleViewHolder(View itemView) {
            super(itemView);
            playlist_item_tv = (TextView) itemView.findViewById(R.id.playlist_item_tv);
            playlist_item_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("ssssss");
                }
            });
        }
    }
}
