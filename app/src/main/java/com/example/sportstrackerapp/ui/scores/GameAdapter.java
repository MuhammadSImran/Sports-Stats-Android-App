package com.example.sportstrackerapp.ui.scores;

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

    public GameAdapter(List<Game> gameList) {
        this.gameList = gameList;
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

        holder.teamNames.setText(String.format("%s vs %s",
                game.getHomeTeam().getAbbrev(),
                game.getAwayTeam().getAbbrev())
        );
        holder.gameTime.setText(formatGameTime(game.getGameTime()));

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
    }

    private String formatGameTime(String utcTime) {
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat displayFormat = new SimpleDateFormat("MMM dd, yyyy @ h:mm a", Locale.getDefault());
        displayFormat.setTimeZone(TimeZone.getDefault());

        try {
            Date date = utcFormat.parse(utcTime);
            assert date != null;
            return displayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return utcTime;
        }
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView teamNames, gameTime;//, scores;
        ImageView awayTeamLogo, homeTeamLogo;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            teamNames = itemView.findViewById(R.id.teamNames);
            gameTime = itemView.findViewById(R.id.gameTime);
            //scores = itemView.findViewById(R.id.scores);
            awayTeamLogo = itemView.findViewById(R.id.awayTeamLogo);
            homeTeamLogo = itemView.findViewById(R.id.homeTeamLogo);
        }
    }
}