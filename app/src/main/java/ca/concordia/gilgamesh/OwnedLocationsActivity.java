package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class OwnedLocationsActivity extends BaseActivity {
    FloatingActionButton createLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owned_locations);

        createLocationButton = findViewById(R.id.AddLocationManager_Button);

        createLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OwnedLocationsActivity.this, LavaNewLocationActivity.class));

            }
        });


    }
}
