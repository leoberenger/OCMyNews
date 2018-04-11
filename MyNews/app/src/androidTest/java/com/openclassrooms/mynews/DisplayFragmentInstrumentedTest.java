package com.openclassrooms.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.mynews.Models.API.NYTimesAPI;
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
    public void fetchTopStoriesNumberTest() throws Exception {

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

        // 5 - Verify if TopStories return 44 articles
        assertThat("44 articles received",articles.size() == 44);

    }

    @Test
    public void fetchArticleInfosTest() throws Exception {

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

        assertThat("First Article Title is Paul Ryan, the House Spe…Re-election in November",articles.get(0).getTitle().equals("Paul Ryan, the House Speaker, Will Not Seek Re-election in November"));
    }
}
