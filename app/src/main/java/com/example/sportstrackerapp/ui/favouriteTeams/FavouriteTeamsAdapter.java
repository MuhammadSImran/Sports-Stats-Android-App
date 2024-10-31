package com.example.sportstrackerapp.ui.favouriteTeams.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstrackerapp.R;
import com.example.sportstrackerapp.ui.favouriteTeams.models.Team;

import java.util.List;

public class FavouriteTeamsAdapter extends RecyclerView.Adapter<FavouriteTeamsAdapter.TeamViewHolder> {

    private List<Team> teams;
    private OnTeamClickListener listener;

    public FavouriteTeamsAdapter(OnTeamClickListener listener) {
        this.listener = listener;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.teamName.setText(team.getName());
        holder.itemView.setOnClickListener(v -> listener.onTeamClick(team));
    }

    @Override
    public int getItemCount() {
        return teams == null ? 0 : teams.size();
    }

    public interface OnTeamClickListener {
        void onTeamClick(Team team);
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.team_name);
        }
    }
}
