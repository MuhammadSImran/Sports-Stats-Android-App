package com.example.sportstrackerapp.ui.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstrackerapp.R;
import com.example.sportstrackerapp.ui.settings.NotificationHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Fragment for the news for only your favourite teams
public class FavouritesNewsFragment extends Fragment {
    private NewsViewModel newsViewModel;
    private ArticleAdapter articleAdapter;
    private Set<String> favouriteTeams;
    private SharedPreferences sharedPreferences;
    private String currentUser;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_news, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        TextView textViewNotLoggedIn = view.findViewById(R.id.textViewNotLoggedIn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        progressBar = view.findViewById(R.id.progressBar);

        articleAdapter = new ArticleAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(articleAdapter);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Check if the user is logged in
        currentUser = sharedPreferences.getString("savedUsername", null);
        if (currentUser == null) {
            // if user isn't logged in it displays a message and hides the recyclerview
            textViewNotLoggedIn.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            // if user is logged in it loads their favourite teams and articles
            textViewNotLoggedIn.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            loadFavouriteTeams(currentUser);

            progressBar.setVisibility(View.VISIBLE);
            newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
            newsViewModel.getNewsArticles("nhl", getContext()).observe(getViewLifecycleOwner(), articles -> {
                progressBar.setVisibility(View.GONE);
                if (articles != null) {
                    List<Article> favouriteArticles = filterFavouriteArticles(articles);
                    articleAdapter.setArticles(favouriteArticles);
                }
            });
        }
        return view;
    }

    private void loadFavouriteTeams(String currentUser) {
        favouriteTeams = sharedPreferences.getStringSet("favouriteTeams_" + this.currentUser, new HashSet<>());
    }

    private List<Article> filterFavouriteArticles(List<ArticleEntity> articles) {
        List<Article> favouriteArticles = new ArrayList<>();
        List<Article> domainArticles = ArticleMapper.mapToDomainList(articles);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        Set<String> savedArticleIds = sharedPreferences.getStringSet("savedArticles", new HashSet<>());

        Set<String> newArticleIds = new HashSet<>();
        for (Article article : domainArticles) {
            String content = (article.getTitle() != null ? article.getTitle() : "") +
                    " " +
                    (article.getDescription() != null ? article.getDescription() : "");

            for (String team : favouriteTeams) {
                if (content.toLowerCase().contains(team.toLowerCase())) {
                    favouriteArticles.add(article);
                    // checks if the article is new
                    String articleId = article.getUrl();
                    if (!savedArticleIds.contains(articleId)) {
                        newArticleIds.add(articleId);
                        // sends a notification for the new article
                        NotificationHelper.sendNotification(
                                requireContext(),
                                "New Article Posted!",
                                article.getTitle()
                        );
                    }
                    break;
                }
            }
        }
        savedArticleIds.addAll(newArticleIds);
        sharedPreferences.edit().putStringSet("savedArticles", savedArticleIds).apply();

        return favouriteArticles;
    }
}
