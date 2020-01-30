package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SubjectImageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_subject_image, container, false);
        ImageView imageView = rootView.findViewById(R.id.imageViewSubjectImage);
        return rootView;
    }

    public static SubjectImageFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        SubjectImageFragment fragment = new SubjectImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
