package professional.wellness.health.com.employeeapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.Model.ClockOutReminder;
import professional.wellness.health.com.employeeapp.Model.ClockinReminder;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 04-08-2017.
 */

public class ClockOutAdapter  extends RecyclerView.Adapter<ClockOutAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<ClockOutReminder> clockout_reminder_list;
    private String itam = "";
    String pos = "";
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private String clock_out_value = "";
    /*    items*/
    public ClockOutAdapter(Context context, ArrayList<ClockOutReminder> clockout_reminder_list, String clock_out_value) {
        this.context = context;
        this.clock_out_value = clock_out_value;
        this.clockout_reminder_list = clockout_reminder_list;
        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);
    }


    @Override
    public ClockOutAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_clockout_default, parent, false);

        return new ClockOutAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ClockOutAdapter.ItemViewHolder holder, final int position) {
        //holder.card_name.setText(mycards.get(position));
        holder.clockin_reminder.setText(clockout_reminder_list.get(position).getName());

/*        String actualpos = String.valueOf(position);
        if (actualpos.equals(pos)) {
            holder.selected_checkbox.setVisibility(View.VISIBLE);

        } else {
            holder.selected_checkbox.setVisibility(View.GONE);
        }

        if(clock_out_value.equals(clockout_reminder_list.get(position).getName())){
            holder.selected_checkbox.setVisibility(View.VISIBLE);

            }else{
            holder.selected_checkbox.setVisibility(View.GONE);

        }*/

        if(clock_out_value.equals(clockout_reminder_list.get(position).getName())) {
            holder.selected_checkbox.setVisibility(View.VISIBLE);
        }
        else {
            holder.selected_checkbox.setVisibility(View.GONE);
        }


        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = String.valueOf(position);
                notifyDataSetChanged();
                String clockoutstatus;
                clock_out_value = clockout_reminder_list.get(position).getName();
                if(clockout_reminder_list.get(position).getName().equals("Clock out after default time")){
                    clockoutstatus = String.valueOf(1);
                }else{
                    clockoutstatus = String.valueOf(0);
                }
                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(context));
                    object.put("device_id", SessionManager.getDeviceId(context));
                    object.put("platform_type", "android app");
                    object.put("clock_out_value",clockoutstatus);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new userClockOutUpdateAsync().execute(encrypted);
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
        return clockout_reminder_list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // private final TextView card_name;
        private RegularTextView clockin_reminder;
        private ImageView selected_checkbox;
        private LinearLayout main_layout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            clockin_reminder = (RegularTextView)itemView.findViewById(R.id.clockin_reminder);
            selected_checkbox = (ImageView)itemView.findViewById(R.id.selected_checkbox);
            main_layout = (LinearLayout)itemView.findViewById(R.id.main_layout);


        }


    }

    private class userClockOutUpdateAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendClockOutUpdateRequest(APIUrl.UPDATECLOCKOUTSETTING, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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

}
