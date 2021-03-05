package com.dkp.viewdemo.demo1;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dkp.viewdemo.R;

/**
 * Created by dkp on 2018/9/26.
 * 实现图片跟着手指滑动
 */

public class Demo1Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
    }
}
