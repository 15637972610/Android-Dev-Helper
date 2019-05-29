package com.dkp.viewdemo.design_pattern.decorator;

/**
 * Created by dkp on 2019/5/20.
 * 3.定义装饰者组件 ，装饰传递过来的具体组件对象
 */

public class Weapon implements Hero {
    private Hero mHero;
    @Override
    public void attack(String noName) {

    }

    public Weapon(Hero hero) {
        this.mHero=mHero;
    }
}
