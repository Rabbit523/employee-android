package professional.wellness.health.com.employeeapp.Adapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.MyClinicUpcoming;
import professional.wellness.health.com.employeeapp.Model.ViewListHomeFeed;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.Utils.ClinicService;
import professional.wellness.health.com.employeeapp.Utils.MyLocationService;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularEditText;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.MonthClinicDetailAcivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Admin on 09-08-2017.
 */

public class MyClinicUpcomingAdapter extends RecyclerView.Adapter<MyClinicUpcomingAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<MyClinicUpcoming> myclinic_upcoming_list;
    private ArrayList<String> myclinic_upcoming_listtemp;
    private ArrayList<ViewListHomeFeed> othername_list;
    private ArrayList<ViewListHomeFeed> othername_listtemp;
    private ArrayList<MyClinicUpcoming> othername_listtemp_offline;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    boolean lineIsClose = true;
    private double latitude;
    private double longitude;
    private String clinic_id = "";
    private String iduser = "";
    private ArrayList<String> clockout_id_data_offline;
    private String id_clinic="";
    private int pos;

    /*    items*/
    public MyClinicUpcomingAdapter(Context context, ArrayList<MyClinicUpcoming> myclinic_upcoming_list,
                                   ArrayList<ViewListHomeFeed> othername_list, ArrayList<String> myclinic_upcoming_listtemp) {
        this.context = context;

        this.myclinic_upcoming_list = myclinic_upcoming_list;
        this.myclinic_upcoming_listtemp = myclinic_upcoming_listtemp;
        this.othername_list = othername_list;
        clockout_id_data_offline = new ArrayList<>();
        othername_listtemp = new ArrayList<>();
        othername_listtemp_offline = new ArrayList<>();
        encryptionClass = EncryptionClass.getInstance(context);
        restInteraction = RESTInteraction.getInstance(context);

        GPSTracker gps = new GPSTracker(context);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_myclinic_upcoming, parent, false);

        return new ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        //holder.card_name.setText(mycards.get(position));



        if (!AppUtills.isNetworkAvailable(context)) {

            id_clinic = myclinic_upcoming_listtemp.get(position);



            othername_listtemp_offline.clear();

            if(!myclinic_upcoming_list.get(position).getType().equals("clock_in")) {
                if (SessionManager.getTimeFormatValue(context).equals("12")) {
                    try {
                        String yourTime = myclinic_upcoming_list.get(position).getPrep_time();

                        String today = (String) android.text.format.DateFormat.format(
                                "hh:mm aa", new Date());

                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                        Date date1 = sdf.parse(yourTime);


                        Date date2 = sdf.parse(today);

                        int time = Integer.parseInt(myclinic_upcoming_list.get(position).getCreate_timestamp());


                        if (System.currentTimeMillis()/1000 >= time) {

                            othername_listtemp_offline.add(new MyClinicUpcoming(myclinic_upcoming_list.get(position).getId(), myclinic_upcoming_list.get(position).getProvider_id()
                                    , myclinic_upcoming_list.get(position).getName(), myclinic_upcoming_list.get(position).getPhone(), myclinic_upcoming_list.get(position).getLocation_name(), myclinic_upcoming_list.get(position).getLatitude(),
                                    myclinic_upcoming_list.get(position).getLongitude(), myclinic_upcoming_list.get(position).getTime(), myclinic_upcoming_list.get(position).getPrep_time(),
                                    myclinic_upcoming_list.get(position).getDate(), myclinic_upcoming_list.get(position).getCreate_timestamp(), myclinic_upcoming_list.get(position).getEstimated_duration(), myclinic_upcoming_list.get(position).getPersonnel(),
                                    myclinic_upcoming_list.get(position).getService_provider(), myclinic_upcoming_list.get(position).getNumber_of_provider(), myclinic_upcoming_list.get(position).getJay_walters(), myclinic_upcoming_list.get(position).getStatus()
                                    , myclinic_upcoming_list.get(position).getCreated_at(), myclinic_upcoming_list.get(position).getUpdated_at(), myclinic_upcoming_list.get(position).getContact_name(), myclinic_upcoming_list.get(position).getPrimary_name(),
                                    myclinic_upcoming_list.get(position).getMedtech_name(), myclinic_upcoming_list.get(position).getClocked(), "clock_in", myclinic_upcoming_list.get(position).getStatus_name(),
                                    myclinic_upcoming_list.get(position).getDuration(), myclinic_upcoming_list.get(position).getMileage_required(), myclinic_upcoming_list.get(position).getDrive_time_required(), myclinic_upcoming_list.get(position).getMileage_status()
                                    , myclinic_upcoming_list.get(position).getDrive_time_status()));

                            //String id = othername_listtemp_offline.get(position).getId();
                            myclinic_upcoming_list.remove(position);

                            for (int i = 0; i < othername_listtemp_offline.size(); i++) {
                                myclinic_upcoming_list.add(new MyClinicUpcoming(
                                        othername_listtemp_offline.get(i).getId(), othername_listtemp_offline.get(i).getProvider_id()
                                        , othername_listtemp_offline.get(i).getName(), othername_listtemp_offline.get(i).getPhone(), othername_listtemp_offline.get(i).getLocation_name(), othername_listtemp_offline.get(i).getLatitude(),
                                        othername_listtemp_offline.get(i).getLongitude(), othername_listtemp_offline.get(i).getTime(), othername_listtemp_offline.get(i).getPrep_time(),
                                        othername_listtemp_offline.get(i).getDate(), othername_listtemp_offline.get(i).getCreate_timestamp(), othername_listtemp_offline.get(i).getEstimated_duration(), othername_listtemp_offline.get(i).getPersonnel(),
                                        othername_listtemp_offline.get(i).getService_provider(), othername_listtemp_offline.get(i).getNumber_of_provider(), othername_listtemp_offline.get(i).getJay_walters(), othername_listtemp_offline.get(i).getStatus()
                                        , othername_listtemp_offline.get(i).getCreated_at(), othername_listtemp_offline.get(i).getUpdated_at(), othername_listtemp_offline.get(i).getContact_name(), othername_listtemp_offline.get(i).getPrimary_name(),
                                        othername_listtemp_offline.get(i).getMedtech_name(), othername_listtemp_offline.get(i).getClocked(), "clock_in", othername_listtemp_offline.get(i).getStatus_name(),
                                        othername_listtemp_offline.get(i).getDuration(), othername_listtemp_offline.get(i).getMileage_required(), othername_listtemp_offline.get(i).getDrive_time_required(), othername_listtemp_offline.get(i).getMileage_status()
                                        , othername_listtemp_offline.get(i).getDrive_time_status()
                                ));

                            }
                            holder.txt_jaywalters_hide_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                            holder.txt_jaywalters_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                            holder.txt_midtech_hide_clockin.setText(myclinic_upcoming_list.get(position).getMedtech_name());

                            if (myclinic_upcoming_list.get(position).getType().equals("clock_in")) {
                                holder.layout_clockin.setVisibility(View.VISIBLE);
                                holder.layout_upcoming.setVisibility(View.GONE);
                                holder.empClockClinic.setText("Clinic time: " + myclinic_upcoming_list.get(position).getTime());
                                holder.empClockPrep.setText("Prep time " + myclinic_upcoming_list.get(position).getPrep_time());
                                holder.empClockPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                                holder.empClockLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                                holder.empClockContact.setText(myclinic_upcoming_list.get(position).getName());
                                holder.empClockDate.setText("Date: " + myclinic_upcoming_list.get(position).getDate());
                                holder.empClockServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                                //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
                                holder.empClockPhone.setText(myclinic_upcoming_list.get(position).getPhone());


                                if (SessionManager.getclinicidoffline(context).equals(id_clinic)){
                                    if (SessionManager.getclinicTypeoffline(context).equals("clock_out")){
                                        myclinic_upcoming_list.get(position).setClocked("0");
                                    }
                                    else{
                                        myclinic_upcoming_list.get(position).setClocked("1");
                                    }

                                }else{


                                }


                                if (myclinic_upcoming_list.get(position).getClocked().equals("0")) {
                                    holder.empClockIn.setVisibility(View.INVISIBLE);
                                    holder.empClockOut.setVisibility(View.VISIBLE);
                                }else if(SessionManager.getclinicTypeoffline(context).equals("past")){
                                    holder.empClockIn.setVisibility(View.INVISIBLE);
                                    holder.empClockOut.setVisibility(View.INVISIBLE);
                                } else {

                                    holder.empClockIn.setVisibility(View.VISIBLE);
                                    holder.empClockOut.setVisibility(View.INVISIBLE);
                                }


                            } else {
                                holder.layout_clockin.setVisibility(View.GONE);
                                holder.layout_upcoming.setVisibility(View.VISIBLE);
                                holder.clinic_name.setText(myclinic_upcoming_list.get(position).getName());
                                holder.empupcomingPrep.setText("Prep time : " + myclinic_upcoming_list.get(position).getPrep_time());
                                holder.empUpcomingEstDuration.setText("Estimate duration : " + myclinic_upcoming_list.get(position).getEstimated_duration());
                                holder.empUpcomingPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                                holder.empupcomingLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                                holder.empupcomingDate.setText("Date : " + myclinic_upcoming_list.get(position).getDate());
                                holder.empupcomingClinic.setText("Clinic Time : " + myclinic_upcoming_list.get(position).getTime());
                                holder.empupcomingServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                            }


                        }
                        else {
                            holder.txt_jaywalters_hide_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                            holder.txt_jaywalters_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                            holder.txt_midtech_hide_clockin.setText(myclinic_upcoming_list.get(position).getMedtech_name());

                            if (myclinic_upcoming_list.get(position).getType().equals("clock_in")) {
                                holder.layout_clockin.setVisibility(View.VISIBLE);
                                holder.layout_upcoming.setVisibility(View.GONE);
                                holder.empClockClinic.setText("Clinic time: " + myclinic_upcoming_list.get(position).getTime());
                                holder.empClockPrep.setText("Prep time " + myclinic_upcoming_list.get(position).getPrep_time());
                                holder.empClockPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                                holder.empClockLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                                holder.empClockContact.setText(myclinic_upcoming_list.get(position).getName());
                                holder.empClockDate.setText("Date: " + myclinic_upcoming_list.get(position).getDate());
                                holder.empClockServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                                //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
                                holder.empClockPhone.setText(myclinic_upcoming_list.get(position).getPhone());

                                if (myclinic_upcoming_list.get(position).getClocked().equals("1")) {
                                    holder.empClockIn.setVisibility(View.VISIBLE);
                                    holder.empClockOut.setVisibility(View.INVISIBLE);
                                } else {
                                    holder.empClockIn.setVisibility(View.INVISIBLE);
                                    holder.empClockOut.setVisibility(View.VISIBLE);
                                }


                            } else {
                                holder.layout_clockin.setVisibility(View.GONE);
                                holder.layout_upcoming.setVisibility(View.VISIBLE);
                                holder.clinic_name.setText(myclinic_upcoming_list.get(position).getName());
                                holder.empupcomingPrep.setText("Prep time : " + myclinic_upcoming_list.get(position).getPrep_time());
                                holder.empUpcomingEstDuration.setText("Estimate duration : " + myclinic_upcoming_list.get(position).getEstimated_duration());
                                holder.empUpcomingPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                                holder.empupcomingLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                                holder.empupcomingDate.setText("Date : " + myclinic_upcoming_list.get(position).getDate());
                                holder.empupcomingClinic.setText("Clinic Time : " + myclinic_upcoming_list.get(position).getTime());
                                holder.empupcomingServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                            }

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {

                    try {
                        String yourTime = myclinic_upcoming_list.get(position).getPrep_time();

                        String today = (String) android.text.format.DateFormat.format(
                                "kk:mm", new Date());

                        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
                        Date date1 = sdf.parse(yourTime);


                        Date date2 = sdf.parse(today);


                        int time = Integer.parseInt(myclinic_upcoming_list.get(position).getCreate_timestamp());

                        if (System.currentTimeMillis()/1000 >= time) {

                            othername_listtemp_offline.add(new MyClinicUpcoming(myclinic_upcoming_list.get(position).getId(), myclinic_upcoming_list.get(position).getProvider_id()
                                    , myclinic_upcoming_list.get(position).getName(), myclinic_upcoming_list.get(position).getPhone(), myclinic_upcoming_list.get(position).getLocation_name(), myclinic_upcoming_list.get(position).getLatitude(),
                                    myclinic_upcoming_list.get(position).getLongitude(), myclinic_upcoming_list.get(position).getTime(), myclinic_upcoming_list.get(position).getPrep_time(),
                                    myclinic_upcoming_list.get(position).getDate(), myclinic_upcoming_list.get(position).getCreate_timestamp(), myclinic_upcoming_list.get(position).getEstimated_duration(), myclinic_upcoming_list.get(position).getPersonnel(),
                                    myclinic_upcoming_list.get(position).getService_provider(), myclinic_upcoming_list.get(position).getNumber_of_provider(), myclinic_upcoming_list.get(position).getJay_walters(), myclinic_upcoming_list.get(position).getStatus()
                                    , myclinic_upcoming_list.get(position).getCreated_at(), myclinic_upcoming_list.get(position).getUpdated_at(), myclinic_upcoming_list.get(position).getContact_name(), myclinic_upcoming_list.get(position).getPrimary_name(),
                                    myclinic_upcoming_list.get(position).getMedtech_name(), "1", "clock_in", myclinic_upcoming_list.get(position).getStatus_name(),
                                    myclinic_upcoming_list.get(position).getDuration(), myclinic_upcoming_list.get(position).getMileage_required(), myclinic_upcoming_list.get(position).getDrive_time_required(), myclinic_upcoming_list.get(position).getMileage_status()
                                    , myclinic_upcoming_list.get(position).getDrive_time_status()));

                          //  String id = othername_listtemp_offline.get(position).getId();
                            myclinic_upcoming_list.remove(position);

                            for (int i = 0; i < othername_listtemp_offline.size(); i++) {
                                myclinic_upcoming_list.add(new MyClinicUpcoming(
                                        othername_listtemp_offline.get(i).getId(), othername_listtemp_offline.get(i).getProvider_id()
                                        , othername_listtemp_offline.get(i).getName(), othername_listtemp_offline.get(i).getPhone(), othername_listtemp_offline.get(i).getLocation_name(), othername_listtemp_offline.get(i).getLatitude(),
                                        othername_listtemp_offline.get(i).getLongitude(), othername_listtemp_offline.get(i).getTime(), othername_listtemp_offline.get(i).getPrep_time(),
                                        othername_listtemp_offline.get(i).getDate(), othername_listtemp_offline.get(i).getCreate_timestamp(), othername_listtemp_offline.get(i).getEstimated_duration(), othername_listtemp_offline.get(i).getPersonnel(),
                                        othername_listtemp_offline.get(i).getService_provider(), othername_listtemp_offline.get(i).getNumber_of_provider(), othername_listtemp_offline.get(i).getJay_walters(), othername_listtemp_offline.get(i).getStatus()
                                        , othername_listtemp_offline.get(i).getCreated_at(), othername_listtemp_offline.get(i).getUpdated_at(), othername_listtemp_offline.get(i).getContact_name(), othername_listtemp_offline.get(i).getPrimary_name(),
                                        othername_listtemp_offline.get(i).getMedtech_name(), othername_listtemp_offline.get(i).getClocked(), "clock_in", othername_listtemp_offline.get(i).getStatus_name(),
                                        othername_listtemp_offline.get(i).getDuration(), othername_listtemp_offline.get(i).getMileage_required(), othername_listtemp_offline.get(i).getDrive_time_required(), othername_listtemp_offline.get(i).getMileage_status()
                                        , othername_listtemp_offline.get(i).getDrive_time_status()
                                ));

                            }

                            holder.txt_jaywalters_hide_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                            holder.txt_jaywalters_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                            holder.txt_midtech_hide_clockin.setText(myclinic_upcoming_list.get(position).getMedtech_name());

                            if (myclinic_upcoming_list.get(position).getType().equals("clock_in")) {
                                holder.layout_clockin.setVisibility(View.VISIBLE);
                                holder.layout_upcoming.setVisibility(View.GONE);
                                holder.empClockClinic.setText("Clinic time: " + myclinic_upcoming_list.get(position).getTime());
                                holder.empClockPrep.setText("Prep time " + myclinic_upcoming_list.get(position).getPrep_time());
                                holder.empClockPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                                holder.empClockLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                                holder.empClockContact.setText(myclinic_upcoming_list.get(position).getName());
                                holder.empClockDate.setText("Date: " + myclinic_upcoming_list.get(position).getDate());
                                holder.empClockServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                                //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
                                holder.empClockPhone.setText(myclinic_upcoming_list.get(position).getPhone());



                                if (SessionManager.getclinicidoffline(context).equals(id_clinic)){
                                    if (SessionManager.getclinicTypeoffline(context).equals("clock_out")){
                                        myclinic_upcoming_list.get(position).setClocked("0");
                                    }else{
                                        myclinic_upcoming_list.get(position).setClocked("1");
                                    }

                                }else{


                                }

                                if (myclinic_upcoming_list.get(position).getClocked().equals("1")) {
                                    holder.empClockIn.setVisibility(View.VISIBLE);
                                    holder.empClockOut.setVisibility(View.INVISIBLE);
                                } else {
                                    holder.empClockIn.setVisibility(View.INVISIBLE);
                                    holder.empClockOut.setVisibility(View.VISIBLE);
                                }


                            } else {
                                holder.layout_clockin.setVisibility(View.GONE);
                                holder.layout_upcoming.setVisibility(View.VISIBLE);
                                holder.clinic_name.setText(myclinic_upcoming_list.get(position).getName());
                                holder.empupcomingPrep.setText("Prep time : " + myclinic_upcoming_list.get(position).getPrep_time());
                                holder.empUpcomingEstDuration.setText("Estimate duration : " + myclinic_upcoming_list.get(position).getEstimated_duration());
                                holder.empUpcomingPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                                holder.empupcomingLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                                holder.empupcomingDate.setText("Date : " + myclinic_upcoming_list.get(position).getDate());
                                holder.empupcomingClinic.setText("Clinic Time : " + myclinic_upcoming_list.get(position).getTime());
                                holder.empupcomingServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                            }



                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    holder.txt_jaywalters_hide_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                    holder.txt_jaywalters_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                    holder.txt_midtech_hide_clockin.setText(myclinic_upcoming_list.get(position).getMedtech_name());

                    if (myclinic_upcoming_list.get(position).getType().equals("clock_in")) {
                        holder.layout_clockin.setVisibility(View.VISIBLE);
                        holder.layout_upcoming.setVisibility(View.GONE);
                        holder.empClockClinic.setText("Clinic time: " + myclinic_upcoming_list.get(position).getTime());
                        holder.empClockPrep.setText("Prep time " + myclinic_upcoming_list.get(position).getPrep_time());
                        holder.empClockPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                        holder.empClockLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                        holder.empClockContact.setText(myclinic_upcoming_list.get(position).getName());
                        holder.empClockDate.setText("Date: " + myclinic_upcoming_list.get(position).getDate());
                        holder.empClockServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                        //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
                        holder.empClockPhone.setText(myclinic_upcoming_list.get(position).getPhone());
                        if (SessionManager.getclinicidoffline(context).equals(id_clinic)){
                            if (SessionManager.getclinicTypeoffline(context).equals("clock_out")){
                                myclinic_upcoming_list.get(position).setClocked("0");
                            }else{
                                myclinic_upcoming_list.get(position).setClocked("1");
                            }

                        }else{


                        }
                        if (myclinic_upcoming_list.get(position).getClocked().equals("1")) {
                            holder.empClockIn.setVisibility(View.VISIBLE);
                            holder.empClockOut.setVisibility(View.INVISIBLE);
                        } else {
                            holder.empClockIn.setVisibility(View.INVISIBLE);
                            holder.empClockOut.setVisibility(View.VISIBLE);
                        }


                    } else {
                        holder.layout_clockin.setVisibility(View.GONE);
                        holder.layout_upcoming.setVisibility(View.VISIBLE);
                        holder.clinic_name.setText(myclinic_upcoming_list.get(position).getName());
                        holder.empupcomingPrep.setText("Prep time : " + myclinic_upcoming_list.get(position).getPrep_time());
                        holder.empUpcomingEstDuration.setText("Estimate duration : " + myclinic_upcoming_list.get(position).getEstimated_duration());
                        holder.empUpcomingPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                        holder.empupcomingLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                        holder.empupcomingDate.setText("Date : " + myclinic_upcoming_list.get(position).getDate());
                        holder.empupcomingClinic.setText("Clinic Time : " + myclinic_upcoming_list.get(position).getTime());
                        holder.empupcomingServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                    }


                }
            }else{
                holder.txt_jaywalters_hide_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                holder.txt_jaywalters_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
                holder.txt_midtech_hide_clockin.setText(myclinic_upcoming_list.get(position).getMedtech_name());

                if (myclinic_upcoming_list.get(position).getType().equals("clock_in")) {
                    holder.layout_clockin.setVisibility(View.VISIBLE);
                    holder.layout_upcoming.setVisibility(View.GONE);
                    holder.empClockClinic.setText("Clinic time: " + myclinic_upcoming_list.get(position).getTime());
                    holder.empClockPrep.setText("Prep time " + myclinic_upcoming_list.get(position).getPrep_time());
                    holder.empClockPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                    holder.empClockLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                    holder.empClockContact.setText(myclinic_upcoming_list.get(position).getName());
                    holder.empClockDate.setText("Date: " + myclinic_upcoming_list.get(position).getDate());
                    holder.empClockServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                    //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
                    holder.empClockPhone.setText(myclinic_upcoming_list.get(position).getPhone());
                    if (SessionManager.getclinicidoffline(context).equals(id_clinic)){
                        if (SessionManager.getclinicTypeoffline(context).equals("clock_out")){
                            myclinic_upcoming_list.get(position).setClocked("0");
                        }else{
                            myclinic_upcoming_list.get(position).setClocked("1");
                        }

                    }else{


                    }
                    if (myclinic_upcoming_list.get(position).getClocked().equals("1")) {
                        holder.empClockIn.setVisibility(View.VISIBLE);
                        holder.empClockOut.setVisibility(View.INVISIBLE);
                    }else if(SessionManager.getclinicTypeoffline(context).equals("past")){
                        holder.empClockIn.setVisibility(View.INVISIBLE);
                        holder.empClockOut.setVisibility(View.INVISIBLE);
                    } else {
                        holder.empClockIn.setVisibility(View.INVISIBLE);
                        holder.empClockOut.setVisibility(View.VISIBLE);
                    }


                } else {
                    holder.layout_clockin.setVisibility(View.GONE);
                    holder.layout_upcoming.setVisibility(View.VISIBLE);
                    holder.clinic_name.setText(myclinic_upcoming_list.get(position).getName());
                    holder.empupcomingPrep.setText("Prep time : " + myclinic_upcoming_list.get(position).getPrep_time());
                    holder.empUpcomingEstDuration.setText("Estimate duration : " + myclinic_upcoming_list.get(position).getEstimated_duration());
                    holder.empUpcomingPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                    holder.empupcomingLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                    holder.empupcomingDate.setText("Date : " + myclinic_upcoming_list.get(position).getDate());
                    holder.empupcomingClinic.setText("Clinic Time : " + myclinic_upcoming_list.get(position).getTime());
                    holder.empupcomingServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                }

            }


            holder.empClockIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clinic_id = myclinic_upcoming_list.get(position).getId();

                    if (!AppUtills.isNetworkAvailable(context)) {
                        try {
                            MainActivity.notification();
                        }catch (Exception e){

                        }
                        alertDialogofflineClockin(clinic_id,holder);

                    }else{

                        try {
                            MainActivity.notification();
                        }catch (Exception e){

                        }
                        try {
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(context));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(context));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", myclinic_upcoming_list.get(position).getId());
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new updateClockinAsync().execute(encrypted);
                            holder.empClockIn.setVisibility(View.INVISIBLE);
                            holder.empClockOut.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

            holder.empClockOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!AppUtills.isNetworkAvailable(context)) {
                        clinic_id = myclinic_upcoming_list.get(position).getId();
                        pos = position;
                        alertDialogMilage(clinic_id,holder);
                        MainActivity.clearNotifications(context);
                    }else{

                        MainActivity.clearNotifications(context);

                        try {
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(context));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(context));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", myclinic_upcoming_list.get(position).getId());
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new updateClockOutAsync().execute(encrypted);
                     /*   mItemManger.removeShownLayouts(viewHolder.swipeLayout);*/
                            myclinic_upcoming_list.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, myclinic_upcoming_list.size());
                      /*  mItemManger.closeAllItems();*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                }
            });


            holder.viewlist_open_clockin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.serviceProvider_clockin.setVisibility(View.GONE);
                    holder.dropdown_clockin.setVisibility(View.VISIBLE);
                    holder.view_listclockin.setVisibility(View.GONE);

                    iduser = myclinic_upcoming_list.get(position).getId();
                    othername_listtemp.clear();
                    for (int i = 0; i < othername_list.size(); i++) {
                        if (othername_list.get(i).getId().equals(iduser)) {
                            othername_listtemp.add(new ViewListHomeFeed(othername_list.get(i).getId(), othername_list.get(i).getName()));
                        }
                    }
                    intializeRecyclerViewListClockin(holder);

                }
            });

            holder.hide_clockin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.serviceProvider_clockin.setVisibility(View.VISIBLE);
                    holder.dropdown_clockin.setVisibility(View.GONE);
                    holder.view_listclockin.setVisibility(View.VISIBLE);
                }
            });


        } else {
            holder.txt_jaywalters_hide_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
            holder.txt_jaywalters_clockin.setText(myclinic_upcoming_list.get(position).getPrimary_name());
            holder.txt_midtech_hide_clockin.setText(myclinic_upcoming_list.get(position).getMedtech_name());

            if (myclinic_upcoming_list.get(position).getType().equals("clock_in")) {
                holder.layout_clockin.setVisibility(View.VISIBLE);
                holder.layout_upcoming.setVisibility(View.GONE);
                holder.empClockClinic.setText("Clinic time: " + myclinic_upcoming_list.get(position).getTime());
                holder.empClockPrep.setText("Prep time " + myclinic_upcoming_list.get(position).getPrep_time());
                holder.empClockPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                holder.empClockLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                holder.empClockContact.setText(myclinic_upcoming_list.get(position).getName());
                holder.empClockDate.setText("Date: " + myclinic_upcoming_list.get(position).getDate());
                holder.empClockServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
                //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
                holder.empClockPhone.setText(myclinic_upcoming_list.get(position).getPhone());

                if (myclinic_upcoming_list.get(position).getClocked().equals("1")) {
                    holder.empClockIn.setVisibility(View.VISIBLE);
                    holder.empClockOut.setVisibility(View.INVISIBLE);
                } else {
                    holder.empClockIn.setVisibility(View.INVISIBLE);
                    holder.empClockOut.setVisibility(View.VISIBLE);
                }


            } else {
                holder.layout_clockin.setVisibility(View.GONE);
                holder.layout_upcoming.setVisibility(View.VISIBLE);
                holder.clinic_name.setText(myclinic_upcoming_list.get(position).getName());
                holder.empupcomingPrep.setText("Prep time : " + myclinic_upcoming_list.get(position).getPrep_time());
                holder.empUpcomingEstDuration.setText("Estimate duration : " + myclinic_upcoming_list.get(position).getEstimated_duration());
                holder.empUpcomingPersonnel.setText("Personnel : " + myclinic_upcoming_list.get(position).getPersonnel());
                holder.empupcomingLocation.setText(myclinic_upcoming_list.get(position).getLocation_name());
                holder.empupcomingDate.setText("Date : " + myclinic_upcoming_list.get(position).getDate());
                holder.empupcomingClinic.setText("Clinic Time : " + myclinic_upcoming_list.get(position).getTime());
                holder.empupcomingServiceProvided.setText(myclinic_upcoming_list.get(position).getService_provider());
            }
            holder.empClockIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        MainActivity.notification();
                    }catch (Exception e){

                    }
                    clinic_id = myclinic_upcoming_list.get(position).getId();
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(context));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(context));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("clinic_id", myclinic_upcoming_list.get(position).getId());
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new updateClockinAsync().execute(encrypted);
                        holder.empClockIn.setVisibility(View.INVISIBLE);
                        holder.empClockOut.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.empClockOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.clearNotifications(context);
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(context));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(context));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("clinic_id", myclinic_upcoming_list.get(position).getId());
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new updateClockOutAsync().execute(encrypted);
                     /*   mItemManger.removeShownLayouts(viewHolder.swipeLayout);*/
                        myclinic_upcoming_list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, myclinic_upcoming_list.size());
                      /*  mItemManger.closeAllItems();*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });


            holder.viewlist_open_clockin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.serviceProvider_clockin.setVisibility(View.GONE);
                    holder.dropdown_clockin.setVisibility(View.VISIBLE);
                    holder.view_listclockin.setVisibility(View.GONE);

                    iduser = myclinic_upcoming_list.get(position).getId();
                    othername_listtemp.clear();
                    for (int i = 0; i < othername_list.size(); i++) {
                        if (othername_list.get(i).getId().equals(iduser)) {
                            othername_listtemp.add(new ViewListHomeFeed(othername_list.get(i).getId(), othername_list.get(i).getName()));
                        }
                    }
                    intializeRecyclerViewListClockin(holder);

                }
            });

            holder.hide_clockin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.serviceProvider_clockin.setVisibility(View.VISIBLE);
                    holder.dropdown_clockin.setVisibility(View.GONE);
                    holder.view_listclockin.setVisibility(View.VISIBLE);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return myclinic_upcoming_list.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // private final TextView card_name;
        private final RegularTextView clinic_name;
        private final RegularTextView empupcomingPrep;
        private final RegularTextView empUpcomingEstDuration;
        private final RegularTextView empUpcomingPersonnel;
        private final RegularTextView empupcomingLocation;
        private final RegularTextView empupcomingDate;
        private final RegularTextView empupcomingClinic;
        private final RegularTextView empupcomingServiceProvided;
        private CardView layout_clockin;
        private CardView layout_upcoming;
        private final RegularTextView empClockClinic;
        private final RegularTextView empClockPrep;
        private final RegularTextView empClockPersonnel;
        private final RegularTextView empClockLocation;
        private final RegularTextView empClockContact;
        private final RegularTextView empClockDate;
        private final RegularTextView empClockServiceProvided;
        private final RegularTextView empClockPhone;
        private final LinearLayout serviceProvider_clockin;
        private final LinearLayout dropdown_clockin;
        private final LinearLayout viewlist_open_clockin;
        private final LinearLayout view_listclockin;
        private final LinearLayout hide_clockin;
        private final RegularTextView txt_jaywalters_hide_clockin;
        private final RegularTextView txt_jaywalters_clockin;
        private final RegularTextView txt_midtech_hide_clockin;
        private final RecyclerView recycler_clockin_viewlist;
        private final RegularButton empClockIn;
        private final RegularButton empClockOut;


        public ItemViewHolder(View itemView) {
            super(itemView);
/*            clockin_reminder = (RegularTextView)itemView.findViewById(R.id.clockin_reminder);*/
            clinic_name = (RegularTextView) itemView.findViewById(R.id.clinic_name);
            empupcomingPrep = (RegularTextView) itemView.findViewById(R.id.empupcomingPrep);
            empUpcomingEstDuration = (RegularTextView) itemView.findViewById(R.id.empUpcomingEstDuration);
            empUpcomingPersonnel = (RegularTextView) itemView.findViewById(R.id.empUpcomingPersonnel);
            empupcomingLocation = (RegularTextView) itemView.findViewById(R.id.empupcomingLocation);
            empupcomingDate = (RegularTextView) itemView.findViewById(R.id.empupcomingDate);
            empupcomingClinic = (RegularTextView) itemView.findViewById(R.id.empupcomingClinic);
            empupcomingServiceProvided = (RegularTextView) itemView.findViewById(R.id.empupcomingServiceProvided);
            empClockPrep = (RegularTextView) itemView.findViewById(R.id.empClockPrep);
            empClockClinic = (RegularTextView) itemView.findViewById(R.id.empClockClinic);
            empClockPersonnel = (RegularTextView) itemView.findViewById(R.id.empClockPersonnel);
            empClockLocation = (RegularTextView) itemView.findViewById(R.id.empClockLocation);
            empClockContact = (RegularTextView) itemView.findViewById(R.id.empClockContact);
            empClockDate = (RegularTextView) itemView.findViewById(R.id.empClockDate);
            empClockServiceProvided = (RegularTextView) itemView.findViewById(R.id.empClockServiceProvided);
            empClockPhone = (RegularTextView) itemView.findViewById(R.id.empClockPhone);
            txt_jaywalters_hide_clockin = (RegularTextView) itemView.findViewById(R.id.txt_jaywalters_hide_clockin);
            txt_jaywalters_clockin = (RegularTextView) itemView.findViewById(R.id.txt_jaywalters_clockin);
            txt_midtech_hide_clockin = (RegularTextView) itemView.findViewById(R.id.txt_midtech_hide_clockin);
            layout_clockin = (CardView) itemView.findViewById(R.id.layout_clockin);
            layout_upcoming = (CardView) itemView.findViewById(R.id.layout_upcoming);
            serviceProvider_clockin = (LinearLayout) itemView.findViewById(R.id.serviceProvider_clockin);
            dropdown_clockin = (LinearLayout) itemView.findViewById(R.id.dropdown_clockin);
            hide_clockin = (LinearLayout) itemView.findViewById(R.id.hide_clockin);
            viewlist_open_clockin = (LinearLayout) itemView.findViewById(R.id.viewlist_open_clockin);
            view_listclockin = (LinearLayout) itemView.findViewById(R.id.view_listclockin);
            recycler_clockin_viewlist = (RecyclerView) itemView.findViewById(R.id.recycler_clockin_viewlist);
            empClockIn = (RegularButton) itemView.findViewById(R.id.empClockIn);
            empClockOut = (RegularButton) itemView.findViewById(R.id.empClockOut);

        }


    }

    private void intializeRecyclerViewListClockin(ItemViewHolder viewHolder) {
        viewHolder.recycler_clockin_viewlist.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.recycler_clockin_viewlist.setItemAnimator(new DefaultItemAnimator());
        viewHolder.recycler_clockin_viewlist.setAdapter(new ViewListAdapter(context, othername_listtemp, iduser));

    }


    private class updateClockinAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendUpdateClockinRequest(APIUrl.UPDATECLOCKIN, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        SessionManager.setClinicid(context, clinic_id);
                        SessionManager.setClockinTime(context, jsonObject.getString("data"));
                        SessionManager.setStopService(context, "aaa");

                        if (!SessionManager.getStopService(context).equals("stop")) {
                            SessionManager.setClinicid(context, SessionManager.getClinicid(context));
                            context.startService(new Intent(context, MyLocationService.class));


                        }

                        try {
                            context.startService(new Intent(context,ClinicService.class));
                        }catch (Exception e){

                        }
                        alertDialogSucess(jsonObject.getString("message"));

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


    private class updateClockOutAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendUpdateClockinRequest(APIUrl.UPDATECLOCKOUT, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        SessionManager.setStopService(context, "stop");
                        context.stopService(new Intent(context, MyLocationService.class));

                        try {
                            context.startService(new Intent(context,ClinicService.class));
                        }catch (Exception e){

                        }
                        SessionManager.clearClinicid(context);
                        alertDialogSucess(jsonObject.getString("message"));

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



    public void alertDialogofflineClockin(final String clinic_id, final ItemViewHolder holder) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_offline_clockin, null);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btn_confirm = (RegularButton) view.findViewById(R.id.btn_confirm);
        RegularButton btn_cancil = (RegularButton) view.findViewById(R.id.btn_cancil);

         final long clockin_timestamp = System.currentTimeMillis()/1000;



        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          /*      if(clinic_id.equals(id_clinic)){

                }else{
                    holder.empClockIn.setVisibility(View.VISIBLE);
                    holder.empClockOut.setVisibility(View.INVISIBLE);
                }
*/



                holder.empClockIn.setVisibility(View.INVISIBLE);
                holder.empClockOut.setVisibility(View.VISIBLE);

                SessionManager.setclinicidoffline(context,clinic_id);
                SessionManager.setclinictypeoffline(context,"clock_out");
          /*      JSONArray jsonArraygift = new JSONArray();
                jsonArraygift.put(clinic_id);*/
                ArrayList <String> arrayList;
                arrayList = new ArrayList<String>();
                JSONObject object = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject jObj = new JSONObject();
                // if (!(SessionManager.getclockoutidofflinedata(MonthClinicDetailAcivity.this).size() == 0))
                try{
                    arrayList = SessionManager.getofflinesyncdata(context);
                }catch (Exception e){

                }

                try {
                    jObj.put("user_id", SessionManager.getuserId(context));
                    jObj.put("clinic_id", clinic_id);
                    jObj.put("Type", "clock_in");
                    jObj.put("Time_clockout", "");
                    jObj.put("Time_clockin",clockin_timestamp);
                    jObj.put("mileage","");
                    jObj.put("drive_time","");
                    jsonArray.put(jObj);
                    object.put("data",jsonArray);
                    arrayList.add(jObj.toString());
                    SessionManager.addofflinesyncdata(context,arrayList);
                    //   finish();



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
            }
        });

        btn_cancil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    public void alertDialogMilage(final String clinic_id, final ItemViewHolder holder) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_milege, null);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();

        RegularButton milage_submit = (RegularButton) view.findViewById(R.id.milage_submit);
        RegularButton cancel = (RegularButton) view.findViewById(R.id.cancel);
        final RegularEditText edt_milage = (RegularEditText)view.findViewById(R.id.edt_milage);
        final RegularEditText edt_drive_time = (RegularEditText)view.findViewById(R.id.edt_drive_time);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long clockin_timestamp = System.currentTimeMillis()/1000;
                SessionManager.setclinicidoffline(context,"");
                SessionManager.setclinictypeoffline(context,"");
                // SessionManager.setclockoutidoffline(context,clinic_id);
                try {

                }catch (Exception e){
                    SessionManager.setclinicidofflinenew(context, clinic_id);
                }

                SessionManager.setclinictypeoffline(context,"");
                clockout_id_data_offline.add(clinic_id);
                try {
                    SessionManager.addclockoutidofflinedata(context,clockout_id_data_offline);
                }catch (Exception e){

                }


               /*     JSONArray jsonArraygift = new JSONArray();
                    jsonArraygift.put(clinic_id);*/
                ArrayList <String> arrayList;
                arrayList = new ArrayList<String>();
                JSONObject object = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject jObj = new JSONObject();
                try{
                    arrayList = SessionManager.getofflinesyncdata(context);
                }
                catch (Exception e){}
                try {
                    jObj.put("user_id", SessionManager.getuserId(context));
                    jObj.put("clinic_id", clinic_id);
                    jObj.put("Type", "clock_out");
                    jObj.put("Time_clockout",clockin_timestamp);
                    jObj.put("Time_clockin","");
                    jObj.put("mileage",edt_milage.getText().toString());
                    jObj.put("drive_time",edt_drive_time.getText().toString());
                    jsonArray.put(jObj);
                    object.put("data",jsonArray);
                    arrayList.add(jObj.toString());
                    SessionManager.addofflinesyncdata(context,arrayList);
                    myclinic_upcoming_list.remove(pos);
                    notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();


            }
        });

        milage_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_milage.getText().toString().length() ==0){
                    alertDialog("Enter Mileage");
                }else if(edt_drive_time.getText().toString().length() ==0){
                    alertDialog("Enter Drive Time");
                }else{


                    final long clockin_timestamp = System.currentTimeMillis()/1000;
                    SessionManager.setclinicidoffline(context,"");
                    SessionManager.setclinictypeoffline(context,"");
                   // SessionManager.setclockoutidoffline(context,clinic_id);
                    SessionManager.setclinicidofflinenew(context, clinic_id);
                    SessionManager.setclinictypeoffline(context,"");
                    clockout_id_data_offline.add(clinic_id);
                    try {
                        SessionManager.addclockoutidofflinedata(context,clockout_id_data_offline);
                    }catch (Exception e){

                    }


               /*     JSONArray jsonArraygift = new JSONArray();
                    jsonArraygift.put(clinic_id);*/
                    ArrayList <String> arrayList;
                    arrayList = new ArrayList<String>();
                    JSONObject object = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jObj = new JSONObject();
                    try{
                        arrayList = SessionManager.getofflinesyncdata(context);
                    }
                  catch (Exception e){}
                    try {
                        jObj.put("user_id", SessionManager.getuserId(context));
                        jObj.put("clinic_id", clinic_id);
                        jObj.put("Type", "clock_out");
                        jObj.put("Time_clockout",clockin_timestamp);
                        jObj.put("Time_clockin","");
                        jObj.put("mileage",edt_milage.getText().toString());
                        jObj.put("drive_time",edt_drive_time.getText().toString());
                        jsonArray.put(jObj);
                        object.put("data",jsonArray);
                        arrayList.add(jObj.toString());
                        SessionManager.addofflinesyncdata(context,arrayList);
                        myclinic_upcoming_list.remove(pos);
                        notifyDataSetChanged();

                        try {
                            context.startService(new Intent(context,ClinicService.class));
                        }catch (Exception e){

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();


                }

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


}
