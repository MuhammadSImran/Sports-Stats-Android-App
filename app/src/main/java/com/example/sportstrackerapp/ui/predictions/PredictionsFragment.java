package com.example.sportstrackerapp.ui.predictions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstrackerapp.databinding.FragmentPredictionsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PredictionsFragment extends Fragment {

    private FragmentPredictionsBinding binding;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PredictionsAdapter adapter;
    private List<GameData> gamesList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPredictionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerViewPredictions;
        progressBar = binding.progressBarPredictions;
        setupRecyclerView();
        fetchGamesData();
        return root;
    }

    private void setupRecyclerView() {
        adapter = new PredictionsAdapter(gamesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void fetchGamesData() {
        progressBar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();

        String url = "https://api-web.nhle.com/v1/partner-game/US/now";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (!response.isSuccessful()) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                    }
                    return;
                }

                try {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray gamesArray = jsonObject.getJSONArray("games");

                    for (int i = 0; i < gamesArray.length(); i++) {
                        JSONObject game = gamesArray.getJSONObject(i);

                        JSONObject homeTeam = game.getJSONObject("homeTeam");
                        JSONObject awayTeam = game.getJSONObject("awayTeam");

                        String homeName = homeTeam.getJSONObject("name").getString("default");
                        String awayName = awayTeam.getJSONObject("name").getString("default");

                        // Convert odds to probabilities
                        int homeOddsValue = homeTeam.getJSONArray("odds").getJSONObject(0).getInt("value");
                        int awayOddsValue = awayTeam.getJSONArray("odds").getJSONObject(0).getInt("value");

                        double homeOddsPercentage = calculateProbability(homeOddsValue);
                        double awayOddsPercentage = 100.0 - homeOddsPercentage; // Losing team's odds

                        // Ensure the better odds are for the team with higher probability
                        if (awayOddsPercentage > homeOddsPercentage) {
                            // Swap values if away team has better odds
                            double temp = homeOddsPercentage;
                            homeOddsPercentage = awayOddsPercentage;
                            awayOddsPercentage = temp;
                        }

                        String homeOdds = String.format("%.2f%%", homeOddsPercentage);
                        String awayOdds = String.format("%.2f%%", awayOddsPercentage);

                        String homeLogoUrl = homeTeam.getString("logo");
                        String awayLogoUrl = awayTeam.getString("logo");

                        gamesList.add(new GameData(homeName, awayName, homeOdds, awayOdds, homeLogoUrl, awayLogoUrl));
                    }

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                    }
                }
            }

            // Helper method to calculate the probability from betting odds
            private double calculateProbability(int odds) {
                if (odds < 0) { // Negative odds
                    return ((-1.0 * odds) / ((-1.0 * odds) + 100)) * 100;
                } else { // Positive odds
                    return (100.0 / (odds + 100)) * 100;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
