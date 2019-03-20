package ca.concordia.gilgamesh.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class PatronListFragment extends PatronListBaseFragment {

    private static final String TAG = "PatronListFragment";

    public PatronListFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All patrons
        Query recentPostsQuery = databaseReference.child("patrons");

        return recentPostsQuery;
    }
}
