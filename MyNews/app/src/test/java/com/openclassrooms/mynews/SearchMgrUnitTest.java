package com.openclassrooms.mynews;

import com.openclassrooms.mynews.Models.Search;
import com.openclassrooms.mynews.Utils.DateMgr;
import com.openclassrooms.mynews.Utils.SearchMgr;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchMgrUnitTest {

    Search mSearch = new Search("query", "", "Politics", 20180401, 20180411);
    SearchMgr mSearchMgr = SearchMgr.getInstance();
    @Test
    public void emptyQuery() {
        assertEquals("", mSearch.getQuery());
    }
}