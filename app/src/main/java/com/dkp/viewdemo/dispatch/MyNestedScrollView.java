package com.dkp.viewdemo.dispatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * 解决详情页太长嵌套RecycleView滑动冲突问题
 * Created by dkp on 2018/9/17.
 */

public class MyNestedScrollView extends NestedScrollView {
    private ScrollChangeListener listener;
    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null) {
            listener.onScrollChanged(l,t,oldl,oldt);
        }
    }

    public ScrollChangeListener getListener() {
        return listener;
    }

    public void setListener(ScrollChangeListener listener) {
        this.listener = listener;
    }

    public interface ScrollChangeListener {

        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

}
