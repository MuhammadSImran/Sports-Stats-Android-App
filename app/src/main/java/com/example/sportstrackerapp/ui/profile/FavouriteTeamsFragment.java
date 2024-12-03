package com.example.sportstrackerapp.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

public class FavouriteTeamsFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    public static String currentUser;
    private final String[] nhlTeams = {
            "Maple Leafs", "Panthers", "Bruins", "Canadiens", "Lightning", "Senators",
            "Capitals", "Islanders", "Rangers", "Flyers", "Hurricanes", "Blue Jackets",
            "Sabres", "Red Wings", "Devils", "Penguins", "Jets", "Wild", "Stars", "Avalanche",
            "Blues", "Utah", "Predators", "Blackhawks", "Golden Knights", "Kings", "Flames",
            "Canucks", "Oilers", "Kraken", "Ducks", "Sharks"
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite_teams, container, false);
        LinearLayout teamsLayout = root.findViewById(R.id.teams_layout);
        Button saveButton = root.findViewById(R.id.save_button);
        Button backButton = root.findViewById(R.id.back_button);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        currentUser = sharedPreferences.getString("savedUsername", null);

        // if their is no user logged in
        if (currentUser == null) {
            return root;
        }

        // Gets the users favourite teams from the profile tab
        Set<String> savedFavourites = sharedPreferences.getStringSet("favouriteTeams_" + currentUser, new HashSet<>());

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        for (String team : nhlTeams) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(team);
            checkBox.setChecked(savedFavourites.contains(team));
            teamsLayout.addView(checkBox);
            checkBoxes.add(checkBox);
        }

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_profile, new LoginAcceptedFragment())
                    .commit();
        });

        saveButton.setOnClickListener(v -> {
            Set<String> favouriteTeams = new HashSet<>();
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    favouriteTeams.add(checkBox.getText().toString());
                }
            }
            //sharedPreferences.edit().putStringSet("favouriteTeams_" + currentUser, favouriteTeams).apply();
            new SaveFavouritesTask(sharedPreferences, currentUser, favouriteTeams).execute();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        LinearLayout teamsLayout = getView().findViewById(R.id.teams_layout);
        if (teamsLayout != null) {
            teamsLayout.removeAllViews();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sharedPreferences = null;
    }

    // Uses AsyncTask to save the favourite teams data
    private static class SaveFavouritesTask extends AsyncTask<Void, Void, Void> {
        private final SharedPreferences sharedPreferences;
        private final String currentUser;
        private final Set<String> favouriteTeams;

        SaveFavouritesTask(SharedPreferences sharedPreferences, String currentUser, Set<String> favouriteTeams) {
            this.sharedPreferences = sharedPreferences;
            this.currentUser = currentUser;
            this.favouriteTeams = favouriteTeams;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sharedPreferences.edit().putStringSet("favouriteTeams_" + currentUser, favouriteTeams).apply();
            return null;
        }
    }
}
