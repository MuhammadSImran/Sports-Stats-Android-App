package com.example.sportstrackerapp.ui.favouriteTeams;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavouriteTeamsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FavouriteTeamsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}