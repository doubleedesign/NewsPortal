package com.wardlee.newsportal;

import android.content.Context;
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


    private Context thisContext;
    private RecyclerView thisRecyclerView;


    // API URL to get articles from
    private static final String JSON_URL = "https://newsapi.org/v2/top-headlines?country=au&apiKey=aee4fbcb084948f185beee555aabaf62";

    // List to store articles from the API
    private ArrayList<Article> articleList = new ArrayList<Article>();

    // How many articles to show
    private int articleQty = 1;

    /**
     * Constructor
     */
    public ArticleLoader(Context context, RecyclerView recyclerView) {
        thisContext = context;
        thisRecyclerView = recyclerView;
    }

    /**
     * Method to load articles from NewsAPI.org using Volley
     * Based on tutorial:
     * @author Belal Khan
     * @link https://www.simplifiedcoding.net/android-volley-tutorial-fetch-json/
     */
    protected void loadArticles() {

        // Make progress bar visible
        //final ProgressBar progressBar = thisView.findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);

        // Create the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // Hide the progress bar
                //progressBar.setVisibility(View.GONE);

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

                    // Create adapter passing in the outlets
                    ArticleAdapter articleAdapter = new ArticleAdapter(articleList, thisContext);

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