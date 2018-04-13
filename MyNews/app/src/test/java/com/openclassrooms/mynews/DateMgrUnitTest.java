package com.openclassrooms.mynews;

import com.openclassrooms.mynews.utils.DateMgr;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateMgrUnitTest {

    private DateMgr dateMgr = DateMgr.getInstance();
    private String pubDate = "2018-03-08T05:44:00-05:00";
    private int expectedDate = 2018080305;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);
    private int expectedYesterday = (Integer.valueOf(sdf.format(c.getTime()))) - 1;

    @Test
    public void validFormatRetrieved() {
        assertEquals(expectedDate, dateMgr.transformPublishedDate(pubDate));
    }

    @Test
    public void validDateRetrieved(){
        assertEquals(expectedYesterday, dateMgr.getDate(1));
    }
}