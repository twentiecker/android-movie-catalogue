package com.twentiecker.moviecatalogue;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.twentiecker.moviecatalogue.data.FilmRepository;
import com.twentiecker.moviecatalogue.di.Injection;
import com.twentiecker.moviecatalogue.ui.detail.movie.DetailMovieViewModel;
import com.twentiecker.moviecatalogue.ui.detail.tv.DetailTvViewModel;
import com.twentiecker.moviecatalogue.ui.film.movie.MovieViewModel;
import com.twentiecker.moviecatalogue.ui.film.tv.TvViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final FilmRepository filmRepository;

    public ViewModelFactory(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public static ViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(filmRepository);
        } else if (modelClass.isAssignableFrom(TvViewModel.class)) {
            return (T) new TvViewModel(filmRepository);
        } else if (modelClass.isAssignableFrom(DetailMovieViewModel.class)) {
            return (T) new DetailMovieViewModel(filmRepository);
        } else if (modelClass.isAssignableFrom(DetailTvViewModel.class)) {
            return (T) new DetailTvViewModel(filmRepository);
        }

        throw new IllegalArgumentException("Unknown View Model class: " + modelClass.getName());
    }
}
