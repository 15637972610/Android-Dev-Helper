package com.dkp.viewdemo.design_pattern.decorator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by dkp on 2019/5/20.
 * 装饰者模式，四步走
 * 1.定义抽象组件，规范装饰方法
 * 2.定义被装饰者，也就是我们要装饰的具体对象，可能是多个
 * 3.定义装饰者组件 ，装饰具体组件对象
 * 4.定义具体装饰， 对对象进行扩展
 *
 */

public class DecoratorTest  {

    public static void main(String args[]) {
        Hero mHero = new HeroCike();
        new WeaponBiShou(mHero).attack("刺客");
    }
}
