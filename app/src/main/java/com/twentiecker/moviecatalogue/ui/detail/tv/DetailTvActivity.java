package com.twentiecker.moviecatalogue.ui.detail.tv;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.twentiecker.moviecatalogue.R;
import com.twentiecker.moviecatalogue.ViewModelFactory;
import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;
import com.twentiecker.moviecatalogue.databinding.ActivityDetailMovieBinding;
import com.twentiecker.moviecatalogue.databinding.ContentDetailMovieBinding;

public class DetailTvActivity extends AppCompatActivity {

    public static final String EXTRA_FILM = "extra film";
    private ContentDetailMovieBinding detailContentBinding;
    private ActivityDetailMovieBinding activityDetailMovieBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDetailMovieBinding = ActivityDetailMovieBinding.inflate(getLayoutInflater());
        detailContentBinding = activityDetailMovieBinding.detailContent;

        setContentView(activityDetailMovieBinding.getRoot());

        setSupportActionBar(activityDetailMovieBinding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        DetailTvViewModel viewModel = new ViewModelProvider(this, factory).get(DetailTvViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tvId = extras.getString(EXTRA_FILM);
            if (tvId != null) {
                viewModel.setSelectedMovie(tvId);
                viewModel.getTv().observe(this, this::populateTv);
            }
        }
    }

    private void populateTv(TvResults tvResults) {
        activityDetailMovieBinding.content.setVisibility(View.VISIBLE);
        activityDetailMovieBinding.progressBar.setVisibility(View.GONE);

        detailContentBinding.tvReleaseDate.setText(tvResults.getFirstAirDate());
        detailContentBinding.tvTitle.setText(tvResults.getName());
        detailContentBinding.tvOverview.setText(tvResults.getOverview());
        detailContentBinding.tvPopularity.setText(String.valueOf(tvResults.getPopularity()));
        detailContentBinding.tvUserScore.setText(String.format("%s of 10", tvResults.getVoteAverage()));
        detailContentBinding.tvOriginalTitle.setText(tvResults.getOriginalName());

        Glide.with(this)
                .load(ApiConfig.BASE_IMG + tvResults.getPosterPath())
                .transform(new RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .centerCrop()
                .into(detailContentBinding.imagePoster);

        Glide.with(this)
                .load(ApiConfig.BASE_IMG + tvResults.getBackdropPath())
                .transform(new RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .centerCrop()
                .into(detailContentBinding.imageBackdrop);

    }
}