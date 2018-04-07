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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        configureAndShowSearchFragment();
    }

    private void configureAndShowSearchFragment() {

        SearchMgr searchMgr = SearchMgr.getInstance();
        Intent intent = getIntent();
        Search search = searchMgr.getSearchFromIntent(intent);


        Log.e("DisplayActivity", "mQuery="+search.getQuery()+", mNewsDesk = " +search.getNewsDesk());

        DisplayFragment mDisplayFragment = new DisplayFragment();
        Bundle args = new Bundle();
        searchMgr.setSearchToBundle(args, search);
        mDisplayFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_display_search_frame_layout, mDisplayFragment)
                .commit();
    }
}
