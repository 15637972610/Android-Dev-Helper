package com.dkp.viewdemo.design_pattern.singleton;

/**
 * Created by dkp on 2019/5/19.
 * 单例饿汉式
 * 提供了对唯一实例的受控访问；
 *
 * 依赖 JVM类加载机制，保证单例只会被创建1次，即 线程安全
 * 缺点：单例创建时机不可控，即类加载时 自动创建 单例
 */

public class HungrayMode {
    private static HungrayMode mHungrayMode= new HungrayMode();

    private HungrayMode(){

    }
    public HungrayMode getInstance(){
        return mHungrayMode;
    }

}
