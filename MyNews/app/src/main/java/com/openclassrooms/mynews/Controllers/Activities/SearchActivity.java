package com.openclassrooms.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Controllers.base.BaseSearchActivity;
import com.openclassrooms.mynews.Utils.SearchMgr;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseSearchActivity {

    @BindView(R.id.search_begin_date) EditText beginDatePicker;
    @BindView(R.id.search_end_date) EditText endDatePicker;
    @BindView(R.id.activity_search_button) Button searchBtn;
    @BindView(R.id.activity_search_query_input)EditText queryInput;
    private Search mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        configureDatePicker(beginDatePicker);
        configureDatePicker(endDatePicker);
        this.configureSearch();
    }

    private void configureSearch() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SearchMgr searchMgr = SearchMgr.getInstance();

                mQuery = queryInput.getText().toString();
                String begDateInput = beginDatePicker.getText().toString();
                String endDateInput = endDatePicker.getText().toString();
                boolean oneTopicSelected = searchMgr.checkMin1DeskSelected(desksAreChecked);
                mNewsDesk = searchMgr.getNewsDeskName(desksAreChecked, newsDesk);

                Search search = new Search("query", mQuery, mNewsDesk, mBeginDate, mEndDate);

                if(mQuery.equals("")) {
                    showToast("SearchManager required");
                }else if( !oneTopicSelected) {
                    showToast("Pick at least one topic");
                }else if( !begDateInput.equals("") && !validateDateFormat(beginDatePicker) ){
                    showToast("Invalid Begin Date format");
                }else if( !endDateInput.equals("") && !validateDateFormat(endDatePicker) ){
                    showToast("Invalid End Date format");
                }else {

                    mBeginDate = (!begDateInput.equals("")) ? transformDateFormat(beginDatePicker) : 0 ;
                    mEndDate = (!endDateInput.equals("")) ? transformDateFormat(endDatePicker) : 0;

                    Log.e("SearchManager Activity", "mNewsDesk=" + mNewsDesk + " mQuery= " + mQuery + " begin date ="+mBeginDate + " end date =" + mEndDate);

                    Intent intent = new Intent(SearchActivity.this, DisplaySearchActivity.class);
                    searchMgr.setSearchToIntent(intent, search);
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

    private int transformDateFormat(EditText datePicker){ //transforms 10/01/2018 to 20180110
        String date = datePicker.getText().toString();
        String orderedDate = date.substring(6,10) + date.substring(3,5) + date.substring(0,2);
        int intDate = Integer.valueOf(orderedDate);

        return intDate;
    }

    private boolean validateDateFormat(EditText datePicker){
        //Regular Expression Testing dd-MM-YYYY
        String regexp = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((18|19|20|21)\\d\\d)";
        String date = datePicker.getText().toString();

        return (date.matches(regexp));
    }

    private void showToast(String toastTxt){
        Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_LONG).show();
    }
}
