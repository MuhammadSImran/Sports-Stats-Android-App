package com.example.sportstrackerapp.ui.news;

import android.content.Context;
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

import com.example.sportstrackerapp.ui.profile.FavouriteTeamsFragment;
import com.example.sportstrackerapp.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Fragment for the news for only your favourite teams
public class FavouritesNewsFragment extends Fragment {
    private NewsViewModel newsViewModel;
    private ArticleAdapter articleAdapter;
    private Set<String> favouriteTeams;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_news, container, false); // Reuse layout
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        articleAdapter = new ArticleAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(articleAdapter);

        loadFavouriteTeams();

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getNewsArticles("nhl").observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                List<Article> favouriteArticles = filterFavouriteArticles(articles);
                articleAdapter.setArticles(favouriteArticles);
            }
        });

        return view;
    }

    private void loadFavouriteTeams() {
        favouriteTeams = new HashSet<>();
        if (getContext() != null) {
            favouriteTeams = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    .getStringSet("favouriteTeams_" + FavouriteTeamsFragment.currentUser, new HashSet<>());
        }
    }

    private List<Article> filterFavouriteArticles(List<Article> articles) {
        List<Article> favouriteArticles = new ArrayList<>();
        for (Article article : articles) {
            String content = article.getTitle() + " " + article.getDescription();
            for (String team : favouriteTeams) {
                if (content.toLowerCase().contains(team.toLowerCase())) {
                    favouriteArticles.add(article);
                    break;
                }
            }
        }
        return favouriteArticles;
    }
}
