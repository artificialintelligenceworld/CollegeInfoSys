package isep.fr.collegeinfo;

import android.app.Application;

import isep.fr.collegeinfo.database.AppSharedPreferences;
import isep.fr.collegeinfo.database.DatabaseHelper;

public class MainApplication extends Application {
    public static final String TAG = "MainApplication";
    public static MainApplication Instance;

    public static MainApplication getInstance() {
        return Instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Instance = this;

        //initilize sharedprefference
        AppSharedPreferences.getInstance().Initialize(getApplicationContext());

        //initilize database
        DatabaseHelper.init(getApplicationContext());

    }
}
