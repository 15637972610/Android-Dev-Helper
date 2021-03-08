package com.dkp.viewdemo.adapter.base;


import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者:majiang
 * 创建时间:21/1/7 下午8:35
 * des： good
 */
public interface OnItemClickListener<T> {
    void onItemClick (RecyclerView.ViewHolder holder, T t, int position);
}
