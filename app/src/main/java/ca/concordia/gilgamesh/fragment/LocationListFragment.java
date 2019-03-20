package ca.concordia.gilgamesh.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class LocationListFragment extends LocationListBaseFragment {

    private static final String TAG = "LocationListFragment";

    public LocationListFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All locations
        Query recentPostsQuery = databaseReference.child("locations");

        return recentPostsQuery;
    }
}
