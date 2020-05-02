package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {

    public int count;

    public CategoryFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        count = CategoryActivity.getNumberOfTabs() != 0? CategoryActivity.getNumberOfTabs(): 2;

        Log.e("TAG", String.valueOf(count));
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("subject", 1); // @Todo ADD SHARED PREFERENCES TO DETECT WHICH SUBJECT IS THE FRAGMENT IN!!!

        CategoryFragment categoryFragment = CategoryFragment.newInstance(position);
        return categoryFragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
