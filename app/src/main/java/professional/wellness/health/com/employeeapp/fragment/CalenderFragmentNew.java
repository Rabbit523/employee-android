package professional.wellness.health.com.employeeapp.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import professional.wellness.health.com.employeeapp.Adapter.CalendarAdapter;
import professional.wellness.health.com.employeeapp.Adapter.DayCalenderAdapter;
import professional.wellness.health.com.employeeapp.Adapter.MyClinicDayAdapter;
import professional.wellness.health.com.employeeapp.Adapter.MyClinicUpcomingAdapter;
import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.ClockinReminder;
import professional.wellness.health.com.employeeapp.Model.MyClinicDay;
import professional.wellness.health.com.employeeapp.Model.MyClinicMonth;
import professional.wellness.health.com.employeeapp.Model.MyClinicUpcoming;
import professional.wellness.health.com.employeeapp.Model.MyClinicWeek;
import professional.wellness.health.com.employeeapp.Model.ViewList;
import professional.wellness.health.com.employeeapp.Model.ViewListHomeFeed;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.Utils.ClinicService;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.LoginActivity;
import professional.wellness.health.com.employeeapp.activity.MonthClinicDetailAcivity;
import professional.wellness.health.com.employeeapp.activity.MonthClinicWeekDetailActivity;
import professional.wellness.health.com.employeeapp.activity.OnSwipeTouchListener;
import professional.wellness.health.com.employeeapp.activity.SplashActivity;

public class CalenderFragmentNew extends Fragment implements MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener, WeekView.EventClickListener {
    public Calendar month, daymonth, itemmonth, prevMonth;// calendar instances.
    String selectedGridDate;
    public CalendarAdapter adapter;// adapter instance
    public DayCalenderAdapter Dayadapter;// adapter instance
    public Handler handler;// for grabbing some event values for showing the dot
    // marker.
    public ArrayList<String> items; // container to store calendar items which
    // needs showing the event marker
    private LinearLayout monthView;
    private LinearLayout mothdayView;
    private LinearLayout dayView;
    private LinearLayout empReturn;
    private WeekView mWeekView;
    Context context;
    private Calendar calendar;
    private RegularTextView titlee;
    private ImageView day_calander_down;
    private LinearLayout ll_day_calander_layout;
    private PopupMenu popup;
    private boolean selectedWeek = false;
    private boolean selectedDay = false;
    private boolean selectedMonth = false;
    private boolean selectedUocoming = false;
    private boolean calander = false;
    private LinearLayout date_view;
    private ImageView img_previous;
    private ImageView img_previous_day;
    private ImageView img_next;
    private ImageView img_next_day;
    RegularTextView title;
    String currentDate;
    String dayName;
    private double latitude;
    private double longitude;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    GridView gridview;
    private ArrayList<MyClinicMonth> month_clinic_list;
    private ArrayList<MyClinicMonth> month_clinic_list_offline;
    private ArrayList<MyClinicDay> day_clinic_list;
    private ArrayList<MyClinicDay> day_clinic_list_offline;
    private RecyclerView day_list_Recycler;
    GridView gridviewDay;
    private String curentDateString1;
    private String curentMonth;
    String MONDAY;
    String SUNDAY;
    private ArrayList<MyClinicWeek> week_clinic_list;
    private ArrayList<String> day_clinic_list_temp;
    private ArrayList<MyClinicWeek> week_clinic_list_offline;

    String[] days = new String[7];
    private ArrayList<WeekViewEvent> mNewEvents;
    String curentyear;
    String curentMonthnew;
    private TextView img_current_month;
    private TextView img_current_day;
    private ImageView previous_cal_day;
    private ImageView next_cal_day;
    private LinearLayout ll_week_title;
    private RegularTextView week_title;
    private LinearLayout swipe_layout_day;
    private TextView img_current_week;
    String first;
    String second;
    String third;
    String fourth;
    String fifth;
    String six;
    String seven;
    private RegularTextView txt_date1;
    private RegularTextView txt_date2;
    private RegularTextView txt_date3;
    private RegularTextView txt_date4;
    private RegularTextView txt_date5;
    private RegularTextView txt_date6;
    private RegularTextView txt_date7;

    private LinearLayout layout_upcoming_recycler;
    private RecyclerView upcoming_recycler;
    private ArrayList<MyClinicUpcoming> myclinic_upcoming_list;
    private ArrayList<String> myclinic_upcoming_listtemp;
    private LinearLayout layout_error;
    private RegularTextView text_error;
    private String currentDatecloseday;

    //day close
    private LinearLayout dayclosefirst;
    private LinearLayout dayclosesecond;
    private LinearLayout dayclosethired;
    private LinearLayout dayclosefourth;
    private LinearLayout dayclosefifth;
    private LinearLayout dayclosesix;
    private LinearLayout daycloseseven;
    String monthdayclose;
    String yeardayclose;
    private String curentDaynew = "";
    private String Clinicname = "";
    private String system_calenderstatus = "";
    private ArrayList<ViewList> viewlist;
    private ArrayList<ViewList> viewlistWeek;
    private ArrayList<ViewList> viewlistotherday;
    private ArrayList<ViewList> viewlistWeektemp;
    private ArrayList<ViewListHomeFeed> othername_list;
    private ArrayList<String> clinic_upcoming_data = null;
    private ArrayList<String> clinic_upcoming_get_data;
    private LinearLayout main_layout;
    private ArrayList<String> clinic_month_data = null;
    private ArrayList<String> clinic_month_get_data;
    private ArrayList<String> clinic_week_data = null;
    private ArrayList<String> clinic_week_get_data;
    private ArrayList<String> clinic_day_data = null;
    private ArrayList<String> clinic_day_get_data;
    ArrayList <String> arrayList;
    ArrayList <String> arrayListgetData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calender_new, container, false);
        context = getActivity();

        try {
            context.startService(new Intent(context, ClinicService.class));

        } catch (Exception e) {

        }
        viewlist = new ArrayList<>();
        viewlistWeek = new ArrayList<>();
        viewlistWeektemp = new ArrayList<>();
        viewlistotherday = new ArrayList<>();
        othername_list = new ArrayList<>();
        clinic_upcoming_data = new ArrayList<>();
        clinic_upcoming_get_data = new ArrayList<>();
        arrayList = new ArrayList<String>();
        arrayListgetData = new ArrayList<String>();
        clinic_month_data = new ArrayList<>();
        clinic_month_get_data = new ArrayList<>();

        clinic_week_data = new ArrayList<>();
        clinic_week_get_data = new ArrayList<>();


        clinic_day_data = new ArrayList<>();
        clinic_day_get_data = new ArrayList<>();

        main_layout = (LinearLayout) view.findViewById(R.id.main_layout);
        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        titlee = (RegularTextView) view.findViewById(R.id.titlee);
        day_calander_down = (ImageView) view.findViewById(R.id.day_calander_down);
        date_view = (LinearLayout) view.findViewById(R.id.date_view);
        img_previous = (ImageView) view.findViewById(R.id.img_previous);
        img_current_month = (TextView) view.findViewById(R.id.img_current_month);
        img_next = (ImageView) view.findViewById(R.id.img_next);
        ll_week_title = (LinearLayout) view.findViewById(R.id.ll_week_title);
        week_title = (RegularTextView) view.findViewById(R.id.week_title);
        swipe_layout_day = (LinearLayout) view.findViewById(R.id.swipe_layout_day);
        layout_error = (LinearLayout) view.findViewById(R.id.layout_error);

        text_error = (RegularTextView) view.findViewById(R.id.text_error);
        layout_upcoming_recycler = (LinearLayout) view.findViewById(R.id.layout_upcoming_recycler);
        upcoming_recycler = (RecyclerView) view.findViewById(R.id.upcoming_recycler);
        myclinic_upcoming_list = new ArrayList<>();
        myclinic_upcoming_listtemp = new ArrayList<>();

        previous_cal_day = (ImageView) view.findViewById(R.id.previous_cal_day);
        next_cal_day = (ImageView) view.findViewById(R.id.next_cal_day);
        img_current_week = (TextView) view.findViewById(R.id.img_current_week);
        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);
        // daycloseRecycler = (RecyclerView) view.findViewById(R.id.daycloseRecycler);
        day_list_Recycler = (RecyclerView) view.findViewById(R.id.day_list_Recycler);
        img_current_day = (TextView) view.findViewById(R.id.img_current_day);

        txt_date1 = (RegularTextView) view.findViewById(R.id.txt_date1);
        txt_date2 = (RegularTextView) view.findViewById(R.id.txt_date2);
        txt_date3 = (RegularTextView) view.findViewById(R.id.txt_date3);
        txt_date4 = (RegularTextView) view.findViewById(R.id.txt_date4);
        txt_date5 = (RegularTextView) view.findViewById(R.id.txt_date5);
        txt_date6 = (RegularTextView) view.findViewById(R.id.txt_date6);
        txt_date7 = (RegularTextView) view.findViewById(R.id.txt_date7);

        dayclosefirst = (LinearLayout) view.findViewById(R.id.dayclosefirst);
        dayclosesecond = (LinearLayout) view.findViewById(R.id.dayclosesecond);
        dayclosethired = (LinearLayout) view.findViewById(R.id.dayclosethired);
        dayclosefourth = (LinearLayout) view.findViewById(R.id.dayclosefourth);
        dayclosefifth = (LinearLayout) view.findViewById(R.id.dayclosefifth);
        dayclosesix = (LinearLayout) view.findViewById(R.id.dayclosesix);
        daycloseseven = (LinearLayout) view.findViewById(R.id.daycloseseven);


        GPSTracker gps = new GPSTracker(getActivity());

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }

        previous_cal_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtills.isNetworkAvailable(getActivity())) {
                    setPreviousMonthDay();
                }

            }
        });

        ll_week_title.setVisibility(View.GONE);

        next_cal_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtills.isNetworkAvailable(getActivity())) {
                    setNextMonthDay();
                }

            }
        });

        //current week
        //setupDateTimeInterpreter(true);
        try {
            img_current_month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (AppUtills.isNetworkAvailable(getActivity())) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.view_container, new CalenderFragmentNew());
                        fragmentTransaction.commit();
                    }


                }
            });
        } catch (Exception e) {

        }


        month_clinic_list = new ArrayList<>();
        month_clinic_list_offline = new ArrayList<>();
        day_clinic_list = new ArrayList<>();
        day_clinic_list_offline = new ArrayList<>();
        week_clinic_list = new ArrayList<>();
        day_clinic_list_temp = new ArrayList<>();
        week_clinic_list_offline = new ArrayList<>();
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AppUtills.isNetworkAvailable(getActivity())) {
                    setPreviousMonth();
                }
            }
        });

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtills.isNetworkAvailable(getActivity())) {
                    setNextMonth();
                }
            }
        });


        // Initially, there will be no events on the week view because the user has not tapped on
        // it yet.
        mWeekView.setMonthChangeListener(this);

        // Set up empty view click listener.
        mWeekView.setEmptyViewClickListener(this);
        mWeekView.setOnEventClickListener(this);

        // Initially, there will be no events on the week view because the user has not tapped on
        // it yet.
        mNewEvents = new ArrayList<WeekViewEvent>();




        title = (RegularTextView) view.findViewById(R.id.title);

        calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
        currentDate = sdf.format(calendar.getTime());
////////////////////////////////
        SimpleDateFormat sdff = new SimpleDateFormat("MM yyyy");
        currentDatecloseday = sdff.format(calendar.getTime());

        String CurrentStringfirst = currentDatecloseday;
        String[] separatedfirst = CurrentStringfirst.split(" ");
        monthdayclose = separatedfirst[0];
        yeardayclose = separatedfirst[1];



        SimpleDateFormat sdf_ = new SimpleDateFormat("EEE");
        Date date = new Date();
        dayName = sdf_.format(date);
        titlee.setText("" + dayName + " " + currentDate + "");

        title.setText("" + dayName + " " + currentDate + "");
        week_title.setText("" + dayName + " " + currentDate + "");


        month = Calendar.getInstance();
        // daymonth = Calendar.getInstance();
        itemmonth = (Calendar) month.clone();

        items = new ArrayList<String>();


        gridviewDay = (GridView) view.findViewById(R.id.gridviewDay);


        day_calander_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gridviewDay.getVisibility() == View.GONE) {
                    gridviewDay.setVisibility(View.VISIBLE);
                    date_view.setVisibility(View.GONE);
                    day_calander_down.setRotation(day_calander_down.getRotation() + 180);
                } else {
                    gridviewDay.setVisibility(View.GONE);
                    date_view.setVisibility(View.VISIBLE);
                    day_calander_down.setRotation(day_calander_down.getRotation() - 180);

                }
            }
        });


        month = Calendar.getInstance();
        itemmonth = (Calendar) month.clone();

        items = new ArrayList<String>();

        gridview = (GridView) view.findViewById(R.id.gridview);
        monthView = (LinearLayout) view.findViewById(R.id.monthView);
        mothdayView = (LinearLayout) view.findViewById(R.id.mothdayView);
        dayView = (LinearLayout) view.findViewById(R.id.dayView);
        empReturn = (LinearLayout) view.findViewById(R.id.empReturn);

        handler = new Handler();
        handler.post(calendarUpdater);
        Date b = new Date();

        SimpleDateFormat sdfF = new SimpleDateFormat("yyyy");
        curentyear = sdfF.format(calendar.getTime());
        curentMonthnew = (String) DateFormat.format("MMM", b.getTime());
        curentMonth = curentMonthnew + "," + curentyear;

        curentDaynew = (String) DateFormat.format("d", b.getTime());

        // current week all day
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("d");


        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1; //add 2 if your week start on monday
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);

            first = days[0];
            second = days[1];
            third = days[2];
            fourth = days[3];
            fifth = days[4];
            six = days[5];
            seven = days[6];

        }
        txt_date1.setText(first);
        txt_date2.setText(second);
        txt_date3.setText(third);
        txt_date4.setText(fourth);
        txt_date5.setText(fifth);
        txt_date6.setText(six);
        txt_date7.setText(seven);

        if (curentDaynew.equals(first)) {

            dayclosefirst.setBackgroundResource(R.drawable.round_blue);
        } else {
            dayclosefirst.setBackgroundResource(R.color.white);
        }

        if (curentDaynew.equals(second)) {

            dayclosesecond.setBackgroundResource(R.drawable.round_blue);
        } else {
            dayclosesecond.setBackgroundResource(R.color.white);
        }

        if (curentDaynew.equals(third)) {

            dayclosethired.setBackgroundResource(R.drawable.round_blue);
        } else {
            dayclosethired.setBackgroundResource(R.color.white);
        }


        if (curentDaynew.equals(fourth)) {

            dayclosefourth.setBackgroundResource(R.drawable.round_blue);
        } else {
            dayclosefourth.setBackgroundResource(R.color.white);
        }

        if (curentDaynew.equals(fifth)) {

            dayclosefifth.setBackgroundResource(R.drawable.round_blue);
        } else {
            dayclosefifth.setBackgroundResource(R.color.white);
        }


        if (curentDaynew.equals(six)) {

            dayclosesix.setBackgroundResource(R.drawable.round_blue);
        } else {
            dayclosesix.setBackgroundResource(R.color.white);
        }


        if (curentDaynew.equals(seven)) {

            daycloseseven.setBackgroundResource(R.drawable.round_blue);
        } else {
            daycloseseven.setBackgroundResource(R.color.white);
        }

        // System.out.println(Arrays.toString(days));

        //  intializeRecyclerDClose();
        img_current_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month = Calendar.getInstance();
                itemmonth = (Calendar) month.clone();
                Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                gridviewDay.setAdapter(Dayadapter);
                titlee.setText("" + dayName + " " + currentDate + "");
                Date d = new Date();
                curentDateString1 = (String) DateFormat.format("dd-MM-yyyy", d.getTime());

                if (AppUtills.isNetworkAvailable(getActivity())) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("type", "day");
                        object.put("type_value", curentDateString1);
                             /*   curentDateString1*/
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicDayAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        if (AppUtills.isNetworkAvailable(getActivity())) {
            try {
                JSONObject object = new JSONObject();
                object.put("device_id", SessionManager.getDeviceId(getActivity()));
                object.put("platform_type", "android app");
                object.put("user_id", SessionManager.getuserId(getActivity()));
                object.put("latitude", latitude);
                object.put("longitude", longitude);
                object.put("type", "month");
                object.put("type_value", curentMonth);
                String send_data = object.toString();
                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                new MyClinicMonthAsync().execute(encrypted);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            MyClinicMonthOffline();


        }


        MainActivity.img_calander_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.img_calander_blue.setSelected(true);
                }catch (Exception e){

                }

                Context wrapper = new ContextThemeWrapper(getActivity(), R.style.popup);
                popup = new PopupMenu(wrapper, MainActivity.ll_calander);

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
                    SpannableString s = new SpannableString(popup.getMenu().getItem(2).getTitle().toString());
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
                    SpannableString s = new SpannableString(popup.getMenu().getItem(3).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(3).setTitle(s);
                    popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.red_day_icon));
                } else {
                    SpannableString s = new SpannableString(popup.getMenu().getItem(3).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(3).setTitle(s);
                    popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.dayview));
                }

                if (selectedMonth) {
                    SpannableString s = new SpannableString(popup.getMenu().getItem(1).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(1).setTitle(s);
                    popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.red_month_icon));
                } else {
                    SpannableString s = new SpannableString(popup.getMenu().getItem(1).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(1).setTitle(s);
                    popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.month));

                }

                if (selectedUocoming) {
                    SpannableString s = new SpannableString(popup.getMenu().getItem(0).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(0).setTitle(s);
                    popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.red_upcoming_icon));
                } else {
                    SpannableString s = new SpannableString(popup.getMenu().getItem(0).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(0).setTitle(s);
                    popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.calendergrey_new));
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        MainActivity.img_calander_blue.setSelected(false);
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
                            MainActivity.img_calander_blue.setImageResource(R.drawable.calander_week_selector);
                            ll_week_title.setVisibility(View.VISIBLE);
                            monthView.setVisibility(View.GONE);
                            layout_error.setVisibility(View.GONE);
                            dayView.setVisibility(View.GONE);
                            layout_upcoming_recycler.setVisibility(View.GONE);
                            try {
                                getCurrentWeek(calendar);
                            } catch (Exception e) {

                            }

                            SessionManager.setClinicViewId(getActivity(), "3");

                        } else if (item.getTitle().toString().equals("Day")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            selectedWeek = false;
                            selectedDay = true;
                            selectedMonth = false;
                            selectedUocoming = false;
                            popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.red_day_icon));
                            MainActivity.img_calander_blue.setImageResource(R.drawable.calander_day_selector);
                            mWeekView.setVisibility(View.GONE);
                            monthView.setVisibility(View.GONE);
                            dayView.setVisibility(View.VISIBLE);
                            layout_error.setVisibility(View.GONE);
                            ll_week_title.setVisibility(View.GONE);
                            layout_upcoming_recycler.setVisibility(View.GONE);
                            Date d = new Date();
                            SessionManager.setClinicViewId(getActivity(), "4");
                            curentDateString1 = (String) DateFormat.format("dd-MM-yyyy", d.getTime());


                            if (AppUtills.isNetworkAvailable(getActivity())) {
                                try {
                                    JSONObject object = new JSONObject();
                                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                                    object.put("platform_type", "android app");
                                    object.put("user_id", SessionManager.getuserId(getActivity()));
                                    object.put("latitude", latitude);
                                    object.put("longitude", longitude);
                                    object.put("type", "day");
                                    object.put("type_value", curentDateString1);
                                    String send_data = object.toString();
                                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                                    new MyClinicDayAsync().execute(encrypted);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {

                                try {
                                    final Snackbar snackbar = Snackbar.make(main_layout, "No Internet Connection", (int) 5000L);
                                    snackbar.setAction("DISMISS", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            snackbar.dismiss();
                                        }
                                    });

                                    snackbar.show();

                                } catch (Exception e) {
                                    e.getStackTrace();
                                }

                                MyclinicDayOffline();

                            }


                        } else if (item.getTitle().toString().equals("Upcoming")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.red_upcoming_icon));
                            MainActivity.img_calander_blue.setImageResource(R.drawable.calander_upcoming_selector);
                            selectedWeek = false;
                            selectedDay = false;
                            selectedMonth = false;
                            selectedUocoming = true;
                            mWeekView.setVisibility(View.GONE);
                            monthView.setVisibility(View.GONE);
                            dayView.setVisibility(View.GONE);
                            ll_week_title.setVisibility(View.GONE);
                            layout_upcoming_recycler.setVisibility(View.VISIBLE);


                            if (AppUtills.isNetworkAvailable(getActivity())) {

                                try {
                                    JSONObject object = new JSONObject();
                                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                                    object.put("platform_type", "android app");
                                    object.put("user_id", SessionManager.getuserId(getActivity()));
                                    object.put("latitude", latitude);
                                    object.put("longitude", longitude);
                                    object.put("type", "upcoming");
                                    object.put("type_value", "upcoming");
                                    String send_data = object.toString();
                                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                                    new MyClinicUpcomingAsync().execute(encrypted);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    final Snackbar snackbar = Snackbar.make(main_layout, "No Internet Connection", (int) 5000L);
                                    snackbar.setAction("DISMISS", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            snackbar.dismiss();
                                        }
                                    });

                                    snackbar.show();
                                } catch (Exception e) {
                                    e.getStackTrace();
                                }




                                MyClinicUpcomingOffline();



                            }

                            SessionManager.setClinicViewId(getActivity(), "1");

                        } else if (item.getTitle().toString().equals("Month")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.red_month_icon));
                            MainActivity.img_calander_blue.setImageResource(R.drawable.calander_selector);
                            selectedWeek = false;
                            selectedDay = false;
                            selectedMonth = true;
                            selectedUocoming = false;
                            mWeekView.setVisibility(View.GONE);
                            monthView.setVisibility(View.VISIBLE);
                            layout_upcoming_recycler.setVisibility(View.GONE);
                            dayView.setVisibility(View.GONE);
                            ll_week_title.setVisibility(View.GONE);
                            layout_error.setVisibility(View.GONE);

                            SessionManager.setClinicViewId(getActivity(), "2");
                            if (AppUtills.isNetworkAvailable(getActivity())) {
                                try {
                                    JSONObject object = new JSONObject();
                                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                                    object.put("platform_type", "android app");
                                    object.put("user_id", SessionManager.getuserId(getActivity()));
                                    object.put("latitude", latitude);
                                    object.put("longitude", longitude);
                                    object.put("type", "month");
                                    object.put("type_value", curentMonth);
                                    String send_data = object.toString();
                                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                                    new MyClinicMonthAsync().execute(encrypted);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    final Snackbar snackbar = Snackbar.make(main_layout, "No Internet Connection", (int) 5000L);
                                    snackbar.setAction("DISMISS", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            snackbar.dismiss();
                                        }
                                    });

                                    snackbar.show();
                                } catch (Exception e) {
                                    e.getStackTrace();
                                }

                                MyClinicMonthOffline();


                            }

                        }
                        return true;
                    }
                });

                popup.show();
            }
        });


        empReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthView.setVisibility(View.VISIBLE);
                mothdayView.setVisibility(View.GONE);
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {


                selectedGridDate = CalendarAdapter.dayString
                        .get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*",
                        "");// taking last part of date. ie; 2 from 2012-12-02.
                if (gridvalueString.equals("5")) {
                    monthView.setVisibility(View.GONE);
                    mothdayView.setVisibility(View.VISIBLE);
                } else if (gridvalueString.equals("7")) {
                    monthView.setVisibility(View.GONE);
                    mothdayView.setVisibility(View.VISIBLE);
                } else if (gridvalueString.equals("8")) {
                    monthView.setVisibility(View.GONE);
                    mothdayView.setVisibility(View.VISIBLE);
                } else {
                    monthView.setVisibility(View.VISIBLE);
                    mothdayView.setVisibility(View.GONE);
                }


            }
        });

        gridview.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            public void onSwipeRight() {


                if (AppUtills.isNetworkAvailable(getActivity())) {
                    setPreviousMonth();
                }
            }

            public void onSwipeLeft() {


                if (AppUtills.isNetworkAvailable(getActivity())) {
                    setNextMonth();
                }
            }


        });

        gridviewDay.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            public void onSwipeRight() {


                if (AppUtills.isNetworkAvailable(getActivity())) {
                    setPreviousMonthDay();
                }

            }

            public void onSwipeLeft() {

                if (AppUtills.isNetworkAvailable(getActivity())) {
                    setNextMonthDay();
                }
            }


        });

        img_current_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mWeekView.goToToday();

            }
        });


        dayclosefirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayclosefirst.setBackgroundResource(R.color.red);
                dayclosesecond.setBackgroundResource(R.color.white);
                dayclosethired.setBackgroundResource(R.color.white);
                dayclosefourth.setBackgroundResource(R.color.white);
                dayclosefifth.setBackgroundResource(R.color.white);
                dayclosesix.setBackgroundResource(R.color.white);
                daycloseseven.setBackgroundResource(R.color.white);

                String date = txt_date1.getText().toString().trim() + "-" + monthdayclose + "-" + yeardayclose;
                if (AppUtills.isNetworkAvailable(getActivity())) {
                    day_clinic_list.clear();

                    day_clinic_list_temp.clear();
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("type", "day");
                        object.put("type_value", date);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicDayCloseAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        dayclosesecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayclosefirst.setBackgroundResource(R.color.white);
                dayclosesecond.setBackgroundResource(R.color.red);
                dayclosethired.setBackgroundResource(R.color.white);
                dayclosefourth.setBackgroundResource(R.color.white);
                dayclosefifth.setBackgroundResource(R.color.white);
                dayclosesix.setBackgroundResource(R.color.white);
                daycloseseven.setBackgroundResource(R.color.white);
                String date = txt_date2.getText().toString().trim() + "-" + monthdayclose + "-" + yeardayclose;
                if (AppUtills.isNetworkAvailable(getActivity())) {
                    day_clinic_list.clear();
                    day_clinic_list_temp.clear();
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("type", "day");
                        object.put("type_value", date);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicDayCloseAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        dayclosethired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayclosefirst.setBackgroundResource(R.color.white);
                dayclosesecond.setBackgroundResource(R.color.white);
                dayclosethired.setBackgroundResource(R.color.red);
                dayclosefourth.setBackgroundResource(R.color.white);
                dayclosefifth.setBackgroundResource(R.color.white);
                dayclosesix.setBackgroundResource(R.color.white);
                daycloseseven.setBackgroundResource(R.color.white);
                String date = txt_date3.getText().toString().trim() + "-" + monthdayclose + "-" + yeardayclose;
                if (AppUtills.isNetworkAvailable(getActivity())) {
                    day_clinic_list.clear();
                    day_clinic_list_temp.clear();
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("type", "day");
                        object.put("type_value", date);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicDayCloseAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        dayclosefourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayclosefirst.setBackgroundResource(R.color.white);
                dayclosesecond.setBackgroundResource(R.color.white);
                dayclosethired.setBackgroundResource(R.color.white);
                dayclosefourth.setBackgroundResource(R.color.red);
                dayclosefifth.setBackgroundResource(R.color.white);
                dayclosesix.setBackgroundResource(R.color.white);
                daycloseseven.setBackgroundResource(R.color.white);
                String date = txt_date4.getText().toString().trim() + "-" + monthdayclose + "-" + yeardayclose;
                if (AppUtills.isNetworkAvailable(getActivity())) {
                    day_clinic_list.clear();
                    day_clinic_list_temp.clear();
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("type", "day");
                        object.put("type_value", date);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicDayCloseAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        dayclosefifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayclosefirst.setBackgroundResource(R.color.white);
                dayclosesecond.setBackgroundResource(R.color.white);
                dayclosethired.setBackgroundResource(R.color.white);
                dayclosefourth.setBackgroundResource(R.color.white);
                dayclosefifth.setBackgroundResource(R.color.red);
                dayclosesix.setBackgroundResource(R.color.white);
                daycloseseven.setBackgroundResource(R.color.white);
                String date = txt_date5.getText().toString().trim() + "-" + monthdayclose + "-" + yeardayclose;
                if (AppUtills.isNetworkAvailable(getActivity())) {
                    day_clinic_list.clear();
                    day_clinic_list_temp.clear();
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("type", "day");
                        object.put("type_value", date);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicDayCloseAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        dayclosesix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayclosefirst.setBackgroundResource(R.color.white);
                dayclosesecond.setBackgroundResource(R.color.white);
                dayclosethired.setBackgroundResource(R.color.white);
                dayclosefourth.setBackgroundResource(R.color.white);
                dayclosefifth.setBackgroundResource(R.color.white);
                dayclosesix.setBackgroundResource(R.color.red);
                daycloseseven.setBackgroundResource(R.color.white);

                String date = txt_date6.getText().toString().trim() + "-" + monthdayclose + "-" + yeardayclose;

                if (AppUtills.isNetworkAvailable(getActivity())) {
                    day_clinic_list.clear();
                    day_clinic_list_temp.clear();
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("type", "day");
                        object.put("type_value", date);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicDayCloseAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        daycloseseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayclosefirst.setBackgroundResource(R.color.white);
                dayclosesecond.setBackgroundResource(R.color.white);
                dayclosethired.setBackgroundResource(R.color.white);
                dayclosefourth.setBackgroundResource(R.color.white);
                dayclosefifth.setBackgroundResource(R.color.white);
                dayclosesix.setBackgroundResource(R.color.white);
                daycloseseven.setBackgroundResource(R.color.red);

                String date = txt_date7.getText().toString().trim() + "-" + monthdayclose + "-" + yeardayclose;
                if (AppUtills.isNetworkAvailable(getActivity())) {
                    day_clinic_list.clear();
                    day_clinic_list_temp.clear();
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("type", "day");
                        object.put("type_value", date);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicDayCloseAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        if (SessionManager.getClinicViewId(getActivity()).equals("1")) {
            mWeekView.setVisibility(View.GONE);
            monthView.setVisibility(View.GONE);
            dayView.setVisibility(View.GONE);
            ll_week_title.setVisibility(View.GONE);
            layout_upcoming_recycler.setVisibility(View.VISIBLE);

            if (AppUtills.isNetworkAvailable(getActivity())) {

                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("type", "upcoming");
                    object.put("type_value", "upcoming");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new MyClinicUpcomingAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                MyClinicUpcomingOffline();
            }


        } else if (SessionManager.getClinicViewId(getActivity()).equals("2")) {
            mWeekView.setVisibility(View.GONE);
            monthView.setVisibility(View.VISIBLE);
            layout_upcoming_recycler.setVisibility(View.GONE);
            dayView.setVisibility(View.GONE);
            ll_week_title.setVisibility(View.GONE);
            layout_error.setVisibility(View.GONE);

            if (AppUtills.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("type", "month");
                    object.put("type_value", curentMonth);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new MyClinicMonthAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    final Snackbar snackbar = Snackbar.make(main_layout, "No Internet Connection", (int) 5000L);
                    snackbar.setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

                    snackbar.show();


                } catch (Exception e) {

                }


                 MyClinicMonthOffline();


            }
        } else if (SessionManager.getClinicViewId(getActivity()).equals("3")) {
            ll_week_title.setVisibility(View.VISIBLE);
            monthView.setVisibility(View.GONE);
            layout_error.setVisibility(View.GONE);
            dayView.setVisibility(View.GONE);
            layout_upcoming_recycler.setVisibility(View.GONE);


            try {
                getCurrentWeek(calendar);
            } catch (Exception e) {

            }

        } else if (SessionManager.getClinicViewId(getActivity()).equals("4")) {
            mWeekView.setVisibility(View.GONE);
            monthView.setVisibility(View.GONE);
            dayView.setVisibility(View.VISIBLE);
            layout_error.setVisibility(View.GONE);
            ll_week_title.setVisibility(View.GONE);
            layout_upcoming_recycler.setVisibility(View.GONE);

            Date d = new Date();
            SessionManager.setClinicViewId(getActivity(), "4");
            curentDateString1 = (String) DateFormat.format("dd-MM-yyyy", d.getTime());
            if (AppUtills.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("type", "day");
                    object.put("type_value", curentDateString1);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new MyClinicDayAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                MyclinicDayOffline();
            }


        } else {
            mWeekView.setVisibility(View.GONE);
            monthView.setVisibility(View.VISIBLE);
            layout_upcoming_recycler.setVisibility(View.GONE);
            dayView.setVisibility(View.GONE);
            ll_week_title.setVisibility(View.GONE);
            layout_error.setVisibility(View.GONE);

            if (AppUtills.isNetworkAvailable(getActivity())) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("type", "month");
                    object.put("type_value", curentMonth);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new MyClinicMonthAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    final Snackbar snackbar = Snackbar.make(main_layout, "No Internet Connection", (int) 5000L);
                    snackbar.setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

                    snackbar.show();
                } catch (Exception e) {
                    e.getStackTrace();
                }


                MyClinicMonthOffline();

              //  }


            }


        }


        return view;
    }

    private void MyClinicUpcomingOffline() {

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
    }

    private void MyClinicMonthOffline() {
        try {
            JSONObject object = new JSONObject(SessionManager.getMyClinicMonth(getActivity()));

            if (object.has("status") && object.getString("status").equals("success")) {
                month_clinic_list.clear();
                viewlist.clear();
                if (object.has("clinics")) {
                    JSONArray array = object.getJSONArray("clinics");
                    for (int j = 0; j < array.length(); j++) {
                        JSONObject obj = array.getJSONObject(j);

                        month_clinic_list.add(new MyClinicMonth(obj.getString("id"), obj.getString("provider_id"),
                                obj.getString("name"), obj.getString("phone"), obj.getString("location_name"), obj.getString("latitude"),
                                obj.getString("longitude"), obj.getString("time"), obj.getString("end_time"), obj.getString("prep_time"), obj.getString("default_unfilled_time"), obj.getString("date"),
                                obj.getString("prep_time_key"), obj.getString("estimated_duration"), obj.getString("personnel"), obj.getString("primary_provider"),
                                obj.getString("medtech_provider"), obj.getString("other_provider"),
                                obj.getString("service_provider"),
                                obj.getString("number_of_provider"), obj.getString("jay_walters"), obj.getString("timezone"),
                                obj.getString("status"), obj.getString("created_at"), obj.getString("updated_at"), obj.getString("primary_name"),
                                obj.getString("medtech_name"), obj.getString("system_calender"), obj.getString("clocked"), obj.getString("type"),
                                obj.getString("status_name"), obj.getString("duration"), obj.getString("mileage_required"),
                                obj.getString("drive_time_required"), obj.getString("mileage_status"), obj.getString("drive_time_status"),
                                obj.getString("clinic_spend_time"), obj.getString("mileage"), obj.getString("drive_time")
                        ));

                        if (obj.has("other_name")) {
                            JSONArray array1 = obj.getJSONArray("other_name");
                            for (int k = 0; k < array1.length(); k++) {
                                JSONObject obj_viewlist = array1.getJSONObject(k);
                                viewlist.add(new ViewList(obj.getString("id"), obj_viewlist.getString("name")));

                            }
                        }


                    }
                    month_clinic_list_offline.clear();
                    for (int j = 0; j < month_clinic_list.size(); j++) {
                        if (!month_clinic_list.get(j).getType().equals("clock_in")) {

                           /* try {*/
                               /* String yourTime = month_clinic_list.get(j).getPrep_time();

                                String today = (String) DateFormat.format(
                                        "hh:mm aa", new Date());

                                SimpleDateFormat sdfff = new SimpleDateFormat("hh:mm aa");
                                Date date1 = sdfff.parse(yourTime);


                                Date date2 = sdfff.parse(today);*/


                                int time = Integer.parseInt(month_clinic_list.get(j).getCreate_timestamp());
                                if (month_clinic_list.get(j).getStatus_name().equals("My clinic")) {
                                    if (System.currentTimeMillis() / 1000 >= time) {
                                        month_clinic_list_offline.add(new MyClinicMonth(month_clinic_list.get(j).getId(), month_clinic_list.get(j).getProvider_id(),
                                                month_clinic_list.get(j).getName(), month_clinic_list.get(j).getPhone(), month_clinic_list.get(j).getLocation_name(), month_clinic_list.get(j).getLatitude(),
                                                month_clinic_list.get(j).getLongitude(), month_clinic_list.get(j).getTime(), month_clinic_list.get(j).getEnd_time(), month_clinic_list.get(j).getPrep_time(),
                                                month_clinic_list.get(j).getDefault_unfilled_time(), month_clinic_list.get(j).getDate(),
                                                month_clinic_list.get(j).getCreate_timestamp(), month_clinic_list.get(j).getEstimated_duration(), month_clinic_list.get(j).getPersonnel(), month_clinic_list.get(j).getPrimary_provider(),
                                                month_clinic_list.get(j).getMedtech_provider(), month_clinic_list.get(j).getOther_provider(),
                                                month_clinic_list.get(j).getService_provider(),
                                                month_clinic_list.get(j).getNumber_of_provider(), month_clinic_list.get(j).getJay_walters(), month_clinic_list.get(j).getTimezone(),
                                                month_clinic_list.get(j).getStatus(), month_clinic_list.get(j).getCreated_at(), month_clinic_list.get(j).getUpdated_at(), month_clinic_list.get(j).getPrimary_name(),
                                                month_clinic_list.get(j).getMedtech_name(), month_clinic_list.get(j).getSystem_calender(), "1", "clock_in",
                                                month_clinic_list.get(j).getStatus_name(), month_clinic_list.get(j).getDuration(), month_clinic_list.get(j).getMileage_required(),
                                                month_clinic_list.get(j).getDrive_time_required(), month_clinic_list.get(j).getMileage_status(), month_clinic_list.get(j).getDrive_time_status(),
                                                month_clinic_list.get(j).getClinic_spend_time(), month_clinic_list.get(j).getMileage(), month_clinic_list.get(j).getDrive_time()));


                                        month_clinic_list.remove(j);

                                        for (int k = 0; k < month_clinic_list_offline.size(); k++) {
                                            month_clinic_list.add(new MyClinicMonth(month_clinic_list_offline.get(k).getId(), month_clinic_list_offline.get(k).getProvider_id(),
                                                    month_clinic_list_offline.get(k).getName(), month_clinic_list_offline.get(k).getPhone(), month_clinic_list_offline.get(k).getLocation_name(), month_clinic_list_offline.get(k).getLatitude(),
                                                    month_clinic_list_offline.get(k).getLongitude(), month_clinic_list_offline.get(k).getTime(), month_clinic_list_offline.get(k).getEnd_time(), month_clinic_list_offline.get(k).getPrep_time(),
                                                    month_clinic_list_offline.get(k).getDefault_unfilled_time(), month_clinic_list_offline.get(k).getDate(),
                                                    month_clinic_list_offline.get(k).getCreate_timestamp(), month_clinic_list_offline.get(k).getEstimated_duration(), month_clinic_list_offline.get(k).getPersonnel(), month_clinic_list_offline.get(k).getPrimary_provider(),
                                                    month_clinic_list_offline.get(k).getMedtech_provider(), month_clinic_list_offline.get(k).getOther_provider(),
                                                    month_clinic_list_offline.get(k).getService_provider(),
                                                    month_clinic_list_offline.get(k).getNumber_of_provider(), month_clinic_list_offline.get(k).getJay_walters(), month_clinic_list_offline.get(k).getTimezone(),
                                                    month_clinic_list_offline.get(k).getStatus(), month_clinic_list_offline.get(k).getCreated_at(), month_clinic_list_offline.get(k).getUpdated_at(), month_clinic_list_offline.get(k).getPrimary_name(),
                                                    month_clinic_list_offline.get(k).getMedtech_name(), month_clinic_list_offline.get(k).getSystem_calender(), "1", "clock_in",
                                                    month_clinic_list_offline.get(k).getStatus_name(), month_clinic_list_offline.get(k).getDuration(), month_clinic_list_offline.get(k).getMileage_required(),
                                                    month_clinic_list_offline.get(k).getDrive_time_required(), month_clinic_list_offline.get(k).getMileage_status(), month_clinic_list_offline.get(k).getDrive_time_status(),
                                                    month_clinic_list.get(j).getClinic_spend_time(), month_clinic_list.get(j).getMileage(), month_clinic_list.get(j).getDrive_time()));


                                        }

                                        month_clinic_list_offline.clear();

                                    }
                                }
                           /* } *//*catch (ParseException e) {
                                e.printStackTrace();
                            }*/

                            adapter = new CalendarAdapter(getActivity(), (GregorianCalendar) month, month_clinic_list, viewlist);
                            gridview.setAdapter(adapter);
                        } else {

                            adapter = new CalendarAdapter(getActivity(), (GregorianCalendar) month, month_clinic_list, viewlist);
                            gridview.setAdapter(adapter);
                        }


                    }

                    adapter = new CalendarAdapter(getActivity(), (GregorianCalendar) month, month_clinic_list, viewlist);
                    gridview.setAdapter(adapter);
                }

            } else if (object.has("status") && object.getString("status").equals("error")) {
                month_clinic_list.clear();
                adapter = new CalendarAdapter(getActivity(), (GregorianCalendar) month, month_clinic_list, viewlist);
                gridview.setAdapter(adapter);
                //  alertDialog(object.getString("message"));
            } else if (object.has("status") && object.getString("status").equals("deleted")) {
                SessionManager.clearSession(context);
                ((MainActivity) context).finish();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);

            }


        } catch (JSONException e) {
            e.printStackTrace();
            adapter = new CalendarAdapter(getActivity(), (GregorianCalendar) month, month_clinic_list, viewlist);
            gridview.setAdapter(adapter);
            try {
                final Snackbar snackbarmonth = Snackbar.make(main_layout, "No Internet Connection", (int) 5000L);
                snackbarmonth.setAction("DISMISS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbarmonth.dismiss();
                    }
                });

                snackbarmonth.show();
            } catch (Exception ab) {
                ab.getStackTrace();
            }

        }

    }

    public  void MyclinicDayOffline(){

        try {
            JSONObject object = new JSONObject(SessionManager.getMyClinicDay(getActivity()));
            if (object.has("status") && object.getString("status").equals("success")) {
                day_clinic_list.clear();
                day_clinic_list_temp.clear();
                if (object.has("clinics")) {
                    JSONArray array = object.getJSONArray("clinics");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);

                        day_clinic_list.add(new MyClinicDay(obj.getString("id"), obj.getString("provider_id"),
                                obj.getString("name"), obj.getString("phone"), obj.getString("location_name"), obj.getString("latitude"),
                                obj.getString("longitude"), obj.getString("time"), obj.getString("prep_time"), obj.getString("date"),
                                obj.getString("prep_time_key"), obj.getString("estimated_duration"), obj.getString("personnel"), obj.getString("service_provider"),
                                obj.getString("number_of_provider"), obj.getString("jay_walters"),
                                obj.getString("status"), obj.getString("created_at"), obj.getString("updated_at"),
                                obj.getString("contact_name"), obj.getString("primary_name"), obj.getString("medtech_name"), obj.getString("clocked"),
                                obj.getString("type"),
                                obj.getString("status_name"), obj.getString("duration"), obj.getString("mileage_required"),
                                obj.getString("drive_time_required"), obj.getString("mileage_status"), obj.getString("drive_time_status"),
                                obj.getString("clinic_spend_time")
                        ));

                        if (obj.has("other_name")) {
                            JSONArray arrayother = obj.getJSONArray("other_name");

                            for (int j = 0; j < arrayother.length(); j++) {
                                JSONObject objother = arrayother.getJSONObject(j);
                                viewlistotherday.add(new ViewList(obj.getString("id"), objother.getString("name")));
                            }
                        }
                    }


                    day_clinic_list_offline.clear();
                    for (int j = 0; j < day_clinic_list.size(); j++) {

                        if (!day_clinic_list.get(j).getType().equals("clock_in")) {
                            try {
                                String yourTime = day_clinic_list.get(j).getPrep_time();

                                String today = (String) DateFormat.format(
                                        "hh:mm aa", new Date());

                                SimpleDateFormat sdfff = new SimpleDateFormat("hh:mm aa");
                                Date date1 = sdfff.parse(yourTime);


                                Date date2 = sdfff.parse(today);


                                int time = Integer.parseInt(day_clinic_list.get(j).getCreate_timestamp());
                                if (day_clinic_list.get(j).getStatus_name().equals("My clinic")) {
                                    if (System.currentTimeMillis() / 1000 >= time) {
                                        day_clinic_list_offline.add(new MyClinicDay(day_clinic_list.get(j).getId(), day_clinic_list.get(j).getProvider_id(),
                                                day_clinic_list.get(j).getName(), day_clinic_list.get(j).getPhone(), day_clinic_list.get(j).getLocation_name(), day_clinic_list.get(j).getLatitude(),
                                                day_clinic_list.get(j).getLongitude(), day_clinic_list.get(j).getTime(), day_clinic_list.get(j).getPrep_time(), day_clinic_list.get(j).getDate(),
                                                day_clinic_list.get(j).getCreate_timestamp(), day_clinic_list.get(j).getEstimated_duration(), day_clinic_list.get(j).getPersonnel(), day_clinic_list.get(j).getService_provider(),
                                                day_clinic_list.get(j).getNumber_of_provider(), day_clinic_list.get(j).getJay_walters(),
                                                day_clinic_list.get(j).getStatus(), day_clinic_list.get(j).getCreated_at(), day_clinic_list.get(j).getUpdated_at(),
                                                day_clinic_list.get(j).getContact_name(), day_clinic_list.get(j).getPrimary_name(), day_clinic_list.get(j).getMedtech_name(), "1",
                                                "clock_in",
                                                day_clinic_list.get(j).getStatus_name(), day_clinic_list.get(j).getDuration(), day_clinic_list.get(j).getMileage_required(),
                                                day_clinic_list.get(j).getDrive_time_required(), day_clinic_list.get(j).getMileage_status(), day_clinic_list.get(j).getDrive_time_status(),
                                                day_clinic_list.get(j).getClinic_spend_time()
                                        ));


                                        day_clinic_list.remove(j);

                                        for (int k = 0; k < day_clinic_list_offline.size(); k++) {
                                            day_clinic_list.add(new MyClinicDay(day_clinic_list_offline.get(k).getId(), day_clinic_list_offline.get(k).getProvider_id(),
                                                    day_clinic_list_offline.get(k).getName(), day_clinic_list_offline.get(k).getPhone(), day_clinic_list_offline.get(k).getLocation_name(), day_clinic_list_offline.get(k).getLatitude(),
                                                    day_clinic_list_offline.get(k).getLongitude(), day_clinic_list_offline.get(k).getTime(), day_clinic_list_offline.get(k).getPrep_time(), day_clinic_list_offline.get(k).getDate(),
                                                    day_clinic_list_offline.get(k).getCreate_timestamp(), day_clinic_list_offline.get(k).getEstimated_duration(), day_clinic_list_offline.get(k).getPersonnel(), day_clinic_list_offline.get(k).getService_provider(),
                                                    day_clinic_list_offline.get(k).getNumber_of_provider(), day_clinic_list_offline.get(k).getJay_walters(),
                                                    day_clinic_list_offline.get(k).getStatus(), day_clinic_list_offline.get(k).getCreated_at(), day_clinic_list_offline.get(k).getUpdated_at(),
                                                    day_clinic_list_offline.get(k).getContact_name(), day_clinic_list_offline.get(k).getPrimary_name(), day_clinic_list_offline.get(k).getMedtech_name(), "1",
                                                    "clock_in",
                                                    day_clinic_list_offline.get(k).getStatus_name(), day_clinic_list_offline.get(k).getDuration(), day_clinic_list_offline.get(k).getMileage_required(),
                                                    day_clinic_list_offline.get(k).getDrive_time_required(), day_clinic_list_offline.get(k).getMileage_status(), day_clinic_list_offline.get(k).getDrive_time_status(),
                                                    day_clinic_list_offline.get(k).getClinic_spend_time()
                                            ));


                                        }

                                        day_clinic_list_offline.clear();

                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            day_clinic_list_offline.clear();
                            Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                            gridviewDay.setAdapter(Dayadapter);


                        } else {
                            day_clinic_list_offline.clear();
                            Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                            gridviewDay.setAdapter(Dayadapter);



                        }


                    }

                    day_clinic_list_offline.clear();
                    Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                    gridviewDay.setAdapter(Dayadapter);


                }

            } else if (object.has("status") && object.getString("status").equals("error")) {


                Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                gridviewDay.setAdapter(Dayadapter);

            } else if (object.has("status") && object.getString("status").equals("deleted")) {
                SessionManager.clearSession(context);
                ((MainActivity) context).finish();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);

            }


        } catch (JSONException e) {
            e.printStackTrace();


            try {
                Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                gridviewDay.setAdapter(Dayadapter);
            } catch (Exception bac) {
                bac.getStackTrace();
            }

        }


        intializeRecycler();
    }


    public String getCurrentWeek(Calendar mCalendar) {
        Date date = new Date();
        mCalendar.setTime(date);

        // 1 = Sunday, 2 = Monday, etc.
        int day_of_week = mCalendar.get(Calendar.DAY_OF_WEEK);

        int monday_offset;
        if (day_of_week == 1) {
            monday_offset = -6;
        } else
            monday_offset = (2 - day_of_week); // need to minus back
        mCalendar.add(Calendar.DAY_OF_YEAR, monday_offset);

        Date mDateMonday = mCalendar.getTime();

        // return 6 the next days of current day (object cal save current day)
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date mDateSunday = mCalendar.getTime();

        Date mDateTuesday = mCalendar.getTime();
        //Get format date
        String strDateFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        MONDAY = sdf.format(mDateMonday);

        SUNDAY = sdf.format(mDateSunday);

        String Weekfirstlast = MONDAY + "," + SUNDAY;


        if (AppUtills.isNetworkAvailable(getActivity())) {
            try {
                JSONObject object = new JSONObject();
                object.put("device_id", SessionManager.getDeviceId(getActivity()));
                object.put("platform_type", "android app");
                object.put("user_id", SessionManager.getuserId(getActivity()));
                object.put("latitude", latitude);
                object.put("longitude", longitude);
                object.put("type", "week");
                object.put("type_value", Weekfirstlast);
                String send_data = object.toString();
                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                new MyClinicWeekAsync().execute(encrypted);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            MyClinicWeekOffline();
        }

        // Sub String
        if ((MONDAY.substring(3, 6)).equals(SUNDAY.substring(3, 6))) {
            MONDAY = MONDAY.substring(0, 2);

        }

        return MONDAY + " - " + SUNDAY;
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {


       /* String eventadd = null;
        // Set the new event with duration one hour.
        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        // Create a new event.

        for (int i = 0; i <week_clinic_list.size() ; i++) {
            eventadd   = week_clinic_list.get(i).getName();

        }

        WeekViewEvent event = new WeekViewEvent(20, eventadd, time, endTime);
        mNewEvents.add(event);

        // Refresh the week view. onMonthChange will be called again.
        mWeekView.notifyDatasetChanged();*/

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with the events that was added by tapping on empty view.
        String currentmonth = "";
       /* calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("M");
        currentmonth = sdf.format(calendar.getTime());*/


        Calendar startOfMonth = null;
        String monthname = "";

        startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, newYear);
        startOfMonth.set(Calendar.MONTH, newMonth - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        GregorianCalendar startTime = new GregorianCalendar();

        if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("0")) {
            monthname = "December";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("1")) {
            monthname = "January";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("2")) {
            monthname = "February";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("3")) {
            monthname = "March";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("4")) {
            monthname = "April";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("5")) {
            monthname = "May";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("6")) {
            monthname = "June";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("7")) {
            monthname = "July";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("8")) {
            monthname = "August";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("9")) {
            monthname = "September";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("10")) {
            monthname = "October";
        } else if (String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("11")) {
            monthname = "November";
        }

        week_title.setText(monthname + " " + startOfMonth.get(Calendar.YEAR));

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ArrayList<WeekViewEvent> newEvents = getNewEvents(newYear, newMonth);
        events.addAll(newEvents);

        return events;

    }


    private ArrayList<WeekViewEvent> getNewEvents(int year, int month) {
        Calendar startOfMonth = null;
        Calendar endOfMonth = null;
        startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvents) {

            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                events.add(event);

            }

        }


        return events;


    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        viewlistWeektemp.clear();
        int pos = 0;
        for (int i = 0; i < week_clinic_list.size(); i++) {
            for (int j = 0; j < viewlistWeek.size(); j++) {
                if (week_clinic_list.get(i).getId().equals(viewlistWeek.get(j).getEid())) {

                    viewlistWeektemp.add(new ViewList(viewlistWeek.get(j).getEid(), viewlistWeek.get(j).getName()));

                }

            }
            if (week_clinic_list.get(i).getId().equals(String.valueOf(event.getId()))) {
                pos = i;
            }
        }
            Intent intent = new Intent(getActivity(), MonthClinicWeekDetailActivity.class);
            intent.putExtra("id", week_clinic_list.get(pos).getId());
            intent.putExtra("provider_id", week_clinic_list.get(pos).getProvider_id());
            intent.putExtra("name", week_clinic_list.get(pos).getName());
            intent.putExtra("phone", week_clinic_list.get(pos).getPhone());
            intent.putExtra("location_name", week_clinic_list.get(pos).getLocation_name());
            intent.putExtra("latitude", week_clinic_list.get(pos).getLongitude());
            intent.putExtra("longitude", week_clinic_list.get(pos).getLongitude());
            intent.putExtra("time", week_clinic_list.get(pos).getTime());
            intent.putExtra("end_time", week_clinic_list.get(pos).getEnd_time());
            intent.putExtra("prep_time", week_clinic_list.get(pos).getPrep_time());
            intent.putExtra("default_unfilled_time", week_clinic_list.get(pos).getDefault_unfilled_time());
            intent.putExtra("date", week_clinic_list.get(pos).getDate());
            intent.putExtra("create_timestamp", week_clinic_list.get(pos).getCreate_timestamp());
            intent.putExtra("estimated_duration", week_clinic_list.get(pos).getEstimated_duration());
            intent.putExtra("personnel", week_clinic_list.get(pos).getPersonnel());
            intent.putExtra("primary_provider", week_clinic_list.get(pos).getPrimary_provider());
            intent.putExtra("medtech_provider", week_clinic_list.get(pos).getMedtech_provider());
            intent.putExtra("other_provider", week_clinic_list.get(pos).getOther_provider());
            intent.putExtra("service_provider", week_clinic_list.get(pos).getService_provider());
            intent.putExtra("number_of_provider", week_clinic_list.get(pos).getNumber_of_provider());
            intent.putExtra("jay_walters", week_clinic_list.get(pos).getJay_walters());
            intent.putExtra("timezone", week_clinic_list.get(pos).getTimezone());
            intent.putExtra("status", week_clinic_list.get(pos).getStatus());
            intent.putExtra("created_at", week_clinic_list.get(pos).getCreated_at());
            intent.putExtra("updated_at", week_clinic_list.get(pos).getUpdated_at());
            intent.putExtra("primary_name", week_clinic_list.get(pos).getPrimary_name());
            intent.putExtra("medtech_name", week_clinic_list.get(pos).getMedtech_name());
            intent.putExtra("system_calender", week_clinic_list.get(pos).getSystem_calender());
            intent.putExtra("clocked", week_clinic_list.get(pos).getClocked());
            intent.putExtra("type", week_clinic_list.get(pos).getType());
            intent.putExtra("status_name", week_clinic_list.get(pos).getStatus_name());
            intent.putExtra("duration", week_clinic_list.get(pos).getDuration());
            intent.putExtra("mileage_required", week_clinic_list.get(pos).getMileage_required());
            intent.putExtra("drive_time_required", week_clinic_list.get(pos).getDrive_time_required());
            intent.putExtra("mileage_status", week_clinic_list.get(pos).getMileage_status());
            intent.putExtra("drive_time_status", week_clinic_list.get(pos).getDrive_time_status());
            intent.putExtra("spend_time", week_clinic_list.get(pos).getClinic_spend_time());
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) viewlistWeektemp);
            intent.putExtra("BUNDLE", args);
            startActivity(intent);
    }

    public void MyClinicWeekOffline(){
        mWeekView.setVisibility(View.VISIBLE);

        try {
            JSONObject object = new JSONObject(SessionManager.getMyClinicWeek(getActivity()));
            if (object.has("status") && object.getString("status").equals("success")) {
                week_clinic_list.clear();
                if (object.has("clinics")) {
                    JSONArray array = object.getJSONArray("clinics");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);

                        try {

                            Clinicname = obj.getString("name");
                            system_calenderstatus = obj.getString("system_calender");
                            week_clinic_list.add(new MyClinicWeek(obj.getString("id"), obj.getString("provider_id"),
                                    obj.getString("name"), obj.getString("phone"), obj.getString("location_name"), obj.getString("latitude"),
                                    obj.getString("longitude"), obj.getString("time"), obj.getString("end_time"), obj.getString("prep_time"),
                                    obj.getString("default_unfilled_time"), obj.getString("date"),
                                    obj.getString("prep_time_key"), obj.getString("estimated_duration"), obj.getString("personnel"),
                                    obj.getString("primary_provider"), obj.getString("medtech_provider"), obj.getString("other_provider"),
                                    obj.getString("service_provider"),
                                    obj.getString("number_of_provider"), obj.getString("jay_walters"), obj.getString("timezone"),
                                    obj.getString("status"), obj.getString("created_at"), obj.getString("updated_at"), obj.getString("primary_name"),
                                    obj.getString("medtech_name"), obj.getString("system_calender"), obj.getString("start_year"),
                                    obj.getString("start_month"), obj.getString("start_day"), obj.getString("end_year"),
                                    obj.getString("end_month"), obj.getString("end_day"), obj.getString("start_hour"), obj.getString("start_minute"),
                                    obj.getString("end_hour"), obj.getString("end_minute"), obj.getString("clocked"), obj.getString("type"), obj.getString("status_name"),
                                    obj.getString("duration"), obj.getString("mileage_required"), obj.getString("drive_time_required")
                                    , obj.getString("mileage_status"), obj.getString("drive_time_status")
                                    , obj.getString("clinic_spend_time")

                            ));

                            if (obj.has("other_name")) {
                                JSONArray arrayother = obj.getJSONArray("other_name");

                                for (int j = 0; j < arrayother.length(); j++) {
                                    JSONObject objother = arrayother.getJSONObject(j);
                                    viewlistWeek.add(new ViewList(obj.getString("id"), objother.getString("name")));
                                }
                            }


                            //  }
                                   /* }
*/
                        } catch (Exception e) {
                        }
                    }
                    week_clinic_list_offline.clear();
                    mNewEvents.clear();
                    for (int j = 0; j < week_clinic_list.size(); j++) {

                        if (!week_clinic_list.get(j).getType().equals("clock_in")) {
                            try {
                                int time = Integer.parseInt(week_clinic_list.get(j).getCreate_timestamp());
                                if (week_clinic_list.get(j).getStatus_name().equals("My clinic")) {
                                    if (System.currentTimeMillis() / 1000 >= time) {
                                        week_clinic_list_offline.add(new MyClinicWeek(week_clinic_list.get(j).getId(), week_clinic_list.get(j).getProvider_id(),
                                                week_clinic_list.get(j).getName(), week_clinic_list.get(j).getPhone(), week_clinic_list.get(j).getLocation_name(), week_clinic_list.get(j).getLatitude(),
                                                week_clinic_list.get(j).getLongitude(), week_clinic_list.get(j).getTime(), week_clinic_list.get(j).getEnd_time(), week_clinic_list.get(j).getPrep_time(),
                                                week_clinic_list.get(j).getDefault_unfilled_time(), week_clinic_list.get(j).getDate(),
                                                week_clinic_list.get(j).getCreate_timestamp(), week_clinic_list.get(j).getEstimated_duration(), week_clinic_list.get(j).getPersonnel(),
                                                week_clinic_list.get(j).getPrimary_provider(), week_clinic_list.get(j).getMedtech_provider(), week_clinic_list.get(j).getOther_provider(),
                                                week_clinic_list.get(j).getService_provider(),
                                                week_clinic_list.get(j).getNumber_of_provider(), week_clinic_list.get(j).getJay_walters(), week_clinic_list.get(j).getTimezone(),
                                                week_clinic_list.get(j).getStatus(), week_clinic_list.get(j).getCreated_at(), week_clinic_list.get(j).getUpdated_at(), week_clinic_list.get(j).getPrimary_name(),
                                                week_clinic_list.get(j).getMedtech_name(), week_clinic_list.get(j).getSystem_calender(), week_clinic_list.get(j).getStart_year(),
                                                week_clinic_list.get(j).getStart_month(), week_clinic_list.get(j).getStart_day(), week_clinic_list.get(j).getEnd_year(),
                                                week_clinic_list.get(j).getEnd_month(), week_clinic_list.get(j).getEnd_day(), week_clinic_list.get(j).getStart_hour(), week_clinic_list.get(j).getStart_minute(),
                                                week_clinic_list.get(j).getEnd_hour(), week_clinic_list.get(j).getEnd_minute(), "1", "clock_in", week_clinic_list.get(j).getStatus_name(),
                                                week_clinic_list.get(j).getDuration(), week_clinic_list.get(j).getMileage_required(), week_clinic_list.get(j).getDrive_time_required()
                                                , week_clinic_list.get(j).getMileage_status(), week_clinic_list.get(j).getDrive_time_status(),
                                                week_clinic_list.get(j).getClinic_spend_time()


                                        ));


                                        week_clinic_list.remove(j);

                                        for (int k = 0; k < week_clinic_list_offline.size(); k++) {

                                            week_clinic_list.add(new MyClinicWeek(week_clinic_list_offline.get(k).getId(), week_clinic_list_offline.get(k).getProvider_id(),
                                                    week_clinic_list_offline.get(k).getName(), week_clinic_list_offline.get(k).getPhone(), week_clinic_list_offline.get(k).getLocation_name(), week_clinic_list_offline.get(k).getLatitude(),
                                                    week_clinic_list_offline.get(k).getLongitude(), week_clinic_list_offline.get(k).getTime(), week_clinic_list_offline.get(k).getEnd_time(), week_clinic_list_offline.get(k).getPrep_time(),
                                                    week_clinic_list_offline.get(k).getDefault_unfilled_time(), week_clinic_list_offline.get(k).getDate(),
                                                    week_clinic_list_offline.get(k).getCreate_timestamp(), week_clinic_list_offline.get(k).getEstimated_duration(), week_clinic_list_offline.get(k).getPersonnel(),
                                                    week_clinic_list_offline.get(k).getPrimary_provider(), week_clinic_list_offline.get(k).getMedtech_provider(), week_clinic_list_offline.get(k).getOther_provider(),
                                                    week_clinic_list_offline.get(k).getService_provider(),
                                                    week_clinic_list_offline.get(k).getNumber_of_provider(), week_clinic_list_offline.get(k).getJay_walters(), week_clinic_list_offline.get(k).getTimezone(),
                                                    week_clinic_list_offline.get(k).getStatus(), week_clinic_list_offline.get(k).getCreated_at(), week_clinic_list_offline.get(k).getUpdated_at(), week_clinic_list_offline.get(k).getPrimary_name(),
                                                    week_clinic_list_offline.get(k).getMedtech_name(), week_clinic_list_offline.get(k).getSystem_calender(), week_clinic_list_offline.get(k).getStart_year(),
                                                    week_clinic_list_offline.get(k).getStart_month(), week_clinic_list_offline.get(k).getStart_day(), week_clinic_list_offline.get(k).getEnd_year(),
                                                    week_clinic_list_offline.get(k).getEnd_month(), week_clinic_list_offline.get(k).getEnd_day(), week_clinic_list_offline.get(k).getStart_hour(), week_clinic_list_offline.get(k).getStart_minute(),
                                                    week_clinic_list_offline.get(k).getEnd_hour(), week_clinic_list_offline.get(k).getEnd_minute(), week_clinic_list_offline.get(k).getClocked(), week_clinic_list_offline.get(k).getType(), week_clinic_list_offline.get(k).getStatus_name(),
                                                    week_clinic_list_offline.get(k).getDuration(), week_clinic_list_offline.get(k).getMileage_required(), week_clinic_list_offline.get(k).getDrive_time_required()
                                                    , week_clinic_list_offline.get(k).getMileage_status(), week_clinic_list_offline.get(k).getDrive_time_status(),
                                                    week_clinic_list_offline.get(k).getClinic_spend_time()


                                            ));


                                        }

                                        week_clinic_list_offline.clear();

                                    }
                                    else {
                                        mNewEvents.clear();
                                        for (int i = 0; i < week_clinic_list.size(); i++) {
                                            WeekViewEvent event = new WeekViewEvent(Integer.parseInt(week_clinic_list.get(i).getId()), week_clinic_list.get(i).getName(), Integer.parseInt(week_clinic_list.get(i).getStart_year()), Integer.parseInt(week_clinic_list.get(i).getStart_month())
                                                    , Integer.parseInt(week_clinic_list.get(i).getStart_day()), Integer.parseInt(week_clinic_list.get(i).getStart_hour()),
                                                    Integer.parseInt(week_clinic_list.get(i).getStart_minute()), Integer.parseInt(week_clinic_list.get(i).getEnd_year()),
                                                    Integer.parseInt(week_clinic_list.get(i).getEnd_month()), Integer.parseInt(week_clinic_list.get(i).getEnd_day()),
                                                    Integer.parseInt(week_clinic_list.get(i).getEnd_hour()), Integer.parseInt(week_clinic_list.get(i).getEnd_minute()));


                                            if (week_clinic_list.get(i).getType().equals("Past")) {
                                                event.setColor(getResources().getColor(R.color.txt));
                                            } else {
                                                event.setColor(getResources().getColor(R.color.darkBlue));
                                            }



                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            mNewEvents.clear();
                            for (int i = 0; i < week_clinic_list.size(); i++) {
                                WeekViewEvent event = new WeekViewEvent(Integer.parseInt(week_clinic_list.get(i).getId()), week_clinic_list.get(i).getName(), Integer.parseInt(week_clinic_list.get(i).getStart_year()), Integer.parseInt(week_clinic_list.get(i).getStart_month())
                                        , Integer.parseInt(week_clinic_list.get(i).getStart_day()), Integer.parseInt(week_clinic_list.get(i).getStart_hour()),
                                        Integer.parseInt(week_clinic_list.get(i).getStart_minute()), Integer.parseInt(week_clinic_list.get(i).getEnd_year()),
                                        Integer.parseInt(week_clinic_list.get(i).getEnd_month()), Integer.parseInt(week_clinic_list.get(i).getEnd_day()),
                                        Integer.parseInt(week_clinic_list.get(i).getEnd_hour()), Integer.parseInt(week_clinic_list.get(i).getEnd_minute()));


                                if (week_clinic_list.get(i).getType().equals("Past")) {
                                    event.setColor(getResources().getColor(R.color.txt));
                                } else {
                                    event.setColor(getResources().getColor(R.color.darkBlue));
                                }


                                mNewEvents.add(event);

                            }


                            mWeekView.setVisibility(View.VISIBLE);


                        }


                    }


                }

            } else if (object.has("status") && object.getString("status").equals("error")) {

                week_clinic_list.clear();
                mWeekView.setVisibility(View.VISIBLE);
            } else if (object.has("status") && object.getString("status").equals("deleted")) {
                SessionManager.clearSession(context);
                ((MainActivity) context).finish();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);

            }


        } catch (JSONException e) {

            mWeekView.setVisibility(View.VISIBLE);

            e.printStackTrace();
        }

        // }

        try {
            final Snackbar snackbar = Snackbar.make(main_layout, "No Internet Connection", (int) 5000L);
            snackbar.setAction("DISMISS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });

            snackbar.show();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    private class MyClinicUpcomingAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.getMyClinicUpcoming(APIUrl.MYCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                   // SessionManager.clearSessionupcoming(getActivity());
                    SessionManager.clearclockoutidofflinedata(context);
                    SessionManager.clearclinicidoffline(context);
                    SessionManager.clearclinicTypeoffline(context);
                    JSONObject object = new JSONObject(result);
                    // clinic_upcoming_data.add(object.toString());
                    // SessionManager.addMyClinicUpcoming(getActivity(), clinic_upcoming_data);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        layout_error.setVisibility(View.GONE);
                        month_clinic_list.clear();
                        if (object.has("clinics")) {
                            myclinic_upcoming_list.clear();
                            myclinic_upcoming_listtemp.clear();
                            JSONArray arrayupcoming = object.getJSONArray("clinics");

                            for (int i = 0; i < arrayupcoming.length(); i++) {
                                JSONObject objupcoming = arrayupcoming.getJSONObject(i);


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
                                    for (int j = 0; j < array.length(); j++) {
                                        JSONObject objother = array.getJSONObject(j);
                                        othername_list.add(new ViewListHomeFeed(objupcoming.getString("id"), objother.getString("name")));

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

            } else {
            }

        }
    }


    private class MyClinicMonthAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.getMyClinicMonth(APIUrl.MYCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                   // SessionManager.clearSessionMonth(getActivity());
                    SessionManager.clearclockoutidofflinedata(context);
                    SessionManager.clearclinicidoffline(context);
                    SessionManager.clearclinicTypeoffline(context);
                    JSONObject object = new JSONObject(result);
                    //clinic_month_data.add(object.toString());
                    //  SessionManager.addMyClinicMonth(getActivity(), clinic_month_data);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        month_clinic_list.clear();
                        viewlist.clear();
                        if (object.has("clinics")) {
                            JSONArray array = object.getJSONArray("clinics");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);


                                month_clinic_list.add(new MyClinicMonth(obj.getString("id"), obj.getString("provider_id"),
                                        obj.getString("name"), obj.getString("phone"), obj.getString("location_name"), obj.getString("latitude"),
                                        obj.getString("longitude"), obj.getString("time"), obj.getString("end_time"), obj.getString("prep_time"), obj.getString("default_unfilled_time"), obj.getString("date"),
                                        obj.getString("prep_time_key"), obj.getString("estimated_duration"), obj.getString("personnel"), obj.getString("primary_provider"),
                                        obj.getString("medtech_provider"), obj.getString("other_provider"),
                                        obj.getString("service_provider"),
                                        obj.getString("number_of_provider"), obj.getString("jay_walters"), obj.getString("timezone"),
                                        obj.getString("status"), obj.getString("created_at"), obj.getString("updated_at"), obj.getString("primary_name"),
                                        obj.getString("medtech_name"), obj.getString("system_calender"), obj.getString("clocked"), obj.getString("type"),
                                        obj.getString("status_name"), obj.getString("duration"), obj.getString("mileage_required"),
                                        obj.getString("drive_time_required"), obj.getString("mileage_status"), obj.getString("drive_time_status"),
                                        obj.getString("clinic_spend_time"), obj.getString("mileage"), obj.getString("drive_time")

                                ));

                                if (obj.has("other_name")) {
                                    JSONArray array1 = obj.getJSONArray("other_name");
                                    for (int j = 0; j < array1.length(); j++) {
                                        JSONObject obj_viewlist = array1.getJSONObject(j);
                                        viewlist.add(new ViewList(obj.getString("id"), obj_viewlist.getString("name")));

                                    }
                                }


                            }

                            adapter = new CalendarAdapter(getActivity(), (GregorianCalendar) month, month_clinic_list, viewlist);
                            gridview.setAdapter(adapter);
                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        month_clinic_list.clear();
                        adapter = new CalendarAdapter(getActivity(), (GregorianCalendar) month, month_clinic_list, viewlist);
                        gridview.setAdapter(adapter);
                        //  alertDialog(object.getString("message"));
                    } else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity) context).finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter = new CalendarAdapter(getActivity(), (GregorianCalendar) month, month_clinic_list, viewlist);
                    gridview.setAdapter(adapter);
                }

            } else {
            }

        }
    }


    private class MyClinicDayAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.getMyClinicDay(APIUrl.MYCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                  //  SessionManager.clearSessionday(getActivity());
                    SessionManager.clearclockoutidofflinedata(context);
                    SessionManager.clearclinicidoffline(context);
                    SessionManager.clearclinicTypeoffline(context);
                    JSONObject object = new JSONObject(result);
                    //clinic_day_data.add(object.toString());
                    // SessionManager.addMyClinicDay(getActivity(), clinic_day_data);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        day_clinic_list.clear();
                        if (object.has("clinics")) {
                            JSONArray array = object.getJSONArray("clinics");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                day_clinic_list.add(new MyClinicDay(obj.getString("id"), obj.getString("provider_id"),
                                        obj.getString("name"), obj.getString("phone"), obj.getString("location_name"), obj.getString("latitude"),
                                        obj.getString("longitude"), obj.getString("time"), obj.getString("prep_time"), obj.getString("date"),
                                        obj.getString("prep_time_key"), obj.getString("estimated_duration"), obj.getString("personnel"), obj.getString("service_provider"),
                                        obj.getString("number_of_provider"), obj.getString("jay_walters"),
                                        obj.getString("status"), obj.getString("created_at"), obj.getString("updated_at"),
                                        obj.getString("contact_name"), obj.getString("primary_name"), obj.getString("medtech_name"), obj.getString("clocked"),
                                        obj.getString("type"),
                                        obj.getString("status_name"), obj.getString("duration"), obj.getString("mileage_required"),
                                        obj.getString("drive_time_required"), obj.getString("mileage_status"), obj.getString("drive_time_status"),
                                        obj.getString("clinic_spend_time")
                                ));

                                if (obj.has("other_name")) {
                                    JSONArray arrayother = obj.getJSONArray("other_name");

                                    for (int j = 0; j < arrayother.length(); j++) {
                                        JSONObject objother = arrayother.getJSONObject(j);
                                        viewlistotherday.add(new ViewList(obj.getString("id"), objother.getString("name")));
                                    }
                                }


                            }
                            Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                            gridviewDay.setAdapter(Dayadapter);

                            intializeRecycler();
                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {

                        intializeRecycler();
                        Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                        gridviewDay.setAdapter(Dayadapter);

                    } else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity) context).finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    intializeRecycler();
                    Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
                    gridviewDay.setAdapter(Dayadapter);
                }

            } else {
            }

        }
    }


    private class MyClinicDayCloseAsync extends AsyncTask<String, Integer, String> {

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
                         if (object.has("clinics")) {
                            JSONArray array = object.getJSONArray("clinics");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                day_clinic_list.add(new MyClinicDay(obj.getString("id"), obj.getString("provider_id"),
                                        obj.getString("name"), obj.getString("phone"), obj.getString("location_name"), obj.getString("latitude"),
                                        obj.getString("longitude"), obj.getString("time"), obj.getString("prep_time"), obj.getString("date"),
                                        obj.getString("prep_time_key"), obj.getString("estimated_duration"), obj.getString("personnel"), obj.getString("service_provider"),
                                        obj.getString("number_of_provider"), obj.getString("jay_walters"),
                                        obj.getString("status"), obj.getString("created_at"), obj.getString("updated_at"),
                                        obj.getString("contact_name"), obj.getString("primary_name"), obj.getString("medtech_name"),
                                        obj.getString("clocked"),
                                        obj.getString("type"),
                                        obj.getString("status_name"), obj.getString("duration"), obj.getString("mileage_required"),
                                        obj.getString("drive_time_required"), obj.getString("mileage_status"), obj.getString("drive_time_status"),
                                        obj.getString("clinic_spend_time")
                                ));

                                if (obj.has("other_name")) {
                                    JSONArray arrayother = obj.getJSONArray("other_name");

                                    for (int j = 0; j < arrayother.length(); j++) {
                                        JSONObject objother = arrayother.getJSONObject(j);
                                        viewlistotherday.add(new ViewList(obj.getString("id"), objother.getString("name")));
                                    }
                                }

                            }


                            intializeRecycler();
                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {

                        intializeRecycler();
                        //alertDialog(object.getString("message"));


                    } else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity) context).finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
            }

        }
    }


    private class MyClinicWeekAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.getMyClinicWeek(APIUrl.MYCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                   // SessionManager.clearSessionweek(getActivity());
                    SessionManager.clearclockoutidofflinedata(context);
                    SessionManager.clearclinicidoffline(context);
                    SessionManager.clearclinicTypeoffline(context);
                    JSONObject object = new JSONObject(result);
                    //  clinic_week_data.add(object.toString());
                    // SessionManager.addMyClinicWeek(getActivity(), clinic_week_data);

                    if (object.has("status") && object.getString("status").equals("success")) {
                        week_clinic_list.clear();
                        if (object.has("clinics")) {
                            JSONArray array = object.getJSONArray("clinics");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                Clinicname = obj.getString("name");
                                system_calenderstatus = obj.getString("system_calender");
                                week_clinic_list.add(new MyClinicWeek(obj.getString("id"), obj.getString("provider_id"),
                                        obj.getString("name"), obj.getString("phone"), obj.getString("location_name"), obj.getString("latitude"),
                                        obj.getString("longitude"), obj.getString("time"), obj.getString("end_time"), obj.getString("prep_time"),
                                        obj.getString("default_unfilled_time"), obj.getString("date"),
                                        obj.getString("prep_time_key"), obj.getString("estimated_duration"), obj.getString("personnel"),
                                        obj.getString("primary_provider"), obj.getString("medtech_provider"), obj.getString("other_provider"),
                                        obj.getString("service_provider"),
                                        obj.getString("number_of_provider"), obj.getString("jay_walters"), obj.getString("timezone"),
                                        obj.getString("status"), obj.getString("created_at"), obj.getString("updated_at"), obj.getString("primary_name"),
                                        obj.getString("medtech_name"), obj.getString("system_calender"), obj.getString("start_year"),
                                        obj.getString("start_month"), obj.getString("start_day"), obj.getString("end_year"),
                                        obj.getString("end_month"), obj.getString("end_day"), obj.getString("start_hour"), obj.getString("start_minute"),
                                        obj.getString("end_hour"), obj.getString("end_minute"), obj.getString("clocked"), obj.getString("type"), obj.getString("status_name"),
                                        obj.getString("duration"), obj.getString("mileage_required"), obj.getString("drive_time_required")
                                        , obj.getString("mileage_status"), obj.getString("drive_time_status")
                                        , obj.getString("clinic_spend_time")
                                ));

                                if (obj.has("other_name")) {
                                    JSONArray arrayother = obj.getJSONArray("other_name");

                                    for (int j = 0; j < arrayother.length(); j++) {
                                        JSONObject objother = arrayother.getJSONObject(j);
                                        viewlistWeek.add(new ViewList(obj.getString("id"), objother.getString("name")));
                                    }
                                }


                            }
                            mNewEvents.clear();

                           /* WeekViewEvent events = new WeekViewEvent(0, "",0, 0
                                    , 0, 0,
                                    0, 0,0,0,0,0);
*/
                            for (int i = 0; i < week_clinic_list.size(); i++) {
                                WeekViewEvent event = new WeekViewEvent(Integer.parseInt(week_clinic_list.get(i).getId()), week_clinic_list.get(i).getName(), Integer.parseInt(week_clinic_list.get(i).getStart_year()), Integer.parseInt(week_clinic_list.get(i).getStart_month())
                                        , Integer.parseInt(week_clinic_list.get(i).getStart_day()), Integer.parseInt(week_clinic_list.get(i).getStart_hour()),
                                        Integer.parseInt(week_clinic_list.get(i).getStart_minute()), Integer.parseInt(week_clinic_list.get(i).getEnd_year()),
                                        Integer.parseInt(week_clinic_list.get(i).getEnd_month()), Integer.parseInt(week_clinic_list.get(i).getEnd_day()),
                                        Integer.parseInt(week_clinic_list.get(i).getEnd_hour()), Integer.parseInt(week_clinic_list.get(i).getEnd_minute()));


                                if (week_clinic_list.get(i).getType().equals("Past")) {
                                    event.setColor(getResources().getColor(R.color.txt));
                                } else {
                                    event.setColor(getResources().getColor(R.color.darkBlue));
                                }


                                mNewEvents.add(event);
                                if (week_clinic_list.get(i).getType().equals("Past")){


                                }else {



                                    try {
                                        arrayListgetData = SessionManager.getCalandersyncID(getActivity());
                                    }catch (Exception ee){

                                    }
                                    if (!(arrayListgetData.size()==0)){

                                        for (int j = 0; j <arrayListgetData.size() ; j++) {
                                        if (!week_clinic_list.get(i).getId().equals(arrayListgetData.get(j))){
                                            try {

                                                arrayList.add(week_clinic_list.get(i).getId());


                                            }catch (Exception e){

                                            }

                                            if (system_calenderstatus.equals("2")) {
                                                long calID = 3;
                                                long startMillis = 0;
                                                long endMillis = 0;
                                                Calendar beginTime = Calendar.getInstance();
                                                beginTime.set(Integer.parseInt(week_clinic_list.get(i).getStart_year()), Integer.parseInt(week_clinic_list.get(i).getStart_month()) - 1,
                                                        Integer.parseInt(week_clinic_list.get(i).getStart_day()), Integer.parseInt(week_clinic_list.get(i).getStart_hour()),
                                                        Integer.parseInt(week_clinic_list.get(i).getStart_minute()));
                                                startMillis = beginTime.getTimeInMillis();
                                                Calendar endTime = Calendar.getInstance();
                                                endTime.set(Integer.parseInt(week_clinic_list.get(i).getEnd_year()), Integer.parseInt(week_clinic_list.get(i).getEnd_month()) - 1,
                                                        Integer.parseInt(week_clinic_list.get(i).getEnd_day()), Integer.parseInt(week_clinic_list.get(i).getEnd_hour()),
                                                        Integer.parseInt(week_clinic_list.get(i).getEnd_minute()));
                                                endMillis = endTime.getTimeInMillis();


                                                ContentResolver cr = context.getContentResolver();
                                                ContentValues values = new ContentValues();
                                                values.put(CalendarContract.Events.DTSTART, startMillis);
                                                values.put(CalendarContract.Events.DTEND, endMillis);
                                                values.put(CalendarContract.Events.TITLE, Clinicname);
                                                values.put(CalendarContract.Events.DESCRIPTION, "abc");
                                                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                                                // values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Calcutta");
                                                values.put(CalendarContract.Events.EVENT_TIMEZONE, week_clinic_list.get(i).getTimezone());

                                                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                                            } else if (system_calenderstatus.equals("1")) {
                                                long calID = 3;
                                                long startMillis = 0;
                                                long endMillis = 0;
                                                Calendar beginTime = Calendar.getInstance();
                                                beginTime.set(Integer.parseInt(week_clinic_list.get(i).getStart_year()), Integer.parseInt(week_clinic_list.get(i).getStart_month()),
                                                        Integer.parseInt(week_clinic_list.get(i).getStart_day()), Integer.parseInt(week_clinic_list.get(i).getStart_hour()),
                                                        Integer.parseInt(week_clinic_list.get(i).getStart_minute()));
                                                startMillis = beginTime.getTimeInMillis();
                                                Calendar endTime = Calendar.getInstance();
                                                endTime.set(Integer.parseInt(week_clinic_list.get(i).getEnd_year()), Integer.parseInt(week_clinic_list.get(i).getEnd_month()),
                                                        Integer.parseInt(week_clinic_list.get(i).getEnd_day()), Integer.parseInt(week_clinic_list.get(i).getEnd_hour()),
                                                        Integer.parseInt(week_clinic_list.get(i).getEnd_minute()));
                                                endMillis = endTime.getTimeInMillis();


                                                ContentResolver cr = context.getContentResolver();
                                                ContentValues values = new ContentValues();
                                                values.put(CalendarContract.Events.DTSTART, startMillis);
                                                values.put(CalendarContract.Events.DTEND, endMillis);
                                                values.put(CalendarContract.Events.TITLE, Clinicname);
                                                values.put(CalendarContract.Events.DESCRIPTION, "");
                                                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                                                //values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Calcutta");
                                                values.put(CalendarContract.Events.EVENT_TIMEZONE, week_clinic_list.get(i).getTimezone());

                                                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                                            } else {

                                            }

                                        }

                                    }
                                        try {
                                            SessionManager.addCalandersyncID(getActivity(),arrayList);
                                        }catch (Exception e){

                                        }

                                    }else{

                                        try {
                                            arrayList.add(week_clinic_list.get(i).getId());
                                            SessionManager.addCalandersyncID(getActivity(),arrayList);

                                        }catch (Exception e){

                                        }

                                        if (system_calenderstatus.equals("2")) {
                                            long calID = 3;
                                            long startMillis = 0;
                                            long endMillis = 0;
                                            Calendar beginTime = Calendar.getInstance();
                                            beginTime.set(Integer.parseInt(week_clinic_list.get(i).getStart_year()), Integer.parseInt(week_clinic_list.get(i).getStart_month()) - 1,
                                                    Integer.parseInt(week_clinic_list.get(i).getStart_day()), Integer.parseInt(week_clinic_list.get(i).getStart_hour()),
                                                    Integer.parseInt(week_clinic_list.get(i).getStart_minute()));
                                            startMillis = beginTime.getTimeInMillis();
                                            Calendar endTime = Calendar.getInstance();
                                            endTime.set(Integer.parseInt(week_clinic_list.get(i).getEnd_year()), Integer.parseInt(week_clinic_list.get(i).getEnd_month()) - 1,
                                                    Integer.parseInt(week_clinic_list.get(i).getEnd_day()), Integer.parseInt(week_clinic_list.get(i).getEnd_hour()),
                                                    Integer.parseInt(week_clinic_list.get(i).getEnd_minute()));
                                            endMillis = endTime.getTimeInMillis();


                                            ContentResolver cr = context.getContentResolver();
                                            ContentValues values = new ContentValues();
                                            values.put(CalendarContract.Events.DTSTART, startMillis);
                                            values.put(CalendarContract.Events.DTEND, endMillis);
                                            values.put(CalendarContract.Events.TITLE, Clinicname);
                                            values.put(CalendarContract.Events.DESCRIPTION, "abc");
                                            values.put(CalendarContract.Events.CALENDAR_ID, calID);
                                            // values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Calcutta");
                                            values.put(CalendarContract.Events.EVENT_TIMEZONE, week_clinic_list.get(i).getTimezone());

                                            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                                        } else if (system_calenderstatus.equals("1")) {
                                            long calID = 3;
                                            long startMillis = 0;
                                            long endMillis = 0;
                                            Calendar beginTime = Calendar.getInstance();
                                            beginTime.set(Integer.parseInt(week_clinic_list.get(i).getStart_year()), Integer.parseInt(week_clinic_list.get(i).getStart_month()),
                                                    Integer.parseInt(week_clinic_list.get(i).getStart_day()), Integer.parseInt(week_clinic_list.get(i).getStart_hour()),
                                                    Integer.parseInt(week_clinic_list.get(i).getStart_minute()));
                                            startMillis = beginTime.getTimeInMillis();
                                            Calendar endTime = Calendar.getInstance();
                                            endTime.set(Integer.parseInt(week_clinic_list.get(i).getEnd_year()), Integer.parseInt(week_clinic_list.get(i).getEnd_month()),
                                                    Integer.parseInt(week_clinic_list.get(i).getEnd_day()), Integer.parseInt(week_clinic_list.get(i).getEnd_hour()),
                                                    Integer.parseInt(week_clinic_list.get(i).getEnd_minute()));
                                            endMillis = endTime.getTimeInMillis();


                                            ContentResolver cr = context.getContentResolver();
                                            ContentValues values = new ContentValues();
                                            values.put(CalendarContract.Events.DTSTART, startMillis);
                                            values.put(CalendarContract.Events.DTEND, endMillis);
                                            values.put(CalendarContract.Events.TITLE, Clinicname);
                                            values.put(CalendarContract.Events.DESCRIPTION, "");
                                            values.put(CalendarContract.Events.CALENDAR_ID, calID);
                                            //values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Calcutta");
                                            values.put(CalendarContract.Events.EVENT_TIMEZONE, week_clinic_list.get(i).getTimezone());

                                            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                                        } else {

                                        }

                                    }


                                }


                            }


                            mWeekView.setVisibility(View.VISIBLE);
                 /*           Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_clinic_list);
                            gridviewDay.setAdapter(Dayadapter);*/
                            //  intializeRecycler();
                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        //  alertDialog(object.getString("message"));
                        week_clinic_list.clear();
                        mWeekView.setVisibility(View.VISIBLE);
                    } else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity) context).finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }

                } catch (JSONException e) {
                    mWeekView.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }

            } else {
            }

        }
    }


    private void intializeRecycler() {
      /*  day_clinic_list_temp.clear();
        for (int i = 0; i <day_clinic_list.size() ; i++) {
            day_clinic_list_temp.add(day_clinic_list.get(i).getId());
        }*/
        for (int i = 0; i < day_clinic_list.size(); i++) {
            day_clinic_list_temp.add(day_clinic_list.get(i).getId());

        }
        day_list_Recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        day_list_Recycler.setItemAnimator(new DefaultItemAnimator());
        day_list_Recycler.setAdapter(new MyClinicDayAdapter(getActivity(), day_clinic_list, viewlistotherday, day_clinic_list_temp));
    }


    protected void setNextMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR) + 1),
                    month.getActualMinimum(Calendar.MONTH), 1);
            refreshCalendar();
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
            try {
                SessionManager.setMyClinicMonthnameValue(context, String.valueOf(month));
            }catch (Exception e){

            }

            refreshCalendar();
        }

    }


    protected void setNextMonthDay() {
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR) + 1),
                    month.getActualMinimum(Calendar.MONTH), 1);
            refreshCalendarDay();
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
            try {
                SessionManager.setMyClinicMonthnameValue(context, String.valueOf(month));
            }catch (Exception e){

            }

            refreshCalendarDay();
        }

    }


    protected void setPreviousMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR) - 1),
                    month.getActualMaximum(Calendar.MONTH), 1);
            refreshCalendar();
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
            try {
                SessionManager.setMyClinicMonthnameValue(context, String.valueOf(month));
            }catch (Exception e){

            }

            refreshCalendar();

        }

    }


    protected void setPreviousMonthDay() {
        if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR) - 1),
                    month.getActualMaximum(Calendar.MONTH), 1);
            refreshCalendarDay();
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
            try {
                SessionManager.setMyClinicMonthnameValue(context, String.valueOf(month));
            }catch (Exception e){

            }

            refreshCalendarDay();

        }

    }


    public void refreshCalendarDay() {
        Dayadapter.refreshDays();
        Dayadapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        if (curentMonthnew.equals(DateFormat.format("MMM", month))) {

            titlee.setText("" + dayName + " " + currentDate + "");
            title.setText("" + dayName + " " + currentDate + "");
        } else {
            titlee.setText(DateFormat.format("EEE MMM dd yyyy", month));
            title.setText(DateFormat.format("EEE MMM dd yyyy", month));
        }
        try {
            SessionManager.setMyClinicMonthdateValue(getActivity(), String.valueOf(DateFormat.format("EEE MMM dd yyyy", month)));
        }catch (Exception e){

        }


        Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
        gridviewDay.setAdapter(Dayadapter);


    }


    public void refreshCalendar() {
        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        if (curentMonthnew.equals(DateFormat.format("MMM", month))) {

            title.setText("" + dayName + " " + currentDate + "");
            titlee.setText("" + dayName + " " + currentDate + "");
        } else {

            title.setText(DateFormat.format("EEE MMM dd yyyy", month));
            titlee.setText(DateFormat.format("EEE MMM dd yyyy", month));
        }
        try {
            SessionManager.setMyClinicMonthdateValue(getActivity(), String.valueOf(DateFormat.format("EEE MMM dd yyyy", month)));
        }catch (Exception e){

        }

        try {
            JSONObject object = new JSONObject();
            object.put("device_id", SessionManager.getDeviceId(getActivity()));
            object.put("platform_type", "android app");
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("latitude", latitude);
            object.put("longitude", longitude);
            object.put("type", "month");
            object.put("type_value", DateFormat.format("MMMM", month));
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new MyClinicMonthAsync().execute(encrypted);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();

        }
    };

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

    private void intializeRecyclerMyClinicUpcoming() {
        upcoming_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        upcoming_recycler.setItemAnimator(new DefaultItemAnimator());
        upcoming_recycler.setAdapter(new MyClinicUpcomingAdapter(getActivity(), myclinic_upcoming_list, othername_list, myclinic_upcoming_listtemp));
    }


/*    private void intializeRecyclerMyClinicUpcomingOffline() {
        upcoming_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        upcoming_recycler.setItemAnimator(new DefaultItemAnimator());
        upcoming_recycler.setAdapter(new MyClinicUpcomingAdapter(getActivity(), SessionManager.getMyClinicUpcoming(getActivity()), SessionManager.getMyClinicOtherName(getActivity())));
    }*/


}



