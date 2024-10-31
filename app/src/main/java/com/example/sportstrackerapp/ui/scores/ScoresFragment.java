package com.example.sportstrackerapp.ui.scores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstrackerapp.R;

import java.util.ArrayList;
import java.util.List;

public class ScoresFragment extends Fragment {
    private GameAdapter gameAdapter;
    ScoresViewModel scoresViewModel;
    List<Game> gameList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_scores, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewScores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gameAdapter = new GameAdapter(new ArrayList<>());
        scoresViewModel = new ViewModelProvider(this).get(ScoresViewModel.class);
        scoresViewModel.getScores().observe(getViewLifecycleOwner(), gameWeek -> {
            if (gameWeek != null){
                for (GameWeek gameWeeks : gameWeek) {

                    gameList.addAll(gameWeeks.getGames());
                }
                gameAdapter = new GameAdapter(gameList);
                recyclerView.setAdapter(gameAdapter);
            }
        });


        return rootView;
    }

}
