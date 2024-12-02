package com.example.sportstrackerapp.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.sportstrackerapp.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize the theme switch
        Switch switchTheme = findViewById(R.id.switch_theme);

        // Access shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        switchTheme.setChecked(isDarkMode);

        // Handle theme switch toggling
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("darkMode", isChecked);
            editor.apply();

            // Apply theme
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Set the title for the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Help button
        Button helpButton = findViewById(R.id.button_help);
        helpButton.setOnClickListener(v -> {
            // Opens help dialog
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_help, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView)
                    .setTitle("Help")
                    .setPositiveButton("Close", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });

        // Notifications button
        Button notificationsButton = findViewById(R.id.button_notifications);
        notificationsButton.setOnClickListener(v -> {
            // Opens notification dialog (not implemented yet)
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle back button in the action bar
        finish();
        return true;
    }
}
