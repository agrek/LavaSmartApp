package ca.concordia.gilgamesh.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class PostMachine {

    public String uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public String id;
    public String location;
    public String status;
    public String start_time;
    public String stop_time;
    public String qr;

    public PostMachine() {
        // Default constructor required for calls to DataSnapshot.getValue(PostMachine.class)
    }

    public PostMachine(String uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    public PostMachine(String uid, String author, String title, String body, String location, String id, String status, String qr) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.location = location;
        this.id = id;
        this.status = status;
        this.qr = qr;
    }

    public PostMachine(String uid, String author, String title, String body, String location, String id, String status, String qr, String start_time, String stop_time) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.location = location;
        this.id = id;
        this.status = status;
        this.qr = qr;
        this.start_time = start_time;
        this.stop_time = stop_time;
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

        result.put("id", id);
        result.put("location", location);
        result.put("status", status);
        result.put("start_time", start_time);
        result.put("stop_time", stop_time);
        result.put("qr", qr);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
