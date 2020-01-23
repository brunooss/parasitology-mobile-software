package com.android.parasitologymobilesoftware;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class IntroductionTabsAdapter extends FragmentPagerAdapter {
    public IntroductionTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = IntroductionFragment.newInstance(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
