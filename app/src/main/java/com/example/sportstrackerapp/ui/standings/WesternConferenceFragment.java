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

public class WesternConferenceFragment extends Fragment {  // western conference, showing central & pacific divisions
//    private StandingsAdapter centralAdapter;
//    private StandingsAdapter pacificAdapter;

    private StandingsAdapter standingsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conference_standings, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        standingsAdapter = new StandingsAdapter();
        recyclerView.setAdapter(standingsAdapter);

        StandingsViewModel standingsViewModel = new ViewModelProvider(this).get(StandingsViewModel.class);
        standingsViewModel.getStandings().observe(getViewLifecycleOwner(), standingsResponse -> {
            if (standingsResponse != null) {
                // Filter for Western Conference teams
                List<StandingsResponse.Standing> centralTeams = new ArrayList<>();
                List<StandingsResponse.Standing> pacificTeams = new ArrayList<>();

                for (StandingsResponse.Standing team : standingsResponse.getStandings()) {
                    if ("Central".equals(team.getDivisionName())) {
                        centralTeams.add(team);
                    } else if ("Pacific".equals(team.getDivisionName())) {
                        pacificTeams.add(team);
                    }
                }

                standingsAdapter.setWesternDivisionStandings(centralTeams, pacificTeams);

//                centralAdapter.updateStandings(centralTeams);
//                pacificAdapter.updateStandings(pacificTeams);
            }
        });

        return view;
    }
}
