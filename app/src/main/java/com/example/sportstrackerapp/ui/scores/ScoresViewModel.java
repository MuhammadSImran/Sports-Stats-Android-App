package com.example.sportstrackerapp.ui.scores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoresViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ScoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}