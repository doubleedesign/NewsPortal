package com.wardlee.newsportal;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import java.util.ArrayList;

public class NewsOutlet {
    private String Name;
    private String LogoName;
    private String BrandColor;
    private String SecondaryColor;
    private String NewsAPIParam;
    private ArrayList<CategoryLink> CategoryURLs;

    // The constructor
    protected NewsOutlet(String name, String logoName, String color, String secondaryColor, String apiParam, ArrayList<CategoryLink> urls) {
        Name = name;
        LogoName = logoName;
        BrandColor = color;
        SecondaryColor = secondaryColor;
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

    // Methods to get the brand colours
    public String getBrandColor() {
        return BrandColor;
    }
    public String getSecondaryColor() {
        return SecondaryColor;
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