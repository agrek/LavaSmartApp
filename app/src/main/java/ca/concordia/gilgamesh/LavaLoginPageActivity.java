package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LavaLoginPageActivity extends BaseActivity {


    Button sign_in;
    Button sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lava_login_page);


        sign_in = findViewById(R.id.SignIn_Button);
        sign_up = findViewById(R.id.SignUp_Button);


        sign_in.setOnClickListener(new View.OnClickListener() {

            //Write an if statement if machine exists or not.
            //If machine exists, then go to MachineListActivity Activity
            //If not, go to user type.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LavaLoginPageActivity.this, MachineListActivity.class));
            }
        });

        //If no user exists, sign up.
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LavaLoginPageActivity.this, UserTypeActivity.class));
            }
        });
    }
}
