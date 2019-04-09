package ca.concordia.gilgamesh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.d("TAG", "MyReceiver");


        final Context context1 = context;

        Intent serviceIntentTest = new Intent(context, NotificationTestActivity.class);
        context.startService(serviceIntentTest);


        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent serviceIntent = new Intent(context, UpdateUserMachinesService.class);

                    context1.startService(serviceIntent);
                } else {
                    //Resolve to authentication activity
                }
            }
        });


    }


}
