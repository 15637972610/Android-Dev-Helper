package com.dkp.viewdemo.adapter.base;




/**
 * 作者:majiang
 * 创建时间:21/1/7 下午8:35
 */
public interface OnItemLongClickListener<T> {
    void onItemLongClick (ViewHolder holder, T t, int position);
}
