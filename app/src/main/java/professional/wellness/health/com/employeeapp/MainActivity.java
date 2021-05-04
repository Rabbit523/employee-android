package professional.wellness.health.com.employeeapp;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessaging;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import professional.wellness.health.com.employeeapp.Adapter.DayCalenderAdapter;
import professional.wellness.health.com.employeeapp.Adapter.RecyclerViewAdapter;
import professional.wellness.health.com.employeeapp.Adapter.menuAdapter;

import professional.wellness.health.com.employeeapp.Fcm.Config;
import professional.wellness.health.com.employeeapp.Fcm.NotificationUtils;
import professional.wellness.health.com.employeeapp.Residemenu.ResideMenu;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.Constant;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.fragment.AvailableFragment;
import professional.wellness.health.com.employeeapp.fragment.AvailableFragmentChange;
import professional.wellness.health.com.employeeapp.fragment.AvailableNewFragment;
import professional.wellness.health.com.employeeapp.fragment.CalenderFragment;
import professional.wellness.health.com.employeeapp.fragment.CalenderFragmentNew;
import professional.wellness.health.com.employeeapp.fragment.CertificateFragment;
import professional.wellness.health.com.employeeapp.fragment.HomeFeedFragment;
import professional.wellness.health.com.employeeapp.fragment.HomeFragment;
import professional.wellness.health.com.employeeapp.fragment.ProfileFragment;
import professional.wellness.health.com.employeeapp.fragment.TimeSheet;

import static professional.wellness.health.com.employeeapp.Utils.AppUtills.context;

public class MainActivity extends AppCompatActivity {

    public static ResideMenu resideMenu;
    private static NotificationManager notificationManager;
    private RecyclerView leftSlide;
    private ImageView delet_reside;
    public static RegularTextView user_email;
    private LinearLayout home_feeds;
    private LinearLayout available;
    private LinearLayout my_clinics;
    public static LinearLayout home_feeds_view;
    public static LinearLayout available_view;
    public static LinearLayout my_clinics_view;
    public static LinearLayout ll_calander;

    public static LinearLayout empReturn;
    public static LinearLayout empTab;
    public static ImageView img_calander_blue;
    private ImageView img_clander_red;
    private boolean selected = true;
    private NotificationManager notifier;
    public static LinearLayout available_icon;
    private ImageView toolbar_logo;
    private LinearLayout toolbar_drawer;
    public static int savepos = 0;
    private menuAdapter adapter;
    public static ImageView img_available;
    public static CircleImageView user_image;
    public static Toolbar toolbar;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private double latitude;
    private double longitude;
    public static MainActivity activity;
    public  static LinearLayout main_layout;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    private void init() {
        activity = MainActivity.this;
        notificationManager = (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
        encryptionClass = EncryptionClass.getInstance(this);
        restInteraction = RESTInteraction.getInstance(this);
        final Context context = getApplicationContext();
      /*  Notification notification = new Notification(R.drawable.app_iconnn,
               "EmployeeApp",
                System.currentTimeMillis());
        notification.flags |= Notification.FLAG_NO_CLEAR
                | Notification.FLAG_ONGOING_EVENT;
         notifier = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);
        notifier.notify(1, notification);*/
        GPSTracker gps = new GPSTracker(this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }
        deleteCache(MainActivity.this);
        setUpMenu();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        main_layout = (LinearLayout)findViewById(R.id.main_layout);
        home_feeds = (LinearLayout) findViewById(R.id.home_feeds);
        available = (LinearLayout) findViewById(R.id.available);
        my_clinics = (LinearLayout) findViewById(R.id.my_clinics);

        empTab = (LinearLayout) findViewById(R.id.empTab);
        empReturn = (LinearLayout) findViewById(R.id.empReturn);
        img_available = (ImageView) findViewById(R.id.img_available);
        toolbar_logo = (ImageView) findViewById(R.id.toolbar_logo);
        toolbar_drawer = (LinearLayout) findViewById(R.id.toolbar_drawer);

        toolbar_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empTab.setVisibility(View.VISIBLE);
                img_calander_blue.setSelected(false);
                img_available.setSelected(false);
                savepos = 0;
                adapter.notifyDataSetChanged();
                MainActivity.img_available.setImageResource(R.drawable.available_selector);
                MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
                available_icon.setVisibility(View.INVISIBLE);
                ll_calander.setVisibility(View.INVISIBLE);
                home_feeds_view.setVisibility(View.VISIBLE);
                available_view.setVisibility(View.INVISIBLE);
                my_clinics_view.setVisibility(View.INVISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_container, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        empReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empTab.setVisibility(View.VISIBLE);
                empReturn.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_container, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        home_feeds_view = (LinearLayout) findViewById(R.id.home_feeds_view);
        available_view = (LinearLayout) findViewById(R.id.available_view);
        my_clinics_view = (LinearLayout) findViewById(R.id.my_clinics_view);
        img_calander_blue = (ImageView) findViewById(R.id.img_calander_blue);

        ll_calander = (LinearLayout) findViewById(R.id.ll_calander);
        available_icon = (LinearLayout) findViewById(R.id.available_icon);
        ll_calander.setVisibility(View.INVISIBLE);
        available_icon.setVisibility(View.INVISIBLE);
        home_feeds_view.setVisibility(View.VISIBLE);

        home_feeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.setbackValue(context,"home_feed");
                img_calander_blue.setSelected(false);
                img_available.setSelected(false);
                savepos = 0;

                MainActivity.available_icon.setVisibility(View.GONE);
                MainActivity.empReturn.setVisibility(View.GONE);
                MainActivity.img_available.setImageResource(R.drawable.available_selector);
                MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
                MainActivity.empTab.setVisibility(View.VISIBLE);
                MainActivity.ll_calander.setVisibility(View.INVISIBLE);
                MainActivity.home_feeds_view.setVisibility(View.VISIBLE);
                MainActivity.available_view.setVisibility(View.INVISIBLE);
                MainActivity.my_clinics_view.setVisibility(View.INVISIBLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_container, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.setbackValue(context,"available");
                img_calander_blue.setSelected(false);
                img_available.setSelected(false);
                savepos = 1;
                adapter.notifyDataSetChanged();
                MainActivity.img_available.setImageResource(R.drawable.available_selector);
                MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
                available_icon.setVisibility(View.VISIBLE);
                ll_calander.setVisibility(View.INVISIBLE);
                home_feeds_view.setVisibility(View.INVISIBLE);
                available_view.setVisibility(View.VISIBLE);
                my_clinics_view.setVisibility(View.INVISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_container, new AvailableFragmentChange());
                fragmentTransaction.commit();
            }
        });

        my_clinics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.setbackValue(context,"myclinic");
                img_calander_blue.setSelected(false);
                img_available.setSelected(false);
                savepos = 2;
                adapter.notifyDataSetChanged();
                MainActivity.img_available.setImageResource(R.drawable.available_selector);
                MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
                available_icon.setVisibility(View.INVISIBLE);
                ll_calander.setVisibility(View.VISIBLE);
                home_feeds_view.setVisibility(View.INVISIBLE);
                available_view.setVisibility(View.INVISIBLE);
                my_clinics_view.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_container, new CalenderFragmentNew());
                fragmentTransaction.commit();

            }
        });



        try {
            if (getIntent().getStringExtra("type").equals("clockout")) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(this));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(this));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("clinic_id", getIntent().getStringExtra("clinic_id"));
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new updateClockOutAsync().execute(encrypted);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (getIntent().getStringExtra("type").equals("clockin")) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(this));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(this));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("clinic_id", getIntent().getStringExtra("clinic_id"));
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new updateClockinAsync().execute(encrypted);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

            }
        } catch (Exception e) {

        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_container, new HomeFragment());
        fragmentTransaction.commit();



        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    String title = intent.getStringExtra("title");
                    String type = intent.getStringExtra("type");



                    if (intent.getStringExtra("type").equals("preffered")) {
                        img_calander_blue.setSelected(false);
                        img_available.setSelected(false);
                        savepos = 0;
       try {

                      MainActivity.available_icon.setVisibility(View.GONE);
                        MainActivity.empReturn.setVisibility(View.GONE);
                        MainActivity.img_available.setImageResource(R.drawable.available_selector);
                        MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
                        MainActivity.empTab.setVisibility(View.VISIBLE);
                        MainActivity.ll_calander.setVisibility(View.INVISIBLE);
                        MainActivity.home_feeds_view.setVisibility(View.VISIBLE);
                        MainActivity.available_view.setVisibility(View.INVISIBLE);
                        MainActivity.my_clinics_view.setVisibility(View.INVISIBLE);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.view_container, new HomeFragment());
                        fragmentTransaction.commit();
       }catch (Exception e){

       }
                        try {
                            final Snackbar snackbar = Snackbar.make(main_layout, title, (int) 5000L);
                            snackbar.setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });

                            snackbar.show();
                        }catch (Exception e){

                        }

                       // alertDialogNotification(intent.getStringExtra("title"),intent.getStringExtra("type"));
                    }else if(intent.getStringExtra("type").equals("clockin")){
                        img_calander_blue.setSelected(false);
                        img_available.setSelected(false);
                        savepos = 0;

                        MainActivity.available_icon.setVisibility(View.GONE);
                        MainActivity.empReturn.setVisibility(View.GONE);
                        MainActivity.img_available.setImageResource(R.drawable.available_selector);
                        MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
                        MainActivity.empTab.setVisibility(View.VISIBLE);
                        MainActivity.ll_calander.setVisibility(View.INVISIBLE);
                        MainActivity.home_feeds_view.setVisibility(View.VISIBLE);
                        MainActivity.available_view.setVisibility(View.INVISIBLE);
                        MainActivity.my_clinics_view.setVisibility(View.INVISIBLE);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.view_container, new HomeFragment());
                        fragmentTransaction.commit();
                        try {
                            final Snackbar snackbar = Snackbar.make(main_layout, title, (int) 10000L);
                            snackbar.setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.show();
                        }catch (Exception e){}


                        // alertDialogNotification(intent.getStringExtra("title"),intent.getStringExtra("type"));
                    }else if(intent.getStringExtra("type").equals("clockout")){
                        try {
                            final Snackbar snackbar = Snackbar.make(main_layout, title, (int) 10000L);
                            snackbar.setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });

                            snackbar.show();
                        }catch (Exception e){

                        }
                       // alertDialogNotification(intent.getStringExtra("title"),intent.getStringExtra("type"));
                    }else if(intent.getStringExtra("type").equals("announcement")){

                        try {
                            final Snackbar snackbar = Snackbar.make(main_layout, title, (int) 10000L);
                            snackbar.setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });

                            snackbar.show();
                        }catch (Exception e){

                        }
                        //alertDialogNotification(intent.getStringExtra("title"),intent.getStringExtra("type"));
                    }else if(intent.getStringExtra("type").equals("clinic_update")){
                        try {
                            final Snackbar snackbar = Snackbar.make(main_layout, title, (int) 10000L);
                            snackbar.setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });

                            snackbar.show();
                        }catch (Exception e){

                        }
                      //  alertDialogNotification(intent.getStringExtra("title"),intent.getStringExtra("type"));
                    }else {

                    }
                   // alertDialog(message);

                }
            }
        };


    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private class updateClockOutAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(MainActivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateClockinRequest(APIUrl.UPDATECLOCKOUT, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        alertDialogSucess(jsonObject.getString("message"));

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }


    private class updateClockinAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(MainActivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateClockinRequest(APIUrl.UPDATECLOCKIN, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                        notification();
                        alertDialogSucess(jsonObject.getString("message"));

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }

    public static void notification() {
        final int icon = R.drawable.app_iconnn;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        activity,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                activity);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine("Clockout your Clinic");

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker("Clocked").setWhen(0)
                .setAutoCancel(false)
                .setContentTitle("clockout")
                .setContentIntent(resultPendingIntent)
                .setStyle(inboxStyle)
                .addAction(R.mipmap.blueclock, "clockout", resultPendingIntent) // #0
                .setWhen(getTimeMilliSec(String.valueOf(System.currentTimeMillis())))
                .setSmallIcon(R.drawable.app_iconnn)
                .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), icon))
                .setContentText("Clockout your Clinic")
                .build();

       /*    notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;*/


        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }

    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void alertDialogNotification(String title, String type) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_notification, null);
        RegularTextView clinic_type = (RegularTextView) view.findViewById(R.id.clinic_type);
        RegularTextView clinic_title = (RegularTextView) view.findViewById(R.id.clinic_title);
        clinic_type.setText(type);
        clinic_title.setText(title);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        try {
            dialog.show();
        }catch (Exception e){

        }

        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view_container, new HomeFragment());
                fragmentTransaction.commit();*/

                dialog.dismiss();
            }
        });
    }



    public void alertDialogSucess(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alert_dialog_sucess, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void setUpMenu() {
        List<String> _listDataHeader = new ArrayList<>();
        _listDataHeader.add("Home");
        _listDataHeader.add("Available");
        _listDataHeader.add("My Clinics");
        _listDataHeader.add("Time sheet");
        _listDataHeader.add("Certificates");
        _listDataHeader.add("Profile");
        _listDataHeader.add("Logout");

        Integer[] imageIDs = {
                R.mipmap.home,
                R.mipmap.available,
                R.mipmap.myclinic,
                R.mipmap.clinic,
                R.mipmap.certificate,
                R.mipmap.profile,
                R.mipmap.logout,
        };

        resideMenu = new ResideMenu(MainActivity.this, R.layout.slide_menu, R.layout.home);
        //resideMenu.setUse3D(true);
        resideMenu.setBackground(R.color.residemenu_color);

        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        View leftView = resideMenu.getLeftMenuView();
        leftSlide = (RecyclerView) leftView.findViewById(R.id.leftSlide);
        delet_reside = (ImageView) leftView.findViewById(R.id.delet_reside);
        user_image = (CircleImageView) leftView.findViewById(R.id.user_image);
        user_email = (RegularTextView) leftView.findViewById(R.id.user_email);


        Picasso.with(this)
                .load(SessionManager.getImage(MainActivity.this))
                .into(user_image);

 /*       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            user_image.setImageDrawable(context.getResources().getDrawable(R.drawable.user_default_img, context.getApplicationContext().getTheme()));
        } else {
            user_image.setImageDrawable(context.getResources().getDrawable(R.drawable.user_default_img));
        }*/

        user_email.setText(SessionManager.getfirstName(MainActivity.this) + " " + SessionManager.getlastName(MainActivity.this));
        leftSlide.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        leftSlide.setItemAnimator(new DefaultItemAnimator());
        /*leftSlide.setAdapter(new adapter(MainActivity.this, (List<String>) _listDataHeader, imageIDs,leftSlide));*/
        adapter = new menuAdapter(MainActivity.this, (List<String>) _listDataHeader, imageIDs, leftSlide);
        leftSlide.setAdapter(adapter);
        //   leftSlide.setAdapter(new SlideMenuAdapter(HomeActivity.this, _listDataHeader,_listDataChild));

/*
        leftSlide.addOnItemTouchListener(new AppUtills.RecyclerItemClickListener(MainActivity.this, leftSlide, new AppUtills.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    empReturn.setVisibility(View.GONE);
                    empTab.setVisibility(View.VISIBLE);
                    ll_calander.setVisibility(View.INVISIBLE);
                    home_feeds_view.setVisibility(View.VISIBLE);
                    available_view.setVisibility(View.INVISIBLE);
                    my_clinics_view.setVisibility(View.INVISIBLE);
                    ll_calander.setVisibility(View.GONE);
                    resideMenu.closeMenu();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new HomeFeedFragment());
                    fragmentTransaction.commit();
                } else if (position == 1) {
                    empReturn.setVisibility(View.GONE);
                    empTab.setVisibility(View.VISIBLE);
                    ll_calander.setVisibility(View.INVISIBLE);
                    home_feeds_view.setVisibility(View.INVISIBLE);
                    available_view.setVisibility(View.VISIBLE);
                    my_clinics_view.setVisibility(View.INVISIBLE);
                    ll_calander.setVisibility(View.GONE);
                    resideMenu.closeMenu();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new AvailableFragment());
                    fragmentTransaction.commit();
                } else if (position == 2) {
                    empReturn.setVisibility(View.GONE);
                    empTab.setVisibility(View.VISIBLE);
                    ll_calander.setVisibility(View.INVISIBLE);
                    home_feeds_view.setVisibility(View.INVISIBLE);
                    available_view.setVisibility(View.INVISIBLE);
                    my_clinics_view.setVisibility(View.VISIBLE);
                    ll_calander.setVisibility(View.VISIBLE);
                    resideMenu.closeMenu();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new CalenderFragmentNew());
                    fragmentTransaction.commit();
                } else if (position == 3) {
                    empReturn.setVisibility(View.GONE);
                    empTab.setVisibility(View.GONE);
                    resideMenu.closeMenu();
                    ll_calander.setVisibility(View.GONE);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new TimeSheet());
                    fragmentTransaction.commit();
                } else if (position == 4) {
                    empReturn.setVisibility(View.GONE);
                    empTab.setVisibility(View.GONE);
                    ll_calander.setVisibility(View.GONE);
                    resideMenu.closeMenu();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new CertificateFragment());
                    fragmentTransaction.commit();
                } else if (position == 5) {
                    resideMenu.closeMenu();
                    empReturn.setVisibility(View.GONE);
                    empTab.setVisibility(View.GONE);
                    ll_calander.setVisibility(View.GONE);
                    resideMenu.closeMenu();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new ProfileFragment());
                    fragmentTransaction.commit();
                } else if (position == 6) {
                    resideMenu.closeMenu();
                    finish();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

        }));
*/

        delet_reside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.closeMenu();
                MainActivity.img_available.setImageResource(R.drawable.available_selector);
                MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
            }
        });
        findViewById(R.id.toolbar_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_calander_blue.setSelected(false);
                img_available.setSelected(false);
                MainActivity.img_available.setImageResource(R.drawable.available_selector);
                MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                ll_calander.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }


    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            // Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            // Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (AppUtills.isAppRunning(MainActivity.this, "professional.wellness.health.com.employeeapp")) {
            // App is running
        } else {
            //   notifier.cancel(1);
            // App is not running
        }

    }

    @Override
    public void onBackPressed() {
        if (SessionManager.getbackvalue(MainActivity.this).equals("available")){
            home();
        }else if(SessionManager.getbackvalue(MainActivity.this).equals("myclinic")){
            home();
        }else if(SessionManager.getbackvalue(MainActivity.this).equals("time_sheet")){
            home();
        }else if(SessionManager.getbackvalue(MainActivity.this).equals("certificate")){
            home();
        }else if(SessionManager.getbackvalue(MainActivity.this).equals("profile")){
            home();
        }else{
            alertDialogExit();
        }

    }
public void home(){
    try {
        SessionManager.setbackValue(MainActivity.this,"home_feed");
        available_icon.setVisibility(View.GONE);
        empReturn.setVisibility(View.GONE);
        empTab.setVisibility(View.VISIBLE);
        ll_calander.setVisibility(View.INVISIBLE);
        home_feeds_view.setVisibility(View.VISIBLE);
        available_view.setVisibility(View.INVISIBLE);
        my_clinics_view.setVisibility(View.INVISIBLE);
        resideMenu.closeMenu();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_container, new HomeFragment());
        fragmentTransaction.commit();
    }catch (Exception e){

    }


}

    public void alertDialogExit() {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_exit, null);

        RegularButton btn_exit = (RegularButton)view.findViewById(R.id.btn_exit);
        RegularButton btn_cancil = (RegularButton)view.findViewById(R.id.btn_cancil);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        btn_cancil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("data", "data");
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (savepos == 4) {
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof CertificateFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
        } else {
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof ProfileFragment) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }


        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));


        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));


        NotificationUtils.clearNotifications(getApplicationContext());
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void clearNotifications(Context context) {

        notificationManager.cancelAll();
    }


}
