package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
                startActivity(new Intent(CommercialUserTypeActivity.this, OwnedLocationsActivity.class));
            }
        });

    }
}
