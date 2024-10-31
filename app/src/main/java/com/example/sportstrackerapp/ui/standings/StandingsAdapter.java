package com.example.sportstrackerapp.ui.standings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstrackerapp.R;

import java.util.ArrayList;
import java.util.List;

public class StandingsAdapter extends RecyclerView.Adapter<StandingsAdapter.ViewHolder> {  // to populate the standings data in RecyclerView
    private final List<StandingsResponse.Standing> standings;

    public StandingsAdapter(List<StandingsResponse.Standing> standings) {
        this.standings = standings != null ? standings : new ArrayList<>();
    }

    public void updateStandings(List<StandingsResponse.Standing> newStandings) {
        standings.clear();
        if (newStandings != null) {
            standings.addAll(newStandings);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_standings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StandingsResponse.Standing standing = standings.get(position);
        holder.divisionName.setText(standing.getDivisionName());

        StringBuilder standingsText = new StringBuilder();
        standingsText.append(standing.getTeamDefaultName())
                .append(" - W: ").append(standing.getWins())
                .append(" L: ").append(standing.getLosses())
                .append(" GP: ").append(standing.getGamesPlayed())
                .append(" PTS: ").append(standing.getPoints())
                .append("\n");

        holder.teamStandings.setText(standingsText.toString());
    }

    @Override
    public int getItemCount() {
        return standings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView divisionName;
        TextView teamStandings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            divisionName = itemView.findViewById(R.id.divisionName);
            teamStandings = itemView.findViewById(R.id.teamStandings);
        }
    }
}
