package com.dkp.viewdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

public class BaseLibApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private final String TAG = "BaseLibApplication";
    private static final String DB_NAME = "main.db";
    private static boolean APP_RUNNING = false;
    protected static Application instance;
    private static Context context;
    public static Application getInstance() {
        return instance;
    }

    public static void Exit() {
        getInstance().onTerminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        context = BaseLibApplication.instance.getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}
