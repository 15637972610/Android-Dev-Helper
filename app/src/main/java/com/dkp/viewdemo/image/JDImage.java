/**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dkp.viewdemo.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.dkp.viewdemo.image.delegate.ImageDisplayDelegate;
import com.dkp.viewdemo.image.delegate.ImageDownloadDelegate;
import com.dkp.viewdemo.image.delegate.ImageSize;
import com.dkp.viewdemo.image.delegate.OnDrawableDownloadListener;
import com.dkp.viewdemo.image.delegate.onBitmapDownloadListener;

import java.io.File;

/**
 * Image loader 入口
 * Author: jiayizhan
 * DateTime: 2019-05-21 10:29
 * Version 1.0
 */
public class JDImage {
    private static final String TAG = JDImage.class.getSimpleName();
    private static ILoader sILoader;

    private JDImage() {
    }

    private static final ILoader getImageLoader() {
        if (sILoader == null) {
            synchronized (JDImage.class) {
                if (sILoader == null) {
                    if (isClassExists("com.bumptech.glide.Glide")) {
                        sILoader = new GlideILoader();
                    } else {
                        throw new RuntimeException("必须在你的build.gradle文件中配置「Glide」图片加载库的依赖");
                    }
                }
            }
        }
        return sILoader;
    }

    /**
     * 设置开发者自定义 ILoader
     *
     * @param iLoader
     */
    public static void setImageLoader(ILoader iLoader) {
        sILoader = iLoader;
    }

    private static final boolean isClassExists(String classFullName) {
        try {
            Class.forName(classFullName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void init(Context context) {

    }

    public static void pause(Activity activity) {
        if (isValidContextForGlide(activity)) {
            if (getImageLoader() != null) {
                getImageLoader().pause(activity);
            }
        }
    }

    public static void resume(Activity activity) {
        if (isValidContextForGlide(activity)) {
            if (getImageLoader() != null) {
                getImageLoader().resume(activity);
            }
        }
    }

    public static void destroy(Context context) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().destroy(context);
            }
        }
    }

    public File getCacheDir(Context context) {
        if (getImageLoader() != null) {
            return getImageLoader().getCacheDir(context);
        }
        return null;
    }

    public static void clearMemoryCache(Context context) {
        if (getImageLoader() != null) {
            getImageLoader().clearMemoryCache(context);
        }
    }


    public static void clearDiskCache(Context context) {
        if (getImageLoader() != null) {
            getImageLoader().clearDiskCache(context);
        }
    }


    public static Bitmap getBitmapFromCache(Context context, String url, boolean onlyRetrieveFromCache) {
        if (getImageLoader() != null) {
            return getImageLoader().getBitmapFromCache(context, url,onlyRetrieveFromCache);
        }
        return null;
    }


    public static void getBitmapFromCache(Context context, String url, onBitmapDownloadListener listener) {
        if (getImageLoader() != null) {
            getImageLoader().getBitmapFromCache(context, url, listener);
        }
    }


    public static void displayImage(Context context, int resId, ImageView imageView) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, resId, imageView);
            }
        }
    }


    public static void displayImage(Context context, String url, ImageView imageView) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, url, imageView);
            }
        }
    }

    public static void displayImage(Fragment fragment, String url, ImageView imageView) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(fragment, url, imageView);
            }
        }
    }

    public static void displayImage(Context context, String url, ImageView imageView, boolean isCache) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, url, imageView, isCache);
            }
        }
    }


    public static void displayImage(Context context, String url, ImageView imageView, int defRes) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, url, imageView, defRes);
            }
        }
    }


    public static void displayImage(Fragment fragment, String url, ImageView imageView, int defRes) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(fragment, url, imageView, defRes);
            }
        }
    }


    public static void displayImage(Context context, String url, ImageView imageView, int defRes, BitmapTransformation transformation) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, url, imageView, defRes, transformation);
            }
        }
    }


    public static void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, BitmapTransformation transformation) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(fragment, url, imageView, defRes, transformation);
            }
        }
    }


    public static void displayImage(Context context, String url, ImageView imageView, int defRes, ImageSize size) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, url, imageView, defRes, size);
            }
        }
    }


    public static void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, ImageSize size) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(fragment, url, imageView, defRes, size);
            }
        }
    }


    public static void displayImage(Context context, String url, ImageView imageView, int defRes, boolean cacheInMemory) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, url, imageView, defRes, cacheInMemory);
            }
        }
    }


    public static void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, boolean cacheInMemory) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(fragment, url, imageView, defRes, cacheInMemory);
            }
        }
    }

    public static void displayImage(Context context, String url, ImageView imageView, ImageDisplayDelegate listener) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, url, imageView, listener);
            }
        }
    }


    public static void displayImage(Fragment fragment, String url, ImageView imageView, ImageDisplayDelegate listener) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(fragment, url, imageView, listener);
            }
        }
    }


    public static void displayImage(Context context, String url, ImageView imageView, int defRes, ImageDisplayDelegate listener) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(context, url, imageView, defRes, listener);
            }
        }
    }


    public static void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, ImageDisplayDelegate listener) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImage(fragment, url, imageView, defRes, listener);
            }
        }
    }


    public static void displayCircleImage(Context context, String url, ImageView imageView, int defRes) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayCircleImage(context, url, imageView, defRes);
            }
        }
    }


    public static void displayCircleImage(Fragment fragment, String url, ImageView imageView, int defRes) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayCircleImage(fragment, url, imageView, defRes);
            }
        }
    }


    public static void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayRoundImage(context, url, imageView, defRes, radius);
            }
        }
    }

    public static void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius, GlideILoader.TYPE type) {
        if (isValidContextForGlide(context) && getImageLoader() != null) {
            getImageLoader().displayRoundImage(context, url, imageView, defRes, radius, type);
        }

    }

    public static void displayRoundImage(Fragment fragment, String url, ImageView imageView, int defRes, int radius) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayRoundImage(fragment, url, imageView, defRes, radius);
            }
        }
    }


    public static void displayBlurImage(Context context, String url, int blurRadius, final OnDrawableDownloadListener listener) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayBlurImage(context, url, blurRadius, listener);
            }
        }
    }

    public static void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius, int blurSampling) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayBlurImage(context, url, imageView, blurRadius, blurSampling);
            }
        }
    }

    public static void displayBlurImage(Context context, String url, ImageView imageView, int defRes, int blurRadius, int blurSampling) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayBlurImage(context, url, imageView, defRes, blurRadius, blurSampling);
            }
        }
    }

    public static void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius, int blurSampling) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayBlurImage(context, resId, imageView, blurRadius, blurSampling);
            }
        }
    }

    public static void displayImageResource(Context context, int resId, ImageView imageView) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImageResource(context, resId, imageView);
            }
        }
    }


    public static void displayImageResource(Fragment fragment, int resId, ImageView imageView) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImageResource(fragment, resId, imageView);
            }
        }
    }


    public static void displayImageResource(Context context, int resId, ImageView imageView, BitmapTransformation transformation) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImageResource(context, resId, imageView, transformation);
            }
        }
    }


    public static void displayImageResource(Fragment fragment, int resId, ImageView imageView, BitmapTransformation transformation) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImageResource(fragment, resId, imageView, transformation);
            }
        }
    }


    public static void displayImageResource(Context context, int resId, ImageView imageView, int defRes) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImageResource(context, resId, imageView, defRes);
            }
        }
    }


    public static void displayImageResource(Fragment fragment, int resId, ImageView imageView, int defRes) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImageResource(fragment, resId, imageView, defRes);
            }
        }
    }


    public static void displayImageResource(Context context, int resId, ImageView imageView, int defRes, BitmapTransformation transformation) {
        if (isValidContextForGlide(context)) {
            if (getImageLoader() != null) {
                getImageLoader().displayImageResource(context, resId, imageView, defRes, transformation);
            }
        }
    }


    public static void displayImageResource(Fragment fragment, int resId, ImageView imageView, int defRes, BitmapTransformation transformation) {
        if (fragment != null && isValidContextForGlide(fragment.getActivity())) {
            if (getImageLoader() != null) {
                getImageLoader().displayImageResource(fragment, resId, imageView, defRes, transformation);
            }
        }
    }

    public static void download(String path, ImageDownloadDelegate delegate) {
        if (getImageLoader() != null) {
            getImageLoader().download(path, delegate);
        }
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }
}
