package ca.concordia.gilgamesh.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class LocationListFragment extends LocationListBaseFragment {

    public LocationListFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts")
                .child(getUid());
    }
}
