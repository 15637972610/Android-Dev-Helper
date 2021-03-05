package com.dkp.viewdemo.dispatch;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2019/5/18.
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        int mLastX,mLastY;
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                parent.requestDisallowIntercepterTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                //.....
//
//                if (true) {//父容器需要
//                    parent.requestDisallowIntercepterTouchEvent(false);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//
//        }
//        mLastX = x;
//        mLastY = y;
        return  super.dispatchTouchEvent(ev);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
