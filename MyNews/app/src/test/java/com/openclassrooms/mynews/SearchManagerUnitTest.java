package com.openclassrooms.mynews;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchManagerUnitTest {

    @Test
    public void query_isNotEmpty() throws Exception {
        String query= "";
        assertEquals("", query);
    }
}