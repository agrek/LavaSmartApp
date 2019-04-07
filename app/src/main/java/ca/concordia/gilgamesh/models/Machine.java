package ca.concordia.gilgamesh.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class Machine {

    public String location;
    public String status;
    public String custom_id;
    public String name;

    public Machine() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Machine(String name, String location, String status) {
        this.location = location;
        this.status = status;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }
}
// [END blog_user_class]
