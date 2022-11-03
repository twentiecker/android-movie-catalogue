package com.twentiecker.moviecatalogue.ui.film.movie;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.twentiecker.moviecatalogue.R;
import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.movie.MovieResults;
import com.twentiecker.moviecatalogue.databinding.ItemsFilmBinding;
import com.twentiecker.moviecatalogue.ui.detail.movie.DetailMovieActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final MovieFragmentCallback callback;
    private final List<MovieResults> movieResultsList = new ArrayList<>();

    MovieAdapter(MovieFragment callback) {
        this.callback = callback;
    }

    void setMoviesTmdb(List<MovieResults> listMovies) {
        if (listMovies == null) return;
        this.movieResultsList.clear();
        this.movieResultsList.addAll(listMovies);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsFilmBinding binding = ItemsFilmBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        MovieResults movieResults = movieResultsList.get(position);
        holder.bind(movieResults);
    }

    @Override
    public int getItemCount() {
        return movieResultsList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ItemsFilmBinding binding;

        MovieViewHolder(ItemsFilmBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(MovieResults movie) {
            binding.tvItemTitle.setText(movie.getTitle());
            binding.tvReleaseDate.setText(movie.getReleaseDate());
            binding.tvUserScore.setText(String.format("%s of 10", movie.getVoteAverage()));

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_FILM, String.valueOf(movie.getId()));
                itemView.getContext().startActivity(intent);
            });
            Glide.with(itemView.getContext())
                    .load(ApiConfig.BASE_IMG + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster);

            binding.imgShare.setOnClickListener(v -> callback.onShareClick(movie));
            Glide.with(itemView.getContext())
                    .load(ApiConfig.BASE_IMG + movie.getPosterPath())
                    .centerCrop()
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster);
        }
    }
}

