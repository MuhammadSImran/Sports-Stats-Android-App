package com.example.sportstrackerapp.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sportstrackerapp.R;

public class LoginAcceptedFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login_accepted, container, false);

        Button buttonFavoriteTeams = root.findViewById(R.id.button_fav_teams);
        Button logoutButton = root.findViewById(R.id.logout_button);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Navigate to FavoriteTeamsFragment when "Select Favorite Teams" is clicked
        buttonFavoriteTeams.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, new FavoriteTeamsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Logout functionality
        logoutButton.setOnClickListener(v -> {
            sharedPreferences.edit().clear().apply();
            // Navigate back to LoginFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, new ProfileFragment())
                    .commit();
        });

        return root;
    }
}