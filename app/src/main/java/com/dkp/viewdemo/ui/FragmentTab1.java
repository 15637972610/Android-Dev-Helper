package com.dkp.viewdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkp.viewdemo.R;
import com.dkp.viewdemo.design_pattern.factory.BaseFragment;
import com.dkp.viewdemo.widget.tab.HomeTabView;
import com.dkp.viewdemo.widget.tab.IMainTabInterface;

/**
 * Created by Administrator on 2019/5/19.
 */

public class FragmentTab1 extends BaseFragment implements IMainTabInterface, HomeTabView.ChangeFragmentCallback {
    @Override
    public void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onUserLoginChanged (boolean var1, String var2){

    }

    @Override
    public void onNetWorkStateChanged (boolean var1, boolean var2){

    }

    @Override
    public void onSwitchFragmentAgain (IMainTabInterface var1){

    }

    @Override
    public void onSwitchFragment (IMainTabInterface var1){

    }

    @Override
    public boolean getExistGuide (){
        return false;
    }

    @Override
    public void onChangeFragment (IMainTabInterface fragment){

    }

    @Override
    public void onUpdateCurrentFragment (IMainTabInterface currentFragment){

    }

    @Override
    public int bindLayout (){
        return 0;
    }
}
