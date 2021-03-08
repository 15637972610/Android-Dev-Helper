/**
 * Copyright 2015 bingoogolapple
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dkp.viewdemo.adapter.base;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 作者:majiang
 * 创建时间:21/1/7 下午8:35
 * 描述:适用于RecyclerView的item的ViewHolder
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    protected Context mContext;
    protected OnListItemClickListener mOnRVItemClickListener;
    protected OnListItemLongClickListener mOnRVItemLongClickListener;
    protected ViewHolderHelper mViewHolderHelper;
    protected RecyclerView mRecyclerView;
    protected RecyclerViewAdapter mRecyclerViewAdapter;

    public RecyclerViewHolder(RecyclerViewAdapter recyclerViewAdapter, RecyclerView recyclerView, View itemView, OnListItemClickListener onRVItemClickListener, OnListItemLongClickListener onRVItemLongClickListener) {
        super(itemView);
        mRecyclerViewAdapter = recyclerViewAdapter;
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mOnRVItemClickListener = onRVItemClickListener;
        mOnRVItemLongClickListener = onRVItemLongClickListener;
        itemView.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (v.getId() == RecyclerViewHolder.this.itemView.getId() && null != mOnRVItemClickListener) {
                    mOnRVItemClickListener.onListItemClick(mRecyclerView, v, getAdapterPositionWrapper());
                }
            }
        });
        itemView.setOnLongClickListener(this);

        mViewHolderHelper = new ViewHolderHelper(mRecyclerView, this);
    }

    public ViewHolderHelper getViewHolderHelper() {
        return mViewHolderHelper;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == this.itemView.getId() && null != mOnRVItemLongClickListener) {
            return mOnRVItemLongClickListener.onListItemLongClick(mRecyclerView, v, getAdapterPositionWrapper());
        }
        return false;
    }

    public int getAdapterPositionWrapper() {
        if (mRecyclerViewAdapter.getHeadersCount() > 0) {
            return getAdapterPosition() - mRecyclerViewAdapter.getHeadersCount();
        } else {
            return getAdapterPosition();
        }
    }
}