package com.dkp.viewdemo.design_pattern.decorator;

/**
 * Created by dkp on 2019/5/20.
 */

public class WeaponGongNu extends Weapon {
    public WeaponGongNu(Hero hero) {
        super(hero);
    }
    @Override
    public void attack(String noName) {
        System.out.println(noName+"拿着弓弩");
    }

}
