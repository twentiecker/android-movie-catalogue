package com.twentiecker.moviecatalogue.data.remote;

import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;

import java.util.List;

public interface MovieCallback {
    void onAllMovieReceived(List<MovieResults> movieResultsList);
}
