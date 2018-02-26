package com.openclassrooms.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.openclassrooms.mynews.Controllers.Fragments.PageFragment;

/**
 * Created by berenger on 26/02/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter{

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return (PageFragment.newInstance(position));
    }

    @Override
    public int getCount() {
        return (5);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "Page"+position;
    }
}
