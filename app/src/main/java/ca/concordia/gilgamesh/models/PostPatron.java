package ca.concordia.gilgamesh.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class PostPatron {

    public String uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public String username;
    public String name;
    public String email;
    public Map<String, Boolean> saved_machines = new HashMap<>();
    public Map<String, Boolean> owned_machines = new HashMap<>();

    public PostPatron() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public PostPatron(String uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    public PostPatron(String uid, String author, String title, String body, String username, String name, String email, Map<String, Boolean> saved_machines, Map<String, Boolean> owned_machines) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.username = username;
        this.name = name;
        this.email = email;
        this.saved_machines = saved_machines;
        this.owned_machines = owned_machines;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        result.put("username", username);
        result.put("name", name);
        result.put("email", email);
        result.put("saved_machines", saved_machines);
        result.put("owned_machines", owned_machines);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
