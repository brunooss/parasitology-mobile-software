package com.android.parasitologymobilesoftware;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.MyApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class CategoryFragment extends Fragment {

    static InputStream inputStream;
    public static String category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        TextView textViewTitle = rootView.findViewById(R.id.textViewCategoryTitle);
        WebView webViewText = rootView.findViewById(R.id.webViewCategoryText);
        ImageView imageView = rootView.findViewById(R.id.imageViewCategoryImage);

        if (getArguments().getString("type") != null) {
            if(getArguments().getString("type").equals("textAndImage")) {
                textViewTitle.setText(Html.fromHtml(this.getArguments().getString("title"), Html.FROM_HTML_MODE_COMPACT));
                webViewText.loadData("<body><style>* {text-align: justify; }</style>" + this.getArguments().getString("text") + "</body>", "text/html", "UTF-8");
                imageView.setImageResource(getResources().getIdentifier(getArguments().getString("imageAddress"), "drawable", getContext().getPackageName()));

            } else if (getArguments().getString("type").equals("text")) {
                textViewTitle.setText(Html.fromHtml(this.getArguments().getString("title"), Html.FROM_HTML_MODE_COMPACT));
                webViewText.loadData("<body><style> @font-face { font-family: Futura; src: url(\"file:///android_asset/font/futura_light_bt.ttf)\"} * {text-align: justify; font-family: Futura; }</style>" + this.getArguments().getString("text") + "</body>", "text/html", "UTF-8");
                imageView.setVisibility(ImageView.INVISIBLE);
            }
        }

        return rootView;
    }

    public static CategoryFragment newInstance(int position) {
        Bundle args = new Bundle();
        HomeActivity activity = new HomeActivity();

        category = activity.getCategory();
        Log.d("CategoryFragment", "Category's name received: " +category);


        try {
            inputStream = MyApplication.getMyApplicationContext().getAssets().open("fragments_settings.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String JSON = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(JSON);


            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if(jsonObject.getString("categoryName").equals(category) && !jsonObject.get("numberOfTabs").equals(position)) {
                    JSONObject tab = jsonObject.getJSONArray("tabs").getJSONObject(position);
                    args.putString("title", tab.getString("title"));
                    args.putString("type", tab.getString("type"));
                    args.putString("text", tab.getString("text"));

                    try {
                        tab.get("imageAddress");
                        args.putString("imageAddress", tab.getString("imageAddress"));
                    } catch (JSONException ignored) { }
                    Toast.makeText(MyApplication.getMyApplicationContext(), activity.category, Toast.LENGTH_LONG).show();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getMyApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        args.putInt("position", position);

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
