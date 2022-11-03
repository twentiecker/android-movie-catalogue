package com.twentiecker.moviecatalogue.ui.film.tv;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.twentiecker.moviecatalogue.R;
import com.twentiecker.moviecatalogue.data.api.ApiConfig;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;
import com.twentiecker.moviecatalogue.databinding.ItemsFilmBinding;
import com.twentiecker.moviecatalogue.ui.detail.tv.DetailTvActivity;

import java.util.ArrayList;
import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private final TvFragmentCallback callback;
    private final List<TvResults> listTvs = new ArrayList<>();

    TvAdapter(TvFragment callback) {
        this.callback = callback;
    }

    void setTvs(List<TvResults> listTvs) {
        if (listTvs == null) return;
        this.listTvs.clear();
        this.listTvs.addAll(listTvs);
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsFilmBinding binding = ItemsFilmBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TvViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvViewHolder holder, int position) {
        TvResults tv = listTvs.get(position);
        holder.bind(tv);
    }

    @Override
    public int getItemCount() {
        return listTvs.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {

        private final ItemsFilmBinding binding;

        TvViewHolder(ItemsFilmBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(TvResults tv) {
            binding.tvItemTitle.setText(tv.getName());
            binding.tvReleaseDate.setText(tv.getFirstAirDate());
            binding.tvUserScore.setText(String.format("%s of 10", tv.getVoteAverage()));

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.EXTRA_FILM, String.valueOf(tv.getId()));
                itemView.getContext().startActivity(intent);
            });
            Glide.with(itemView.getContext())
                    .load(ApiConfig.BASE_IMG + tv.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster);

            binding.imgShare.setOnClickListener(v -> callback.onShareClick(tv));
            Glide.with(itemView.getContext())
                    .load(ApiConfig.BASE_IMG + tv.getPosterPath())
                    .centerCrop()
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster);
        }
    }
}

