package com.example.sportstrackerapp.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sportstrackerapp.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        ProgressBar progressBar = binding.progressBar;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new NewsAdapter(null);
        recyclerView.setAdapter(newsAdapter);

        // Observe the articles LiveData
        newsViewModel.getArticles().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null && !articles.isEmpty()) {
                newsAdapter.setArticles(articles);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No articles found", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe error messages
        newsViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
