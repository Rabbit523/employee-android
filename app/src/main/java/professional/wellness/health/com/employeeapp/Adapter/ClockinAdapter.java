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

import professional.wellness.health.com.employeeapp.Model.ClockinReminder;
import professional.wellness.health.com.employeeapp.Model.PrepTime;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.fragment.ProfileFragment;

/**
 * Created by Admin on 04-08-2017.
 */

public class ClockinAdapter  extends RecyclerView.Adapter<ClockinAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<ClockinReminder> clockin_reminder_list;
    private String itam = "";
    String pos = "";
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private String clock_in_value = "";
    /*    items*/
    public ClockinAdapter(Context context, ArrayList<ClockinReminder> clockin_reminder_list, String clock_in_value) {
        this.context = context;
        this.clock_in_value = clock_in_value;
        this.clockin_reminder_list = clockin_reminder_list;
        restInteraction = RESTInteraction.getInstance(context);
        encryptionClass = EncryptionClass.getInstance(context);
    }


    @Override
    public ClockinAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_clockin_default, parent, false);

        return new ClockinAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ClockinAdapter.ItemViewHolder holder, final int position) {
        //holder.card_name.setText(mycards.get(position));
        holder.clockin_reminder.setText("Reminder clock in :" + clockin_reminder_list.get(position).getName());

        String actualPos = String.valueOf(position);
/*
        if(!clock_in_value.equals("")) {
            if (actualPos.equals(pos)) {
                holder.selected_checkbox.setVisibility(View.VISIBLE);

            } else {
                holder.selected_checkbox.setVisibility(View.GONE);
            }
        }
*/
        if(clock_in_value.equals(clockin_reminder_list.get(position).getName())) {
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
                clock_in_value = clockin_reminder_list.get(position).getName();
                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(context));
                    object.put("device_id", SessionManager.getDeviceId(context));
                    object.put("platform_type", "android app");
                    object.put("clock_in_value", clockin_reminder_list.get(position).getName());
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new userClockinUpdateAsync().execute(encrypted);
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
        return clockin_reminder_list.size();
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
    private class userClockinUpdateAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendClockinUpdateRequest(APIUrl.UPDATECLOCKINSETTING, params[0]);
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



