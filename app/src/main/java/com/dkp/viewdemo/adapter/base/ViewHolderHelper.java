/**
 * Copyright 2015 bingoogolapple
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

package com.dkp.viewdemo.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 作者:majiang
 * 创建时间:21/1/7 下午8:35
 * 描述:为AdapterView和RecyclerView的item设置常见属性（链式编程）
 */
public class ViewHolderHelper implements View.OnLongClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener {
    protected final SparseArrayCompat<View> mViews;
    protected OnItemChildClickListener mOnItemChildClickListener;
    protected OnItemChildLongClickListener mOnItemChildLongClickListener;
    protected OnItemChildCheckedChangeListener mOnItemChildCheckedChangeListener;
    protected OnRVItemChildTouchListener mOnRVItemChildTouchListener;
    protected View mConvertView;
    protected Context mContext;
    protected int mPosition;
    protected RecyclerViewHolder mRecyclerViewHolder;
    protected RecyclerView mRecyclerView;

    protected AdapterView mAdapterView;
    /**
     * 留着以后作为扩充对象
     */
    protected Object mObj;

    public ViewHolderHelper(ViewGroup adapterView, View convertView) {
        mViews = new SparseArrayCompat<>();
        mAdapterView = (AdapterView) adapterView;
        mConvertView = convertView;
        mContext = convertView.getContext();
    }

    public ViewHolderHelper(RecyclerView recyclerView, RecyclerViewHolder recyclerViewHolder) {
        mViews = new SparseArrayCompat<>();
        mRecyclerView = recyclerView;
        mRecyclerViewHolder = recyclerViewHolder;
        mConvertView = mRecyclerViewHolder.itemView;
        mContext = mConvertView.getContext();
    }

    public RecyclerViewHolder getRecyclerViewHolder() {
        return mRecyclerViewHolder;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        if (mRecyclerViewHolder != null) {
            return mRecyclerViewHolder.getAdapterPositionWrapper();
        }
        return mPosition;
    }

    /**
     * 设置item子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 为id为viewId的item子控件设置点击事件监听器
     *
     * @param viewId
     */
    public void setItemChildClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (mOnItemChildClickListener != null) {
                        if (mRecyclerView != null) {
                            mOnItemChildClickListener.onItemChildClick(mRecyclerView, v, getPosition());
                        } else if (mAdapterView != null) {
                            mOnItemChildClickListener.onItemChildClick(mAdapterView, v, getPosition());
                        }
                    }
                }
            });
        }

    }

    /**
     * 设置item子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    /**
     * 为id为viewId的item子控件设置长按事件监听器
     *
     * @param viewId
     */
    public void setItemChildLongClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(this);
        }
    }

    /**
     * 设置 RecyclerView 中的 item 子控件触摸事件监听器
     *
     * @param onRVItemChildTouchListener
     */
    public void setOnRVItemChildTouchListener(OnRVItemChildTouchListener onRVItemChildTouchListener) {
        mOnRVItemChildTouchListener = onRVItemChildTouchListener;
    }

    /**
     * 为 id 为 viewId 的 RecyclerView 的 item 子控件设置触摸事件监听器
     *
     * @param viewId
     */
    public void setRVItemChildTouchListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnTouchListener(this);
        }
    }

    /**
     * 设置item子控件选中状态变化事件监听器
     *
     * @param onItemChildCheckedChangeListener
     */
    public void setOnItemChildCheckedChangeListener(OnItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        mOnItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    /**
     * 为id为viewId的item子控件设置选中状态变化事件监听器
     *
     * @param viewId
     */
    public void setItemChildCheckedChangeListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null && view instanceof CompoundButton) {
            ((CompoundButton) view).setOnCheckedChangeListener(this);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mOnRVItemChildTouchListener != null && mRecyclerView != null) {
            return mOnRVItemChildTouchListener.onRvItemChildTouch(mRecyclerViewHolder, view, motionEvent);
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemChildLongClickListener != null) {
            if (mRecyclerView != null) {
                return mOnItemChildLongClickListener.onItemChildLongClick(mRecyclerView, v, getPosition());
            } else if (mAdapterView != null) {
                return mOnItemChildLongClickListener.onItemChildLongClick(mAdapterView, v, getPosition());
            }
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mOnItemChildCheckedChangeListener != null) {
            if (mRecyclerView != null) {
                RecyclerViewAdapter recyclerViewAdapter;

                RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                if (adapter instanceof HeaderAndFooterAdapter) {
                    recyclerViewAdapter = (RecyclerViewAdapter) ((HeaderAndFooterAdapter) adapter).getInnerAdapter();
                } else {
                    recyclerViewAdapter = (RecyclerViewAdapter) adapter;
                }
                if (!recyclerViewAdapter.isIgnoreCheckedChanged()) {
                    mOnItemChildCheckedChangeListener.onItemChildCheckedChanged(mRecyclerView, buttonView, getPosition(), isChecked);
                }
            } else if (mAdapterView != null) {
//                if (!((AdapterViewAdapter) mAdapterView.getAdapter()).isIgnoreCheckedChanged()) {
//                    mOnItemChildCheckedChangeListener.onItemChildCheckedChanged(mAdapterView, buttonView, getPosition(), isChecked);
//                }
            }
        }
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 通过ImageView的Id获取ImageView
     *
     * @param viewId
     * @return
     */
    public ImageView getImageView(@IdRes int viewId) {
        return getView(viewId);
    }

    /**
     * 通过TextView的Id获取TextView
     *
     * @param viewId
     * @return
     */
    public TextView getTextView(@IdRes int viewId) {
        return getView(viewId);
    }

    /**
     * 获取item的根控件
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }

    public void setObj(Object obj) {
        mObj = obj;
    }

    public Object getObj() {
        return mObj;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolderHelper setText(@IdRes int viewId, CharSequence text) {
        if (text == null) {
            text = "";
        }
        getTextView(viewId).setText(text);
        return this;
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param stringResId 字符串资源id
     * @return
     */
    public ViewHolderHelper setText(@IdRes int viewId, @StringRes int stringResId) {
        getTextView(viewId).setText(stringResId);
        return this;
    }

    /**
     * 设置对应id的控件的文字大小，单位为 sp
     *
     * @param viewId
     * @param size   文字大小，单位为 sp
     * @return
     */
    public ViewHolderHelper setTextSizeSp(@IdRes int viewId, float size) {
        getTextView(viewId).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 设置对应id的控件的文字是否为粗体
     *
     * @param viewId
     * @param isBold 是否为粗体
     * @return
     */
    public ViewHolderHelper setIsBold(@IdRes int viewId, boolean isBold) {
        getTextView(viewId).getPaint().setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }

    /**
     * 设置对应id的控件的html文本内容
     *
     * @param viewId
     * @param source html文本
     * @return
     */
    public ViewHolderHelper setHtml(@IdRes int viewId, String source) {
        if (source == null) {
            source = "";
        }
        getTextView(viewId).setText(Html.fromHtml(source));
        return this;
    }

    /**
     * 设置对应id的控件是否选中
     *
     * @param viewId
     * @param checked
     * @return
     */
    public ViewHolderHelper setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewHolderHelper setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolderHelper setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolderHelper setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public ViewHolderHelper setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolderHelper setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * @param viewId
     * @param textColorResId 颜色资源id
     * @return
     */
    public ViewHolderHelper setTextColorRes(@IdRes int viewId, @ColorRes int textColorResId) {
        getTextView(viewId).setTextColor(mContext.getResources().getColor(textColorResId));
        return this;
    }

    /**
     * @param viewId
     * @param textColor 颜色值
     * @return
     */
    public ViewHolderHelper setTextColor(@IdRes int viewId, int textColor) {
        getTextView(viewId).setTextColor(textColor);
        return this;
    }

    /**
     * @param viewId
     * @param backgroundResId 背景资源id
     * @return
     */
    public ViewHolderHelper setBackgroundRes(@IdRes int viewId, int backgroundResId) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundResId);
        return this;
    }

    /**
     * @param viewId
     * @param color  颜色值
     * @return
     */
    public ViewHolderHelper setBackgroundColor(@IdRes int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * @param viewId
     * @param colorResId 颜色值资源id
     * @return
     */
    public ViewHolderHelper setBackgroundColorRes(@IdRes int viewId, @ColorRes int colorResId) {
        View view = getView(viewId);
        view.setBackgroundColor(mContext.getResources().getColor(colorResId));
        return this;
    }

    /**
     * @param viewId
     * @param imageResId 图像资源id
     * @return
     */
    public ViewHolderHelper setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * 设置字体是否为粗体
     *
     * @param viewId
     * @param isBold
     * @return
     */
    public ViewHolderHelper setBold(@IdRes int viewId, boolean isBold) {
        getTextView(viewId).getPaint().setTypeface(isBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }
}