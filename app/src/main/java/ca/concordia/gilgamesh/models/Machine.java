package ca.concordia.gilgamesh.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class Machine {

    public String status;
    public int starttime;
    public int stoptime;
    public String qr;

    public Machine() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Machine(String status, int starttime, int stoptime, String qr) {
        this.status = status;
        this.starttime = starttime;
        this.stoptime = stoptime;
        this.qr = qr;
    }

    public Machine(String status, String qr) {
        this.status = status;
        this.qr = qr;
    }
}
// [END blog_user_class]
