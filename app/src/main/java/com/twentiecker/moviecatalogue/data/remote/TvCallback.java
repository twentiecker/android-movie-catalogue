package com.twentiecker.moviecatalogue.data.remote;

import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;

import java.util.List;

public interface TvCallback {
    void onAllTvReceived(List<TvResults> tvResultsList);
}
