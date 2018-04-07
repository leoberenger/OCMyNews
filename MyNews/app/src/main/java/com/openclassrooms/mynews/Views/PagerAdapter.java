package com.openclassrooms.mynews.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.openclassrooms.mynews.Controllers.Fragments.DisplayFragment;
import com.openclassrooms.mynews.Controllers.Fragments.DisplaySearchFragment;

/**
 * Created by berenger on 26/02/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    String SEARCH_TYPE_KEY = "searchType";
    String SEARCH_TYPE_TOPIC = "topic";
    String SEARCH_TYPE_TOP_STORIES = "topStories";
    String SEARCH_TYPE_MOST_POPULAR = "mostPopular";
    String SEARCH_TOPIC_BUSINESS = "Business";
    String SEARCH_TOPIC_SPORTS = "Sports";
    String SEARCH_TOPIC_ARTS = "Arts";

    int nbTabs = 5;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new Fragment();
        Fragment displayFragment = new DisplayFragment();
        Fragment displaySearchFragment = new DisplaySearchFragment();

        Bundle args = new Bundle();

        switch (position){
            case 0:
                args.putString(SEARCH_TYPE_KEY, SEARCH_TYPE_TOP_STORIES);
                displayFragment.setArguments(args);
                fragment = displayFragment;
                break;

            case 1:
                args.putString(SEARCH_TYPE_KEY, SEARCH_TYPE_MOST_POPULAR);
                displayFragment.setArguments(args);
                fragment = displayFragment;
                break;

            case 2:
                args.putString(SEARCH_TYPE_KEY, SEARCH_TYPE_TOPIC);
                args.putString(SEARCH_TYPE_TOPIC, SEARCH_TOPIC_BUSINESS);
                displaySearchFragment.setArguments(args);
                fragment = displaySearchFragment;
                break;

            case 3:
                args.putString(SEARCH_TYPE_KEY, SEARCH_TYPE_TOPIC);
                args.putString(SEARCH_TYPE_TOPIC, SEARCH_TOPIC_SPORTS);
                displaySearchFragment.setArguments(args);
                fragment = displaySearchFragment;
                break;

            case 4:
                args.putString(SEARCH_TYPE_KEY, SEARCH_TYPE_TOPIC);
                args.putString(SEARCH_TYPE_TOPIC, SEARCH_TOPIC_ARTS);
                displaySearchFragment.setArguments(args);
                fragment = displaySearchFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return nbTabs;
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
