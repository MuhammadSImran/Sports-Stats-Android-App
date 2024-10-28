package com.example.sportstrackerapp.ui.standings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportstrackerapp.databinding.FragmentStandingsBinding;

public class StandingsFragment extends Fragment {

    private FragmentStandingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StandingsViewModel StandingsViewModel =
                new ViewModelProvider(this).get(StandingsViewModel.class);

        binding = FragmentStandingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStandings;
        StandingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}