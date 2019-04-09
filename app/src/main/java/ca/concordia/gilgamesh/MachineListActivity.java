package ca.concordia.gilgamesh;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import ca.concordia.gilgamesh.models.Machine;

public class MachineListActivity extends BaseActivity {

    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_list);
        final ListView listview = findViewById(R.id.Machine_ListView);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();

        query = databaseRef.child("user-machines").child(getUid());

        FloatingActionButton fabAddMachine = findViewById(R.id.fabAddMachine);

        fabAddMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LavaAddMachineActivity.class);
                startActivity(intent);
            }
        });



        FirebaseListOptions<Machine> options = new FirebaseListOptions.Builder<Machine>()
                .setLayout(R.layout.rowlist)
                .setQuery(query, Machine.class)
                .build();

        FirebaseListAdapter<Machine> adapter = new FirebaseListAdapter<Machine>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull final Machine model, int position) {
                TextView machineName = v.findViewById(R.id.MachineName_TextView);
                TextView machineLocation = v.findViewById(R.id.MachineLocation_TextView);
                TextView machineStatus = v.findViewById(R.id.MachineStatus_TextView);


                machineName.setText(model.name);
                machineLocation.setText(model.location);
                machineStatus.setText(model.status);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), LavaMachineDetailsActivity.class);
                        intent.putExtra("custom_id", model.custom_id);
                        startActivity(intent);
                    }
                });


                if (model.status.equals("ON")) {
                    machineStatus.setTextColor(Color.GREEN);
                    machineStatus.setText("Status: ON");
                } else if (model.status.equals("OFF")) {
                    machineStatus.setTextColor(Color.RED);
                    machineStatus.setText("Status: OFF");
                }

            }
        };

        listview.setAdapter(adapter);
        adapter.startListening();

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
