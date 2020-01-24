package com.android.parasitologymobilesoftware;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeTabsAdapter extends FragmentPagerAdapter {
    public HomeTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new UserFragment();
        } else if (position == 1) {
            return new HomeFragment();
        }
        return new StudentPreferencesFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
