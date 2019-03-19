package ca.concordia.gilgamesh.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public Map<String, Boolean> savedmachines = new HashMap<>();
    public Map<String, Boolean> ownedmachines = new HashMap<>();

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, Map<String, Boolean> savedmachines, Map<String, Boolean> ownedmachines) {
        this.username = username;
        this.email = email;
        this.savedmachines = savedmachines;
        this.ownedmachines = ownedmachines;
    }
}
// [END blog_user_class]
