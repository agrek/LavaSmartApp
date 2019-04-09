package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TenantFragment extends DialogFragment {
    Button addLocationButton;

    TextView addLocation_InputEditText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tenant, container, false);
        addLocationButton = view.findViewById(R.id.AddLocation_Button);


        addLocation_InputEditText = view.findViewById(R.id.AddLocation_InputEditText);


        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateForm()) {
                    return;
                }

                final String locationShortId = addLocation_InputEditText.getText().toString();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseRef = database.getReference();

                databaseRef.child("custom-locations").orderByChild("custom_id").equalTo(locationShortId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();

                            databaseRef.child("users").child(getUid()).child("custom_location").setValue(key);

                            Intent intent = new Intent(new Intent(getActivity(), MachineListActivity.class));
                            intent.putExtra("location_type", "DEFAULT");
                            startActivity(intent);
                            return;

                        }


                        Toast.makeText(getActivity(), "Could not find custom location " + locationShortId,
                                Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


        return view;
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(addLocation_InputEditText.getText().toString())) {
            addLocation_InputEditText.setError("Required");
            result = false;
        } else {
            addLocation_InputEditText.setError(null);
        }

        return result;
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
