package ahmed.haikal.myplanner.View.Main_Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.ProjectsActivity;
import ahmed.haikal.myplanner.View.Sign_In_Or_Up.LogInFragment;
import ahmed.haikal.myplanner.View.Sign_In_Or_Up.Sign_In_Up_Activity;

public class MainScreenActivity extends AppCompatActivity {

    private ViewPager main_screen_viewpager;
    private AuthenticationPagerAdapter pagerAdapter;
    private TabLayout mainScreenTabs;
    private TextView header;
    private Spinner dropDownMenu;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        header = findViewById(R.id.header);
        main_screen_viewpager = findViewById(R.id.main_screen_viewpager);
        mainScreenTabs = findViewById(R.id.main_screen_tabs);
        pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new All_Lists_Fragment());
        pagerAdapter.addFragments(new TodayViewFragment());
        main_screen_viewpager.setAdapter(pagerAdapter);
        mainScreenTabs.setupWithViewPager(main_screen_viewpager);

        // drop down menu in toolbar
        dropDownMenu = findViewById(R.id.app_dropdown_menu);
        dropDownMenu.setBackground(getDrawable(R.drawable.ic_dropdown_menu));

        list = new ArrayList<>();
        list.add("Home");
        list.add("Today");
        list.add("Projects");
        list.add("Calender");
        list.add("Sign Out");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_item, list);
        dropDownMenu.setAdapter(dataAdapter);
        dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = (String) adapterView.getItemAtPosition(position);
                switch (item){
                    case "Home":
                        //do nothing cause we're already there
                        break;
                    case "Projects":
                        startActivity(new Intent(getApplicationContext(), ProjectsActivity.class));
                        break;
                    case "Today":
                        startActivity(new Intent(getApplicationContext(), TodayViewFragment.class));
                        break;
                    case "Sign Out":
                        startActivity(new Intent(getApplicationContext(), Sign_In_Up_Activity.class));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //set user name in greeting bar
        Bundle importData = getIntent().getExtras();
    //    String username = importData.getString("username");
    //    header.setText("Hey, " + username + "!");
    //    System.out.println("username from intent: " + username);
    }

//////////////////////////////////////   Fragment Pager Adapter   ////////////////////////////////////////////
    /*
    This class is an adapter for the viewPager on the MainScreenActivity.
    The ViewPage that should host the fragment of the All_Lists and the fragment of TodayView
    */

    private class AuthenticationPagerAdapter extends FragmentPagerAdapter {

        public AuthenticationPagerAdapter (FragmentManager fm){ super(fm);}

        ArrayList<Fragment> fragmentList = new ArrayList<>();

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            if(position == 0)
                fragment = new All_Lists_Fragment();
            else if(position == 1)
                fragment = new TodayViewFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        //add
        public void addFragments(Fragment fragment){

        }

        public CharSequence getPageTitle(int position){
            if(position == 0)
                return "All Lists";
            else if(position == 1)
                return "Today";
            return "";
        }
    }
}