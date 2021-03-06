package com.gwijiapp.leaderboard.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gwijiapp.leaderboard.fragments.HourFragment;
import com.gwijiapp.leaderboard.fragments.SkillIQFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm,int numberOfTabs) {
        super(fm);
        this.numberOfTabs=numberOfTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new HourFragment();
            case 1:
                return new SkillIQFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
