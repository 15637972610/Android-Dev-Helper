package com.dkp.viewdemo.design_pattern.factory;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2019/5/19.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doSomething();
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(bindLayout(), container, false);
    }
    public abstract int bindLayout();


    private void doSomething() {

    }
}
