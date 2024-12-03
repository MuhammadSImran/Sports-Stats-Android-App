package com.example.sportstrackerapp.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sportstrackerapp.R;

public class LoginAcceptedFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login_accepted, container, false);

        Button buttonFavouriteTeams = root.findViewById(R.id.button_fav_teams);
        Button logoutButton = root.findViewById(R.id.logout_button);
        Button changePasswordButton = root.findViewById(R.id.change_password_button);
        Button deleteAccountButton = root.findViewById(R.id.delete_account_button);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("savedUsername", null);

        // Navigate to FavouriteTeamsFragment
        buttonFavouriteTeams.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_profile, new FavouriteTeamsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Logout functionality
        logoutButton.setOnClickListener(v -> {
            sharedPreferences.edit()
                    .putBoolean("isLoggedIn", false)
                    .remove("savedUsername")
                    .remove("savedPassword")
                    .apply();

            // Navigate back to ProfileFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_profile, new ProfileFragment())
                    .commit();
        });

        // Change password functionality
        changePasswordButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(getString(R.string.change_password));

            // Input fields for old and new password
            View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_change_password, null);
            EditText oldPasswordInput = dialogView.findViewById(R.id.old_password_input);
            EditText newPasswordInput = dialogView.findViewById(R.id.new_password_input);
            builder.setView(dialogView);

            builder.setPositiveButton(getString(R.string.change), (dialog, which) -> {
                String oldPassword = oldPasswordInput.getText().toString().trim();
                String newPassword = newPasswordInput.getText().toString().trim();

                String savedPassword = sharedPreferences.getString(currentUsername, null);
                if (savedPassword != null && savedPassword.equals(oldPassword)) {
                    sharedPreferences.edit().putString(currentUsername, newPassword).apply();
                    Toast.makeText(requireContext(), getString(R.string.password_change_successful), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), getString(R.string.incorrect_old_password), Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });

        // Delete account functionality
        deleteAccountButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(getString(R.string.delete_account))
                    .setMessage(getString(R.string.delete_message))
                    .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                        // Use the captured value of currentUsername
                        String usernameToDelete = sharedPreferences.getString("savedUsername", null);
                        if (usernameToDelete != null) {
                            // Remove all data associated with the account
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove(usernameToDelete); // Remove account credentials
                            editor.remove("favouriteTeams_" + usernameToDelete); // Remove favourite teams
                            editor.putBoolean("isLoggedIn", false); // Clear login state
                            editor.remove("savedUsername"); // Remove current session username
                            editor.remove("savedPassword"); // Remove current session password
                            editor.apply();

                            // Notify the user
                            Toast.makeText(requireContext(), getString(R.string.account_delete_successful), Toast.LENGTH_SHORT).show();

                            // Navigate back to ProfileFragment
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_profile, new ProfileFragment())
                                    .commit();
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });

        return root;
    }
}
