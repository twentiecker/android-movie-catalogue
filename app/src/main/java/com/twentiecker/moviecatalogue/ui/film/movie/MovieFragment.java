package com.twentiecker.moviecatalogue.ui.film.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twentiecker.moviecatalogue.ViewModelFactory;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.databinding.FragmentMovieBinding;

public class MovieFragment extends Fragment implements MovieFragmentCallback{
    private FragmentMovieBinding fragmentMovieBinding;

    public MovieFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentMovieBinding = FragmentMovieBinding.inflate(inflater, container, false);
        return fragmentMovieBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            MovieViewModel viewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

            MovieAdapter movieAdapter = new MovieAdapter(this);

            fragmentMovieBinding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getMovies().observe(this, movieResults -> {
                fragmentMovieBinding.progressBar.setVisibility(View.GONE);
                movieAdapter.setMoviesTmdb(movieResults);
                movieAdapter.notifyDataSetChanged();
            });

            fragmentMovieBinding.rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
            fragmentMovieBinding.rvMovies.setHasFixedSize(true);
            fragmentMovieBinding.rvMovies.setAdapter(movieAdapter);
        }
    }

    @Override
    public void onShareClick(MovieResults movie) {
        if (getActivity() != null) {
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType(mimeType)
                    .setChooserTitle("Bagikan film ini sekarang.")
                    .setText(String.format("Jangan lewatkan film \"%s\" di bioskop kesayangan anda", movie.getTitle()))
                    .startChooser();
        }
    }
}