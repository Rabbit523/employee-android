package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.Model.AvailableMonth;
import professional.wellness.health.com.employeeapp.Model.MyClinicMonth;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.MonthAvailableDetailActivity;
import professional.wellness.health.com.employeeapp.activity.MonthClinicDetailAcivity;

/**
 * Created by Admin on 10-08-2017.
 */

public class EventMonthAvailableAdapter extends RecyclerView.Adapter<EventMonthAvailableAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<AvailableMonth> temp;
    private RegularTextView textview_multipleevent_date;

    public EventMonthAvailableAdapter(Context context, ArrayList<AvailableMonth> temp, RegularTextView textview_multipleevent_date) {
        this.context = context;
        this.textview_multipleevent_date = textview_multipleevent_date;
        this.temp = temp;
    }


    @Override
    public EventMonthAvailableAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event_month_dialog, parent, false);


        return new EventMonthAvailableAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final EventMonthAvailableAdapter.ItemViewHolder holder, final int position) {
/*        holder.empDate.setText(items.get(position).getCreated_at());*/


        holder.status_name.setText(temp.get(position).getName());
        if (temp.get(position).getStatus_name().equals("Past clinic")) {
            holder.ll_layout_status.setBackgroundResource(R.drawable.round_gray);
        } else {
            holder.ll_layout_status.setBackgroundResource(R.drawable.round_blue);
        }
        textview_multipleevent_date.setText(temp.get(position).getDate());
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


            status_name = (RegularTextView) itemView.findViewById(R.id.status_name);
            ll_layout_status = (LinearLayout) itemView.findViewById(R.id.ll_layout_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MonthAvailableDetailActivity.class);

            intent.putExtra("id", temp.get(getPosition()).getId());
            intent.putExtra("provider_id", temp.get(getPosition()).getProvider_id());
            intent.putExtra("name", temp.get(getPosition()).getName());
            intent.putExtra("phone", temp.get(getPosition()).getPhone());
            intent.putExtra("location_name", temp.get(getPosition()).getLocation_name());
            intent.putExtra("latitude", temp.get(getPosition()).getLatitude());
            intent.putExtra("longitude", temp.get(getPosition()).getLongitude());
            intent.putExtra("time", temp.get(getPosition()).getTime());
            intent.putExtra("prep_time", temp.get(getPosition()).getPrep_time());
            intent.putExtra("date", temp.get(getPosition()).getDate());
            intent.putExtra("estimated_duration", temp.get(getPosition()).getEstimated_duration());
            intent.putExtra("personnel", temp.get(getPosition()).getPersonnel());
            intent.putExtra("service_provider", temp.get(getPosition()).getService_provider());
            intent.putExtra("number_of_provider", temp.get(getPosition()).getNumber_of_provider());
            intent.putExtra("jay_wallters", temp.get(getPosition()).getJay_walters());
            intent.putExtra("status", temp.get(getPosition()).getStatus());
            intent.putExtra("created_at", temp.get(getPosition()).getCreated_at());
            intent.putExtra("updated_at", temp.get(getPosition()).getUpdated_at());
            intent.putExtra("type", temp.get(getPosition()).getType());
            intent.putExtra("status_name", temp.get(getPosition()).getStatus_name());
            context.startActivity(intent);
        }
    }
}