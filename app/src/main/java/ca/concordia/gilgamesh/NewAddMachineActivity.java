package ca.concordia.gilgamesh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ca.concordia.gilgamesh.models.Post;
import ca.concordia.gilgamesh.models.User;

public class NewAddMachineActivity extends BaseActivity {

    private static final String TAG = "NewMachineActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;


    private EditText mMachineId;
    private Button mButtonAdd;
    private Button mButtonRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_machine);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);
        mSubmitButton = findViewById(R.id.fabSubmitPost);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });


        mMachineId = findViewById(R.id.fieldId);
        mButtonAdd = findViewById(R.id.buttonAdd);
        mButtonRemove = findViewById(R.id.buttonRemove);

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMachine();
            }
        });
        mButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeMachine();
            }
        });

    }

    private void addMachine() {

        final String machineId = mMachineId.getText().toString();

        // Machine ID is required
        if (TextUtils.isEmpty(machineId)) {
            mMachineId.setError(REQUIRED);
        }

        mDatabase.child("machines").child(machineId).child("enabled").setValue("TRUE");

        finish();

    }

    private void removeMachine() {

        final String machineId = mMachineId.getText().toString();

        // Machine ID is required
        if (TextUtils.isEmpty(machineId)) {
            mMachineId.setError(REQUIRED);
        }

        mDatabase.child("machines").child(machineId).child("enabled").setValue("FALSE");

        finish();

    }

    private void submitPost() {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewAddMachineActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username, title, body);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.show();
        } else {
            mSubmitButton.hide();
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-machines/$userid/$machineid and at
        // /machines/$machineid simultaneously
        String key = mDatabase.child("machines").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/machines/" + key, postValues);
        childUpdates.put("/user-machines/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}
