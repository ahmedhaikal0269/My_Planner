package ahmed.haikal.myplanner.View.Sign_In_Or_Up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;

import ahmed.haikal.myplanner.R;

public class Sign_In_Up_Activity extends AppCompatActivity {

    private ViewPager sign_in_up_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        sign_in_up_viewpager = findViewById(R.id.sign_in_up_viewpager);

        Sign_In_Up_Activity.AuthenticationPagerAdapter pagerAdapter = new Sign_In_Up_Activity.AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragmet(new LogInFragment());
        pagerAdapter.addFragmet(new SignUpFragment());
        sign_in_up_viewpager.setAdapter(pagerAdapter);

    }

    public void switchFragments(int fragNum){
        sign_in_up_viewpager.setCurrentItem(fragNum);
    }

    /**
     * View pager adapter class to connect the log in and sign up fragments to the main activity
     */
    private class AuthenticationPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragmet(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}