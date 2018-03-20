package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openclassrooms.mynews.Controllers.Fragments.SearchFragment;
import com.openclassrooms.mynews.R;

public class SearchActivity extends AppCompatActivity {

    private SearchFragment mSearchPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.configureAndShowSearchFragment();
    }

    private void configureAndShowSearchFragment() {
        if (mSearchPageFragment == null) {
            mSearchPageFragment = new SearchFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, mSearchPageFragment)
                    .commit();
        }
    }
}
