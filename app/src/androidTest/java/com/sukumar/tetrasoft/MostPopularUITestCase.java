package com.sukumar.tetrasoft;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import com.sukumar.tetrasoft.module.mostPopular.ui.MostPopularActivity;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

//@RunWith(AndroidJUnit4.class)
public class MostPopularUITestCase {

    @Rule
    public ActivityTestRule<MostPopularActivity> rule = new ActivityTestRule<MostPopularActivity>(MostPopularActivity.class);

    @Test
    public void MOST_POPULAR_ACTIVITY_LAUNCH_SUCCESSFULLY(){
        ActivityScenario.launch(MostPopularActivity.class);
    }
    @Test
    public void MOST_POPULAR_ACTIVITY_PARENT_VIEW_IS_SHOWING(){
        Espresso.onView(withId(R.id.mParentLayout)).check(matches(isDisplayed()));
    }
    @Test
    public void MOST_POPULAR_ACTIVITY_LOADER_IS_SHOWING(){
        Espresso.onView(withId(R.id.mProgressBar)).check(matches(isDisplayed()));
    }


}
