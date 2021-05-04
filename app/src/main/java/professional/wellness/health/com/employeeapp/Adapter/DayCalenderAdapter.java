package professional.wellness.health.com.employeeapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.Model.MyClinicDay;
import professional.wellness.health.com.employeeapp.Model.ViewList;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 05-07-2017.
 */

public class DayCalenderAdapter extends BaseAdapter {

    private Context mContext;

    private java.util.Calendar month;
    public GregorianCalendar pmonth; // calendar instance for previous month
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString,curentDateString1;
    SimpleDateFormat df;
    TextView dateItem;
    private ArrayList<String> items;
    public static List<String> dayString;
   /* private ArrayList<MyClinicDay> day_clinic_list;*/
    private View previousView;
    private double latitude;
    private double longitude;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private ArrayList <MyClinicDay>day_clinic_list;
    private ArrayList <String>day_clinic_list_temp;
    private ArrayList <MyClinicDay>day_clinic_lista;
    private ArrayList<ViewList>viewlistotherday;

     RecyclerView day_list_Recycler;
     private FrameLayout layout;
    private String itrm = "";

    public DayCalenderAdapter(Context c, GregorianCalendar monthCalendar, RecyclerView day_list_Recycler, ArrayList<MyClinicDay> day_clinic_lista) {
        DayCalenderAdapter.dayString = new ArrayList<String>();
         month = monthCalendar;
        this.day_list_Recycler = day_list_Recycler;
        this.day_clinic_list = new ArrayList<>();
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        mContext = c;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        this.day_clinic_lista = day_clinic_lista;
        viewlistotherday = new ArrayList<>();
        day_clinic_list_temp = new ArrayList<>();
        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        Date d = new Date();
        curentDateString1  = (String) DateFormat.format("dd-MM-yyyy", d.getTime());

        GPSTracker gps = new GPSTracker(mContext);
        restInteraction = RESTInteraction.getInstance(mContext);
        encryptionClass = EncryptionClass.getInstance(mContext);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }
        refreshDays();
    }


    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {
        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final TextView dayView;
        LinearLayout view;


        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.day_calander_itam, null);

        }
        dayView = (TextView) v.findViewById(R.id.date);
        view = (LinearLayout)v.findViewById(R.id.view);
        layout = (FrameLayout)v.findViewById(R.id.layout);

        dateItem = (TextView) v.findViewById(R.id.dateItem);
        // separates daystring into parts.
        String[] separatedTime = dayString.get(position).split("-");



        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = separatedTime[0].replaceFirst("^0*", "");
        // checking whether the day is in current month or not.
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            // setting offdays to white color.
            dayView.setTextColor(Color.parseColor("#E1E1E1"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.parseColor("#E1E1E1"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            dayView.setTextColor(Color.parseColor("#9eA4Af"));
        }




        for (int i = 0; i <day_clinic_lista.size() ; i++) {
            if(dayString.get(position).equals(curentDateString1)) {
                setSelectedCurrentDate(view);
                view.setVisibility(View.VISIBLE);
                dayView.setTextColor(Color.parseColor("#FFFFFF"));
            }else
            if(dayString.get(position).equals(day_clinic_lista.get(i).getDate())){

                if (day_clinic_lista.get(i).getName().equals("Past clinic")){
                    setSelectedPast(view);
                    view.setVisibility(View.VISIBLE);
                   // dayView.setTextColor(Color.parseColor("#FFFFFF"));
                      }
            }else{
                v.setBackgroundResource(R.color.white);
                view.setVisibility(View.GONE);
            }



        }

        String pos = String.valueOf(position);
        if (itrm.equals(pos)) {
            layout.setSelected(true);

        } else {
            layout.setSelected(false);
        }

    /*    if (dayString.get(position).equals(curentDateString)) {
            setSelected(view);
            previousView = view;
            view.setVisibility(View.VISIBLE);
        } else {
            v.setBackgroundResource(R.color.white);
            view.setVisibility(View.GONE);
        }*/



        dayView.setText(gridvalue);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = dayString.get(position);
                itrm = String.valueOf(position);


                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(mContext));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(mContext));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("type", "day");
                    object.put("type_value",date );
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new MyClinicDayAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                notifyDataSetChanged();

            }
        });

        // create date string for comparison
        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        // show icon if date is not empty and it exists in the items array
     /*   ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.INVISIBLE);
        }*/

   /*     if (separatedTime[2].equals("04")){
            dateItem.setText("Clinic : W.main sir");
            dateItem.setBackgroundResource((R.drawable.blue_corner));
            dateItem.setTextColor(Color.parseColor("#FFFFFF"));
            iw.setVisibility(View.GONE);
        }
        else {
            dateItem.setVisibility(View.INVISIBLE);
            iw.setVisibility(View.INVISIBLE);
        }*/

        return v;
    }

    public View setSelectedTemp(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.color.residemenu_color);
        }
        previousView = view;
        view.setBackgroundResource(R.color.residemenu_color);
        return view;
    }

    public View setSelectedPast(View view) {
        if (previousView != null) {

            previousView.setBackgroundResource(R.drawable.round_gray);
        }
        previousView = view;

        previousView.setBackgroundResource(R.drawable.round_gray);
        return view;
    }

    public View setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.color.residemenu_color);
        }
        previousView = view;
        view.setBackgroundResource(R.color.residemenu_color);
        return view;
    }

    public View setSelectedCurrentDate(View view) {
        if (previousView != null) {

            previousView.setBackgroundResource(R.drawable.round_blue);
        }
        previousView = view;

        previousView.setBackgroundResource(R.drawable.round_blue);
        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        dayString.clear();
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }

    private class MyClinicDayAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.getMyClinicDay(APIUrl.MYCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);

                    if (object.has("status") && object.getString("status").equals("success")) {
                        day_clinic_list.clear();
                        day_clinic_list_temp.clear();
                        if(object.has("clinics")){
                            JSONArray array = object.getJSONArray("clinics");
                            for (int i = 0; i <array.length() ; i++) {
                                JSONObject obj = array.getJSONObject(i);
                                day_clinic_list.add(new MyClinicDay(obj.getString("id"),obj.getString("provider_id"),
                                        obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),obj.getString("latitude"),
                                        obj.getString("longitude"),obj.getString("time"),obj.getString("prep_time"),obj.getString("date"),
                                        obj.getString("prep_time_key"),obj.getString("estimated_duration"),obj.getString("personnel"),obj.getString("service_provider"),
                                        obj.getString("number_of_provider"),obj.getString("jay_walters"),
                                        obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                        obj.getString("contact_name"),obj.getString("primary_name"),obj.getString("medtech_name"),obj.getString("clocked"),
                                        obj.getString("type"),
                                        obj.getString("status_name"),obj.getString("duration"),obj.getString("mileage_required"),
                                        obj.getString("drive_time_required"),obj.getString("mileage_status"),obj.getString("drive_time_status"),
                                        obj.getString("clinic_spend_time")
                                ));

                                if(obj.has("other_name")){
                                    JSONArray arrayother = obj.getJSONArray("other_name");

                                    for (int j = 0; j <arrayother.length() ; j++) {
                                        JSONObject objother = arrayother.getJSONObject(j);
                                        viewlistotherday.add(new ViewList(obj.getString("id"),objother.getString("name")));
                                    }
                                }



                            }


                            intializeRecycler();
                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        day_clinic_list.clear();
                        intializeRecycler();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
            }

        }
    }

    private void intializeRecycler() {
        //day_clinic_list_temp.clear();
        for (int i = 0; i <day_clinic_list.size() ; i++) {
            day_clinic_list_temp.add(day_clinic_list.get(i).getId());

        }
        day_list_Recycler.setLayoutManager(new LinearLayoutManager(mContext));
        day_list_Recycler.setItemAnimator(new DefaultItemAnimator());
        day_list_Recycler.setAdapter(new MyClinicDayAdapter(mContext, day_clinic_list, viewlistotherday,day_clinic_list_temp));
    }
    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
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
