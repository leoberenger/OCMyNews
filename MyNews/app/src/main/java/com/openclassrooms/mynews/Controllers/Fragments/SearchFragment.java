package com.openclassrooms.mynews.Controllers.Fragments;


import android.app.DatePickerDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.openclassrooms.mynews.Controllers.Fragments.ArticleViews.DisplaySearchFragment;
import com.openclassrooms.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment{

    private DisplaySearchFragment mDisplaySearchFragment;
    private DatePickerDialog beginDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;

    @BindView(R.id.activity_search_query_input)
    EditText queryInput;
    @BindView(R.id.search_begin_date)
    EditText beginDatePicker;
    @BindView(R.id.search_end_date)
    EditText endDatePicker;
    @BindView(R.id.activity_search_button)
    Button searchBtn;

    private String mQuery;      //"france";
    private String mNewsDesk = "news_desk:(%22Travel%22)";
    private int mBeginDate = 20170910;
    private int mEndDate = 20171001;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        configureDatePicker(beginDatePicker);
        configureDatePicker(endDatePicker);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mQuery = queryInput.getText().toString();
                mBeginDate = transformDate(beginDatePicker);
                mEndDate = transformDate(endDatePicker);
                Log.e("SearchFragment", "query = "+mQuery);
                Log.e("SearchFragment", "begin date = "+mBeginDate);
                Log.e("SearchFragment", "end date = "+mEndDate);
                //deskCheckboxes(view);
                //configureAndShowDisplaySearchFragment();
            }
        });

        return view;
    }

    private void configureAndShowDisplaySearchFragment() {

        if (mDisplaySearchFragment == null) {
            mDisplaySearchFragment = new DisplaySearchFragment();

            Bundle args = new Bundle();
            args.putString("query", mQuery);
            args.putString("newsDesk", mNewsDesk);
            args.putInt("beginDate", mBeginDate);
            args.putInt("endDate", mEndDate);
            mDisplaySearchFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .remove(this)
                    .add(R.id.activity_search_frame_layout, mDisplaySearchFragment)
                    .addToBackStack(null)
                    .commit();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,
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

    public void deskCheckboxes(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        mNewsDesk = "news_desk:(";

        boolean min1checked = false;

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_arts:
                if (checked) {
                    mNewsDesk += "%22Arts%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_business:
                if (checked){
                    mNewsDesk += "%22Business%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_entrepreneur:
                if (checked){
                    mNewsDesk += "%22Entrepreneurs%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_politics:
                if (checked){
                    mNewsDesk += "%22Politics%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_sports:
                if (checked){
                    mNewsDesk += "%22Sports%22 ";
                    min1checked = true;
                }
                break;
            case R.id.checkbox_travel:
                if (checked){
                    mNewsDesk += "%22Travel%22 ";
                    min1checked = true;
                }
                break;
        }
        mNewsDesk += ")";

        if(!min1checked){
            //SHOW MESSAGE TO SELECT AT LEAST 1
        }
    }

    public int transformDate(EditText datePicker){ //transforms 10/01/2018 to 20180110
        String date = datePicker.getText().toString();
        String orderedDate = date.substring(6,10) + date.substring(3,5) + date.substring(0,2);
        int intDate = Integer.valueOf(orderedDate);

        return intDate;
        }
}
