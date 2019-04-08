package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserTypeActivity extends BaseActivity {

    private static final String TAG = "UserTypeActivity";
    private static final String REQUIRED = "Required";
    static String defaultLocationId;
    private Button homeUserButton;
    private Button commercialUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        homeUserButton = findViewById(R.id.HomeUser_Button);
        commercialUserButton = findViewById(R.id.CommercialUser_Button);


        homeUserButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserTypeActivity.this, LavaAddMachineActivity.class));
            }
        });


        commercialUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserTypeActivity.this, CommercialUserTypeActivity.class));
            }
        });


        startService(new Intent(this, UpdateUserMachinesService.class));

        // getDefaultLocationId();


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

    void getDefaultLocationId() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();


        databaseRef.child("users").
                child(getUid()).
                child("default_location").
                addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                defaultLocationId = dataSnapshot.getValue(String.class);


                                Log.v(TAG, defaultLocationId);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                defaultLocationId = null;
                            }
                        });

    }
}
