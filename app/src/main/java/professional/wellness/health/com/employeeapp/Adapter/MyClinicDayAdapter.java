package professional.wellness.health.com.employeeapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.Certificate;
import professional.wellness.health.com.employeeapp.Model.MyClinicDay;
import professional.wellness.health.com.employeeapp.Model.ViewList;
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
import professional.wellness.health.com.employeeapp.activity.MonthClinicWeekDetailActivity;
import professional.wellness.health.com.employeeapp.fragment.CalenderFragmentNew;

/**
 * Created by Admin on 25-07-2017.
 */

public class MyClinicDayAdapter  extends RecyclerView.Adapter<MyClinicDayAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<MyClinicDay> day_clinic_list;
    private ArrayList<ViewList> viewlistotherday;
    private ArrayList<ViewListHomeFeed> othername_listtemp;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private double latitude;
    private double longitude;
    private Handler handler;
    private Runnable run;
    private ItemViewHolder holderr;
    private String iduser = "";
    private String clinic_id="";
    private String encrypted = "";
    private String id_clinic="";
    private int pos;
    private ArrayList<String> clockout_id_data_offline=null;
    private ArrayList<String> day_clinic_list_temp;
    private String type= "";
    /*    items*/
    public MyClinicDayAdapter(Context context, ArrayList<MyClinicDay> day_clinic_list, ArrayList<ViewList> viewlistotherday,
                              ArrayList<String> day_clinic_list_temp) {
        this.context = context;
        this.day_clinic_list = day_clinic_list;
        this.viewlistotherday = viewlistotherday;
        this.day_clinic_list_temp = day_clinic_list_temp;
        othername_listtemp = new ArrayList<>();
        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);
        clockout_id_data_offline = new ArrayList<>();
        GPSTracker gps = new GPSTracker(context);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }
    }


    @Override
    public MyClinicDayAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_my_clinic_day_row, parent, false);

        return new MyClinicDayAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final MyClinicDayAdapter.ItemViewHolder holder, final int position) {


        //holder.card_name.setText(mycards.get(position));
        if (AppUtills.isNetworkAvailable(context)){
            type = day_clinic_list.get(position).getType();
        }else {
            ArrayList<String> arrayList;
            try {
                arrayList = new ArrayList<>();
                arrayList = SessionManager.getclockoutidofflinedata(context);
                for (int i = 0; i < arrayList.size(); i++) {
                    if (day_clinic_list.get(position).getId().equals(arrayList.get(i))) {
                        type = "Past";
                        break;
                    } else type = day_clinic_list.get(position).getType();
                }

            } catch (Exception e) {
            /*if (user_id.equals(SessionManager.getclinicidofflinenew(MonthClinicDetailAcivity.this))) {*/
                type = day_clinic_list.get(position).getType();
           /* }*/
            }
        }


     /*   if (!AppUtills.isNetworkAvailable(context)){
            ArrayList<String> arrayListss;
            arrayListss = new ArrayList<>();
            try {

                arrayListss = SessionManager.getclockoutidofflinedata(context);
                for (int i = 0; i < arrayListss.size(); i++) {
                    if (arrayListss.get(i).equals(day_clinic_list.get(position).getId())) {
                        type = "Past";
                        break;
                    }
                }



            } catch (Exception e) {
                type  = day_clinic_list.get(position).getType();
            }
        }else{
            type  = day_clinic_list.get(position).getType();
        }
*/
        id_clinic = day_clinic_list.get(position).getId();
        holder.txt_jaywalters_hide_clockin.setText(day_clinic_list.get(position).getPrimary_name());
        holder.spend_time_text.setText(day_clinic_list.get(position).getClinic_spend_time());
        holder.txt_jaywalters_clockin.setText(day_clinic_list.get(position).getPrimary_name());
        holder.txt_midtech_hide_clockin.setText(day_clinic_list.get(position).getMedtech_name());
        holder.status_name.setText(day_clinic_list.get(position).getStatus_name());
        holder.prep_time.setText("Prep time "+ day_clinic_list.get(position).getPrep_time());
        holder.duration.setText("Duration : "+ day_clinic_list.get(position).getDuration());
        holder.past_personal.setText("Personnel : " + day_clinic_list.get(position).getPersonnel());
        holder.past_date.setText("Date: "+ day_clinic_list.get(position).getDate());
        holder.location.setText(day_clinic_list.get(position).getLocation_name());
        holder.clinic_time.setText("Clinic time : "+ day_clinic_list.get(position).getTime());
        holder.service_provider.setText(day_clinic_list.get(position).getService_provider());
        holder.milage.setText(day_clinic_list.get(position).getMileage_required());
        holder.drive_time.setText(day_clinic_list.get(position).getDrive_time_required());
        if(type.equals("Past")){
            holder.layout_clockin.setVisibility(View.GONE);
            holder.past.setVisibility(View.VISIBLE);
            holder.empPreferredAvailable.setVisibility(View.GONE);
            holder.layout_accept_decline.setVisibility(View.GONE);
            if(day_clinic_list.get(position).getMileage_status().equals("1")){
                holder.milage_layout.setVisibility(View.GONE);
                holder.milage_layout_filled.setVisibility(View.VISIBLE);
            }else if(day_clinic_list.get(position).getDrive_time_status().equals("1")){
                holder.milage_layout.setVisibility(View.GONE);
                holder.milage_layout_filled.setVisibility(View.VISIBLE);
            }else{
                holder.milage_layout.setVisibility(View.VISIBLE);
                holder.milage_layout_filled.setVisibility(View.GONE);
            }

        }else if(type.equals("clock_in")){
            holder.layout_accept_decline.setVisibility(View.GONE);
            holder.layout_clockin.setVisibility(View.VISIBLE);
            holder.empPreferredAvailable.setVisibility(View.GONE);
            holder.past.setVisibility(View.GONE);
            holder.empClockClinic.setText("Clinic time: " + day_clinic_list.get(position).getTime());
            holder.empClockPrep.setText("Prep time " + day_clinic_list.get(position).getPrep_time());
            holder.empClockPersonnel.setText("Personnel : " + day_clinic_list.get(position).getPersonnel());
            holder.empClockLocation.setText(day_clinic_list.get(position).getLocation_name());
            holder.empClockContact.setText(day_clinic_list.get(position).getName());
            holder.empClockDate.setText("Date: " + day_clinic_list.get(position).getDate());
            holder.empClockServiceProvided.setText(day_clinic_list.get(position).getService_provider());
            //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
            holder.empClockPhone.setText(day_clinic_list.get(position).getPhone());

            if (!AppUtills.isNetworkAvailable(context)) {
               // for (int i = 0; i < day_clinic_list_temp.size(); i++) {
                    if (day_clinic_list_temp.get(position).equals(SessionManager.getclinicidoffline(context))){
                        if (SessionManager.getclinicTypeoffline(context).equals("clock_out")){
                            day_clinic_list.get(position).setClocked("0");
                        }
                        else{
                            day_clinic_list.get(position).setClocked("1");
                        }
                    }
                    else {

                    }
                //}


           /*     if (SessionManager.getclinicidoffline(context).equals(id_clinic)){
                    if (SessionManager.getclinicTypeoffline(context).equals("clock_out")){
                        day_clinic_list.get(position).setClocked("0");
                    }else{
                        day_clinic_list.get(position).setClocked("1");
                    }

                }else{


                }
*/
                if (day_clinic_list.get(position).getClocked().equals("1")) {
                    holder.empClockIn.setVisibility(View.VISIBLE);
                    holder.empClockOut.setVisibility(View.INVISIBLE);

                }else {
                    holder.empClockIn.setVisibility(View.INVISIBLE);
                    holder.empClockOut.setVisibility(View.VISIBLE);
                }



            }else{
                if (day_clinic_list.get(position).getClocked().equals("1")) {
                    holder.empClockIn.setVisibility(View.VISIBLE);
                    holder.empClockOut.setVisibility(View.INVISIBLE);
                } else {
                    holder.empClockIn.setVisibility(View.INVISIBLE);
                    holder.empClockOut.setVisibility(View.VISIBLE);
                }
            }



            holder.empClockIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clinic_id = day_clinic_list.get(position).getId();
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
                            clinic_id = day_clinic_list.get(position).getId();
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(context));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(context));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", day_clinic_list.get(position).getId());
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
                    pos = position;
                    if (!AppUtills.isNetworkAvailable(context)) {
                        clinic_id = day_clinic_list.get(position).getId();
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
                            object.put("clinic_id", day_clinic_list.get(position).getId());
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new updateClockOutAsync().execute(encrypted);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                }
            });


        }
        else{
            holder.layout_clockin.setVisibility(View.GONE);
            holder.layout_accept_decline.setVisibility(View.GONE);
            holder.milage_layout.setVisibility(View.GONE);
            holder.milage_layout_filled.setVisibility(View.GONE);
            holder.empPreferredAvailable.setVisibility(View.GONE);
        }



        holder.milage_submit.setOnClickListener(new View.OnClickListener() {

            String clinic_id = day_clinic_list.get(position).getId();
            @Override
            public void onClick(View view) {
             if(holder.edt_milage.getText().toString().length() ==0){
                 alertDialog("PLease Enter Milage (RT)");
             }else if(holder.edt_drive_time.getText().toString().length()==0){
                 alertDialog("PLease Enter Drive time (RT)");
             }else{
                 try {
                     JSONObject object = new JSONObject();
                     object.put("device_id", SessionManager.getDeviceId(context));
                     object.put("platform_type", "android app");
                     object.put("user_id", SessionManager.getuserId(context));
                     object.put("clinic_id", clinic_id);
                     object.put("mileage_required", holder.edt_milage.getText().toString().trim());
                     object.put("drive_time_required", holder.edt_drive_time.getText().toString().trim());
                     String send_data = object.toString();
                     String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                     holderr = holder;
                     new UpdateClinicMilage().execute(encrypted);
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

                iduser = day_clinic_list.get(position).getId();
                othername_listtemp.clear();
                for (int i = 0; i < viewlistotherday.size(); i++) {
                    if (viewlistotherday.get(i).getEid().equals(iduser)) {
                        othername_listtemp.add(new ViewListHomeFeed(viewlistotherday.get(i).getEid(), viewlistotherday.get(i).getName()));
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
                clockout_id_data_offline.add(clinic_id);
                try {
                    SessionManager.addclockoutidofflinedata(context,clockout_id_data_offline);
                }catch (Exception e){

                }
              try {
                  SessionManager.setclinicidofflinenew(context, clinic_id);
              }catch (Exception e){

              }


               /*     JSONArray jsonArraygift = new JSONArray();
                    jsonArraygift.put(clinic_id);*/

                ArrayList <String> arrayList;
                arrayList= new ArrayList<String>();
                JSONObject object = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                JSONObject jObj = new JSONObject();
                try {
                    arrayList = SessionManager.getofflinesyncdata(context);
                }catch (Exception  e){

                }
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
                    day_clinic_list.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, day_clinic_list.size());




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
                    clockout_id_data_offline.add(clinic_id);
                    try {
                        SessionManager.addclockoutidofflinedata(context,clockout_id_data_offline);
                    }catch (Exception e){

                    }

                    try {

                        SessionManager.setclinicidofflinenew(context, clinic_id);
                    }catch (Exception e){

                    }


               /*     JSONArray jsonArraygift = new JSONArray();
                    jsonArraygift.put(clinic_id);*/

                    ArrayList <String> arrayList;
                    arrayList= new ArrayList<String>();
                    JSONObject object = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jObj = new JSONObject();
                    try {
                        arrayList = SessionManager.getofflinesyncdata(context);
                    }catch (Exception  e){

                    }
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
                        day_clinic_list.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, day_clinic_list.size());

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
/*
                if(clinic_id.equals(id_clinic)){

                }else{
                    holder.empClockIn.setVisibility(View.VISIBLE);
                    holder.empClockOut.setVisibility(View.INVISIBLE);
                }*/



                holder.empClockIn.setVisibility(View.INVISIBLE);
                holder.empClockOut.setVisibility(View.VISIBLE);

                SessionManager.setclinicidoffline(context,clinic_id);
                SessionManager.setclinictypeoffline(context,"clock_out");
             /*   JSONArray jsonArraygift = new JSONArray();
                jsonArraygift.put(clinic_id);*/
                ArrayList <String> arrayList ;
                arrayList= new ArrayList<String>();
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


    @Override
    public int getItemCount() {
        return day_clinic_list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        // private final TextView card_name;


        private final RegularTextView status_name;
        private final RegularTextView prep_time;
        private final RegularTextView duration;
        private final RegularTextView past_personal;
        private final RegularTextView past_date;
        private final RegularTextView location;
        private final RegularTextView clinic_time;
        private final RegularTextView service_provider;
        private final RegularButton milage_submit;
        private  RegularEditText edt_milage;
        private  LinearLayout milage_layout;
        private  RegularTextView milage;
        private  RegularTextView drive_time;
        private  LinearLayout milage_layout_filled;
        private  RegularEditText edt_drive_time;
        private  RegularTextView spend_time_text;

        //clockin
        private final CardView layout_clockin;
        private final CardView empPreferredAvailable;
        private final CardView past;
        private final RegularTextView txt_jaywalters_hide_clockin;
        private final RegularTextView txt_jaywalters_clockin;
        private final RegularTextView txt_midtech_hide_clockin;
        private final RegularTextView empClockClinic;
        private final RegularTextView empClockPrep;
        private final RegularTextView empClockPersonnel;
        private final RegularTextView empClockLocation;
        private final RegularTextView empClockContact;
        private final RegularTextView empClockDate;
        private final RegularTextView empClockServiceProvided;
        private final RegularTextView empClockPhone;
        private final RegularButton empClockIn;
        private final RegularButton empClockOut;
        private final LinearLayout viewlist_open_clockin;
        private final LinearLayout serviceProvider_clockin;
        private final LinearLayout dropdown_clockin;
        private final LinearLayout view_listclockin;
        private final LinearLayout hide_clockin;
        private final  RecyclerView recycler_clockin_viewlist;
        private final  LinearLayout layout_accept_decline;


        public ItemViewHolder(View itemView) {
            super(itemView);
            //card_name = (TextView) itemView.findViewById(R.id.card_name);
            milage_layout = (LinearLayout)itemView.findViewById(R.id.milage_layout);
            milage_layout_filled = (LinearLayout)itemView.findViewById(R.id.milage_layout_filled);
            status_name = (RegularTextView) itemView.findViewById(R.id.status_name);
            prep_time = (RegularTextView) itemView.findViewById(R.id.prep_time);
            duration = (RegularTextView) itemView.findViewById(R.id.duration);
            past_personal = (RegularTextView) itemView.findViewById(R.id.past_personal);
            past_date = (RegularTextView) itemView.findViewById(R.id.past_date);
            location = (RegularTextView) itemView.findViewById(R.id.location);
            clinic_time = (RegularTextView) itemView.findViewById(R.id.clinic_time);
            service_provider = (RegularTextView) itemView.findViewById(R.id.service_provider);
            milage = (RegularTextView) itemView.findViewById(R.id.milage);
            drive_time = (RegularTextView) itemView.findViewById(R.id.drive_time);
            milage_submit = (RegularButton)itemView.findViewById(R.id.milage_submit);
            edt_milage = (RegularEditText)itemView.findViewById(R.id.edt_milage);
            edt_drive_time = (RegularEditText)itemView.findViewById(R.id.edt_drive_time);
            layout_clockin = (CardView) itemView.findViewById(R.id.layout_clockin);
            empPreferredAvailable = (CardView) itemView.findViewById(R.id.empPreferredAvailable);
            past = (CardView) itemView.findViewById(R.id.past);
            txt_jaywalters_hide_clockin = (RegularTextView)itemView.findViewById(R.id.txt_jaywalters_hide_clockin);
            txt_jaywalters_clockin = (RegularTextView)itemView.findViewById(R.id.txt_jaywalters_clockin);
            txt_midtech_hide_clockin = (RegularTextView)itemView.findViewById(R.id.txt_midtech_hide_clockin);
            empClockClinic = (RegularTextView)itemView.findViewById(R.id.empClockClinic);
            empClockPersonnel = (RegularTextView)itemView.findViewById(R.id.empClockPersonnel);
            empClockLocation = (RegularTextView)itemView.findViewById(R.id.empClockLocation);
            empClockContact = (RegularTextView)itemView.findViewById(R.id.empClockContact);
            empClockDate = (RegularTextView)itemView.findViewById(R.id.empClockDate);
            empClockServiceProvided = (RegularTextView)itemView.findViewById(R.id.empClockServiceProvided);
            empClockPhone = (RegularTextView)itemView.findViewById(R.id.empClockPhone);
            spend_time_text = (RegularTextView)itemView.findViewById(R.id.spend_time_text);
            empClockPrep = (RegularTextView)itemView.findViewById(R.id.empClockPrep);
            empClockIn = (RegularButton) itemView.findViewById(R.id.empClockIn);
            empClockOut = (RegularButton) itemView.findViewById(R.id.empClockOut);
            viewlist_open_clockin = (LinearLayout) itemView.findViewById(R.id.viewlist_open_clockin);
            serviceProvider_clockin = (LinearLayout) itemView.findViewById(R.id.serviceProvider_clockin);
            dropdown_clockin = (LinearLayout) itemView.findViewById(R.id.dropdown_clockin);
            view_listclockin = (LinearLayout) itemView.findViewById(R.id.view_listclockin);
            hide_clockin = (LinearLayout) itemView.findViewById(R.id.hide_clockin);
            recycler_clockin_viewlist = (RecyclerView) itemView.findViewById(R.id.recycler_clockin_viewlist);
            layout_accept_decline = (LinearLayout)itemView.findViewById(R.id.layout_accept_decline);
        }



    }

    private void intializeRecyclerViewListClockin(ItemViewHolder viewHolder) {
        viewHolder.recycler_clockin_viewlist.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.recycler_clockin_viewlist.setItemAnimator(new DefaultItemAnimator());
        viewHolder.recycler_clockin_viewlist.setAdapter(new ViewListAdapter(context, othername_listtemp, iduser));

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


    private class UpdateClinicMilage extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendUpdateClinicMilage(APIUrl.UPDATECLINICMILAGE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {
                        holderr.milage_layout.setVisibility(View.GONE);
                        holderr.milage_layout_filled.setVisibility(View.VISIBLE);
                        holderr.milage.setText(holderr.edt_milage.getText().toString().trim());
                        holderr.drive_time.setText(holderr.edt_drive_time.getText().toString().trim());
                    } else if (object.has("status") && object.getString("status").equals("error")) {
                        alertDialog(object.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
            }

        }
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
                        try {
                            context.startService(new Intent(context,ClinicService.class));
                        }catch (Exception e){

                        }


                        if(!SessionManager.getStopService(context).equals("stop")){
                            SessionManager.setClinicid(context,SessionManager.getClinicid(context));
                            context.startService(new Intent(context, MyLocationService.class));


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

    private class locationUpdateAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendUpdatelocationAsync(APIUrl.UPDATELOCATION , encrypted);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                        //    alertDialogSucess(jsonObject.getString("message"));

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

                        day_clinic_list.remove(pos);
                        notifyDataSetChanged();
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
    public void removeHandler()
    {
        Log.i("Stop Handler ","Yes");
        handler.removeCallbacks(run);
    }
}
