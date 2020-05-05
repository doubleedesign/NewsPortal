package com.wardlee.newsportal;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;


public class ArticleLoader {
    // Tag for debugging
    private static final String TAG = "ArticleLoader";

    // Variables to be passed in from the activity or fragment calling the ArticleLoader
    private Context thisContext;
    private RecyclerView thisRecyclerView;
    private String sourceParam;
    private int backgroundColor;

    // List to store articles from the API
    private ArrayList<Article> articleList = new ArrayList<Article>();

    // How many articles to show
    private int articleQty = 1;


    /**
     * Constructor
     */
    public ArticleLoader(Context context, RecyclerView recyclerView, String source, int bg) {
        thisContext = context;
        thisRecyclerView = recyclerView;
        sourceParam = source;
        backgroundColor = bg;
    }


    /**
     * Utility function to check if the internet is available before trying to fetch live news
     * @link https://stackoverflow.com/questions/9570237/android-check-internet-connection/33764301
     *
     * @return bool
     */
    public boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    /**
     * Method to show an error dialog if there's no internet connection
     */
    protected void showLoadingError(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Network error");
        alertDialog.setMessage("Please check your network connection so you can see the latest news from NewsAPI.org and access links in this application.");
        alertDialog.show();
    }


    /**
     * Utility method to get the text colour (black or white) based on background colour,
     * ready to send to the adapter
     * @link https://stackoverflow.com/questions/4672271/reverse-opposing-colors
     */
    public int getContrastColor(int colorToConvert) {
        return colorToConvert ^ 0x00ffffff;
    }


    /**
     * Method to load articles from NewsAPI.org using Volley
     * Based on tutorial:
     * @author Belal Khan
     * @link https://www.simplifiedcoding.net/android-volley-tutorial-fetch-json/
     */
    protected void loadArticles() {

        // Build API URL to get articles from
        String JSON_URL;
        // If source parameter is all, use the top headlines in Australia endpoint
        if(sourceParam == "all") {
            JSON_URL = "https://newsapi.org/v2/top-headlines?country=au&apiKey=aee4fbcb084948f185beee555aabaf62";
        }
        // Check for null source parameter, if it's not null then build the string
        // If it is null, hide the article recyclerview in this fragment and exit out of this method so no API request is attempted
        else {
            if(sourceParam != null) {
                JSON_URL = "https://newsapi.org/v2/top-headlines?sources=" + sourceParam + "&apiKey=aee4fbcb084948f185beee555aabaf62";
            } else {
                thisRecyclerView.setVisibility(View.GONE);
                return;
            }
        }

        // Create the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    // Get the JSON object from the response
                    JSONObject obj = new JSONObject(response);

                    // Get the articles array from the JSON object
                    JSONArray articles = obj.getJSONArray("articles");

                    // Loop through the array
                    for (int i = 0; i < articleQty; i++) {

                        // Get the JSON object of this article
                        JSONObject articleObject = articles.getJSONObject(i);

                        // Get the data we want from the JSON object
                        String title = articleObject.getString("title");
                        String url = URI.create(articleObject.getString("url")).toString();
                        String imageUrl = URI.create(articleObject.getString("urlToImage")).toString();

                        // Create an Article object (our article class object, not the JSON object)
                        Article thisArticle = new Article(title, url, imageUrl);

                        // Add this article to the list that the RecyclerView will use
                        articleList.add(thisArticle);
                    }

                    // Lookup the recyclerview in the activity layout
                    RecyclerView rvArticles = thisRecyclerView;

                    // Get the colours
                    backgroundColor = backgroundColor;
                    int textColor = getContrastColor(backgroundColor);

                    // Create adapter passing in the outlets and colours
                    ArticleAdapter articleAdapter = new ArticleAdapter(articleList, thisContext, backgroundColor, textColor);

                    // Attach the adapter to the recyclerview to populate items
                    rvArticles.setAdapter(articleAdapter);

                    // Set layout manager to position the items
                    rvArticles.setLayoutManager(new LinearLayoutManager(thisContext));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Display error in toast
                Toast.makeText(thisContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Create the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);

        // Add the string request to request queue
        requestQueue.add(stringRequest);
    }
}