/*
 *
 *  *
 *  *     京东金融团队Android基础开发库积累、沉淀、封装、共同整理
 *  *
 *  *     Copyright (c) 2017. @ 京东金融移动研发团队
 *  *
 *  *     技术支持：曾繁添<zengfantian@jd.com>
 *
 */

package com.dkp.viewdemo.widget.tab;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dkp.viewdemo.BaseLibApplication;
import com.dkp.viewdemo.R;
import com.dkp.viewdemo.image.JDImage;
import com.dkp.viewdemo.utils.CollectionUtils;
import com.dkp.viewdemo.utils.LogUtils;
import com.dkp.viewdemo.utils.ScreenUtil;
import com.dkp.viewdemo.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gonglei on 2017/8/3. APP底部Tab view，目前只支持4个tab，不支持下发文案和icon
 *
 * <p>修改-->切换时，使用add初始化 时 动态图标 by zhaowei on 2018/03/06
 */
public class HomeTabView extends LinearLayout {

    private static final String TAG = HomeTabView.class.getName();
    private Context mContext;

    private ChangeFragmentCallback mChangeFragmentCallback;
    private long mLastSwitchTime = 0;
    private IMainTabInterface mCurrentFragment;
    private OnTabClickListener onTabClickListener;
    // 后台配置的icon和文字
    private List<NavigationBarBean> mNavigationBarDataList = null;

    private ArrayList<IMainTabInterface> mFragments = new ArrayList<>();

    /**
     * 是否有皮肤并正在显示
     */
    public boolean hasSkin = false;

    protected List<TabIconBean> itemDataList = new ArrayList<>();

    private LinearLayout tabViewContainer;
    private int[] tabDefaultNormalResIds = {R.drawable.main_youth_tab_first_normal, R.drawable.main_youth_tab_second_normal,  R.drawable.main_youth_tab_fifth_normal,};
    private int[] tabDefaultSelectResIds = {R.drawable.main_youth_tab_first_highlight, R.drawable.main_youth_tab_second_highlight,  R.drawable.main_youth_tab_fifth_highlight,};
    private Drawable[] tabCacheDrawableNormal;
    private Drawable[] tabCacheDrawableSelected;
    private String[] tabTitles;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int selectedTabIndex = -1;
    int tabWidth;

    public HomeTabView (Context context){
        super(context);
        init(context, null);
    }

    public HomeTabView (Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, attrs);
    }

    public HomeTabView (Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init (Context context, AttributeSet attrs){
        itemDataList.clear();
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.zhyy_main_tab_view_layout1, this, true);
        tabViewContainer = findViewById(R.id.tab_list);
    }

    /**
     * 默认的初始化行为，
     *
     * @param fg
     * @return
     */
    public HomeTabView defaultInit (FragmentManager fg){
        defaultInit(fg, 0);
        return this;
    }

    public HomeTabView defaultInit (FragmentManager fg, int defaultIndex){
        defaultInit(fg, defaultIndex, tabTitles);
        return this;
    }

    public HomeTabView defaultInit (FragmentManager fg, int defaultIndex, String[] titles){
        fragmentManager = fg;
        fragmentTransaction = fragmentManager.beginTransaction();
        this.setTitleList(titles).build(defaultIndex);
        return this;
    }

    /**
     * 构建 NavigatorBar
     *
     * @param defaultIndex 默认选中的 tab index
     * @return
     */
    public HomeTabView build (int defaultIndex){
        int tabCount, index;
        tabCount = mFragments.size();
        if (tabCount == 0) {
            return this;
        }
        // 计算但个tab的宽度，添加tab itemview的时候要单独对第二个，第三个的margin进行处理
        tabWidth = (ScreenUtil.getScreenWidth()) / tabCount;
        tabCacheDrawableNormal = new Drawable[tabCount];
        tabCacheDrawableSelected = new Drawable[tabCount];
        for (index = 0; index < tabCount; index++) {
            View itemView = View.inflate(mContext, R.layout.layout_tab_item, null);
            ImageView tabIcon = itemView.findViewById(R.id.iv_tab_icon);
            TextView tabDes = itemView.findViewById(R.id.tv_tab_des);
            String text;
            String textColorNormal = "#666666";
            String textColorSelected = "#333333";

            if (!CollectionUtils.isEmpty(mNavigationBarDataList) && mNavigationBarDataList.size() > index) {
                if (tabCacheDrawableNormal[index] == null) {
                    JDImage.displayImage(mContext, mNavigationBarDataList.get(index).getImagesNormal(), tabIcon);
                } else {
                    tabIcon.setImageDrawable(tabCacheDrawableNormal[index]);
                }
                NavigationBarBean navigationBarBean = mNavigationBarDataList.get(index);
                if (navigationBarBean == null) {
                    continue;
                }
                text = navigationBarBean.getTitleText();
                textColorNormal = navigationBarBean.getNormalColor();
                textColorSelected = navigationBarBean.getSelectedColor();
            } else {
                text = tabTitles[index];
                tabIcon.setImageResource(tabDefaultNormalResIds[index]);
            }

            if (TextUtils.isEmpty(text)) {
                tabDes.setVisibility(View.GONE);
                ViewUtil.resetItemSize(46, 46, tabIcon);
            } else {
                tabDes.setVisibility(View.VISIBLE);
                tabDes.setText(text);
                tabDes.setTextColor(Color.parseColor(textColorNormal));
            }

            itemView.setTag(index);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick (View v){
                    int selectedIndex = (int) v.getTag();
                    if (onTabClickListener != null) {
                        if (onTabClickListener.onTabClickEvent(v, selectedIndex)) {
                            changeFragment(mFragments.get(selectedIndex), selectedIndex);
                        }
                    } else {
                        changeFragment(mFragments.get(selectedIndex), selectedIndex);
                    }
                }
            });
            // 添加tab item
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            params.width = tabWidth;
            tabViewContainer.addView(itemView, params);
        }
        changeFragment(mFragments.get(defaultIndex), defaultIndex);
        return this;
    }

    /**
     * 设置我tab的消息数量
     *
     * @param count
     */
    public void setMeTabMsgCount (int count){
    }

    /**
     * 获取tab名称
     *
     * @param index
     * @return
     */
    public String getTabName (int index){
        String tabName = "";
        try {
            if (!CollectionUtils.isEmpty(mNavigationBarDataList)) {
                tabName = mNavigationBarDataList.get(index).getTitleText();
            } else {
                tabName = tabTitles[index];
            }
        } catch (Throwable e) {
            LogUtils.e(TAG, "getTabName error = " + e.toString());
        }

        return tabName;
    }

    /**
     * 切换fragment
     *
     * @param fragment
     */
    public HomeTabView changeFragment (IMainTabInterface fragment, int index){
        if (index == selectedTabIndex) {
            return this;
        }
        if (mChangeFragmentCallback == null) {
            return this;
        }
        mChangeFragmentCallback.onUpdateCurrentFragment(mCurrentFragment);
        boolean isFrequently = (SystemClock.elapsedRealtime() - mLastSwitchTime) < 300;
        if (isFrequently) {
            if (fragment == mCurrentFragment) {
                mCurrentFragment.onSwitchFragmentAgain(fragment);
            }
            return this;
        }
        mLastSwitchTime = SystemClock.elapsedRealtime();

        if (fragment != mCurrentFragment) {
            mCurrentFragment = fragment;
        }
        mChangeFragmentCallback.onChangeFragment(fragment);

        View itemView = tabViewContainer.getChildAt(index);
        ImageView tabIcon = itemView.findViewById(R.id.iv_tab_icon);
        TextView tabDes = itemView.findViewById(R.id.tv_tab_des);

        updateTabStatus(true, index);

        if (selectedTabIndex >= 0) {
            // 重置上一tab
            updateTabStatus(false, selectedTabIndex);
        }
        selectedTabIndex = index;
        return this;
    }

    /**
     * 切换fragment回调
     */
    public interface ChangeFragmentCallback {
        void onChangeFragment (IMainTabInterface fragment);

        void onUpdateCurrentFragment (IMainTabInterface currentFragment);
    }


    /**
     * 缓存选中的tabIcon
     */
    private void cacheTabDrawablesSelected (){
        for (int index = 0; index < mNavigationBarDataList.size(); index++) {
            NavigationBarBean navigationBarBean = mNavigationBarDataList.get(index);
            final int finalIndex = index;
            Glide.with(BaseLibApplication.getInstance()).load(navigationBarBean.getImagesSelected())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed (@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource){
                            return false;
                        }

                        @Override
                        public boolean onResourceReady (Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource){
                            tabCacheDrawableSelected[finalIndex] = resource;
                            return false;
                        }
                    }).submit();
        }
    }

    /**
     * 缓存未选中的tabIcon
     */
    private void cacheTabDrawablesNormal (){
        for (int index = 0; index < mNavigationBarDataList.size(); index++) {
            NavigationBarBean navigationBarBean = mNavigationBarDataList.get(index);
            final int finalIndex = index;
            Glide.with(BaseLibApplication.getInstance()).load(navigationBarBean.getImagesNormal())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed (@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource){
                            return false;
                        }

                        @Override
                        public boolean onResourceReady (Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource){
                            tabCacheDrawableNormal[finalIndex] = resource;
                            return false;
                        }
                    }).submit();
        }

    }

    public HomeTabView setFragmentList (ArrayList<IMainTabInterface> fragmentList){
        mFragments = fragmentList;
        return this;
    }

    public HomeTabView setTitleList (String[] strs){
        tabTitles = strs;
        return this;
    }

    public HomeTabView setTabResIds (int[] tabResIds){
        this.tabDefaultNormalResIds = tabResIds;
        return this;
    }

    /**
     * 刷新item选中状态
     *
     * @param selected
     * @param index
     */
    private void updateTabStatus (boolean selected, int index){
        NavigationBarBean navigationBarBean = null;
        boolean useDefault = true;
        String textColorNormal = "#666666";
        String textColorSelected = "#333333";
        tabViewContainer.getChildAt(index).setSelected(selected);
        View itemView = tabViewContainer.getChildAt(index);
        ImageView tabIcon = itemView.findViewById(R.id.iv_tab_icon);
        TextView tabDes = itemView.findViewById(R.id.tv_tab_des);
        if (!CollectionUtils.isEmpty(mNavigationBarDataList) && mNavigationBarDataList.size() > index) {
            useDefault = false;
            navigationBarBean = mNavigationBarDataList.get(index);
        }
        if (navigationBarBean == null) {
            useDefault = true;
        }
        if (useDefault) {
            if (selected) {
                tabIcon.setImageResource(tabDefaultSelectResIds[index]);
                tabDes.setTextColor(Color.parseColor(textColorSelected));
            } else {
                tabDes.setTextColor(Color.parseColor(textColorNormal));
                tabIcon.setImageResource(tabDefaultNormalResIds[index]);
            }
        } else {
            loadCacheTab(selected, index, navigationBarBean, tabIcon, tabDes);
        }
    }

    /**
     * 加载缓存的tab
     *
     * @param selected
     * @param index
     * @param navigationBarBean
     * @param tabIcon
     * @param tabDes
     */
    private void loadCacheTab (boolean selected, int index, NavigationBarBean navigationBarBean, ImageView tabIcon, TextView tabDes){
        String textColorNormal = "#666666";
        String textColorSelected = "#333333";
        String normalColor = mNavigationBarDataList.get(index).getNormalColor();
        String colorSelected = mNavigationBarDataList.get(index).getSelectedColor();

        if (!TextUtils.isEmpty(normalColor)) {
            textColorNormal = normalColor;
        }
        if (!TextUtils.isEmpty(colorSelected)) {
            textColorSelected = colorSelected;
        }
        if (selected) {
            if (tabCacheDrawableSelected[index] == null) {
                JDImage.displayImage(mContext, navigationBarBean.getImagesSelected(), tabIcon);
            } else {
                tabIcon.setImageDrawable(tabCacheDrawableSelected[index]);
            }
            tabDes.setTextColor(Color.parseColor(textColorNormal));
        } else {
            if (tabCacheDrawableNormal[index] == null) {
                JDImage.displayImage(mContext, navigationBarBean.getImagesNormal(), tabIcon);
            } else {
                tabIcon.setImageDrawable(tabCacheDrawableNormal[index]);
            }
            tabDes.setTextColor(Color.parseColor(textColorSelected));
        }
    }

    public interface OnTabClickListener {
        /**
         * Tab 点击切换监听
         *
         * @param view
         * @param index
         * @return 是否继续切换tab，false则中断
         */
        boolean onTabClickEvent (View view, int index);
    }

    /**
     * 设置tab切换回调监听
     *
     * @param callback
     */
    public HomeTabView setChangeListener (ChangeFragmentCallback callback){
        this.mChangeFragmentCallback = callback;
        return this;
    }

    public HomeTabView setOnTabClickListener (HomeTabView.OnTabClickListener onTabClickListener){
        this.onTabClickListener = onTabClickListener;
        return this;
    }

    public HomeTabView setTabDataList (List<NavigationBarBean> list){
        if (list != null && !list.isEmpty()) {
            mNavigationBarDataList = list;
            cacheTabDrawablesSelected();
            cacheTabDrawablesNormal();
        }
        return this;
    }

    public static class NavigationBarBean {

        private String imagesNormal;
        private String imagesSelected;
        private String skipUrl;
        private String version;
        private String jumpData;
        String normalColor;
        String selectedColor;
        String titleText;

        public String getVersion (){
            return version;
        }

        public void setVersion (String version){
            this.version = version;
        }

        public String getJumpData (){
            return jumpData;
        }

        public void setJumpData (String jumpData){
            this.jumpData = jumpData;
        }

        public String getImagesNormal (){
            return imagesNormal;
        }

        public void setImagesNormal (String imagesNormal){
            this.imagesNormal = imagesNormal;
        }

        public String getImagesSelected (){
            return imagesSelected;
        }

        public void setImagesSelected (String imagesSelected){
            this.imagesSelected = imagesSelected;
        }

        public String getSkipUrl (){
            return skipUrl;
        }

        public void setSkipUrl (String skipUrl){
            this.skipUrl = skipUrl;
        }

        public String getNormalColor (){
            return normalColor;
        }

        public void setNormalColor (String normalColor){
            this.normalColor = normalColor;
        }

        public String getSelectedColor (){
            return selectedColor;
        }

        public void setSelectedColor (String selectedColor){
            this.selectedColor = selectedColor;
        }

        public String getTitleText (){
            return titleText;
        }

        public void setTitleText (String titleText){
            this.titleText = titleText;
        }
    }
}
