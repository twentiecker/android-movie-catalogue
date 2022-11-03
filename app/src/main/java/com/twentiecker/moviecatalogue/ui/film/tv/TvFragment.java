package com.twentiecker.moviecatalogue.ui.film.tv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.twentiecker.moviecatalogue.ViewModelFactory;
import com.twentiecker.moviecatalogue.data.entity.tv.TvResults;
import com.twentiecker.moviecatalogue.databinding.FragmentTvBinding;

public class TvFragment extends Fragment implements TvFragmentCallback {
    private FragmentTvBinding fragmentTvBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentTvBinding = FragmentTvBinding.inflate(inflater, container, false);
        return fragmentTvBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            TvViewModel viewModel = new ViewModelProvider(this, factory).get(TvViewModel.class);

            TvAdapter tvAdapter = new TvAdapter(this);

            viewModel.getTvs().observe(this, tvResultsList -> {
                fragmentTvBinding.progressBar.setVisibility(View.GONE);
                tvAdapter.setTvs(tvResultsList);
                tvAdapter.notifyDataSetChanged();
            });

            fragmentTvBinding.rvTv.setLayoutManager(new LinearLayoutManager(getContext()));
            fragmentTvBinding.rvTv.setHasFixedSize(true);
            fragmentTvBinding.rvTv.setAdapter(tvAdapter);
        }
    }

    @Override
    public void onShareClick(TvResults tv) {
        if (getActivity() != null) {
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType(mimeType)
                    .setChooserTitle("Bagikan serial tv ini sekarang.")
                    .setText(String.format("Jangan lewatkan serial \"%s\" di media streaming kesayangan anda", tv.getName()))
                    .startChooser();
        }
    }
}