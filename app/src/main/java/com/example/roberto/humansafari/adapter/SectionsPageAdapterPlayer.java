package com.example.roberto.humansafari.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberto on 16/01/18.
 */

public class SectionsPageAdapterPlayer extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public SectionsPageAdapterPlayer(FragmentManager fm) {
        super(fm);
    }

    public Fragment getFragmentByTitle(String title){
        int i=0;
        for(String s: mFragmentTitleList){
            if(s.equals(title)){
                return mFragmentList.get(i);
            }
            i++;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
