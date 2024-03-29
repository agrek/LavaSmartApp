package ca.concordia.gilgamesh;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

    String CHANNEL_ID = "my_channel_id";


    private static final int REFRESH_PERIOD = 1000;
    private static final int THREAD_SLEEP = 500;


    static List<String> defaultMachineIdList = new ArrayList<>();
    static Lock defaultMachineIdListLock = new ReentrantLock();

    static List<String> customMachineIdList = new ArrayList<>();
    static Lock customMachineIdListLock = new ReentrantLock();


    static List<String> ownedMachineIdList = new ArrayList<>();
    static Lock ownedMachineIdListLock = new ReentrantLock();
    static Set<String> currentOwnedMachines = new HashSet<>();
    static Lock currentOwnedMachinesLock = new ReentrantLock();


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
    public void onCreate() {
        super.onCreate();


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
                copyOwnedLocationMachines();

                updateNotifications();

            }
        });

        t.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);


        return Service.START_STICKY;
    }


    void updateCustomLocationId() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();

        databaseRef.child("users").
                child(getUid()).
                child("custom_location").
                addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                customLocationIdLock.lock();
                                try {
                                    // access the resource protected by this lock

                                    customLocationId = dataSnapshot.getValue(String.class);


                                } finally {

                                    if (customLocationId == null) {
                                        Log.v(TAG, "customLocationId is null");

                                    } else {
                                        Log.v(TAG, customLocationId);

                                    }

                                    customLocationIdLock.unlock();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                customLocationIdLock.lock();
                                try {
                                    customLocationId = null;

                                } finally {
                                    customLocationIdLock.unlock();
                                }
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

                // default location machine ids copy to Java

                try {
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

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                try {
                    databaseRef.child("custom-locations").child(customLocationId).child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
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

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                try {
                    databaseRef.child("custom-locations").child(getUid()).child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            ownedMachineIdListLock.lock();

                            try {
                                // access the resource protected by this lock

                                ownedMachineIdList.clear();

                                for (DataSnapshot machine : dataSnapshot.getChildren()) {

                                    ownedMachineIdList.add(machine.getKey());

                                }


                            } finally {
                                ownedMachineIdListLock.unlock();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


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
                                    databaseRef.child("user-default-location-view").child(getUid()).child(machine).setValue(dataSnapshot.getValue());

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

                        databaseRef.child("user-default-location-view").child(getUid()).child(machine).removeValue();


                    }


                } finally {


                    currentMachinesLock.unlock();
                    customMachineIdListLock.unlock();
                    defaultMachineIdListLock.unlock();

                }

            }
        }, 0, REFRESH_PERIOD);


    }

    void copyOwnedLocationMachines() {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseRef = database.getReference();

                ownedMachineIdListLock.lock();
                currentOwnedMachinesLock.lock();

                try {
                    // access the resource protected by these locks

                    Set<String> newMachines = new HashSet<>();
                    Set<String> removableMachines = new HashSet<>();

                    newMachines.addAll(ownedMachineIdList);

                    removableMachines.addAll(difference(currentOwnedMachines, newMachines));

                    currentOwnedMachines.clear();
                    currentOwnedMachines.addAll(newMachines);


                    for (final String machine : newMachines) {


                        databaseRef.child("machines").child(machine).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                try {
                                    databaseRef.child("manager-location-view").child(getUid()).child(machine).setValue(dataSnapshot.getValue());

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

                        try {
                            databaseRef.child("manager-location-view").child(getUid()).child(machine).removeValue();

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }


                    }


                } finally {


                    currentOwnedMachinesLock.unlock();
                    ownedMachineIdListLock.unlock();


                }

            }
        }, 0, REFRESH_PERIOD);


    }

    void updateNotifications() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();

        try {
            databaseRef.child("notifications").child(getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        final String machineId = child.getKey();


                        databaseRef.child("machines").child(machineId).child("status").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String status = dataSnapshot.getValue().toString();

                                if (status.equals("OFF")) {
                                    // TODO: FIXME
                                    addNotification("Machine");

                                    databaseRef.child("notifications").child(getUid()).child(machineId).removeValue();
                                }

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

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    public void addNotification(String machine_name) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notifyID = 1;

        Notification notification = new Notification();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            notification = new Notification.Builder(UpdateUserMachinesService.this)
                    .setContentTitle("LAVASMART")
                    .setContentText(machine_name + " is done washing!")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setChannelId(CHANNEL_ID)
                    .build();

        } else {
            createNotificationChannel();
            notification = new Notification.Builder(UpdateUserMachinesService.this)
                    .setContentTitle("LAVASMART")
                    .setContentText(machine_name + " is done washing!")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build();
        }


        notificationManager.notify(notifyID, notification);

    }

    //Adding notification channel that will run only in the case that the API is above the required one.

    public void createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


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

