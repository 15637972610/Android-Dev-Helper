package com.dkp.viewdemo.recycler.holder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dkp.viewdemo.R;
import com.dkp.viewdemo.recycler.bean.HolderOrientedModel;

import java.util.List;

/**
 * Created by 杜坤鹏 on 2019/5/28.
 */

public class TestHolder2 extends BaseHolder {
    private final static String TAG = "TestHolder2";
    TextView tv_2;

    public TestHolder2(View itemView) {
        super(itemView);
        tv_2 = (TextView) itemView.findViewById(R.id.tv_2);

    }


    @Override
    public void bindItemView(List<HolderOrientedModel> mDataList, int position) {
        if (mDataList==null)return;

        if (mDataList.get(position)==null)return;

        int id = mDataList.get(position).getId();
        if (tv_2!=null){
            tv_2.setText("第"+id+"个类型的Item,p = "+position);
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        Log.d(TAG,"onItemClick pos = " +position);
    }

}
