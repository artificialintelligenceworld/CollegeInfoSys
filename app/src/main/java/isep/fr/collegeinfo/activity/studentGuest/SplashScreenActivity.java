package isep.fr.collegeinfo.activity.studentGuest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import isep.fr.collegeinfo.R;

import isep.fr.collegeinfo.activity.admin.AdminHomeActivity;
import isep.fr.collegeinfo.database.AppSharedPreferences;

@SuppressLint("Registered")
public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                String userType = null;

                userType = AppSharedPreferences.getInstance().getUserType();

                if(userType != null && !userType.isEmpty()){

                    if(userType.equals("admin")){
                        Intent intentFive = new Intent(getApplicationContext(), AdminHomeActivity.class);
                        intentFive.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intentFive);
                        finish();
                    }else {
                        Intent i = new Intent(getApplicationContext(), StudentHomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                        finish();

                    }


                }else{

                    Intent i = new Intent(SplashScreenActivity.this, LogInActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
