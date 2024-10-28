package com.example.sportstrackerapp.ui.favouriteTeams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportstrackerapp.databinding.FragmentFavouriteTeamsBinding;
import com.example.sportstrackerapp.databinding.FragmentFavouriteTeamsBinding;
import com.example.sportstrackerapp.ui.favouriteTeams.FavouriteTeamsViewModel;

public class FavouriteTeamsFragment extends Fragment {

    private FragmentFavouriteTeamsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavouriteTeamsViewModel FavouriteTeamsViewModel = new ViewModelProvider(this).get(FavouriteTeamsViewModel.class);

        binding = FragmentFavouriteTeamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFavouriteTeams;
        FavouriteTeamsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}