package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.concordia.gilgamesh.models.Location;

public class LavaNewLocationActivity extends BaseActivity {

    Button addMachineButton;
    Button saveButton;

    EditText locationName_InputEditText;
    EditText locationID_InputEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lava_new_location);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference();


        saveButton = findViewById(R.id.Save_Button);
        addMachineButton = findViewById(R.id.AddMachine_Button);
        locationName_InputEditText = findViewById(R.id.LocationName_InputEditText);
        locationID_InputEditText = findViewById(R.id.LocationID_InputEditText);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If user clicks on save button, then Add Machine Button should be displayed.

                if (validateForm()) {

                    Location new_location = new Location();

                    new_location.name = locationName_InputEditText.getText().toString();
                    new_location.custom_id = locationID_InputEditText.getText().toString();


                    databaseRef.child("custom-locations").child(getUid()).setValue(new_location);

                    saveButton.setClickable(false);

                    Intent intent = new Intent(LavaNewLocationActivity.this, CustomLocationManagerActivity.class);

                    startActivity(intent);

                }


            }
        });

        addMachineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LavaNewLocationActivity.this, LavaAddMachineActivity.class);

                intent.putExtra("location_type", "CUSTOM");

                startActivity(intent);


            }
        });

    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(locationName_InputEditText.getText().toString())) {
            locationName_InputEditText.setError("Required");
            result = false;
        } else {
            locationName_InputEditText.setError(null);
        }

        if (TextUtils.isEmpty(locationID_InputEditText.getText().toString())) {
            locationID_InputEditText.setError("Required");
            result = false;
        } else {
            locationID_InputEditText.setError(null);
        }

        return result;
    }
}
