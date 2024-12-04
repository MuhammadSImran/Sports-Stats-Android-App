package com.example.sportstrackerapp.ui.news;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApiService apiService;
    private final ArticleDao articleDao;
    private final ExecutorService executorService;

    public NewsRepository(NewsApiService apiService, ArticleDatabase database) {
        this.apiService = apiService;
        this.articleDao = database.articleDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ArticleEntity>> getNewsArticles(String query, Context context) {
        MutableLiveData<List<ArticleEntity>> liveData = new MutableLiveData<>();
        apiService.getLatestHockeyNews("nhl", "publishedAt", "en", "7ddfac08b1254b6693a1c62aa2101b7b")
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Article> articles = response.body().getArticles();
                            List<ArticleEntity> articleEntities = ArticleMapper.mapToEntityList(articles);

                            // saves to the database on a background thread
                            executorService.execute(() -> {
                                articleDao.clearArticles();
                                articleDao.insertArticles(articleEntities);
                                liveData.postValue(articleEntities);
                            });
                        } else {
                            // use stored data if the api fails
                            liveData.postValue(articleDao.getAllArticles().getValue());
                        }
                    }
                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        liveData.postValue(articleDao.getAllArticles().getValue());
                    }
                });

        return articleDao.getAllArticles();
    }
}
