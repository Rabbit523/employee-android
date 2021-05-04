package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import professional.wellness.health.com.employeeapp.Model.AvailableMonth;
import professional.wellness.health.com.employeeapp.Model.AvailableNew;
import professional.wellness.health.com.employeeapp.Model.MyClinicMonth;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.MonthAvailableDetailActivity;
import professional.wellness.health.com.employeeapp.activity.MonthClinicDetailAcivity;

/**
 * Created by Admin on 10-08-2017.
 */

public class AvailableMonthCalander extends BaseAdapter {
    private Context mContext;
    private java.util.Calendar month;
    public GregorianCalendar pmonth;
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int mnthlength;
    String itemvalue, curentDateString;
    SimpleDateFormat df;
    private ArrayList<String> items;
    public static List<String> dayString;
    private View previousView;
    LinearLayout ll_selected;
    private ArrayList<AvailableMonth> month_clinic_list;
    private ArrayList<AvailableMonth> temp;
    String date;
    public AvailableMonthCalander(Context c, GregorianCalendar monthCalendar, ArrayList<AvailableMonth> month_clinic_list) {
        CalendarAdapter.dayString = new ArrayList<String>();
        month = monthCalendar;
        this.month_clinic_list = month_clinic_list;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        mContext = c;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date d = new Date();
        //  curentDateString = df.format(selectedDate.getTime());
        curentDateString  = (String) DateFormat.format("dd-MM-yyyy", d.getTime());
        temp =  new ArrayList<>();
        dayString = new ArrayList<>();
        refreshDays();
    }


    public void setItems(ArrayList<String> items) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;

        RegularTextView dateItem;
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);

        }
        dayView = (TextView) v.findViewById(R.id.date);
        dateItem = (RegularTextView) v.findViewById(R.id.dateItem);
        ll_selected = (LinearLayout)v.findViewById(R.id.ll_selected);

        String[] separatedTime = dayString.get(position).split("-");



        String gridvalue = separatedTime[0].replaceFirst("^0*", "");

        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {

            dayView.setTextColor(Color.parseColor("#E1E1E1"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.parseColor("#E1E1E1"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            if(dayString.get(position).equals(curentDateString)){
                dayView.setTextColor(Color.parseColor("#f12b2b"));
            }else
                dayView.setTextColor(Color.parseColor("#9eA4Af"));
        }



        for (int i = 0; i <month_clinic_list.size() ; i++) {
            if(dayString.get(position).equals(month_clinic_list.get(i).getDate())){
                if (month_clinic_list.get(i).getName().equals("Past clinic")){
                    setSelectedPast(v);
                }
                else

                if(month_clinic_list.get(i).getName().equals("null")){

                }else if(month_clinic_list.get(i).getName().equals("")){

                }else
                    setSelected(v);
                dateItem.setText(month_clinic_list.get(i).getName());
                previousView = v;
            }else{

                v.setBackgroundResource(R.color.white);

            }
        }


        dayView.setText(gridvalue);

        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }


        ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.INVISIBLE);
        }


        ll_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp.clear();
                for (int i = 0; i <month_clinic_list.size() ; i++) {

                    if (month_clinic_list.get(i).getDate().equals(dayString.get(position))){
                        temp.add(new AvailableMonth(month_clinic_list.get(i).getId(), month_clinic_list.get(i).getProvider_id(),
                                month_clinic_list.get(i).getName(), month_clinic_list.get(i).getPhone(),month_clinic_list.get(i).getLocation_name(),month_clinic_list.get(i).getLatitude(),
                                month_clinic_list.get(i).getLongitude(),month_clinic_list.get(i).getTime(),month_clinic_list.get(i).getPrep_time(),month_clinic_list.get(i).getDate(),
                                month_clinic_list.get(i).getCreate_timestamp(),month_clinic_list.get(i).getEstimated_duration(),month_clinic_list.get(i).getPersonnel(),month_clinic_list.get(i).getService_provider(),
                                month_clinic_list.get(i).getNumber_of_provider(),month_clinic_list.get(i).getJay_walters(),
                                month_clinic_list.get(i).getStatus(),month_clinic_list.get(i).getCreated_at(),month_clinic_list.get(i).getUpdated_at(),month_clinic_list.get(i).getClinic_name(),month_clinic_list.get(i).getType(),
                                month_clinic_list.get(i).getStatus_name()
                        ));

                    }
                }

                if(temp.size() >1){
                    alertDialogEvent();
                }else{
                    for (int j = 0; j < temp.size(); j++) {
                        Intent intent = new Intent(mContext, MonthAvailableDetailActivity.class);
                        intent.putExtra("id", temp.get(j).getId());
                        intent.putExtra("provider_id", temp.get(j).getProvider_id());
                        intent.putExtra("name", temp.get(j).getName());
                        intent.putExtra("phone", temp.get(j).getPhone());
                        intent.putExtra("location_name", temp.get(j).getLocation_name());
                        intent.putExtra("latitude", temp.get(j).getLatitude());
                        intent.putExtra("longitude", temp.get(j).getLongitude());
                        intent.putExtra("time", temp.get(j).getTime());
                        intent.putExtra("prep_time", temp.get(j).getPrep_time());
                        intent.putExtra("date", temp.get(j).getDate());
                        intent.putExtra("estimated_duration", temp.get(j).getEstimated_duration());
                        intent.putExtra("personnel", temp.get(j).getPersonnel());
                        intent.putExtra("service_provider", temp.get(j).getService_provider());
                        intent.putExtra("number_of_provider", temp.get(j).getNumber_of_provider());
                        intent.putExtra("jay_wallters", temp.get(j).getJay_walters());
                        intent.putExtra("status", temp.get(j).getStatus());
                        intent.putExtra("created_at", temp.get(j).getCreated_at());
                        intent.putExtra("updated_at", temp.get(j).getUpdated_at());
                        intent.putExtra("type", temp.get(j).getType());
                        intent.putExtra("status_name", temp.get(j).getStatus_name());


                        mContext.startActivity(intent);
                    }

                }


            }
        });


        return v;
    }


    public void alertDialogEvent() {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.dialog_calander_available, null);
        RecyclerView calander_status_event = (RecyclerView)view.findViewById(R.id.calander_status_event);
        RegularTextView textview_multipleevent_date = (RegularTextView)view.findViewById(R.id.textview_multipleevent_date);
        calander_status_event.setLayoutManager(new LinearLayoutManager(mContext));
        calander_status_event.setItemAnimator(new DefaultItemAnimator());
        calander_status_event.setAdapter(new EventMonthAvailableAdapter(mContext,temp,textview_multipleevent_date));
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();


    }

    public View setSelected(View view) {
        if (previousView != null) {

            ll_selected.setBackgroundResource(R.drawable.round_sky);
        }
        previousView = view;

        ll_selected.setBackgroundResource(R.drawable.round_sky);
        return view;
    }

    public View setSelectedPast(View view) {
        if (previousView != null) {

            ll_selected.setBackgroundResource(R.drawable.round_gray);
        }
        previousView = view;

        ll_selected.setBackgroundResource(R.drawable.round_gray);
        return view;
    }

    public View setSelectedCurrentDate(View view) {
        if (previousView != null) {

            ll_selected.setBackgroundResource(R.drawable.round_blue);
        }
        previousView = view;

        ll_selected.setBackgroundResource(R.drawable.round_blue);
        return view;
    }


    public void refreshDays() {

        items.clear();
        dayString.clear();
        pmonth = (GregorianCalendar) month.clone();
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP();
        calMaxP = maxP - (firstDay - 1);

        pmonthmaxset = (GregorianCalendar) pmonth.clone();

        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);


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

}
