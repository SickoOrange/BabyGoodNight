package com.example.orange.babygoodnight.custumview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Orange on 2017/1/2.
 */

public class HeaderVideoView extends BaseVideoView {
    public HeaderVideoView(Context context) {
        super(context);
    }

    public HeaderVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize
                (heightMeasureSpec));
    }
}
