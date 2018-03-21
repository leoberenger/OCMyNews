package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.mynews.Controllers.Fragments.ArticleViews.DisplaySearchFragment;
import com.openclassrooms.mynews.R;

public class DisplaySearchActivity extends AppCompatActivity {

    private DisplaySearchFragment mDisplaySearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        configureAndShowDisplaySearchFragment();
    }

    private void configureAndShowDisplaySearchFragment() {

        if (mDisplaySearchFragment == null) {
            mDisplaySearchFragment = new DisplaySearchFragment();

            /*Bundle args = new Bundle();
            args.putString("query", mQuery);
            args.putString("newsDesk", mNewsDesk);
            args.putInt("beginDate", mBeginDate);
            args.putInt("endDate", mEndDate);
            mDisplaySearchFragment.setArguments(args);
            */
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_display_search_frame_layout, mDisplaySearchFragment)
                    .commit();
        }
    }
}
