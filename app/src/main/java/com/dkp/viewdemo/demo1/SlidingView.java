package com.dkp.viewdemo.demo1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by dkp on 2018/9/26.
 */

public class SlidingView extends View {
    private static final String TAG = "SlidingView";
    private int mScaledTouchSlop;
    private int mLastX=0;
    private int mLastY=0;

    public SlidingView(Context context) {
        super(context);
    }

    public SlidingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SlidingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext())
                .getScaledTouchSlop();
        Log.d(TAG, "sts:" + mScaledTouchSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX =x - mLastX;//x轴方向的移动距离
                int deltaY =y - mLastY;//y轴方向的移动距离
                Log.d(TAG,"move deltaX="+deltaX+"deltaY="+deltaY);
                int translationX = (int) (getTranslationX()+deltaX);
                int translationY = (int) (getTranslationY()+deltaY);
                setTranslationX(translationX);
                setTranslationY(translationY);

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX=x;
        mLastY=y;

        return true;
    }
}
