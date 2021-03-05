package com.dkp.viewdemo.utils;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtil {

    public static void resetItemSize (int dpvalueWidth,int dpvalueHeigh, final View view){
        try {
            if (view == null) {
                return;
            }
            final int pxWid =ScreenUtil.dip2px(dpvalueWidth);
            final int pxHeigh =ScreenUtil.dip2px(dpvalueHeigh);
            view.post(new Runnable() {
                @Override
                public void run (){
                    ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) view.getLayoutParams();
                    if (lp != null) {
                        lp.height = pxHeigh;
                        lp.width = pxWid;
                        view.setLayoutParams(lp);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    public static void resetItemWidth (int dpvalue, final View view){
        if (view == null) {
            return;
        }
        final int pxWid =ScreenUtil.dip2px(dpvalue);
        view.post(new Runnable() {
            @Override
            public void run (){
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) view.getLayoutParams();
                if (lp != null) {
                    lp.width = pxWid;
                    view.setLayoutParams(lp);
                }
            }
        });
    }

    public static void resetItemHeight (final int dpvalue, final View view){
        try {
            if (view == null) {
                return;
            }
            final int pxWid =ScreenUtil.dip2px(dpvalue);
            view.post(new Runnable() {
                @Override
                public void run (){
                    ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) view.getLayoutParams();
                    if (lp != null) {
                        lp.height = dpvalue;
                        view.setLayoutParams(lp);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    public static void resetItemWidth (int dpvalue, int size, final View view){
        if (view == null) {
            return;
        }
        final int itemWidth = caculateItemWidth(dpvalue, size);
        view.post(new Runnable() {
            @Override
            public void run (){
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) view.getLayoutParams();
                if (lp != null) {
                    lp.width = itemWidth;
                    view.setLayoutParams(lp);
                }
            }
        });
    }



    /**
     * 计算item的宽度
     *
     * @param margin
     * @param size
     * @return
     */
    public static int caculateItemWidth (int margin, int size){
        try {
            if (size > 0) {
                int margins = 2 * ScreenUtil.dip2px(margin);
                int viewWidth = ScreenUtil.getScreenWidth() - margins;
                int itemWidth = viewWidth / size;
                return itemWidth;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 扩大view的点击范围
     *
     * @param view
     */
    public static void expandTouchArea (View view){
        expandTouchArea(view, 800);
    }


    public static void expandTouchArea (final View view, final int size){
        try {
            final View parentView = (View) view.getParent();
            parentView.post(new Runnable() {
                @Override
                public void run (){
                    Rect rect = new Rect();
                    view.getHitRect(rect);

                    rect.top -= size;
                    rect.bottom += size;
                    rect.left -= size;
                    rect.right += size;

                    parentView.setTouchDelegate(new TouchDelegate(rect, view));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
