package com.dkp.viewdemo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.dkp.viewdemo.ui.FragmentTab1;
import com.dkp.viewdemo.utils.ClickUtil;
import com.dkp.viewdemo.utils.LogUtils;
import com.dkp.viewdemo.utils.SPUtils;
import com.dkp.viewdemo.widget.tab.HomeTabView;
import com.dkp.viewdemo.widget.tab.IMainTabInterface;
import com.dkp.viewdemo.widget.tab.TabConfig;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeTabView.ChangeFragmentCallback {
    protected final String TAG = "MainActivity";
    private HomeTabView mHomeTabView;
    private ArrayList<IMainTabInterface> fragments = new ArrayList<>();
    private List<HomeTabView.NavigationBarBean> mTabList;
    private IMainTabInterface mCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHomeTabView = (HomeTabView) findViewById(R.id.tab_view);
        // 初始化底部tab
        TabConfig.init();
        getTabData();
        initButtomTab();
        //https://blog.csdn.net/stevennest/article/details/77877074
    }
    private void getTabData (){
    }

    @Override
    protected void onNewIntent (Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
        changeTab();
    }

    /** 切换tab */
    private void changeTab() {
        // 默认tab
        int value = getIntent().getIntExtra("ARGS_KEY_FRAGMENT_ID", 0);
        mHomeTabView.changeFragment(TabConfig.getFragment(TabConfig.getScheme(value)),value);
    }

    /** 初始化主界面底部tab */
    private void initButtomTab() {
        if (fragments != null && fragments.size() > 1) {
            return;
        }
        try {
            mTabList = new ArrayList<>();
            String tabJson = (String) SPUtils.get(TabConfig.SP_KEY_TAB, "");
            // 有远程配置
            if (!TextUtils.isEmpty(tabJson)) {
                mTabList = JSON.parseArray(tabJson, HomeTabView.NavigationBarBean.class);
                LogUtils.e("getTabData", "青春版--->" + "initButtomTab= "+",tabJson="+tabJson+"/n mTabList.size," + mTabList.size());
                for (int i = 0; i < mTabList.size(); i++) {
                    HomeTabView.NavigationBarBean tab = mTabList.get(i);
                    // 初始化fragment
                    IMainTabInterface fragment;
                    Bundle args = new Bundle();
                    if (TabConfig.isWebScheme(tab.getJumpData())) {
                        // 单独处理webview fragment
                        fragment = new FragmentTab1();
//                        args.putString(
//                                "web_url",
//                                CommonManager.generateJumpH5UrlWithToken(tab.getJumpData()));
                    } else {
                        fragment = TabConfig.getTabFragmentByScheme(tab.getJumpData());
                        args.putString("title", TabConfig.getTabName(tab.getTitleText()));
                    }
                    ((Fragment)fragment).setArguments(args);
                    fragments.add(fragment);
                    // 缓存首页tab信息
                }
            } else {// 默认页面
                initDefaultFragments();
            }
            // 设置tab bar的数据
            initTabBar(mTabList);
        } catch (Throwable e) {
            System.out.println("--------------报错了-------------");
            e.printStackTrace();
            // 异常情况走兜底逻辑
            mTabList.clear();
            initDefaultFragments();
            initTabBar(null);
            // 清空错误的sp数据
            SPUtils.remove(BaseLibApplication.getInstance(), TabConfig.SP_KEY_TAB);
        }
    }

    /**
     * 初始化默认页面
     */
    private void initDefaultFragments() {
        try {
            fragments = new ArrayList<>();
            for (int i = 0; i < TabConfig.DEFAULT_SCHEME_ARRAY.length; i++) {
                Bundle args = new Bundle();
                IMainTabInterface fragment = TabConfig.getTabFragment(i);
                if (i == 1||i == 2){
//                    args.putString(
//                            IWebConstant.ARGS_KEY_WEBURL,
//                            CommonManager.generateJumpH5UrlWithToken(IYouthConstant.TAB_2_WEBURL));
                }else {
                    args.putString("title", TabConfig.getTabName(TabConfig.DEFAULT_SCHEME_ARRAY[i]));
                }
                ((Fragment)fragment).setArguments(args);
                fragments.add(fragment);
                LogUtils.e(TAG, "initDefaultFragments " + fragments.size());
            }
        } catch (Throwable e) {
            LogUtils.e(TAG, "initDefaultFragments " + e.toString());
        }
    }


    private void initTabBar(final List<HomeTabView.NavigationBarBean> tabList) {
        // 根据默认scheme创建默认的tabName和icon数组
        String[] tabNameArray = new String[TabConfig.DEFAULT_SCHEME_ARRAY.length];
        int[] tabIconArray = new int[TabConfig.DEFAULT_SCHEME_ARRAY.length];
        for (int i = 0; i < TabConfig.DEFAULT_SCHEME_ARRAY.length; i++) {
            tabNameArray[i] = TabConfig.getTabName(TabConfig.DEFAULT_SCHEME_ARRAY[i]);
            tabIconArray[i] = TabConfig.getIcon(TabConfig.DEFAULT_SCHEME_ARRAY[i]);
        }
        // 初始化底部导航栏
        mHomeTabView.setFragmentList(fragments)
                .setChangeListener(this)
                .setTitleList(tabNameArray)
                .setTabResIds(tabIconArray)
                .setTabDataList(tabList)
                .setOnTabClickListener(new HomeTabView.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int index) {
                        if (!ClickUtil.isNotFastClick(300)){
                            if (mCurrentFragment instanceof FragmentTab1){
//                                ((FragmentTab1)mCurrentFragment).onTabFastClick();
                            }
                        }
                        return true;
                    }
                })
                .defaultInit(getFragmentManager(), 0);

        changeTab();
    }

    @Override
    public void onChangeFragment (IMainTabInterface fragment){

    }

    @Override
    public void onUpdateCurrentFragment (IMainTabInterface currentFragment){

    }
}
