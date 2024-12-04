package com.example.sportstrackerapp.ui.predictions;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportstrackerapp.R;

import java.util.List;

public class PredictionsAdapter extends RecyclerView.Adapter<PredictionsAdapter.ViewHolder> {

    private final List<GameData> gamesList;

    public PredictionsAdapter(List<GameData> gamesList) {
        this.gamesList = gamesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameData game = gamesList.get(position);

        holder.homeTeam.setText(game.getHomeTeam());
        holder.awayTeam.setText(game.getAwayTeam());
        holder.homeOdds.setText(game.getHomeOdds());
        holder.awayOdds.setText(game.getAwayOdds());

        // Set text color based on which team has better odds
        double homeOddsValue = Double.parseDouble(game.getHomeOdds().replace("%", "").trim());
        double awayOddsValue = Double.parseDouble(game.getAwayOdds().replace("%", "").trim());

        if (homeOddsValue > awayOddsValue) {
            holder.homeOdds.setTextColor(Color.GREEN);
            holder.awayOdds.setTextColor(Color.RED);
        } else {
            holder.homeOdds.setTextColor(Color.RED);
            holder.awayOdds.setTextColor(Color.GREEN);
        }

        // Load team logos using Glide
        Glide.with(holder.homeLogo.getContext())
                .load(game.getHomeLogoUrl())
                .into(holder.homeLogo);

        Glide.with(holder.awayLogo.getContext())
                .load(game.getAwayLogoUrl())
                .into(holder.awayLogo);
    }

    // helper method to find which team has better odds
    private String getBetterOddsTeam(String homeTeam, String awayTeam, String homeOdds, String awayOdds) {
        try {
            // Parse odds percentages to numeric values
            double homeOddsValue = Double.parseDouble(homeOdds.replace("%", "").trim());
            double awayOddsValue = Double.parseDouble(awayOdds.replace("%", "").trim());

            if (homeOddsValue > awayOddsValue) {
                return homeTeam;
            } else if (awayOddsValue > homeOddsValue) {
                return awayTeam;
            } else {
                return "Equal odds";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Error in odds";
        }
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView homeTeam, awayTeam, homeOdds, awayOdds;
        ImageView homeLogo, awayLogo;

        ViewHolder(View view) {
            super(view);
            homeTeam = view.findViewById(R.id.textHomeTeam);
            awayTeam = view.findViewById(R.id.textAwayTeam);
            homeOdds = view.findViewById(R.id.textHomeOdds);
            awayOdds = view.findViewById(R.id.textAwayOdds);
            homeLogo = view.findViewById(R.id.imageHomeTeamLogo);
            awayLogo = view.findViewById(R.id.imageAwayTeamLogo);
        }
    }
}
