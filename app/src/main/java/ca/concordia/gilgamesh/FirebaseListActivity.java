package ca.concordia.gilgamesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ca.concordia.gilgamesh.fragment.MachineListFragment;
import ca.concordia.gilgamesh.fragment.LocationListFragment;
import ca.concordia.gilgamesh.fragment.UserListFragment;

public class FirebaseListActivity extends BaseActivity {

    private static final String TAG = "FirebaseListActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_list);

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new UserListFragment(),
                    new LocationListFragment(),
                    new MachineListFragment(),
            };
            private final String[] mFragmentNames = new String[]{
                    getString(R.string.heading_user_list),
                    getString(R.string.heading_locations_list),
                    getString(R.string.heading_machines_list)
            };

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.firebase_list_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Button launches NewPostActivity
        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseListActivity.this, NewUserActivity.class));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            FloatingActionButton floatingActionButton = findViewById(R.id.fabNewPost);

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                final int position = tab.getPosition();

                // Toast.makeText(getApplicationContext(), "TabSelected" + String.valueOf(tab.getPosition()), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "TabSelected" + String.valueOf(position));

                switch (position) {
                    case 0:
                        Log.d(TAG, "Case 0" + String.valueOf(position));
                        findViewById(R.id.fabNewPost).setOnClickListener(null);
                        // Button launches NewPostActivity
                        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(FirebaseListActivity.this, NewUserActivity.class));
                                Log.d(TAG, "OnCLick" + String.valueOf(position));
                            }
                        });
                        break;
                    case 1:
                        Log.d(TAG, "Case 1" + String.valueOf(position));
                        findViewById(R.id.fabNewPost).setOnClickListener(null);
                        // Button launches NewPostActivity
                        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(FirebaseListActivity.this, NewLocationActivity.class));
                                Log.d(TAG, "OnCLick" + String.valueOf(position));
                            }
                        });
                        break;
                    case 2:
                        Log.d(TAG, "Case 2" + String.valueOf(position));
                        findViewById(R.id.fabNewPost).setOnClickListener(null);
                        // Button launches NewPostActivity
                        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(FirebaseListActivity.this, NewMachineActivity.class));
                                Log.d(TAG, "OnCLick" + String.valueOf(position));
                            }
                        });
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                // Toast.makeText(getApplicationContext(), "TabUnselected" + String.valueOf(tab.getPosition()), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                // Toast.makeText(getApplicationContext(), "TabReselected" + String.valueOf(tab.getPosition()), Toast.LENGTH_SHORT).show();

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
