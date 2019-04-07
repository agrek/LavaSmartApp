package ca.concordia.gilgamesh.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START blog_patron_class]
@IgnoreExtraProperties
public class Patron {

    public String username;
    public String name;
    public String email;
    public Map<String, Boolean> saved_machines = new HashMap<>();
    public Map<String, Boolean> owned_machines = new HashMap<>();

    public Patron() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Patron(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Patron(String username, String email, Map<String, Boolean> saved_machines, Map<String, Boolean> owned_machines) {
        this.username = username;
        this.email = email;
        this.saved_machines = saved_machines;
        this.owned_machines = owned_machines;
    }
}
// [END blog_patron_class]
