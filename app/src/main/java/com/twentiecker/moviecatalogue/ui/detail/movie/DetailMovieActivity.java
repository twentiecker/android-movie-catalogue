package com.twentiecker.moviecatalogue.ui.detail.movie;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.twentiecker.moviecatalogue.R;
import com.twentiecker.moviecatalogue.ViewModelFactory;
import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.databinding.ActivityDetailMovieBinding;
import com.twentiecker.moviecatalogue.databinding.ContentDetailMovieBinding;

public class DetailMovieActivity extends AppCompatActivity {

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
        DetailMovieViewModel viewModel = new ViewModelProvider(this, factory).get(DetailMovieViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String movieId = extras.getString(EXTRA_FILM);
            if (movieId != null) {
                viewModel.setSelectedMovie(movieId);
                viewModel.getMovie().observe(this, this::populateMovie);
            }
        }
    }

    private void populateMovie(MovieResults movieResults) {
        activityDetailMovieBinding.content.setVisibility(View.VISIBLE);
        activityDetailMovieBinding.progressBar.setVisibility(View.GONE);

        detailContentBinding.tvReleaseDate.setText(movieResults.getReleaseDate());
        detailContentBinding.tvTitle.setText(movieResults.getTitle());
        detailContentBinding.tvOverview.setText(movieResults.getOverview());
        detailContentBinding.tvPopularity.setText(String.valueOf(movieResults.getPopularity()));
        detailContentBinding.tvUserScore.setText(String.format("%s of 10", movieResults.getVoteAverage()));
        detailContentBinding.tvOriginalTitle.setText(movieResults.getOriginalTitle());

        Glide.with(this)
                .load(ApiConfig.BASE_IMG + movieResults.getBackdropPath())
                .transform(new RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .centerCrop()
                .into(detailContentBinding.imageBackdrop);

        Glide.with(this)
                .load(ApiConfig.BASE_IMG + movieResults.getPosterPath())
                .transform(new RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .centerCrop()
                .into(detailContentBinding.imagePoster);
    }
}