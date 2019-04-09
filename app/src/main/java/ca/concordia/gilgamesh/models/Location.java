package ca.concordia.gilgamesh.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class Location {

    @Exclude
    public String key;
    public String name;
    public String manager_id;
    public Map<String, Boolean> machines = new HashMap<>();

    public Location() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public Location(String name, String manager_id, Map<String, Boolean> machines) {
        this.name = name;
        this.manager_id = manager_id;
        this.machines = machines;
    }
}
// [END blog_user_class]
