package com.example.sportstrackerapp.ui.standings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportstrackerapp.R;

//import coil.load;
//import coil.decode.SvgDecoder;
//import coil.request.ImageRequest;
//import coil.request.CachePolicy;

import java.util.ArrayList;
import java.util.List;

public class StandingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {  // to populate the standings data in RecyclerView
    private static final int TYPE_DIVISION_HEADER = 0;
    private static final int TYPE_TEAM_ITEM = 1;

    private final List<Object> items; // Mixed list of String (division headers) and Standing items

    public StandingsAdapter() {
        this.items = new ArrayList<>();
    }

    public void setDivisionStandings(List<StandingsResponse.Standing> atlanticTeams, List<StandingsResponse.Standing> metropolitanTeams) {
        items.clear();
        if (atlanticTeams != null && !atlanticTeams.isEmpty()) {
            items.add("Atlantic Division"); // Header
            items.addAll(atlanticTeams); // Teams
        }
        if (metropolitanTeams != null && !metropolitanTeams.isEmpty()) {
            items.add("Metropolitan Division"); // Header
            items.addAll(metropolitanTeams); // Teams
        }
        notifyDataSetChanged();
    }

    public void setWesternDivisionStandings(List<StandingsResponse.Standing> centralTeams, List<StandingsResponse.Standing> pacificTeams) {
        items.clear();
        if (centralTeams != null && !centralTeams.isEmpty()) {
            items.add("Central Division"); // Header
            items.addAll(centralTeams); // Teams
        }
        if (pacificTeams != null && !pacificTeams.isEmpty()) {
            items.add("Pacific Division"); // Header
            items.addAll(pacificTeams); // Teams
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof String) {
            return TYPE_DIVISION_HEADER;
        } else {
            return TYPE_TEAM_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_DIVISION_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_division_header, parent, false);
            return new DivisionHeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
            return new TeamViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_DIVISION_HEADER) {
            DivisionHeaderViewHolder headerHolder = (DivisionHeaderViewHolder) holder;
            headerHolder.divisionName.setText((String) items.get(position));

        } else {
            TeamViewHolder teamHolder = (TeamViewHolder) holder;
            StandingsResponse.Standing team = (StandingsResponse.Standing) items.get(position);
            teamHolder.bind(team);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class DivisionHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView divisionName;

        public DivisionHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            divisionName = itemView.findViewById(R.id.divisionName);
        }
    }

    static class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView teamName, gamesPlayed, wins, losses, otLosses, points, streak;
//        ImageView teamLogo;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
//            teamLogo = itemView.findViewById(R.id.team_logo);
            teamName = itemView.findViewById(R.id.team_name);
            gamesPlayed = itemView.findViewById(R.id.games_played);
            wins = itemView.findViewById(R.id.wins);
            losses = itemView.findViewById(R.id.losses);
            otLosses = itemView.findViewById(R.id.ot_losses);
            points = itemView.findViewById(R.id.points);
        }

        public void bind(StandingsResponse.Standing standing) {
            teamName.setText(standing.getTeamCommonName());
            gamesPlayed.setText(String.valueOf(standing.getGamesPlayed()));
            wins.setText(String.valueOf(standing.getWins()));
            losses.setText(String.valueOf(standing.getLosses()));
            otLosses.setText(String.valueOf(standing.getOtLosses()));
            points.setText(String.valueOf(standing.getPoints()));

            // Log.d("StandingsAdapter", "Loading logo for team: " + standing.getTeamCommonName() + " URL: " + standing.getTeamLogo());
            // This loads the team logos using coil from the nhl api
//            teamLogo.load(standing.getTeamLogo());
//            decoderFactory(SvgDecoder.Factory()); // makes sure coil uses the file type .svg
//            placeholder(R.drawable.placeholder);
//            error(R.drawable.placeholder);
//            diskCachePolicy(CachePolicy.ENABLED);
        }
    }
}

