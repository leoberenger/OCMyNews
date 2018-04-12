package com.openclassrooms.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.DateMgr;
import com.openclassrooms.mynews.Utils.SearchMgr;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseSearchActivity {

    @BindView(R.id.search_begin_date) EditText beginDatePicker;
    @BindView(R.id.search_end_date) EditText endDatePicker;
    @BindView(R.id.activity_search_button) Button searchBtn;
    @BindView(R.id.activity_search_query_input)EditText queryInput;
    @BindView(R.id.activity_search_toolbar)
    Toolbar mToolbar;

    private final String searchType = "query";
    private Search mSearch;
    private SearchMgr searchMgr  = SearchMgr.getInstance();
    private DateMgr dateMgr  = DateMgr.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        this.configureToolbar();
        configureDatePicker(beginDatePicker);
        configureDatePicker(endDatePicker);
        this.configureSearch();
    }

    private void configureSearch() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mQuery = queryInput.getText().toString();
                mNewsDesk = searchMgr.newsDesks(newsDesksSelected);

                boolean noDesksAreSelected = searchMgr.noDeskSelected(newsDesksSelected);
                boolean queryIsEmpty = searchMgr.emptyQuery(mQuery);
                boolean noBeginDate = searchMgr.emptyDateInput(beginDatePicker.getText().toString());
                boolean noEndDate = searchMgr.emptyDateInput(endDatePicker.getText().toString());
                boolean invalidBeginDateFormat = dateMgr.invalidDateFormat(beginDatePicker);
                boolean invalidEndDateFormat = dateMgr.invalidDateFormat(endDatePicker);

                if(queryIsEmpty) {
                    showToast("Query required");
                }else if(noDesksAreSelected) {
                    showToast("Pick at least one topic");
                }else if(!noBeginDate && invalidBeginDateFormat){
                    showToast("Invalid Begin Date format");
                }else if(!noEndDate && invalidEndDateFormat){
                    showToast("Invalid End Date format");
                }else {
                    //Transform Begin and End dates format to Search Date Format
                    mBeginDate = (!noBeginDate) ? dateMgr.transformDateFormat(beginDatePicker) : 0 ;
                    mEndDate = (!noEndDate) ? dateMgr.transformDateFormat(endDatePicker) : 0;

                    mSearch = new Search(searchType, mQuery, mNewsDesk, mBeginDate, mEndDate);

                    Intent intent = new Intent(SearchActivity.this, DisplaySearchActivity.class);
                    //Save search to Intent
                    searchMgr.setSearchToIntent(intent, mSearch);
                    startActivity(intent);
                }
            }
        });
    }

    private void configureDatePicker(final EditText datePicker){

        // perform click event on edit text
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String day, month;
                                if(dayOfMonth<10) day = "0"+dayOfMonth;
                                else day = ""+dayOfMonth;

                                if(monthOfYear<9) month = "0"+(monthOfYear+1);
                                else month = ""+(monthOfYear+1);

                                String dateString = day + "/" + month + "/" + year;
                                datePicker.setText(dateString);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    private void showToast(String toastTxt){
        Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_LONG).show();
    }

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
