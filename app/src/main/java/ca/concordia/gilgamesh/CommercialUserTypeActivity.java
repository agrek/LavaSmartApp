package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CommercialUserTypeActivity extends BaseActivity {
    Button tenant_button;
    Button manager_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_user_type);
        tenant_button = findViewById(R.id.Tenant_Button);
        tenant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TenantFragment tenantFragment = new TenantFragment();
                tenantFragment.show(getSupportFragmentManager(), "Tenant Fragment");


            }
        });

        manager_button = findViewById(R.id.Manager_Button);
        manager_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommercialUserTypeActivity.this, LavaNewLocationActivity.class));

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
