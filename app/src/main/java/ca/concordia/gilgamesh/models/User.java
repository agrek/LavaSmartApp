package ca.concordia.gilgamesh.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String custom_location;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


    public User(String username, String email, String custom_location) {
        this.username = username;
        this.email = email;
        this.custom_location = custom_location;
    }
}
// [END blog_user_class]
