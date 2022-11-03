package com.twentiecker.moviecatalogue.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.data.remote.RemoteDataSource;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;

import java.util.ArrayList;
import java.util.List;

public class FilmRepository implements FilmDataSource {
    private volatile static FilmRepository INSTANCE = null;
    private final RemoteDataSource remoteDataSource;

    public FilmRepository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static FilmRepository getInstance(RemoteDataSource remoteDataSource) {
        if (INSTANCE == null) {
            synchronized (FilmRepository.class) {
                INSTANCE = new FilmRepository(remoteDataSource);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<MovieResults>> getMovies() {
        MutableLiveData<List<MovieResults>> movieResults = new MutableLiveData<>();
        remoteDataSource.getMovies(movieResultsList -> {
            ArrayList<MovieResults> movieList = new ArrayList<>();
            for (MovieResults response : movieResultsList) {
                MovieResults movie = new MovieResults(response.getOverview(),
                        response.getOriginalLanguage(),
                        response.getOriginalTitle(),
                        response.isVideo(),
                        response.getTitle(),
                        response.getGenreIds(),
                        response.getPosterPath(),
                        response.getBackdropPath(),
                        response.getReleaseDate(),
                        response.getPopularity(),
                        response.getVoteAverage(),
                        response.getId(),
                        response.isAdult(),
                        response.getVoteCount());
                movieList.add(movie);
            }
            movieResults.postValue(movieList);
        });

        return movieResults;
    }

    @Override
    public LiveData<List<TvResults>> getTvs() {
        MutableLiveData<List<TvResults>> tvResults = new MutableLiveData<>();
        remoteDataSource.getTvs(tvResultsList -> {
            ArrayList<TvResults> tvList = new ArrayList<>();
            for (TvResults response : tvResultsList) {
                TvResults tv = new TvResults(response.getFirstAirDate(),
                        response.getOverview(),
                        response.getOriginalLanguage(),
                        response.getGenreIds(),
                        response.getPosterPath(),
                        response.getOriginCountry(),
                        response.getBackdropPath(),
                        response.getOriginalName(),
                        response.getPopularity(),
                        response.getVoteAverage(),
                        response.getName(),
                        response.getId(),
                        response.getVoteCount());
                tvList.add(tv);
            }
            tvResults.postValue(tvList);
        });

        return tvResults;
    }

    @Override
    public LiveData<MovieResults> getMovieId(final String movieId) {
        MutableLiveData<MovieResults> movieResults = new MutableLiveData<>();
        remoteDataSource.getMovies(movieResultsList -> {
            MovieResults movie = null;
            for (MovieResults response : movieResultsList) {
                if (String.valueOf(response.getId()).equals(movieId)) {
                    movie = new MovieResults(response.getOverview(),
                            response.getOriginalLanguage(),
                            response.getOriginalTitle(),
                            response.isVideo(),
                            response.getTitle(),
                            response.getGenreIds(),
                            response.getPosterPath(),
                            response.getBackdropPath(),
                            response.getReleaseDate(),
                            response.getPopularity(),
                            response.getVoteAverage(),
                            response.getId(),
                            response.isAdult(),
                            response.getVoteCount());
                }
            }
            movieResults.postValue(movie);
        });

        return movieResults;
    }

    @Override
    public LiveData<TvResults> getTvId(final String tvId) {
        MutableLiveData<TvResults> tvResults = new MutableLiveData<>();
        remoteDataSource.getTvs(tvResultsList -> {
            TvResults tv = null;
            for (TvResults response : tvResultsList) {
                if (String.valueOf(response.getId()).equals(tvId)) {
                    tv = new TvResults(response.getFirstAirDate(),
                            response.getOverview(),
                            response.getOriginalLanguage(),
                            response.getGenreIds(),
                            response.getPosterPath(),
                            response.getOriginCountry(),
                            response.getBackdropPath(),
                            response.getOriginalName(),
                            response.getPopularity(),
                            response.getVoteAverage(),
                            response.getName(),
                            response.getId(),
                            response.getVoteCount());
                }
            }
            tvResults.postValue(tv);
        });

        return tvResults;
    }

}
