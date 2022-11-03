package com.twentiecker.moviecatalogue.data.remote;

import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResponse;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResponse;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

public class RemoteDataSourceTest {

    private Response<MovieResponse> movieResponseResponse;
    private List<MovieResults> movieResultsList;
    private String movieId;
    private MovieResults movie;

    private Response<TvResponse> tvResponseResponse;
    private List<TvResults> tvResultsList;
    private String tvId;
    private TvResults tv;

    @Before
    public void setUp() throws IOException {
        Call<MovieResponse> movieResponseCall = ApiConfig.getApiService().getMovies("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        movieResponseResponse = movieResponseCall.execute();
        movieResultsList = movieResponseResponse.body().getResults();

        movieId = String.valueOf(movieResultsList.get(3).getId());
        for (MovieResults response : movieResultsList) {
            if (String.valueOf(response.getId()).equals(movieId)) {
                movie = new MovieResults(response.getOverview(),
                        response.getOriginalLanguage(),
                        response.getOriginalTitle(),
                        response.isVideo(),
                        response.getTitle(),
                        response.getGenreIds(),
                        response.getPosterPath(),
                        response.getBackdropPath(),
                        response.getReleaseDate(),
                        response.getPopularity(),
                        response.getVoteAverage(),
                        response.getId(),
                        response.isAdult(),
                        response.getVoteCount());
            }
        }

        Call<TvResponse> tvResponseCall = ApiConfig.getApiService().getTvs("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        tvResponseResponse = tvResponseCall.execute();
        tvResultsList = tvResponseResponse.body().getResults();

        tvId = String.valueOf(tvResultsList.get(11).getId());
        for (TvResults response : tvResultsList) {
            if (String.valueOf(response.getId()).equals(tvId)) {
                tv = new TvResults(response.getFirstAirDate(),
                        response.getOverview(),
                        response.getOriginalLanguage(),
                        response.getGenreIds(),
                        response.getPosterPath(),
                        response.getOriginCountry(),
                        response.getBackdropPath(),
                        response.getOriginalName(),
                        response.getPopularity(),
                        response.getVoteAverage(),
                        response.getName(),
                        response.getId(),
                        response.getVoteCount());
            }
        }

    }

    @Test
    public void getMovies() {
        assertEquals(movieResponseResponse.code(), 200);
        assertEquals(true, movieResponseResponse.isSuccessful());
        assertEquals(movieResponseResponse.body().getResults().size(), movieResultsList.size());

        assertEquals(movieResultsList.get(3).getOverview(), movie.getOverview());
        assertEquals(movieResultsList.get(3).getOriginalLanguage(), movie.getOriginalLanguage());
        assertEquals(movieResultsList.get(3).getOriginalTitle(), movie.getOriginalTitle());
        assertEquals(movieResultsList.get(3).isVideo(), movie.isVideo());
        assertEquals(movieResultsList.get(3).getTitle(), movie.getTitle());
        assertEquals(movieResultsList.get(3).getGenreIds(), movie.getGenreIds());
        assertEquals(movieResultsList.get(3).getPosterPath(), movie.getPosterPath());
        assertEquals(movieResultsList.get(3).getBackdropPath(), movie.getBackdropPath());
        assertEquals(movieResultsList.get(3).getReleaseDate(), movie.getReleaseDate());
        assertEquals(movieResultsList.get(3).getPopularity(), movie.getPopularity(), 0);
        assertEquals(movieResultsList.get(3).getVoteAverage(), movie.getVoteAverage(), 0);
        assertEquals(movieResultsList.get(3).getId(), movie.getId());
        assertEquals(movieResultsList.get(3).isAdult(), movie.isAdult());
        assertEquals(movieResultsList.get(3).getVoteCount(), movie.getVoteCount());
    }

    @Test
    public void getTvs() {
        assertEquals(tvResponseResponse.code(), 200);
        assertEquals(true, tvResponseResponse.isSuccessful());
        assertEquals(tvResponseResponse.body().getResults().size(), tvResultsList.size());

        assertEquals(tvResultsList.get(11).getFirstAirDate(), tv.getFirstAirDate());
        assertEquals(tvResultsList.get(11).getOverview(), tv.getOverview());
        assertEquals(tvResultsList.get(11).getOriginalLanguage(), tv.getOriginalLanguage());
        assertEquals(tvResultsList.get(11).getGenreIds(), tv.getGenreIds());
        assertEquals(tvResultsList.get(11).getPosterPath(), tv.getPosterPath());
        assertEquals(tvResultsList.get(11).getOriginCountry(), tv.getOriginCountry());
        assertEquals(tvResultsList.get(11).getBackdropPath(), tv.getBackdropPath());
        assertEquals(tvResultsList.get(11).getOriginalName(), tv.getOriginalName());
        assertEquals(tvResultsList.get(11).getPopularity(), tv.getPopularity(), 0);
        assertEquals(tvResultsList.get(11).getVoteAverage(), tv.getVoteAverage(), 0);
        assertEquals(tvResultsList.get(11).getName(), tv.getName());
        assertEquals(tvResultsList.get(11).getId(), tv.getId());
        assertEquals(tvResultsList.get(11).getVoteCount(), tv.getVoteCount());
    }
}