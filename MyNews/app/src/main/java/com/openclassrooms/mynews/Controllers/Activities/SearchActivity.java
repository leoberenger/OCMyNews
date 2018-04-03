package com.openclassrooms.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.activity_search_query_input)
    EditText queryInput;
    @BindView(R.id.search_begin_date)
    EditText beginDatePicker;
    @BindView(R.id.search_end_date)
    EditText endDatePicker;
    @BindView(R.id.activity_search_button)
    Button searchBtn;

    private String mQuery = "";         //"france";
    private int mBeginDate = 0;         //20170910
    private int mEndDate = 0;           //20171001
    private String [] newsDesks = {"","","","","","",};
    private String mNewsDesk;           //"news_desk:(%22Travel%22)";

    private boolean min1DeskIsSelected = false;

    private String EXTRA_QUERY = "EXTRA_QUERY";
    private String EXTRA_NEWS_DESKS = "EXTRA_NEWS_DESKS";
    private String EXTRA_BEGIN_DATE = "EXTRA_BEGIN_DATE";
    private String EXTRA_END_DATE = "EXTRA_END_DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Log.e("onCreate()", beginDatePicker.getText().toString());

        configureDatePicker(beginDatePicker);
        configureDatePicker(endDatePicker);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mQuery = queryInput.getText().toString();
                String begDateInput = beginDatePicker.getText().toString();
                String endDateInput = endDatePicker.getText().toString();

                if(mQuery.equals("")) {
                    Toast.makeText(getApplicationContext(), "Query required", Toast.LENGTH_LONG).show();
                }else if(!min1DeskIsSelected) {
                    Toast.makeText(getApplicationContext(), "Pick at least one topic", Toast.LENGTH_LONG).show();
                }else if( !begDateInput.equals("") && !validateDateFormat(beginDatePicker) ){
                    Toast.makeText(getApplicationContext(), "Invalid Begin Date format", Toast.LENGTH_LONG).show();
                }else if( !endDateInput.equals("") && !validateDateFormat(endDatePicker) ){
                    Toast.makeText(getApplicationContext(), "Invalid End Date format", Toast.LENGTH_LONG).show();
                }else{
                    mNewsDesk = "news_desk:(" + newsDesks[0] + newsDesks[1] + newsDesks[2] + newsDesks[3] + newsDesks[4] + newsDesks[5] + ")";
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

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        min1DeskIsSelected = false;

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_arts:
                if (checked) {
                    newsDesks[0] = "%22Arts%22";
                    min1DeskIsSelected = true;
                }else{
                    newsDesks[0] = "";
                }
                break;
            case R.id.checkbox_business:
                if (checked) {
                    newsDesks[1] = "%22Business%22";
                    min1DeskIsSelected = true;
                }else{
                    newsDesks[1] = "";
                }
                break;
            case R.id.checkbox_entrepreneur:
                if (checked){
                    newsDesks[2] = "%22Entrepreneur%22";
                    min1DeskIsSelected = true;
                }else{
                    newsDesks[2] = "";
                }
                break;
            case R.id.checkbox_politics:
                if (checked){
                    newsDesks[3] = "%22Politics%22";
                    min1DeskIsSelected = true;
                }else{
                    newsDesks[3] = "";
                }
                break;
            case R.id.checkbox_sports:
                if (checked){
                    newsDesks[4] = "%22Sports%22";
                    min1DeskIsSelected = true;
                }else{
                    newsDesks[4] = "";
                }
                break;
            case R.id.checkbox_travel:
                if (checked){
                    newsDesks[5] = "%22Travel%22";
                    min1DeskIsSelected = true;
                }else{
                    newsDesks[5] = "";
                }
                break;
        }
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
