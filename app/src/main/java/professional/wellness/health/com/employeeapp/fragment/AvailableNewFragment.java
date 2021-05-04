package professional.wellness.health.com.employeeapp.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.MyOptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import professional.wellness.health.com.employeeapp.Adapter.AvailableAdapter;
import professional.wellness.health.com.employeeapp.Adapter.CertificateAdapter;
import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.Available;
import professional.wellness.health.com.employeeapp.Model.AvailableNew;
import professional.wellness.health.com.employeeapp.Model.Certificate;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Fujitsu on 14-07-2017.
 */

public class AvailableNewFragment extends Fragment {
    private RecyclerView availableRecycler;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private Context context;

    private ArrayList<AvailableNew> available_list;
    private PopupMenu popup;
    private boolean selectedWeek =false;
    private boolean selectedDay =false;
    private boolean selectedMonth =false;
    private boolean selectedUocoming =false;
    MyOptionsPickerView MonthPicker, WeekPicker;
    List<Available> available_id;
    private double latitude;
    private double longitude;
    private  String month;
    private String week;
    private String weekmonthname;
    private String weekdate;
    private LinearLayout layout_main;
    private LinearLayout layout_error;
    private RegularTextView text_error;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.available_fragment, container, false);
        context = getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {
        layout_main = (LinearLayout)view.findViewById(R.id.layout_main);
        layout_error = (LinearLayout)view.findViewById(R.id.layout_error);
        text_error = (RegularTextView) view.findViewById(R.id.text_error);
        GPSTracker gps = new GPSTracker(getActivity());

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }
        available_id = new ArrayList<Available>();
        availableRecycler = (RecyclerView) view.findViewById(R.id.availableRecycler);
        encryptionClass = EncryptionClass.getInstance(context);
        restInteraction = RESTInteraction.getInstance(context);
      //  available = new ArrayList<>();
      //  available_list = new ArrayList<>();
        available_list = new ArrayList<>();
        MainActivity.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.img_available.setSelected(false);
                MainActivity.img_calander_blue.setSelected(false);
            }
        });
        MainActivity.img_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.img_available.setSelected(true);
                Context wrapper = new ContextThemeWrapper(getActivity(), R.style.popupMenuStyle);
                popup = new PopupMenu(wrapper, MainActivity.ll_calander);

                // popup = new PopupMenu(getActivity(), MainActivity.ll_calander);

                try {
                    Field[] fields = popup.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(popup);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                popup.getMenuInflater()
                        .inflate(R.menu.poupup_menu, popup.getMenu());
                if (selectedWeek) {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(2).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(2).setTitle(s);
                    popup.getMenu().getItem(2).setIcon(getResources().getDrawable(R.mipmap.red_week_icon));
                } else {
                    SpannableString s = new SpannableString(popup.getMenu().getItem(2).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(2).setTitle(s);
                    popup.getMenu().getItem(2).setIcon(getResources().getDrawable(R.mipmap.week));
                }

                if (selectedDay) {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(3).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(3).setTitle(s);
                    popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.red_day_icon));
                } else {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(3).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(3).setTitle(s);
                    popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.dayview));
                }

                if (selectedMonth) {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(1).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(1).setTitle(s);
                    popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.red_month_icon));
                } else {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(1).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(1).setTitle(s);
                    popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.month));

                }

                if (selectedUocoming) {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(0).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(0).setTitle(s);
                    popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.red_upcoming_icon));
                } else {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(0).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(0).setTitle(s);
                    popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.calendergrey));
                }
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        MainActivity.img_available.setSelected(false);
                        if (item.getTitle().toString().equals("Week")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            selectedWeek = true;
                            selectedDay = false;
                            selectedMonth = false;
                            selectedUocoming = false;
                            popup.getMenu().getItem(2).setIcon(getResources().getDrawable(R.mipmap.red_week_icon));
                            MainActivity.img_available.setImageResource(R.drawable.calander_week_selector);

                            WeekPicker.show();
                        } else if (item.getTitle().toString().equals("Day")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            selectedWeek = false;
                            selectedDay = true;
                            selectedMonth = false;
                            selectedUocoming = false;
                            popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.red_day_icon));
                            MainActivity.img_available.setImageResource(R.drawable.calander_day_selector);
                            createDialogWithoutDateField();
                        } else if (item.getTitle().toString().equals("Upcoming")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.red_upcoming_icon));
                            MainActivity.img_available.setImageResource(R.drawable.calander_upcoming_selector);
                            selectedWeek = false;
                            selectedDay = false;
                            selectedMonth = false;
                            selectedUocoming = true;

                            try {
                                JSONObject object = new JSONObject();
                                object.put("device_id", "SessionManager.getDeviceId(getActivity())");
                                object.put("platform_type", "android app");
                                object.put("user_id", SessionManager.getuserId(getActivity()));
                                object.put("type", "all");
                                object.put("type_value", "dddffd");
                                object.put("latitude",latitude);
                                object.put("longitude", longitude);
                                String send_data = object.toString();
                                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                                new availableAsync().execute(encrypted);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (item.getTitle().toString().equals("Month")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.red_month_icon));
                            MainActivity.img_available.setImageResource(R.drawable.calander_selector);
                            selectedWeek = false;
                            selectedDay = false;
                            selectedMonth = true;
                            selectedUocoming = false;
                            MonthPicker.show();
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });


        //Two Options PickerView
        MonthPicker = new MyOptionsPickerView(getActivity());
        final ArrayList<String> twoItemsOptions1 = new ArrayList<String>();
        twoItemsOptions1.add("January");
        twoItemsOptions1.add("February");
        twoItemsOptions1.add("March");
        twoItemsOptions1.add("April");
        twoItemsOptions1.add("May");
        twoItemsOptions1.add("June");
        twoItemsOptions1.add("July");
        twoItemsOptions1.add("August");
        twoItemsOptions1.add("September");
        twoItemsOptions1.add("October");
        twoItemsOptions1.add("November");
        twoItemsOptions1.add("December");
        final ArrayList<String> twoItemsOptions2 = new ArrayList<String>();
/*        twoItemsOptions2.add("1990");
        twoItemsOptions2.add("1991");
        twoItemsOptions2.add("1992");
        twoItemsOptions2.add("1993");
        twoItemsOptions2.add("1994");
        twoItemsOptions2.add("1995");
        twoItemsOptions2.add("1996");
        twoItemsOptions2.add("1997");
        twoItemsOptions2.add("1998");
        twoItemsOptions2.add("1999");
        twoItemsOptions2.add("2000");
        twoItemsOptions2.add("2001");
        twoItemsOptions2.add("2002");
        twoItemsOptions2.add("2003");
        twoItemsOptions2.add("2004");
        twoItemsOptions2.add("2005");
        twoItemsOptions2.add("2006");
        twoItemsOptions2.add("2007");
        twoItemsOptions2.add("2008");
        twoItemsOptions2.add("2009");
        twoItemsOptions2.add("2010");
        twoItemsOptions2.add("2011");
        twoItemsOptions2.add("2012");
        twoItemsOptions2.add("2013");
        twoItemsOptions2.add("2014");
        twoItemsOptions2.add("2015");
        twoItemsOptions2.add("2016");*/
        twoItemsOptions2.add("2017");
        twoItemsOptions2.add("2018");
        twoItemsOptions2.add("2019");
        twoItemsOptions2.add("2020");
        twoItemsOptions2.add("2021");
        twoItemsOptions2.add("2022");
        twoItemsOptions2.add("2023");
        twoItemsOptions2.add("2024");
        twoItemsOptions2.add("2025");
        twoItemsOptions2.add("2026");
        twoItemsOptions2.add("2027");
        twoItemsOptions2.add("2028");
        twoItemsOptions2.add("2029");
        twoItemsOptions2.add("2030");

        MonthPicker.setPicker(twoItemsOptions1, twoItemsOptions2, false);
        // twoPicker.setTitle("Month");

        MonthPicker.setSelectOptions(6, 0);
        MonthPicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                //select_month.setText(twoItemsOptions1.get(options1) + " " + twoItemsOptions2.get(option2));
                month = twoItemsOptions1.get(options1)+","+twoItemsOptions2.get(option2);
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("type", "month");
                    object.put("type_value", month);
                    object.put("latitude",latitude);
                    object.put("longitude", longitude);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new availablemonthAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        //Two Options PickerView
        WeekPicker = new MyOptionsPickerView(getActivity());
        final ArrayList<String> twoItemsOptions3 = new ArrayList<String>();
        twoItemsOptions3.add("January");
        twoItemsOptions3.add("February");
        twoItemsOptions3.add("March");
        twoItemsOptions3.add("April");
        twoItemsOptions3.add("May");
        twoItemsOptions3.add("June");
        twoItemsOptions3.add("July");
        twoItemsOptions3.add("August");
        twoItemsOptions3.add("September");
        twoItemsOptions3.add("October");
        twoItemsOptions3.add("November");
        twoItemsOptions3.add("December");
        final ArrayList<String> twoItemsOptions4 = new ArrayList<String>();
        twoItemsOptions4.add("01 to 07");
        twoItemsOptions4.add("08 to 15");
        twoItemsOptions4.add("16 to 22");
        twoItemsOptions4.add("23 to 31");


        WeekPicker.setPicker(twoItemsOptions3, twoItemsOptions4, false);
        // twoPicker.setTitle("Month");
        WeekPicker.setSelectOptions(6, 3);
        WeekPicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options4, int option5, int options6) {

                //select_month.setText(twoItemsOptions1.get(options1) + " " + twoItemsOptions2.get(option2));
                week = twoItemsOptions3.get(options4) + " " + twoItemsOptions4.get(option5);
                String s = week;
                StringTokenizer st = new StringTokenizer(s, " ");
                String monthname = st.nextToken();
                String datefirst = st.nextToken();
                String to = st.nextToken();
                String datelast = st.nextToken();

                if(monthname.equals("January")){
                    weekmonthname = "01";
                }else if(monthname.equals("February")){
                    weekmonthname = "02";
                }else if(monthname.equals("March")){
                    weekmonthname = "03";
                }else if(monthname.equals("April")){
                    weekmonthname = "04";
                }else if(monthname.equals("May")){
                    weekmonthname = "05";
                }else if(monthname.equals("June")){
                    weekmonthname = "06";
                }else if(monthname.equals("July")){
                    weekmonthname = "07";
                }else if(monthname.equals("August")){
                    weekmonthname = "08";
                }else if(monthname.equals("September")){
                    weekmonthname = "09";
                }else if(monthname.equals("October")){
                    weekmonthname = "10";
                }else if(monthname.equals("November")){
                    weekmonthname = "11";
                }else if(monthname.equals("December")){
                    weekmonthname = "12";
                }


                String startdate = datefirst+"-"+weekmonthname+"-"+"2017";
                String lastdate = datelast+"-"+weekmonthname+"-"+"2017";
                weekdate = startdate+ ","+lastdate;

                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("type", "week");
                    object.put("type_value", weekdate);
                    object.put("latitude",latitude);
                    object.put("longitude", longitude);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new availableweekAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        try {
            JSONObject object = new JSONObject();
            object.put("device_id", SessionManager.getDeviceId(getActivity()));
            object.put("platform_type", "android app");
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("type", "all");
            object.put("type_value", "dddffd");
            object.put("latitude",latitude);
            object.put("longitude", longitude);
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new availableAsync().execute(encrypted);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void createDialogWithoutDateField() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
                        + "-" + String.valueOf(dayOfMonth);
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat outputFormat1 = new SimpleDateFormat("dd-MM-yyyy");
                String inputDateStr = date;
                try {
                    Date datex = inputFormat.parse(inputDateStr);
                    String outputDateStr = outputFormat.format(datex);
                /*    outputDateStr1 = outputFormat1.format(datex);
                    submission_time.setText(outputDateStr);*/

                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("type", "day");
                        object.put("type_value", outputDateStr);
                        object.put("latitude",latitude);
                        object.put("longitude", longitude);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new availabledayAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }

    private class availableAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

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
            return restInteraction.getAvailableRequest(APIUrl.AVAILABLE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        layout_main.setVisibility(View.VISIBLE);
                        layout_error.setVisibility(View.GONE);
                        available_list.clear();
                        available_id.clear();

                        if (jsonObject.has("clinics")) {
                            JSONObject clinics = jsonObject.getJSONObject("clinics");

                            Iterator x = clinics.keys();
                            JSONArray jsonArray = new JSONArray();


                            while (x.hasNext()) {
                                String key = (String) x.next();
                                Available list = new Available();
                                list.setEid(key); /*= key;*/
                                available_id.add(list);
                                Log.e("key", key);
                                jsonArray.put(clinics.get(key));
                            }


                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONArray array = jsonArray.getJSONArray(i);
                                for(int j =0; j<array.length(); j++){
                                    JSONObject obj = array.getJSONObject(j);

                                    available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                            obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                            obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                            obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                            obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                            obj.getString("jay_walters"),
                                            obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                            obj.getString("type"),obj.getString("status_name")));


                                }

                            }
                            intializeRecycler();

                        }

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                      //  alertDialog(jsonObject.getString("message"));
                        layout_main.setVisibility(View.GONE);
                        layout_error.setVisibility(View.VISIBLE);
                        text_error.setText(jsonObject.getString("message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }


    private class availabledayAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

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
            return restInteraction.getAvailableRequest(APIUrl.AVAILABLE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        available_id.clear();
                        available_list.clear();
                        if (jsonObject.has("clinics")) {
                            JSONObject clinics = jsonObject.getJSONObject("clinics");

                            if (clinics.has("june")) {
                                JSONObject june = clinics.getJSONObject("june");
                                Iterator x = june.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(june.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("july")) {
                                JSONObject july = clinics.getJSONObject("july");
                                Iterator x = july.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(july.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("august")) {
                                JSONObject august = clinics.getJSONObject("august");
                                Iterator x = august.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(august.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("september")) {
                                JSONObject september = clinics.getJSONObject("september");
                                Iterator x = september.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(september.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("october")) {
                                JSONObject october = clinics.getJSONObject("october");
                                Iterator x = october.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(october.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("november")) {
                                JSONObject november = clinics.getJSONObject("november");
                                Iterator x = november.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(november.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("december")) {
                                JSONObject december = clinics.getJSONObject("december");
                                Iterator x = december.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(december.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("january")) {
                                JSONObject january = clinics.getJSONObject("january");
                                Iterator x = january.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(january.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("february")) {
                                JSONObject february = clinics.getJSONObject("february");
                                Iterator x = february.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(february.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("march")) {
                                JSONObject march = clinics.getJSONObject("march");
                                Iterator x = march.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(march.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("april")) {
                                JSONObject april = clinics.getJSONObject("april");
                                Iterator x = april.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(april.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("may")) {
                                JSONObject may = clinics.getJSONObject("may");
                                Iterator x = may.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(may.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }



                        }

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


    private class availableweekAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

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
            return restInteraction.getAvailableRequest(APIUrl.AVAILABLE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        available_id.clear();
                        available_list.clear();
                        if (jsonObject.has("clinics")) {
                            JSONObject clinics = jsonObject.getJSONObject("clinics");

                            if (clinics.has("june")) {
                                JSONObject june = clinics.getJSONObject("june");
                                Iterator x = june.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(june.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("july")) {
                                JSONObject july = clinics.getJSONObject("july");
                                Iterator x = july.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(july.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("august")) {
                                JSONObject august = clinics.getJSONObject("august");
                                Iterator x = august.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(august.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("september")) {
                                JSONObject september = clinics.getJSONObject("september");
                                Iterator x = september.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(september.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("october")) {
                                JSONObject october = clinics.getJSONObject("october");
                                Iterator x = october.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(october.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("november")) {
                                JSONObject november = clinics.getJSONObject("november");
                                Iterator x = november.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(november.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("december")) {
                                JSONObject december = clinics.getJSONObject("december");
                                Iterator x = december.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(december.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("january")) {
                                JSONObject january = clinics.getJSONObject("january");
                                Iterator x = january.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(january.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("february")) {
                                JSONObject february = clinics.getJSONObject("february");
                                Iterator x = february.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(february.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("march")) {
                                JSONObject march = clinics.getJSONObject("march");
                                Iterator x = march.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(march.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("april")) {
                                JSONObject april = clinics.getJSONObject("april");
                                Iterator x = april.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(april.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("may")) {
                                JSONObject may = clinics.getJSONObject("may");
                                Iterator x = may.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(may.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }



                        }

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



    private class availablemonthAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

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
            return restInteraction.getAvailableRequest(APIUrl.AVAILABLE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        available_id.clear();
                        available_list.clear();
                        if (jsonObject.has("clinics")) {
                            JSONObject clinics = jsonObject.getJSONObject("clinics");

                            if (clinics.has("june")) {
                                JSONObject june = clinics.getJSONObject("june");
                                Iterator x = june.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(june.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("july")) {
                                JSONObject july = clinics.getJSONObject("july");
                                Iterator x = july.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(july.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("august")) {
                                JSONObject august = clinics.getJSONObject("august");
                                Iterator x = august.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(august.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("september")) {
                                JSONObject september = clinics.getJSONObject("september");
                                Iterator x = september.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(september.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("october")) {
                                JSONObject october = clinics.getJSONObject("october");
                                Iterator x = october.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(october.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("november")) {
                                JSONObject november = clinics.getJSONObject("november");
                                Iterator x = november.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(november.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("december")) {
                                JSONObject december = clinics.getJSONObject("december");
                                Iterator x = december.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(december.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }

                            if (clinics.has("january")) {
                                JSONObject january = clinics.getJSONObject("january");
                                Iterator x = january.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(january.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("february")) {
                                JSONObject february = clinics.getJSONObject("february");
                                Iterator x = february.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(february.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("march")) {
                                JSONObject march = clinics.getJSONObject("march");
                                Iterator x = march.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(march.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("april")) {
                                JSONObject april = clinics.getJSONObject("april");
                                Iterator x = april.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(april.get(key));
                                }
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }
                            if (clinics.has("may")) {
                                JSONObject may = clinics.getJSONObject("may");
                                Iterator x = may.keys();
                                JSONArray jsonArray = new JSONArray();
                                available_id = new ArrayList<Available>();

                                while (x.hasNext()) {
                                    String key = (String) x.next();
                                    Available list = new Available();
                                    list.setEid(key); /*= key;*/
                                    available_id.add(list);
                                    Log.e("key", key);
                                    jsonArray.put(may.get(key));
                                }

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONArray array = jsonArray.getJSONArray(i);
                                    for(int j =0; j<array.length(); j++){
                                        JSONObject obj = array.getJSONObject(j);

                                        available_list.add(new AvailableNew(obj.getString("id"),obj.getString("provider_id"),
                                                obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                                obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                                obj.getString("prep_time"),obj.getString("date"),obj.getString("estimated_duration"),
                                                obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                                obj.getString("jay_walters"),
                                                obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                                obj.getString("type"),obj.getString("status_name")));


                                    }

                                }

                                intializeRecycler();

                            }



                        }

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

    private void intializeRecycler() {
        availableRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        availableRecycler.setItemAnimator(new DefaultItemAnimator());
        availableRecycler.setAdapter(new AvailableAdapter(getActivity(), available_list ,available_id));
    }

}
