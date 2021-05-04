package professional.wellness.health.com.employeeapp.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mukesh.permissions.AppPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyImage;
import professional.wellness.health.com.employeeapp.Fcm.Config;
import professional.wellness.health.com.employeeapp.Fcm.NotificationUtils;
import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.MyClinicWeek;
import professional.wellness.health.com.employeeapp.Model.ViewList;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.Utils.ClinicService;


/**
 * Created by Navit on 29-06-2017.
 */

public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/


    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private AppPermissions mRuntimePermission;
    private static final int ALL_REQUEST_CODE = 0;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    //fcm notification
    private double latitude;
    private double longitude;
    private static final String TAG = SplashActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        restInteraction = RESTInteraction.getInstance(SplashActivity.this);
        encryptionClass = EncryptionClass.getInstance(SplashActivity.this);
        init();
    }

    private void init() {
        GPSTracker gps = new GPSTracker(SplashActivity.this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }

        mRuntimePermission = new AppPermissions();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {


                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };
       // displayFirebaseRegId();
        if (mRuntimePermission.hasPermission(this, AppUtills.ALL_PERMISSIONS)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (SessionManager.getuserId(SplashActivity.this).equals("")) {
                        Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        mainIntent.putExtra("type", "");
                        mainIntent.putExtra("clinic_id", "");
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                        mainIntent.putExtra("type", "");
                        mainIntent.putExtra("clinic_id", "");
                        startActivity(mainIntent);

                     try {
                         startService(new Intent(SplashActivity.this,ClinicService.class));
                     }catch (Exception e){

                     }

                        finish();
                    }


                }
            }, SPLASH_DISPLAY_LENGTH);
        }else{
            mRuntimePermission.requestPermission(this, AppUtills.ALL_PERMISSIONS, ALL_REQUEST_CODE);
        }






    }




    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
        try {
            SessionManager.setDeviceID(this,regId);
        }catch (Exception e){

        }


        if (!TextUtils.isEmpty(regId))

        Log.e(TAG, "Firebase reg id: " + regId);
        else

        Log.e(TAG, "Firebase Reg Id is not received yet!");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (SessionManager.getuserId(SplashActivity.this).equals("")) {
                            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(mainIntent);

                            try {
                                startService(new Intent(SplashActivity.this,ClinicService.class));
                            }catch (Exception e){

                            }
                            finish();
                        }
                    }
                }, 4000);


                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }



}
