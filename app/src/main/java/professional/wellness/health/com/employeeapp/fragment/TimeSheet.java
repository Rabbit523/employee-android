package professional.wellness.health.com.employeeapp.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.MyOptionsPickerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import professional.wellness.health.com.employeeapp.Adapter.AllAnnoucementAdapter;
import professional.wellness.health.com.employeeapp.Adapter.PayPeriodAdapter;
import professional.wellness.health.com.employeeapp.Adapter.RecyclerViewDay;
import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.GraphdataList;
import professional.wellness.health.com.employeeapp.Model.PeriodGraph;
import professional.wellness.health.com.employeeapp.Model.WeekGraph;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

import static professional.wellness.health.com.employeeapp.R.id.chart1;
import static professional.wellness.health.com.employeeapp.R.id.date;
import static professional.wellness.health.com.employeeapp.R.mipmap.dayview;
import static professional.wellness.health.com.employeeapp.R.mipmap.month;

/**
 * Created by Fujitsu on 03-07-2017.
 */

public class TimeSheet extends Fragment {
    private RegularTextView dayView;
    private RegularTextView weekView;
    private RegularTextView payPeriod;
    public static RegularTextView txt_date;
    private RecyclerView recyclerViewday;
    private RecyclerView payperiodRecycler;
    //  private ImageView img_payview;
    //  private ImageView img_weekview;
    /// private ImageView img_dayview;
    private RegularTextView select_month;
    MyOptionsPickerView twoPicker;
    private List<String> getData;
    private List<String> daysString;
    private List<String> getDataWeek;
    private String weekDay;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private double latitude;
    private double longitude;
    String month;
    private LineChart mChart;

    Calendar calendar;
    String currentDate;
    String dayName;
    String monthnamenew = "";

    public static RegularTextView total_earned;
    public static RegularTextView hours_tracked;
    public static RegularTextView milage_total;
    public static RegularTextView drive_time;
    private LinearLayout period;
    private LinearLayout week;
    private LinearLayout day;
    private TextView per_1;
    private TextView per_2;
    private TextView week1;
    private TextView week2;
    private TextView week3;
    private TextView week4;
    //private TextView week5;

    private RegularTextView gross_income_total;
    private RegularTextView gross_milage_total;
    private RegularTextView gross_drive_time_total;
    private RegularTextView gross_clinic_time_total;

    private ArrayList<PeriodGraph> periodGraph;
    private ArrayList<WeekGraph> weekgraph;
    private ArrayList<GraphdataList> graphdataLists;
    private ArrayList<GraphdataList> graphdataListsday;
    private ArrayList<List> weekArrayList;
    public static String monthname = "";
    String FirstdateCurrentMonth = "";
    String lastdateCurrentMonth = "";
    private List<String> current_month_dates;
    private LinearLayout btn_prev_cal;
    private LinearLayout btn_next_cal;
    public Calendar monthnew;
    private LinearLayout header_layout;
    private LinearLayout layout_nonetwork_message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_sheet, container, false);
        initView(view);


        return view;
    }

    private void initView(View view) {
        header_layout = (LinearLayout) view.findViewById(R.id.header_layout);
        layout_nonetwork_message = (LinearLayout) view.findViewById(R.id.layout_nonetwork_message);
        if (!AppUtills.isNetworkAvailable(getActivity())) {
            header_layout.setVisibility(View.GONE);
            layout_nonetwork_message.setVisibility(View.VISIBLE);
        } else {

            header_layout.setVisibility(View.VISIBLE);
            layout_nonetwork_message.setVisibility(View.GONE);
        }
        current_month_dates = new ArrayList<>();
        monthnew = Calendar.getInstance();
        graphdataLists = new ArrayList<>();
        graphdataListsday = new ArrayList<>();
        weekgraph = new ArrayList<>();
        daysString = new ArrayList<>();
        getData = new ArrayList<>();
        getDataWeek = new ArrayList<>();
        periodGraph = new ArrayList<>();
        restInteraction = RESTInteraction.getInstance(getActivity());
        encryptionClass = EncryptionClass.getInstance(getActivity());


        period = (LinearLayout) view.findViewById(R.id.period);
        week = (LinearLayout) view.findViewById(R.id.week);
        day = (LinearLayout) view.findViewById(R.id.day_month);
        recyclerViewday = (RecyclerView) view.findViewById(R.id.recDay);
        payperiodRecycler = (RecyclerView) view.findViewById(R.id.payperiodRecycler);
        select_month = (RegularTextView) view.findViewById(R.id.select_month);
        total_earned = (RegularTextView) view.findViewById(R.id.total_earned);
        hours_tracked = (RegularTextView) view.findViewById(R.id.hours_tracked);
        milage_total = (RegularTextView) view.findViewById(R.id.milage_total);
        drive_time = (RegularTextView) view.findViewById(R.id.drive_time);
        gross_income_total = (RegularTextView) view.findViewById(R.id.gross_income_total);
        gross_milage_total = (RegularTextView) view.findViewById(R.id.gross_milage_total);
        gross_drive_time_total = (RegularTextView) view.findViewById(R.id.gross_drive_time_total);
        gross_clinic_time_total = (RegularTextView) view.findViewById(R.id.gross_clinic_time_total);
        btn_prev_cal = (LinearLayout) view.findViewById(R.id.btn_prev_cal);
        btn_next_cal = (LinearLayout) view.findViewById(R.id.btn_next_cal);

        per_1 = (TextView) view.findViewById(R.id.first_15);
        per_2 = (TextView) view.findViewById(R.id.second_15);
        week1 = (TextView) view.findViewById(R.id.week_1);
        week2 = (TextView) view.findViewById(R.id.week_2);
        week3 = (TextView) view.findViewById(R.id.week_3);
        week4 = (TextView) view.findViewById(R.id.week_4);
        //week5 = (TextView) view.findViewById(R.id.week_5);
        GPSTracker gps = new GPSTracker(getActivity());

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }


        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        currentDate = sdf.format(calendar.getTime());

        select_month.setText(currentDate);
        dayView = (RegularTextView) view.findViewById(R.id.dayView);
        weekView = (RegularTextView) view.findViewById(R.id.weekView);
        payPeriod = (RegularTextView) view.findViewById(R.id.payPeriod);
        txt_date = (RegularTextView) view.findViewById(R.id.txt_date);

        mChart = (LineChart) view.findViewById(chart1);
        //  mChart.setOnChartValueSelectedListener(this);

        // no description text


        // img_payview = (ImageView) view.findViewById(R.id.img_payview);
        // img_weekview = (ImageView) view.findViewById(R.id.img_weekview);
        //  img_dayview = (ImageView) view.findViewById(R.id.img_dayview);

        //    img_payview.setVisibility(View.VISIBLE);


        select_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  createDialogWithoutDateField();
                twoPicker.show();
            }
        });
        //Two Options PickerView
        twoPicker = new MyOptionsPickerView(getActivity());
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

        twoPicker.setPicker(twoItemsOptions1, twoItemsOptions2, false);
        // twoPicker.setTitle("Month");

        twoPicker.setSelectOptions(6, 0);
        twoPicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                select_month.setText(twoItemsOptions1.get(options1) + " " + twoItemsOptions2.get(option2));
                //   month = twoItemsOptions1.get(options1) + "," + twoItemsOptions2.get(option2);
                monthname = twoItemsOptions1.get(options1);
                month = twoItemsOptions1.get(options1);
                Calendar aCalendar = Calendar.getInstance();
                if (twoItemsOptions1.get(options1).equals("January"))
                    aCalendar.set(Calendar.MONTH, Calendar.JANUARY);
                else if (twoItemsOptions1.get(options1).equals("February")) {
                    aCalendar.set(Calendar.MONTH, Calendar.FEBRUARY);
                } else if (twoItemsOptions1.get(options1).equals("March")) {
                    aCalendar.set(Calendar.MONTH, Calendar.MARCH);
                } else if (twoItemsOptions1.get(options1).equals("April")) {
                    aCalendar.set(Calendar.MONTH, Calendar.APRIL);
                } else if (twoItemsOptions1.get(options1).equals("May")) {
                    aCalendar.set(Calendar.MONTH, Calendar.MAY);
                } else if (twoItemsOptions1.get(options1).equals("June")) {
                    aCalendar.set(Calendar.MONTH, Calendar.JUNE);
                } else if (twoItemsOptions1.get(options1).equals("July")) {
                    aCalendar.set(Calendar.MONTH, Calendar.JULY);
                } else if (twoItemsOptions1.get(options1).equals("August")) {
                    aCalendar.set(Calendar.MONTH, Calendar.AUGUST);
                } else if (twoItemsOptions1.get(options1).equals("September")) {
                    aCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
                } else if (twoItemsOptions1.get(options1).equals("October")) {
                    aCalendar.set(Calendar.MONTH, Calendar.OCTOBER);
                } else if (twoItemsOptions1.get(options1).equals("November")) {
                    aCalendar.set(Calendar.MONTH, Calendar.NOVEMBER);
                } else if (twoItemsOptions1.get(options1).equals("December")) {
                    aCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
                }


                aCalendar.set(Calendar.DATE, 1);
                aCalendar.get(Calendar.DAY_OF_YEAR);

                Date firstDateOfCurrentMonth = aCalendar.getTime();
                String CurrentStringfirst = String.valueOf(firstDateOfCurrentMonth);
                String[] separatedfirst = CurrentStringfirst.split(" ");
                String firstNamefirst = separatedfirst[0];
                String lastNamefirst = separatedfirst[1];
                FirstdateCurrentMonth = separatedfirst[2];
                String weekDay = "";
                int dayOfWeek = aCalendar.get(Calendar.DAY_OF_WEEK);
                int Start = aCalendar.get(Calendar.DAY_OF_YEAR);

                if (Calendar.MONDAY == dayOfWeek) {
                    weekDay = "monday";
                } else if (Calendar.TUESDAY == dayOfWeek) {
                    weekDay = "tuesday";
                } else if (Calendar.WEDNESDAY == dayOfWeek) {
                    weekDay = "wednesday";
                } else if (Calendar.THURSDAY == dayOfWeek) {
                    weekDay = "thursday";
                } else if (Calendar.FRIDAY == dayOfWeek) {
                    weekDay = "friday";
                } else if (Calendar.SATURDAY == dayOfWeek) {
                    weekDay = "saturday";
                } else if (Calendar.SUNDAY == dayOfWeek) {
                    weekDay = "sunday";
                }
                aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date lastDateOfCurrentMonth = aCalendar.getTime();

                String CurrentString = String.valueOf(lastDateOfCurrentMonth);
                String[] separated = CurrentString.split(" ");
                String firstName = separated[0];
                String lastName = separated[1];
                lastdateCurrentMonth = separated[2];
                getData.clear();
                getDataWeek.clear();
                daysString.clear();
                for (int i = Integer.parseInt(FirstdateCurrentMonth); i <= Integer.parseInt(lastdateCurrentMonth); i++) {
                    getData.add(String.valueOf(dayOfWeek));
                    dayOfWeek = dayOfWeek + 1;
                    if (dayOfWeek == 7 + 1) {
                        dayOfWeek = 1;
                    }
                    daysString.add(String.valueOf(i));

                }
                Collections.reverse(daysString);
                for (int i = 0; i < getData.size(); i++) {
                    if (getData.get(i).equals("1")) {
                        getDataWeek.add("Sun");
                    } else if (getData.get(i).equals("2")) {
                        getDataWeek.add("Mon");
                    } else if (getData.get(i).equals("3")) {
                        getDataWeek.add("Tue");
                    } else if (getData.get(i).equals("4")) {
                        getDataWeek.add("Wed");
                    } else if (getData.get(i).equals("5")) {
                        getDataWeek.add("Thu");
                    } else if (getData.get(i).equals("6")) {
                        getDataWeek.add("Fri");
                    } else if (getData.get(i).equals("7")) {
                        getDataWeek.add("Sat");
                    }
                }
                Collections.reverse(getDataWeek);

                intializeRecycler();
                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("month", twoItemsOptions1.get(options1) + "," + twoItemsOptions2.get(option2));
                    object.put("type", "pay_period");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new TimeSheetAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        String date = select_month.getText().toString().trim();
        String s = date;
        StringTokenizer st = new StringTokenizer(s, " ");
        monthname = st.nextToken();
        String yearname = st.nextToken();
        month = monthname + "," + yearname;

        payPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  img_payview.setVisibility(View.VISIBLE);
                //  img_weekview.setVisibility(View.GONE);
                //     img_dayview.setVisibility(View.GONE);
                payPeriod.setBackground(getResources().getDrawable(R.color.red));
                weekView.setBackground(getResources().getDrawable(R.color.darkBlue));
                dayView.setBackground(getResources().getDrawable(R.color.darkBlue));
                period.setVisibility(View.VISIBLE);
                week.setVisibility(View.GONE);
                day.setVisibility(View.GONE);
                per_1.setTextColor(getResources().getColor(R.color.white));

                per_2.setTextColor(getResources().getColor(R.color.grey));
                if (periodGraph.size() == 0) {
                    txt_date.setText(monthname);
                /*for (int i = 0; i < periodGraph.size(); i++) {
                    if (periodGraph.get(i).getPeriod().equals("1st-15th")) {*/
                    total_earned.setText("$ " + "0");
                    milage_total.setText("0" + " miles");
                    drive_time.setText("0" + " hour");
                    hours_tracked.setText("0" + " hour");
                  /*  }
                }*/
                } else {
                    int size = periodGraph.size() - 1;
                    txt_date.setText(periodGraph.get(size).getPeriod() + ", " + monthname);
                /*for (int i = 0; i < periodGraph.size(); i++) {
                    if (periodGraph.get(i).getPeriod().equals("1st-15th")) {*/
                    total_earned.setText("$ " + periodGraph.get(size).getIncome());
                    milage_total.setText(periodGraph.get(size).getMileage());
                    drive_time.setText(periodGraph.get(size).getDrive_time());
                    hours_tracked.setText(periodGraph.get(size).getHours_time());
                }

            }
        });

        weekView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    img_payview.setVisibility(View.GONE);
                //   img_weekview.setVisibility(View.VISIBLE);
                //    img_dayview.setVisibility(View.GONE);
                txt_date.setText("Week of 1st, " + monthname);
                payPeriod.setBackground(getResources().getDrawable(R.color.darkBlue));
                weekView.setBackground(getResources().getDrawable(R.color.red));
                dayView.setBackground(getResources().getDrawable(R.color.darkBlue));
                week.setVisibility(View.VISIBLE);
                period.setVisibility(View.GONE);
                day.setVisibility(View.GONE);
                // mChart.zoom(7, 7, 1, 1);
                week1.setTextColor(getResources().getColor(R.color.white));
                week2.setTextColor(getResources().getColor(R.color.grey));
                week3.setTextColor(getResources().getColor(R.color.grey));
                week4.setTextColor(getResources().getColor(R.color.grey));
                txt_date.setText("Week of 1st, " + monthname);
                // week5.setTextColor(getResources().getColor(R.color.grey));
                for (int i = 0; i < weekgraph.size(); i++) {
                    if (weekgraph.get(i).getWeek().equals("1st week")) {
                        total_earned.setText("$ " + weekgraph.get(i).getIncome());
                        milage_total.setText(weekgraph.get(i).getMileage());
                        drive_time.setText(weekgraph.get(i).getDrive_time());
                        hours_tracked.setText(weekgraph.get(i).getHours_time());
                    }
                }
            }
        });


        dayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                week.setVisibility(View.GONE);
                period.setVisibility(View.GONE);
                day.setVisibility(View.VISIBLE);
                intializeRecycler();
                //  txt_date.setText("Mon September 3rd 2017");
                payPeriod.setBackground(getResources().getDrawable(R.color.darkBlue));
                weekView.setBackground(getResources().getDrawable(R.color.darkBlue));
                dayView.setBackground(getResources().getDrawable(R.color.red));

                for (int i = 0; i < graphdataListsday.size(); i++) {
                    if (graphdataListsday.get(i).getDay().equals("1")) {
                        total_earned.setText("$ " + graphdataListsday.get(i).getIncome());
                        milage_total.setText(graphdataListsday.get(i).getMileage());
                        drive_time.setText(graphdataListsday.get(i).getDrive_time());
                        hours_tracked.setText(graphdataListsday.get(i).getHours_time());
                    }
                }
            }
        });
        //  txt_date.setText("Week of 1st, " + monthname);

        try {
            JSONObject object = new JSONObject();
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("device_id", SessionManager.getDeviceId(getActivity()));
            object.put("platform_type", "android app");
            object.put("latitude", latitude);
            object.put("longitude", longitude);
            object.put("month", month);
            object.put("type", "pay_period");
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new TimeSheetAsync().execute(encrypted);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        per_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per_1.setTextColor(getResources().getColor(R.color.white));
                txt_date.setText("1st Half, " + monthname);
                per_2.setTextColor(getResources().getColor(R.color.grey));

                for (int i = 0; i < periodGraph.size(); i++) {
                    if (periodGraph.get(i).getPeriod().equals("1st-15th")) {
                        total_earned.setText("$ " + periodGraph.get(i).getIncome());
                        milage_total.setText(periodGraph.get(i).getMileage());
                        drive_time.setText(periodGraph.get(i).getDrive_time());
                        hours_tracked.setText(periodGraph.get(i).getHours_time());
                    }
                }
            }

        });
        per_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per_2.setTextColor(getResources().getColor(R.color.white));
                per_1.setTextColor(getResources().getColor(R.color.grey));
                txt_date.setText("2nd Half, " + monthname);
                for (int i = 0; i < periodGraph.size(); i++) {
                    if (periodGraph.get(i).getPeriod().equals("16th-31th")) {
                        total_earned.setText("$ " + periodGraph.get(i).getIncome());
                        milage_total.setText(periodGraph.get(i).getMileage());
                        drive_time.setText(periodGraph.get(i).getDrive_time());
                        hours_tracked.setText(periodGraph.get(i).getHours_time());
                    }
                }
            }

        });
        week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week1.setTextColor(getResources().getColor(R.color.white));
                week2.setTextColor(getResources().getColor(R.color.grey));
                week3.setTextColor(getResources().getColor(R.color.grey));
                week4.setTextColor(getResources().getColor(R.color.grey));
                txt_date.setText("Week of 1st, " + monthname);
                // week5.setTextColor(getResources().getColor(R.color.grey));
                for (int i = 0; i < weekgraph.size(); i++) {
                    if (weekgraph.get(i).getWeek().equals("1st week")) {
                        total_earned.setText("$ " + weekgraph.get(i).getIncome());
                        milage_total.setText(weekgraph.get(i).getMileage());
                        drive_time.setText(weekgraph.get(i).getDrive_time());
                        hours_tracked.setText(weekgraph.get(i).getHours_time());
                    }
                }

            }
        });
        week2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week1.setTextColor(getResources().getColor(R.color.grey));
                week2.setTextColor(getResources().getColor(R.color.white));
                week3.setTextColor(getResources().getColor(R.color.grey));
                week4.setTextColor(getResources().getColor(R.color.grey));
                txt_date.setText("Week of 2nd, " + monthname);
                //week5.setTextColor(getResources().getColor(R.color.grey));
                for (int i = 0; i < weekgraph.size(); i++) {
                    if (weekgraph.get(i).getWeek().equals("2nd week")) {
                        total_earned.setText("$ " + weekgraph.get(i).getIncome());
                        milage_total.setText(weekgraph.get(i).getMileage());
                        drive_time.setText(weekgraph.get(i).getDrive_time());
                        hours_tracked.setText(weekgraph.get(i).getHours_time());
                    }
                }
            }
        });
        week3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week1.setTextColor(getResources().getColor(R.color.grey));
                week2.setTextColor(getResources().getColor(R.color.grey));
                week3.setTextColor(getResources().getColor(R.color.white));
                week4.setTextColor(getResources().getColor(R.color.grey));
                txt_date.setText("Week of 3rd, " + monthname);
                // week5.setTextColor(getResources().getColor(R.color.grey));
                for (int i = 0; i < weekgraph.size(); i++) {
                    if (weekgraph.get(i).getWeek().equals("3rd week")) {
                        total_earned.setText("$ " + weekgraph.get(i).getIncome());
                        milage_total.setText(weekgraph.get(i).getMileage());
                        drive_time.setText(weekgraph.get(i).getDrive_time());
                        hours_tracked.setText(weekgraph.get(i).getHours_time());
                    }
                }
            }
        });
        week4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week1.setTextColor(getResources().getColor(R.color.grey));
                week2.setTextColor(getResources().getColor(R.color.grey));
                week3.setTextColor(getResources().getColor(R.color.grey));
                week4.setTextColor(getResources().getColor(R.color.white));
                txt_date.setText("Week of 4th, " + monthname);
                //week5.setTextColor(getResources().getColor(R.color.grey));
                for (int i = 0; i < weekgraph.size(); i++) {
                    if (weekgraph.get(i).getWeek().equals("4th week")) {
                        total_earned.setText("$ " + weekgraph.get(i).getIncome());
                        milage_total.setText(weekgraph.get(i).getMileage());
                        drive_time.setText(weekgraph.get(i).getDrive_time());
                        hours_tracked.setText(weekgraph.get(i).getHours_time());
                    }
                }
            }
        });

       /* week5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                week1.setTextColor(getResources().getColor(R.color.grey));
                week2.setTextColor(getResources().getColor(R.color.grey));
                week3.setTextColor(getResources().getColor(R.color.grey));
                week4.setTextColor(getResources().getColor(R.color.grey));
                week5.setTextColor(getResources().getColor(R.color.white));
                for (int i = 0; i < weekgraph.size(); i++) {
                    if (weekgraph.get(i).getWeek().equals("4th week")){
                        total_earned.setText(weekgraph.get(i).getIncome());
                        milage_total.setText(weekgraph.get(i).getMileage());
                        drive_time.setText(weekgraph.get(i).getDrive_time());
                        hours_tracked.setText(weekgraph.get(i).getHours_time());
                    }
                }
            }
        });*/


        Calendar aCalendar = Calendar.getInstance();

        aCalendar.add(Calendar.MONTH, 0);

        aCalendar.set(Calendar.DATE, 1);
        aCalendar.get(Calendar.DAY_OF_YEAR);

        Date firstDateOfCurrentMonth = aCalendar.getTime();
        String CurrentStringfirst = String.valueOf(firstDateOfCurrentMonth);
        String[] separatedfirst = CurrentStringfirst.split(" ");
        String firstNamefirst = separatedfirst[0];
        String lastNamefirst = separatedfirst[1];
        FirstdateCurrentMonth = separatedfirst[2];
        String weekDay = "";
        int dayOfWeek = aCalendar.get(Calendar.DAY_OF_WEEK);
        int Start = aCalendar.get(Calendar.DAY_OF_YEAR);

        if (Calendar.MONDAY == dayOfWeek) {
            weekDay = "monday";
        } else if (Calendar.TUESDAY == dayOfWeek) {
            weekDay = "tuesday";
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            weekDay = "wednesday";
        } else if (Calendar.THURSDAY == dayOfWeek) {
            weekDay = "thursday";
        } else if (Calendar.FRIDAY == dayOfWeek) {
            weekDay = "friday";
        } else if (Calendar.SATURDAY == dayOfWeek) {
            weekDay = "saturday";
        } else if (Calendar.SUNDAY == dayOfWeek) {
            weekDay = "sunday";
        }
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDateOfCurrentMonth = aCalendar.getTime();

        String CurrentString = String.valueOf(lastDateOfCurrentMonth);
        String[] separated = CurrentString.split(" ");
        String firstName = separated[0];
        String lastName = separated[1];
        lastdateCurrentMonth = separated[2];
        getData.clear();
        getDataWeek.clear();
        daysString.clear();
        for (int i = Integer.parseInt(FirstdateCurrentMonth); i <= Integer.parseInt(lastdateCurrentMonth); i++) {
            getData.add(String.valueOf(dayOfWeek));
            dayOfWeek = dayOfWeek + 1;
            if (dayOfWeek == 7 + 1) {
                dayOfWeek = 1;
            }
            daysString.add(String.valueOf(i));

        }
        Collections.reverse(daysString);
        for (int i = 0; i < getData.size(); i++) {
            if (getData.get(i).equals("1")) {
                getDataWeek.add("Sun");
            } else if (getData.get(i).equals("2")) {
                getDataWeek.add("Mon");
            } else if (getData.get(i).equals("3")) {
                getDataWeek.add("Tue");
            } else if (getData.get(i).equals("4")) {
                getDataWeek.add("Wed");
            } else if (getData.get(i).equals("5")) {
                getDataWeek.add("Thu");
            } else if (getData.get(i).equals("6")) {
                getDataWeek.add("Fri");
            } else if (getData.get(i).equals("7")) {
                getDataWeek.add("Sat");
            }
        }
        Collections.reverse(getDataWeek);

        final String finalWeekDay = weekDay;
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txt_date.setText(getDataWeek.get());
                for (int i = 0; i < graphdataListsday.size(); i++) {
                    if (graphdataListsday.get(i).getDay().equals("1")) {
                        total_earned.setText("$ " + graphdataListsday.get(i).getIncome());
                        milage_total.setText(graphdataListsday.get(i).getMileage());
                        drive_time.setText(graphdataListsday.get(i).getDrive_time());
                        hours_tracked.setText(graphdataListsday.get(i).getHours_time());
                    }
                }
            }
        });


        btn_prev_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPrevMonth();

            }
        });

        btn_next_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setNextMonth();
            }
        });

    }

    private class TimeSheetAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendTimeSheetRequest(APIUrl.TIMESHEET, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                        // monthname = monthnamenew;
                        if (jsonObject.has("timesheet")) {
                            JSONObject timeshet = jsonObject.getJSONObject("timesheet");
                            if (timeshet.has("gross_info")) {

                                try {
                                    JSONObject obj = timeshet.getJSONObject("gross_info");
                                    gross_income_total.setText("Gross income total " + obj.getString("gross_income_total"));
                                    gross_milage_total.setText(obj.getString("gross_mileage_total"));
                                    gross_drive_time_total.setText(obj.getString("gross_drive_time_total"));
                                    gross_clinic_time_total.setText(obj.getString("gross_hours_time_total"));
                                } catch (Exception e) {

                                }

                            }

                            if (timeshet.has("week_view")) {
                                weekgraph.clear();
                                JSONArray jsonArray = timeshet.getJSONArray("week_view");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObjectwk = jsonArray.getJSONObject(i);
                                    weekgraph.add(new WeekGraph(
                                            jsonObjectwk.getString("mileage"),
                                            jsonObjectwk.getString("drive_time"),
                                            jsonObjectwk.getString("hours_time"),
                                            jsonObjectwk.getString("week"),
                                            jsonObjectwk.getString("income")
                                    ));
                                }
                            }
                            if (timeshet.has("pay_period")) {
                                periodGraph.clear();
                                JSONArray jsonArray = timeshet.getJSONArray("pay_period");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObjectper = jsonArray.getJSONObject(i);
                                    periodGraph.add(new PeriodGraph(
                                            jsonObjectper.getString("mileage"),
                                            jsonObjectper.getString("drive_time"),
                                            jsonObjectper.getString("hours_time"),
                                            jsonObjectper.getString("pay_period"),
                                            jsonObjectper.getString("income")));
                                }

                                txt_date.setText(periodGraph.get(0).getPeriod() + ", " + TimeSheet.monthname);
                                total_earned.setText("$ " + periodGraph.get(0).getIncome());
                                milage_total.setText(periodGraph.get(0).getMileage() + " miles");
                                drive_time.setText(periodGraph.get(0).getDrive_time() + " hour");
                                hours_tracked.setText(periodGraph.get(0).getHours_time() + " hour");
                                Collections.reverse(periodGraph);
                                intializeRecyclerPayPeriod();
                            }

                            if (timeshet.has("graph_data")) {
                                graphdataLists.clear();
                                graphdataListsday.clear();
                                JSONArray jsonArray = timeshet.getJSONArray("graph_data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    graphdataLists.add(new GraphdataList(
                                            jsonObject1.getString("mileage"),
                                            jsonObject1.getString("drive_time"),
                                            jsonObject1.getString("hours_time"),
                                            jsonObject1.getString("day"),
                                            jsonObject1.getString("status"),
                                            jsonObject1.getString("income")));
                                    graphdataListsday.add(graphdataLists.get(i));
                                }
                                mChart.getDescription().setEnabled(false);
                                // mChart.isDrawCubicEnabled(true);
                                Collections.reverse(graphdataListsday);
                                // enable touch gestures
                                mChart.setTouchEnabled(true);
                                mChart.setBackgroundColor(Color.parseColor("#18497a"));

                                mChart.setDragDecelerationFrictionCoef(0.9f);

                                // enable scaling and dragging
                                mChart.setDragEnabled(true);
                                mChart.setScaleEnabled(true);
                                mChart.setDrawGridBackground(false);
                                mChart.setHighlightPerDragEnabled(true);

                                // if disabled, scaling can be done on x- and y-axis separately
                                mChart.setPinchZoom(true);

                                // set an alternative background color

                                // add data


                                //mChart.animateX(2500);
                                // mChart.setVisibleXRangeMaximum(20); // allow 20 values to be displayed at once on the x-axis, not more
                                //mChart.moveViewToX(10);
                                // get the legend (only possible after setting data)
                                Legend l = mChart.getLegend();

                                // modify the legend ...
                                l.setForm(Legend.LegendForm.LINE);
                                l.setEnabled(false);
                                //l.setTypeface(mTfLight);
                                l.setTextSize(11f);
                                l.setTextColor(Color.WHITE);
                                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                                l.setDrawInside(false);
//        l.setYOffset(11f);
                                int maxdate = Integer.parseInt(timeshet.getString("month_last_date"));
                                final XAxis xAxis = mChart.getXAxis();
                                //xAxis.setTypeface(mTfLight);
                                xAxis.setTextSize(11f);
                                xAxis.setTextColor(Color.parseColor("#18497a"));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setAxisMaximum(maxdate);
                                xAxis.setAxisMinimum(0f);
                                xAxis.setDrawGridLines(true);
                                xAxis.setDrawAxisLine(true);

                                int max = Integer.parseInt(timeshet.getString("graph_highest_value"));
                                final YAxis leftAxis = mChart.getAxisLeft();
                                //leftAxis.setTypeface(mTfLight);
                                leftAxis.setTextColor(Color.parseColor("#18497a"));
                                leftAxis.setAxisMaximum(max);
                                leftAxis.setAxisMinimum(0f);
                                leftAxis.setDrawGridLines(true);
                                leftAxis.setGranularityEnabled(true);


                                final YAxis rightAxis = mChart.getAxisRight();
                                //rightAxis.setTypeface(mTfLight);
                                rightAxis.setTextColor(Color.parseColor("#18497a"));
                                rightAxis.setAxisMaximum(max);
                                rightAxis.setAxisMinimum(0f);
                                rightAxis.setDrawGridLines(false);
                                rightAxis.setGranularityEnabled(false);
                                setData(20, 30);
                            }

                            per_1.setTextColor(getResources().getColor(R.color.white));
                            per_2.setTextColor(getResources().getColor(R.color.grey));
                            //txt_date.setText("1st Half, " + monthname);

                            if (!(periodGraph.size() == 0)) {
                                for (int i = 0; i < periodGraph.size(); i++) {
                                    if (periodGraph.get(i).getPeriod().equals("1st-15th")) {
                                        total_earned.setText("$ " + periodGraph.get(i).getIncome());
                                        milage_total.setText(periodGraph.get(i).getMileage());
                                        drive_time.setText(periodGraph.get(i).getDrive_time());
                                        hours_tracked.setText(periodGraph.get(i).getHours_time());
                                    }
                                }
                            }
/*
                        if(jsonObject.has("timesheet")){
                            JSONObject obj1 = jsonObject.getJSONObject("timesheet");
                            total_earned.setText(obj1.getString("total_earned"));
                            hours_tracked.setText(obj1.getString("hours_track"));
                            milage_total.setText(obj1.getString("mileage"));
                            drive_time.setText(obj1.getString("drive_time"));

                        }
*/


                        }
                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        intializeRecyclerPayPeriod();
                        //  alertDialog(jsonObject.getString("message"));
                        txt_date.setText("1st Half, " + monthname);
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

    private void setData(int count, float range) {
        try {


            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            ;
            ArrayList<Entry> yVals2 = new ArrayList<Entry>();
            ArrayList<Entry> yVals3 = new ArrayList<Entry>();
            for (int i = 0; i < graphdataLists.size(); i++) {

                if (!graphdataLists.get(i).getStatus().equals("-1")) {


                    yVals1.add(new Entry(Float.parseFloat(graphdataLists.get(i).getDay()), Float.parseFloat(graphdataLists.get(i).getDrive_time())));


                    yVals2.add(new Entry((Float.parseFloat(graphdataLists.get(i).getDay())), Float.parseFloat(graphdataLists.get(i).getMileage())));


                    yVals3.add(new Entry((Float.parseFloat(graphdataLists.get(i).getDay())), Float.parseFloat(graphdataLists.get(i).getHours_time())));

       /* ArrayList<Entry> yVals4 = new ArrayList<Entry>();

        yVals4.add(new Entry(0,30));
        yVals4.add(new Entry(15, 50));
        yVals4.add(new Entry(30, 60));*/

                }
            }
            LineDataSet set1, set2, set3/*set4*/;

            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
                set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
                set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
                //set4=(LineDataSet) mChart.getData().getDataSetByIndex(3);
                set1.setValues(yVals1);
                set2.setValues(yVals2);
                set3.setValues(yVals3);
                //set4.setValues(yVals4);
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
            } else {
                // create a dataset and give it a type
                set1 = new LineDataSet(yVals1, "DataSet 1");

                set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                set1.setColor(ColorTemplate.getHoloBlue());
                set1.setCircleColor(Color.WHITE);
                set1.setLineWidth(2f);
                set1.setCircleRadius(0f);
                set1.setFillAlpha(65);
                set1.setCubicIntensity(5f);
                set1.setFillColor(ColorTemplate.getHoloBlue());
                set1.setHighLightColor(Color.rgb(244, 117, 117));
                set1.setDrawCircleHole(false);
                set1.setDrawFilled(true);
                //set1.setDrawValues(false);
                set1.setDrawCircles(false);
                set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            /*set1.setMode(set1.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                    ? LineDataSet.Mode.LINEAR
                    : LineDataSet.Mode.CUBIC_BEZIER);*/

                //set1.setFillFormatter(new MyFillFormatter(0f));
                //set1.setDrawHorizontalHighlightIndicator(false);
                //set1.setVisible(false);
                //set1.setCircleHoleColor(Color.WHITE);

                // create a dataset and give it a type
                set2 = new LineDataSet(yVals2, "DataSet 2");
                set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
                set2.setColor(Color.RED);
                set2.setCircleColor(Color.WHITE);
                set2.setLineWidth(2f);
                set2.setCubicIntensity(5f);
                set2.setCircleRadius(0f);
                set2.setFillAlpha(65);
                set2.setFillColor(Color.RED);
                set2.setDrawCircleHole(false);
                //set2.setDrawValues(false);
                set2.setDrawFilled(true);
                set2.setDrawCircles(false);
                set2.setHighLightColor(Color.rgb(244, 117, 117));
                set2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            /*set2.setMode(set2.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                    ? LineDataSet.Mode.LINEAR
                    : LineDataSet.Mode.CUBIC_BEZIER);*/
                //set2.setFillFormatter(new MyFillFormatter(900f));

                set3 = new LineDataSet(yVals3, "DataSet 3");
                set3.setAxisDependency(YAxis.AxisDependency.LEFT);
                set3.setColor(Color.WHITE);
                set3.setCircleColor(Color.WHITE);
                set3.setCubicIntensity(5f);
                set3.setLineWidth(2f);
                set3.setCircleRadius(0f);
                set3.setFillAlpha(65);
                set3.setFillColor(ColorTemplate.colorWithAlpha(Color.WHITE, 200));
                set3.setDrawCircleHole(false);
                set3.setHighLightColor(Color.rgb(244, 117, 117));
                set3.setDrawFilled(true);
                set3.setDrawCircles(false);
                set3.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
           /* set3.setMode(set3.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                    ? LineDataSet.Mode.LINEAR
                    : LineDataSet.Mode.CUBIC_BEZIER);*/

          /*  set4 = new LineDataSet(yVals4, "DataSet 4");
            set4.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set4.setColor(Color.YELLOW);
            set4.setCircleColor(Color.WHITE);
            set4.setLineWidth(2f);
            set4.setCircleRadius(3f);
            set4.setFillAlpha(65);
            set4.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set4.setDrawCircleHole(false);
            set4.setHighLightColor(Color.rgb(244, 117, 117));
            set4.setDrawFilled(true);*/


                // create a data object with the datasets
                LineData data = new LineData(set1, set2, set3/*, set4*/);
                data.setValueTextColor(Color.WHITE);
                data.setValueTextSize(0f);

                // set data

                mChart.setData(data);
                mChart.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*    private void intializeRecyclerDayClose() {
            daycloseRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
            daycloseRecycler.setItemAnimator(new DefaultItemAnimator());
            daycloseRecycler.setAdapter(new DayClinicCloseAdapter(getActivity(), days));
        }*/
    private void intializeRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewday.setLayoutManager(linearLayoutManager);
        recyclerViewday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewday.setAdapter(new RecyclerViewDay(getActivity(), daysString, graphdataListsday, daysString));

    }

    private void intializeRecyclerPayPeriod() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setStackFromEnd(true);
        payperiodRecycler.setLayoutManager(linearLayoutManager);
        payperiodRecycler.setItemAnimator(new DefaultItemAnimator());
        payperiodRecycler.setAdapter(new PayPeriodAdapter(getActivity(), periodGraph));

    }


    protected void setNextMonth() {
      /*  if (monthnew.get(Calendar.MONTH) == monthnew.getActualMaximum(Calendar.MONTH)) {
            monthnew.set((monthnew.get(Calendar.YEAR) + 1),
                    monthnew.getActualMinimum(Calendar.MONTH), 1);
        } else {*/
        monthnew.set(Calendar.MONTH, monthnew.get(Calendar.MONTH) + 1);
        select_month.setText(android.text.format.DateFormat.format("MMMM yyyy", monthnew));
     /*   }*/
        ChangeData();
    }

    protected void ChangeData() {
        String monthchange = select_month.getText().toString().trim();
        String CurrentStringfirst = monthchange;
        String[] separatedfirst = CurrentStringfirst.split(" ");
        monthnamenew = separatedfirst[0];
        String year = separatedfirst[1];
        String changemonthname = monthnamenew + "," + year;
        monthname = select_month.getText().toString();

        try {
            JSONObject object = new JSONObject();
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("device_id", SessionManager.getDeviceId(getActivity()));
            object.put("platform_type", "android app");
            object.put("latitude", latitude);
            object.put("longitude", longitude);
            object.put("month", changemonthname);
            object.put("type", "pay_period");
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new TimeSheetChangeAsync().execute(encrypted);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class TimeSheetChangeAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendTimeSheetRequest(APIUrl.TIMESHEET, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                        monthname = monthnamenew;
                        if (jsonObject.has("timesheet")) {
                            JSONObject timeshet = jsonObject.getJSONObject("timesheet");
                            if (timeshet.has("gross_info")) {
                                JSONObject obj = timeshet.getJSONObject("gross_info");
                                gross_income_total.setText("Gross income total " + obj.getString("gross_income_total"));
                                gross_milage_total.setText(obj.getString("gross_mileage_total"));
                                gross_drive_time_total.setText(obj.getString("gross_drive_time_total"));
                                gross_clinic_time_total.setText(obj.getString("gross_hours_time_total"));
                            }

                            if (timeshet.has("week_view")) {
                                weekgraph.clear();
                                JSONArray jsonArray = timeshet.getJSONArray("week_view");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObjectwk = jsonArray.getJSONObject(i);
                                    weekgraph.add(new WeekGraph(
                                            jsonObjectwk.getString("mileage"),
                                            jsonObjectwk.getString("drive_time"),
                                            jsonObjectwk.getString("hours_time"),
                                            jsonObjectwk.getString("week"),
                                            jsonObjectwk.getString("income")
                                    ));
                                }
                            }
                            if (timeshet.has("pay_period")) {
                                periodGraph.clear();
                                JSONArray jsonArray = timeshet.getJSONArray("pay_period");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObjectper = jsonArray.getJSONObject(i);
                                    periodGraph.add(new PeriodGraph(
                                            jsonObjectper.getString("mileage"),
                                            jsonObjectper.getString("drive_time"),
                                            jsonObjectper.getString("hours_time"),
                                            jsonObjectper.getString("period"),
                                            jsonObjectper.getString("income")));
                                }

                            }

                            if (timeshet.has("graph_data")) {
                                graphdataLists.clear();
                                graphdataListsday.clear();
                                JSONArray jsonArray = timeshet.getJSONArray("graph_data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    graphdataLists.add(new GraphdataList(
                                            jsonObject1.getString("mileage"),
                                            jsonObject1.getString("drive_time"),
                                            jsonObject1.getString("hours_time"),
                                            jsonObject1.getString("day"),
                                            jsonObject1.getString("status"),
                                            jsonObject1.getString("income")));
                                    graphdataListsday.add(graphdataLists.get(i));
                                }
                                mChart.getDescription().setEnabled(false);
                                // mChart.isDrawCubicEnabled(true);
                                Collections.reverse(graphdataListsday);
                                // enable touch gestures
                                mChart.setTouchEnabled(true);
                                mChart.setBackgroundColor(Color.parseColor("#18497a"));

                                mChart.setDragDecelerationFrictionCoef(0.9f);

                                // enable scaling and dragging
                                mChart.setDragEnabled(true);
                                mChart.setScaleEnabled(true);
                                mChart.setDrawGridBackground(false);
                                mChart.setHighlightPerDragEnabled(true);

                                // if disabled, scaling can be done on x- and y-axis separately
                                mChart.setPinchZoom(true);

                                // set an alternative background color

                                // add data


                                //mChart.animateX(2500);
                                // mChart.setVisibleXRangeMaximum(20); // allow 20 values to be displayed at once on the x-axis, not more
                                //mChart.moveViewToX(10);
                                // get the legend (only possible after setting data)
                                Legend l = mChart.getLegend();

                                // modify the legend ...
                                l.setForm(Legend.LegendForm.LINE);
                                l.setEnabled(false);
                                //l.setTypeface(mTfLight);
                                l.setTextSize(11f);
                                l.setTextColor(Color.WHITE);
                                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                                l.setDrawInside(false);
//        l.setYOffset(11f);
                                int maxdate = Integer.parseInt(timeshet.getString("month_last_date"));
                                final XAxis xAxis = mChart.getXAxis();
                                //xAxis.setTypeface(mTfLight);
                                xAxis.setTextSize(11f);
                                xAxis.setTextColor(Color.parseColor("#18497a"));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setAxisMaximum(maxdate);
                                xAxis.setAxisMinimum(0f);
                                xAxis.setDrawGridLines(true);
                                xAxis.setDrawAxisLine(true);

                                int max = Integer.parseInt(timeshet.getString("graph_highest_value"));
                                final YAxis leftAxis = mChart.getAxisLeft();
                                //leftAxis.setTypeface(mTfLight);
                                leftAxis.setTextColor(Color.parseColor("#18497a"));
                                leftAxis.setAxisMaximum(max);
                                leftAxis.setAxisMinimum(0f);
                                leftAxis.setDrawGridLines(true);
                                leftAxis.setGranularityEnabled(true);


                                final YAxis rightAxis = mChart.getAxisRight();
                                //rightAxis.setTypeface(mTfLight);
                                rightAxis.setTextColor(Color.parseColor("#18497a"));
                                rightAxis.setAxisMaximum(max);
                                rightAxis.setAxisMinimum(0f);
                                rightAxis.setDrawGridLines(false);
                                rightAxis.setGranularityEnabled(false);
                                setData(20, 30);
                            }

                            per_1.setTextColor(getResources().getColor(R.color.white));
                            per_2.setTextColor(getResources().getColor(R.color.grey));
                            txt_date.setText("1st Half, " + monthname);

                            if (!(periodGraph.size() == 0)) {
                                for (int i = 0; i < periodGraph.size(); i++) {
                                    if (periodGraph.get(i).getPeriod().equals("1st-15th")) {
                                        total_earned.setText("$ " + periodGraph.get(i).getIncome());
                                        milage_total.setText(periodGraph.get(i).getMileage());
                                        drive_time.setText(periodGraph.get(i).getDrive_time());
                                        hours_tracked.setText(periodGraph.get(i).getHours_time());
                                    }
                                }
                            }
/*
                        if(jsonObject.has("timesheet")){
                            JSONObject obj1 = jsonObject.getJSONObject("timesheet");
                            total_earned.setText(obj1.getString("total_earned"));
                            hours_tracked.setText(obj1.getString("hours_track"));
                            milage_total.setText(obj1.getString("mileage"));
                            drive_time.setText(obj1.getString("drive_time"));

                        }
*/


                        }
                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {

                        //alertDialog(jsonObject.getString("message"));
                        gross_income_total.setText("Gross income total " + "0");
                        gross_milage_total.setText("0" + " ml");
                        gross_drive_time_total.setText("0" + " hr");
                        gross_clinic_time_total.setText("0" + " hr");
                        txt_date.setText("1st Half, " + monthname);
                        total_earned.setText("$ " + "0");
                        milage_total.setText("0" + " miles");
                        drive_time.setText("0" + " hour");
                        hours_tracked.setText("0" + " hour");


                        mChart.getDescription().setEnabled(false);
                        // mChart.isDrawCubicEnabled(true);
                        Collections.reverse(graphdataListsday);
                        // enable touch gestures
                        mChart.setTouchEnabled(true);
                        mChart.setBackgroundColor(Color.parseColor("#18497a"));

                        mChart.setDragDecelerationFrictionCoef(0.9f);

                        // enable scaling and dragging
                        mChart.setDragEnabled(true);
                        mChart.setScaleEnabled(true);
                        mChart.setDrawGridBackground(false);
                        mChart.setHighlightPerDragEnabled(true);

                        // if disabled, scaling can be done on x- and y-axis separately
                        mChart.setPinchZoom(true);

                        // set an alternative background color

                        // add data


                        //mChart.animateX(2500);
                        // mChart.setVisibleXRangeMaximum(20); // allow 20 values to be displayed at once on the x-axis, not more
                        //mChart.moveViewToX(10);
                        // get the legend (only possible after setting data)
                        Legend l = mChart.getLegend();

                        // modify the legend ...
                        l.setForm(Legend.LegendForm.LINE);
                        l.setEnabled(false);
                        //l.setTypeface(mTfLight);
                        l.setTextSize(11f);
                        l.setTextColor(Color.WHITE);
                        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        l.setDrawInside(false);
//        l.setYOffset(11f);
                        int maxdate = Integer.parseInt("30");
                        final XAxis xAxis = mChart.getXAxis();
                        //xAxis.setTypeface(mTfLight);
                        xAxis.setTextSize(11f);
                        xAxis.setTextColor(Color.parseColor("#18497a"));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setAxisMaximum(maxdate);
                        xAxis.setAxisMinimum(0f);
                        xAxis.setDrawGridLines(true);
                        xAxis.setDrawAxisLine(true);

                        int max = Integer.parseInt("600");
                        final YAxis leftAxis = mChart.getAxisLeft();
                        //leftAxis.setTypeface(mTfLight);
                        leftAxis.setTextColor(Color.parseColor("#18497a"));
                        leftAxis.setAxisMaximum(max);
                        leftAxis.setAxisMinimum(0f);
                        leftAxis.setDrawGridLines(true);
                        leftAxis.setGranularityEnabled(true);


                        final YAxis rightAxis = mChart.getAxisRight();
                        //rightAxis.setTypeface(mTfLight);
                        rightAxis.setTextColor(Color.parseColor("#18497a"));
                        rightAxis.setAxisMaximum(max);
                        rightAxis.setAxisMinimum(0f);
                        rightAxis.setDrawGridLines(false);
                        rightAxis.setGranularityEnabled(false);
                        setData(20, 30);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }


    protected void setPrevMonth() {
        monthnew.set(Calendar.MONTH, monthnew.get(Calendar.MONTH) - 1);
        select_month.setText(android.text.format.DateFormat.format("MMMM yyyy", monthnew));

        ChangeData();
    }


}

