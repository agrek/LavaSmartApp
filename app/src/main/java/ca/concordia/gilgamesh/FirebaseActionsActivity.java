package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class FirebaseActionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_actions);


        Button washing_machine_demo_button = findViewById(R.id.button1);
        washing_machine_demo_button.setText(R.string.activity_washing_machine);
        washing_machine_demo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), WashingMachineActivity.class);
                startActivity(intent);
            }
        });

        Button main_button = findViewById(R.id.button2);
        main_button.setText(R.string.activity_main);
        main_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button add_machine_button = findViewById(R.id.button3);
        add_machine_button.setText(R.string.activity_add_machine);
        add_machine_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), AddMachineActivity.class);
                startActivity(intent);
            }
        });

        Button add_user_button = findViewById(R.id.button4);
        add_user_button.setText(R.string.activity_add_user);
        add_user_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivity(intent);
            }
        });


        Button add_location_button = findViewById(R.id.button5);
        add_location_button.setText(R.string.activity_add_location);
        add_location_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), AddLocationActivity.class);
                startActivity(intent);
            }
        });

        Button view_users_button = findViewById(R.id.button6);
        view_users_button.setText(R.string.activity_firebase_list);
        view_users_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), FirebaseListActivity.class);
                startActivity(intent);
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