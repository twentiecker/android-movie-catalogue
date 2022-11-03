package com.twentiecker.moviecatalogue.ui.film.movie;

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
public class MovieViewModelTest {
    private MovieViewModel viewModel;
    private List<MovieResults> dummyMovies;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private Observer<List<MovieResults>> observer;

    @Before
    public void setUp() throws IOException {
        viewModel = new MovieViewModel(filmRepository);
        Call<MovieResponse> movieResponseCall = ApiConfig.getApiService().getMovies("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        Response<MovieResponse> movieResponseResponse = movieResponseCall.execute();
        dummyMovies = movieResponseResponse.body().getResults();
    }

    @Test
    public void getMovies() {
        MutableLiveData<List<MovieResults>> movies = new MutableLiveData<>();
        movies.setValue(dummyMovies);

        when(filmRepository.getMovies()).thenReturn(movies);
        List<MovieResults> movieEntities = viewModel.getMovies().getValue();
        verify(filmRepository).getMovies();

        assertNotNull(movieEntities);
        assertEquals(dummyMovies.size(), movieEntities.size());
        assertEquals(dummyMovies.get(0).getId(), movieEntities.get(0).getId());

        viewModel.getMovies().observeForever(observer);
        verify(observer).onChanged(dummyMovies);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOutOfBoundsExceptionMovies() throws IndexOutOfBoundsException {
        MutableLiveData<List<MovieResults>> movies = new MutableLiveData<>();
        movies.setValue(dummyMovies);

        when(filmRepository.getMovies()).thenReturn(movies);
        List<MovieResults> movieEntities = viewModel.getMovies().getValue();
        verify(filmRepository).getMovies();

        movieEntities.get(20);
    }
}