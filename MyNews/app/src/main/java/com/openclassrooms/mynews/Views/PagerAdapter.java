package com.openclassrooms.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.openclassrooms.mynews.Controllers.Fragments.DisplaySearchFragment;
import com.openclassrooms.mynews.Controllers.Fragments.DisplayStoriesFragment;

/**
 * Created by berenger on 26/02/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter{

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position){
            case 0:
                fragment = DisplayStoriesFragment.newInstance("topStories");
                break;
            case 1:
                fragment = DisplayStoriesFragment.newInstance("mostPopular");
                break;
            case 2:
                fragment = DisplaySearchFragment.newInstance("topic", "Business");
                break;
            case 3:
                fragment = DisplaySearchFragment.newInstance("topic","Sports");
                break;
            case 4:
                fragment = DisplaySearchFragment.newInstance("topic","Arts");
                break;
        }

        return (fragment);
    }

    @Override
    public int getCount() {
        return (5);
    }

    @Override
    public CharSequence getPageTitle(int position){
        String pageTitle = "";
        switch (position){
            case 0: pageTitle = "Top Stories"; break;
            case 1: pageTitle = "Most Popular"; break;
            case 2: pageTitle = "Business"; break;
            case 3: pageTitle = "Sports"; break;
            case 4: pageTitle = "Arts"; break;
        }
        return pageTitle;
    }
}
