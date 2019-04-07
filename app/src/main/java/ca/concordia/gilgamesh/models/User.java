package ca.concordia.gilgamesh.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String default_location;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, String default_location) {
        this.username = username;
        this.email = email;
        this.default_location = default_location;
    }

}
// [END blog_user_class]
