package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.openclassrooms.netapp.Controllers.Fragments.DetailFragment;
import com.openclassrooms.netapp.R;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;
    public String articleUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.configureAndShowMainFragment();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowMainFragment(){

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail_frame_layout);

        if (detailFragment == null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_frame_layout, detailFragment)
                    .commit();

            Intent intent = getIntent();
            articleUrl = intent.getStringExtra("EXTRA_ARTICLE_URL");

            Bundle args = new Bundle();
            args.putString("articleUrl", articleUrl);
            detailFragment.setArguments(args);
        }
    }
}
