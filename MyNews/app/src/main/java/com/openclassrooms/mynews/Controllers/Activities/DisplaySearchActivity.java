package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.mynews.Controllers.Fragments.ArticleViews.DisplaySearchFragment;
import com.openclassrooms.mynews.R;

public class DisplaySearchActivity extends AppCompatActivity {

    private DisplaySearchFragment mDisplaySearchFragment;
    private String EXTRA_QUERY = "EXTRA_QUERY";
    private String EXTRA_NEWS_DESKS = "EXTRA_NEWS_DESKS";
    private String EXTRA_BEGIN_DATE = "EXTRA_BEGIN_DATE";
    private String EXTRA_END_DATE = "EXTRA_END_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        configureAndShowDisplaySearchFragment();
    }

    private void configureAndShowDisplaySearchFragment() {

        if (mDisplaySearchFragment == null) {
            mDisplaySearchFragment = new DisplaySearchFragment();

            Bundle bundle = getIntent().getExtras();
            String mQuery = bundle.getString(EXTRA_QUERY);
            String mNewsDesk = bundle.getString(EXTRA_NEWS_DESKS);
            int mBeginDate = bundle.getInt(EXTRA_BEGIN_DATE);
            int mEndDate = bundle.getInt(EXTRA_END_DATE);

            Bundle args = new Bundle();
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
}
