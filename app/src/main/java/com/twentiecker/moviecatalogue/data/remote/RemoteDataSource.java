package com.twentiecker.moviecatalogue.data.remote;

import com.twentiecker.moviecatalogue.BuildConfig;
import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResponse;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResponse;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;
import com.twentiecker.moviecatalogue.utils.EspressoIdlingResource;
import com.twentiecker.moviecatalogue.utils.JsonHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource {
    private static RemoteDataSource INSTANCE;
    private final JsonHelper jsonHelper;

    public RemoteDataSource(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public static RemoteDataSource getInstance(JsonHelper jsonHelper) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(jsonHelper);
        }
        return INSTANCE;
    }

    public void getMovies(MovieCallback callback) {
        EspressoIdlingResource.increment();

        Call<MovieResponse> movieResponseCall = ApiConfig.getApiService().getMovies(BuildConfig.API_KEY,
                "en-US",
                1);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<MovieResults> movieResultsList = response.body().getResults();
                    callback.onAllMovieReceived(movieResultsList);

                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public void getTvs(TvCallback callback) {
        EspressoIdlingResource.increment();

        Call<TvResponse> tvResponseCall = ApiConfig.getApiService().getTvs(BuildConfig.API_KEY,
                "en-US",
                1);
        tvResponseCall.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    List<TvResults> tvResultsList = response.body().getResults();
                    callback.onAllTvReceived(tvResultsList);

                    EspressoIdlingResource.decrement();
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {

            }
        });
    }
}
