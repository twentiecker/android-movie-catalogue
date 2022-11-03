package com.twentiecker.moviecatalogue.data;

import androidx.lifecycle.LiveData;

import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;

import java.util.List;

public interface FilmDataSource {

    LiveData<List<MovieResults>> getMovies();

    LiveData<List<TvResults>> getTvs();

    LiveData<MovieResults> getMovieId(String movieId);

    LiveData<TvResults> getTvId(String tvId);

}
