package professional.wellness.health.com.employeeapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import professional.wellness.health.com.employeeapp.Adapter.MyClinicDayAdapter;
import professional.wellness.health.com.employeeapp.Adapter.MyClinicUpcomingAdapter;
import professional.wellness.health.com.employeeapp.Adapter.RecyclerViewAdapter;
import professional.wellness.health.com.employeeapp.Adapter.ViewListAdapter;
import professional.wellness.health.com.employeeapp.Adapter.ViewListMonthDetailAdapter;
import professional.wellness.health.com.employeeapp.Adapter.ViewListUpcomingDetailAdapter;
import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.ViewList;
import professional.wellness.health.com.employeeapp.Model.ViewListHomeFeed;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.Utils.ClinicService;
import professional.wellness.health.com.employeeapp.Utils.MyLocationService;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularEditText;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.fragment.CalenderFragmentNew;
import professional.wellness.health.com.employeeapp.fragment.HomeFragment;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by Admin on 25-07-2017.
 */

public class MonthClinicDetailAcivity extends AppCompatActivity {
    private LinearLayout empReturn;
    private RegularTextView txt_name;
    private RegularTextView txt_prep_time;
    private RegularTextView txt_personal;
    private RegularTextView txt_estimate_duration;
    private RegularTextView txt_location;
    private RegularTextView txt_clinic_date;
    private RegularTextView txt_clinic_time;
    private RegularTextView phone_number;
    private RegularTextView jaywalters;
    private RegularTextView txt_service_provider;
    private RegularTextView past_name;
    private RegularTextView past_prep_time;
    private RegularTextView past_duration;
    private RegularTextView past_personal;
    private RegularTextView past_location;
    private RegularTextView past_date;
    private RegularTextView past_clinic_time;
    private RegularTextView past_service_provider;
    private RegularTextView milage;
    private RegularTextView drive_time;
    private RegularEditText edt_milage;
    private RegularEditText edt_drive_time;
    private RegularButton milage_submit;
    private String user_id = "";
    private String provider_id;
    private String name_clinic;
    private String phone;
    private String location_name;
    private String latitude;
    private String longitude;
    private String time;
    private String end_time;
    private String prep_time;
    private String default_unfilled_time;
    private String date;
    private String create_timestamp;
    private String estimated_duration;
    private String personnel;
    private String primary_provider;
    private String medtech_provider;
    private String other_provider;
    private String service_provider;
    private String number_of_provider;
    private String jay_walters;
    private String timezone;
    private String status;
    private String created_at;
    private String updated_at;
    private String primary_name;
    private String medtech_name;
    private String system_calender;
    private String type;
    private String status_name;
    private String duration;
    private String mileage_required;
    private String drive_time_required;
    private String mileage_status;
    private String drive_time_status;
    private RegularTextView txt_jaywalters_hide;
    private RegularTextView txt_midtech_hide;
    private CardView cardview_past_layout;
    private CardView cardview__my_clinics;
    private LinearLayout viewlist_open;
    private LinearLayout view_listupcoming;
    private LinearLayout dropdown_upcoming;
    private LinearLayout serviceProvider_upcoming;
    private LinearLayout hide_upcoming;
    private RecyclerView recycler_upcoming_viewlist;
    private LinearLayout milage_layout_filled;
    private LinearLayout milage_layout;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    ArrayList<ViewList> object;
    private RegularTextView empClockClinic;
    private RegularTextView empClockPrep;
    private RegularTextView empClockPersonnel;
    private RegularTextView empClockLocation;
    private RegularTextView empClockContact;
    private RegularTextView empClockDate;
    private RegularTextView empClockServiceProvided;
    private RegularTextView empClockPhone;
    private LinearLayout serviceProvider_clockin;
    private LinearLayout dropdown_clockin;
    private LinearLayout viewlist_open_clockin;
    private LinearLayout view_listclockin;
    private LinearLayout hide_clockin;
    private RegularTextView txt_jaywalters_hide_clockin;
    private RegularTextView txt_jaywalters_clockin;
    private RegularTextView txt_midtech_hide_clockin;
    private RecyclerView recycler_clockin_viewlist;
    private RegularButton empClockIn;
    private RegularButton empClockOut;
    private CardView layout_clockin;
    private double latitudecurrent;
    private double longitudecurrent;
    private String clinic_id = "";
    private String spend_time = "";
    private RegularTextView spend_timee;
    private ArrayList<String> clockout_id_data_offline;
    private String milage_value = "";
    private String drive_time_value = "";
    String date1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_itam_detail);
        init();
    }

    private void init() {
        clockout_id_data_offline = new ArrayList<>();
        GPSTracker gps = new GPSTracker(MonthClinicDetailAcivity.this);

        if (gps.canGetLocation()) {
            latitudecurrent = gps.getLatitude();
            longitudecurrent = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
        //clockout_id_data_offline = new ArrayList<>();
        restInteraction = RESTInteraction.getInstance(MonthClinicDetailAcivity.this);
        encryptionClass = EncryptionClass.getInstance(MonthClinicDetailAcivity.this);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");

        object = (ArrayList<ViewList>) args.getSerializable("ARRAYLIST");
        spend_timee = (RegularTextView) findViewById(R.id.spend_time);

        //clockin
        layout_clockin = (CardView) findViewById(R.id.layout_clockin);
        empClockClinic = (RegularTextView) findViewById(R.id.empClockClinic);
        empClockPrep = (RegularTextView) findViewById(R.id.empClockPrep);
        empClockPersonnel = (RegularTextView) findViewById(R.id.empClockPersonnel);
        empClockLocation = (RegularTextView) findViewById(R.id.empClockLocation);
        empClockContact = (RegularTextView) findViewById(R.id.empClockContact);
        empClockDate = (RegularTextView) findViewById(R.id.empClockDate);
        empClockServiceProvided = (RegularTextView) findViewById(R.id.empClockServiceProvided);
        empClockPhone = (RegularTextView) findViewById(R.id.empClockPhone);
        txt_jaywalters_hide_clockin = (RegularTextView) findViewById(R.id.txt_jaywalters_hide_clockin);
        txt_jaywalters_clockin = (RegularTextView) findViewById(R.id.txt_jaywalters_clockin);
        txt_midtech_hide_clockin = (RegularTextView) findViewById(R.id.txt_midtech_hide_clockin);
        empClockIn = (RegularButton) findViewById(R.id.empClockIn);
        empClockOut = (RegularButton) findViewById(R.id.empClockOut);
        recycler_clockin_viewlist = (RecyclerView) findViewById(R.id.recycler_clockin_viewlist);
        serviceProvider_clockin = (LinearLayout) findViewById(R.id.serviceProvider_clockin);
        dropdown_clockin = (LinearLayout) findViewById(R.id.dropdown_clockin);
        viewlist_open_clockin = (LinearLayout) findViewById(R.id.viewlist_open_clockin);
        view_listclockin = (LinearLayout) findViewById(R.id.view_listclockin);
        hide_clockin = (LinearLayout) findViewById(R.id.hide_clockin);


        empReturn = (LinearLayout) findViewById(R.id.empReturn);
        txt_name = (RegularTextView) findViewById(R.id.txt_name);
        txt_prep_time = (RegularTextView) findViewById(R.id.txt_prep_time);
        txt_personal = (RegularTextView) findViewById(R.id.txt_personal);
        txt_estimate_duration = (RegularTextView) findViewById(R.id.txt_estimate_duration);
        txt_location = (RegularTextView) findViewById(R.id.txt_location);
        txt_clinic_date = (RegularTextView) findViewById(R.id.txt_clinic_date);
        txt_clinic_time = (RegularTextView) findViewById(R.id.txt_clinic_time);
        phone_number = (RegularTextView) findViewById(R.id.phone_number);
        jaywalters = (RegularTextView) findViewById(R.id.jaywalters);
        txt_service_provider = (RegularTextView) findViewById(R.id.txt_service_provider);

        past_name = (RegularTextView) findViewById(R.id.past_name);
        past_prep_time = (RegularTextView) findViewById(R.id.past_prep_time);
        past_duration = (RegularTextView) findViewById(R.id.past_duration);
        past_personal = (RegularTextView) findViewById(R.id.past_personal);
        past_location = (RegularTextView) findViewById(R.id.past_location);
        txt_jaywalters_hide = (RegularTextView) findViewById(R.id.txt_jaywalters_hide);
        txt_midtech_hide = (RegularTextView) findViewById(R.id.txt_midtech_hide);
        past_date = (RegularTextView) findViewById(R.id.past_date);

        past_clinic_time = (RegularTextView) findViewById(R.id.past_clinic_time);
        past_service_provider = (RegularTextView) findViewById(R.id.past_service_provider);
        milage = (RegularTextView) findViewById(R.id.milage);
        drive_time = (RegularTextView) findViewById(R.id.drive_time);
        edt_milage = (RegularEditText) findViewById(R.id.edt_milage);
        edt_drive_time = (RegularEditText) findViewById(R.id.edt_drive_time);
        milage_submit = (RegularButton) findViewById(R.id.milage_submit);

        milage_layout_filled = (LinearLayout) findViewById(R.id.milage_layout_filled);

        milage_layout = (LinearLayout) findViewById(R.id.milage_layout);

        view_listupcoming = (LinearLayout) findViewById(R.id.view_listupcoming);
        dropdown_upcoming = (LinearLayout) findViewById(R.id.dropdown_upcoming);
        serviceProvider_upcoming = (LinearLayout) findViewById(R.id.serviceProvider_upcoming);
        hide_upcoming = (LinearLayout) findViewById(R.id.hide_upcoming);
        recycler_upcoming_viewlist = (RecyclerView) findViewById(R.id.recycler_upcoming_viewlist);

        user_id = getIntent().getStringExtra("id");
        provider_id = getIntent().getStringExtra("provider_id");
        name_clinic = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        location_name = getIntent().getStringExtra("location_name");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        time = getIntent().getStringExtra("time");
        end_time = getIntent().getStringExtra("end_time");
        prep_time = getIntent().getStringExtra("prep_time");
        default_unfilled_time = getIntent().getStringExtra("default_unfilled_time");
        date = getIntent().getStringExtra("date");
        parseDateToddMMyyyy(date);

        create_timestamp = getIntent().getStringExtra("create_timestamp");
        estimated_duration = getIntent().getStringExtra("estimated_duration");
        personnel = getIntent().getStringExtra("personnel");
        primary_provider = getIntent().getStringExtra("primary_provider");
        medtech_provider = getIntent().getStringExtra("medtech_provider");
        other_provider = getIntent().getStringExtra("other_provider");
        service_provider = getIntent().getStringExtra("service_provider");
        number_of_provider = getIntent().getStringExtra("number_of_provider");
        jay_walters = getIntent().getStringExtra("jay_walters");
        timezone = getIntent().getStringExtra("timezone");
        status = getIntent().getStringExtra("status");
        created_at = getIntent().getStringExtra("created_at");
        updated_at = getIntent().getStringExtra("updated_at");
        primary_name = getIntent().getStringExtra("primary_name");
        medtech_name = getIntent().getStringExtra("medtech_name");
        system_calender = getIntent().getStringExtra("system_calender");
        type = getIntent().getStringExtra("type");
        status_name = getIntent().getStringExtra("status_name");
        duration = getIntent().getStringExtra("duration");
        mileage_required = getIntent().getStringExtra("mileage_required");
        drive_time_required = getIntent().getStringExtra("drive_time_required");
        mileage_status = getIntent().getStringExtra("mileage_status");
        drive_time_status = getIntent().getStringExtra("drive_time_status");
        viewlist_open = (LinearLayout) findViewById(R.id.viewlist_open);

        spend_time = getIntent().getStringExtra("spend_time");
        spend_timee.setText(spend_time);
        txt_name.setText(name_clinic);
        txt_prep_time.setText("Prep time " + prep_time);
        txt_personal.setText("Personnel : " + personnel);
        txt_estimate_duration.setText("Estimate duration : " + estimated_duration);
        txt_location.setText(location_name);



        txt_clinic_date.setText("Date : " + date1);
        txt_clinic_time.setText("Clinic Time : " + time);
        phone_number.setText(phone);
        jaywalters.setText(primary_name);
        txt_service_provider.setText(service_provider);
        txt_jaywalters_hide.setText(primary_name);
        txt_midtech_hide.setText(medtech_name);

        past_name.setText(name_clinic);
        past_prep_time.setText(prep_time);
        past_duration.setText("Duration : " + duration);
        past_personal.setText("Personnel : " + personnel);
        past_location.setText(location_name);
        past_date.setText(date1);
        past_clinic_time.setText(time);
        past_service_provider.setText(service_provider);


        milage_value = getIntent().getStringExtra("mileage");
        drive_time_value = getIntent().getStringExtra("drive_time");
        milage.setText(milage_value);
        drive_time.setText(drive_time_value);

        empReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cardview_past_layout = (CardView) findViewById(R.id.cardview_past_layout);
        cardview__my_clinics = (CardView) findViewById(R.id.cardview__my_clinics);
        if (!AppUtills.isNetworkAvailable(MonthClinicDetailAcivity.this)){
        ArrayList<String> arrayList;
        try {
            arrayList = new ArrayList<>();
                arrayList = SessionManager.getclockoutidofflinedata(MonthClinicDetailAcivity.this);
                for (int i = 0; i < arrayList.size(); i++) {
                    if (user_id.equals(arrayList.get(i))){
                        type = "Past";
                        break;
                    }
                    else type = getIntent().getStringExtra("type");
                }





        } catch (Exception e) {
            /*if (user_id.equals(SessionManager.getclinicidofflinenew(MonthClinicDetailAcivity.this))) {*/
            type = getIntent().getStringExtra("type");
           /* }*/
        }
        }

        if (type.equals("Past")) {
            cardview_past_layout.setVisibility(View.VISIBLE);
            cardview__my_clinics.setVisibility(View.GONE);
            layout_clockin.setVisibility(View.GONE);
        } else if (type.equals("clock_in")) {
            cardview_past_layout.setVisibility(View.GONE);
            cardview__my_clinics.setVisibility(View.GONE);
            layout_clockin.setVisibility(View.VISIBLE);
            empClockClinic.setText("Clinic time: " + time);
            empClockPrep.setText("Prep time " + prep_time);
            empClockPersonnel.setText("Personnel : " + personnel);
            empClockLocation.setText(location_name);
          /*  empClockContact.setText(name);*/
            empClockDate.setText("Date: " + date1);
            empClockServiceProvided.setText(service_provider);
            //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
            empClockPhone.setText(phone);
        } else {
            cardview_past_layout.setVisibility(View.GONE);
            cardview__my_clinics.setVisibility(View.VISIBLE);
            layout_clockin.setVisibility(View.GONE);
        }

        //clockin
        txt_jaywalters_hide_clockin.setText(primary_name);
        txt_jaywalters_clockin.setText(primary_name);
        txt_midtech_hide_clockin.setText(medtech_name);

        viewlist_open_clockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceProvider_clockin.setVisibility(View.GONE);
                dropdown_clockin.setVisibility(View.VISIBLE);
                view_listclockin.setVisibility(View.GONE);


                //intializeRecyclerViewListClockin(holder);
                intializeRecyclerClockin();
            }
        });

        hide_clockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serviceProvider_clockin.setVisibility(View.VISIBLE);
                dropdown_clockin.setVisibility(View.GONE);
                view_listclockin.setVisibility(View.VISIBLE);
            }
        });


        String clocked = getIntent().getStringExtra("clocked");

        if (!AppUtills.isNetworkAvailable(MonthClinicDetailAcivity.this)) {
            if (SessionManager.getclinicidoffline(MonthClinicDetailAcivity.this).equals(user_id)) {
                if (SessionManager.getclinicTypeoffline(MonthClinicDetailAcivity.this).equals("clock_out")) {

                    clocked = "0";
                } else {

                    clocked = "1";
                }

            } else {


            }


            if (clocked.equals("1")) {
                empClockIn.setVisibility(View.VISIBLE);
                empClockOut.setVisibility(View.INVISIBLE);
            }
            else {
                empClockIn.setVisibility(View.INVISIBLE);
                empClockOut.setVisibility(View.VISIBLE);
            }
        } else {
            if (getIntent().getStringExtra("clocked").equals("1")) {
                empClockIn.setVisibility(View.VISIBLE);
                empClockOut.setVisibility(View.INVISIBLE);
            } else {
                empClockIn.setVisibility(View.INVISIBLE);
                empClockOut.setVisibility(View.VISIBLE);
            }

        }


        empClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clinic_id = user_id;

                if (!AppUtills.isNetworkAvailable(MonthClinicDetailAcivity.this)) {
                    try {
                        MainActivity.notification();
                    }catch (Exception e){

                    }
                    alertDialogofflineClockin(clinic_id);
                } else {
                    try {
                        MainActivity.notification();
                    }catch (Exception e){

                    }
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(MonthClinicDetailAcivity.this));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(MonthClinicDetailAcivity.this));
                        object.put("latitude", latitudecurrent);
                        object.put("longitude", longitudecurrent);
                        object.put("clinic_id", user_id);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new updateClockinAsync().execute(encrypted);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        empClockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!AppUtills.isNetworkAvailable(MonthClinicDetailAcivity.this)) {
                    clinic_id = user_id;
                    alertDialogMilage(clinic_id);
                    MainActivity.clearNotifications(getApplicationContext());
                } else {
                    try {

                        MainActivity.clearNotifications(getApplicationContext());
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(MonthClinicDetailAcivity.this));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(MonthClinicDetailAcivity.this));
                        object.put("latitude", latitudecurrent);
                        object.put("longitude", longitudecurrent);
                        object.put("clinic_id", user_id);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new updateClockOutAsync().execute(encrypted);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }
        });


        if (mileage_status.equals("1")) {
            milage_layout.setVisibility(View.GONE);
            milage_layout_filled.setVisibility(View.VISIBLE);
        } else if (drive_time_status.equals("1")) {
            milage_layout.setVisibility(View.GONE);
            milage_layout_filled.setVisibility(View.VISIBLE);
        } else {
            milage_layout.setVisibility(View.VISIBLE);
            milage_layout_filled.setVisibility(View.GONE);
        }


        milage_submit.setOnClickListener(new View.OnClickListener() {

            String clinic_id = user_id;

            @Override
            public void onClick(View view) {
                if (edt_milage.getText().toString().length() == 0) {
                    alertDialog("PLease Enter Milage (RT)");
                } else if (edt_drive_time.getText().toString().length() == 0) {
                    alertDialog("PLease Enter Drive time (RT)");
                } else {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(MonthClinicDetailAcivity.this));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(MonthClinicDetailAcivity.this));
                        object.put("clinic_id", clinic_id);
                        object.put("mileage_required", edt_milage.getText().toString().trim());
                        object.put("drive_time_required", edt_drive_time.getText().toString().trim());
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));

                        new UpdateClinicMilage().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }
        });


        viewlist_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_listupcoming.setVisibility(View.GONE);
                dropdown_upcoming.setVisibility(View.VISIBLE);
                serviceProvider_upcoming.setVisibility(View.GONE);


                intializeRecyclerViewList();
            }
        });

        hide_upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_listupcoming.setVisibility(View.VISIBLE);
                dropdown_upcoming.setVisibility(View.GONE);
                serviceProvider_upcoming.setVisibility(View.VISIBLE);
            }
        });


    }


    public void alertDialogofflineClockin(final String clinic_id) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(MonthClinicDetailAcivity.this);
        View view = inflater.inflate(R.layout.dialog_offline_clockin, null);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(MonthClinicDetailAcivity.this)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btn_confirm = (RegularButton) view.findViewById(R.id.btn_confirm);
        RegularButton btn_cancil = (RegularButton) view.findViewById(R.id.btn_cancil);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    startService(new Intent(MonthClinicDetailAcivity.this,ClinicService.class));
                }catch (Exception e){

                }
                final long clockin_timestamp = System.currentTimeMillis() / 1000;

                empClockIn.setVisibility(View.INVISIBLE);
                empClockOut.setVisibility(View.VISIBLE);
                 try {
                     SessionManager.setclinicidoffline(MonthClinicDetailAcivity.this, clinic_id);
                 }catch (Exception e){

                 }
                try {
                    SessionManager.setclinictypeoffline(MonthClinicDetailAcivity.this, "clock_out");
                }catch (Exception e){

                }

             /*   JSONArray jsonArraygift = new JSONArray();
                jsonArraygift.put(clinic_id);*/

                ArrayList<String> arrayList;
                arrayList = new ArrayList<String>();
                JSONObject object = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject jObj = new JSONObject();
                // if (!(SessionManager.getclockoutidofflinedata(MonthClinicDetailAcivity.this).size() == 0))

                try {
                    arrayList = SessionManager.getofflinesyncdata(MonthClinicDetailAcivity.this);
                }catch (Exception e){

                }

                try {
                    jObj.put("user_id", SessionManager.getuserId(MonthClinicDetailAcivity.this));
                    jObj.put("clinic_id", clinic_id);
                    jObj.put("Type", "clock_in");
                    jObj.put("Time_clockout", "");
                    jObj.put("Time_clockin", clockin_timestamp);
                    jObj.put("mileage", "");
                    jObj.put("drive_time", "");
                    jsonArray.put(jObj);
                    object.put("data", jsonArray);
                    arrayList.add(jObj.toString());
                    SessionManager.addofflinesyncdata(MonthClinicDetailAcivity.this, arrayList);
                    //   finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
            }
        });

        btn_cancil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void alertDialogMilage(final String clinic_id) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(MonthClinicDetailAcivity.this);
        View view = inflater.inflate(R.layout.dialog_milege, null);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(MonthClinicDetailAcivity.this)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();

        RegularButton milage_submit = (RegularButton) view.findViewById(R.id.milage_submit);
        RegularButton cancel = (RegularButton) view.findViewById(R.id.cancel);
        final RegularEditText edt_milage = (RegularEditText) view.findViewById(R.id.edt_milage);
        final RegularEditText edt_drive_time = (RegularEditText) view.findViewById(R.id.edt_drive_time);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long clockin_timestamp = System.currentTimeMillis() / 1000;
                SessionManager.setclinicidoffline(MonthClinicDetailAcivity.this, "");
                SessionManager.setclinictypeoffline(MonthClinicDetailAcivity.this, "");
                clockout_id_data_offline.add(clinic_id);
                try {
                    SessionManager.addclockoutidofflinedata(MonthClinicDetailAcivity.this, clockout_id_data_offline);
                }catch (Exception e){

                }

                try {
                    SessionManager.setclinicidofflinenew(MonthClinicDetailAcivity.this, clinic_id);
                }catch (Exception e){

                }




                ArrayList<String> arrayList;
                arrayList = new ArrayList<String>();
                JSONObject object = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject jObj = new JSONObject();
                try {
                    arrayList = SessionManager.getofflinesyncdata(MonthClinicDetailAcivity.this);
                }catch (Exception e){

                }

                try {
                    jObj.put("user_id", SessionManager.getuserId(MonthClinicDetailAcivity.this));
                    jObj.put("clinic_id", user_id);
                    jObj.put("Type", "clock_out");
                    jObj.put("Time_clockout", clockin_timestamp);
                    jObj.put("Time_clockin", "");
                    jObj.put("mileage", edt_milage.getText().toString());
                    jObj.put("drive_time", edt_drive_time.getText().toString());
                    jsonArray.put(jObj);
                    object.put("data", jsonArray);
                    arrayList.add(jObj.toString());
                    SessionManager.addofflinesyncdata(MonthClinicDetailAcivity.this, arrayList);

                    finish();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();

            }
        });

        milage_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_milage.getText().toString().length() == 0) {
                    alertDialog("Enter Mileage");
                } else if (edt_drive_time.getText().toString().length() == 0) {
                    alertDialog("Enter Drive Time");
                } else {
                    final long clockin_timestamp = System.currentTimeMillis() / 1000;
                    SessionManager.setclinicidoffline(MonthClinicDetailAcivity.this, "");
                    SessionManager.setclinictypeoffline(MonthClinicDetailAcivity.this, "");
                    // SessionManager.setclockoutidoffline(context,clinic_id);
                    clockout_id_data_offline.add(clinic_id);
                    try {
                        SessionManager.addclockoutidofflinedata(MonthClinicDetailAcivity.this, clockout_id_data_offline);
                    }catch (Exception e){

                    }
                    try {
                        SessionManager.setclinicidofflinenew(MonthClinicDetailAcivity.this, clinic_id);
                    }catch (Exception e){

                    }


                  /*  JSONArray jsonArraygift = new JSONArray();
                    jsonArraygift.put(clinic_id);*/
                    ArrayList<String> arrayList;
                    arrayList = new ArrayList<String>();
                    JSONObject object = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jObj = new JSONObject();
                    try {
                        arrayList = SessionManager.getofflinesyncdata(MonthClinicDetailAcivity.this);
                    }catch (Exception e){

                    }

                    try {
                        jObj.put("user_id", SessionManager.getuserId(MonthClinicDetailAcivity.this));
                        jObj.put("clinic_id", user_id);
                        jObj.put("Type", "clock_out");
                        jObj.put("Time_clockout", clockin_timestamp);
                        jObj.put("Time_clockin", "");
                        jObj.put("mileage", edt_milage.getText().toString());
                        jObj.put("drive_time", edt_drive_time.getText().toString());
                        jsonArray.put(jObj);
                        object.put("data", jsonArray);
                        arrayList.add(jObj.toString());
                        SessionManager.addofflinesyncdata(MonthClinicDetailAcivity.this, arrayList);

                        try {
                            MonthClinicDetailAcivity.this.startService(new Intent(MonthClinicDetailAcivity.this,ClinicService.class));
                        }catch (Exception e){

                        }
                        finish();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();


                }

            }
        });
    }


    private class UpdateClinicMilage extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(MonthClinicDetailAcivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateClinicMilage(APIUrl.UPDATECLINICMILAGE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        milage_layout.setVisibility(View.GONE);
                        milage_layout_filled.setVisibility(View.VISIBLE);
                        milage.setText(edt_milage.getText().toString().trim());
                        drive_time.setText(edt_drive_time.getText().toString().trim());
                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        alertDialog(object.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
            }

        }
    }


    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(MonthClinicDetailAcivity.this);
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(MonthClinicDetailAcivity.this)
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

    private void intializeRecyclerViewList() {
        recycler_upcoming_viewlist.setLayoutManager(new LinearLayoutManager(MonthClinicDetailAcivity.this));
        recycler_upcoming_viewlist.setItemAnimator(new DefaultItemAnimator());
        recycler_upcoming_viewlist.setAdapter(new ViewListMonthDetailAdapter(MonthClinicDetailAcivity.this, object, user_id));


    }


    private void intializeRecyclerClockin() {
        recycler_clockin_viewlist.setLayoutManager(new LinearLayoutManager(MonthClinicDetailAcivity.this));
        recycler_clockin_viewlist.setItemAnimator(new DefaultItemAnimator());
        recycler_clockin_viewlist.setAdapter(new ViewListUpcomingDetailAdapter(MonthClinicDetailAcivity.this, object, user_id));

    }


    private class updateClockinAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(MonthClinicDetailAcivity.this);
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
                        empClockIn.setVisibility(View.INVISIBLE);
                        empClockOut.setVisibility(View.VISIBLE);
                        SessionManager.setClinicid(MonthClinicDetailAcivity.this, clinic_id);
                        SessionManager.setClockinTime(MonthClinicDetailAcivity.this, jsonObject.getString("data"));
                        SessionManager.setStopService(MonthClinicDetailAcivity.this, "aaa");

                        if (!SessionManager.getStopService(MonthClinicDetailAcivity.this).equals("stop")) {
                            SessionManager.setClinicid(MonthClinicDetailAcivity.this, SessionManager.getClinicid(MonthClinicDetailAcivity.this));
                            MonthClinicDetailAcivity.this.startService(new Intent(MonthClinicDetailAcivity.this, MyLocationService.class));


                        }

                        try {
                            startService(new Intent(MonthClinicDetailAcivity.this,ClinicService.class));
                        }catch (Exception e){

                        }

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

    private class updateClockOutAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(MonthClinicDetailAcivity.this);
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
                        SessionManager.setStopService(MonthClinicDetailAcivity.this, "stop");
                        MonthClinicDetailAcivity.this.stopService(new Intent(MonthClinicDetailAcivity.this, MyLocationService.class));


                        try {
                            startService(new Intent(MonthClinicDetailAcivity.this,ClinicService.class));
                        }catch (Exception e){

                        }
                        SessionManager.clearClinicid(MonthClinicDetailAcivity.this);

                        alertDialogSucessClockout(jsonObject.getString("message"));

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

    public void alertDialogSucess(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(MonthClinicDetailAcivity.this);
        View view = inflater.inflate(R.layout.alert_dialog_sucess, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(MonthClinicDetailAcivity.this)
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


    public void alertDialogSucessClockout(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(MonthClinicDetailAcivity.this);
        View view = inflater.inflate(R.layout.alert_dialog_sucess, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(MonthClinicDetailAcivity.this)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empClockIn.setVisibility(View.INVISIBLE);
                empClockOut.setVisibility(View.INVISIBLE);
                finish();
                dialog.dismiss();
            }
        });
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "MM-dd-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            date1 = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
