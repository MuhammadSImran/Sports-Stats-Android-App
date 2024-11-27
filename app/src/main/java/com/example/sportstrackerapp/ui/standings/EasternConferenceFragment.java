package com.example.sportstrackerapp.ui.standings;

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

public class EasternConferenceFragment extends Fragment {  // eastern conference, showing atlantic & metropolitan divisions
//    private StandingsAdapter atlanticAdapter;
//    private StandingsAdapter metropolitanAdapter;

    private StandingsAdapter standingsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conference_standings, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //recyclerView.setNestedScrollingEnabled(false);

        standingsAdapter = new StandingsAdapter();
        recyclerView.setAdapter(standingsAdapter);

        StandingsViewModel standingsViewModel = new ViewModelProvider(this).get(StandingsViewModel.class);
        standingsViewModel.getStandings().observe(getViewLifecycleOwner(), standingsResponse -> {
            if (standingsResponse != null) {
                // Filter for Eastern Conference teams
                List<StandingsResponse.Standing> atlanticTeams = new ArrayList<>();
                List<StandingsResponse.Standing> metropolitanTeams = new ArrayList<>();

                for (StandingsResponse.Standing team : standingsResponse.getStandings()) {
                    if ("Atlantic".equals(team.getDivisionName())) {
                        atlanticTeams.add(team);
                    } else if ("Metropolitan".equals(team.getDivisionName())) {
                        metropolitanTeams.add(team);
                    }
                }

                standingsAdapter.setDivisionStandings(atlanticTeams, metropolitanTeams);
                standingsAdapter.notifyDataSetChanged();

//                atlanticAdapter.updateStandings(atlanticTeams);
//                metropolitanAdapter.updateStandings(metropolitanTeams);
            }
        });

        return view;
    }
}
