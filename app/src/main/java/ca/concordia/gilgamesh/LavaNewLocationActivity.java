package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LavaNewLocationActivity extends BaseActivity {

    Button addMachineButton;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lava_new_location);


        saveButton = findViewById(R.id.Save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If user clicks on save button, then Add Machine Button should be displayed.
            }
        });

        addMachineButton = findViewById(R.id.AddMachine_Button);
        addMachineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LavaNewLocationActivity.this, AddMachineManagerActivity.class));
            }
        });

    }
}
