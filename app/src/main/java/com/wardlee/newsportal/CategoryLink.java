package com.wardlee.newsportal;

public class CategoryLink {
    private String Label;
    private String Url;

    // The constructor
    protected CategoryLink(String label, String url) {
        Label = label;
        Url = url;
    }

    public String getLabel() {
        return Label;
    }

    public String getUrl() {
        return Url;
    }
}