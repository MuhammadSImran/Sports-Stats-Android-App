package com.example.sportstrackerapp.ui.news;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {
    private final NewsRepository newsRepository;
    private final MutableLiveData<List<Article>> articles = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public NewsViewModel() {
        newsRepository = new NewsRepository();
        fetchNHLNews();
    }

    private void fetchNHLNews() {
        newsRepository.fetchNHLNews(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articles.setValue(response.body().getArticles());
                } else {
                    errorMessage.setValue("Failed to fetch articles");
                    Log.e("NewsViewModel", "Failed to fetch articles");
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                errorMessage.setValue(t.getMessage());
                Log.e("NewsViewModel", "API failure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Article>> getArticles() {
        return articles;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
