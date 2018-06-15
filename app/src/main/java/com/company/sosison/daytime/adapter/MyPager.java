package com.company.sosison.daytime.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.company.sosison.daytime.fragment.Tasks;
import com.company.sosison.daytime.fragment.TimeTable;

public class MyPager extends FragmentPagerAdapter {
    String[] tabs = {"Tasks","TimeTable"};
    public MyPager(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Tasks();
            case 1:
                return new TimeTable();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
