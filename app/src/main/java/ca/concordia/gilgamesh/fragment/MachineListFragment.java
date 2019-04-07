package ca.concordia.gilgamesh.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MachineListFragment extends MachineListBaseFragment {

    private static final String TAG = "MachineListFragment";

    public MachineListFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All machines
        Query recentPostsQuery = databaseReference.child("machines").orderByChild("enabled").equalTo(true);

        return recentPostsQuery;
    }
}
