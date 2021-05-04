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
import java.util.List;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.Model.Available;
import professional.wellness.health.com.employeeapp.Model.AvailableNew;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.MonthAvailableDetailActivity;

/**
 * Created by Admin on 16-08-2017.
 */

public class AvailableDetailAdapter  extends RecyclerView.Adapter<AvailableDetailAdapter.ItemViewHolder> {
    private Context context;
    private EncryptionClass encryptionClass;
    private ArrayList<AvailableNew> available_list;
    private List<Available> available_id;
    private RESTInteraction restInteraction;
    private double latitude;
    private double longitude;
    private ItemViewHolder itemViewHolder;
    int pos = 0;

    /*    items*/
    public AvailableDetailAdapter(Context context, ArrayList<AvailableNew> available_list) {
        this.context = context;
        this.available_list = available_list;

        encryptionClass = EncryptionClass.getInstance(context);
        restInteraction = RESTInteraction.getInstance(context);
    }


    @Override
    public AvailableDetailAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_detail_row, parent, false);


        GPSTracker gps = new GPSTracker(context);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }

        return new AvailableDetailAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final AvailableDetailAdapter.ItemViewHolder holder, final int position) {
        itemViewHolder = holder;
    /*    for (int i = 0; i < available_id.size(); i++) {*/
            holder.empAcceptReject.setVisibility(View.VISIBLE);

       /*     if (available_id.get(i).getEid().equals(available_list.get(position).getDate())) {*/

                if (available_list.get(position).getType().equals("Unfilled")) {
                    holder.date_layout.setVisibility(View.GONE);
                    holder.decline_preffred.setVisibility(View.INVISIBLE);
                    holder.status_preffred.setText(available_list.get(position).getStatus_name());
                    holder.estimate_duration_preffred.setText("Estimate duration : ");
                    holder.personel_preffred.setText("Personnel :        ");
                    holder.location_preffred.setText(available_list.get(position).getLocation_name());
                    holder.date_preffred.setText("              ");
                    holder.clinic_time_preffred.setText("              ");
                    holder.service_provider_preffred.setText("        ");
                }else if(available_list.get(position).getType().equals("available_clinics")){
                    holder.decline_preffred.setVisibility(View.INVISIBLE);
                    holder.date_layout.setVisibility(View.VISIBLE);
                    holder.title_date.setText(available_list.get(position).getDate());
                    holder.status_preffred.setText(available_list.get(position).getStatus_name());
                    holder.estimate_duration_preffred.setText("Estimate duration : " + available_list.get(position).getEstimated_duration());
                    holder.personel_preffred.setText("Personnel : " + available_list.get(position).getPersonnel());
                    holder.location_preffred.setText(available_list.get(position).getLocation_name());
                    holder.date_preffred.setText(available_list.get(position).getDate());
                    holder.clinic_time_preffred.setText(available_list.get(position).getTime());
                    holder.service_provider_preffred.setText(available_list.get(position).getService_provider());
                }

                else {
                    holder.date_layout.setVisibility(View.VISIBLE);
                    holder.title_date.setText(available_list.get(position).getDate());
                    holder.decline_preffred.setVisibility(View.VISIBLE);
                    holder.status_preffred.setText(available_list.get(position).getStatus_name());
                    holder.estimate_duration_preffred.setText("Estimate duration : " + available_list.get(position).getEstimated_duration());
                    holder.personel_preffred.setText("Personnel : " + available_list.get(position).getPersonnel());
                    holder.location_preffred.setText(available_list.get(position).getLocation_name());
                    holder.date_preffred.setText(available_list.get(position).getDate());
                    holder.clinic_time_preffred.setText(available_list.get(position).getTime());
                    holder.service_provider_preffred.setText(available_list.get(position).getService_provider());
                }

       /*     } else {

            }*/
/*        }*/
        holder.empAccepte.setOnClickListener(new View.OnClickListener() {
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
                    object.put("clinic_id", available_list.get(position).getId());
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

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bottom_layout.setVisibility(View.VISIBLE);
                holder.accept_layout.setVisibility(View.GONE);
                pos = position;
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(context));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(context));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("clinic_id", available_list.get(position).getId());
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


        holder.decline_preffred.setOnClickListener(new View.OnClickListener() {
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
                    object.put("clinic_id", available_list.get(position).getId());
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

    }

    @Override
    public int getItemCount() {
        return available_list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout date_layout;
        private final RegularTextView title_date;
        private final RegularTextView status_preffred;
        private final RegularTextView estimate_duration_preffred;
        private final RegularTextView personel_preffred;
        private final RegularTextView location_preffred;
        private final RegularTextView date_preffred;
        private final RegularTextView clinic_time_preffred;
        private final RegularTextView service_provider_preffred;
        private final RegularButton decline_preffred;
        private final RegularButton empAccepte;
        private final RegularButton done;
        private final LinearLayout bottom_layout;
        private final LinearLayout accept_layout;
        private final  LinearLayout empAcceptReject;

        public ItemViewHolder(View itemView) {
            super(itemView);

            date_layout = (LinearLayout) itemView.findViewById(R.id.date_layout);
            title_date = (RegularTextView) itemView.findViewById(R.id.title_date);

            status_preffred = (RegularTextView) itemView.findViewById(R.id.status_preffred);
            estimate_duration_preffred = (RegularTextView) itemView.findViewById(R.id.estimate_duration_preffred);
            personel_preffred = (RegularTextView) itemView.findViewById(R.id.personel_preffred);
            location_preffred = (RegularTextView) itemView.findViewById(R.id.location_preffred);
            date_preffred = (RegularTextView) itemView.findViewById(R.id.date_preffred);
            clinic_time_preffred = (RegularTextView) itemView.findViewById(R.id.clinic_time_preffred);
            service_provider_preffred = (RegularTextView) itemView.findViewById(R.id.service_provider_preffred);
            decline_preffred = (RegularButton) itemView.findViewById(R.id.decline_preffred);
            empAccepte = (RegularButton) itemView.findViewById(R.id.empAccepte);
            done = (RegularButton) itemView.findViewById(R.id.done);
            bottom_layout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
            accept_layout = (LinearLayout) itemView.findViewById(R.id.accept_layout);
            empAcceptReject = (LinearLayout) itemView.findViewById(R.id.empAcceptReject);

        }


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
                        MonthAvailableDetailActivity.monthAvailableDetailActivity.finish();
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
                        MonthAvailableDetailActivity.monthAvailableDetailActivity.finish();


                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialogSlot(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }

    public void alertDialogSlot(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slot_dialog, null);
       /* RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.done);
        txtMessage.setText(message);*/
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.done);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                available_list.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, available_list.size());
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });
    }


}

