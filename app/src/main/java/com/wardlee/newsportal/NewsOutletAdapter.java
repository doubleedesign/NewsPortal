package com.wardlee.newsportal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsOutletAdapter extends RecyclerView.Adapter<NewsOutletAdapter.ViewHolder> {
    private static final String TAG = "NewsOutletAdapter";

    // Member variables for the outlet list and fragment manager
    private List<NewsOutlet> OutletList;
    private FragmentManager OutletFragmentManager;

    // Constructor
    public NewsOutletAdapter(ArrayList<NewsOutlet> outlets, FragmentActivity thisActivity) {
        OutletList = outlets;
        OutletFragmentManager = thisActivity.getSupportFragmentManager();
    }

    // Inner class for the viewholder for each item
    protected class ViewHolder extends RecyclerView.ViewHolder {

        // Variables for the data about each item
        public TextView OutletName;
        public ImageView LogoName;
        public NewsOutlet Outlet;


        // The constructor for the viewholder
        public ViewHolder(View itemView) {
            super(itemView);
            OutletName = itemView.findViewById(R.id.textView_Name);
            LogoName = itemView.findViewById(R.id.imageView_logo);

            // Add click listener to each item
            OutletName.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    // Create a new instance of a news outlet detail fragment,
                    // passing in the relevant NewsOutlet object
                    NewsListFragment OutletFragment = new NewsListFragment(Outlet);

                    // Create a fragment transaction
                    FragmentTransaction transaction = OutletFragmentManager.beginTransaction();

                    // Replace the contents of the placeholder with this fragment
                    transaction.replace(R.id.fragment_placeholder, OutletFragment);

                    // Add an animation setting so the fragment nicely fades in/out of view
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                    // Make the back button work (i.e. hide the fragment and go back to the main menu)
                    transaction.addToBackStack(null);

                    // Done
                    transaction.commit();
                }
            });
        }

        // Method to set the news outlet object after the view has been created,
        // so the whole object can be passed to the fragment
        public void setOutletObject(NewsOutlet outlet) {
            Outlet = outlet;
        }
    }
    
    // Adapter methods
    @Override
    public NewsOutletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View NewsOutletView = inflater.inflate(R.layout.item_newsoutlet, parent, false);

        // Create a new holder instance
        ViewHolder viewHolder = new ViewHolder(NewsOutletView);

        // Return the completed viewholder
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(NewsOutletAdapter.ViewHolder viewHolder, int position) {
        View view = viewHolder.itemView;

        // Get the object for this item based on position
        NewsOutlet outlet = OutletList.get(position);

        // Set the viewholder's object to that news outlet object
        viewHolder.setOutletObject(outlet);

        // Set the label text
        TextView label = view.findViewById(R.id.textView_Name);
        label.setText(outlet.getName());

        // Set the logo image
        ImageView logo = view.findViewById(R.id.imageView_logo);
        outlet.setLogoImage(logo);
    }

    @Override
    public int getItemCount() {
        return OutletList.size();
    }
}