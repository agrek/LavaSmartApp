package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.concordia.gilgamesh.models.Machine;

public class LavaAddMachineActivity extends BaseActivity {

    private static final String TAG = "LavaAddMachineActivity";
    private static final String REQUIRED = "Required";
    static private String customMacId;
    static private String customMacName;
    static private int THREAD_SLEEP = 500;
    private Button addNewMachine;
    private EditText nameEditText;
    private EditText idEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lava_add_machine);

        Intent intent = getIntent();
        final String location_type = intent.getStringExtra("location_type");


        // Database connection is created in background according to 'google-services.json'
        // Returns a handle to access the database.
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference databaseRef = database.getReference();

        final DatabaseReference machines = database.getReference("machines");


        addNewMachine = findViewById(R.id.AddMachine_Button);
        idEditText = findViewById(R.id.MachineId_InputEditText);
        nameEditText = findViewById(R.id.Name_InputEditText);


        addNewMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customMacId = idEditText.getText().toString();
                customMacName = nameEditText.getText().toString();

                if (!validateForm()) {
                    return;
                }


                machines.orderByChild("custom_id").equalTo(customMacId).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {

                            // machine exists

                            Machine myMachine = new Machine();

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                myMachine = child.getValue(Machine.class);
                                myMachine.key = child.getKey();
                            }

                            Log.d(TAG, String.format("Machine [%s] (%s) added", myMachine.name, myMachine.custom_id));

                            Toast.makeText(LavaAddMachineActivity.this,
                                    String.format("Machine %s already exists", myMachine.custom_id),
                                    Toast.LENGTH_SHORT).show();

                            // TODO: update name of machine in db
                            machines.child(myMachine.key).child("name").setValue(customMacName);

                            // TODO: add machine to list of machines

                            Intent newIntent;

                            if (location_type.equals("DEFAULT")) {

                                databaseRef.child("locations").child(getUid()).child("machines").child(myMachine.key).setValue(true);
                                // go back to main machine view

                                newIntent = new Intent(LavaAddMachineActivity.this, MachineListActivity.class);
                                newIntent.putExtra("location_type", "DEFAULT");

                            } else {
                                databaseRef.child("custom-locations").child(getUid()).child("machines").child(myMachine.key).setValue(true);
                                newIntent = new Intent(LavaAddMachineActivity.this, CustomLocationManagerActivity.class);
                                newIntent.putExtra("location_type", "CUSTOM");
                            }


                            startActivity(newIntent);


                        } else {

                            // Machine is null, error out

                            Log.e(TAG, "Machine " + customMacId + " does not exist");

                        }

                        // finish();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Log.e(TAG, "Machine " + customMacId + " error " + databaseError.getCode());
                        Log.w(TAG, "getMachine:onCancelled", databaseError.toException());

                    }
                });
            }

            private boolean validateForm() {
                boolean result = true;
                if (TextUtils.isEmpty(nameEditText.getText().toString())) {
                    nameEditText.setError("Required");
                    result = false;
                } else {
                    nameEditText.setError(null);
                }

                if (TextUtils.isEmpty(idEditText.getText().toString())) {
                    idEditText.setError("Required");
                    result = false;
                } else {
                    idEditText.setError(null);
                }

                return result;
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
