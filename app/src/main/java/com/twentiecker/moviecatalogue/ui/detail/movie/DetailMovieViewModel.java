package com.twentiecker.moviecatalogue.ui.detail.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.data.FilmRepository;

public class DetailMovieViewModel extends ViewModel {
    private String movieId;
    private final FilmRepository filmRepository;

    public DetailMovieViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public void setSelectedMovie(String movieId) {
        this.movieId = movieId;
    }

    public LiveData<MovieResults> getMovie() {
        return filmRepository.getMovieId(movieId);
    }
}
