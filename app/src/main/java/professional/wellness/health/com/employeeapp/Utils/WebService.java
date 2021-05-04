package professional.wellness.health.com.employeeapp.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 30-08-2017.
 */

public class WebService extends Service {

    String tag = "Service";
    Activity activity;
    Context context;
    RESTInteraction restInteraction;
    private EncryptionClass encryptionClass;
    private double latitude;
    private double longitude;
    private static int TIMEINTERVAL = 5000;

    Timer timer;
    TimerTask timerTask1;
    private boolean time_flag = false;


    @Override
    public void onCreate() {
        super.onCreate();


        //Toast.makeText(this, "Service created...", Toast.LENGTH_LONG).show();
        Log.i(tag, "Service created...");
        context = this;

        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);
        timer = new Timer();

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (SessionManager.getStopService(context).equals("stop")) {
            context.stopService(intent);
        } else {

            //intializedata();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        if (!SessionManager.getStopService(context).equals("stop")) {
                            GPSTracker gps = new GPSTracker(context);

                            if (gps.canGetLocation()) {
                                latitude = gps.getLatitude(); // returns latitude
                                longitude = gps.getLongitude(); // returns longitude
                            } else {
                                gps.showSettingsAlert();
                            }
                            setTimer();

                            timer.schedule(timerTask1, TIMEINTERVAL);

                        try {
                            Thread.sleep(Integer.parseInt(SessionManager.getClockinTime(context)));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        }else
                            context.stopService(intent);
                    }

                }
            });
            t.start();


           // TIMEINTERVAL = Integer.parseInt(SessionManager.getClockinTime(context));
        }
        Log.i(tag, "Service started...");
        return START_STICKY;
    }

    private void intializedata() {


/*

        imageItems.clear();
        for (int i = 0; i < db.getImageItems().size(); i++) {
            imageItems.add(db.getImageItems().get(i));


            if (AppUtills.isNetworkAvailable(context)) {
                AsyTask web = new AsyTask();

                web.execute(imageItems.get(i).getNotes(), imageItems.get(i).getNotestype(), imageItems.get(i).getImage(), imageItems.get(i).getBooking_id(),imageItems.get(i).getId(),imageItems.get(i).getTimestamp());


                //imageItems.remove(i);
            }
        }*/

        MainActivity.activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(context));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(context));
                    object.put("clinic_id", SessionManager.getClinicid(context));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));

                      new locationUpdateAsync().execute(encrypted);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }


    void setTimer() {
        timerTask1 = new TimerTask() {
            @Override
            public void run() {
                time_flag = false;

                intializedata();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Toast.makeText(this, "Service destroyed...", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }



/*
    private class AsyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendPublicPrivateNotesRequestBackground(APIUrl.SAVEPUBLICPLIVATENOTES, params[0], params[1], params[2], params[3],params[4],params[5]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // progressDialog.dismiss();
            if (!result.equals("")) {
                time_flag = true;
            } else {

            }
        }

    }
*/

    private class locationUpdateAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
       /*     if (progressDialog == null) {
            progressDialog = AppUtills.createProgressDialog(context);
            progressDialog.show();
        } else {
            progressDialog.show();
        }*/
    }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdatelocationAsync(APIUrl.UPDATELOCATION , params[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
         ///   progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                      //  alertDialogSucess(jsonObject.getString("message"));

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                     //   alertDialog(jsonObject.getString("message"));
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_dialog_sucess, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
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
    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
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

}
