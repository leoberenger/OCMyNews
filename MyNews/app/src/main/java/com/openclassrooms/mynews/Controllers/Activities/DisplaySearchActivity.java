package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.openclassrooms.mynews.Controllers.Fragments.DisplayFragment;
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.SearchMgr;

public class DisplaySearchActivity extends AppCompatActivity {

    private SearchMgr searchMgr;
    private Search mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);

        searchMgr = SearchMgr.getInstance();
        configureAndShowSearchFragment();
    }

    private void configureAndShowSearchFragment() {

        Intent intent = getIntent();
        mSearch = searchMgr.getSearchFromIntent(intent);

        DisplayFragment mDisplayFragment = new DisplayFragment();
        Bundle args = new Bundle();
        searchMgr.setSearchToBundle(args, mSearch);
        mDisplayFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_display_search_frame_layout, mDisplayFragment)
                .commit();
    }
}
