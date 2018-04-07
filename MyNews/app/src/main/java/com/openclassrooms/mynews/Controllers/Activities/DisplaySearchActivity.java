package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.openclassrooms.mynews.Controllers.Fragments.DisplaySearchFragment;
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;

public class DisplaySearchActivity extends AppCompatActivity {

    private String SEARCH_TYPE_KEY = "searchType";
    private String SEARCH_TYPE_QUERY_KEY = "query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        configureAndShowSearchFragment();
    }

    private void configureAndShowSearchFragment() {

            Search search = new Search();
            Bundle bundle = getIntent().getExtras();

            String mQuery = search.getQuery(bundle);
            String mNewsDesk = search.getNewsDesk(bundle);
            int mBeginDate = search.getBeginDate(bundle);
            int mEndDate = search.getEndDate(bundle);

            Log.e("DisplayActivity", "mQuery="+mQuery+", mNewsDesk = " + mNewsDesk);

            DisplaySearchFragment mDisplaySearchFragment = new DisplaySearchFragment();

            Bundle args = new Bundle();
            args.putString(SEARCH_TYPE_KEY, SEARCH_TYPE_QUERY_KEY);
            args.putString("query", mQuery);
            args.putString("newsDesk", mNewsDesk);
            args.putInt("beginDate", mBeginDate);
            args.putInt("endDate", mEndDate);

            mDisplaySearchFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_display_search_frame_layout, mDisplaySearchFragment)
                    .commit();
        }
}
