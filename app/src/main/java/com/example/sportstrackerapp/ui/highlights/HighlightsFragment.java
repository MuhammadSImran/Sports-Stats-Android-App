package com.example.sportstrackerapp.ui.highlights;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportstrackerapp.databinding.FragmentHighlightsBinding;

public class HighlightsFragment extends Fragment {

    private FragmentHighlightsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        com.example.sportstrackerapp.ui.highlights.HighlightsViewModel HighlightsViewModel =
                new ViewModelProvider(this).get(com.example.sportstrackerapp.ui.highlights.HighlightsViewModel.class);

        binding = FragmentHighlightsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHighlights;
        HighlightsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}