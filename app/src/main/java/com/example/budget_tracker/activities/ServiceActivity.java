package com.example.budget_tracker.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.budget_tracker.fragments.GroupFragment;
import com.example.budget_tracker.setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.example.budget_tracker.R;
import com.example.budget_tracker.fragments.ExpenseFragment;
import com.example.budget_tracker.fragments.fragments_navigation.AppDetails;
import com.example.budget_tracker.fragments.fragments_navigation.Feedbacknav;
import com.example.budget_tracker.fragments.fragments_navigation.GraphFragment;
import com.example.budget_tracker.fragments.fragments_navigation.ShareNav;
import com.example.budget_tracker.fragments.fragments_navigation.userprofile;
import com.example.budget_tracker.notifications.NotificationFragment;



public class ServiceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;

    public NavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_group:
                        fragment = new GroupFragment();
                        break;
                    case R.id.navigation_expense:
                        fragment = new ExpenseFragment();
                        break;

                    case R.id.navigation_notification:
                        fragment = new NotificationFragment();
                       break;

                }
                return loadFragment(fragment);
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        loadFragment(new GroupFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_userProfile:
                fragment = new userprofile();
                break;
            case R.id.nav_noOfNotification:
                fragment = new NotificationFragment();
                break;

            case R.id.nav_feedback:
                fragment = new Feedbacknav();
                break;

            case R.id.nav_phoneNumber:
                fragment = new AppDetails();
                break;
            case R.id.nav_share:
                fragment = new ShareNav();
                break;
            case R.id.nav_signOut:
                signOut(findViewById(R.id.nav_signOut));
                break;
            case R.id.nav_setting:
                fragment=new setting();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return loadFragment(fragment);
    }


    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
            return false;
    }


    public void composeEmail(View v) {
        final EditText userFeedback = findViewById(R.id.giveFriendEmail);
        String emailFriend = userFeedback.getText().toString();
        switch (v.getId()) {
            case R.id.buttonShareEmail:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_TEXT, "Use this link to download Budget Tracker https://budgettracker/google/playstore");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Budget Tracker Invitation");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{emailFriend});
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
        }
    }

    public void sendFeedback(View v) {

        final TextInputLayout userFeedback = findViewById(R.id.textInputLayoutFeed);
        String feedback = userFeedback.getEditText().getText().toString();
        String emailApp = "budgettracker03@gmail.com";
        switch (v.getId()) {
            case R.id.btnfeedback:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_TEXT, feedback);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Budgettracker");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{emailApp});
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
        }
    }


    public void signOut(View view) {
        mAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void createNewGroup(View view) {
        Intent intent = new Intent(this, CreateNewGroupActivity.class);
        startActivity(intent);
    }

    public void addExpense(View v){
        Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
        startActivity(intent);
    }

    public void goToQuery(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.budget_tracker.activities.QueryActivity.class);
        startActivity(intent);

    }
}
