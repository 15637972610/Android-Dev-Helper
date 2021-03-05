package com.dkp.viewdemo.dispatch;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2019/5/18.
 */

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean intercepted = false;
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        int mLastX,mLastY;
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                intercepted = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (true) {//父容器需要
//                    intercepted = true;
//                } else {
//                    intercepted = false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                intercepted = false;
//                break;
//            default:
//                break;
//
//        }
//        mLastX = x;
//        mLastY = y;
//        return intercepted;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int mLastX, mLastY;

        if (ev.getAction()==MotionEvent.ACTION_DOWN) {//不拦截ACTION_DOWN
            return false;
        }else{
            return true;
        }
    }
}
