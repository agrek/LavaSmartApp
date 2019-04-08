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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import ca.concordia.gilgamesh.models.Machine;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private static final String REQUIRED = "Required";
    static List<Machine> machinelist = new ArrayList<>();
    static String currLoc;
    static String currMac;

    static String defaultLocation;
    static String customLocation;

    static List<String> myLocations;
    static Lock locationsLock;


    static List<String> myMachines;
    static Lock machinesLock;

    static Semaphore available = new Semaphore(1, true);

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();

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

        databaseRef.child("users").
                child(getUid()).
                child("custom_location").
                addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String customLocation = dataSnapshot.getValue(String.class);


                                Log.v(TAG, customLocation);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });


        while (customLocation == null) {
        }


        Timer timer = new Timer();

        timer.schedule(new TimerTask() {


            @Override
            public void run() {

                databaseRef.child("locations").child(getUid()).child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        }, 0, 2000);


        return super.onStartCommand(intent, flags, startId);
    }


    void unusedStuff() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();

        final List<String> locs = new ArrayList<>();
        final List<String> macs = new ArrayList<>();

        locs.add(defaultLocation);


        databaseRef.child("users").child(getUid()).child("saved_locations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> myTempLocations;

                myTempLocations = new ArrayList<>();

                locationsLock.lock();
                try {
                    // access the resource protected by this lock

                    for (DataSnapshot locId : dataSnapshot.getChildren()) {

                        myTempLocations.add(locId.getKey());

                    }

                } finally {


                    myLocations.addAll(myTempLocations);

                    locationsLock.unlock();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        while (myLocations == null) {
        }


        for (String loc : locs) {

            currLoc = loc;

            Timer timer = new Timer();

            timer.schedule(new TimerTask() {


                @Override
                public void run() {


                    new Thread(new Runnable() {

                        @Override
                        public void run() {


                            databaseRef.child("locations").child(currLoc).child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        currMac = child.getKey();
                                        macs.add(currMac);


                                        databaseRef.child("machines").child(currMac).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                databaseRef.child("locations").child(currLoc).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                                        String loc_name = dataSnapshot.getValue(String.class);
                                                        Machine currentMachine = dataSnapshot.getValue(Machine.class);
                                                        currentMachine.location = loc_name;
                                                        machinelist.add(currentMachine);
                                                        String Uid = getUid();
                                                        databaseRef.child("user-machine").child(Uid).child(dataSnapshot.getKey()).setValue(currentMachine);

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });


                                            }


                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    });

                }
            }, 0, 2000);

            Timer timer2 = new Timer();

            timer2.schedule(new TimerTask() {


                @Override
                public void run() {


                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            databaseRef.child("users").child(getUid()).child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    });

                }
            }, 0, 2000);


        }
    }
}