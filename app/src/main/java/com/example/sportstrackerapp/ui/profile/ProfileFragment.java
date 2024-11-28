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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportstrackerapp.R;
import com.example.sportstrackerapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Buttons
        EditText editUsername = root.findViewById(R.id.edit_username);
        EditText editPassword = root.findViewById(R.id.edit_password);
        Button buttonLogin = root.findViewById(R.id.button_login);
        Button buttonSignup = root.findViewById(R.id.button_signup);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Login functionality
        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String savedPassword = sharedPreferences.getString(username, null);
            if (savedPassword != null && savedPassword.equals(password)) {
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply();
                sharedPreferences.edit().putString("username", username).apply();

                // Navigate to LoginAcceptedFragment
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new LoginAcceptedFragment())
                        .commit();
                editUsername.setVisibility(View.GONE);
                editPassword.setVisibility(View.GONE);
                buttonLogin.setVisibility(View.GONE);
                buttonSignup.setVisibility(View.GONE);
            } else {
                Toast.makeText(getContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        });

        // Sign-up functionality
        buttonSignup.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            sharedPreferences.edit().putString(username, password).apply();
            Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}
