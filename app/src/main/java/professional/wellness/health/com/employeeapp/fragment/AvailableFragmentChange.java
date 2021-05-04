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
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import professional.wellness.health.com.employeeapp.Adapter.AvailableAdapter;
import professional.wellness.health.com.employeeapp.Adapter.AvailableDayCalander;
import professional.wellness.health.com.employeeapp.Adapter.AvailableDayListAdapter;
import professional.wellness.health.com.employeeapp.Adapter.AvailableMonthCalander;
import professional.wellness.health.com.employeeapp.Adapter.AvailableUpcomingAdapter;
import professional.wellness.health.com.employeeapp.Adapter.CalendarAdapter;
import professional.wellness.health.com.employeeapp.Adapter.DayCalenderAdapter;
import professional.wellness.health.com.employeeapp.Adapter.MyClinicDayAdapter;
import professional.wellness.health.com.employeeapp.Adapter.menuAdapter;
import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.Available;
import professional.wellness.health.com.employeeapp.Model.AvailableDay;
import professional.wellness.health.com.employeeapp.Model.AvailableMonth;
import professional.wellness.health.com.employeeapp.Model.AvailableNew;
import professional.wellness.health.com.employeeapp.Model.AvailableUpcoming;
import professional.wellness.health.com.employeeapp.Model.AvailableWeek;
import professional.wellness.health.com.employeeapp.Model.MyClinicDay;
import professional.wellness.health.com.employeeapp.Model.MyClinicMonth;
import professional.wellness.health.com.employeeapp.Model.MyClinicWeek;
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
import professional.wellness.health.com.employeeapp.activity.MonthAvailableDetailActivity;
import professional.wellness.health.com.employeeapp.activity.OnSwipeTouchListener;

import static android.media.CamcorderProfile.get;
import static professional.wellness.health.com.employeeapp.R.id.date;

/**
 * Created by Admin on 09-08-2017.
 */

public class AvailableFragmentChange extends Fragment implements MonthLoader.MonthChangeListener, WeekView.EmptyViewClickListener ,WeekView.EventClickListener{
    public Calendar month,monthview, itemmonth, prevMonth;// calendar instances.
    String selectedGridDate;
    public AvailableMonthCalander adapter;// adapter instance
    public AvailableDayCalander Dayadapter;// adapter instance
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
    private ArrayList<MyClinicDay> day_clinic_list;
    private RecyclerView day_list_Recycler;
    GridView gridviewDay;
    private String curentDateString1;
    private String curentMonth;
    String MONDAY;
    String SUNDAY;
    private ArrayList<MyClinicWeek> week_clinic_list;
    private RecyclerView daycloseRecycler;
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

    //old
    private LinearLayout layout_error;
    private RegularTextView text_error;
    private LinearLayout layout_main;
    private RecyclerView availableRecycler;
    private ArrayList<AvailableNew> available_list;
    List<Available> available_id;

     private LinearLayout layout_upcoming_recycler;
     private RecyclerView upcoming_recycler;
    private ArrayList<AvailableUpcoming> available_upcoming_list;
    private ArrayList<AvailableWeek> available_week_list;

    private ArrayList<AvailableMonth> available_month_list;
    private ArrayList<AvailableDay> available_day_list;


    //day close
    private LinearLayout dayclosefirst;
    private LinearLayout dayclosesecond;
    private LinearLayout dayclosethired;
    private LinearLayout dayclosefourth;
    private LinearLayout dayclosefifth;
    private LinearLayout dayclosesix;
    private LinearLayout daycloseseven;
    String monthdayclose ;
    String yeardayclose ;
    private String currentDatecloseday;
    private String curentDaynew="";
    private Runnable run;

    private String handlertext = "0";
    private   AvailableAdapter availableAdapter;
    static boolean setadapter = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.available_fragment_change, container, false);
        context = getActivity();
        setadapter = false;
        try {
            context.startService(new Intent(context,ClinicService.class));
        }catch (Exception e){

        }

        available_week_list = new ArrayList<>();
        available_month_list = new ArrayList<>();
        available_day_list = new ArrayList<>();
        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        titlee = (RegularTextView) view.findViewById(R.id.titlee);
        day_calander_down = (ImageView) view.findViewById(R.id.day_calander_down);
        date_view = (LinearLayout) view.findViewById(R.id.date_view);
        img_previous = (ImageView) view.findViewById(R.id.img_previous);
        img_current_month = (TextView)view.findViewById(R.id.img_current_month);
        img_next = (ImageView) view.findViewById(R.id.img_next);
        ll_week_title = (LinearLayout)view.findViewById(R.id.ll_week_title);
        week_title = (RegularTextView)view.findViewById(R.id.week_title);
        swipe_layout_day = (LinearLayout)view.findViewById(R.id.swipe_layout_day);
        layout_error = (LinearLayout)view.findViewById(R.id.layout_error);
        text_error = (RegularTextView)view.findViewById(R.id.text_error);
        layout_main = (LinearLayout)view.findViewById(R.id.layout_main);
        availableRecycler = (RecyclerView)view.findViewById(R.id.availableRecycler);
        previous_cal_day = (ImageView) view.findViewById(R.id.previous_cal_day);
        next_cal_day = (ImageView) view.findViewById(R.id.next_cal_day);
        img_current_week = (TextView)view.findViewById(R.id.img_current_week);
        layout_upcoming_recycler = (LinearLayout)view.findViewById(R.id.layout_upcoming_recycler);
        upcoming_recycler = (RecyclerView)view.findViewById(R.id.upcoming_recycler);
        available_upcoming_list = new ArrayList<>();
        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);
        // daycloseRecycler = (RecyclerView) view.findViewById(R.id.daycloseRecycler);
        day_list_Recycler = (RecyclerView) view.findViewById(R.id.day_list_Recycler);
        img_current_day = (TextView)view.findViewById(R.id.img_current_day);
        available_id = new ArrayList<Available>();
        available_list = new ArrayList<>();
        txt_date1 =(RegularTextView)view.findViewById(R.id.txt_date1);
        txt_date2 =(RegularTextView)view.findViewById(R.id.txt_date2);
        txt_date3 =(RegularTextView)view.findViewById(R.id.txt_date3);
        txt_date4 =(RegularTextView)view.findViewById(R.id.txt_date4);
        txt_date5 =(RegularTextView)view.findViewById(R.id.txt_date5);
        txt_date6 =(RegularTextView)view.findViewById(R.id.txt_date6);
        txt_date7 =(RegularTextView)view.findViewById(R.id.txt_date7);
        //setupDateTimeInterpreter(false);

        dayclosefirst = (LinearLayout)view.findViewById(R.id.dayclosefirst);
        dayclosesecond = (LinearLayout)view.findViewById(R.id.dayclosesecond);
        dayclosethired = (LinearLayout)view.findViewById(R.id.dayclosethired);
        dayclosefourth = (LinearLayout)view.findViewById(R.id.dayclosefourth);
        dayclosefifth = (LinearLayout)view.findViewById(R.id.dayclosefifth);
        dayclosesix = (LinearLayout)view.findViewById(R.id.dayclosesix);
        daycloseseven = (LinearLayout)view.findViewById(R.id.daycloseseven);

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
                setPreviousMonthDay();
            }
        });
        ll_week_title.setVisibility(View.GONE);

        next_cal_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextMonthDay();
            }
        });

        //current week
        //setupDateTimeInterpreter(true);

        img_current_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("type", "month");
                    object.put("type_value", curentMonth);
                    object.put("latitude",latitude);
                    object.put("longitude", longitude);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new MyClinicMonthAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        month_clinic_list = new ArrayList<>();
        day_clinic_list = new ArrayList<>();
        week_clinic_list = new ArrayList<>();
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPreviousMonth();
            }
        });

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextMonth();
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

  /*      MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                List<WeekViewEvent> events = new ArrayList<>();
                events.add(new WeekViewEvent(1,"",1,1,1,1,1,1,1,1,1,1));

                return events;

            }
        };*/


        title = (RegularTextView) view.findViewById(R.id.title);

        calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
        currentDate = sdf.format(calendar.getTime());

        SimpleDateFormat sdf_ = new SimpleDateFormat("EEE");
        Date date = new Date();
        dayName = sdf_.format(date);
        titlee.setText("" + dayName + " " + currentDate + "");

        title.setText("" + dayName + " " + currentDate + "");
        week_title.setText("" + dayName + " " + currentDate + "");


        month = Calendar.getInstance();


        itemmonth = (Calendar) month.clone();

        items = new ArrayList<String>();


        gridviewDay = (GridView) view.findViewById(R.id.gridviewDay);

       /* Dayadapter = new DayCalenderAdapter(getActivity(), (GregorianCalendar) month, day_list_Recycler, day_clinic_list);
        gridviewDay.setAdapter(Dayadapter);*/


        SimpleDateFormat sdff = new SimpleDateFormat("MM yyyy");
        currentDatecloseday = sdff.format(calendar.getTime());

        String CurrentStringfirst = currentDatecloseday;
        String[] separatedfirst = CurrentStringfirst.split(" ");
        monthdayclose =   separatedfirst[0];
        yeardayclose =   separatedfirst[1];

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
        curentMonth = curentMonthnew+","+curentyear;
        curentDaynew = (String) DateFormat.format("d", b.getTime());
        // current week all day
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("d");


        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1; //add 2 if your week start on monday
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);

            first =  days[0];
            second =  days[1];
            third =  days[2];
            fourth =  days[3];
            fifth =  days[4];
            six =  days[5];
            seven = days[6];

        }
        txt_date1.setText(first);
        txt_date2.setText(second);
        txt_date3.setText(third);
        txt_date4.setText(fourth);
        txt_date5.setText(fifth);
        txt_date6.setText(six);
        txt_date7.setText(seven);


        if(curentDaynew.equals(first)){

            dayclosefirst.setBackgroundResource(R.drawable.round_sky);
        }else{
            dayclosefirst.setBackgroundResource(R.color.white);
        }

        if(curentDaynew.equals(second)){

            dayclosesecond.setBackgroundResource(R.drawable.round_sky);
        }else{
            dayclosesecond.setBackgroundResource(R.color.white);
        }

        if(curentDaynew.equals(third)){

            dayclosethired.setBackgroundResource(R.drawable.round_sky);
        }else{
            dayclosethired.setBackgroundResource(R.color.white);
        }


        if(curentDaynew.equals(fourth)){

            dayclosefourth.setBackgroundResource(R.drawable.round_sky);
        }else{
            dayclosefourth.setBackgroundResource(R.color.white);
        }

        if(curentDaynew.equals(fifth)){

            dayclosefifth.setBackgroundResource(R.drawable.round_sky);
        }else{
            dayclosefifth.setBackgroundResource(R.color.white);
        }


        if(curentDaynew.equals(six)){

            dayclosesix.setBackgroundResource(R.drawable.round_sky);
        }else{
            dayclosesix.setBackgroundResource(R.color.white);
        }


        if(curentDaynew.equals(seven)){

            daycloseseven.setBackgroundResource(R.drawable.round_blue);
        }else{
            daycloseseven.setBackgroundResource(R.color.white);
        }

        // System.out.println(Arrays.toString(days));

        //  intializeRecyclerDClose();
        img_current_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month = Calendar.getInstance();
                itemmonth = (Calendar) month.clone();
                Dayadapter = new AvailableDayCalander(getActivity(), (GregorianCalendar) month, day_list_Recycler, available_day_list);
                gridviewDay.setAdapter(Dayadapter);
                titlee.setText("" + dayName + " " + currentDate + "");

                Date d = new Date();
                curentDateString1 = (String) DateFormat.format("dd-MM-yyyy", d.getTime());
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
            }
        });
        MainActivity.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.img_available.setSelected(false);
                MainActivity.img_calander_blue.setSelected(false);
            }
        });




        MainActivity.img_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.img_available.setSelected(true);
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
                        MainActivity.img_available.setSelected(false);
                      //  MainActivity.img_available.setSelected(false);
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
                            ll_week_title.setVisibility(View.VISIBLE);
                            layout_upcoming_recycler.setVisibility(View.GONE);
                            layout_main.setVisibility(View.GONE);
                            monthView.setVisibility(View.GONE);
                            layout_error.setVisibility(View.GONE);
                            dayView.setVisibility(View.GONE);

                            SessionManager.setAvailableRefreshApiValue(getActivity(),"3");

                            setadapter = false;
                            getCurrentWeek(calendar);

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
                            mWeekView.setVisibility(View.GONE);
                            layout_main.setVisibility(View.GONE);
                            layout_upcoming_recycler.setVisibility(View.GONE);
                            monthView.setVisibility(View.GONE);
                            dayView.setVisibility(View.VISIBLE);
                            layout_error.setVisibility(View.GONE);
                            ll_week_title.setVisibility(View.GONE);
                            setadapter = false;

                            SessionManager.setAvailableRefreshApiValue(getActivity(),"4");

                            Date d = new Date();
                            curentDateString1 = (String) DateFormat.format("dd-MM-yyyy", d.getTime());
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
                            mWeekView.setVisibility(View.GONE);
                            monthView.setVisibility(View.GONE);
                            layout_main.setVisibility(View.VISIBLE);
                            layout_upcoming_recycler.setVisibility(View.GONE);
                            dayView.setVisibility(View.GONE);
                            ll_week_title.setVisibility(View.GONE);
                            setadapter = false;

                            SessionManager.setAvailableRefreshApiValue(getActivity(),"1");

                            try {
                                JSONObject object = new JSONObject();
                                object.put("device_id", "SessionManager.getDeviceId(getActivity())");
                                object.put("platform_type", "android app");
                                object.put("user_id", SessionManager.getuserId(getActivity()));
                                object.put("type", "upcoming");
                                object.put("type_value", "upcoming");
                                object.put("latitude",latitude);
                                object.put("longitude", longitude);
                                String send_data = object.toString();
                                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                                //new availableUpcomingAsync().execute(encrypted);

                                if (AppUtills.isNetworkAvailable(getActivity())){
                                    new availableAsync().execute(encrypted);
                                }else{
                                    layout_main.setVisibility(View.GONE);
                                    layout_error.setVisibility(View.VISIBLE);
                                    text_error.setText("No Internet Connection");
                                }

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
                            mWeekView.setVisibility(View.GONE);
                            monthView.setVisibility(View.VISIBLE);
                            layout_main.setVisibility(View.GONE);
                            layout_upcoming_recycler.setVisibility(View.GONE);
                            dayView.setVisibility(View.GONE);
                            ll_week_title.setVisibility(View.GONE);
                            layout_error.setVisibility(View.GONE);
                            setadapter = false;

                            SessionManager.setAvailableRefreshApiValue(getActivity(),"2");
                          //  SessionManager.setAvailableViewId(getActivity(),"2");
                            try {
                                JSONObject object = new JSONObject();
                                object.put("device_id", SessionManager.getDeviceId(getActivity()));
                                object.put("platform_type", "android app");
                                object.put("user_id", SessionManager.getuserId(getActivity()));
                                object.put("type", "month");
                                object.put("type_value", curentMonth);
                                object.put("latitude",latitude);
                                object.put("longitude", longitude);
                                String send_data = object.toString();
                                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                                new MyClinicMonthAsync().execute(encrypted);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
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

        try {



        }catch (Exception e){
            e.getStackTrace();
        }
        gridview.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            public void onSwipeRight() {

                setPreviousMonth();
            }
            public void onSwipeLeft() {
                setNextMonth();
            }


        });

        gridviewDay.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            public void onSwipeRight() {
                setPreviousMonthDay();

            }
            public void onSwipeLeft() {
                setNextMonthDay();
            }


        });

        img_current_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeekView.goToToday();
             //   week_title.setText("" + dayName + " " + currentDate + "");
            }
        });




/*

        if(SessionManager.getToday(getActivity()).equals("today")){
            mWeekView.setVisibility(View.GONE);
            monthView.setVisibility(View.VISIBLE);
            layout_main.setVisibility(View.GONE);
            layout_upcoming_recycler.setVisibility(View.GONE);
            dayView.setVisibility(View.GONE);
            ll_week_title.setVisibility(View.GONE);
            layout_error.setVisibility(View.GONE);

            try {
                JSONObject object = new JSONObject();
                object.put("device_id", SessionManager.getDeviceId(getActivity()));
                object.put("platform_type", "android app");
                object.put("user_id", SessionManager.getuserId(getActivity()));
                object.put("type", "month");
                object.put("type_value", curentMonth);
                object.put("latitude",latitude);
                object.put("longitude", longitude);
                String send_data = object.toString();
                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                new MyClinicMonthAsync().execute(encrypted);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SessionManager.clearToday(getActivity());
        }else{
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
*/


        handler = new Handler();
        handler.postDelayed(run = new Runnable() {
            @Override
            public void run() {

                if(SessionManager.getAvailableRefreshApiValue(getActivity()).equals("1")){
                    mWeekView.setVisibility(View.GONE);
                    monthView.setVisibility(View.GONE);
                    layout_main.setVisibility(View.VISIBLE);
                    layout_upcoming_recycler.setVisibility(View.GONE);
                    dayView.setVisibility(View.GONE);
                    ll_week_title.setVisibility(View.GONE);
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
                        if (AppUtills.isNetworkAvailable(getActivity())){
                            new availableAsync().execute(encrypted);
                        }else{
                            layout_main.setVisibility(View.GONE);
                            layout_error.setVisibility(View.VISIBLE);
                            text_error.setText("No Internet Connection");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(SessionManager.getAvailableRefreshApiValue(getActivity()).equals("2")){
                    mWeekView.setVisibility(View.GONE);
                    monthView.setVisibility(View.VISIBLE);
                    layout_main.setVisibility(View.GONE);
                    layout_upcoming_recycler.setVisibility(View.GONE);
                    dayView.setVisibility(View.GONE);
                    ll_week_title.setVisibility(View.GONE);
                    layout_error.setVisibility(View.GONE);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("type", "month");
                        object.put("type_value", curentMonth);
                        object.put("latitude",latitude);
                        object.put("longitude", longitude);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new MyClinicMonthAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else if(SessionManager.getAvailableRefreshApiValue(getActivity()).equals("3")){
                    ll_week_title.setVisibility(View.VISIBLE);
                    layout_upcoming_recycler.setVisibility(View.GONE);
                    layout_main.setVisibility(View.GONE);
                    monthView.setVisibility(View.GONE);
                    layout_error.setVisibility(View.GONE);
                    dayView.setVisibility(View.GONE);
                    getCurrentWeek(calendar);

                }else if(SessionManager.getAvailableRefreshApiValue(getActivity()).equals("4")){
                    mWeekView.setVisibility(View.GONE);
                    layout_main.setVisibility(View.GONE);
                    layout_upcoming_recycler.setVisibility(View.GONE);
                    monthView.setVisibility(View.GONE);
                    dayView.setVisibility(View.VISIBLE);
                    layout_error.setVisibility(View.GONE);
                    ll_week_title.setVisibility(View.GONE);
                    Date d = new Date();
                    curentDateString1 = (String) DateFormat.format("dd-MM-yyyy", d.getTime());
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

                }else{
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
                        if (AppUtills.isNetworkAvailable(getActivity())){
                            new availableAsync().execute(encrypted);
                        }else{
                            layout_main.setVisibility(View.GONE);
                            layout_error.setVisibility(View.VISIBLE);
                            text_error.setText("No Internet Connection");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                handler.postDelayed(this, 60000);
            }
        }, 1000);

        ///////////////////

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

                String date = txt_date1.getText().toString().trim()+ "-"+monthdayclose+"-"+yeardayclose;
                available_day_list.clear();

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
                String date = txt_date2.getText().toString().trim()+ "-"+monthdayclose+"-"+yeardayclose;

                available_day_list.clear();
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
                String date = txt_date3.getText().toString().trim()+ "-"+monthdayclose+"-"+yeardayclose;
                available_day_list.clear();
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
                String date = txt_date4.getText().toString().trim()+ "-"+monthdayclose+"-"+yeardayclose;
                available_day_list.clear();
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
                String date = txt_date5.getText().toString().trim()+ "-"+monthdayclose+"-"+yeardayclose;
                available_day_list.clear();
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

                String date = txt_date6.getText().toString().trim()+ "-"+monthdayclose+"-"+yeardayclose;
                available_day_list.clear();
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

                String date = txt_date7.getText().toString().trim()+ "-"+monthdayclose+"-"+yeardayclose;
                available_day_list.clear();
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
        });

      /*  if(SessionManager.getAvailableViewId(getActivity()).equals("1")){
            mWeekView.setVisibility(View.GONE);
            monthView.setVisibility(View.GONE);
            layout_main.setVisibility(View.VISIBLE);
            layout_upcoming_recycler.setVisibility(View.GONE);
            dayView.setVisibility(View.GONE);
            ll_week_title.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject();
                object.put("device_id", "SessionManager.getDeviceId(getActivity())");
                object.put("platform_type", "android app");
                object.put("user_id", SessionManager.getuserId(getActivity()));
                object.put("type", "upcoming");
                object.put("type_value", "upcoming");
                object.put("latitude",latitude);
                object.put("longitude", longitude);
                String send_data = object.toString();
                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                //new availableUpcomingAsync().execute(encrypted);
                new availableAsync().execute(encrypted);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(SessionManager.getAvailableViewId(getActivity()).equals("2")){
            mWeekView.setVisibility(View.GONE);
            monthView.setVisibility(View.VISIBLE);
            layout_main.setVisibility(View.GONE);
            layout_upcoming_recycler.setVisibility(View.GONE);
            dayView.setVisibility(View.GONE);
            ll_week_title.setVisibility(View.GONE);
            layout_error.setVisibility(View.GONE);

            try {
                JSONObject object = new JSONObject();
                object.put("device_id", SessionManager.getDeviceId(getActivity()));
                object.put("platform_type", "android app");
                object.put("user_id", SessionManager.getuserId(getActivity()));
                object.put("type", "month");
                object.put("type_value", curentMonth);
                object.put("latitude",latitude);
                object.put("longitude", longitude);
                String send_data = object.toString();
                String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                new MyClinicMonthAsync().execute(encrypted);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(SessionManager.getAvailableViewId(getActivity()).equals("3")){
            ll_week_title.setVisibility(View.VISIBLE);
            layout_upcoming_recycler.setVisibility(View.GONE);
            layout_main.setVisibility(View.GONE);
            monthView.setVisibility(View.GONE);
            layout_error.setVisibility(View.GONE);
            dayView.setVisibility(View.GONE);
            getCurrentWeek(calendar);
        }else if(SessionManager.getAvailableViewId(getActivity()).equals("4")){
            mWeekView.setVisibility(View.GONE);
            layout_main.setVisibility(View.GONE);
            layout_upcoming_recycler.setVisibility(View.GONE);
            monthView.setVisibility(View.GONE);
            dayView.setVisibility(View.VISIBLE);
            layout_error.setVisibility(View.GONE);
            ll_week_title.setVisibility(View.GONE);

            Date d = new Date();
            curentDateString1 = (String) DateFormat.format("dd-MM-yyyy", d.getTime());
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



        }else{

        }*/






        return view;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

        for (int i = 0; i <available_week_list.size() ; i++) {
            if(available_week_list.get(i).getId().equals(String.valueOf(event.getId()))){
                Intent intent = new Intent(getActivity(), MonthAvailableDetailActivity.class);
                intent.putExtra("id", available_week_list.get(i).getId());
                intent.putExtra("provider_id", available_week_list.get(i).getProvider_id());
                intent.putExtra("name", available_week_list.get(i).getName());
                intent.putExtra("phone", available_week_list.get(i).getPhone());
                intent.putExtra("location_name", available_week_list.get(i).getLocation_name());
                intent.putExtra("latitude", available_week_list.get(i).getLatitude());
                intent.putExtra("longitude", available_week_list.get(i).getLongitude());
                intent.putExtra("time", available_week_list.get(i).getTime());
                intent.putExtra("prep_time", available_week_list.get(i).getPrep_time());
                intent.putExtra("date", available_week_list.get(i).getDate());
                intent.putExtra("estimated_duration", available_week_list.get(i).getEstimated_duration());
                intent.putExtra("personnel", available_week_list.get(i).getPersonnel());
                intent.putExtra("service_provider", available_week_list.get(i).getService_provider());
                intent.putExtra("number_of_provider", available_week_list.get(i).getNumber_of_provider());
                intent.putExtra("jay_wallters", available_week_list.get(i).getJay_walters());
                intent.putExtra("status", available_week_list.get(i).getStatus());
                intent.putExtra("created_at", available_week_list.get(i).getCreated_at());
                intent.putExtra("updated_at", available_week_list.get(i).getUpdated_at());
                intent.putExtra("type", available_week_list.get(i).getType());
                intent.putExtra("status_name", available_week_list.get(i).getStatus_name());
                startActivity(intent);

            }

        }


    }

// available month



    private class availableUpcomingAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.getAvailableUpcomingRequest(APIUrl.AVAILABLE, params[0]);
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
                        available_upcoming_list.clear();

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

                                    available_upcoming_list.add(new AvailableUpcoming(obj.getString("id"),obj.getString("provider_id"),
                                            obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),
                                            obj.getString("latitude"),obj.getString("longitude"),obj.getString("time"),
                                            obj.getString("prep_time"),obj.getString("date"),obj.getString("create_timestamp"),obj.getString("estimated_duration"),
                                            obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                            obj.getString("jay_walters"),
                                            obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),
                                            obj.getString("type"),obj.getString("status_name")));


                                }

                            }
                            intializeRecyclerUpcoming();

                        }

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        //  alertDialog(jsonObject.getString("message"));
                        layout_upcoming_recycler.setVisibility(View.GONE);
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
                            intializeRecyclerAvailable();

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

/*        try {
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
        }*/

        try {
            JSONObject object = new JSONObject();
            object.put("device_id", SessionManager.getDeviceId(getActivity()));
            object.put("platform_type", "android app");
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("type", "week");
            object.put("type_value", Weekfirstlast);
            object.put("latitude",latitude);
            object.put("longitude", longitude);
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new AvailableWeekAsync().execute(encrypted);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

        Calendar startOfMonth = null;
        String monthname = "";

        startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, newYear);
        startOfMonth.set(Calendar.MONTH, newMonth-1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        GregorianCalendar startTime = new GregorianCalendar();

        if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("0")){
            monthname = "December";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("1")){
            monthname = "January";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("2")){
            monthname = "February";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("3")){
            monthname = "March";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("4")){
            monthname = "April";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("5")){
            monthname = "May";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("6")){
            monthname = "June";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("7")){
            monthname = "July";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("8")){
            monthname = "August";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("9")){
            monthname = "September";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("10")){
            monthname = "October";
        }else if(String.valueOf(startOfMonth.get(Calendar.MONTH)).equals("11")){
            monthname = "November";
        }
       if(monthname.equals("December")){
           week_title.setText(monthname+" "+ String.valueOf(startTime.get(Calendar.YEAR)));
       }else{
           week_title.setText(monthname+" "+ startOfMonth.get(Calendar.YEAR));
       }

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

    //myclinic month
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
            return restInteraction.getAvailableRequest(APIUrl.AVAILABLE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        available_month_list.clear();

                        if(object.has("clinics")){
                            JSONArray array = object.getJSONArray("clinics");

                            for (int i = 0; i <array.length() ; i++) {
                                JSONObject obj = array.getJSONObject(i);

                                available_month_list.add(new AvailableMonth(obj.getString("id"),obj.getString("provider_id"),
                                        obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),obj.getString("latitude"),
                                        obj.getString("longitude"),obj.getString("time"),obj.getString("prep_time"),obj.getString("date")
                                        ,obj.getString("create_timestamp"),obj.getString("estimated_duration"),obj.getString("personnel"),obj.getString("service_provider"),
                                        obj.getString("number_of_provider"),obj.getString("jay_walters"),obj.getString("status"),obj.getString("created_at")
                                        ,obj.getString("updated_at"),obj.getString("clinic_name"),obj.getString("type"),obj.getString("status_name")));

                            }
                            adapter = new AvailableMonthCalander(getActivity(), (GregorianCalendar) month, available_month_list);
                            gridview.setAdapter(adapter);
                        }



                    } else if (object.has("status") && object.getString("status").equals("error")) {
                     //   month_clinic_list.clear();
                        available_month_list.clear();
                        adapter = new AvailableMonthCalander(getActivity(), (GregorianCalendar) month, available_month_list);
                        gridview.setAdapter(adapter);

                    }else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity)context).finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    adapter = new AvailableMonthCalander(getActivity(), (GregorianCalendar) month, available_month_list);
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
            return restInteraction.getAvailableRequest(APIUrl.AVAILABLE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        available_day_list.clear();
                        if (object.has("clinics")) {
                            JSONArray array = object.getJSONArray("clinics");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);

                                available_day_list.add(new AvailableDay(obj.getString("id"),obj.getString("provider_id"),obj.getString("name")
                                ,obj.getString("phone"),obj.getString("location_name"),obj.getString("latitude"),obj.getString("longitude"),
                                        obj.getString("time"),obj.getString("prep_time"),obj.getString("default_unfilled_time"),obj.getString("date")
                                ,obj.getString("create_timestamp"),obj.getString("estimated_duration"),obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                        obj.getString("jay_walters"),obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),obj.getString("clinic_name"),
                                        obj.getString("type"),obj.getString("status_name")));

                            }


                            Dayadapter = new AvailableDayCalander(getActivity(), (GregorianCalendar) month,day_list_Recycler, available_day_list);
                            gridviewDay.setAdapter(Dayadapter);

                            intializeRecycler();
                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        intializeRecycler();
                        //alertDialog(object.getString("message"));
                        Dayadapter = new AvailableDayCalander(getActivity(), (GregorianCalendar) month, day_list_Recycler, available_day_list);
                        gridviewDay.setAdapter(Dayadapter);
                    }else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity)context).finish();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Dayadapter = new AvailableDayCalander(getActivity(), (GregorianCalendar) month, day_list_Recycler, available_day_list);
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
            return restInteraction.getAvailableRequest(APIUrl.AVAILABLE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        available_day_list.clear();
                        if (object.has("clinics")) {
                            JSONArray array = object.getJSONArray("clinics");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);

                                available_day_list.add(new AvailableDay(obj.getString("id"),obj.getString("provider_id"),obj.getString("name")
                                        ,obj.getString("phone"),obj.getString("location_name"),obj.getString("latitude"),obj.getString("longitude"),
                                        obj.getString("time"),obj.getString("prep_time"),obj.getString("default_unfilled_time"),obj.getString("date")
                                        ,obj.getString("create_timestamp"),obj.getString("estimated_duration"),obj.getString("personnel"),obj.getString("service_provider"),obj.getString("number_of_provider"),
                                        obj.getString("jay_walters"),obj.getString("status"),obj.getString("created_at"),obj.getString("updated_at"),obj.getString("clinic_name"),
                                        obj.getString("type"),obj.getString("status_name")));

                            }




                            intializeRecycler();
                        }

                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        intializeRecycler();
                        //alertDialog(object.getString("message"));


                    }else if (object.has("status") && object.getString("status").equals("deleted")) {
                        SessionManager.clearSession(context);
                        ((MainActivity)context).finish();
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


    // my clinic Week
    private class AvailableWeekAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.getAvailableRequest(APIUrl.AVAILABLE, params[0]);
        }

      @Override
      protected void onPostExecute(String result) {
          super.onPostExecute(result);
          progressDialog.dismiss();
          if (!result.equals("")) {

              try {
                  JSONObject object = new JSONObject(result);
                  if (object.has("status") && object.getString("status").equals("success")) {
                      available_week_list.clear();
                      if (object.has("clinics")) {
                          JSONArray array = object.getJSONArray("clinics");
                          for (int i = 0; i <array.length() ; i++) {
                              JSONObject obj = array.getJSONObject(i);
                              available_week_list.add(new AvailableWeek(obj.getString("id"),obj.getString("provider_id")
                                      ,obj.getString("name"),obj.getString("phone"),obj.getString("location_name"),obj.getString("latitude")
                                      ,obj.getString("longitude"),obj.getString("time"),obj.getString("prep_time"),obj.getString("date"),
                                      obj.getString("create_timestamp"),obj.getString("estimated_duration"),obj.getString("personnel"),obj.getString("service_provider")
                                      ,obj.getString("number_of_provider"),obj.getString("jay_walters"),obj.getString("status"),obj.getString("created_at"),
                                      obj.getString("updated_at"),obj.getString("clinic_name"),obj.getString("start_year"),obj.getString("start_month"),obj.getString("start_day"),
                                      obj.getString("end_year"),obj.getString("end_month"),obj.getString("end_day"),obj.getString("start_hour"),
                                      obj.getString("start_minute"),obj.getString("start_meridiem"),obj.getString("end_hour"),obj.getString("end_minute"),
                                      obj.getString("end_meridiem"),obj.getString("type"),obj.getString("status_name"),obj.getString("duration"),
                                      obj.getString("mileage_required"),obj.getString("drive_time_required"),obj.getString("mileage_status"),obj.getString("drive_time_status")
                              ));


                          }


                          for (int i = 0; i < available_week_list.size(); i++) {
                              WeekViewEvent event = new WeekViewEvent(Integer.parseInt(available_week_list.get(i).getId()),available_week_list.get(i).getClinic_name() ,Integer.parseInt(available_week_list.get(i).getStart_year()), Integer.parseInt(available_week_list.get(i).getStart_month())
                                      , Integer.parseInt(available_week_list.get(i).getStart_day()), Integer.parseInt(available_week_list.get(i).getStart_hour()),
                                      Integer.parseInt(available_week_list.get(i).getStart_minute()), Integer.parseInt(available_week_list.get(i).getEnd_year()),
                                      Integer.parseInt(available_week_list.get(i).getEnd_month()),Integer.parseInt(available_week_list.get(i).getEnd_day()),
                                      Integer.parseInt(available_week_list.get(i).getEnd_hour()),Integer.parseInt(available_week_list.get(i).getEnd_minute())  );


                              if(available_week_list.get(i).getType().equals("Past")){
                                  event.setColor(getResources().getColor(R.color.txt));
                              }else{
                                  event.setColor(getResources().getColor(R.color.lightblue));
                              }


                              mNewEvents.add(event);



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
                  }else if (object.has("status") && object.getString("status").equals("deleted")) {
                      SessionManager.clearSession(context);
                      ((MainActivity)context).finish();
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

    private void intializeRecyclerAvailable() {
  /*      availableRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        availableRecycler.setItemAnimator(new DefaultItemAnimator());
        availableRecycler.setAdapter(new AvailableAdapter(getActivity(), available_list ,available_id))*/;


        if (!setadapter) {
            availableRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            availableAdapter = new AvailableAdapter(getActivity(),  available_list ,available_id);
            availableRecycler.setAdapter(availableAdapter);
            setadapter = true;
        } else {
            availableAdapter.notifyDataSetChanged();
        }
    }
    private void intializeRecycler() {
        day_list_Recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        day_list_Recycler.setItemAnimator(new DefaultItemAnimator());
        day_list_Recycler.setAdapter(new AvailableDayListAdapter(getActivity(), available_day_list));
    }



/*    private void intializeRecyclerAvaiableMonth() {
        adapter = new AvailableMonthCalander(getActivity(), (GregorianCalendar) month, month_clinic_list);
        gridview.setAdapter(adapter);

    }*/

    private void intializeRecyclerUpcoming() {
        upcoming_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        upcoming_recycler.setItemAnimator(new DefaultItemAnimator());
        upcoming_recycler.setAdapter(new AvailableUpcomingAdapter(getActivity(), available_upcoming_list));
    }


/*    private void intializeRecyclerDayClose() {
        daycloseRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        daycloseRecycler.setItemAnimator(new DefaultItemAnimator());
        daycloseRecycler.setAdapter(new DayClinicCloseAdapter(getActivity(), days));
    }*/

    protected void setNextMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR) + 1),
                    month.getActualMinimum(Calendar.MONTH), 1);
            refreshCalendar();
        } else {
            month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);

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

            refreshCalendarDay();
        }

    }


    protected void setPreviousMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR) - 1),
                    month.getActualMaximum(Calendar.MONTH), 1);
            refreshCalendar();
        } else {
            monthview.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
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
            refreshCalendarDay();

        }

    }


    public void refreshCalendarDay() {
        Dayadapter.refreshDays();
        Dayadapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        if(curentMonthnew.equals(DateFormat.format("MMM", month))){

            titlee.setText("" + dayName + " " + currentDate + "");

            title.setText("" + dayName + " " + currentDate + "");
        }else{
            titlee.setText(DateFormat.format("EEE MMM dd yyyy", month));
            title.setText(DateFormat.format("EEE MMM dd yyyy", month));
        }


        Dayadapter = new AvailableDayCalander(getActivity(), (GregorianCalendar) month,day_list_Recycler, available_day_list);
        gridviewDay.setAdapter(Dayadapter);

    }


    public void refreshCalendar() {
        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        if(curentMonthnew.equals(DateFormat.format("MMM", month))){

            title.setText("" + dayName + " " + currentDate + "");
            titlee.setText("" + dayName + " " + currentDate + "");
        }else{

            title.setText(DateFormat.format("EEE MMM dd yyyy", month));
            titlee.setText(DateFormat.format("EEE MMM dd yyyy", month));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeHandler();
    }

    public void removeHandler()
    {
        Log.i("Stop Handler ","Yes");
        handler.removeCallbacks(run);
    }


/*
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
               // SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("E", Locale.getDefault());

                SimpleDateFormat format = new SimpleDateFormat("E M/d", Locale.getDefault());
                String weekday = format.format(date.getTime());
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                if (hour == 24) hour = 0;
                if (hour == 0) hour = 0;
                return hour + ":00";
            }
        });
    }
*/
}
