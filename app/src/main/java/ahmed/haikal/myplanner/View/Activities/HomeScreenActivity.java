package ahmed.haikal.myplanner.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Fragments.All_Lists_Fragment;
import ahmed.haikal.myplanner.View.Fragments.TodayViewFragment;
import ahmed.haikal.myplanner.View.Sign_In_Or_Up.Sign_In_Up_Activity;

public class HomeScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private static int actingUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();


        Bundle importData = getIntent().getExtras();
        actingUserID = importData.getInt("userID");


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new All_Lists_Fragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homePage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new All_Lists_Fragment()).commit();
                break;
            case R.id.todayView:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TodayViewFragment()).commit();
                break;
            case R.id.projectsPage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new All_Lists_Fragment()).commit();
                break;
            case R.id.calenderView:
                break;
            case R.id.signOut:
                startActivity(new Intent(this, Sign_In_Up_Activity.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public static int getActingUserID(){
        return actingUserID;
    }
}