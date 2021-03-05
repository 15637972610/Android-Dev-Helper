package com.dkp.viewdemo.design_pattern.factory;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;

/**
 * Created by dkp on 2019/5/19.
 * 传入不同参数从而创建不同子类对象
 *
 * 将创建实例的工作与使用实例的工作分开，使用者不必关心类对象如何创建，实现了解耦；
 * 把初始化实例时的工作放到工厂里进行，使代码更容易维护。
 * 更符合面向对象的原则 & 面向接口编程，而不是面向实现编程。
 */

public class FactoryActivity extends Activity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //需要fragmentA的时候
        FragmentA fragmentA = (FragmentA) SimpleFactory.createFragmentYouNeed(1);

        //需要fragmentB的时候
        FragmentB fragmentB = (FragmentB) SimpleFactory.createFragmentYouNeed(1);

    }
}
