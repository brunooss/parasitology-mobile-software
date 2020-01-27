package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        View categoryArtropodes = rootView.findViewById(R.id.categoryHomeFragmentArtropodes);
        ImageView artropodesIcon = categoryArtropodes.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodesIcon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodesTitle = rootView.findViewById(R.id.textViewCategoryArtropodes);
        artropodesTitle.setText("Artrópodes".toUpperCase());

        View subCategoryArtropodes1 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes1);
        ImageView artropodes1Icon = subCategoryArtropodes1.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes1Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes1Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes1);
        artropodes1Title.setText("Mosquitos".toUpperCase());

        View subCategoryArtropodes2 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes2);
        ImageView artropodes2Icon = subCategoryArtropodes2.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes2Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes2Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes2);
        artropodes2Title.setText("Besouros".toUpperCase());

        View subCategoryArtropodes3 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes3);
        ImageView artropodes3Icon = subCategoryArtropodes3.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes3Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes3Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes3);
        artropodes3Title.setText("Moscas".toUpperCase());

        // Inflate the layout for this fragment
        return rootView;
    }
}
