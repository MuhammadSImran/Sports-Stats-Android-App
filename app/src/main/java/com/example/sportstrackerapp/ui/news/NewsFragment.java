package com.example.sportstrackerapp.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportstrackerapp.R;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private NewsViewModel newsViewModel;
    private ArticleAdapter articleAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        // Initialize an empty adapter to avoid the "No adapter attached" warning
        articleAdapter = new ArticleAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(articleAdapter);

        // Initialize ViewModel and observe data
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        newsViewModel.getNewsArticles("nhl").observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                articleAdapter = new ArticleAdapter(getContext(), articles);
                recyclerView.setAdapter(articleAdapter);
            }
        });

        return view;
    }
}
