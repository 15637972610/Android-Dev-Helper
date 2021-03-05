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

public class TestHolderDefult extends BaseHolder {
    private final static String TAG = "TestHolderDefult";
    TextView tv;

    public TestHolderDefult(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.tv_1);

    }

    @Override
    public void bindItemView(List<HolderOrientedModel> mDataList, int position) {

        int id = mDataList.get(position).getId();
        if (tv!=null){

            tv.setText("defult text");
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        Log.d(TAG,"onItemClick pos = " +position);
    }


}
