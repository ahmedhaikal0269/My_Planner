package ahmed.haikal.myplanner.View.Main_Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import ahmed.haikal.myplanner.R;

public class MainScreenActivity extends AppCompatActivity {

    ViewPager main_screen_viewpager;
    AuthenticationPagerAdapter pagerAdapter;
    TabLayout mainScreenTabs;
    ImageButton dropdown;
    boolean menuOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        dropdown = findViewById(R.id.main_dropdown);

        main_screen_viewpager = findViewById(R.id.main_screen_viewpager);
        mainScreenTabs = findViewById(R.id.main_screen_tabs);
        pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new All_Lists_Fragment());
        pagerAdapter.addFragments(new TodayViewFragment());
        main_screen_viewpager.setAdapter(pagerAdapter);
        mainScreenTabs.setupWithViewPager(main_screen_viewpager);

        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable menu_open = getDrawable(R.drawable.ic_arrow_upward_24);
                Drawable menu_closed = getDrawable(R.drawable.ic_dropdown_menu);
                if(menuOpened) {
                    dropdown.setBackground(menu_closed);
                    menuOpened = false;
                }
                else {
                    dropdown.setBackground(menu_open);
                    menuOpened = true;
                }
            }
        });

    }

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