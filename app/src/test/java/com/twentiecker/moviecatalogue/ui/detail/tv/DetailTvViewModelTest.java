package com.twentiecker.moviecatalogue.ui.detail.tv;

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
public class DetailTvViewModelTest {
    private DetailTvViewModel detailTvViewModel;
    private TvResults dummyTv;
    private String tvId;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private Observer<TvResults> filmObserver;

    @Before
    public void setUp() throws Exception {
        Call<TvResponse> tvResponseCall = ApiConfig.getApiService().getTvs("c21b7029b812a242b36580f34db559c6",
                "en-US",
                1);
        Response<TvResponse> tvResponseResponse = tvResponseCall.execute();
        List<TvResults> tvResultsList = tvResponseResponse.body().getResults();
        dummyTv = tvResultsList.get(2);
        tvId = String.valueOf(dummyTv.getId());

        detailTvViewModel = new DetailTvViewModel(filmRepository);
        detailTvViewModel.setSelectedMovie(tvId);
    }

    @Test
    public void getTv() {
        MutableLiveData<TvResults> tv = new MutableLiveData<>();
        tv.setValue(dummyTv);
        when(filmRepository.getTvId(tvId)).thenReturn(tv);

        TvResults tvEntity = detailTvViewModel.getTv().getValue();
        verify(filmRepository).getTvId(tvId);

        assertNotNull(tvEntity);
        assertEquals(dummyTv.getFirstAirDate(), tvEntity.getFirstAirDate());
        assertEquals(dummyTv.getOverview(), tvEntity.getOverview());
        assertEquals(dummyTv.getOriginalLanguage(), tvEntity.getOriginalLanguage());
        assertEquals(dummyTv.getGenreIds(), tvEntity.getGenreIds());
        assertEquals(dummyTv.getPosterPath(), tvEntity.getPosterPath());
        assertEquals(dummyTv.getOriginCountry(), tvEntity.getOriginCountry());
        assertEquals(dummyTv.getBackdropPath(), tvEntity.getBackdropPath());
        assertEquals(dummyTv.getOriginalName(), tvEntity.getOriginalName());
        assertEquals(dummyTv.getPopularity(), tvEntity.getPopularity(), 0);
        assertEquals(dummyTv.getVoteAverage(), tvEntity.getVoteAverage(), 0);
        assertEquals(dummyTv.getName(), tvEntity.getName());
        assertEquals(dummyTv.getId(), tvEntity.getId());
        assertEquals(dummyTv.getVoteCount(), tvEntity.getVoteCount());

        detailTvViewModel.getTv().observeForever(filmObserver);
        verify(filmObserver).onChanged(dummyTv);
    }
}