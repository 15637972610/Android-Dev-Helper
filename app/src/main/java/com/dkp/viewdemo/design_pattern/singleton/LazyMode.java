package com.dkp.viewdemo.design_pattern.singleton;

/**
 * Created by dkp on 2019/5/19.
 * 懒汉式：双重校验 保证线程安全
 * 按需加载
 */

public class LazyMode {
    private volatile static LazyMode mLazyMode;

    private LazyMode(){

    }

    public LazyMode getInstance(){
        if (mLazyMode==null){
            synchronized (LazyMode.this){
                if (mLazyMode==null){
                    mLazyMode= new LazyMode();
                }
            }
        }
        return mLazyMode;
    }
}
