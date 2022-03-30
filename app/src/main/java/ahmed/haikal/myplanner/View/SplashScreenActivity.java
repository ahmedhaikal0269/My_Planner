package ahmed.haikal.myplanner.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import ahmed.haikal.myplanner.R;
import ahmed.haikal.myplanner.View.Sign_In_Or_Up.Sign_In_Up_Activity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView logo = findViewById(R.id.app_logo_image);
                Animation animationUtils_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_in_left);
                logo.startAnimation(animationUtils_1);
                startActivity(new Intent(getApplicationContext(), Sign_In_Up_Activity.class));
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                finish();
            }
        }, 4000);

    }   
}