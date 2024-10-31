package com.example.sportstrackerapp.ui.standings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StandingsPagerAdapter extends FragmentStateAdapter {  //  handle two fragments, 1 for each conference (eastern/western)
    public StandingsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new EasternConferenceFragment();
        } else {
            return new WesternConferenceFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
