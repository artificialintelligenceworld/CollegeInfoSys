package isep.fr.collegeinfo.activity.admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import isep.fr.collegeinfo.R;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import isep.fr.collegeinfo.Constants;
import isep.fr.collegeinfo.WebServiceRequest.UpdateEventInfoService;
import isep.fr.collegeinfo.WebServiceUtil.ServerResponseListener;
import isep.fr.collegeinfo.activity.studentGuest.LogInActivity;
import isep.fr.collegeinfo.database.AppSharedPreferences;
import isep.fr.collegeinfo.interfaces.FileUtil;

public class AdminEventUpdateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "InsertEventActivity";

    EditText eventId, eventTitle, eventAddress, eventHall, eventDescrip;

    ImageView eventImg, eventImfFile, datePic, timePic;
    TextView dateView, timeView;

    Button createEvent;
    private int PICK_GALLERY = 1708;
    String eventImage = null;
    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Update Event");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        TextView navText = (TextView) headerView.findViewById(R.id.nav_tv_username);
        navText.setText(AppSharedPreferences.getInstance().getUserName());

        /*RelativeLayout header = (RelativeLayout) headerView.findViewById(R.id.nav_profile_pic);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AdminProfileActivity.class);
                startActivity(intent);

            }
        });*/
        eventImg = (ImageView) findViewById(R.id.event_img);
        eventImfFile = (ImageView) findViewById(R.id.event_img_bt);

        eventId = (EditText) findViewById(R.id.est_evnt_id);
        eventTitle = (EditText) findViewById(R.id.est_evnt_title);
        eventAddress = (EditText) findViewById(R.id.est_evnt_addr);
        eventHall = (EditText) findViewById(R.id.est_evnt_hall);
        eventDescrip = (EditText) findViewById(R.id.est_evnt_desc);

        createEvent = (Button) findViewById(R.id.btn_evnt_crt);


        datePic = (ImageView) findViewById(R.id.imv_event_calender);
        timePic = (ImageView) findViewById(R.id.imv_event_time);

        dateView = (TextView) findViewById(R.id.tv_event_date);
        timeView = (TextView) findViewById(R.id.tv_event_time);

        Intent valuesIntent = getIntent();
        Bundle extraBundle = valuesIntent.getExtras();

        if (extraBundle != null) {

            String evtId = extraBundle.getString(Constants.EVENT_ID);
            String evtTitle = extraBundle.getString(Constants.EVENT_TITLE);
            String evtDatTim = extraBundle.getString(Constants.EVENT_DATE_TIME);
            String evtAddress = extraBundle.getString(Constants.EVENT_PLACE);
            String evtHall = extraBundle.getString(Constants.EVENT_HALL);
            String evtType = extraBundle.getString(Constants.EVENT_TYPE);
            String evtDescript = extraBundle.getString(Constants.EVENT_DISCRIPTION);
            String evtImage = extraBundle.getString(Constants.EVENT_IMAGE);


            String[] namesList = evtDatTim.split(" ");


            String dat = namesList[0];
            String time = namesList[1] + " " + namesList[2];
            String imageUrl = Constants.EVENT_IMG_URL + evtImage;

            eventImage = imageUrl;

            Glide.with(AdminEventUpdateActivity.this)
                    .load(eventImage)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).centerCrop())
                    .into(eventImg);


            eventId.setText(evtId);
            eventTitle.setText(evtTitle);
            eventAddress.setText(evtAddress);
            eventHall.setText(evtHall);
            eventDescrip.setText(evtDescript);
            dateView.setText(dat);
            timeView.setText(time);


        }


        eventImfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromGallery();

            }
        });

        datePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminEventUpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        timePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AdminEventUpdateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String AM_PM = null;
                                if(hourOfDay < 12) {
                                    AM_PM = "AM";
                                } else {
                                    AM_PM = "PM";
                                }

                                timeView.setText(hourOfDay + ":" + minute + " "+ AM_PM);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternetConnection()) {

                    String evtId = eventId.getText().toString();
                    String evtTitle = eventTitle.getText().toString();
                    String evtAdd = eventAddress.getText().toString();
                    String evtHall = eventHall.getText().toString();
                    String evtDesc = eventDescrip.getText().toString();

                    String evtDate = dateView.getText().toString();
                    String evtTime = timeView.getText().toString();

                    String imgPat = eventImage;


                    if (evtId != null && !evtId.isEmpty() && evtTitle != null && !evtTitle.isEmpty() && evtAdd != null && !evtAdd.isEmpty()
                            && evtHall != null && !evtHall.isEmpty() && evtDesc != null && !evtDesc.isEmpty() && evtDate != null && !evtDate.isEmpty() && evtTime != null && !evtDate.isEmpty()) {

                        progressDoalog = new ProgressDialog(AdminEventUpdateActivity.this);
                        progressDoalog.setMessage("Please wait....");
                        progressDoalog.show();

                        String dateTime = evtDate + " " + evtTime;

                        UpdateEventInfoService updateEventInfoService = new UpdateEventInfoService(getApplicationContext(), evtId, evtTitle, dateTime, evtDesc, "college", evtAdd, evtHall, imgPat, new updateEventResponse());
                        updateEventInfoService.UpdateEventInfoService();


                    } else {

                        Toast.makeText(getApplicationContext(), "please enter all details ", Toast.LENGTH_SHORT).show();

                    }


                } else {

                }
            }
        });


    }

    private void getImageFromGallery() {

        Intent imageIntent = new Intent(Intent.ACTION_PICK);
        imageIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Constants.IMAGE_TYPE);
        startActivityForResult(imageIntent, PICK_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICK_GALLERY) {

                onSelectFromGalleryResult(data);

            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {

        Uri selectedImage = data.getData();
        File filePath = null;
        try {
            filePath = FileUtil.from(getApplicationContext(), selectedImage);
            eventImage = filePath.getPath();

            Glide.with(AdminEventUpdateActivity.this)
                    .load(Uri.fromFile(new File(eventImage)))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).centerCrop())
                    .into(eventImg);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //home navigation
            startActivity(new Intent(getApplicationContext(), AdminHomeActivity.class));

        } else if (id == R.id.nav_dept) {

            //navigation depart
            startActivity(new Intent(getApplicationContext(), AdminDepartmentViewActivity.class));

        } else if (id == R.id.nav_cour) {

            startActivity(new Intent(getApplicationContext(), AdminCourseViewActivity.class));

        } else if (id == R.id.nav_student) {

            startActivity(new Intent(getApplicationContext(), AdminStudentViewActivity.class));

        } else if (id == R.id.nav_events) {

            startActivity(new Intent(getApplicationContext(), AdminEventsActivity.class));

        } else if (id == R.id.nav_logout) {

            Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            return true;
        } else {

            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onClick(View v) {

    }

    private class updateEventResponse implements ServerResponseListener {

        @Override
        public void onResponseData(Object resultObj) {
            Log.d(TAG, "student create responce = " + resultObj.toString());

            try {
                JSONObject object = new JSONObject(resultObj.toString());

                String status = object.getString("status");

                progressDoalog.dismiss();
                if (status.equals("0")) {

                    finish();
                    onBackPressed();

                    Log.d(TAG, "update event is success ");
                } else {
                    Toast.makeText(getApplicationContext(), "Failed Update event", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                progressDoalog.dismiss();
                Log.d(TAG, "getting string status  error - " + e.toString());
                Toast.makeText(getApplicationContext(), "Failed Update Event", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        }
    }
}

