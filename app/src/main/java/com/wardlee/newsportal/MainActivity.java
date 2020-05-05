package com.wardlee.newsportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    // Tag for debugging
    private static final String TAG = "MainActivity";


    /**
     * OnCreate, where all the action starts
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request the latest articles from NewsAPI.org
        RecyclerView theRecyclerView = findViewById(R.id.rv_latestNews);
        ArticleLoader loader = new ArticleLoader(this, theRecyclerView);
        if(loader.isInternetAvailable(this)) {
            loader.loadArticles();
        } else {
            loader.showLoadingError(this);
        }

        // Lookup the news outlet list recyclerview in the activity layout
        RecyclerView rvOutlets = findViewById(R.id.rv_outlets);

        // Initialize items
        ArrayList<NewsOutlet> outlets = createNewsOutlets();

        // Create adapter passing in the outlets
        NewsOutletAdapter adapter = new NewsOutletAdapter(outlets, this);

        // Attach the adapter to the recyclerview to populate items
        rvOutlets.setAdapter(adapter);

        // Set layout manager to position the items
        rvOutlets.setLayoutManager(new LinearLayoutManager(this));

        // Add a basic divider between the items
        rvOutlets.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    /**
     * Method to add the news outlet list objects
     * Created a separate method to keep onCreate() tidy, and keep all information loaded about each outlet together
     *
     * @return ArrayList
     */
    protected ArrayList<NewsOutlet> createNewsOutlets() {

        // Create array lists of category links for each news outlet object
        ArrayList<CategoryLink> ABCLinks = new ArrayList<CategoryLink>();
        ABCLinks.add(new CategoryLink("Just In", "https://www.abc.net.au/news/justin/"));
        ABCLinks.add(new CategoryLink("Fact Check", "https://www.abc.net.au/news/factcheck"));
        ABCLinks.add(new CategoryLink("Science", "https://www.abc.net.au/news/science/"));
        ABCLinks.add(new CategoryLink("Health", "https://www.abc.net.au/news/health/"));
        ABCLinks.add(new CategoryLink("Arts", "https://www.abc.net.au/news/arts/"));

        ArrayList<CategoryLink> SBSLinks = new ArrayList<CategoryLink>();
        SBSLinks.add(new CategoryLink("Latest News", "https://www.sbs.com.au/news/latest"));
        SBSLinks.add(new CategoryLink("Australia", "https://www.sbs.com.au/news/topic/australia"));
        SBSLinks.add(new CategoryLink("World", "https://www.sbs.com.au/news/topic/world"));

        ArrayList<CategoryLink> AgeLinks = new ArrayList<CategoryLink>();
        AgeLinks.add(new CategoryLink("Top Stories", "https://www.theage.com.au/"));
        AgeLinks.add(new CategoryLink("Politics", "https://www.theage.com.au/politics"));
        AgeLinks.add(new CategoryLink("Business", "https://www.theage.com.au/business"));
        AgeLinks.add(new CategoryLink("World", "https://www.theage.com.au/world"));
        AgeLinks.add(new CategoryLink("Lifestyle", "https://www.theage.com.au/lifestyle"));
        AgeLinks.add(new CategoryLink("Culture", "https://www.theage.com.au/culture"));

        ArrayList<CategoryLink> SMHLinks = new ArrayList<CategoryLink>();
        SMHLinks.add(new CategoryLink("Top Stories", "https://www.smh.com.au/"));
        SMHLinks.add(new CategoryLink("Politics", "https://www.smh.com.au/politics"));
        SMHLinks.add(new CategoryLink("Business", "https://www.smh.com.au/business"));
        SMHLinks.add(new CategoryLink("World", "https://www.smh.com.au/world"));
        SMHLinks.add(new CategoryLink("Life", "https://www.smh.com.au/lifestyle"));
        SMHLinks.add(new CategoryLink("Culture", "https://www.smh.com.au/culture"));

        ArrayList<CategoryLink> ConvoLinks = new ArrayList<CategoryLink>();
        ConvoLinks.add(new CategoryLink("Arts + Culture", "https://theconversation.com/au/arts"));
        ConvoLinks.add(new CategoryLink("Business + Economy", "https://theconversation.com/au/business"));
        ConvoLinks.add(new CategoryLink("Cities", "https://theconversation.com/au/cities"));
        ConvoLinks.add(new CategoryLink("Education", "https://theconversation.com/au/education"));
        ConvoLinks.add(new CategoryLink("Environment + Energy", "https://theconversation.com/au/environment"));
        ConvoLinks.add(new CategoryLink("Health + Medicine", "https://theconversation.com/au/health"));
        ConvoLinks.add(new CategoryLink("Science + Technology", "https://theconversation.com/au/technology"));

        ArrayList<CategoryLink> GuardianLinks = new ArrayList<CategoryLink>();
        GuardianLinks.add(new CategoryLink("Latest News", "https://www.theguardian.com/au"));
        GuardianLinks.add(new CategoryLink("Opinion", "https://www.theguardian.com/au/commentisfree"));
        GuardianLinks.add(new CategoryLink("Culture", "https://www.theguardian.com/au/culture"));
        GuardianLinks.add(new CategoryLink("Lifestyle", "https://www.theguardian.com/au/lifeandstyle"));

        ArrayList<CategoryLink> SaturdayLinks = new ArrayList<CategoryLink>();
        SaturdayLinks.add(new CategoryLink("Latest News", "https://www.thesaturdaypaper.com.au/news"));
        SaturdayLinks.add(new CategoryLink("Opinion", "https://www.thesaturdaypaper.com.au/opinion"));
        SaturdayLinks.add(new CategoryLink("Culture", "https://www.thesaturdaypaper.com.au/culture"));
        SaturdayLinks.add(new CategoryLink("Life", "https://www.thesaturdaypaper.com.au/life"));
        SaturdayLinks.add(new CategoryLink("Food", "https://www.thesaturdaypaper.com.au/food"));

        // Create the array list of the actual news outlet objects
        ArrayList<NewsOutlet> outlets = new ArrayList<>();
        outlets.add(new NewsOutlet("ABC", "abc", "#FFFFFF", ABCLinks));
        outlets.add(new NewsOutlet("SBS", "sbs", "#FFFFFF", SBSLinks));
        outlets.add(new NewsOutlet("The Age", "age", "#0a1633", AgeLinks));
        outlets.add(new NewsOutlet("The Sydney Morning Herald", "smh", "#0a1633", SMHLinks));
        outlets.add(new NewsOutlet("The Conversation", "conversation", "#FFFFFF", ConvoLinks));
        outlets.add(new NewsOutlet("The Guardian", "guardian", "#FFFFFF", GuardianLinks));
        outlets.add(new NewsOutlet("The Saturday Paper", "saturday", "#FFFFFF", SaturdayLinks));


        // Return the array list of complete objects, to be passed to an adapter to show them
        return outlets;
    }
}