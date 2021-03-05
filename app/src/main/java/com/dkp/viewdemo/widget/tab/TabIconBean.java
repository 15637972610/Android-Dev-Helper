/*
 *
 *  *
 *  *     京东金融团队Android基础开发库积累、沉淀、封装、共同整理
 *  *
 *  *     Copyright (c) 2017. @ 京东金融移动研发团队
 *  *
 *  *     技术支持：曾繁添<zengfantian@jd.com>
 *
 */

package com.dkp.viewdemo.widget.tab;

import java.io.Serializable;

/** Created by gonglei on 2017/11/7. 首页底部导航bean */
public class TabIconBean implements Serializable {
    private static final long serialVersionUID = 9027525428539291723L;
    public String text;

    public int normalIcon = -1;
    public int pressedIcon = -1;

    /**
     * 点击图片 -- 服务器下发字段名
     */
    public String clickImg = "";

    /**
     * 常态图片 -- 服务器下发字段名
     */
    public String normalImg = "";




    public TabIconBean () {}

    public TabIconBean (String text, int normalIcon, int pressedIcon) {
        this.text = text;
        this.normalIcon = normalIcon;
        this.pressedIcon = pressedIcon;
    }


    public TabIconBean (String text, String clickImg, String normalImg) {
        this.text = text;
        this.clickImg = clickImg;
        this.normalImg = normalImg;
    }

}
