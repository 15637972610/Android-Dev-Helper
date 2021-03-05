package com.dkp.viewdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dkp.viewdemo.bean.HolderOrientedModel;
import com.dkp.viewdemo.holder.BaseHolder;
import com.dkp.viewdemo.holder.HolderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杜坤鹏 on 2019/5/27.
 */

public  class HolderOrientedAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    protected Context mContext;
    private LayoutInflater mInflater;
    private final static String TAG = "MyBaseAdapter";

    protected List<T> mDataList = new ArrayList<>();
    protected List<HolderOrientedModel> mList = new ArrayList<>();

    public HolderOrientedAdapter(Context context) {
        this.mContext =  context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder type"+viewType);
        return HolderFactory.createViewHolder(mInflater,parent,viewType);
    }

    @Override
    public void onBindViewHolder (@NonNull final BaseHolder holder, @SuppressLint("RecyclerView") final int position){
        Log.d(TAG,"onBindViewHolder position"+position);
        holder.bindItemView(mList,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.onItemClick(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList!=null? mList.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        HolderOrientedModel model =(HolderOrientedModel) mList.get(position);
        int type = model.getType();
        return type;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(ArrayList<HolderOrientedModel> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }


}
