package com.wardlee.newsportal;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import java.util.ArrayList;

public class NewsOutlet {
    private String Name;
    private String LogoName;
    private String BrandColor;
    private String NewsAPIParam;
    private ArrayList<CategoryLink> CategoryURLs;

    // The constructor
    protected NewsOutlet(String name, String logoName, String color, String apiParam, ArrayList<CategoryLink> urls) {
        Name = name;
        LogoName = logoName;
        BrandColor = color;
        NewsAPIParam = apiParam;
        CategoryURLs = urls;
    }

    // Method to get the name
    public String getName() {
        return Name;
    }

    // Shortcut method to find the logo's drawable and assign it to the provided ImageView
    public void setLogoImage(ImageView imageview) {
        Context context = imageview.getContext();
        int imageID = context.getResources().getIdentifier(LogoName, "drawable", context.getPackageName());
        imageview.setImageResource(imageID);
    }

    // Method to get the brand colour
    public String getBrandColor() {
        return BrandColor;
    }

    // Method to get the source parameter to be passed to a NewsAPI.org request
    public String getNewsAPIParam() {
        return NewsAPIParam;
    }

    // Method to get all the URLs to be listed in the fragment
    public ArrayList<CategoryLink> getCategoryURLs() {
        return CategoryURLs;
    }
}