package com.dkp.viewdemo.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dkp.viewdemo.R;
import com.dkp.viewdemo.recycler.adapter.HolderOrientedTestAdapter;
import com.dkp.viewdemo.recycler.bean.HolderOrientedModel;

import java.util.ArrayList;

/**
 * Created by 杜坤鹏 on 2018/5/26.
 */

public class HolderOrientedActivity extends Activity {
    private RecyclerView myRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_demo);
        ArrayList<HolderOrientedModel> list = initTestData();

        HolderOrientedTestAdapter myAdapter = new HolderOrientedTestAdapter(HolderOrientedActivity.this);
        myRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        myRecyclerView.setAdapter(myAdapter);
        myAdapter.setData(list);
    }

    /**
     * 模拟测试用数据
     * @return
     */
    @NonNull
    private ArrayList<HolderOrientedModel> initTestData() {
        ArrayList<HolderOrientedModel> list = new ArrayList<HolderOrientedModel>();
        HolderOrientedModel baseModel = new HolderOrientedModel();
        baseModel.setId(1);
        baseModel.setType(1);

        HolderOrientedModel baseModel2 = new HolderOrientedModel();
        baseModel2.setId(2);
        baseModel2.setType(2);

        HolderOrientedModel baseModel3 = new HolderOrientedModel();
        baseModel3.setId(3);
        baseModel3.setType(3);
        list.add(baseModel);

        for (int i =0;i<20;i++){
            if (i==8){
                list.add(baseModel3);
            }else {
                list.add(baseModel2);
            }
        }
        return list;
    }
}
