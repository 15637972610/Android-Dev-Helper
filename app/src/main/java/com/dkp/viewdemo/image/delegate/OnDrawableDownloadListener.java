package com.dkp.viewdemo.image.delegate;

import android.graphics.drawable.Drawable;

/**
*  * 设置此皆苦是为了业务需要，一般不需要关心网络请求回来的drawable，但是业务需要切换的地方的话，需要拿到网络请求回来的drawable
* Author: jiayizhan
* DateTime: 2019-05-22 11:02
* Version 1.0
*/
public interface OnDrawableDownloadListener {
    void onDrawable(Drawable drawable);
}
