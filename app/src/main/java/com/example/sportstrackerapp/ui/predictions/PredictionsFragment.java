package com.example.sportstrackerapp.ui.predictions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportstrackerapp.databinding.FragmentPredictionsBinding;

public class PredictionsFragment extends Fragment {

    private FragmentPredictionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        com.example.sportstrackerapp.ui.predictions.PredictionsViewModel PredictionsViewModel =
                new ViewModelProvider(this).get(com.example.sportstrackerapp.ui.predictions.PredictionsViewModel.class);

        binding = FragmentPredictionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
