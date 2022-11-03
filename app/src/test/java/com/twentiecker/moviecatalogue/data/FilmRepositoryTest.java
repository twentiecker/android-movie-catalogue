package com.twentiecker.moviecatalogue.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResponse;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResponse;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;
import com.twentiecker.moviecatalogue.data.remote.MovieCallback;
import com.twentiecker.moviecatalogue.data.remote.RemoteDataSource;
import com.twentiecker.moviecatalogue.data.remote.TvCallback;
import com.twentiecker.moviecatalogue.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FilmRepositoryTest {
    private List<MovieResults> movieResponse;
    private String movieId;
    private List<TvResults> tvResponse;
    private String tvId;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final RemoteDataSource remote = mock(RemoteDataSource.class);
    private final FakeFilmRepository fakeFilmRepository = new FakeFilmRepository(remote);

    @Before
    public void setUp() throws IOException {
        Call<MovieResponse> movieResponseCall = ApiConfig.getApiService().getMovies("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        Response<MovieResponse> movieResponseResponse = movieResponseCall.execute();
        movieResponse = movieResponseResponse.body().getResults();
        movieId = String.valueOf(movieResponse.get(0).getId());

        Call<TvResponse> tvResponseCall = ApiConfig.getApiService().getTvs("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        Response<TvResponse> tvResponseResponse = tvResponseCall.execute();
        tvResponse = tvResponseResponse.body().getResults();
        tvId = String.valueOf(tvResponse.get(0).getId());
    }

    @Test
    public void getMovies() {
        doAnswer(invocation -> {
            ((MovieCallback) invocation.getArguments()[0]).onAllMovieReceived(movieResponse);
            return null;
        }).when(remote).getMovies(any(MovieCallback.class));
        List<MovieResults> movieEntities = LiveDataTestUtil.getValue(fakeFilmRepository.getMovies());
        verify(remote).getMovies(any(MovieCallback.class));

        assertNotNull(movieEntities);
        assertEquals(movieResponse.size(), movieEntities.size());
    }

    @Test
    public void getTvs() {
        doAnswer(invocation -> {
            ((TvCallback) invocation.getArguments()[0]).onAllTvReceived(tvResponse);
            return null;
        }).when(remote).getTvs(any(TvCallback.class));
        List<TvResults> tvEntities = LiveDataTestUtil.getValue(fakeFilmRepository.getTvs());
        verify(remote).getTvs(any(TvCallback.class));

        assertNotNull(tvEntities);
        assertEquals(tvResponse.size(), tvEntities.size());
    }

    @Test
    public void getMovieId() {
        doAnswer(invocation -> {
            ((MovieCallback) invocation.getArguments()[0])
                    .onAllMovieReceived(movieResponse);
            return null;
        }).when(remote).getMovies(any(MovieCallback.class));

        MovieResults movieEntities = LiveDataTestUtil.getValue(fakeFilmRepository.getMovieId(movieId));

        verify(remote).getMovies(any(MovieCallback.class));

        assertNotNull(movieEntities);
        assertEquals(movieResponse.get(0).getOverview(), movieEntities.getOverview());
        assertEquals(movieResponse.get(0).getOriginalLanguage(), movieEntities.getOriginalLanguage());
        assertEquals(movieResponse.get(0).getOriginalTitle(), movieEntities.getOriginalTitle());
        assertEquals(movieResponse.get(0).isVideo(), movieEntities.isVideo());
        assertEquals(movieResponse.get(0).getTitle(), movieEntities.getTitle());
        assertEquals(movieResponse.get(0).getGenreIds(), movieEntities.getGenreIds());
        assertEquals(movieResponse.get(0).getPosterPath(), movieEntities.getPosterPath());
        assertEquals(movieResponse.get(0).getBackdropPath(), movieEntities.getBackdropPath());
        assertEquals(movieResponse.get(0).getReleaseDate(), movieEntities.getReleaseDate());
        assertEquals(movieResponse.get(0).getPopularity(), movieEntities.getPopularity(), 0);
        assertEquals(movieResponse.get(0).getVoteAverage(), movieEntities.getVoteAverage(), 0);
        assertEquals(movieResponse.get(0).getId(), movieEntities.getId());
        assertEquals(movieResponse.get(0).isAdult(), movieEntities.isAdult());
        assertEquals(movieResponse.get(0).getVoteCount(), movieEntities.getVoteCount());
    }

    @Test
    public void getTvId() {
        doAnswer(invocation -> {
            ((TvCallback) invocation.getArguments()[0])
                    .onAllTvReceived(tvResponse);
            return null;
        }).when(remote).getTvs(any(TvCallback.class));

        TvResults tvEntities = LiveDataTestUtil.getValue(fakeFilmRepository.getTvId(tvId));

        verify(remote).getTvs(any(TvCallback.class));

        assertNotNull(tvEntities);
        assertEquals(tvResponse.get(0).getFirstAirDate(), tvEntities.getFirstAirDate());
        assertEquals(tvResponse.get(0).getOverview(), tvEntities.getOverview());
        assertEquals(tvResponse.get(0).getOriginalLanguage(), tvEntities.getOriginalLanguage());
        assertEquals(tvResponse.get(0).getGenreIds(), tvEntities.getGenreIds());
        assertEquals(tvResponse.get(0).getPosterPath(), tvEntities.getPosterPath());
        assertEquals(tvResponse.get(0).getOriginCountry(), tvEntities.getOriginCountry());
        assertEquals(tvResponse.get(0).getBackdropPath(), tvEntities.getBackdropPath());
        assertEquals(tvResponse.get(0).getOriginalName(), tvEntities.getOriginalName());
        assertEquals(tvResponse.get(0).getPopularity(), tvEntities.getPopularity(), 0);
        assertEquals(tvResponse.get(0).getVoteAverage(), tvEntities.getVoteAverage(), 0);
        assertEquals(tvResponse.get(0).getName(), tvEntities.getName());
        assertEquals(tvResponse.get(0).getId(), tvEntities.getId());
        assertEquals(tvResponse.get(0).getVoteCount(), tvEntities.getVoteCount());
    }
}