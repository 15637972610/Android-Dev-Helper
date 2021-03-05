package com.dkp.viewdemo.design_pattern.proxy;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;

/**
 * Created by dkp on 2019/5/19.
 *
 * 代理模式演示使用
 * 场景：小黄同学想买娃娃做XX的事情，但是呢不想被发现，于是老王帮他。
 *
 * 1.创建抽象接口IProxyDothings
 * 2.创建真实类小黄 xiaohuang.java
 * 3.创建代理类老王 laowang.java
 * 4.让老王帮小黄买娃娃
 *
 * 老王买了，但是老王没忍住dosomethings了
 *
 */

public class ProxyDemoActivity extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //4.让老王帮小黄买娃娃
        LaoWang laoWang= new LaoWang();
        laoWang.buyWaWa();
    }
}
