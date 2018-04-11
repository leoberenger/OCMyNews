package com.openclassrooms.mynews.Controllers.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Views.PagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.activity_main_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_main_nav_view)
    NavigationView mNavigationView;

    private FragmentStatePagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureViewPagerAndTabs();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    //---------------------------------
    //CONFIGURATION OF NAVIGATION ITEMS
    //---------------------------------
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
        //Get to SearchManager or Notification Activity
        switch (item.getItemId()){
            case R.id.menu_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_more:
                View customView = findViewById(R.id.menu_more);
                showPopup(customView);
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
                mViewPager.setCurrentItem(0);
                break;
            case R.id.activity_main_drawer_mostpopular:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.activity_main_drawer_business:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.activity_main_drawer_sports:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.activity_main_drawer_arts:
                mViewPager.setCurrentItem(4);
                break;
            default: break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    //----------------------------
    // CONFIGURATION OF VIEWS
    //----------------------------

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
    }

    private void configureViewPagerAndTabs(){

        //ViewPager
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setAdapter(this.mAdapter);

        //Tabs
        tabs.setupWithViewPager(mViewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);

        popupMenu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener () {
            @Override
            public boolean onMenuItemClick (MenuItem item)
            {
                int id = item.getItemId();
                switch (id)
                {
                    case R.id.menu_more_notifications:
                        Intent intent2 = new Intent(getApplicationContext(), NotificationActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.menu_more_help:
                        Toast.makeText(getApplicationContext(), "no help to show", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_more_about:
                        Toast.makeText(getApplicationContext(), "no about to show", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate (R.menu.activity_main_menu_more);
        popupMenu.show();
        }
   }
