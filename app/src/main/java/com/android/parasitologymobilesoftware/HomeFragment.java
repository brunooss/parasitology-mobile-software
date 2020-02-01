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

        HomeActivity activity = new HomeActivity();
        String text[][] = activity.searchSubcategories;


        View categoryProtozoarios = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios);
        ImageView protozoariosIcon = categoryProtozoarios.findViewById(R.id.imageViewCategoryBackgroundIcon);
        protozoariosIcon.setImageResource(R.drawable.icons8_bug_64);
        TextView protozoariosTitle = rootView.findViewById(R.id.textViewCategoryProtozoarios);
        protozoariosTitle.setText(text[0][0].toUpperCase());

        View subCategoryProtozoarios1 = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios1);
        ImageView protozoarios1Icon = subCategoryProtozoarios1.findViewById(R.id.imageViewCategoryBackgroundIcon);
        protozoarios1Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView protozoarios1Title = rootView.findViewById(R.id.textViewSubcategoryProtozoarios1);
        protozoarios1Title.setText(text[0][1].toUpperCase());

        View subCategoryProtozoarios2 = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios2);
        ImageView protozoarios2Icon = subCategoryProtozoarios2.findViewById(R.id.imageViewCategoryBackgroundIcon);
        protozoarios2Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView protozoarios2Title = rootView.findViewById(R.id.textViewSubcategoryProtozoarios2);
        protozoarios2Title.setText(text[0][2].toUpperCase());

        View subCategoryProtozoarios3 = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios3);
        ImageView protozoarios3Icon = subCategoryProtozoarios3.findViewById(R.id.imageViewCategoryBackgroundIcon);
        protozoarios3Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView protozoarios3Title = rootView.findViewById(R.id.textViewSubcategoryProtozoarios3);
        protozoarios3Title.setText(text[0][3].toUpperCase());

        View subCategoryProtozoarios4 = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios4);
        ImageView protozoarios4Icon = subCategoryProtozoarios4.findViewById(R.id.imageViewCategoryBackgroundIcon);
        protozoarios4Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView protozoarios4Title = rootView.findViewById(R.id.textViewSubcategoryProtozoarios4);
        protozoarios4Title.setText(text[0][4].toUpperCase());

        View subCategoryProtozoarios5 = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios5);
        ImageView protozoarios5Icon = subCategoryProtozoarios5.findViewById(R.id.imageViewCategoryBackgroundIcon);
        protozoarios5Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView protozoarios5Title = rootView.findViewById(R.id.textViewSubcategoryProtozoarios5);
        protozoarios5Title.setText(text[0][5].toUpperCase());

        View subCategoryProtozoarios6 = rootView.findViewById(R.id.categoryHomeFragmentProtozoarios6);
        ImageView protozoarios6Icon = subCategoryProtozoarios6.findViewById(R.id.imageViewCategoryBackgroundIcon);
        protozoarios6Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView protozoarios6Title = rootView.findViewById(R.id.textViewSubcategoryProtozoarios6);
        protozoarios6Title.setText(text[0][6].toUpperCase());





        View categoryHelmintos = rootView.findViewById(R.id.categoryHomeFragmentHelmintos);
        ImageView helmintosIcon = categoryHelmintos.findViewById(R.id.imageViewCategoryBackgroundIcon);
        helmintosIcon.setImageResource(R.drawable.icons8_bug_64);
        TextView helmintosTitle = rootView.findViewById(R.id.textViewCategoryHelmintos);
        helmintosTitle.setText(text[1][0].toUpperCase());

        View subCategoryHelmintos1 = rootView.findViewById(R.id.categoryHomeFragmentHelmintos1);
        ImageView helmintos1Icon = subCategoryHelmintos1.findViewById(R.id.imageViewCategoryBackgroundIcon);
        helmintos1Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView helmintos1Title = rootView.findViewById(R.id.textViewSubcategoryHelmintos1);
        helmintos1Title.setText(text[1][1].toUpperCase());

        View subCategoryHelmintos2 = rootView.findViewById(R.id.categoryHomeFragmentHelmintos2);
        ImageView helmintos2Icon = subCategoryHelmintos2.findViewById(R.id.imageViewCategoryBackgroundIcon);
        helmintos2Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView helmintos2Title = rootView.findViewById(R.id.textViewSubcategoryHelmintos2);
        helmintos2Title.setText(text[1][2].toUpperCase());

        View subCategoryHelmintos3 = rootView.findViewById(R.id.categoryHomeFragmentHelmintos3);
        ImageView helmintos3Icon = subCategoryHelmintos3.findViewById(R.id.imageViewCategoryBackgroundIcon);
        helmintos3Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView helmintos3Title = rootView.findViewById(R.id.textViewSubcategoryHelmintos3);
        helmintos3Title.setText(text[1][3].toUpperCase());

        View subCategoryHelmintos4 = rootView.findViewById(R.id.categoryHomeFragmentHelmintos4);
        ImageView helmintos4Icon = subCategoryHelmintos4.findViewById(R.id.imageViewCategoryBackgroundIcon);
        helmintos4Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView helmintos4Title = rootView.findViewById(R.id.textViewSubcategoryHelmintos4);
        helmintos4Title.setText(text[1][4].toUpperCase());

        View subCategoryHelmintos5 = rootView.findViewById(R.id.categoryHomeFragmentHelmintos5);
        ImageView helmintos5Icon = subCategoryHelmintos5.findViewById(R.id.imageViewCategoryBackgroundIcon);
        helmintos5Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView helmintos5Title = rootView.findViewById(R.id.textViewSubcategoryHelmintos5);
        helmintos5Title.setText(text[1][5].toUpperCase());

        View subCategoryHelmintos6 = rootView.findViewById(R.id.categoryHomeFragmentHelmintos6);
        ImageView helmintos6Icon = subCategoryHelmintos6.findViewById(R.id.imageViewCategoryBackgroundIcon);
        helmintos6Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView helmintos6Title = rootView.findViewById(R.id.textViewSubcategoryHelmintos6);
        helmintos6Title.setText(text[1][6].toUpperCase());





        View categoryArtropodes = rootView.findViewById(R.id.categoryHomeFragmentArtropodes);
        ImageView artropodesIcon = categoryArtropodes.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodesIcon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodesTitle = rootView.findViewById(R.id.textViewCategoryArtropodes);
        artropodesTitle.setText(text[2][0].toUpperCase());

        View subCategoryArtropodes1 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes1);
        ImageView artropodes1Icon = subCategoryArtropodes1.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes1Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes1Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes1);
        artropodes1Title.setText(text[2][1].toUpperCase());

        View subCategoryArtropodes2 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes2);
        ImageView artropodes2Icon = subCategoryArtropodes2.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes2Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes2Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes2);
        artropodes2Title.setText(text[2][2].toUpperCase());

        View subCategoryArtropodes3 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes3);
        ImageView artropodes3Icon = subCategoryArtropodes3.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes3Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes3Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes3);
        artropodes3Title.setText(text[2][3].toUpperCase());

        View subCategoryArtropodes4 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes4);
        ImageView artropodes4Icon = subCategoryArtropodes4.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes4Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes4Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes4);
        artropodes4Title.setText(text[2][4].toUpperCase());

        View subCategoryArtropodes5 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes5);
        ImageView artropodes5Icon = subCategoryArtropodes5.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes5Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes5Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes5);
        artropodes5Title.setText(text[2][5].toUpperCase());

        View subCategoryArtropodes6 = rootView.findViewById(R.id.categoryHomeFragmentArtropodes6);
        ImageView artropodes6Icon = subCategoryArtropodes6.findViewById(R.id.imageViewCategoryBackgroundIcon);
        artropodes6Icon.setImageResource(R.drawable.icons8_bug_64);
        TextView artropodes6Title = rootView.findViewById(R.id.textViewSubcategoryArtropodes6);
        artropodes6Title.setText(text[2][6].toUpperCase());

        // Inflate the layout for this fragment
        return rootView;
    }
}
