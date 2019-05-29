package com.dkp.viewdemo.design_pattern.factory;

/**
 * Created by dkp on 2019/5/19.
 * 简单工厂模式
 */

public class SimpleFactory {

    public static BaseFragment createFragmentYouNeed(int whichType) {
        switch (whichType) {
            case 1:
                return new FragmentA();
            case 2:
                return new FragmentB();

            default:
                return new BaseFragment();
        }
    }

}
