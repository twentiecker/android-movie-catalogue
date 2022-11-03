package com.twentiecker.moviecatalogue.ui.home;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.twentiecker.moviecatalogue.R;
import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResponse;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResponse;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;
import com.twentiecker.moviecatalogue.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class HomeActivityTest {
    private List<MovieResults> dummyMovies;
    private List<TvResults> dummyTvs;

    @Before
    public void setUp() throws IOException {
        ActivityScenario.launch(HomeActivity.class);
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
        dummyMovies = getMovieResponse();
        dummyTvs = getTvResponse();
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    public List<TvResults> getTvResponse() throws IOException {
        Call<TvResponse> tvResponseCall = ApiConfig.getApiService().getTvs("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        Response<TvResponse> tvResponseResponse = tvResponseCall.execute();
        List<TvResults> tvResultsList = tvResponseResponse.body().getResults();
        return tvResultsList;
    }

    public List<MovieResults> getMovieResponse() throws IOException {
        Call<MovieResponse> movieResponseCall = ApiConfig.getApiService().getMovies("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        Response<MovieResponse> movieResponseResponse = movieResponseCall.execute();
        List<MovieResults> movieResultsList = movieResponseResponse.body().getResults();
        return movieResultsList;
    }

    @Test
    public void loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition(dummyMovies.size()));
    }

    @Test
    public void loadDetailMovie() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_release_date)).check(matches(withText(dummyMovies.get(0).getReleaseDate())));
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_title)).check(matches(withText(dummyMovies.get(0).getTitle())));
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_overview)).check(matches(withText(dummyMovies.get(0).getOverview())));
        onView(withId(R.id.content)).perform(swipeUp());
        onView(withId(R.id.tv_popularity)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_popularity)).check(matches(withText(String.valueOf(dummyMovies.get(0).getPopularity()))));
        onView(withId(R.id.tv_user_score)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_user_score)).check(matches(withText(String.valueOf(dummyMovies.get(0).getVoteAverage()))));
        onView(withId(R.id.tv_original_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_original_title)).check(matches(withText(dummyMovies.get(0).getOriginalTitle())));
    }

    @Test
    public void loadTvs() {
        onView(withText("TvShow")).perform(click());
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.scrollToPosition(dummyTvs.size()));
    }

    @Test
    public void loadDetailTv() {
        onView(withText("TvShow")).perform(click());
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_release_date)).check(matches(withText(dummyTvs.get(9).getFirstAirDate())));
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_title)).check(matches(withText(dummyTvs.get(9).getName())));
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_overview)).check(matches(withText(dummyTvs.get(9).getOverview())));
        onView(withId(R.id.content)).perform(swipeUp());
        onView(withId(R.id.tv_popularity)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_popularity)).check(matches(withText(String.valueOf(dummyTvs.get(9).getPopularity()))));
        onView(withId(R.id.tv_user_score)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_user_score)).check(matches(withText(String.valueOf(dummyTvs.get(9).getVoteAverage()))));
        onView(withId(R.id.tv_original_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_original_title)).check(matches(withText(dummyTvs.get(9).getOriginalName())));
    }
}