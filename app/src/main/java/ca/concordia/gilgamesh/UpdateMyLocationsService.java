package ca.concordia.gilgamesh;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import ca.concordia.gilgamesh.models.Machine;

public class UpdateMyLocationsService extends Service {


    private static final String TAG = "UpdateMyLocationsService";
    private static final String REQUIRED = "Required";


    public UpdateMyLocationsService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();
        final DatabaseReference machines = database.getReference("machines");


        databaseRef.child("users").
                child(getUid()).
                child("default_location").
                addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String defaultLocation = dataSnapshot.getValue(String.class);


                                Log.v(TAG, defaultLocation);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });


        Timer timer = new Timer();

        timer.schedule(new TimerTask() {


            @Override
            public void run() {


                new Thread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });

            }
        }, 0, 2000);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
