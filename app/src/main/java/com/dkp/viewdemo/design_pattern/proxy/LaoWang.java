package com.dkp.viewdemo.design_pattern.proxy;

/**
 * Created by Administrator on 2019/5/19.
 */

public class LaoWang extends IProxyDothings {
    @Override
    public void buyWaWa() {
    XiaoHuang xiaoHuang = new XiaoHuang();
    xiaoHuang.buyWaWa();

    doXXthings();

    }

    public void doXXthings() {

    }
}
