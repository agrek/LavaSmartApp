package ca.concordia.gilgamesh;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UpdateUserMachinesService extends Service {

    private static final String TAG = "UpdateUserMachines";
    private static final String REQUIRED = "Required";

    private static final int REFRESH_PERIOD = 1000;
    private static final int THREAD_SLEEP = 500;


    static List<String> defaultMachineIdList = new ArrayList<>();
    static Lock defaultMachineIdListLock = new ReentrantLock();

    static List<String> customMachineIdList = new ArrayList<>();
    static Lock customMachineIdListLock = new ReentrantLock();

    static String customLocationId;
    static Lock customLocationIdLock = new ReentrantLock();


    static Set<String> currentMachines = new HashSet<>();
    static Lock currentMachinesLock = new ReentrantLock();

    public static <T> Set<T> difference(final Set<T> setOne, final Set<T> setTwo) {
        // set1 - set2
        Set<T> result = new HashSet<T>(setOne);
        result.removeAll(setTwo);


        return result;
    }

    public String getUid() {

        // https://firebase.google.com/docs/auth/android/manage-users

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            // No user is signed in
            return null;
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseRef = database.getReference();

                updateCustomLocationId();

                while (getUid() == null || customLocationId == null) {

                    try {
                        Thread.sleep(THREAD_SLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                updateLocationMachineIds();
                copyUserMachines();

            }
        });

        t.start();


        return super.onStartCommand(intent, flags, startId);
    }


    void updateCustomLocationId() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();

        databaseRef.child("users").
                child(getUid()).
                child("custom_location").
                addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                customLocationIdLock.lock();
                                try {
                                    // access the resource protected by this lock

                                    customLocationId = dataSnapshot.getValue(String.class);


                                } finally {
                                    Log.v(TAG, customLocationId);
                                    customLocationIdLock.unlock();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                customLocationId = null;
                            }
                        });


    }

    void updateLocationMachineIds() {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {


            @Override
            public void run() {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseRef = database.getReference();

                databaseRef.child("locations").child(getUid()).child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        defaultMachineIdListLock.lock();

                        try {
                            // access the resource protected by this lock

                            defaultMachineIdList.clear();

                            for (DataSnapshot machine : dataSnapshot.getChildren()) {

                                defaultMachineIdList.add(machine.getKey());

                            }


                        } finally {
                            defaultMachineIdListLock.unlock();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                databaseRef.child("locations").child(customLocationId).child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        customMachineIdListLock.lock();

                        try {
                            // access the resource protected by this lock

                            customMachineIdList.clear();


                            for (DataSnapshot machine : dataSnapshot.getChildren()) {

                                customMachineIdList.add(machine.getKey());

                            }


                        } finally {
                            customMachineIdListLock.unlock();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        }, 0, REFRESH_PERIOD);

    }

    void copyUserMachines() {


        Timer timer = new Timer();

        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseRef = database.getReference();


                defaultMachineIdListLock.lock();
                customMachineIdListLock.lock();
                currentMachinesLock.lock();

                try {
                    // access the resource protected by these locks

                    Set<String> newMachines = new HashSet<>();
                    Set<String> removableMachines = new HashSet<>();

                    newMachines.addAll(defaultMachineIdList);
                    newMachines.addAll(customMachineIdList);

                    removableMachines.addAll(difference(currentMachines, newMachines));

                    currentMachines.clear();
                    currentMachines.addAll(newMachines);


                    for (final String machine : newMachines) {


                        databaseRef.child("machines").child(machine).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                try {
                                    databaseRef.child("user-machines").child(getUid()).child(machine).setValue(dataSnapshot.getValue());

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    for (final String machine : removableMachines) {

                        databaseRef.child("user-machines").child(getUid()).child(machine).removeValue();


                    }


                } finally {

                    defaultMachineIdListLock.unlock();
                    customMachineIdListLock.unlock();
                    currentMachinesLock.unlock();

                }

            }
        }, 0, REFRESH_PERIOD);


    }


}


//    Thread thread = new Thread(new Runnable() {
//
//        @Override
//        public void run() {
//            getDefaultLocationId();
//
//        }
//    });
//
//    thread.start();
//
//
//    Timer timer = new Timer();
//
//    timer.schedule(new TimerTask() {
//
//
//        @Override
//        public void run() {
//
//
//        }
//
//    }, 0, 1000);

