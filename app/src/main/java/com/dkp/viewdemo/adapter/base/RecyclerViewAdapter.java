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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者:majiang
 * 创建时间:21/1/7 下午8:35
 * @param <M> 适配的数据类型
 */
public abstract class RecyclerViewAdapter<M> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected int mDefaultItemLayoutId;
    protected Context mContext;
    protected List<M> mData;
    protected OnItemChildClickListener mOnItemChildClickListener;
    protected OnItemChildLongClickListener mOnItemChildLongClickListener;
    protected OnItemChildCheckedChangeListener mOnItemChildCheckedChangeListener;
    protected OnListItemClickListener mOnListItemClickListener;
    protected OnListItemLongClickListener mOnListItemLongClickListener;
    protected OnRVItemChildTouchListener mOnRVItemChildTouchListener;
    protected HeaderAndFooterAdapter mHeaderAndFooterAdapter;

    protected RecyclerView mRecyclerView;
    /**
     * 当前选中的索引
     */
    protected int mCheckedPosition = 0;
    /**
     * 在填充数据列表时，忽略选中状态变化
     */
    private boolean mIsIgnoreCheckedChanged = true;

    public RecyclerViewAdapter(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mData = new ArrayList<>();
    }

    public RecyclerViewAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        this(recyclerView);
        mDefaultItemLayoutId = defaultItemLayoutId;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(this, mRecyclerView, LayoutInflater.from(mContext).inflate(viewType, parent, false), mOnListItemClickListener, mOnListItemLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildClickListener(mOnItemChildClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(mOnItemChildLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildCheckedChangeListener(mOnItemChildCheckedChangeListener);
        viewHolder.getViewHolderHelper().setOnRVItemChildTouchListener(mOnRVItemChildTouchListener);
        setItemChildListener(viewHolder.getViewHolderHelper(), viewType);
        return viewHolder;
    }

    /**
     * 为item的孩子节点设置监听器，并不是每一个数据列表都要为item的子控件添加事件监听器，所以这里采用了空实现，需要设置事件监听器时重写该方法即可
     *
     * @param helper
     */
    protected void setItemChildListener(ViewHolderHelper helper, int viewType) {
    }

    @Override
    public int getItemViewType(int position) {
        if (mDefaultItemLayoutId == 0) {
            throw new RuntimeException("请在 " + this.getClass().getSimpleName() + " 中重写 getItemViewType 方法返回布局资源 id，或者使用 RecyclerViewAdapter 两个参数的构造方法 RecyclerViewAdapter(RecyclerView recyclerView, int itemLayoutId)");
        }
        return mDefaultItemLayoutId;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        // 在设置值的过程中忽略选中状态变化
        mIsIgnoreCheckedChanged = true;

        fillData(viewHolder.getViewHolderHelper(), position, getItem(position));

        mIsIgnoreCheckedChanged = false;
    }

    /**
     * 填充item数据
     *
     * @param helper
     * @param position
     * @param model
     */
    protected abstract void fillData(ViewHolderHelper helper, int position, M model);

    public boolean isIgnoreCheckedChanged() {
        return mIsIgnoreCheckedChanged;
    }

    /**
     * 设置item的点击事件监听器
     *
     * @param OnListItemClickListener
     */
    public void setOnListItemClickListener(OnListItemClickListener OnListItemClickListener) {
        mOnListItemClickListener = OnListItemClickListener;
    }

    /**
     * 设置item的长按事件监听器
     *
     * @param OnListItemLongClickListener
     */
    public void setOnListItemLongClickListener(OnListItemLongClickListener OnListItemLongClickListener) {
        mOnListItemLongClickListener = OnListItemLongClickListener;
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 设置item中的子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
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
     * 设置item子控件触摸事件监听器
     *
     * @param onRVItemChildTouchListener
     */
    public void setOnRVItemChildTouchListener(OnRVItemChildTouchListener onRVItemChildTouchListener) {
        mOnRVItemChildTouchListener = onRVItemChildTouchListener;
    }

    /**
     * 获取指定索引位置的数据模型
     *
     * @param position
     * @return
     */
    public M getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            return null;
        }
        return mData.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<M> getData() {
        return mData;
    }

    public final void notifyItemRangeChangedWrapper(int positionStart, int itemCount) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemRangeChangedWrapper(positionStart, itemCount);
        } else {
            mHeaderAndFooterAdapter.notifyItemRangeChanged(mHeaderAndFooterAdapter.getHeadersCount() + positionStart, itemCount);
        }
    }

    public final void notifyItemRangeInsertedWrapper(int positionStart, int itemCount) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            mHeaderAndFooterAdapter.notifyItemRangeInserted(mHeaderAndFooterAdapter.getHeadersCount() + positionStart, itemCount);
        }
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param data
     */
    public void addNewData(List<M> data) {
        if (BaseAdapterUtil.isListNotEmpty(data)) {
            mData.addAll(0, data);
            notifyItemRangeInsertedWrapper(0, data.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param data
     */
    public void addMoreData(List<M> data) {
        if (BaseAdapterUtil.isListNotEmpty(data)) {
            int positionStart = mData.size();
            mData.addAll(mData.size(), data);
            notifyItemRangeInsertedWrapper(positionStart, data.size());
        }
    }

    public final void notifyDataSetChangedWrapper() {
        if (mHeaderAndFooterAdapter == null) {
            notifyDataSetChanged();
        } else {
            mHeaderAndFooterAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param data
     */
    public void setData(List<M> data) {
        if (BaseAdapterUtil.isListNotEmpty(data)) {
            mData = data;
        } else {
            mData.clear();
        }
        notifyDataSetChangedWrapper();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mData.clear();
        notifyDataSetChangedWrapper();
    }

    public final void notifyItemRemovedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemRemoved(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemRemoved(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        if(mData != null && mData.size() > position){
            mData.remove(position);
            notifyItemRemovedWrapper(position);
        }
    }

    /**
     * 删除指定数据条目。该方法在 ItemTouchHelper.Callback 的 onSwiped 方法中调用
     *
     * @param viewHolder
     */
    public void removeItem(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        if (mHeaderAndFooterAdapter != null) {
            mData.remove(position - mHeaderAndFooterAdapter.getHeadersCount());
            mHeaderAndFooterAdapter.notifyItemRemoved(position);
        } else {
            removeItem(position);
        }
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(M model) {
        removeItem(mData.indexOf(model));
    }

    public final void notifyItemInsertedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemInserted(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemInserted(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, M model) {
        if(mData != null && mData.size() > 0 ){
            if(mData.size() >= position ){
                mData.add(position, model);
                notifyItemInsertedWrapper(position);
            }
        }else {
            mData.add(0, model);
            notifyItemInsertedWrapper(0);
        }
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(M model) {
        if(mData != null){
            addItem(mData.size(), model);
        }
    }

    public final void notifyItemChangedWrapper(int position) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemChanged(position);
        } else {
            mHeaderAndFooterAdapter.notifyItemChanged(mHeaderAndFooterAdapter.getHeadersCount() + position);
        }
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        if(mData != null && mData.size() > 0 ){
            if(mData.size() > location ){
                mData.set(location, newModel);
                notifyItemChangedWrapper(location);
            }
        }else {
            mData.add(0, newModel);
            notifyItemInsertedWrapper(0);
        }
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mData.indexOf(oldModel), newModel);
    }

    public final void notifyItemMovedWrapper(int fromPosition, int toPosition) {
        if (mHeaderAndFooterAdapter == null) {
            notifyItemMoved(fromPosition, toPosition);
        } else {
            mHeaderAndFooterAdapter.notifyItemMoved(mHeaderAndFooterAdapter.getHeadersCount() + fromPosition, mHeaderAndFooterAdapter.getHeadersCount() + toPosition);
        }
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        notifyItemChangedWrapper(fromPosition);
        notifyItemChangedWrapper(toPosition);

        // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作

        mData.add(toPosition, mData.remove(fromPosition));
        notifyItemMovedWrapper(fromPosition, toPosition);
    }

    /**
     * 移动数据条目的位置。该方法在 ItemTouchHelper.Callback 的 onMove 方法中调用
     *
     * @param from
     * @param to
     */
    public void moveItem(RecyclerView.ViewHolder from, RecyclerView.ViewHolder to) {
        int fromPosition = from.getAdapterPosition();
        int toPosition = to.getAdapterPosition();
        if (mHeaderAndFooterAdapter != null) {
            mHeaderAndFooterAdapter.notifyItemChanged(fromPosition);
            mHeaderAndFooterAdapter.notifyItemChanged(toPosition);

            // 要先执行上面的 notifyItemChanged,然后再执行下面的 moveItem 操作

            mData.add(toPosition - mHeaderAndFooterAdapter.getHeadersCount(), mData.remove(fromPosition - mHeaderAndFooterAdapter.getHeadersCount()));
            mHeaderAndFooterAdapter.notifyItemMoved(fromPosition, toPosition);
        } else {
            moveItem(fromPosition, toPosition);
        }
    }

    /**
     * @return 获取第一个数据模型
     */
    public
    @Nullable
    M getFirstItem() {
        return getItemCount() > 0 ? getItem(0) : null;
    }

    /**
     * @return 获取最后一个数据模型
     */
    public
    @Nullable
    M getLastItem() {
        return getItemCount() > 0 ? getItem(getItemCount() - 1) : null;
    }


    public void addHeaderView(View headerView) {
        getHeaderAndFooterAdapter().addHeaderView(headerView);
    }

    public void addFooterView(View footerView) {
        getHeaderAndFooterAdapter().addFooterView(footerView);
    }

    public void removeHeaderView(View view) {
        getHeaderAndFooterAdapter().removeHeaderView(view);
    }

    public void removeFooterView(View view) {
        getHeaderAndFooterAdapter().removeFooterView(view);
    }

    public int getHeadersCount() {
        return mHeaderAndFooterAdapter == null ? 0 : mHeaderAndFooterAdapter.getHeadersCount();
    }

    public int getFootersCount() {
        return mHeaderAndFooterAdapter == null ? 0 : mHeaderAndFooterAdapter.getFootersCount();
    }

    public HeaderAndFooterAdapter getHeaderAndFooterAdapter() {
        if (mHeaderAndFooterAdapter == null) {
            synchronized (RecyclerViewAdapter.this) {
                if (mHeaderAndFooterAdapter == null) {
                    mHeaderAndFooterAdapter = new HeaderAndFooterAdapter(this);
                }
            }
        }
        return mHeaderAndFooterAdapter;
    }

    /**
     * 是否是头部或尾部
     *
     * @param viewHolder
     * @return
     */
    public boolean isHeaderOrFooter(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() < getHeadersCount() || viewHolder.getAdapterPosition() >= getHeadersCount() + getItemCount();
    }

    /**
     * 设置当前选中索引
     *
     * @param position
     */
    public void setCheckedPosition(int position) {
        if (position == mCheckedPosition) {
            return;
        }

        int lastCheckedPosition = mCheckedPosition;
        mCheckedPosition = position;

        if (mCheckedPosition < getData().size()) {
            notifyItemChangedWrapper(mCheckedPosition);
        }
        if (lastCheckedPosition < getData().size()) {
            notifyItemChangedWrapper(lastCheckedPosition);
        }
    }

    /**
     * 获取当前选中的索引
     *
     * @return
     */
    public int getCheckedPosition() {
        return mCheckedPosition;
    }
}