package com.openclassrooms.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        configureDatePicker(beginDatePicker);
        configureDatePicker(endDatePicker);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mQuery = queryInput.getText().toString();

                if (!beginDatePicker.getText().toString().equals(""))
                    mBeginDate = transformDateFormat(beginDatePicker);


                if (!endDatePicker.getText().toString().equals(""))
                    mEndDate = transformDateFormat(endDatePicker);

                if(mQuery.equals(""))
                    Toast.makeText(getApplicationContext(), "Query required", Toast.LENGTH_LONG).show();
                else if ((newsDesks[0].equals(""))&&(newsDesks[1].equals(""))&&(newsDesks[2].equals(""))
                            &&(newsDesks[3].equals(""))&&(newsDesks[4].equals(""))&&(newsDesks[5].equals(""))) {
                    Toast.makeText(getApplicationContext(), "Pick at least one topic", Toast.LENGTH_LONG).show();
                }else{
                    mNewsDesk = "news_desk:(" + newsDesks[0] + newsDesks[1] + newsDesks[2] + newsDesks[3] + newsDesks[4] + newsDesks[5] + ")";
                    Log.e("Search Activity", "mNewsDesk=" + mNewsDesk + " mQuery= " + mQuery + " begin date ="+mBeginDate + " end date =" + mEndDate);
                }
            }
        });
    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_arts:
                if (checked) newsDesks[0] = "%22Arts%22";
                else newsDesks[0] = "";
                break;
            case R.id.checkbox_business:
                if (checked) newsDesks[1] = "%22Business%22";
                else newsDesks[1] = "";
                break;
            case R.id.checkbox_entrepreneur:
                if (checked)newsDesks[2] = "%22Entrepreneur%22";
                else newsDesks[2] = "";
                break;
            case R.id.checkbox_politics:
                if (checked)newsDesks[3] = "%22Politics%22";
                else newsDesks[3] = "";
                break;
            case R.id.checkbox_sports:
                if (checked)newsDesks[4] = "%22Sports%22";
                else newsDesks[4] = "";
                break;
            case R.id.checkbox_travel:
                if (checked)newsDesks[5] = "%22Travel%22";
                else newsDesks[5] = "";
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
}
