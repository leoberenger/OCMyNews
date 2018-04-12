package com.openclassrooms.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.mynews.Models.API.NYTimesAPI;
import com.openclassrooms.mynews.Models.API.Response;
import com.openclassrooms.mynews.Models.API.Result;
import com.openclassrooms.mynews.Utils.APIRequests.NYTStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DisplayFragmentInstrumentedTest {
    @Test
    public void topStoriesReturnResultsTest() throws Exception {

        //1 - Get the stream
        Observable<NYTimesAPI> observableTopStories = NYTStreams.streamFetchTopStories();

        //2 - Create a new TestObserver
        TestObserver<NYTimesAPI> testObserver = new TestObserver<>();

        //3 - Launch observable
        observableTopStories.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get list of articles
        List<Result> articles = testObserver.values().get(0).getResults();

        // 5 - Verify if TopStories n°0 has a title
        assertThat("Articles are returned", !articles.get(0).getTitle().equals(""));
        }

    @Test
    public void mostPopularReturnResultsTest() throws Exception {

        //1 - Get the stream
        Observable<NYTimesAPI> observableMostPopular = NYTStreams.streamFetchMostPopular();

        //2 - Create a new TestObserver
        TestObserver<NYTimesAPI> testObserver = new TestObserver<>();

        //3 - Launch observable
        observableMostPopular.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get list of articles
        List<Result> articles = testObserver.values().get(0).getResults();

        // 5 - Verify if TopStories n°0 has a title
        assertThat("Articles are returned", !articles.get(0).getTitle().equals(""));
    }
/*
    @Test
    public void searchReturnsResponsesTest() throws Exception {

        //1 - Get the stream
        Observable<NYTimesAPI> observableSearch = NYTStreams.streamFetchSearchArticles("trump", "Business");

        //2 - Create a new TestObserver
        TestObserver<NYTimesAPI> testObserver = new TestObserver<>();

        //3 - Launch observable
        observableSearch.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get list of articles
        List<Response.Doc> articles = testObserver.values().get(0).getResponse().getDocs();

        assertThat("Articles are returned", !articles.get(0).getHeadline().getMain().equals(""));
    }
*/
    @Test
    public void searchReturns10ArticlesTest() throws Exception {

        //1 - Get the stream
        Observable<NYTimesAPI> observableSearch = NYTStreams.streamFetchSearchArticles("trump", "Business");

        //2 - Create a new TestObserver
        TestObserver<NYTimesAPI> testObserver = new TestObserver<>();

        //3 - Launch observable
        observableSearch.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get list of articles
        List<Response.Doc> articles = testObserver.values().get(0).getResponse().getDocs();

        assertThat("10 Articles are returned", articles.size() == 10);
    }
}
