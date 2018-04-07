package com.openclassrooms.mynews.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.openclassrooms.mynews.Controllers.Fragments.DisplaySearchFragment;
import com.openclassrooms.mynews.Controllers.Fragments.DisplayStoriesFragment;

/**
 * Created by berenger on 26/02/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter{

    private String STORY_TYPE_KEY = "storyType";
    private String TOP_STORIES_KEY = "topStories";
    private String MOST_POPULAR_KEY = "mostPopular";


    private String SEARCH_TYPE_KEY = "searchType";
    private String TOPIC_KEY = "topic";
    private String BUSINESS_KEY = "Business";
    private String SPORTS_KEY = "Sports";
    private String ARTS_KEY = "Arts";

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position){
            case 0:
                DisplayStoriesFragment displayTopStoriesFragment = new DisplayStoriesFragment();

                Bundle argsTS = new Bundle();
                argsTS.putString(STORY_TYPE_KEY, TOP_STORIES_KEY);
                displayTopStoriesFragment.setArguments(argsTS);

                fragment = displayTopStoriesFragment;
                break;

            case 1:
                DisplayStoriesFragment displayMostPopFragment = new DisplayStoriesFragment();

                Bundle argsMP = new Bundle();
                argsMP.putString(STORY_TYPE_KEY, MOST_POPULAR_KEY);
                displayMostPopFragment.setArguments(argsMP);

                fragment = displayMostPopFragment;
                break;

            case 2:
                DisplaySearchFragment businessFragment = new DisplaySearchFragment();

                Bundle argsBusiness = new Bundle();
                argsBusiness.putString(SEARCH_TYPE_KEY, TOPIC_KEY);
                argsBusiness.putString(TOPIC_KEY, BUSINESS_KEY);
                businessFragment.setArguments(argsBusiness);

                fragment = businessFragment;
                break;

            case 3:
                DisplaySearchFragment sportsFragment = new DisplaySearchFragment();

                Bundle argsSports = new Bundle();
                argsSports.putString(SEARCH_TYPE_KEY, TOPIC_KEY);
                argsSports.putString(TOPIC_KEY, SPORTS_KEY);
                sportsFragment.setArguments(argsSports);

                fragment = sportsFragment;
                break;

            case 4:
                DisplaySearchFragment artsFragment = new DisplaySearchFragment();

                Bundle argsArts = new Bundle();
                argsArts.putString(SEARCH_TYPE_KEY, TOPIC_KEY);
                argsArts.putString(TOPIC_KEY, ARTS_KEY);
                artsFragment.setArguments(argsArts);

                fragment = artsFragment;
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
