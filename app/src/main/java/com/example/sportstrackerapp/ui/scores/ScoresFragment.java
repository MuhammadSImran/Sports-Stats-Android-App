package com.example.sportstrackerapp.ui.scores;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstrackerapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class ScoresFragment extends Fragment {
    private ScoresViewModel scoresViewModel;
    private final List<Game> gameList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CheckBox filterFavoritesCheckbox;
    private String currentUser;
    private Set<String> favoriteTeams;
    Boolean showScores = false;
    Boolean showFavourites = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scores, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerViewScores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        scoresViewModel = new ViewModelProvider(this).get(ScoresViewModel.class);
        progressBar = rootView.findViewById(R.id.progressBar);

        Button btnPastGames = rootView.findViewById(R.id.btnPastGames);
        Button btnTodayGames = rootView.findViewById(R.id.btnTodayGames);
        Button btnFutureGames = rootView.findViewById(R.id.btnFutureGames);
        ImageButton btnDatePicker = rootView.findViewById(R.id.btnDatePicker);
        filterFavoritesCheckbox = rootView.findViewById(R.id.filter_favorites_checkbox);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        currentUser = sharedPreferences.getString("savedUsername", null);
        favoriteTeams = sharedPreferences.getStringSet("favouriteTeams_" + currentUser, new HashSet<>());

        btnPastGames.setOnClickListener(v -> {
            showScores = true;
            getGamesForPast();
            highlightSelectedButton(btnPastGames, btnTodayGames, btnFutureGames, btnDatePicker);
        });

        btnTodayGames.setOnClickListener(v -> {
            showScores = false;
            getGamesForToday();
            highlightSelectedButton(btnTodayGames, btnPastGames, btnFutureGames, btnDatePicker);
        });

        btnFutureGames.setOnClickListener(v -> {
            showScores = false;
            getGamesForFuture();
            highlightSelectedButton(btnFutureGames, btnPastGames, btnTodayGames, btnDatePicker);
        });

        btnDatePicker.setOnClickListener(v -> {
            showDatePicker();
            highlightSelectedButton(btnDatePicker, btnPastGames, btnTodayGames, btnFutureGames);
        });

        filterFavoritesCheckbox.setOnClickListener(v -> OnCheckChanged());

        getGamesForToday();

        highlightSelectedButton(btnTodayGames, btnPastGames, btnFutureGames, btnDatePicker);

        return rootView;
    }

    public void OnCheckChanged(){
        if (currentUser == null) {
            Toast.makeText(requireContext().getApplicationContext(), "You are not logged in. Please login to view favourite teams.", Toast.LENGTH_SHORT).show();
            if(filterFavoritesCheckbox.isChecked()) {
                filterFavoritesCheckbox.toggle();
            }
        } else{
            if(filterFavoritesCheckbox.isChecked()) {
                showFavourites = true;
                filterGames();
            } else{
                showFavourites = false;
                updateRecyclerView(gameList);
            }
        }
    }

    private void filterGames() {
        List<Game> filteredGames = new ArrayList<>();
        for (Game game : gameList) {
            if (favoriteTeams.contains(game.getHomeTeam().getName()) || favoriteTeams.contains(game.getAwayTeam().getName())) {
                filteredGames.add(game);
            }
        }
        updateRecyclerView(filteredGames);
    }

    private void getGamesForPast() {
        String pastGames = getPastDate();
        getGames(pastGames, false);
    }

    private void getGamesForToday() {
        String today = getCurrentDate();
        getGames(today, true);
    }

    private void getGamesForFuture() {
        String tomorrow = getTomorrowDate();
        getGames(tomorrow, false);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    showScores = isDatePast(selectedDate);
                    getGames(selectedDate, true);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setOnDateChangedListener((view, year, month, day) -> {
            String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
            if (getCurrentDate().equals(selectedDate)) {
                view.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        });

        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));

        if (getCurrentDate().equals(selectedDate)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            datePickerDialog.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        datePickerDialog.show();
    }

    private void highlightSelectedButton(View selectedButton, View... otherButtons) {
        int selectedColor = ContextCompat.getColor(requireContext(), android.R.color.holo_blue_light);
        int defaultBackgroundColor = ContextCompat.getColor(requireContext(), android.R.color.darker_gray);
        int defaultTextColor = ContextCompat.getColor(requireContext(), android.R.color.black);
        int selectedTextColor = ContextCompat.getColor(requireContext(), android.R.color.white);
        selectedButton.setBackgroundColor(selectedColor);

        if (selectedButton instanceof Button) {
            ((Button) selectedButton).setTextColor(selectedTextColor);
        }

        for (View button : otherButtons) {
            button.setBackgroundColor(defaultBackgroundColor);
            if (button instanceof Button) {
                ((Button) button).setTextColor(defaultTextColor);
            }
        }
    }

    private void getGames(String date, boolean exactDate) {
        showLoading();  // shows the progress bar
        scoresViewModel.getScheduleForDate(date).observe(getViewLifecycleOwner(), gameWeeks -> {
            hideLoading(); // hides the progress bar
            if (gameWeeks != null) {
                gameList.clear();
                for (GameWeek gameWeek : gameWeeks) {
                    for (Game game : gameWeek.getGames()) {
                        if (exactDate) {
                            if (exactDate(game, date)) {
                                gameList.add(game);
                            }
                        } else {
                            gameList.add(game);
                            if (isDatePast(date)) {
                                gameList.sort((g1, g2) -> g2.getGameTime().compareTo(g1.getGameTime()));
                            }
                        }
                    }
                }
                if (showFavourites){
                    filterGames();
                }else {
                    updateRecyclerView(gameList);
                }
            } else {
                Log.d("ScoresFragment", "No games found for date: " + date);
            }
        });
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerView(List<Game> games) {
        GameAdapter gameAdapter = new GameAdapter(games, showScores);
        recyclerView.setAdapter(gameAdapter);
        gameAdapter.notifyDataSetChanged();
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    private String getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private String getPastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private boolean isDatePast(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date selectedDate = dateFormat.parse(date);
            Date currentDate = Calendar.getInstance().getTime();
            assert selectedDate != null;
            return selectedDate.before(currentDate);

        } catch (ParseException e) {
            Log.e("ScoresFragment", "isDatePast: selectedDate error");
            return false;
        }
    }

    private boolean exactDate(Game game, String date) {
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date gameDate = utcFormat.parse(game.getGameTime());
            SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            assert gameDate != null;
            String gameDateString = localDateFormat.format(gameDate);
            return gameDateString.equals(date);

        } catch (ParseException e) {
            Log.e("ScoresFragment", "exactDate: gameDate error");
            return false;
        }
    }
}
