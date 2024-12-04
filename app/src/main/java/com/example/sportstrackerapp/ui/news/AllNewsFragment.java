package com.example.sportstrackerapp.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstrackerapp.R;

import java.util.ArrayList;

public class AllNewsFragment extends Fragment {
    private NewsViewModel newsViewModel;
    private ArticleAdapter articleAdapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_news, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        progressBar = view.findViewById(R.id.progressBar);

        articleAdapter = new ArticleAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(articleAdapter);

        progressBar.setVisibility(View.VISIBLE);

        newsViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(NewsViewModel.class);
        newsViewModel.getNewsArticles("nhl", requireContext()).observe(getViewLifecycleOwner(), articles -> {
            progressBar.setVisibility(View.GONE);
            if (articles != null) {
                articleAdapter.setArticles(ArticleMapper.mapToDomainList(articles));
            }
        });

        return view;
    }
}
