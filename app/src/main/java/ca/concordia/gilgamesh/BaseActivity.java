package ca.concordia.gilgamesh;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private static final String REQUIRED = "Required";
    static protected String defaultLocation;
    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    public String getDefaultLocation() {
        return defaultLocation;
    }

    void defaultLocation() {

        // Database connection is created in background according to 'google-services.json'
        // Returns a handle to access the database.
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference databaseRef = database.getReference();

        databaseRef.child("users").
                child(getUid()).
                child("default_location").
                addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                defaultLocation = dataSnapshot.getValue(String.class);


                                Log.v(TAG, defaultLocation);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                defaultLocation = null;
                            }
                        });


    }


    public void baseActivityInit() {
        defaultLocation();
    }


}
