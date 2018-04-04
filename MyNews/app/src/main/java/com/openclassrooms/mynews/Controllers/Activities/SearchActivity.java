package com.openclassrooms.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseSearchActivity {

    @BindView(R.id.search_begin_date) EditText beginDatePicker;
    @BindView(R.id.search_end_date) EditText endDatePicker;
    @BindView(R.id.activity_search_button) Button searchBtn;
    @BindView(R.id.activity_search_query_input)EditText queryInput;

    private int mBeginDate = 0;
    private int mEndDate = 0;
    private String mNewsDesk;
    private String [] newsDesk = {"%22Arts%22", "%22Business%22", "%22Entrepreneur%22", "%22Politics%22", "%22Sports%22", "%22Travel%22"};

    private String EXTRA_QUERY = "EXTRA_QUERY";
    private String EXTRA_NEWS_DESKS = "EXTRA_NEWS_DESKS";
    private String EXTRA_BEGIN_DATE = "EXTRA_BEGIN_DATE";
    private String EXTRA_END_DATE = "EXTRA_END_DATE";

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

                mQuery = queryInput.getText().toString();
                String begDateInput = beginDatePicker.getText().toString();
                String endDateInput = endDatePicker.getText().toString();
                boolean min1DeskIsSelected = false;

                for(int i = 0; i<newsDesksLength; i++){
                    if(deskIsSet[i])
                        min1DeskIsSelected = true;
                }

                if(mQuery.equals("")) {
                    Toast.makeText(getApplicationContext(), "Query required", Toast.LENGTH_LONG).show();
                }else if(!min1DeskIsSelected) {
                    Toast.makeText(getApplicationContext(), "Pick at least one topic", Toast.LENGTH_LONG).show();
                }else if( !begDateInput.equals("") && !validateDateFormat(beginDatePicker) ){
                    Toast.makeText(getApplicationContext(), "Invalid Begin Date format", Toast.LENGTH_LONG).show();
                }else if( !endDateInput.equals("") && !validateDateFormat(endDatePicker) ){
                    Toast.makeText(getApplicationContext(), "Invalid End Date format", Toast.LENGTH_LONG).show();
                }else {

                    StringBuilder str = new StringBuilder("news_desk:(");
                    for (int i = 0; i < newsDesksLength; i++) {
                        if (deskIsSet[i])
                            str.append(newsDesk[i]);
                    }
                    str.append(")");
                    mNewsDesk = str.toString();

                    mBeginDate = (!begDateInput.equals("")) ? transformDateFormat(beginDatePicker) : 0 ;
                    mEndDate = (!endDateInput.equals("")) ? transformDateFormat(endDatePicker) : 0;

                    Log.e("Search Activity", "mNewsDesk=" + mNewsDesk + " mQuery= " + mQuery + " begin date ="+mBeginDate + " end date =" + mEndDate);

                    Intent intent = new Intent(SearchActivity.this, DisplaySearchActivity.class);
                    intent.putExtra(EXTRA_QUERY, mQuery);
                    intent.putExtra(EXTRA_NEWS_DESKS, mNewsDesk);
                    intent.putExtra(EXTRA_BEGIN_DATE, mBeginDate);
                    intent.putExtra(EXTRA_END_DATE, mEndDate);
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

    public int transformDateFormat(EditText datePicker){ //transforms 10/01/2018 to 20180110
        String date = datePicker.getText().toString();
        String orderedDate = date.substring(6,10) + date.substring(3,5) + date.substring(0,2);
        int intDate = Integer.valueOf(orderedDate);

        return intDate;
    }

    public boolean validateDateFormat(EditText datePicker){
        //Regular Expression Testing dd-MM-YYYY
        String regexp = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((18|19|20|21)\\d\\d)";
        String date = datePicker.getText().toString();

        return (date.matches(regexp));
    }
}
