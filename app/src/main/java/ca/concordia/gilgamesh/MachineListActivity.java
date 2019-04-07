package ca.concordia.gilgamesh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ca.concordia.gilgamesh.models.Machine;

public class MachineListActivity extends BaseActivity {

    //Create Array List of Machine
    static ArrayList<Machine> machinelist = new ArrayList<>();

    static String currLoc;
    static String currMac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_list);
        final ListView listview = findViewById(R.id.Machine_ListView);

        baseActivityInit();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();
        final DatabaseReference machines = database.getReference("machines");

        final List<String> locs = new ArrayList<>();

        final List<String> macs = new ArrayList<>();


        locs.add(defaultLocation);


        Timer timer = new Timer();

        timer.schedule(new TimerTask() {


            @Override
            public void run() {

                machinelist.clear();

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        for (String loc : locs) {

                            currLoc = loc;

                            databaseRef.child("locations").child(currLoc).child("machines").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        currMac = child.getKey();
                                        macs.add(currMac);


                                        databaseRef.child("machines").child(currMac).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                                Machine currentMachine = dataSnapshot.getValue(Machine.class);
                                                currentMachine.location = currLoc;
                                                machinelist.add(currentMachine);


                                                updateAdapter();
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
                    }
                });

            }
        }, 0, 10000);


        //Add dummy machine
        // machinelist.add(new Machine("Lorenzo Machine", "summerhill_home", "ON"));
        // machinelist.add(new Machine("Mario Machine", "summerhill_building", "OFF"));


    }

    void updateAdapter() {

        ListView listview = findViewById(R.id.Machine_ListView);
        MachineListViewCustomAdapter machineListViewCustomAdapter = new MachineListViewCustomAdapter(this, machinelist);
        listview.setAdapter(machineListViewCustomAdapter);

    }

}
