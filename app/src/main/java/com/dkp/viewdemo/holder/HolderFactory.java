package com.dkp.viewdemo.holder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkp.viewdemo.R;

/**
 * Created by 杜坤鹏 on 2019/5/28.
 * 根据不同的type生产Holder
 */

public class HolderFactory {
    private final static String TAG = "HolderFactory";
    private final static int HOLDER_DEFULT = 0;
    private final static int HOLDER_ITEM_ONE = HOLDER_DEFULT+1;
    private final static int HOLDER_ITEM_TWO = HOLDER_ITEM_ONE+1;
    private final static int HOLDER_ITEM_THREE = HOLDER_ITEM_TWO+1;


    public static BaseHolder createViewHolder(LayoutInflater mInflater, ViewGroup parent , int viewType){

        BaseHolder baseHoler=null;
        switch (viewType){
            case HOLDER_ITEM_ONE:
                Log.d(TAG,viewType+",1");
                View view = mInflater.inflate(R.layout.item_1,parent,false);
                baseHoler = new TestHolder(view);
                break;
            case HOLDER_ITEM_TWO:
                Log.d(TAG,viewType+",2");
                View view2 = mInflater.inflate(R.layout.item_2,parent,false);
                baseHoler = new TestHolder2(view2);
                break;
            case HOLDER_ITEM_THREE:
                Log.d(TAG,viewType+",3");
                View view3 = mInflater.inflate(R.layout.item_3,parent,false);
                baseHoler = new TestHolder3(view3);
                break;
            case HOLDER_DEFULT:
                View viewdefult = mInflater.inflate(R.layout.item_3,parent,false);
                baseHoler = new TestHolder3(viewdefult);
                Log.d(TAG,viewType+",4");
               break;
        }
        return baseHoler;

    }



}
