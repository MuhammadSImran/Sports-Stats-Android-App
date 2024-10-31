package com.example.sportstrackerapp.ui.favouriteTeams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstrackerapp.R;
import com.example.sportstrackerapp.databinding.FragmentFavouriteTeamsBinding;
import com.example.sportstrackerapp.ui.favouriteTeams.adapters.FavouriteTeamsAdapter;
import com.example.sportstrackerapp.ui.favouriteTeams.models.Team;

import java.util.List;

public class FavouriteTeamsFragment extends Fragment {

    private FragmentFavouriteTeamsBinding binding;
    private FavouriteTeamsViewModel favouriteTeamsViewModel;
    private FavouriteTeamsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favouriteTeamsViewModel = new ViewModelProvider(this).get(FavouriteTeamsViewModel.class);
        binding = FragmentFavouriteTeamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up RecyclerView
        RecyclerView recyclerView = binding.recyclerViewFavouriteTeams;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavouriteTeamsAdapter(new FavouriteTeamsAdapter.OnTeamClickListener() {
            @Override
            public void onTeamClick(Team team) {
                favouriteTeamsViewModel.removeTeam(team);
                Toast.makeText(getContext(), team.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        // Observe the favorite teams list
        favouriteTeamsViewModel.getFavouriteTeams().observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                adapter.setTeams(teams);
            }
        });

        // Set up the EditText and button for adding a new team
        EditText editTextTeamName = binding.editTextTeamName;
        Button buttonAddTeam = binding.buttonAddTeam;

        buttonAddTeam.setOnClickListener(view -> {
            String teamName = editTextTeamName.getText().toString().trim();
            if (!teamName.isEmpty()) {
                favouriteTeamsViewModel.addTeam(new Team(teamName));
                editTextTeamName.setText("");
                Toast.makeText(getContext(), teamName + " added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please enter a team name", Toast.LENGTH_SHORT).show();
            }
        });

        // Button to delete all favorite teams
        Button buttonDeleteAll = binding.buttonDeleteAll;
        buttonDeleteAll.setOnClickListener(view -> {
            favouriteTeamsViewModel.clearAllTeams();
            Toast.makeText(getContext(), "All favorite teams cleared", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
