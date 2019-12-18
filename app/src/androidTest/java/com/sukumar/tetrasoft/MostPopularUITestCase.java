package com.sukumar.tetrasoft;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.sukumar.tetrasoft.module.mostPopular.ui.MostPopularActivity;
import com.sukumar.tetrasoft.rules.OkHttpIdlingResourceRule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MostPopularUITestCase {

    @Rule
    public OkHttpIdlingResourceRule okHttpRule = new OkHttpIdlingResourceRule();

    @Rule
    public ActivityTestRule<MostPopularActivity> activityRule = new ActivityTestRule<>(MostPopularActivity.class);

    public final MockWebServer mockWebServer = new MockWebServer();
    public String BASE_URL = "https://api.nytimes.com/svc/";
    public String mockResponse="";

    @Before
    public void SETUP() throws IOException {
        mockWebServer.start(8080);
        BASE_URL = mockWebServer.url("/").toString();
        ActivityScenario.launch(MostPopularActivity.class);
    }

    @Test
    public void loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activityRule.getActivity().getAssets().open("mostpopular.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mockResponse=json;

    }

    @Test
    public void MOST_POPULAR_ACTIVITY_PARENT_VIEW_IS_SHOWING(){
        onView(withId(R.id.mParentLayout)).check(matches(isDisplayed()));
    }
    @Test
    public void MOST_POPULAR_ACTIVITY_FETCH_DATA(){
        MockResponse response = new MockResponse().setBody(mockResponse).setBodyDelay(5, TimeUnit.SECONDS);
        onView(withId(R.id.mProgressBar)).check(matches(isDisplayed()));
        mockWebServer.enqueue(response);
        /*onView(withId(R.id.mMostPopularRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));*/
    }


    @Test
    public void MOST_POPULAR_ACTIVITY_FETCH_DATA_FAILURE(){
        MockResponse response = new MockResponse().setBodyDelay(5, TimeUnit.SECONDS).setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
        mockWebServer.enqueue(response);
        onView(withId(R.id.errorTv)).check(matches(withText("Error loading")));
        onView(withId(R.id.errorTv)).check(matches(isDisplayed()));
    }

    @After
    public void  tearDown() throws IOException {
        mockWebServer.shutdown();
    }

}
