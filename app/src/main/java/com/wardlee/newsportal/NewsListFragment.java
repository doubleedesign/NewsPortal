package com.wardlee.newsportal;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class NewsListFragment extends Fragment {
    // Tag for debugging
    private static final String TAG = "NewsListFragment";

    // Member variables
    private NewsOutlet Outlet; // Outlet detail object

    // Constructor
    public NewsListFragment(NewsOutlet outlet) {
        Outlet = outlet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Define the layout file for the fragment
        return inflater.inflate(R.layout.fragment_newslist, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Find the fragment placeholder and bring it to the front
        FrameLayout thisFragment = getActivity().findViewById(R.id.fragment_placeholder);
        thisFragment.bringToFront();

        // Set the logo image and background colour
        LinearLayout logoWrapper = view.findViewById(R.id.linearLayout_logoWrapper);
        logoWrapper.setBackgroundColor(Color.parseColor(Outlet.getBrandColor()));
        ImageView logo = view.findViewById(R.id.imageView_logo);
        Outlet.setLogoImage(logo);

        // Get the links
        ArrayList<CategoryLink> links = Outlet.getCategoryURLs();

        // Look up the recyclerview in the activity layout
        RecyclerView rvLinks = getActivity().findViewById(R.id.rv_links);

        // Create adapter passing in the list of categories
        CategoryLinkAdapter adapter = new CategoryLinkAdapter(links, getContext());

        // Attach the adapter to the recyclerview to populate items
        rvLinks.setAdapter(adapter);

        // Set layout manager to position the items
        rvLinks.setLayoutManager(new LinearLayoutManager(getContext()));

        // Add a basic divider between the items
        rvLinks.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Add the latest news article from NewsAPI.org
        RecyclerView theRecyclerView = view.findViewById(R.id.rv_latestNews);
        ArticleLoader loader = new ArticleLoader(getContext(), theRecyclerView, "abc-news-au");
        if(loader.isInternetAvailable(getContext())) {
            loader.loadArticles();
        } else {
            loader.showLoadingError(getContext());
        }

    }
}
