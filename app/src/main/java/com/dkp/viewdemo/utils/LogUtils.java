package com.dkp.viewdemo.utils;

import android.util.Log;

import com.dkp.viewdemo.BuildConfig;
/**
 * 全局统一日志类
 */
public class LogUtils {
    enum LogLevel {
        I, D, W, E
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            log(LogLevel.D, tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            log(LogLevel.I, tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            log(LogLevel.W, tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            log(LogLevel.E, tag, msg);
        }
    }

    private static void log(LogLevel level, String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            logByLevel(level, tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                logByLevel(level, tag, logContent);
            }
            logByLevel(level, tag, msg);
        }
    }

    private static void logByLevel(LogLevel level, String tag, String msg) {
        switch (level) {
            case D:
                Log.d(tag, msg);
                break;
            case E:
                Log.e(tag, msg);
                break;
            case I:
                Log.i(tag, msg);
                break;
            case W:
                Log.w(tag, msg);
                break;
        }
    }
}
