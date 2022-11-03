package com.twentiecker.moviecatalogue.ui.film.tv;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.twentiecker.moviecatalogue.data.FilmRepository;
import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResponse;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;

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
public class TvViewModelTest {
    private TvViewModel viewModel;
    private List<TvResults> dummyTvs;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private Observer<List<TvResults>> observer;

    @Before
    public void setUp() throws IOException {
        viewModel = new TvViewModel(filmRepository);
        Call<TvResponse> tvResponseCall = ApiConfig.getApiService().getTvs("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        Response<TvResponse> tvResponseResponse = tvResponseCall.execute();
        dummyTvs = tvResponseResponse.body().getResults();
    }

    @Test
    public void getTvs() {
        MutableLiveData<List<TvResults>> tvs = new MutableLiveData<>();
        tvs.setValue(dummyTvs);

        when(filmRepository.getTvs()).thenReturn(tvs);
        List<TvResults> tvEntities = viewModel.getTvs().getValue();
        verify(filmRepository).getTvs();

        assertNotNull(tvEntities);
        assertEquals(dummyTvs.size(), tvEntities.size());
        assertEquals(dummyTvs.get(3).getId(), tvEntities.get(3).getId());

        viewModel.getTvs().observeForever(observer);
        verify(observer).onChanged(dummyTvs);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOutOfBoundsExceptionTvs() throws IndexOutOfBoundsException {
        MutableLiveData<List<TvResults>> tvs = new MutableLiveData<>();
        tvs.setValue(dummyTvs);

        when(filmRepository.getTvs()).thenReturn(tvs);
        List<TvResults> tvEntities = viewModel.getTvs().getValue();
        verify(filmRepository).getTvs();

        tvEntities.get(20);
    }
}