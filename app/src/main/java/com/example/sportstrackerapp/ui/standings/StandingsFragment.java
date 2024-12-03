package com.example.sportstrackerapp.ui.standings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sportstrackerapp.R;
import com.example.sportstrackerapp.ui.news.ArticleAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class StandingsFragment extends Fragment {
    private StandingsPagerAdapter standingPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standings, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        standingPagerAdapter = new StandingsPagerAdapter(this);
        viewPager.setAdapter(standingPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(getString(R.string.eastern_conference));
            } else {
                tab.setText(getString(R.string.western_conference));
            }
        }).attach();

        return view;
    }
}