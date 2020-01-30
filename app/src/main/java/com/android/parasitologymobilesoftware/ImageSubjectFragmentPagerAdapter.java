package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ImageSubjectFragmentPagerAdapter extends FragmentPagerAdapter {
    public ImageSubjectFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("subject", 1); // @Todo ADD SHARED PREFERENCES TO DETECT WHICH SUBJECT IS THE FRAGMENT IN!!!
        SubjectImageFragment subjectImageFragment = SubjectImageFragment.newInstance(position);
        subjectImageFragment.setArguments(args);
        return subjectImageFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
