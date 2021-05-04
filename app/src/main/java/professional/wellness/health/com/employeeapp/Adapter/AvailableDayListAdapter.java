package professional.wellness.health.com.employeeapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.Model.AvailableDay;
import professional.wellness.health.com.employeeapp.Model.MyClinicDay;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularEditText;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.MonthAvailableDetailActivity;

/**
 * Created by Admin on 10-08-2017.
 */

public class AvailableDayListAdapter extends RecyclerView.Adapter<AvailableDayListAdapter.ItemViewHolder> {

    private LinearLayout milage_layout;
    private RegularTextView milage;
    private RegularTextView drive_time;
    private LinearLayout milage_layout_filled;
    private Context context;
    private ArrayList<AvailableDay> day_clinic_list;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private RegularEditText edt_milage;
    private RegularEditText edt_drive_time;
    private int pos;
    private double latitude;
    private double longitude;

    /*    items*/
    public AvailableDayListAdapter(Context context, ArrayList<AvailableDay> day_clinic_list) {
        this.context = context;

        this.day_clinic_list = day_clinic_list;
        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);

        GPSTracker gps = new GPSTracker(context);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }
    }


    @Override
    public AvailableDayListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_my_clinic_day_row, parent, false);

        return new AvailableDayListAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final AvailableDayListAdapter.ItemViewHolder holder, final int position) {
        //holder.card_name.setText(mycards.get(position));

        if (day_clinic_list.get(position).getType().equals("Unfilled")) {
            holder.status_name.setText(day_clinic_list.get(position).getStatus_name());
            holder.prep_time.setText("Prep time " + day_clinic_list.get(position).getPrep_time());
            // holder.duration.setText("Duration : " + day_clinic_list.get(position).getDuration());
            holder.past_personal.setText("Personnel : " + day_clinic_list.get(position).getPersonnel());
            holder.past_date.setText("Date: " + day_clinic_list.get(position).getDate());
            holder.location.setText(day_clinic_list.get(position).getLocation_name());
            holder.clinic_time.setText("Clinic time : " + day_clinic_list.get(position).getTime());
            holder.service_provider.setText(day_clinic_list.get(position).getService_provider());
            holder.emp_decline.setVisibility(View.INVISIBLE);

        }else if(day_clinic_list.get(position).getType().equals("available_clinics")){
            holder.status_name.setText(day_clinic_list.get(position).getStatus_name());
            holder.prep_time.setText("Prep time " + day_clinic_list.get(position).getPrep_time());
            // holder.duration.setText("Duration : " + day_clinic_list.get(position).getDuration());
            holder.past_personal.setText("Personnel : " + day_clinic_list.get(position).getPersonnel());
            holder.past_date.setText("Date: " + day_clinic_list.get(position).getDate());
            holder.location.setText(day_clinic_list.get(position).getLocation_name());
            holder.clinic_time.setText("Clinic time : " + day_clinic_list.get(position).getTime());
            holder.service_provider.setText(day_clinic_list.get(position).getService_provider());
            holder.emp_decline.setVisibility(View.INVISIBLE);
        }

        else {

            holder.status_name.setText(day_clinic_list.get(position).getStatus_name());
            holder.prep_time.setText("Prep time " + day_clinic_list.get(position).getPrep_time());
            // holder.duration.setText("Duration : " + day_clinic_list.get(position).getDuration());
            holder.past_personal.setText("Personnel : " + day_clinic_list.get(position).getPersonnel());
            holder.past_date.setText("Date: " + day_clinic_list.get(position).getDate());
            holder.location.setText(day_clinic_list.get(position).getLocation_name());
            holder.clinic_time.setText("Clinic time : " + day_clinic_list.get(position).getTime());
            holder.service_provider.setText(day_clinic_list.get(position).getService_provider());
        }

        holder.emp_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* holder.bottom_layout.setVisibility(View.GONE);
                holder.accept_layout.setVisibility(View.VISIBLE);*/

                pos = position;
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(context));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(context));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("clinic_id", day_clinic_list.get(position).getId());
                    object.put("status", "1");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new acceptClinicAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        holder.emp_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(context));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(context));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("clinic_id", day_clinic_list.get(position).getId());
                    object.put("status", "0");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new RejectClinicAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });



        //  milage.setText(day_clinic_list.get(position).getMileage_required());
     //   drive_time.setText(day_clinic_list.get(position).getDrive_time_required());
/*
        if (day_clinic_list.get(position).getMileage_status().equals("1")) {
            milage_layout.setVisibility(View.GONE);
            milage_layout_filled.setVisibility(View.VISIBLE);
        } else if (day_clinic_list.get(position).getDrive_time_status().equals("1")) {
            milage_layout.setVisibility(View.GONE);
            milage_layout_filled.setVisibility(View.VISIBLE);
        } else {
            milage_layout.setVisibility(View.VISIBLE);
            milage_layout_filled.setVisibility(View.GONE);
        }*/


/*
        holder.milage_submit.setOnClickListener(new View.OnClickListener() {

            String clinic_id = day_clinic_list.get(position).getId();

            @Override
            public void onClick(View view) {
                if (edt_milage.getText().toString().length() == 0) {
                    alertDialog("PLease Enter Milage (RT)");
                } else if (edt_drive_time.getText().toString().length() == 0) {
                    alertDialog("PLease Enter Drive time (RT)");
                } else {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(context));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(context));
                        object.put("clinic_id", clinic_id);
                        object.put("mileage_required", edt_milage.getText().toString().trim());
                        object.put("drive_time_required", edt_drive_time.getText().toString().trim());
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new UpdateClinicMilage().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }
        });
*/

    }

    private class RejectClinicAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendAcceptRejectClinicRequest(APIUrl.AVAILABLEACCEPTREJECTCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        alertDialogSuccess(jsonObject.getString("message"));
                       // MonthAvailableDetailActivity.monthAvailableDetailActivity.finish();
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


    private class acceptClinicAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendAcceptRejectClinicRequest(APIUrl.AVAILABLEACCEPTREJECTCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        alertDialogSuccess(jsonObject.getString("message"));
                      //  MonthAvailableDetailActivity.monthAvailableDetailActivity.finish();


                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                       // alertDialogSlot(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }

    public void alertDialogSuccess(String message) {
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
        dialog.setCancelable(false);
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day_clinic_list.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, day_clinic_list.size());
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });
    }


    @Override
    public int getItemCount() {
        return day_clinic_list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
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
        private LinearLayout layout_accept_decline;
        private RegularButton emp_decline;
        private RegularButton emp_accept;

        public ItemViewHolder(View itemView) {
            super(itemView);
            //card_name = (TextView) itemView.findViewById(R.id.card_name);
            milage_layout = (LinearLayout) itemView.findViewById(R.id.milage_layout);
            milage_layout_filled = (LinearLayout) itemView.findViewById(R.id.milage_layout_filled);
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
            milage_submit = (RegularButton) itemView.findViewById(R.id.milage_submit);
            edt_milage = (RegularEditText) itemView.findViewById(R.id.edt_milage);
            edt_drive_time = (RegularEditText) itemView.findViewById(R.id.edt_drive_time);

            layout_accept_decline = (LinearLayout)itemView.findViewById(R.id.layout_accept_decline);
            emp_decline = (RegularButton)itemView.findViewById(R.id.emp_decline);
            emp_accept = (RegularButton)itemView.findViewById(R.id.emp_accept);



        }


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
                        milage_layout.setVisibility(View.GONE);
                        milage_layout_filled.setVisibility(View.VISIBLE);
                        milage.setText(edt_milage.getText().toString().trim());
                        drive_time.setText(edt_drive_time.getText().toString().trim());
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
}
