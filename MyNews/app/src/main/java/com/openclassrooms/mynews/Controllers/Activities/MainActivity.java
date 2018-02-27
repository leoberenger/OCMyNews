package com.openclassrooms.mynews.Controllers.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Views.PagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolbar();
        this.configureViewPagerAndTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_navMenu:
                Toast.makeText(this, "Menu de Navigation indisponible", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_search:
                Toast.makeText(this, "Recherche indisponible", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_more:
                Toast.makeText(this, "En savoir plus indisponible", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureToolbar(){
        android.support.v7.widget.Toolbar toolbar
                = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureViewPagerAndTabs(){
        //ViewPager
        ViewPager pager = (ViewPager)findViewById(R.id.activity_main_viewPager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()){});

        //Tabs
        TabLayout tabs = (TabLayout)findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
