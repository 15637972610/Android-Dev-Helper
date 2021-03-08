package com.dkp.viewdemo.adapter.vp;



import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * viewpage 适配器
 * @date 2021/2
 * @author majiang
 */
public class CustomerPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<CharSequence> mFragmentTitleList = new ArrayList<>();
    private Fragment mCurrentFragment;


    public CustomerPagerAdapter (FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return (mFragmentList != null && mFragmentList.size() > position) ? mFragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    public void addFrag(Fragment fragment, CharSequence title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void addFrag2(List<Fragment> fragment, List<String> title) {
        mFragmentList.addAll(fragment);
        mFragmentTitleList.addAll(title);
    }

    public void cleanFrag() {
        mFragmentList.clear();
        mFragmentTitleList.clear();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    //用于区分具体属于哪个fragment
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
