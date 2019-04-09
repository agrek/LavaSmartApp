package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class AddMachineManagerActivity extends BaseActivity {

    FloatingActionButton addMachineManagerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_machine_manager);

        addMachineManagerButton = findViewById(R.id.fabAddMachineManager_Button);

        addMachineManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newIntent = new Intent(AddMachineManagerActivity.this, LavaAddMachineActivity.class);
                newIntent.putExtra("location_type", "DEFAULT");
                startActivity(newIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
