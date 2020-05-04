package com.wardlee.newsportal;

public class Article {
    private String Title;
    private String Url;
    private String ImageURL;

    // Constructor
    public Article(String title, String url, String imageUrl) {
        Title = title;
        Url = url;
        ImageURL = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public String getURL() {
        return Url;
    }

    public String getImageUrl() {
        return ImageURL;
    }
}