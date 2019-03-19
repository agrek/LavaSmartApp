package ca.concordia.gilgamesh.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class Location {

    public String name;
    public String manager;
    public String address;
    public String latitude;
    public String longitude;
    public Map<String, Boolean> machines = new HashMap<>();
    public String qr;

    public Location() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Location(String name, String manager, String address, String latitude, String longitude, Map<String, Boolean> machines, String qr) {
        this.name = name;
        this.manager = manager;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.machines = machines;
        this.qr = qr;
    }

    public Location(String name, String manager, String address, Map<String, Boolean> machines) {
        this.name = name;
        this.manager = manager;
        this.address = address;
        this.machines = machines;
    }
}
// [END blog_user_class]
