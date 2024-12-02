package com.example.sportstrackerapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sportstrackerapp.R;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        Button helpButton = view.findViewById(R.id.button_help);
        Button notificationsButton = view.findViewById(R.id.button_notifications);

        // Handle button clicks
        helpButton.setOnClickListener(v -> {
            // Navigate to HelpFragment or perform help-related actions
        });

        notificationsButton.setOnClickListener(v -> {
            // Navigate to NotificationsFragment or handle notifications settings
        });

        return view;
    }
}
