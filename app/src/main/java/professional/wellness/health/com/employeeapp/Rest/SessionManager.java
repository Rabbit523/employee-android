package professional.wellness.health.com.employeeapp.Rest;


import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    // Set user Info
    public static void setUserInfo(Context context, String userID, String title, String email
            , String phone, String address, String social_security_number, String provider_type, String hourly_rate, String max_hours
            , String status) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("userID", userID);
        prefsEditor.putString("title", title);
        prefsEditor.putString("email", email);
        prefsEditor.putString("phone", phone);
        prefsEditor.putString("address", address);
        prefsEditor.putString("social_security_number", social_security_number);
        prefsEditor.putString("provider_type", provider_type);
        prefsEditor.putString("hourly_rate", hourly_rate);
        prefsEditor.putString("max_hours", max_hours);
        prefsEditor.putString("status", status);
        prefsEditor.commit();
    }



    // Set userprofile Info
    public static void setUserProfile(Context context, String firstname, String lastname, String degignation, String description
            , String useraddress) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("Profilefirstname", firstname);
        prefsEditor.putString("Profilelastname", lastname);
        prefsEditor.putString("Profiledegignation", degignation);
        prefsEditor.putString("Profiledescription", description);
        prefsEditor.putString("Profileuseraddress", useraddress);
        prefsEditor.commit();
    }

    public static String getuserProfileFirstname(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("Profilefirstname", "");
    }

    public static String getuserProfilelastname(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("Profilelastname", "");
    }

    public static String getuserProfiledesgignation(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("Profiledegignation", "");
    }

    public static String getuserProfiledescription(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("Profiledescription", "");
    }


    public static String getuserProfileaddress(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("Profileuseraddress", "");
    }


    public static String getuserId(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("userID", "");
    }

    public static void setfirstName(Context context, String first_name) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("first_name", first_name);
        prefsEditor.commit();
    }

    public static String getfirstName(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("first_name", "");
    }

    public static void setlastName(Context context, String last_name) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("last_name", last_name);
        prefsEditor.commit();
    }

    public static String getlastName(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("last_name", "");
    }

    public static String getTitle(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("title", "");
    }

    public static String getEmail(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("email", "");
    }

    public static String getPhone(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("phone", "");
    }

    public static String getAddress(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("address", "");
    }

    public static String getsocialsecurityNumber(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("social_security_number", "");
    }

    public static String getproviderType(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("provider_type", "");
    }

    public static String gethourlyRate(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("hourly_rate", "");
    }

    public static String getmaxHours(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("max_hours", "");
    }

    public static void setImage(Context context, String image) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("image", image);
        prefsEditor.commit();
    }

    public static String getImage(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("image", "");
    }

    public static void clearImage(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("image").commit();

    }

    public static String getStatus(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("status", "");
    }

    public static String getConnectionId(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("ConnectionId", "");
    }


    // clear all shared preference data after logout
    public static void clearSession(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("userID").commit();

    }


    public static void setDeviceID(Context context, String device_id) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("device_id", device_id);
        prefsEditor.commit();
    }

    // get couple image
    public static String getDeviceId(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("device_id", "");
    }


    public static void setUserfirstname(Context context, String userfirstname) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("userfirstname", userfirstname);
        prefsEditor.commit();
    }

    // get couple image
    public static String getUserfirstname(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("userfirstname", "");
    }


    public static void setUserlastname(Context context, String userlastname) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("userlastname", userlastname);
        prefsEditor.commit();
    }

    // get couple image
    public static String getUserlastname(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("userlastname", "");
    }

    public static void setUserdesgination(Context context, String desgination) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("desgination", desgination);
        prefsEditor.commit();
    }

    // get couple image
    public static String getUserdesgination(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("desgination", "");
    }

    public static void setUserdescription(Context context, String description) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("description", description);
        prefsEditor.commit();
    }

    // get couple image
    public static String getUserdescription(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("description", "");
    }

    public static void setUseraddress(Context context, String address) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("address", address);
        prefsEditor.commit();
    }

    // get couple image
    public static String getUseraddress(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("address", "");
    }


    public static void setToday(Context context, String today) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("today", today);
        prefsEditor.commit();
    }

    public static String getToday(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("today", "");
    }

    public static void clearToday(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("today").commit();

    }


    public static void setClinicid(Context context, String clinic_id) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("clinic_id", clinic_id);
        prefsEditor.commit();
    }

    public static String getClinicid(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("clinic_id", "");
    }

    public static void clearClinicid(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("clinic_id").commit();

    }
    public static void setClockinTime(Context context, String clockin_time) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("clockin_time", clockin_time);
        prefsEditor.commit();
    }

    public static String getClockinTime(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("clockin_time", "");
    }


    public static void setStopService(Context context, String stop_service) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("stop_service", stop_service);
        prefsEditor.commit();
    }

    public static String getStopService(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("stop_service", "");
    }


    public static void addCart(Context context, ArrayList<String> cart_data) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        List<String> textList = cart_data;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("cart", jsonText);
        prefsEditor.commit();
    }


    // get Cart Data
    public static ArrayList<String> getCart(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonText = mPrefs.getString("cart", null);
        String[] text = gson.fromJson(jsonText, String[].class);
        ArrayList<String> getCarts = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            getCarts.add(text[i]);
        }
        return getCarts;
    }


    public static void setClinicViewId(Context context, String view_id) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("view_id", view_id);
        prefsEditor.commit();
    }

    public static String getClinicViewId(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("view_id", "");
    }


    public static void setAvailableRefreshApiValue(Context context, String id_refresh) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("id_refresh", id_refresh);
        prefsEditor.commit();
    }

    public static String getAvailableRefreshApiValue(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("id_refresh", "");
    }


   /* public static void setMyClinicMonthnameValue(Context context, String month_name){
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("month_name", month_name);
        prefsEditor.commit();
    }

    public static String getMyClinicMonthnameValue(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("month_name", "");
    }*/

    public static void setMyClinicMonthdateValue(Context context, String month_date) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("month_date", month_date);
        prefsEditor.commit();
    }

    public static String getMyClinicMonthdateValue(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("month_date", "");
    }

    public static void setMyClinicMonthnameValue(Context context, String month) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        // prefsEditor.putInt("month",month)
        prefsEditor.commit();

    }

    public static void addMyClinicMonth(Context context,String jsonText) {
       /* android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        List<String> textList = monthclinic_data;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("monthmyclinic", jsonText);
        prefsEditor.commit();*/

        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("monthmyclinic", jsonText);
        prefsEditor.commit();
    }
    public static String getMyClinicMonth(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("monthmyclinic", "");
    }

   /* public static ArrayList<String> getMyClinicMonth(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonText = mPrefs.getString("monthmyclinic", null);
        String[] text = gson.fromJson(jsonText, String[].class);
        ArrayList<String> getCarts = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            getCarts.add(text[i]);
        }
        return getCarts;
    }*/


    public static void clearSessionMonth(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("monthmyclinic").commit();

    }


    // set Cart Data
    public static void addMyClinicUpcoming(Context context, String upcomingclinic_data) {
       /* android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        List<String> textList = upcomingclinic_data;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("upcomingclinic_data", jsonText);
        prefsEditor.commit();*/

        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("upcomingclinic_data", upcomingclinic_data);
        prefsEditor.commit();
    }
    public static String getMyClinicUpcoming(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("upcomingclinic_data", "");
    }

    // get Cart Data
/*
    public static ArrayList<String> getMyClinicUpcoming(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonText = mPrefs.getString("upcomingclinic_data", null);
        String[] text = gson.fromJson(jsonText, String[].class);
        ArrayList<String> getCarts = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            getCarts.add(text[i]);
        }
        return getCarts;
    }
*/

    public static void clearSessionupcoming(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("upcomingclinic_data").commit();

    }


    public static void addMyClinicWeek(Context context, String WeekMyclinic_data) {
       /* android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        List<String> textList = WeekMyclinic_data;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("WeekMyclinic_data", jsonText);
        prefsEditor.commit();*/

        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("WeekMyclinic_data", WeekMyclinic_data);
        prefsEditor.commit();
    }
    public static String getMyClinicWeek(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("WeekMyclinic_data", "");
    }

/*
    public static ArrayList<String> getMyClinicWeek(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonText = mPrefs.getString("WeekMyclinic_data", null);
        String[] text = gson.fromJson(jsonText, String[].class);
        ArrayList<String> getCarts = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            getCarts.add(text[i]);
        }
        return getCarts;
    }
*/

    public static void clearSessionweek(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("WeekMyclinic_data").commit();

    }


    public static void addMyClinicDay(Context context, String DayMyclinic_data) {
       /* android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        List<String> textList = DayMyclinic_data;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("DayMyclinic_data", jsonText);
        prefsEditor.commit();*/

        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("DayMyclinic_data", DayMyclinic_data);
        prefsEditor.commit();
    }

    public static String getMyClinicDay(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("DayMyclinic_data", "");
    }

/*
    public static ArrayList<String> getMyClinicDay(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonText = mPrefs.getString("DayMyclinic_data", null);
        String[] text = gson.fromJson(jsonText, String[].class);
        ArrayList<String> getCarts = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            getCarts.add(text[i]);
        }
        return getCarts;
    }
*/

    public static void clearSessionday(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("DayMyclinic_data").commit();

    }


    public static void setTimeFormatValue(Context context, String time_format) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("time_format", time_format);
        prefsEditor.commit();
    }

    public static String getTimeFormatValue(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("time_format", "");
    }


    public static void setclinicidoffline(Context context, String clinic_id_offline) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("clinic_id_offline", clinic_id_offline);
        prefsEditor.commit();
    }

    public static String getclinicidoffline(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("clinic_id_offline", "");
    }

    public static void clearclinicidoffline(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("clinic_id_offline").commit();

    }


    public static void setclockoutidoffline(Context context, String clockout_id_offline) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("clockout_id_offline", clockout_id_offline);
        prefsEditor.commit();
    }

    public static String getclockoutidoffline(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("clockout_id_offline", "");
    }


    public static void setclinictypeoffline(Context context, String clinic_type_offline) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("clinic_type_offline", clinic_type_offline);
        prefsEditor.commit();
    }

    public static String getclinicTypeoffline(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("clinic_type_offline", "");
    }

    public static void clearclinicTypeoffline(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("clinic_type_offline").commit();

    }


   /* // set Cart Data
    public static void addofflinesyncdata(Context context, JSONArray offline_sync_data) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        JSONArray textList = offline_sync_data;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("offline_sync_data", jsonText);
        prefsEditor.commit();
    }



    // get Cart Data
    public static String getofflinesyncdata(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();

        String jsonText = mPrefs.getString("offline_sync_data", null);

        return jsonText;
    }
*/

    public static void addofflinesyncdata(Context context, ArrayList<String>offline_sync_data) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        List<String> textList = offline_sync_data;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("offline_sync_data", jsonText);
        prefsEditor.commit();
    }


    // get Cart Data
    public static ArrayList<String> getofflinesyncdata(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonText =mPrefs.getString("offline_sync_data", null);
        String[] text = gson.fromJson(jsonText, String[].class);
        ArrayList<String> getCarts = new ArrayList<>();
        for(int i = 0 ; i < text.length;i++){
            getCarts.add(text[i]);
        }
        return getCarts;
    }

    public static void clearofflinesyncdata(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("offline_sync_data").commit();

    }

    public static void addclockoutidofflinedata(Context context, ArrayList<String> clockout_id) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        List<String> textList = clockout_id;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("clockout_id", jsonText);
        prefsEditor.commit();
    }


    public static ArrayList<String> getclockoutidofflinedata(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonText = mPrefs.getString("clockout_id", null);
        String[] text = gson.fromJson(jsonText, String[].class);

        ArrayList<String> getCarts = new ArrayList<>();

        for (int i = 0; i < text.length; i++) {
            getCarts.add(text[i]);
        }
        return getCarts;
    }

    public static void clearclockoutidofflinedata(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("clockout_id").commit();

    }



    public static void setclinicidofflinenew(Context context, String clinic_id_offline_new) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("clinic_id_offline_new", clinic_id_offline_new);
        prefsEditor.commit();
    }

    public static String getclinicidofflinenew(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("clinic_id_offline_new", "");
    }



    public static void addCalandersyncID(Context context, ArrayList<String>calander_sync) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        List<String> textList = calander_sync;
        String jsonText = gson.toJson(textList);
        prefsEditor.putString("calander_sync", jsonText);
        prefsEditor.commit();
    }


    // get Cart Data
    public static ArrayList<String> getCalandersyncID(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonText =mPrefs.getString("calander_sync", null);
        String[] text = gson.fromJson(jsonText, String[].class);
        ArrayList<String> getCarts = new ArrayList<>();
        for(int i = 0 ; i < text.length;i++){
            getCarts.add(text[i]);
        }
        return getCarts;
    }

    public static void clearCalandersyncID(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.edit().remove("calander_sync").commit();

    }



    public static void setbackValue(Context context, String value_back) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("value_back", value_back);
        prefsEditor.commit();
    }

    public static String getbackvalue(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("value_back", "");
    }


}
