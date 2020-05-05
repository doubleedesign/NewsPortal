package com.wardlee.newsportal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private static final String TAG = "ArticleAdapter";

    // Member variable for the link list
    private ArrayList<Article> ArticleList;

    // Variables for the colours based on the news outlet's brand colours
    private int bgColor;
    private int textColor;

    // Constructor
    public ArticleAdapter(ArrayList<Article> articles, Context context, int bg, int text) {
        ArticleList = articles;
        bgColor = bg;
        textColor = text;
    }

    // Inner class for the viewholder for each item
    protected class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ArticleWrapper;
        TextView ArticleTitle;
        TextView LinkURL;

        // The constructor for the viewholder
        public ViewHolder(final View itemView) {
            super(itemView);

            // The interface elements
            ArticleWrapper = itemView.findViewById(R.id.layout_articleItem);
            ArticleTitle = itemView.findViewById(R.id.textView_articleTitle);
            LinkURL = itemView.findViewById(R.id.textView_articleURL);

            // Set interface styling
            ArticleWrapper.setBackgroundColor(bgColor);
            ArticleTitle.setTextColor(textColor);

            // Add click listener to each item
            ArticleWrapper.setOnClickListener(new View.OnClickListener() {

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
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View ArticleView = inflater.inflate(R.layout.item_article, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(ArticleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder viewHolder, int position) {
        View view = viewHolder.itemView;

        // Get the data for this item based on position
        Article article = ArticleList.get(position);

        // Get layout elements for this item
        TextView label = view.findViewById(R.id.textView_articleTitle);
        TextView url = view.findViewById(R.id.textView_articleURL);

        // Set the label text
        label.setText(article.getTitle());

        // Set the URL value
        url.setText(article.getURL());
    }

    @Override
    public int getItemCount() {
        return ArticleList.size();
    }
}