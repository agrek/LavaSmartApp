package com.google.firebase.quickstart.database.java;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.quickstart.database.R;

public class WashingMachineActivity extends AppCompatActivity {

    private static final String TAG = "WashingMachineActivity";

    private static final int GREEN = -10053376;
    private static final int RED = -3407872;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washing_machine);

        // Database connection is created in background according to 'google-services.json'
        // Returns a handle to access the database.
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Reference the entry points in the database that are to be used
        DatabaseReference led_status = database.getReference("LED_STATUS");
        DatabaseReference machine_message = database.getReference("MACHINE_MESSAGE");

        // Views that are to be modified by this activity
        final TextView statusTextView = findViewById(R.id.statusTextView);
        final ImageView statusImageView = findViewById(R.id.statusImageView);

        final TextView messageTextView = findViewById(R.id.messageTextView);

        // Reset database log string
        machine_message.setValue("");

        // Initialize Database with value (may be used in debug only)
        // led_status.setValue("ON");

        // Update value of led_status from the database
        led_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String machine_state = dataSnapshot.getValue(String.class);

                // int color = ImageViewCompat.getImageTintList(statusImageView).getDefaultColor();


                if (machine_state.equals("ON")) {

                    // set power color to green
                    ImageViewCompat.setImageTintList(statusImageView, ColorStateList.valueOf(GREEN));

                    // log color change
                    Log.d(TAG, "COLOR_CHANGE " + machine_state + ":" + GREEN);

                } else if (machine_state.equals("OFF")) {

                    // set power color to green
                    ImageViewCompat.setImageTintList(statusImageView, ColorStateList.valueOf(RED));

                    // log color change
                    Log.d(TAG, "COLOR_CHANGE " + machine_state + ":" + RED);
                }

                // Set machine status in TextView
                statusTextView.setText(machine_state);

                // Change color of the power icon
                int current_color = ImageViewCompat.getImageTintList(statusImageView).getDefaultColor();

                Log.d(TAG, "Value is: '" + machine_state + "', Current color: " + current_color);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Private feature used in testing, not a public feature.
        // Update value of machine_message from the database
        machine_message.addValueEventListener(new ValueEventListener() {
            // Called when value of 'machine_message' changes in database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                String msg = messageTextView.getText().toString();

                msg += '\n' + value;

                // append log message to TextView
                messageTextView.setText(msg);

                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
