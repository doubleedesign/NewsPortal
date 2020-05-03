package com.wardlee.newsportal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CategoryLinkAdapter extends RecyclerView.Adapter<CategoryLinkAdapter.ViewHolder> {
    private static final String TAG = "CategoryLinkAdapter";

    // Member variable for the link list
    private List<CategoryLink> LinkList;

    // Constructor
    public CategoryLinkAdapter(ArrayList<CategoryLink> links, Context context) {
        LinkList = links;
    }

    // Inner class for the viewholder for each item
    protected class ViewHolder extends RecyclerView.ViewHolder {

        // Variables for the data about each item
        public TextView LinkLabel;
        public TextView LinkURL;

        // The constructor for the viewholder
        public ViewHolder(final View itemView) {
            super(itemView);

            // Assign the values from the interface elements
            LinkLabel = itemView.findViewById(R.id.textView_Label);
            LinkURL = itemView.findViewById(R.id.textView_URL);

            // Add click listener to each item
            LinkLabel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // Parse the URL to a string
                    String url = LinkURL.getText().toString();

                    // Create URL intent - opens browser to that URL on click
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    itemView.getContext().startActivity(urlIntent);
                }
            });
        }
    }

    // Adapter methods
    @Override
    public CategoryLinkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View LinkView = inflater.inflate(R.layout.item_categorylink, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(LinkView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryLinkAdapter.ViewHolder viewHolder, int position) {
        View view = viewHolder.itemView;

        // Get the data for this item based on position
        CategoryLink link = LinkList.get(position);

        // Get layout elements for this item
        TextView label = view.findViewById(R.id.textView_Label);
        TextView url = view.findViewById(R.id.textView_URL);

        // Set the label text
        label.setText(link.getLabel());

        // Set the URL value
        url.setText(link.getUrl());
    }

    @Override
    public int getItemCount() {
        return LinkList.size();
    }
}