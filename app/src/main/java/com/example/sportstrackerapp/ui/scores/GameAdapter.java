package com.example.sportstrackerapp.ui.scores;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportstrackerapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private final List<Game> gameList;
    private final boolean showScores;

    public GameAdapter(List<Game> gameList, boolean showScores) {
        this.gameList = gameList;
        this.showScores = showScores;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scores, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(game.getHomeTeam().getLogo())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(holder.homeTeamLogo);

        Glide.with(holder.itemView.getContext())
                .load(game.getAwayTeam().getLogo())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(holder.awayTeamLogo);

        if (showScores) {
            holder.homeTeamScore.setVisibility(View.VISIBLE);
            holder.awayTeamScore.setVisibility(View.VISIBLE);

            holder.homeTeamScore.setText(String.valueOf(game.getHomeTeam().getScore()));
            holder.awayTeamScore.setText(String.valueOf(game.getAwayTeam().getScore()));
        } else {
            holder.homeTeamScore.setVisibility(View.GONE);
            holder.awayTeamScore.setVisibility(View.GONE);
        }

        String[] formattedDateTime = formatGameDateTime(game.getGameTime());
        holder.gameDate.setText(formattedDateTime[0]); // Date
        holder.gameTime.setText(formattedDateTime[1]); // Time
    }

    private String[] formatGameDateTime(String utcTime) {
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("@ h:mm a", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        timeFormat.setTimeZone(TimeZone.getDefault());

        try {
            Date date = utcFormat.parse(utcTime);
            assert date != null;
            return new String[]{dateFormat.format(date), timeFormat.format(date)};
        } catch (ParseException e) {
            Log.e("GameAdapter", "Error with date");
            return new String[]{"Invalid Date", "Invalid Time"};
        }
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView homeTeamLogo, awayTeamLogo;
        TextView homeTeamScore, awayTeamScore, gameDate, gameTime;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);

            homeTeamLogo = itemView.findViewById(R.id.homeTeamLogo);
            awayTeamLogo = itemView.findViewById(R.id.awayTeamLogo);
            homeTeamScore = itemView.findViewById(R.id.homeTeamScore);
            awayTeamScore = itemView.findViewById(R.id.awayTeamScore);

            gameDate = itemView.findViewById(R.id.gameDate);
            gameTime = itemView.findViewById(R.id.gameTime);
        }
    }
}
