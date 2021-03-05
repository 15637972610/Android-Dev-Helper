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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.dkp.viewdemo.BaseLibApplication;
import com.dkp.viewdemo.image.delegate.ImageDisplayDelegate;
import com.dkp.viewdemo.image.delegate.ImageDownloadDelegate;
import com.dkp.viewdemo.image.delegate.ImageSize;
import com.dkp.viewdemo.image.delegate.OnDrawableDownloadListener;
import com.dkp.viewdemo.image.delegate.onBitmapDownloadListener;
import com.dkp.viewdemo.image.tranform.RoundBitmapTranformation;
import com.dkp.viewdemo.utils.ImageUrlUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * glide 实现
 * Author: jiayizhan
 * DateTime: 2019-05-21 10:29
 * Version 1.0
 */
public class GlideILoader implements ILoader {
    public enum TYPE {
        FitXY, FitCenter, CenterCrop, CenterInside
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void pause(Activity activity) {
        Glide.with(activity).pauseRequests();
    }

    @Override
    public void resume(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            Glide.with(activity).resumeRequestsRecursive();
        }
    }

    @Override
    public void destroy(Context context) {
        clearMemoryCache(context);
    }

    @Override
    public File getCacheDir(Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void clearDiskCache(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //必须在子线程中  This method must be called on a background thread.
                Glide.get(context).clearDiskCache();
                return null;
            }
        };
    }

    @Override
    public Bitmap getBitmapFromCache(Context context, String url, boolean onlyRetrieveFromCache) {
        try {
            FutureTarget<Bitmap> futureBitmap = Glide.with(context)
                    .asBitmap()
                    .onlyRetrieveFromCache(onlyRetrieveFromCache)
                    .load(url)
                    .submit();
            Bitmap bitmap = futureBitmap.get();
            return bitmap;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getBitmapFromCache(Context context, String url, onBitmapDownloadListener listener) {

    }

    /**
     * 默认的策略是DiskCacheStrategy.AUTOMATIC
     * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
     * DiskCacheStrategy.NONE 不使用磁盘缓存
     * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
     * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
     * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
     *
     * @param context   上下文
     * @param resId     id
     * @param imageView into
     */
    //DiskCacheStrategy.SOURCE：缓存原始数据 DiskCacheStrategy.DATA对应Glide 3中的DiskCacheStrategy.SOURCE
    @Override
    public void displayImage(Context context, int resId, ImageView imageView) {
        //设置缓存策略缓存原始数据  Saves just the original data to cache
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
    }

    /**
     * @param context
     * @param url       url
     * @param imageView in
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @param isCache   是否是缓存 如果是：缓存策略缓存原始数据  不是的话 ：缓存策略DiskCacheStrategy.NONE：什么都不缓存
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView, boolean isCache) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).skipMemoryCache(isCache).diskCacheStrategy(isCache ? DiskCacheStrategy.AUTOMATIC : DiskCacheStrategy.NONE).into(imageView);
    }

    /**
     * @param fragment  绑定生命周期
     * @param url
     * @param imageView
     */
    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
    }

    /**
     * 使用.placeholder()方法在某些情况下会导致图片显示的时候出现图片变形的情况
     * 这是因为Glide默认开启的crossFade动画导致的TransitionDrawable绘制异常
     *
     * @param context
     * @param url
     * @param imageView
     * @param defRes    defRes 可以是个new ColorDrawable(Color.BLACK) 也可以是张图片
     */
    //默认为200  时间有点长  ，工程中要修改下，设置一个加载失败和加载中的动画过渡，V4.0的使用的方法
    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).transition(new DrawableTransitionOptions().crossFade(200)).placeholder(defRes).error(defRes).into(imageView);
    }

    /**
     * 默认时间为200 需
     *
     * @param fragment
     * @param url
     * @param imageView
     * @param defRes
     */
    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).transition(new DrawableTransitionOptions().crossFade(200)).placeholder(defRes).error(defRes).into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     * @param transformations bitmapTransform 方法设置图片转换
     */

    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes, final BitmapTransformation transformations) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptionsTransformation(defRes, defRes, transformations)).into(imageView);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, BitmapTransformation transformations) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptionsTransformation(defRes, defRes, transformations)).into(imageView);
    }

    /**
     * 加载原圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     */
    public void displayImageCircle(Context context, String url, ImageView imageView, int defRes) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes, defRes)).into(imageView);

    }

    public void displayImageCircle(Fragment context, String url, ImageView imageView, int defRes) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes, defRes)).into(imageView);
    }

    public RequestOptions requestOptions(int placeholderResId, int errorResId) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
    }

    public RequestOptions requestOptionsTransformation(int placeholderResId, int errorResId, BitmapTransformation bitmapTransformation) {
        return requestOptions(placeholderResId, errorResId).transform(bitmapTransformation);
    }

    /**
     * 加载原图
     *
     * @param placeholderResId
     * @param errorResId
     * @return
     */
    public RequestOptions circleRequestOptions(int placeholderResId, int errorResId) {
        return requestOptions(placeholderResId, errorResId).transform(new CircleCrop());
    }

    public RequestOptions roundRequestOptions(int placeholderResId, int errorResId, int radius) {
        return requestOptions(placeholderResId, errorResId).transform(new RoundBitmapTranformation(radius));
    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @param defRes    placeholder(int resourceId). 设置资源加载过程中的占位Drawable  error(int resourceId).设置load失败时显示的Drawable
     * @param size      override(int width, int height). 重新设置Target的宽高值
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes, ImageSize size) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes).override(size.getWidth(), size.getHeight()).into(imageView);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, ImageSize size) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes).override(size.getWidth(), size.getHeight()).into(imageView);
    }

    /**
     * .skipMemoryCache( true )去特意告诉Glide跳过内存缓存  是否跳过内存，还是不跳过
     *
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     * @param cacheInMemory
     */
    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes, boolean cacheInMemory) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes).skipMemoryCache(cacheInMemory).into(imageView);
    }

    @Override
    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, boolean cacheInMemory) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes).skipMemoryCache(cacheInMemory).into(imageView);
    }

    /**
     * 只在需要的地方进行监听 listener 通过自定义的接口回调参数
     *
     * @param context
     * @param url
     * @param imageView
     * @param listener  监听资源加载的请求状态 但不要每次请求都使用新的监听器，要避免不必要的内存申请，
     *                  可以使用单例进行统一的异常监听和处理
     */
    @Override
    public void displayImage(Context context, final String url, final ImageView imageView, final ImageDisplayDelegate listener) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.onFailed(e, url);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.onSuccess(imageView, url);
                return false;
            }
        }).into(imageView);
    }

    @Override
    public void displayImage(Fragment fragment, final String url, final ImageView imageView, final ImageDisplayDelegate listener) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.onFailed(e, url);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.onSuccess(imageView, url);
                return false;
            }
        }).into(imageView);
    }

    @Override
    public void displayImage(Context context, final String url, final ImageView imageView, int defRes, final ImageDisplayDelegate listener) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.onFailed(e, url);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.onSuccess(imageView, url);
                return false;
            }
        }).into(imageView);
    }

    @Override
    public void displayImage(Fragment fragment, final String url, final ImageView imageView, int defRes, final ImageDisplayDelegate listener) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.onFailed(e, url);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.onSuccess(imageView, url);
                return false;
            }
        }).into(imageView);
    }

    /**
     * 圆形图片的裁剪
     *
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     */
    @Override
    public void displayCircleImage(Context context, String url, ImageView imageView, int defRes) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes, defRes)).into(imageView);
    }

    @Override
    public void displayCircleImage(Fragment fragment, String url, ImageView imageView, int defRes) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes, defRes)).into(imageView);
    }


    public void displayCircleImage(Activity fragment, String url, ImageView imageView, int defRes) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes, defRes)).into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     * @param radius    倒圆角的图片 需要传入需要radius  越大的话，倒角越明显
     */
    @Override
    public void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius, TYPE type) {
        RequestOptions requestOptions = roundRequestOptions(defRes, defRes, radius);
        if (type == TYPE.FitCenter) {
            requestOptions.fitCenter();
        } else if (type == TYPE.CenterCrop) {
            requestOptions.centerCrop();
        } else if (type == TYPE.CenterInside) {
            requestOptions.centerInside();
        } else if (type == TYPE.FitXY) {
            requestOptions.downsample(DownsampleStrategy.NONE);
        } else {
            requestOptions.centerCrop();
        }
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptions).into(imageView);
    }
    /**
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     * @param radius    倒圆角的图片 需要传入需要radius  越大的话，倒角越明显
     */
    @Override
    public void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius) {
        Glide.with(context).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(RequestOptions.centerCropTransform()).apply(roundRequestOptions(defRes, defRes, radius)).into(imageView);
    }

    @Override
    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, int defRes, int radius) {
        Glide.with(fragment).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(RequestOptions.centerCropTransform()).apply(roundRequestOptions(defRes, defRes, radius)).into(imageView);
    }

    /**
     * -     *
     *
     * @param context
     * @param url     -     * @param blurRadius 模糊的程度 ，数字越大越模糊
     *                -     * @param listener 接口回调需要拿到drawable
     *                +     * @param blurRadius   （0-25） 模糊的程度 ，数字越大越模糊
     *                +     * @param blurSampling 压缩比例
     */
    @Override
    public void displayBlurImage(Context context, String url, int blurRadius, final OnDrawableDownloadListener listener) {
        Glide.with(getContext(context)).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (listener != null) {
                    listener.onDrawable(resource);
                }
            }
        });
    }

    /**
     * 设置高斯模糊
     *
     * @param context
     * @param url
     * @param imageView
     * @param blurRadius：设置模糊度(在0.0到25.0之间)
     * @param blurSampling:图片缩放比例,默认“1”
     */
    @Override
    public void displayBlurImage(Context context, String url, ImageView imageView, int blurRadius, int blurSampling) {
        if (blurRadius <= 0) {
            blurRadius = 1;
        }
        if (blurRadius > 25) {
            blurRadius = 25;
        }
        if (blurSampling <= 0) {
            blurSampling = 1;
        }
        Glide.with(getContext(context)).load(ImageUrlUtil.wrapUrl(url))
                .apply(RequestOptions.centerCropTransform()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(bitmapTransform(new BlurTransformation(blurRadius, blurSampling)))
                .into(imageView);
    }

    /**
     * 不需要关系此模糊图的drawable
     *
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     * @param blurRadius
     */
    @Override
    public void displayBlurImage(Context context, String url, ImageView imageView, int defRes, int blurRadius, int blurSampling) {
        Glide.with(getContext(context)).load(ImageUrlUtil.wrapUrl(url)).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(blurRequestOptions(defRes, defRes, blurRadius, blurSampling)).into(imageView);
    }

    @Override
    public void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius, int blurSampling) {
        Glide.with(getContext(context)).load(resId).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(blurRequestOptions(resId, resId, blurRadius, blurSampling)).into(imageView);
    }

    private RequestOptions blurRequestOptions(int defRes, int defRes1, int blurRadius, int blurSampling) {
        if (blurRadius <= 0) {
            blurRadius = 1;
        }
        if (blurRadius > 25) {
            blurRadius = 25;
        }
        if (blurSampling <= 0) {
            blurSampling = 1;
        }
        return requestOptions(defRes, defRes1).transform(new BlurTransformation(blurRadius, blurSampling));
    }

    /**
     * 加载资源文件
     *
     * @param context
     * @param resId
     * @param imageView
     */
    @Override
    public void displayImageResource(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    @Override
    public void displayImageResource(Fragment fragment, int resId, ImageView imageView) {
        Glide.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    @Override
    public void displayImageResource(Context context, int resId, ImageView
            imageView, BitmapTransformation transformations) {
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).transform(transformations).into(imageView);
    }

    @Override
    public void displayImageResource(Fragment fragment, int resId, ImageView
            imageView, BitmapTransformation transformations) {
        Glide.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).transform(transformations).into(imageView);
    }

    public void displayImageResource(Activity fragment, int resId, ImageView imageView) {
        Glide.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    /**
     * 当传入到其他的东西的时候，我要保证图片不变形
     *
     * @param placeholderResId
     * @param errorResId
     * @param transformation
     * @return
     */
    public RequestOptions requestOptionsNoTransform(int placeholderResId, int errorResId, Transformation<Bitmap> transformation) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId).transform(transformation);
    }

    /**
     * 加载资源文件失败了，加载中的默认图和失败的图片
     *
     * @param context
     * @param resId
     * @param imageView
     * @param defRes
     */
    @Override
    public void displayImageResource(Context context, int resId, ImageView imageView, int defRes) {
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(defRes).error(defRes).into(imageView);
    }

    @Override
    public void displayImageResource(Fragment fragment, int resId, ImageView imageView, int defRes) {
        Glide.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(defRes).error(defRes).into(imageView);
    }

    @Override
    public void displayImageResource(Context context, int resId, ImageView imageView,
                                     int defRes, BitmapTransformation transformations) {
        Glide.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(defRes).error(defRes).transform(transformations).into(imageView);
    }

    @Override
    public void displayImageResource(Fragment fragment, int resId, ImageView imageView,
                                     int defRes, BitmapTransformation transformations) {
        Glide.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(defRes).error(defRes).transform(transformations).into(imageView);
    }

    @Override
    public void download(final String path, final ImageDownloadDelegate delegate) {
        Glide.with(BaseLibApplication.getInstance()).asFile().load(path).into(new CustomTarget<File>() {

            @Override
            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                if (delegate != null) {
                    delegate.onSuccess(path, file);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable drawable) {
                if (delegate != null) {
                    delegate.onFailed(path);
                }
            }
        });
    }

    public Context getContext(Context context) {
        if (context == null) {
            context = BaseLibApplication.getAppContext();
        }
        return context;
    }
}