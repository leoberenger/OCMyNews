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

    public int transformDateFormat(EditText datePicker){ //transforms 10/01/2018 to 20180110
        String date = datePicker.getText().toString();
        String orderedDate = date.substring(6,10) + date.substring(3,5) + date.substring(0,2);

        return Integer.valueOf(orderedDate);
    }

    public int transformPublishedDate(String pubDate){
        //(Ex: 2018-03-08T05:44:00-05:00 to 2018080305)
        String date =
                pubDate.substring(0,4)      //YYYY
                + pubDate.substring(8,10)   //MM
                + pubDate.substring(5,7)    //DD
                + pubDate.substring(11,13); //HH

        return Integer.valueOf(date);

    }

    public int getDate(int nbDayBefore){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);

        c.add(Calendar.DATE, -(nbDayBefore));

        return Integer.valueOf(sdf.format(c.getTime()));
    }
}
