package com.example.sportstrackerapp.ui.news;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// Adapter for managing News tabs (All News and Favourite Teams News)
public class NewsPagerAdapter extends FragmentStateAdapter {
    public NewsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new AllNewsFragment();
        } else {
            return new FavouritesNewsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
