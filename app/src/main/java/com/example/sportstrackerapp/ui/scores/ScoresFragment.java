package com.example.sportstrackerapp.ui.scores;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ScoresFragment extends Fragment {
    private ScoresViewModel scoresViewModel;
    private final List<Game> gameList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

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

        btnPastGames.setOnClickListener(v -> {
            getGamesForPast();
            highlightSelectedButton(btnPastGames, btnTodayGames, btnFutureGames, btnDatePicker);
        });

        btnTodayGames.setOnClickListener(v -> {
            getGamesForToday();
            highlightSelectedButton(btnTodayGames, btnPastGames, btnFutureGames, btnDatePicker);
        });

        btnFutureGames.setOnClickListener(v -> {
            getGamesForFuture();
            highlightSelectedButton(btnFutureGames, btnPastGames, btnTodayGames, btnDatePicker);
        });

        btnDatePicker.setOnClickListener(v -> {
            showDatePicker();
            highlightSelectedButton(btnDatePicker, btnPastGames, btnTodayGames, btnFutureGames);
        });

        getGamesForToday();
        highlightSelectedButton(btnTodayGames, btnPastGames, btnFutureGames, btnDatePicker);

        return rootView;
    }

    private void getGamesForPast() {
        String pastGames = getPastDate();
        getGames(pastGames, true, false);
    }

    private void getGamesForToday() {
        String today = getCurrentDate();
        getGames(today, false, true);
    }

    private void getGamesForFuture() {
        String tomorrow = getTomorrowDate();
        getGames(tomorrow, false, false);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    boolean isPastDate = isDatePast(selectedDate);
                    getGames(selectedDate, isPastDate, true);
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

    private void getGames(String date, boolean showScores, boolean exactDate) {
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
                updateRecyclerView(showScores);
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
    private void updateRecyclerView(boolean showScores) {
        GameAdapter gameAdapter = new GameAdapter(gameList, showScores);
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
