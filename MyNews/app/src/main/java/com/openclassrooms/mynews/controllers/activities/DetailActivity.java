package com.openclassrooms.mynews.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.openclassrooms.mynews.controllers.fragments.DetailFragment;
import com.openclassrooms.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.activity_detail_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureAndShowDetailFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowDetailFragment(){

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail_frame_layout);

        if (detailFragment == null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_frame_layout, detailFragment)
                    .commit();

            Intent intent = getIntent();
            String articleUrl = intent.getStringExtra("EXTRA_ARTICLE_URL");

            Bundle args = new Bundle();
            args.putString("articleUrl", articleUrl);
            detailFragment.setArguments(args);
        }
    }

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
