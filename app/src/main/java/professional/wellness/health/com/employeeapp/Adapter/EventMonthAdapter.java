package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.Model.AllAnnouncementList;
import professional.wellness.health.com.employeeapp.Model.MyClinicMonth;
import professional.wellness.health.com.employeeapp.Model.ViewList;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.CompanyAnnouncementDetailActivity;
import professional.wellness.health.com.employeeapp.activity.MonthClinicDetailAcivity;

/**
 * Created by Admin on 02-08-2017.
 */

public class EventMonthAdapter extends RecyclerView.Adapter<EventMonthAdapter.ItemViewHolder>  {

    private Context context;
    private ArrayList<MyClinicMonth> temp;
    private ArrayList<ViewList> viewlist;
    private ArrayList<ViewList> viewlisttemp;

    public EventMonthAdapter(Context context, ArrayList<MyClinicMonth> temp, ArrayList<ViewList> viewlist) {
        this.context = context;
        this.viewlist = viewlist;
        viewlisttemp = new ArrayList<>();
        this.temp = temp;
    }


    @Override
    public EventMonthAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event_month_dialog, parent, false);


        return new EventMonthAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final EventMonthAdapter.ItemViewHolder holder, final int position) {
/*        holder.empDate.setText(items.get(position).getCreated_at());*/

        holder.status_name.setText(temp.get(position).getName());
        if(temp.get(position).getStatus_name().equals("Past clinic")){
            holder.ll_layout_status.setBackgroundResource(R.drawable.round_gray);
        }else{
            holder.ll_layout_status.setBackgroundResource(R.drawable.round_blue);
        }
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RegularTextView status_name;
        private final LinearLayout ll_layout_status;


        public ItemViewHolder(View itemView) {
            super(itemView);


            status_name = (RegularTextView)itemView.findViewById(R.id.status_name);
            ll_layout_status = (LinearLayout)itemView.findViewById(R.id.ll_layout_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
         /*   if (!AppUtills.isNetworkAvailable(context)) {
                viewlisttemp.clear();
                for (int j = 0; j < temp.size(); j++) {
                    for (int i = 0; i < viewlist.size(); i++) {
                        if (temp.get(j).getId().equals(viewlist.get(i).getEid())) {
                            viewlisttemp.add(new ViewList(viewlist.get(i).getEid(), viewlist.get(i).getName()));
                        }
                    }

                }
                ArrayList<String> arrayLista;
                arrayLista = new ArrayList<>();
                try {
                    arrayLista = SessionManager.getclockoutidofflinedata(context);
                }catch (Exception e){

                }

                if (!(arrayLista.size() == 0))
                for (int j = 0; j < arrayLista.size() ; j++) {
                    if(!arrayLista.get(j).equals(temp.get(getPosition()).getId())){
                        Intent intent = new Intent(context, MonthClinicDetailAcivity.class);
                    intent.putExtra("id", temp.get(getPosition()).getId());
                    intent.putExtra("provider_id", temp.get(getPosition()).getProvider_id());
                    intent.putExtra("name", temp.get(getPosition()).getName());
                    intent.putExtra("phone", temp.get(getPosition()).getPhone());
                    intent.putExtra("location_name", temp.get(getPosition()).getLocation_name());
                    intent.putExtra("latitude", temp.get(getPosition()).getLongitude());
                    intent.putExtra("longitude", temp.get(getPosition()).getLongitude());
                    intent.putExtra("time", temp.get(getPosition()).getTime());
                    intent.putExtra("end_time", temp.get(getPosition()).getEnd_time());
                    intent.putExtra("prep_time", temp.get(getPosition()).getPrep_time());
                    intent.putExtra("default_unfilled_time", temp.get(getPosition()).getDefault_unfilled_time());
                    intent.putExtra("date", temp.get(getPosition()).getDate());
                    intent.putExtra("create_timestamp", temp.get(getPosition()).getCreate_timestamp());
                    intent.putExtra("estimated_duration", temp.get(getPosition()).getEstimated_duration());
                    intent.putExtra("personnel", temp.get(getPosition()).getPersonnel());
                    intent.putExtra("primary_provider", temp.get(getPosition()).getPrimary_provider());
                    intent.putExtra("medtech_provider", temp.get(getPosition()).getMedtech_provider());
                    intent.putExtra("other_provider", temp.get(getPosition()).getOther_provider());
                    intent.putExtra("service_provider", temp.get(getPosition()).getService_provider());
                    intent.putExtra("number_of_provider", temp.get(getPosition()).getNumber_of_provider());
                    intent.putExtra("jay_walters", temp.get(getPosition()).getJay_walters());
                    intent.putExtra("timezone", temp.get(getPosition()).getTimezone());
                    intent.putExtra("status", temp.get(getPosition()).getStatus());
                    intent.putExtra("created_at", temp.get(getPosition()).getCreated_at());
                    intent.putExtra("updated_at", temp.get(getPosition()).getUpdated_at());
                    intent.putExtra("primary_name", temp.get(getPosition()).getPrimary_name());
                    intent.putExtra("medtech_name", temp.get(getPosition()).getMedtech_name());
                    intent.putExtra("system_calender", temp.get(getPosition()).getSystem_calender());
                    intent.putExtra("clocked", temp.get(getPosition()).getClocked());
                    intent.putExtra("type", temp.get(getPosition()).getType());
                    intent.putExtra("status_name", temp.get(getPosition()).getStatus_name());
                    intent.putExtra("duration", temp.get(getPosition()).getDuration());
                    intent.putExtra("mileage_required", temp.get(getPosition()).getMileage_required());
                    intent.putExtra("drive_time_required", temp.get(getPosition()).getDrive_time_required());
                    intent.putExtra("mileage_status", temp.get(getPosition()).getMileage_status());
                    intent.putExtra("drive_time_status", temp.get(getPosition()).getDrive_time_status());
                    intent.putExtra("spend_time", temp.get(getPosition()).getClinic_spend_time());
                    intent.putExtra("mileage", temp.get(getPosition()).getMileage());
                    intent.putExtra("drive_time", temp.get(getPosition()).getDrive_time());
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) viewlisttemp);
                    intent.putExtra("BUNDLE", args);
                    context.startActivity(intent);
                    }else{

                        Intent intent = new Intent(context, MonthClinicDetailAcivity.class);
                        intent.putExtra("id", temp.get(getPosition()).getId());
                        intent.putExtra("provider_id", temp.get(getPosition()).getProvider_id());
                        intent.putExtra("name", temp.get(getPosition()).getName());
                        intent.putExtra("phone", temp.get(getPosition()).getPhone());
                        intent.putExtra("location_name", temp.get(getPosition()).getLocation_name());
                        intent.putExtra("latitude", temp.get(getPosition()).getLongitude());
                        intent.putExtra("longitude", temp.get(getPosition()).getLongitude());
                        intent.putExtra("time", temp.get(getPosition()).getTime());
                        intent.putExtra("end_time", temp.get(getPosition()).getEnd_time());
                        intent.putExtra("prep_time", temp.get(getPosition()).getPrep_time());
                        intent.putExtra("default_unfilled_time", temp.get(getPosition()).getDefault_unfilled_time());
                        intent.putExtra("date", temp.get(getPosition()).getDate());
                        intent.putExtra("create_timestamp", temp.get(getPosition()).getCreate_timestamp());
                        intent.putExtra("estimated_duration", temp.get(getPosition()).getEstimated_duration());
                        intent.putExtra("personnel", temp.get(getPosition()).getPersonnel());
                        intent.putExtra("primary_provider", temp.get(getPosition()).getPrimary_provider());
                        intent.putExtra("medtech_provider", temp.get(getPosition()).getMedtech_provider());
                        intent.putExtra("other_provider", temp.get(getPosition()).getOther_provider());
                        intent.putExtra("service_provider", temp.get(getPosition()).getService_provider());
                        intent.putExtra("number_of_provider", temp.get(getPosition()).getNumber_of_provider());
                        intent.putExtra("jay_walters", temp.get(getPosition()).getJay_walters());
                        intent.putExtra("timezone", temp.get(getPosition()).getTimezone());
                        intent.putExtra("status", temp.get(getPosition()).getStatus());
                        intent.putExtra("created_at", temp.get(getPosition()).getCreated_at());
                        intent.putExtra("updated_at", temp.get(getPosition()).getUpdated_at());
                        intent.putExtra("primary_name", temp.get(getPosition()).getPrimary_name());
                        intent.putExtra("medtech_name", temp.get(getPosition()).getMedtech_name());
                        intent.putExtra("system_calender", temp.get(getPosition()).getSystem_calender());
                        intent.putExtra("clocked", temp.get(getPosition()).getClocked());
                        intent.putExtra("type", "Past");
                        intent.putExtra("status_name", temp.get(getPosition()).getStatus_name());
                        intent.putExtra("duration", temp.get(getPosition()).getDuration());
                        intent.putExtra("mileage_required", temp.get(getPosition()).getMileage_required());
                        intent.putExtra("drive_time_required", temp.get(getPosition()).getDrive_time_required());
                        intent.putExtra("mileage_status", temp.get(getPosition()).getMileage_status());
                        intent.putExtra("drive_time_status", temp.get(getPosition()).getDrive_time_status());
                        intent.putExtra("spend_time", temp.get(getPosition()).getClinic_spend_time());
                        intent.putExtra("mileage", temp.get(getPosition()).getMileage());
                        intent.putExtra("drive_time", temp.get(getPosition()).getDrive_time());
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST", (Serializable) viewlisttemp);
                        intent.putExtra("BUNDLE", args);
                        context.startActivity(intent);
                    }
                }

                else{
                    Intent intent = new Intent(context, MonthClinicDetailAcivity.class);
                    intent.putExtra("id", temp.get(getPosition()).getId());
                    intent.putExtra("provider_id", temp.get(getPosition()).getProvider_id());
                    intent.putExtra("name", temp.get(getPosition()).getName());
                    intent.putExtra("phone", temp.get(getPosition()).getPhone());
                    intent.putExtra("location_name", temp.get(getPosition()).getLocation_name());
                    intent.putExtra("latitude", temp.get(getPosition()).getLongitude());
                    intent.putExtra("longitude", temp.get(getPosition()).getLongitude());
                    intent.putExtra("time", temp.get(getPosition()).getTime());
                    intent.putExtra("end_time", temp.get(getPosition()).getEnd_time());
                    intent.putExtra("prep_time", temp.get(getPosition()).getPrep_time());
                    intent.putExtra("default_unfilled_time", temp.get(getPosition()).getDefault_unfilled_time());
                    intent.putExtra("date", temp.get(getPosition()).getDate());
                    intent.putExtra("create_timestamp", temp.get(getPosition()).getCreate_timestamp());
                    intent.putExtra("estimated_duration", temp.get(getPosition()).getEstimated_duration());
                    intent.putExtra("personnel", temp.get(getPosition()).getPersonnel());
                    intent.putExtra("primary_provider", temp.get(getPosition()).getPrimary_provider());
                    intent.putExtra("medtech_provider", temp.get(getPosition()).getMedtech_provider());
                    intent.putExtra("other_provider", temp.get(getPosition()).getOther_provider());
                    intent.putExtra("service_provider", temp.get(getPosition()).getService_provider());
                    intent.putExtra("number_of_provider", temp.get(getPosition()).getNumber_of_provider());
                    intent.putExtra("jay_walters", temp.get(getPosition()).getJay_walters());
                    intent.putExtra("timezone", temp.get(getPosition()).getTimezone());
                    intent.putExtra("status", temp.get(getPosition()).getStatus());
                    intent.putExtra("created_at", temp.get(getPosition()).getCreated_at());
                    intent.putExtra("updated_at", temp.get(getPosition()).getUpdated_at());
                    intent.putExtra("primary_name", temp.get(getPosition()).getPrimary_name());
                    intent.putExtra("medtech_name", temp.get(getPosition()).getMedtech_name());
                    intent.putExtra("system_calender", temp.get(getPosition()).getSystem_calender());
                    intent.putExtra("clocked", temp.get(getPosition()).getClocked());
                    intent.putExtra("type", temp.get(getPosition()).getType());
                    intent.putExtra("status_name", temp.get(getPosition()).getStatus_name());
                    intent.putExtra("duration", temp.get(getPosition()).getDuration());
                    intent.putExtra("mileage_required", temp.get(getPosition()).getMileage_required());
                    intent.putExtra("drive_time_required", temp.get(getPosition()).getDrive_time_required());
                    intent.putExtra("mileage_status", temp.get(getPosition()).getMileage_status());
                    intent.putExtra("drive_time_status", temp.get(getPosition()).getDrive_time_status());
                    intent.putExtra("spend_time", temp.get(getPosition()).getClinic_spend_time());
                    intent.putExtra("mileage", temp.get(getPosition()).getMileage());
                    intent.putExtra("drive_time", temp.get(getPosition()).getDrive_time());
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) viewlisttemp);
                    intent.putExtra("BUNDLE", args);
                    context.startActivity(intent);
                }

            }else{*/
                viewlisttemp.clear();
                for (int j = 0; j < temp.size(); j++) {
                    for (int i = 0; i < viewlist.size(); i++) {
                        if (temp.get(j).getId().equals(viewlist.get(i).getEid())) {
                            viewlisttemp.add(new ViewList(viewlist.get(i).getEid(), viewlist.get(i).getName()));
                        }
                    }

                }

                Intent intent = new Intent(context, MonthClinicDetailAcivity.class);
                intent.putExtra("id", temp.get(getPosition()).getId());
                intent.putExtra("provider_id", temp.get(getPosition()).getProvider_id());
                intent.putExtra("name", temp.get(getPosition()).getName());
                intent.putExtra("phone", temp.get(getPosition()).getPhone());
                intent.putExtra("location_name", temp.get(getPosition()).getLocation_name());
                intent.putExtra("latitude", temp.get(getPosition()).getLongitude());
                intent.putExtra("longitude", temp.get(getPosition()).getLongitude());
                intent.putExtra("time", temp.get(getPosition()).getTime());
                intent.putExtra("end_time", temp.get(getPosition()).getEnd_time());
                intent.putExtra("prep_time", temp.get(getPosition()).getPrep_time());
                intent.putExtra("default_unfilled_time", temp.get(getPosition()).getDefault_unfilled_time());
                intent.putExtra("date", temp.get(getPosition()).getDate());
                intent.putExtra("create_timestamp", temp.get(getPosition()).getCreate_timestamp());
                intent.putExtra("estimated_duration", temp.get(getPosition()).getEstimated_duration());
                intent.putExtra("personnel", temp.get(getPosition()).getPersonnel());
                intent.putExtra("primary_provider", temp.get(getPosition()).getPrimary_provider());
                intent.putExtra("medtech_provider", temp.get(getPosition()).getMedtech_provider());
                intent.putExtra("other_provider", temp.get(getPosition()).getOther_provider());
                intent.putExtra("service_provider", temp.get(getPosition()).getService_provider());
                intent.putExtra("number_of_provider", temp.get(getPosition()).getNumber_of_provider());
                intent.putExtra("jay_walters", temp.get(getPosition()).getJay_walters());
                intent.putExtra("timezone", temp.get(getPosition()).getTimezone());
                intent.putExtra("status", temp.get(getPosition()).getStatus());
                intent.putExtra("created_at", temp.get(getPosition()).getCreated_at());
                intent.putExtra("updated_at", temp.get(getPosition()).getUpdated_at());
                intent.putExtra("primary_name", temp.get(getPosition()).getPrimary_name());
                intent.putExtra("medtech_name", temp.get(getPosition()).getMedtech_name());
                intent.putExtra("system_calender", temp.get(getPosition()).getSystem_calender());
                intent.putExtra("clocked", temp.get(getPosition()).getClocked());
                intent.putExtra("type", temp.get(getPosition()).getType());
                intent.putExtra("status_name", temp.get(getPosition()).getStatus_name());
                intent.putExtra("duration", temp.get(getPosition()).getDuration());
                intent.putExtra("mileage_required", temp.get(getPosition()).getMileage_required());
                intent.putExtra("drive_time_required", temp.get(getPosition()).getDrive_time_required());
                intent.putExtra("mileage_status", temp.get(getPosition()).getMileage_status());
                intent.putExtra("drive_time_status", temp.get(getPosition()).getDrive_time_status());
                intent.putExtra("spend_time", temp.get(getPosition()).getClinic_spend_time());
                intent.putExtra("mileage", temp.get(getPosition()).getMileage());
                intent.putExtra("drive_time", temp.get(getPosition()).getDrive_time());

                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) viewlisttemp);
                intent.putExtra("BUNDLE", args);
                context.startActivity(intent);





        }
    }

}
