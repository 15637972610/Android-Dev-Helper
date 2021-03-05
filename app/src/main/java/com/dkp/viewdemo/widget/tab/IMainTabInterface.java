package com.dkp.viewdemo.widget.tab;

/**
 * @author: dukunpeng3
 * @date: 2021/3/5 4:26 PM
 * @description:
 */
public interface IMainTabInterface {
    void onUserLoginChanged(boolean var1, String var2);

    void onNetWorkStateChanged(boolean var1, boolean var2);

    void onSwitchFragmentAgain(IMainTabInterface var1);

    void onSwitchFragment(IMainTabInterface var1);

    boolean getExistGuide();
}
