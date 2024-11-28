package com.example.sportstrackerapp.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sportstrackerapp.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavoriteTeamsFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private final String[] nhlTeams = {  // Not all teams added yet (use nhl api to get the teams)
            "Maple Leafs", "Panthers", "Bruins", "Canadiens", "Lightning", "Senators",
            "Capitals", "Islanders", "Rangers", "Flyers", "Hurricanes", "Blue Jackets"
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite_teams, container, false);
        LinearLayout teamsLayout = root.findViewById(R.id.teams_layout);
        Button saveButton = root.findViewById(R.id.save_button);
        Button backButton = root.findViewById(R.id.back_button);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        Set<String> savedFavorites = sharedPreferences.getStringSet("favoriteTeams", new HashSet<>());

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        for (String team : nhlTeams) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(team);
            checkBox.setChecked(savedFavorites.contains(team));
            teamsLayout.addView(checkBox);
            checkBoxes.add(checkBox);
        }

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, new LoginAcceptedFragment())
                    .commit();
            backButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            teamsLayout.setVisibility(View.GONE);
        });

        saveButton.setOnClickListener(v -> {
            Set<String> favoriteTeams = new HashSet<>();
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    favoriteTeams.add(checkBox.getText().toString());
                }
            }
            sharedPreferences.edit().putStringSet("favoriteTeams", favoriteTeams).apply();
        });
        return root;
    }
}
