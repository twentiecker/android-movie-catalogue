package com.twentiecker.moviecatalogue.ui.detail.tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.twentiecker.moviecatalogue.data.FilmRepository;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;

public class DetailTvViewModel extends ViewModel {
    private String tvId;
    private final FilmRepository filmRepository;

    public DetailTvViewModel(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public void setSelectedMovie(String tvId) {
        this.tvId = tvId;
    }

    public LiveData<TvResults> getTv() {
        return filmRepository.getTvId(tvId);
    }
}
