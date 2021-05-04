package professional.wellness.health.com.employeeapp.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.activity.SplashActivity;

/**
 * Created by Admin on 02-10-2017.
 */

public class ClinicService extends Service {

    String tag = "Service";
    Context context;
    RESTInteraction restInteraction;
    private EncryptionClass encryptionClass;
    private static final int TIMEINTERVAL = 5000;
    private ArrayList<String> clinic_upcoming_data;
    private ArrayList<String> clinic_week_data;
    private ArrayList<String> clinic_day_data;
    private ArrayList<String> clinic_month_data;
    Timer timer;
    TimerTask timerTask1;
    private boolean time_flag = false;
    Intent intentt;

    @Override
    public void onCreate() {
        super.onCreate();
        //Toast.makeText(this, "Service created...", Toast.LENGTH_LONG).show();
        Log.i(tag, "Service created...");
        context = this;
        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);
        clinic_upcoming_data = new ArrayList<>();
        clinic_week_data = new ArrayList<>();
        clinic_day_data = new ArrayList<>();
        clinic_month_data = new ArrayList<>();

        timer = new Timer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
 /*       if (db.getImageItems().size() == 0) {
            context.stopService(intent);
        } else {


        }*/

        intentt = intent;
        //intializedata();
        setTimer();
        timer.schedule(timerTask1, TIMEINTERVAL);
        Log.i(tag, "Service started...");
        return 0;
    }

    private void intializedata() {
            if (AppUtills.isNetworkAvailable(context)) {


                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(context));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(context));
                    object.put("latitude", "");
                    object.put("longitude", "");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    AsyTask web = new AsyTask();

                    web.execute(encrypted);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }




                //imageItems.remove(i);
            }


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

    private class AsyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.getMyClinicOfflineData(APIUrl.AllCLINICDATA, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // progressDialog.dismiss();
            if (!result.equals("")) {
                time_flag = true;

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        if(object.has("clinics")){
                            JSONObject jsonObject = object.getJSONObject("clinics");

                            if(jsonObject.has("upcoming")){
                                JSONArray array = jsonObject.getJSONArray("upcoming");
                                JSONObject  obj  = new JSONObject();
                                obj.put("status","success");
                                obj.put("message","ok");
                                obj.put("clinics",array);

                                clinic_upcoming_data.add(obj.toString());
                                try {
                                    SessionManager.addMyClinicUpcoming(context, obj.toString());
                                }catch (Exception e){

                                }



                            }

                            if(jsonObject.has("month")){
                                JSONArray array = jsonObject.getJSONArray("month");
                                JSONObject  obj  = new JSONObject();
                                obj.put("status","success");
                                obj.put("message","ok");
                                obj.put("clinics",array);
                                clinic_month_data.add(obj.toString());
                                try {
                                    SessionManager.addMyClinicMonth(context, obj.toString());
                                }catch (Exception e){

                                }

                            }
                            if(jsonObject.has("day")){
                                JSONArray array = jsonObject.getJSONArray("day");
                                JSONObject  obj  = new JSONObject();
                                obj.put("status","success");
                                obj.put("message","ok");
                                obj.put("clinics",array);

                                clinic_day_data.add(obj.toString());
                                try {
                                    SessionManager.addMyClinicDay(context, obj.toString());
                                }catch (Exception e){

                                }


                            }
                            if(jsonObject.has("week")){
                                JSONArray array = jsonObject.getJSONArray("week");
                                JSONObject  obj  = new JSONObject();
                                obj.put("status","success");
                                obj.put("message","ok");
                                obj.put("clinics",array);

                                clinic_week_data.add(obj.toString());
                                try {
                                    SessionManager.addMyClinicWeek(context, obj.toString());
                                }catch (Exception e){

                                }

                            }

                        }

                        context.stopService(intentt);
                    } else if (object.has("status") && object.getString("status").equals("error")) {

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            } else {

            }
        }

    }
}
