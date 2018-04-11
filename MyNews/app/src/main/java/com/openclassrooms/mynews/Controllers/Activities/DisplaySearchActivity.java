package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.openclassrooms.mynews.Controllers.Fragments.DisplayFragment;
import com.openclassrooms.mynews.Controllers.Fragments.DisplaySearchFragment;
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.SearchMgr;

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
        mSearch = searchMgr.getSearchFromIntent(intent);

        Log.e("DisplaySearchActivity", "query = " + mSearch.getQuery()
                + " desks = " + mSearch.getNewsDesk()
                + " beginDate = " + mSearch.getBeginDate()
                + " endDate = " + mSearch.getEndDate());

        DisplaySearchFragment mDisplaySearchFragment = new DisplaySearchFragment();
        Bundle args = new Bundle();
        searchMgr.setSearchToBundle(args, mSearch);
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
