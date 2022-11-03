package com.twentiecker.moviecatalogue.ui.film.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.twentiecker.moviecatalogue.data.FilmRepository;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;

import java.util.List;

public class TvViewModel extends ViewModel {
    private final FilmRepository filmRepository;

    public TvViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public LiveData<List<TvResults>> getTvs() {
        return filmRepository.getTvs();
    }
}
