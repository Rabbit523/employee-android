package professional.wellness.health.com.employeeapp.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.swipe.util.Attributes;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import professional.wellness.health.com.employeeapp.Adapter.AllAnnoucementAdapter;
import professional.wellness.health.com.employeeapp.Adapter.MyClinicUpcomingAdapter;
import professional.wellness.health.com.employeeapp.Adapter.RecyclerViewAdapter;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.AllAnnouncementList;
import professional.wellness.health.com.employeeapp.Model.HomeFeed;
import professional.wellness.health.com.employeeapp.Model.MyClinicUpcoming;
import professional.wellness.health.com.employeeapp.Model.ViewListHomeFeed;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.Constant;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.Utils.ClinicService;
import professional.wellness.health.com.employeeapp.Utils.MyLocationService;
import professional.wellness.health.com.employeeapp.View.FooRecyclerView;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.View.SpeedyLinearLayoutManager;
import professional.wellness.health.com.employeeapp.activity.LoginActivity;

/**
 * Created by Fujitsu on 13-07-2017.
 */

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewall;
    private RecyclerView recyclerView;
    private LinearLayout announcement;
    private LinearLayout empShowAllAnnouncement;
    private LinearLayout allFeed;
    private RESTInteraction restInteraction;
    private Context context;
    private EncryptionClass encryptionClass;
    private ArrayList<AllAnnouncementList> allAnnouncementLists;
    private ArrayList<HomeFeed> allAnnouncementListsLatest;
    private RecyclerViewAdapter mAdapter;
    private double latitude;
    private double longitude;
    public static ProgressDialog progressDialog;
    public static LinearLayout layout_main;
    public static LinearLayout layout_error;
    private RegularTextView text_error;
    private ArrayList<ViewListHomeFeed> othername_list;
    private LinearLayout viewMain;
    public static LinearLayout layout_upcoming_recycler;
    private RecyclerView upcoming_recycler;
    private ArrayList<String> clinic_upcoming_get_data = null;
    private ArrayList<MyClinicUpcoming> myclinic_upcoming_list;
    private ArrayList<String> myclinic_upcoming_listtemp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        context = getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {

        try {
            GPSTracker gps = new GPSTracker(getActivity());

            if (gps.canGetLocation()) {
                latitude = gps.getLatitude(); // returns latitude
                longitude = gps.getLongitude(); // returns longitude
            } else {
                gps.showSettingsAlert();
            }

        } catch (Exception e) {

        }


        try {
            context.startService(new Intent(context, ClinicService.class));
        } catch (Exception e) {

        }


        othername_list = new ArrayList<>();
        allAnnouncementLists = new ArrayList<>();
        clinic_upcoming_get_data = new ArrayList<>();
        allAnnouncementListsLatest = new ArrayList<>();
        myclinic_upcoming_list = new ArrayList<>();
        myclinic_upcoming_listtemp = new ArrayList<>();
        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);
        recyclerViewall = (RecyclerView) view.findViewById(R.id.recyclerViewall);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        allFeed = (LinearLayout) view.findViewById(R.id.allFeed);
        announcement = (LinearLayout) view.findViewById(R.id.announcement);
        empShowAllAnnouncement = (LinearLayout) view.findViewById(R.id.empShowAllAnnouncement);
        layout_main = (LinearLayout) view.findViewById(R.id.layout_main);
        layout_error = (LinearLayout) view.findViewById(R.id.layout_error);
        text_error = (RegularTextView) view.findViewById(R.id.text_error);

        viewMain = (LinearLayout) view.findViewById(R.id.view);

        layout_upcoming_recycler = (LinearLayout) view.findViewById(R.id.layout_upcoming_recycler);
        upcoming_recycler = (RecyclerView) view.findViewById(R.id.upcoming_recycler);


/*
            try {
                JSONObject object = new JSONObject();
                object.put("device_id", SessionManager.getDeviceId(getActivity()));
                object.put("platform_type", "android app");
                object.put("user_id", SessionManager.getuserId(getActivity()));
                object.put("latitude", latitude);
                object.put("longitude", longitude);
                String send_data = object.toString();
                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                new homeFeedAsync().execute(encrypted);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }*/


        if (AppUtills.isNetworkAvailable(getActivity())) {
            layout_upcoming_recycler.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject();
                     /*   object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);*/
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList = SessionManager.getofflinesyncdata(getActivity());
                ;
                String as = String.valueOf(arrayList);
                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(as));
                new syncDataOfflineAsync().execute(encrypted);
            } catch (JSONException ea) {

                layout_upcoming_recycler.setVisibility(View.GONE);
                try {
                    JSONObject objecthome = new JSONObject();
                    objecthome.put("device_id", SessionManager.getDeviceId(getActivity()));
                    objecthome.put("platform_type", "android app");
                    objecthome.put("user_id", SessionManager.getuserId(getActivity()));
                    objecthome.put("latitude", latitude);
                    objecthome.put("longitude", longitude);
                    String send_data = objecthome.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new homeFeedAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ea.printStackTrace();
            } catch (Exception ed) {
                layout_upcoming_recycler.setVisibility(View.GONE);
                try {
                    JSONObject objecthome = new JSONObject();
                    objecthome.put("device_id", SessionManager.getDeviceId(getActivity()));
                    objecthome.put("platform_type", "android app");
                    objecthome.put("user_id", SessionManager.getuserId(getActivity()));
                    objecthome.put("latitude", latitude);
                    objecthome.put("longitude", longitude);
                    String send_data = objecthome.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new homeFeedAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ed.printStackTrace();
            }
        } else {
            layout_upcoming_recycler.setVisibility(View.VISIBLE);
            layout_error.setVisibility(View.VISIBLE);
            layout_main.setVisibility(View.GONE);
            text_error.setText("No Internet Connection");

/*
            try {

                if (!SessionManager.getMyClinicUpcoming(getActivity()).equals("null")) {
                 //   clinic_upcoming_get_data = SessionManager.getMyClinicUpcoming(getActivity());
                }
            } catch (Exception eee) {
                eee.getStackTrace();
            }
*/


         //   for (int i = 0; i < clinic_upcoming_get_data.size(); i++) {


                try {
                    JSONObject object = new JSONObject(SessionManager.getMyClinicUpcoming(getActivity()));
                    if (object.has("status") && object.getString("status").equals("success")) {
                        layout_error.setVisibility(View.GONE);

                        if (object.has("clinics")) {
                            myclinic_upcoming_list.clear();
                            myclinic_upcoming_listtemp.clear();
                            JSONArray arrayupcoming = object.getJSONArray("clinics");
                            for (int j = 0; j < arrayupcoming.length(); j++) {
                                JSONObject objupcoming = arrayupcoming.getJSONObject(j);

                                try {


                                    if (!(SessionManager.getclockoutidofflinedata(context).size() == 0))
                                        for (int m = 0; m < SessionManager.getclockoutidofflinedata(getActivity()).size(); m++) {
                                            if (!SessionManager.getclockoutidofflinedata(getActivity()).get(m).equals(objupcoming.getString("id"))) {

                                                myclinic_upcoming_list.add(new MyClinicUpcoming(objupcoming.getString("id"), objupcoming.getString("provider_id")
                                                        , objupcoming.getString("name"), objupcoming.getString("phone"), objupcoming.getString("location_name"), objupcoming.getString("latitude"),
                                                        objupcoming.getString("longitude"), objupcoming.getString("time"), objupcoming.getString("prep_time"),
                                                        objupcoming.getString("date"), objupcoming.getString("prep_time_key"), objupcoming.getString("estimated_duration"), objupcoming.getString("personnel"),
                                                        objupcoming.getString("service_provider"), objupcoming.getString("number_of_provider"), objupcoming.getString("jay_walters"), objupcoming.getString("status")
                                                        , objupcoming.getString("created_at"), objupcoming.getString("updated_at"), objupcoming.getString("contact_name"), objupcoming.getString("primary_name"), objupcoming.getString("medtech_name"), objupcoming.getString("clocked"), objupcoming.getString("type"), objupcoming.getString("status_name"),
                                                        objupcoming.getString("duration"), objupcoming.getString("mileage_required"), objupcoming.getString("drive_time_required"), objupcoming.getString("mileage_status")
                                                        , objupcoming.getString("drive_time_status")
                                                ));


                                                if (objupcoming.has("other_name")) {
                                                    JSONArray array = objupcoming.getJSONArray("other_name");
                                                    for (int k = 0; k < array.length(); k++) {
                                                        JSONObject objother = array.getJSONObject(k);
                                                        othername_list.add(new ViewListHomeFeed(objupcoming.getString("id"), objother.getString("name")));

                                                    }


                                                }
                                            }
                                        }
                                    else {

                                        myclinic_upcoming_list.add(new MyClinicUpcoming(objupcoming.getString("id"), objupcoming.getString("provider_id")
                                                , objupcoming.getString("name"), objupcoming.getString("phone"), objupcoming.getString("location_name"), objupcoming.getString("latitude"),
                                                objupcoming.getString("longitude"), objupcoming.getString("time"), objupcoming.getString("prep_time"),
                                                objupcoming.getString("date"), objupcoming.getString("prep_time_key"), objupcoming.getString("estimated_duration"), objupcoming.getString("personnel"),
                                                objupcoming.getString("service_provider"), objupcoming.getString("number_of_provider"), objupcoming.getString("jay_walters"), objupcoming.getString("status")
                                                , objupcoming.getString("created_at"), objupcoming.getString("updated_at"), objupcoming.getString("contact_name"), objupcoming.getString("primary_name"), objupcoming.getString("medtech_name"), objupcoming.getString("clocked"), objupcoming.getString("type"), objupcoming.getString("status_name"),
                                                objupcoming.getString("duration"), objupcoming.getString("mileage_required"), objupcoming.getString("drive_time_required"), objupcoming.getString("mileage_status")
                                                , objupcoming.getString("drive_time_status")
                                        ));


                                        if (objupcoming.has("other_name")) {
                                            JSONArray array = objupcoming.getJSONArray("other_name");
                                            for (int k = 0; k < array.length(); k++) {
                                                JSONObject objother = array.getJSONObject(k);
                                                othername_list.add(new ViewListHomeFeed(objupcoming.getString("id"), objother.getString("name")));

                                            }


                                        }
                                    }

                                } catch (Exception e) {
                                    myclinic_upcoming_list.add(new MyClinicUpcoming(objupcoming.getString("id"), objupcoming.getString("provider_id")
                                            , objupcoming.getString("name"), objupcoming.getString("phone"), objupcoming.getString("location_name"), objupcoming.getString("latitude"),
                                            objupcoming.getString("longitude"), objupcoming.getString("time"), objupcoming.getString("prep_time"),
                                            objupcoming.getString("date"), objupcoming.getString("prep_time_key"), objupcoming.getString("estimated_duration"), objupcoming.getString("personnel"),
                                            objupcoming.getString("service_provider"), objupcoming.getString("number_of_provider"), objupcoming.getString("jay_walters"), objupcoming.getString("status")
                                            , objupcoming.getString("created_at"), objupcoming.getString("updated_at"), objupcoming.getString("contact_name"), objupcoming.getString("primary_name"), objupcoming.getString("medtech_name"), objupcoming.getString("clocked"), objupcoming.getString("type"), objupcoming.getString("status_name"),
                                            objupcoming.getString("duration"), objupcoming.getString("mileage_required"), objupcoming.getString("drive_time_required"), objupcoming.getString("mileage_status")
                                            , objupcoming.getString("drive_time_status")
                                    ));


                                    if (objupcoming.has("other_name")) {
                                        JSONArray array = objupcoming.getJSONArray("other_name");
                                        for (int k = 0; k < array.length(); k++) {
                                            JSONObject objother = array.getJSONObject(k);
                                            othername_list.add(new ViewListHomeFeed(objupcoming.getString("id"), objother.getString("name")));

                                        }


                                    }
                                }

                            }
                            for (int j = 0; j < myclinic_upcoming_list.size(); j++) {
                                myclinic_upcoming_listtemp.add(myclinic_upcoming_list.get(j).getId());
                            }
                            intializeRecyclerMyClinicUpcoming();

                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        layout_error.setVisibility(View.VISIBLE);
                        layout_upcoming_recycler.setVisibility(View.GONE);
                        text_error.setText(object.getString("message"));
                        //  alertDialog(object.getString("message"));
                    } else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity) context).finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            //}


        }


        empShowAllAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.empReturn.setVisibility(View.VISIBLE);
                MainActivity.empTab.setVisibility(View.GONE);
                allFeed.setVisibility(View.GONE);
                announcement.setVisibility(View.VISIBLE);

                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("platform_type", "android app");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new announcementAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });




       /* Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetID);
        startActivityForResult(pickIntent, KEY_CODE);*/

    }

    private void intializeRecyclerMyClinicUpcoming() {
        upcoming_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        upcoming_recycler.setItemAnimator(new DefaultItemAnimator());
        upcoming_recycler.setAdapter(new MyClinicUpcomingAdapter(getActivity(), myclinic_upcoming_list, othername_list, myclinic_upcoming_listtemp));
    }

    private class syncDataOfflineAsync extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // progressDialog = AppUtills.createProgressDialog(context);
            //  progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendSyncDataOfflineFeed(APIUrl.SYNCDATAOFFLINE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        try {
                            SessionManager.clearofflinesyncdata(getActivity());
                        }catch (Exception e){

                        }

                        try {
                            JSONObject objecthome = new JSONObject();
                            objecthome.put("device_id", SessionManager.getDeviceId(getActivity()));
                            objecthome.put("platform_type", "android app");
                            objecthome.put("user_id", SessionManager.getuserId(getActivity()));
                            objecthome.put("latitude", latitude);
                            objecthome.put("longitude", longitude);
                            String send_data = objecthome.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new homeFeedAsync().execute(encrypted);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (object.has("status") && object.getString("status").equals("error")) {

                        try {
                            JSONObject objecthome = new JSONObject();
                            objecthome.put("device_id", SessionManager.getDeviceId(getActivity()));
                            objecthome.put("platform_type", "android app");
                            objecthome.put("user_id", SessionManager.getuserId(getActivity()));
                            objecthome.put("latitude", latitude);
                            objecthome.put("longitude", longitude);
                            String send_data = objecthome.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new homeFeedAsync().execute(encrypted);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ee) {

                    try {
                        JSONObject objecthome = new JSONObject();
                        objecthome.put("device_id", SessionManager.getDeviceId(getActivity()));
                        objecthome.put("platform_type", "android app");
                        objecthome.put("user_id", SessionManager.getuserId(getActivity()));
                        objecthome.put("latitude", latitude);
                        objecthome.put("longitude", longitude);
                        String send_data = objecthome.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new homeFeedAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ee.printStackTrace();
                }

            } else {

                try {
                    JSONObject objecthome = new JSONObject();
                    objecthome.put("device_id", SessionManager.getDeviceId(getActivity()));
                    objecthome.put("platform_type", "android app");
                    objecthome.put("user_id", SessionManager.getuserId(getActivity()));
                    objecthome.put("latitude", latitude);
                    objecthome.put("longitude", longitude);
                    String send_data = objecthome.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new homeFeedAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    private class homeFeedAsync extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.getHomeFeed(APIUrl.HOMEFEED, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        //   SessionManager.clearSessionupcoming(getActivity());
                        SessionManager.clearclockoutidofflinedata(context);
                        SessionManager.clearclinicidoffline(context);
                        SessionManager.clearclinicTypeoffline(context);
                        if (!object.getString("image").equals("")) {
                            Picasso.with(getActivity())
                                    .load(object.getString("image"))
                                    .into(MainActivity.user_image);
                        }
                        String additional_message = object.getString("additional_message");
                        try {
                            String time_format = object.getString("time_format");
                            SessionManager.setTimeFormatValue(getActivity(), time_format);
                        } catch (Exception e) {

                        }

                        if (!additional_message.equals("")) {
                            alertDialogNoClinic(additional_message);
                        }
                        empShowAllAnnouncement.setVisibility(View.VISIBLE);
                        layout_error.setVisibility(View.GONE);
                        layout_main.setVisibility(View.VISIBLE);
                        allAnnouncementListsLatest.clear();
                        if (object.has("latest_announcement")) {
                            JSONArray anouncement = object.getJSONArray("latest_announcement");
                            for (int i = 0; i < anouncement.length(); i++) {
                                JSONObject obj = anouncement.getJSONObject(i);
                                allAnnouncementListsLatest.add(new HomeFeed(obj.has("id") ? obj.getString("id") : "",
                                        obj.has("title") ? obj.getString("title") : "", obj.has("image") ? obj.getString("image") : "",
                                        obj.has("description") ? obj.getString("description") : "", obj.has("visible_providers") ? obj.getString("visible_providers") : "",
                                        obj.has("visible_cities") ? obj.getString("visible_cities") : "", obj.has("notification_alert") ? obj.getString("notification_alert") : "",
                                        obj.has("email_alert") ? obj.getString("email_alert") : "", obj.has("stable_time") ? obj.getString("stable_time") : "",
                                        obj.has("status") ? obj.getString("status") : "", obj.has("created_at") ? obj.getString("created_at") : "",
                                        obj.has("updated_at") ? obj.getString("updated_at") : "", obj.has("primary_name") ? obj.getString("primary_name") : "",
                                        obj.has("medtech_name") ? obj.getString("medtech_name") : "",
                                        obj.has("image_path") ? obj.getString("image_path") : "", obj.has("provider_id") ? obj.getString("provider_id") : "",
                                        obj.has("name") ? obj.getString("name") : "", obj.has("phone") ? obj.getString("phone") : "", obj.has("location_name") ? obj.getString("location_name") : "",
                                        obj.has("latitude") ? obj.getString("latitude") : "", obj.has("longitude") ? obj.getString("longitude") : "", obj.has("time") ? obj.getString("time") : "",
                                        obj.has("date") ? obj.getString("date") : "", obj.has("clock_in") ? obj.getString("clock_in") : "", obj.has("clock_out") ? obj.getString("clock_out") : "",
                                        obj.has("type") ? obj.getString("type") : "anouncement",
                                        obj.has("prep_time") ? obj.getString("prep_time") : "",
                                        obj.has("estimated_duration") ? obj.getString("estimated_duration") : "",
                                        obj.has("personnel") ? obj.getString("personnel") : "",
                                        obj.has("service_provider") ? obj.getString("service_provider") : "",
                                        obj.has("jay_walters") ? obj.getString("jay_walters") : "",
                                        obj.has("clocked") ? obj.getString("clocked") : ""
                                ));
                            }

                        }
                        if (object.has("home_feeds")) {
                            JSONArray clincs = object.getJSONArray("home_feeds");
                            othername_list.clear();
                            for (int i = 0; i < clincs.length(); i++) {
                                JSONObject obj1 = clincs.getJSONObject(i);
                                allAnnouncementListsLatest.add(new HomeFeed(obj1.has("id") ? obj1.getString("id") : "",
                                        obj1.has("title") ? obj1.getString("title") : "", obj1.has("image") ? obj1.getString("image") : "",
                                        obj1.has("description") ? obj1.getString("description") : "", obj1.has("visible_providers") ? obj1.getString("visible_providers") : "",
                                        obj1.has("visible_cities") ? obj1.getString("visible_cities") : "", obj1.has("notification_alert") ? obj1.getString("notification_alert") : "",
                                        obj1.has("email_alert") ? obj1.getString("email_alert") : "", obj1.has("stable_time") ? obj1.getString("stable_time") : "",
                                        obj1.has("status") ? obj1.getString("status") : "", obj1.has("created_at") ? obj1.getString("created_at") : "",
                                        obj1.has("updated_at") ? obj1.getString("updated_at") : "", obj1.has("primary_name") ? obj1.getString("primary_name") : "",
                                        obj1.has("medtech_name") ? obj1.getString("medtech_name") : "",
                                        obj1.has("image_path") ? obj1.getString("image_path") : "", obj1.has("provider_id") ? obj1.getString("provider_id") : "",
                                        obj1.has("name") ? obj1.getString("name") : "", obj1.has("phone") ? obj1.getString("phone") : "", obj1.has("location_name") ? obj1.getString("location_name") : "",
                                        obj1.has("latitude") ? obj1.getString("latitude") : "", obj1.has("longitude") ? obj1.getString("longitude") : "", obj1.has("time") ? obj1.getString("time") : "",
                                        obj1.has("date") ? obj1.getString("date") : "", obj1.has("clock_in") ? obj1.getString("clock_in") : "", obj1.has("clock_out") ? obj1.getString("clock_out") : "",
                                        obj1.has("type") ? obj1.getString("type") : "",
                                        obj1.has("prep_time") ? obj1.getString("prep_time") : "",
                                        obj1.has("estimated_duration") ? obj1.getString("estimated_duration") : "",
                                        obj1.has("personnel") ? obj1.getString("personnel") : "",
                                        obj1.has("service_provider") ? obj1.getString("service_provider") : "",
                                        obj1.has("jay_walters") ? obj1.getString("jay_walters") : "",
                                        obj1.has("clocked") ? obj1.getString("clocked") : ""
                                ));

                                if (obj1.has("other_name")) {
                                    JSONArray array = obj1.getJSONArray("other_name");
                                    for (int j = 0; j < array.length(); j++) {
                                        JSONObject obj = array.getJSONObject(j);
                                        othername_list.add(new ViewListHomeFeed(obj1.has("id") ? obj1.getString("id") : ""
                                                , obj.has("name") ? obj.getString("name") : ""));
                                    }
                                }
                            }
                        }
                        intializeRecyclerLatest();

                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        progressDialog.dismiss();
                        // alertDialog(object.getString("message"));
                        layout_error.setVisibility(View.VISIBLE);
                        layout_main.setVisibility(View.GONE);
                        text_error.setText(object.getString("message"));
                    } else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity) context).finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    layout_error.setVisibility(View.VISIBLE);
                    layout_main.setVisibility(View.GONE);
                    text_error.setText("Server Error");
                    e.printStackTrace();
                }

            } else {
            }

        }
    }

    public void alertDialogNoClinic(String message) {
        try {
            final Snackbar snackbar = Snackbar.make(viewMain, message, (int) 5000L);
            snackbar.setAction("DISMISS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });

            snackbar.show();
        }catch (Exception e){

        }


    }

    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
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

    private void intializeRecyclerLatest() {
        recyclerView.setLayoutManager(new SpeedyLinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerViewAdapter(getActivity(), allAnnouncementListsLatest, othername_list);
        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);
    }

    private class announcementAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(context);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.getallannouncement(APIUrl.ALLANNOUNCEMENT, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        if (object.has("announcement")) {
                            allAnnouncementLists.clear();
                            JSONArray notes = object.getJSONArray("announcement");
                            for (int i = 0; i < notes.length(); i++) {
                                JSONObject childJsObj = notes.getJSONObject(i);
                                allAnnouncementLists.add(new AllAnnouncementList(childJsObj.getString("id"),
                                        childJsObj.getString("title"),
                                        childJsObj.getString("description"),
                                        childJsObj.getString("visible_providers"),
                                        childJsObj.getString("email_alert"),
                                        childJsObj.getString("notification_alert"),
                                        childJsObj.getString("status"),
                                        childJsObj.getString("stable_time"),
                                        childJsObj.getString("created_at"),
                                        childJsObj.getString("image_path")));

                            }
                            intializeRecycler();
                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
            }

        }
    }

    private void intializeRecycler() {
        recyclerViewall.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewall.setItemAnimator(new DefaultItemAnimator());
        recyclerViewall.setAdapter(new AllAnnoucementAdapter(context, allAnnouncementLists));

    }


}
