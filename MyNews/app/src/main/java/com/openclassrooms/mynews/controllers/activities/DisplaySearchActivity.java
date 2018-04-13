package com.openclassrooms.mynews.controllers.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.openclassrooms.mynews.controllers.fragments.DisplaySearchFragment;
import com.openclassrooms.mynews.models.Search;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.utils.SearchMgr;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplaySearchActivity extends AppCompatActivity {

    private SearchMgr searchMgr;
    private Search mSearch;
    @BindView(R.id.activity_display_search_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        ButterKnife.bind(this);

        this.configureToolbar();

        searchMgr = SearchMgr.getInstance();
        configureAndShowSearchFragment();
    }

    private void configureAndShowSearchFragment() {

        Intent intent = getIntent();
        //Get Search From Intent
        mSearch = searchMgr.getSearchFromIntent(intent);
        int lastReadPubDate = intent.getIntExtra("lastReadPubDate", 0);


        DisplaySearchFragment mDisplaySearchFragment = new DisplaySearchFragment();
        Bundle args = new Bundle();

        //Save Search to Bundle
        searchMgr.setSearchToBundle(args, mSearch);

        args.putInt("lastReadPubDate", lastReadPubDate);

        mDisplaySearchFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_display_search_frame_layout, mDisplaySearchFragment)
                .commit();
    }

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
