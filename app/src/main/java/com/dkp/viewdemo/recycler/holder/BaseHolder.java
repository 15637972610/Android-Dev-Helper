package com.dkp.viewdemo.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dkp.viewdemo.recycler.bean.HolderOrientedModel;

import java.util.List;


public abstract class BaseHolder extends RecyclerView.ViewHolder  {
    public BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindItemView(List<HolderOrientedModel> mDataList, int position);

    public abstract void onItemClick(View v, int position);


}
