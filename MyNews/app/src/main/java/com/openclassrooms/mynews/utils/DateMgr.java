package com.openclassrooms.mynews.utils;

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

    //transforms (string) 10/01/2018 to (int) 20180110
    public int transformDateFormat(EditText datePicker){
        String date = datePicker.getText().toString();
        String orderedDate = date.substring(6,10) + date.substring(3,5) + date.substring(0,2);

        return Integer.valueOf(orderedDate);
    }

    //transforms (string) "2018-03-08T05:44:00-05:00" to (int) 2018080305
    public int transformPublishedDate(String pubDate){
        String date =
                pubDate.substring(0,4)      //YYYY
                + pubDate.substring(8,10)   //MM
                + pubDate.substring(5,7)    //DD
                + pubDate.substring(11,13); //HH

        return Integer.valueOf(date);

    }

    //get (int) date of previous days
    public int getDate(int nbDayBefore){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);

        c.add(Calendar.DATE, -(nbDayBefore));

        return Integer.valueOf(sdf.format(c.getTime()));
    }
}
