package com.dkp.viewdemo.utils;

/**
 * 防连点工具类
 * 2020/2/13
 */
public class ClickUtil {
    public static int DELAY = 1000;
    private static long lastClickTime = 0;

    /**
     * 设置连点间隔
     * @param delay
     */
    public static void setDelay(int delay) {
        DELAY = delay;
    }

    public static boolean isNotFastClick(int delay) {
        if (delay!=0){
            DELAY=delay;
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            return true;
        } else {
            return false;
        }
    }
}
