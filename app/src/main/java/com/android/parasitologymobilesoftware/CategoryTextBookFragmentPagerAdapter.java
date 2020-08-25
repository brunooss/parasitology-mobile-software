package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryTextBookFragmentPagerAdapter extends FragmentPagerAdapter {

    public int count;

    public CategoryTextBookFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        count = CategoryTextBookActivity.getNumberOfTabs() != 0? CategoryTextBookActivity.getNumberOfTabs(): 2;

        Log.e("TAG", String.valueOf(count));
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("subject", 1); // @Todo ADD SHARED PREFERENCES TO DETECT WHICH SUBJECT IS THE FRAGMENT IN!!!

        CategoryTextBookFragment categoryTextBookFragment = CategoryTextBookFragment.newInstance(position);
        return categoryTextBookFragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
