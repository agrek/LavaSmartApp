package ca.concordia.gilgamesh.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class PostLocation {

    public String uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public String name;
    public String manager;
    public String address;
    public String latitude;
    public String longitude;
    public Map<String, Boolean> machines = new HashMap<>();
    public String qr;

    public PostLocation() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public PostLocation(String uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    public PostLocation(String uid, String author, String title, String body, String name, String manager, String address, String latitude, String longitude, String qr) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.name = name;
        this.manager = manager;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.qr = qr;
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

        result.put("name", name);
        result.put("manager", manager);
        result.put("address", address);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("machines", machines);
        result.put("qr", qr);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
