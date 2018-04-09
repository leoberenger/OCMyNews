package com.openclassrooms.mynews.Utils;

import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateMgr {
    private static final DateMgr ourInstance = new DateMgr();

    public static DateMgr getInstance() {
        return ourInstance;
    }

    private DateMgr() {
    }

    public boolean invalidDateFormat(EditText datePicker){
        //Regular Expression Testing dd-MM-YYYY
        String regexp = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((18|19|20|21)\\d\\d)";
        String date = datePicker.getText().toString();

        return (!date.matches(regexp));
    }

    public int transformDateFormat(EditText datePicker){ //transforms 10/01/2018 to 20180110
        String date = datePicker.getText().toString();
        String orderedDate = date.substring(6,10) + date.substring(3,5) + date.substring(0,2);

        return Integer.valueOf(orderedDate);
    }

    public int getDate(int nbDayBefore){
        int date = 0;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);

        if(nbDayBefore == 0 ) {
            date = Integer.valueOf(sdf.format(c.getTime()));
        }else if(nbDayBefore == 1){
            c.add(Calendar.DATE, -1);
            date = Integer.valueOf(sdf.format(c.getTime()));
        }

        return date;
    }
}
