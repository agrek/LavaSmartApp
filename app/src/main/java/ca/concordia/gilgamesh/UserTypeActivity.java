package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserTypeActivity extends BaseActivity {

    private static final String TAG = "UserTypeActivity";
    private static final String REQUIRED = "Required";
    private Button homeUserButton;
    private Button commercialUserButton;

    private Button allMachinesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);


        allMachinesButton = findViewById(R.id.all_machines_Button);

        homeUserButton = findViewById(R.id.HomeUser_Button);


        homeUserButton = findViewById(R.id.HomeUser_Button);
        commercialUserButton = findViewById(R.id.CommercialUser_Button);


        allMachinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserTypeActivity.this, MachineListActivity.class);
                intent.putExtra("location_type", "DEFAULT");

                startActivity(intent);

            }
        });


        homeUserButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this, LavaAddMachineActivity.class);
                intent.putExtra("location_type", "DEFAULT");

                startActivity(intent);
            }
        });


        commercialUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this, CommercialUserTypeActivity.class);
                intent.putExtra("location_type", "DEFAULT");

                startActivity(intent);
            }
        });


        startService(new Intent(this, UpdateUserMachinesService.class));


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
