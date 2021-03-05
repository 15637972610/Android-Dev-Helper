package com.dkp.viewdemo.widget.tab;

import android.text.TextUtils;

import com.dkp.viewdemo.R;
import com.dkp.viewdemo.ui.FragmentTab1;
import com.dkp.viewdemo.ui.FragmentTab2;
import com.dkp.viewdemo.ui.FragmentTab3;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dukunpeng3
 */
public class TabConfig {

    /**青春版首页*/
    public static String HOST_YOUTH_MAIN= "openjdjrapp://com.jd.jrapp/youth/mainPage";
    /**青春版特权*/
    public static String HOST_YOUTH_PRIVATE= "youth_private";
    /**青春版我的*/
    public final static String HOST_YOUTH_ME = "openjdjrapp://com.jd.jrapp/youth/personalCenter";

    private static Map<String, String> scheme2Name = new HashMap<>();
    private static Map<String, IMainTabInterface> scheme2fragment = new HashMap<>();
    private static Map<String, Integer> scheme2icon = new HashMap<>();

    //***************************************************************************************************
    //*                                                                                                 *
    //*   如需更改默认tab配置，只需要更改默认的scheme数组即可，其他配置均是以scheme为key，去各个默认的map配置里去取   *
    //*                                                                                                 *
    //***************************************************************************************************
    /**
     * 默认tab为：首页，特权，主会场，种草，我的
     */
    public final static String[] DEFAULT_SCHEME_ARRAY = {HOST_YOUTH_MAIN,HOST_YOUTH_PRIVATE,HOST_YOUTH_ME};

    public final static String SP_KEY_TAB = "sp_tab";

    //***************************************************************************************************
    //*                                                                                                 *
    //*   ⚠️以下配置是默认的映射关系，如果没有特殊情况（如加tab，更改类名，icon等）不要修改                         *
    //*                                                                                                 *
    //***************************************************************************************************
    // 初始化默认配置，所有的映射关系都是以scheme为key【scheme：xxx】

    public static void init() {
        // scheme：name
        scheme2Name.put(HOST_YOUTH_MAIN, "首页");
        scheme2Name.put(HOST_YOUTH_PRIVATE, "特权");
        scheme2Name.put(HOST_YOUTH_ME, "我的");


        // scheme：fragment
        scheme2fragment.put(HOST_YOUTH_MAIN, new FragmentTab1());
        scheme2fragment.put(HOST_YOUTH_PRIVATE, new FragmentTab1());
        scheme2fragment.put(HOST_YOUTH_ME, new FragmentTab1());


        // scheme：icon
        scheme2icon.put(HOST_YOUTH_MAIN, R.drawable.main_youth_tab_first_normal);
        scheme2icon.put(HOST_YOUTH_PRIVATE, R.drawable.main_youth_tab_second_normal);
        scheme2icon.put(HOST_YOUTH_ME, R.drawable.main_youth_tab_fifth_normal);

    }

    /**
     * 根据scheme获取tab名称
     *
     * @param scheme
     * @return
     */
    public static String getTabName(String scheme) {
        return scheme2Name.get(scheme);
    }

    /**
     * 根据scheme获取fragment类名
     *
     * @param scheme
     * @return
     */
    public static IMainTabInterface getFragment(String scheme) {
        return scheme2fragment.get(scheme);
    }

    public static IMainTabInterface getTabFragment(int index) {
        IMainTabInterface fragment =new FragmentTab1();
        switch(index){
            case 0:
                fragment = new FragmentTab1();
                break;
            case 1:
                fragment = new FragmentTab2();
                break;
            case 2:
                fragment = new FragmentTab3();
                break;
            default:
                break;
        }
        return fragment;
    }

    /**
     * 通过 scheme 获取对应的页面
     * @param scheme 目标scheme
     * @return 对应的页面实体
     */
    public static IMainTabInterface getTabFragmentByScheme(String scheme) {
        if (TextUtils.equals(HOST_YOUTH_MAIN,scheme)){
            return new FragmentTab1();
        }else if (TextUtils.equals(HOST_YOUTH_ME,scheme)){
            return new FragmentTab1();
        }else {
            return new FragmentTab1();
        }
    }

    /**
     * 根据scheme获取tab icon
     *
     * @param scheme
     * @return
     */
    public static int getIcon(String scheme) {
        return scheme2icon.get(scheme);
    }

    /**
     * 判断是否展示webview fragment
     * openjdjrapp:// 是原生
     * eg："jumpData": "openjdjrapp://com.jd.jrapp/youth/personalCenter"
     *
     * @param jumpData
     * @return true 是web ;false 是原生
     */
    public static boolean isWebScheme(String jumpData) {
        if (jumpData != null) {
            return !jumpData.toLowerCase().contains("openjdjrapp://");
        }
        return false;
    }

    public static String getScheme(int index) {
        String scheme = DEFAULT_SCHEME_ARRAY[index];
        return scheme;
    }

    public static String getTabName(int index) {
        String scheme = DEFAULT_SCHEME_ARRAY[index];
        String des = scheme2Name.get(scheme);
        return des;
    }
}
