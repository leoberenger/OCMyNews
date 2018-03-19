package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.openclassrooms.mynews.Controllers.Fragments.DisplaySearchFragment;
import com.openclassrooms.mynews.Controllers.Fragments.SearchPageFragment;
import com.openclassrooms.mynews.R;

import butterknife.BindView;

public class SearchActivity extends AppCompatActivity {

    private SearchPageFragment mSearchPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.configureAndShowSearchFragment();
    }

    private void configureAndShowSearchFragment() {
        if (mSearchPageFragment == null) {
            mSearchPageFragment = new SearchPageFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, mSearchPageFragment)
                    .commit();
        }
    }
}
