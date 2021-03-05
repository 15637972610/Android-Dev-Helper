package com.dkp.viewdemo.holder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dkp.viewdemo.R;
import com.dkp.viewdemo.bean.HolderOrientedModel;

import java.util.List;

/**
 * Created by 杜坤鹏 on 2019/5/28.
 */

public class TestHolder3 extends BaseHolder {
    private final static String TAG = "TestHolder3";
    TextView tv_3;

    public TestHolder3(View itemView) {
        super(itemView);
        tv_3 = (TextView) itemView.findViewById(R.id.tv_3);

    }


    @Override
    public void bindItemView(List<HolderOrientedModel> mDataList, int position) {
        int id = mDataList.get(position).getId();
        if (tv_3!=null){

            tv_3.setText("第"+id+"个类型的Item,position = "+position);
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        Log.d(TAG,"onItemClick pos = " +position);
    }
}
