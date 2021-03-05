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
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.dkp.viewdemo.image.delegate.ImageDisplayDelegate;
import com.dkp.viewdemo.image.delegate.ImageDownloadDelegate;
import com.dkp.viewdemo.image.delegate.ImageSize;
import com.dkp.viewdemo.image.delegate.OnDrawableDownloadListener;
import com.dkp.viewdemo.image.delegate.onBitmapDownloadListener;

import java.io.File;

/**
 * Image loader parent class
 * Author: jiayizhan
 * DateTime: 2019-05-21 10:29
 * Version 1.0
 */
public interface ILoader {

    public void init(Context context);

    public void pause(Activity activity);

    public void resume(Activity activity);

    public void destroy(Context context);

    public File getCacheDir(Context context);

    public void clearMemoryCache(Context context);

    public void clearDiskCache(Context context);

    public Bitmap getBitmapFromCache(Context context, String url, boolean onlyRetrieveFromCache);

    public void getBitmapFromCache(Context context, String url, onBitmapDownloadListener listener);

    public void displayImage(Context context, int resId, ImageView imageView);

    public void displayImage(Context context, String url, ImageView imageView);

    public void displayImage(Context context, String url, ImageView imageView, boolean isCache);

    public void displayImage(Fragment fragment, String url, ImageView imageView);

    public void displayImage(Context context, String url, ImageView imageView, int defRes);

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes);

    public void displayImage(Context context, String url, ImageView imageView, int defRes, BitmapTransformation transformations);

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, BitmapTransformation transformations);

    public void displayImage(Context context, String url, ImageView imageView, int defRes, ImageSize size);

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, ImageSize size);

    public void displayImage(Context context, String url, ImageView imageView, int defRes, boolean cacheInMemory);

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, boolean cacheInMemory);

    public void displayImage(Context context, String url, ImageView imageView, ImageDisplayDelegate listener);

    public void displayImage(Fragment fragment, String url, ImageView imageView, ImageDisplayDelegate listener);

    public void displayImage(Context context, String url, ImageView imageView, int defRes, ImageDisplayDelegate listener);

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, ImageDisplayDelegate listener);

    public void displayCircleImage(Context context, String url, ImageView imageView, int defRes);

    public void displayCircleImage(Fragment fragment, String url, ImageView imageView, int defRes);

    public void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius, GlideILoader.TYPE type);

    public void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius);

    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, int defRes, int radius);

    public void displayBlurImage(Context context, String url, int blurRadius, final OnDrawableDownloadListener listener);

    public void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius, int blurSampling);

    public void displayBlurImage(Context context, String url, ImageView imageView, int defRes, int blurRadius, int blurSampling);

    public void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius, int blurSampling);

    public void displayImageResource(Context context, int resId, ImageView imageView);

    public void displayImageResource(Fragment fragment, int resId, ImageView imageView);

    public void displayImageResource(Context context, int resId, ImageView imageView, BitmapTransformation transformations);

    public void displayImageResource(Fragment fragment, int resId, ImageView imageView, BitmapTransformation transformations);

    public void displayImageResource(Context context, int resId, ImageView imageView, int defRes);

    public void displayImageResource(Fragment fragment, int resId, ImageView imageView, int defRes);

    public void displayImageResource(Context context, int resId, ImageView imageView, int defRes, BitmapTransformation transformations);

    public void displayImageResource(Fragment fragment, int resId, ImageView imageView, int defRes, BitmapTransformation transformations);

    public void download(String path, ImageDownloadDelegate delegate);
}