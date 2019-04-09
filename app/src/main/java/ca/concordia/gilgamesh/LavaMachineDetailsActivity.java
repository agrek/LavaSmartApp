package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.concordia.gilgamesh.models.Machine;

public class LavaMachineDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lava_machine_details);

        final TextView status = findViewById(R.id.Status__TextView);

        final TextView name = findViewById(R.id.MachineName_TextView);

        final TextView location = findViewById(R.id.MachineLocation_TextView);

        final Button deleteMachine_Button = findViewById(R.id.DeleteMachine_Button);

        final Switch notifyMe_Switch = findViewById(R.id.NotifyMe_Switch);

        Intent intent = getIntent();

        String custom_id = intent.getStringExtra("custom_id");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();


        databaseRef.child("machines").orderByChild("custom_id").equalTo(custom_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String machineId = new String();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    machineId = child.getKey();
                }

                if (machineId != null) {

                    final String finalMachineId = machineId;
                    deleteMachine_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            databaseRef.child("locations").child(getUid()).child("machines").child(finalMachineId).removeValue();

                            Intent intent = new Intent(v.getContext(), MachineListActivity.class);

                            startActivity(intent);

                        }
                    });

                    notifyMe_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                databaseRef.child("notifications").child(getUid()).child(finalMachineId).setValue(true);

                            } else {
                                databaseRef.child("notifications").child(getUid()).child(finalMachineId).removeValue();
                            }
                        }
                    });

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: machine null", Toast.LENGTH_SHORT);
                    toast.show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseRef.child("machines").orderByChild("custom_id").equalTo(custom_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    Machine machine = child.getValue(Machine.class);

                    status.setText(machine.status);

                    name.setText(machine.name);

                    location.setText(machine.location);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
