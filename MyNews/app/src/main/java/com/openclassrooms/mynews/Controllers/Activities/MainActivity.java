package com.openclassrooms.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.openclassrooms.mynews.Controllers.Fragments.ArticleViews.MostPopularFragment;
import com.openclassrooms.mynews.Controllers.Fragments.ArticleViews.TopStoriesFragment;
import com.openclassrooms.mynews.Controllers.Fragments.ArticleViews.TopicFragment;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Views.PagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    //FRAGMENTS
    private TopStoriesFragment mTopStoriesFragment;
    private MostPopularFragment mMostPopularFragment;
    private TopicFragment mSportsFragment;
    private TopicFragment mBusinessFragment;
    private TopicFragment mArtsFragment;

    private static final int FRAGMENT_TOPSTORIES = 0;
    private static final int FRAGMENT_MOSTPOPULAR = 1;
    private static final int FRAGMENT_SPORTS = 2;
    private static final int FRAGMENT_BUSINESS = 3;
    private static final int FRAGMENT_ARTS = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolbar();
        this.configureViewPagerAndTabs();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        //Handle back click to close menu
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_more:
                Intent intent2 = new Intent(this, NotificationActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle Navigation Item Click
        int id = item.getItemId();

        switch(id){
            case R.id.activity_main_drawer_topstories:
                this.showFragment(FRAGMENT_TOPSTORIES);
                break;
            case R.id.activity_main_drawer_mostpopular:
                this.showFragment(FRAGMENT_MOSTPOPULAR);
                break;
            case R.id.activity_main_drawer_sports:
                this.showFragment(FRAGMENT_SPORTS);
                break;
            case R.id.activity_main_drawer_business:
                this.showFragment(FRAGMENT_BUSINESS);
                break;
            case R.id.activity_main_drawer_arts:
                this.showFragment(FRAGMENT_ARTS);
                break;
            default: break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    //----------------------------
    // FRAGMENTS
    //----------------------------
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_TOPSTORIES:
                this.showTopStoriesFragment();
                break;
            case FRAGMENT_MOSTPOPULAR:
                this.showMostPopularFragment();
                break;
            case FRAGMENT_SPORTS:
                this.showSportsFragment();
                break;
            case FRAGMENT_BUSINESS:
                this.showBusinessFragment();
                break;
            case FRAGMENT_ARTS:
                this.showArtsFragment();
                break;
            default:
                break;
        }
    }

    private void showTopStoriesFragment(){
        if(this.mTopStoriesFragment == null)
            this.mTopStoriesFragment = TopStoriesFragment.newInstance();
        this.startTransactionFragment(this.mTopStoriesFragment);
    }

    private void showMostPopularFragment(){
        if(this.mMostPopularFragment == null)
            this.mMostPopularFragment = MostPopularFragment.newInstance();
        this.startTransactionFragment(this.mMostPopularFragment);
    }

    private void showSportsFragment(){
        if(this.mSportsFragment == null)
            this.mSportsFragment = TopicFragment.newInstance("Sports");
        this.startTransactionFragment(this.mSportsFragment);
    }

    private void showBusinessFragment(){
        if(this.mBusinessFragment == null)
            this.mBusinessFragment = TopicFragment.newInstance("Business");
        this.startTransactionFragment(this.mBusinessFragment);
    }

    private void showArtsFragment(){
        if(this.mArtsFragment == null)
            this.mArtsFragment = TopicFragment.newInstance("Arts");
        this.startTransactionFragment(this.mArtsFragment);
    }

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_drawer_layout, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    //----------------------------
    // CONFIGURATION OF VIEWS
    //----------------------------
    private void configureToolbar(){
        this.mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void configureViewPagerAndTabs(){
        //ViewPager
        ViewPager pager = findViewById(R.id.activity_main_viewPager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()){});

        //Tabs
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    private void configureDrawerLayout(){
        this.mDrawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        this.mNavigationView = (NavigationView)findViewById(R.id.activity_main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

   }
