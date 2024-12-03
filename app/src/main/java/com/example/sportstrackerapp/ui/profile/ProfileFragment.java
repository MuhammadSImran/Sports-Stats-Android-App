package com.example.sportstrackerapp.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sportstrackerapp.R;

public class ProfileFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // UI Components
        EditText editUsername = root.findViewById(R.id.edit_username);
        EditText editPassword = root.findViewById(R.id.edit_password);
        Button buttonLogin = root.findViewById(R.id.button_login);
        Button buttonSignup = root.findViewById(R.id.button_signup);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Auto-fill saved username and password if available
        String savedUsername = sharedPreferences.getString("savedUsername", null);
        String savedPassword = sharedPreferences.getString("savedPassword", null);

        if (savedUsername != null && savedPassword != null) {
            editUsername.setText(savedUsername);
            editPassword.setText(savedPassword);
        }

        // Login functionality
        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show();
                return;
            }

            String savedPasswordFromPrefs = sharedPreferences.getString(username, null);
            if (savedPasswordFromPrefs != null && savedPasswordFromPrefs.equals(password)) {
                sharedPreferences.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString("savedUsername", username) // Save credentials for next session
                        .putString("savedPassword", password)
                        .apply();

                // Navigate to LoginAcceptedFragment
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_profile, new LoginAcceptedFragment())
                        .commit();
                editUsername.setVisibility(View.GONE);
                editPassword.setVisibility(View.GONE);
                buttonLogin.setVisibility(View.GONE);
                buttonSignup.setVisibility(View.GONE);
            } else {
                Toast.makeText(getContext(), getString(R.string.invalid_username_or_password), Toast.LENGTH_SHORT).show();
            }
        });

        // Sign-up functionality
        buttonSignup.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the username already exists
            if (sharedPreferences.contains(username)) {
                Toast.makeText(getContext(), getString(R.string.username_already_taken), Toast.LENGTH_SHORT).show();
                return;
            }

            // Save the new account credentials
            sharedPreferences.edit()
                    .putString(username, password) // Save the new account credentials
                    .putString("savedUsername", username) // Save credentials for next session
                    .putString("savedPassword", password)
                    .apply();
            Toast.makeText(getContext(), getString(R.string.account_created_successfully), Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}
