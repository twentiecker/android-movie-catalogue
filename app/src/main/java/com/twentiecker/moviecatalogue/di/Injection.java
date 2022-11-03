package com.twentiecker.moviecatalogue.di;

import android.content.Context;

import com.twentiecker.moviecatalogue.data.FilmRepository;
import com.twentiecker.moviecatalogue.data.remote.RemoteDataSource;
import com.twentiecker.moviecatalogue.utils.JsonHelper;

public class Injection {
    public static FilmRepository provideRepository(Context context) {
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(new JsonHelper(context));
        return FilmRepository.getInstance(remoteDataSource);
    }
}
