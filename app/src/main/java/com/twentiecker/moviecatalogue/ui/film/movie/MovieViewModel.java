package com.twentiecker.moviecatalogue.ui.film.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.data.FilmRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private final FilmRepository filmRepository;

    public MovieViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public LiveData<List<MovieResults>> getMovies() {
        return filmRepository.getMovies();
    }

}
