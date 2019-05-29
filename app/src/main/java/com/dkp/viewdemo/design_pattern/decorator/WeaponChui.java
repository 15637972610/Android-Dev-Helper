package com.dkp.viewdemo.design_pattern.decorator;

/**
 * Created by dkp on 2019/5/20.
 */

public class WeaponChui extends Weapon {
    public WeaponChui(Hero hero) {
        super(hero);
    }
    @Override
    public void attack(String noName) {
        System.out.println(noName+"拿着锤");
    }

}
