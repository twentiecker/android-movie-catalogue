package com.twentiecker.moviecatalogue.ui.detail.movie;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.twentiecker.moviecatalogue.data.FilmRepository;
import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResponse;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetailMovieViewModelTest {
    private DetailMovieViewModel detailMovieViewModel;
    private MovieResults dummyMovie;
    private String movieId;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private Observer<MovieResults> filmObserver;

    @Before
    public void setUp() throws Exception {
        Call<MovieResponse> movieResponseCall = ApiConfig.getApiService().getMovies("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        Response<MovieResponse> movieResponseResponse = movieResponseCall.execute();
        List<MovieResults> movieResultsList = movieResponseResponse.body().getResults();
        dummyMovie = movieResultsList.get(2);
        movieId = String.valueOf(dummyMovie.getId());

        detailMovieViewModel = new DetailMovieViewModel(filmRepository);
        detailMovieViewModel.setSelectedMovie(movieId);
    }

    @Test
    public void getMovie() {
        MutableLiveData<MovieResults> movie = new MutableLiveData<>();
        movie.setValue(dummyMovie);
        when(filmRepository.getMovieId(movieId)).thenReturn(movie);

        MovieResults movieEntity = detailMovieViewModel.getMovie().getValue();
        verify(filmRepository).getMovieId(movieId);

        assertNotNull(movieEntity);
        assertEquals(dummyMovie.getOverview(), movieEntity.getOverview());
        assertEquals(dummyMovie.getOriginalLanguage(), movieEntity.getOriginalLanguage());
        assertEquals(dummyMovie.getOriginalTitle(), movieEntity.getOriginalTitle());
        assertEquals(dummyMovie.isVideo(), movieEntity.isVideo());
        assertEquals(dummyMovie.getTitle(), movieEntity.getTitle());
        assertEquals(dummyMovie.getGenreIds(), movieEntity.getGenreIds());
        assertEquals(dummyMovie.getPosterPath(), movieEntity.getPosterPath());
        assertEquals(dummyMovie.getBackdropPath(), movieEntity.getBackdropPath());
        assertEquals(dummyMovie.getReleaseDate(), movieEntity.getReleaseDate());
        assertEquals(dummyMovie.getPopularity(), movieEntity.getPopularity(), 0);
        assertEquals(dummyMovie.getVoteAverage(), movieEntity.getVoteAverage(), 0);
        assertEquals(dummyMovie.getId(), movieEntity.getId());
        assertEquals(dummyMovie.isAdult(), movieEntity.isAdult());
        assertEquals(dummyMovie.getVoteCount(), movieEntity.getVoteCount());

        detailMovieViewModel.getMovie().observeForever(filmObserver);
        verify(filmObserver).onChanged(dummyMovie);
    }
}