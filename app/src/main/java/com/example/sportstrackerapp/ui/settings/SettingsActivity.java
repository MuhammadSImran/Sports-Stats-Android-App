package com.example.sportstrackerapp.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportstrackerapp.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set the title for the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button helpButton = findViewById(R.id.button_help);
        Button notificationsButton = findViewById(R.id.button_notifications);

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

        notificationsButton.setOnClickListener(v -> {
            // opens notification dialog (not done yet)

        });
    }

    @Override
    public boolean onSupportNavigateUp() {  // when you press the back button
        finish();
        return true;
    }
}
