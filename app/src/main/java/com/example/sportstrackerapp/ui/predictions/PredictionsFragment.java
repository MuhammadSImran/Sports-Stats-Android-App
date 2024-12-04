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

                        int homeOddsValue = homeTeam.getJSONArray("odds").getJSONObject(0).getInt("value");
                        int awayOddsValue = awayTeam.getJSONArray("odds").getJSONObject(0).getInt("value");

                        String homeOddsPercentage = convertOddsToPercentage(homeOddsValue);
                        String awayOddsPercentage = convertOddsToPercentage(awayOddsValue);

                        String homeLogoUrl = homeTeam.getString("logo");
                        String awayLogoUrl = awayTeam.getString("logo");
                        gamesList.add(new GameData(homeName, awayName, homeOddsPercentage, awayOddsPercentage, homeLogoUrl, awayLogoUrl));
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

            // helper method to convert the odds to a percentage
            private String convertOddsToPercentage(int odds) {
                double probability;

                if (odds < 0) {  // negative
                    probability = ((-1.0 * odds) / ((-1.0 * odds) + 100)) * 100;
                } else {  // positive
                    probability = (100.0 / (odds + 100)) * 100;
                }
                return String.format("%.2f%%", probability);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
