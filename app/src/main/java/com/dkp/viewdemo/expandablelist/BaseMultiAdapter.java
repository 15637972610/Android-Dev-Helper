package com.dkp.viewdemo.expandablelist;

import android.content.Context;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseMultiAdapter<T extends MultiItemEntity> extends RecyclerView.Adapter<SuperViewHolder> {
    protected Context mContext;
    private LayoutInflater mInflater;

    /**
     * layouts indexed with their types
     */
    private SparseArray<Integer> layouts;
    private static final int DEFAULT_VIEW_TYPE = -0xff;

    protected List<T> mDataList = new ArrayList<>();

    public BaseMultiAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataList.get(position);
        if (item instanceof MultiItemEntity) {
            return ((MultiItemEntity)item).getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(getLayoutId(viewType), parent, false);
        return new SuperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        onBindItemHolder(holder, position);
    }

    //局部刷新关键：带payload的这个onBindViewHolder方法必须实现
    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindItemHolder(holder, position);
        } else {
            onBindItemHolder(holder, position, payloads);
        }

    }

    //根据ViewType获取LayoutId
    public int getLayoutId(int viewType) {
        return layouts.get(viewType);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }

    public abstract void onBindItemHolder(SuperViewHolder holder, int position);

    public void onBindItemHolder(SuperViewHolder holder, int position, List<Object> payloads){

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if(position != (getDataList().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.mDataList.size()-position);
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * Expand an expandable item
     *
     * @param position     position of the item
     * @param animate      expand items with animation
     * @param shouldNotify notify the RecyclerView to rebind items, <strong>false</strong> if you want to do it yourself.
     * @return the number of items that have been added.
     */
    public int expand(@IntRange(from = 0) int position, boolean animate, boolean shouldNotify) {
        position -= getHeaderLayoutCount();//position - headersize 返回的是当前 可展开的item的position

        IExpandable expandable = getExpandableItem(position);//通过position获取一个bean
        if (expandable == null) {
            return 0;//如果数据为空 返回0
        }
        if (!hasSubItems(expandable)) {//如果这个Item没有子节点；依据是expandable.getSubItems返回的list 为空或 size是0
            expandable.setExpanded(false);//设置不可展开并返回
            return 0;
        }
        int subItemCount = 0;
        if (!expandable.isExpanded()) {//当前节点没有展开
            List list = expandable.getSubItems();//获取子节点的集合
            mDataList.addAll(position + 1, list);//把数据插入到mDataList中
            subItemCount += recursiveExpand(position + 1, list);//展开的子节点的数量

            expandable.setExpanded(true);//设置为展开状态
            subItemCount += list.size();//统计当前节点所有展开子节点数
        }
        int parentPos = position + getHeaderLayoutCount();//在列表中的实际位置
        if (shouldNotify) {//是否刷新，是
            if (animate) {//有动画
                notifyItemChanged(parentPos);//刷新当前Item
                notifyItemRangeInserted(parentPos + 1, subItemCount);//刷新展开的所有item
            } else {//没动画
                notifyDataSetChanged();
            }
        }
        return subItemCount;
    }

    /**
     * Expand an expandable item
     *
     * @param position position of the item, which includes the header layout count.
     * @param animate  expand items with animation
     * @return the number of items that have been added.
     */
    public int expand(@IntRange(from = 0) int position, boolean animate) {
        return expand(position, animate, true);
    }

    /**
     * Expand an expandable item with animation.
     *
     * @param position position of the item, which includes the header layout count.
     * @return the number of items that have been added.
     */
    public int expand(@IntRange(from = 0) int position) {
        return expand(position, true, true);
    }

    public int expandAll(int position, boolean animate, boolean notify) {
        position -= getHeaderLayoutCount();//除去header后的list的

        T endItem = null;
        if (position + 1 < this.mDataList.size()) {
            endItem = getItem(position + 1);//或取当前Item对应的bean
        }

        IExpandable expandable = getExpandableItem(position);//校验 这个Item bean 是IExpandable类型
        if (!hasSubItems(expandable)) {//如果当前节点没有子节点，返回
            return 0;
        }

        //展开当前节点，统计展开的节点的数量
        int count = expand(position + getHeaderLayoutCount(), false, false);
        for (int i = position + 1; i < this.mDataList.size(); i++) {
            T item = getItem(i);

            if (item == endItem) {
                break;
            }
            if (isExpandable(item)) {
                count += expand(i + getHeaderLayoutCount(), false, false);
            }
        }
        //刷新view
        if (notify) {
            if (animate) {
                notifyItemRangeInserted(position + getHeaderLayoutCount() + 1, count);
            } else {
                notifyDataSetChanged();
            }
        }
        return count;
    }

    /**
     * expand the item and all its subItems
     *
     * @param position position of the item, which includes the header layout count.
     * @param init     whether you are initializing the recyclerView or not.
     *                 if <strong>true</strong>, it won't notify recyclerView to redraw UI.
     * @return the number of items that have been added to the adapter.
     */
    public int expandAll(int position, boolean init) {
        return expandAll(position, true, !init);
    }

    private int recursiveCollapse(@IntRange(from = 0) int position) {
        T item = getItem(position);
        if (!isExpandable(item)) {
            return 0;
        }
        IExpandable expandable = (IExpandable) item;//IExpandable类型校验
        int subItemCount = 0;
        if (expandable.isExpanded()) {//当前节点是展开状态
            List<T> subItems = expandable.getSubItems();//获取子节点的集合
            for (int i = subItems.size() - 1; i >= 0; i--) {//从后往前
                T subItem = subItems.get(i);
                int pos = getItemPosition(subItem);
                if (pos < 0) {
                    continue;
                }
                if (subItem instanceof IExpandable) {
                    subItemCount += recursiveCollapse(pos);
                }
                mDataList.remove(pos);//从当前list里移除
                subItemCount++;
            }
        }
        return subItemCount;
    }

    private int recursiveExpand(int position, @NonNull List list) {
        int count = 0;
        int pos = position + list.size() - 1;
        for (int i = list.size() - 1; i >= 0; i--, pos--) {
            if (list.get(i) instanceof IExpandable) {
                IExpandable item = (IExpandable) list.get(i);//倒序，获取每一个具体的子节点
                if (item.isExpanded() && hasSubItems(item)) {//如果当前是展开状态，并且该子节点还有子节点
                    List subList = item.getSubItems();//获取子节点集合
                    mDataList.addAll(pos + 1, subList);//插入到mDataList
                    int subItemCount = recursiveExpand(pos + 1, subList);
                    count += subItemCount;
                }
            }
        }
        return count;

    }

    /**
     * 判断是否有子节点
     * @param item
     * @return
     */
    private boolean hasSubItems(IExpandable item) {
        List list = item.getSubItems();
        return list != null && list.size() > 0;
    }

    /**
     * 对当前item进行类型检查
     * @param item
     * @return
     */
    private boolean isExpandable(T item) {
        return item != null && item instanceof IExpandable;
    }

    /**
     * 返回一个IExpandable类型的item
     * @param position
     * @return
     */
    private IExpandable getExpandableItem(int position) {
        T item = getItem(position);
        if (isExpandable(item)) {
            return (IExpandable) item;
        } else {
            return null;
        }
    }

    /**
     * Collapse an expandable item that has been expanded..
     *
     * @param position the position of the item, which includes the header layout count.
     * @param animate  collapse with animation or not.
     * @param notify   notify the recyclerView refresh UI or not.
     * @return the number of subItems collapsed.
     */
    public int collapse(@IntRange(from = 0) int position, boolean animate, boolean notify) {
        position -= getHeaderLayoutCount();

        IExpandable expandable = getExpandableItem(position);
        if (expandable == null) {
            return 0;
        }
        int subItemCount = recursiveCollapse(position);//移除该节点下展开的节点，返回移除的个数
        expandable.setExpanded(false);
        int parentPos = position + getHeaderLayoutCount();
        if (notify) {
            if (animate) {
                notifyItemChanged(parentPos);
                notifyItemRangeRemoved(parentPos + 1, subItemCount);
            } else {
                notifyDataSetChanged();
            }
        }
        return subItemCount;
    }

    /**
     * Collapse an expandable item that has been expanded..
     *
     * @param position the position of the item, which includes the header layout count.
     * @return the number of subItems collapsed.
     */
    public int collapse(@IntRange(from = 0) int position) {
        return collapse(position, true, true);
    }

    /**
     * Collapse an expandable item that has been expanded..
     *
     * @param position the position of the item, which includes the header layout count.
     * @return the number of subItems collapsed.
     */
    public int collapse(@IntRange(from = 0) int position, boolean animate) {
        return collapse(position, animate, true);
    }

    private int getItemPosition(T item) {
        return item != null && mDataList != null && !mDataList.isEmpty() ? mDataList.indexOf(item) : -1;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public T getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * if addHeaderView will be return 1, if not will be return 0
     */
    public int getHeaderLayoutCount() {

        return 0;
    }
}
