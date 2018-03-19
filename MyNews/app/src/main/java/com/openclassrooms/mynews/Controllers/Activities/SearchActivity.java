package com.openclassrooms.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.openclassrooms.mynews.Controllers.Fragments.SearchArticleFragment;
import com.openclassrooms.mynews.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.fragment_main_query_input)
    EditText queryInput;

    //FOR SEARCH
    public String mQuery;    //"france"
    public String mNewsDesk = "Travel";
    public int mBeginDate = 20170910;
    public int mEndDate = 20171001;

    private SearchArticleFragment mSearchArticleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.fragment_searcharticles_button)
    public void submit(View view) {
        mQuery = queryInput.getText().toString();
        this.configureAndShowMainFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowMainFragment() {

        mSearchArticleFragment = (SearchArticleFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);

        if (mSearchArticleFragment == null) {
            mSearchArticleFragment = new SearchArticleFragment();

            Bundle args = new Bundle();
            args.putString("query", mQuery);
            args.putString("newsDesk", mNewsDesk);
            args.putInt("beginDate", mBeginDate);
            args.putInt("endDate", mEndDate);
            mSearchArticleFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, mSearchArticleFragment)
                    .commit();
        }
    }
}
