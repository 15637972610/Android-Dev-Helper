package com.dkp.viewdemo.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


import com.dkp.viewdemo.BaseLibApplication;
import com.dkp.viewdemo.image.ImageBase;

import java.util.Locale;

/**
 * jfs图片服务前缀拼接
 * 版权信息(@copyright Copyright 2019 JD.COM All Right Reserved)
 * 与该类相关联类(@see);
 * 作者(@author jiayizhan 部门:校园生态部-校园产研组);
 * 版本(@version V1.0);
 * 创建、开发日期(@date 2019-08-1519:56);
 * 最后修改日期 2019-08-15;
 * 复审人
 */
public class ImageUrlUtil {
    private static String TAG = "[ImageUrlUtil]";
    public static String IMAGE_HOST = "https://m.360buyimg.com/yocial/";
    /**
     * 定制化的获取图片url，根据传进来的ImageView尺寸动态评价URL
     *
     * @param imageView
     * @param imageURL
     * @param listener
     */
    public static void getUrlSync(@NonNull ImageView imageView, final String imageURL, final OnImageUrlListener listener) {
        if (!TextUtils.isEmpty(imageURL)) {
            if (imageURL.startsWith("jfs")) {
                // 排除gif图，不能指定尺寸
                if (imageURL.endsWith("gif")) {
                    if (listener != null) {
                        listener.onGetURL(IMAGE_HOST + imageURL);
                    }
                    return;
                }
                // 普通图片，不包含host的URL，手动拼接host
                getViewSize(imageView, new OnSizeListener() {
                    @Override
                    public void onSize(int width, int height) {
                        if (width <= 0 || height <= 0) {
                            // 未获取到imageview的尺寸，直接使用原图
                            if (listener != null) {
                                listener.onGetURL(IMAGE_HOST + imageURL);
                            }
                        } else {
                            // 获取到了view尺寸，拼接url
                            String size_path = String.format(Locale.ENGLISH, "s%dx%d", width, height);
                            LogUtils.w(TAG, "图片URL host = " + size_path);
                            String format = String.format("%s%s_%s", IMAGE_HOST, size_path, imageURL);
                            LogUtils.w(TAG, "图片URL = " + format);
                            if (listener != null) {
                                listener.onGetURL(format);
                            }

                        }
                    }
                });
            } else {
                // 完整的URL，暂时不做处理
                if (listener != null) {
                    listener.onGetURL(imageURL);
                }
            }
        }else {
            if (listener != null) {
                listener.onGetURL("");
            }
        }
    }

    public interface OnImageUrlListener {
        void onGetURL(String url);
    }

    /**
     * 获取iamgeview的尺寸，为了拼接合适的图片url
     *
     * @param imageView
     * @return
     */
    private static void getViewSize(final ImageView imageView, final OnSizeListener listener) {
        if (imageView != null) {
            imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (listener != null) {
                        int measuredWidth = imageView.getMeasuredWidth();
                        int measuredHeight = imageView.getMeasuredHeight();
                        listener.onSize(measuredWidth, measuredHeight);
                        LogUtils.w(TAG, "图片size：" + measuredWidth + " -- " + measuredHeight);
                        imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    public interface OnSizeListener {
        void onSize (int width, int height);
    }


    public static String wrapUrl(String imageUrl) {
        return wrapUrl(null, imageUrl);
    }

    public static String wrapUrl(String host, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            return "";
        }
        if (ImageBase.Scheme.ofUri(imageUrl) == ImageBase.Scheme.UNKNOWN) {
            if (TextUtils.isEmpty(host)) {
                host = IMAGE_HOST;
            }
            imageUrl = host + imageUrl;
        }
        return imageUrl;
    }

    public static String generateMipmapResourceUrl(@DrawableRes int mipmapId) {
        return "android.resource://" + BaseLibApplication.getAppContext().getPackageName() + "/mipmap/" + mipmapId;
    }

    public static String generateDrawableResourceUrl(@DrawableRes int drawableId) {
        return "android.resource://" + BaseLibApplication.getAppContext().getPackageName() + "/drawable/" + drawableId;
    }


}
