package com.twentiecker.moviecatalogue.data.api;

import com.twentiecker.moviecatalogue.data.entity.movie.MovieResponse;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<MovieResponse> getMovies(
            @Query("api_key") String api_key,
            @Query("language") String langauge,
            @Query("page") int page
    );

    @GET("tv/popular")
    Call<TvResponse> getTvs(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );
}
