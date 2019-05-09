package com.dkp.viewdemo.demo_myattrs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.widget.TextView;import android.util.Log;

import com.dkp.viewdemo.R;

/**
 * Created by Administrator on 2019/5/8.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {


    public MyTextView(Context context) {
        super(context);
        init(context,null,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        String text = ta.getString(R.styleable.MyTextView_text);
        int mTextColor = ta.getColor(R.styleable.MyTextView_color, Color.BLACK);
        int mTextSize = ta.getDimensionPixelSize(R.styleable.MyTextView_size, 18);
        int mTextHeight = ta.getDimensionPixelSize(R.styleable.MyTextView_height, 40);
        int mTextWidth = ta.getDimensionPixelSize(R.styleable.MyTextView_width, 100);
        setText(text);
        setTextColor(mTextColor);
        setTextSize(mTextSize);
        setHeight(mTextHeight);
        setWidth(mTextWidth);
        ta.recycle();  //注意回收
        Log.v("MyTextView","text属性值:"+text);
        Log.v("MyTextView", "mTextColor属性值:"+mTextColor);
        Log.v("MyTextView", "mTextSize属性值:"+mTextSize);
        Log.v("MyTextView", "mTextHeight属性值:"+mTextHeight);
        Log.v("MyTextView", "mTextWidth属性值:"+mTextWidth);
    }

}
