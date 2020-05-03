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
    NewsOutlet Outlet;

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

        LinearLayout logoWrapper = view.findViewById(R.id.linearLayout_logoWrapper);
        logoWrapper.setBackgroundColor(Color.parseColor(Outlet.getBrandColor()));

        // Set the logo image
        ImageView logo = view.findViewById(R.id.imageView_logo);
        Outlet.setLogoImage(logo);

        // Set the heading
        TextView heading = view.findViewById(R.id.textView_heading);
        heading.setText(Outlet.getName());

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
    }
}