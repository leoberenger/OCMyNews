package com.openclassrooms.mynews;

import com.openclassrooms.mynews.utils.SearchMgr;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchMgrUnitTest {

    boolean [] newsDesksArray = {false, true, true, false, false, true};
    boolean [] emptyNewsDesksArray = {false,false,false,false,false,false};
    SearchMgr searchMgr = SearchMgr.getInstance();

    @Test
    public void getNewsDesksString() {
        assertEquals("news_desk:( %22Business%22 %22Entrepreneur%22 %22Travel%22)", searchMgr.newsDesks(newsDesksArray));
    }

    @Test
    public void noDeskSelected(){
        assertEquals(true, searchMgr.noDeskSelected(emptyNewsDesksArray));
    }

    @Test
    public void min1DeskSelected(){
        assertEquals(false, searchMgr.noDeskSelected(newsDesksArray));
    }
}