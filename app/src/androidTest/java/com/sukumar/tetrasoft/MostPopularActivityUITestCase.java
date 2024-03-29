package com.sukumar.tetrasoft;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.sukumar.tetrasoft.module.common.AppConstants;
import com.sukumar.tetrasoft.module.common.NetworkAvailability;
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
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static com.sukumar.tetrasoft.module.common.AppConstants.ERROR_LOADING;
import static com.sukumar.tetrasoft.module.common.AppConstants.INTENT_KEY;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class MostPopularActivityUITestCase {
    @Rule
    public OkHttpIdlingResourceRule okHttpRule = new OkHttpIdlingResourceRule();
    @Rule
    public ActivityTestRule<MostPopularActivity> activityRule = new ActivityTestRule<>(MostPopularActivity.class);
    private final MockWebServer mockWebServer = new MockWebServer();
    private String mockResponse="";
    @Before
    public void SETUP() throws IOException {
        mockWebServer.start(8080);
        ActivityScenario.launch(MostPopularActivity.class);
    }
    @Test
    public void loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activityRule.getActivity().getAssets().open("mostpopular.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);

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
    public void MOST_POPULAR_ACTIVITY_NETWORK_AVAILABILITY(){
        if(!NetworkAvailability.Companion.isNetworkAvailable(activityRule.getActivity())){
            onView(withId(R.id.errorTv)).check(matches(isDisplayed()));
            onView(withId(R.id.errorTv)).check(matches(withText(AppConstants.INTERNET_MSG)));
            onView(withId(R.id.mProgressBar)).check(matches(not(isDisplayed())));
        }
    }
    @Test
    public void MOST_POPULAR_ACTIVITY_FETCH_DATA(){
        MockResponse response = new MockResponse().setBody(mockResponse).setBodyDelay(5, TimeUnit.SECONDS);
        onView(withId(R.id.mProgressBar)).check(matches(isDisplayed()));
        onView(withId(R.id.errorTv)).check(matches(not(isDisplayed())));
        mockWebServer.enqueue(response);

    }
    /**/
    @Test
    public void MOST_POPULAR_ACTIVITY_FETCH_DATA_RECYCLER_VIEW_ONCLICK_ACTION() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.mMostPopularRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Intent intent = new Intent();
        intent.putExtra(INTENT_KEY, "UI Testing");
        activityRule.launchActivity(intent);
    }
    /*Api fails*/
    @Test
    public void MOST_POPULAR_ACTIVITY_FETCH_DATA_FAILURE() {
        MockResponse response = new MockResponse().setBodyDelay(5, TimeUnit.SECONDS).setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
        mockWebServer.enqueue(response);
        onView(withId(R.id.errorTv)).check(matches(isDisplayed()));
        onView(withId(R.id.errorTv)).check(matches(withText(ERROR_LOADING)));
        onView(withId(R.id.mProgressBar)).check(matches(not(isDisplayed())));
        System.out.println("api not success");
    }
    @After
    public void  tearDown() throws IOException {
        mockWebServer.shutdown();
    }


}
