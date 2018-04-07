package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.openclassrooms.mynews.Controllers.Fragments.DisplayFragment;
import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;

public class DisplaySearchActivity extends AppCompatActivity {

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

            DisplayFragment mDisplayFragment = new DisplayFragment();

            Bundle args = new Bundle();
            args.putString(getResources().getString(R.string.bundle_search_type),
                    getResources().getString(R.string.bundle_search_type_query));

            args.putString("query", mQuery);
            args.putString("newsDesk", mNewsDesk);
            args.putInt("beginDate", mBeginDate);
            args.putInt("endDate", mEndDate);

            mDisplayFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_display_search_frame_layout, mDisplayFragment)
                    .commit();
        }
}
