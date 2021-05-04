package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.Model.AvailableUpcoming;
import professional.wellness.health.com.employeeapp.Model.MyClinicUpcoming;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 09-08-2017.
 */

public class AvailableUpcomingAdapter extends RecyclerView.Adapter<AvailableUpcomingAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<AvailableUpcoming> available_upcoming_list;

    /*    items*/
    public AvailableUpcomingAdapter(Context context, ArrayList<AvailableUpcoming> available_upcoming_list) {
        this.context = context;

        this.available_upcoming_list = available_upcoming_list;

    }


    @Override
    public AvailableUpcomingAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_myclinic_upcoming, parent, false);

        return new AvailableUpcomingAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final AvailableUpcomingAdapter.ItemViewHolder holder, final int position) {
        //holder.card_name.setText(mycards.get(position));

        holder.clinic_name.setText(available_upcoming_list.get(position).getStatus_name());
        holder.empupcomingPrep.setText("Prep time : " + available_upcoming_list.get(position).getPrep_time());
        holder.empUpcomingEstDuration.setText("Estimate duration : " +available_upcoming_list.get(position).getEstimated_duration());
        holder.empUpcomingPersonnel.setText("Personnel : "+ available_upcoming_list.get(position).getPersonnel());
        holder.empupcomingLocation.setText(available_upcoming_list.get(position).getLocation_name());
        holder.empupcomingDate.setText("Date : "+available_upcoming_list.get(position).getDate());
        holder.empupcomingClinic.setText("Clinic Time : "+available_upcoming_list.get(position).getTime());
        holder.empupcomingServiceProvided.setText(available_upcoming_list.get(position).getService_provider());
    }

    @Override
    public int getItemCount() {
        return available_upcoming_list.size();
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




        public ItemViewHolder(View itemView) {
            super(itemView);
/*            clockin_reminder = (RegularTextView)itemView.findViewById(R.id.clockin_reminder);*/
            clinic_name = (RegularTextView)itemView.findViewById(R.id.clinic_name);
            empupcomingPrep = (RegularTextView)itemView.findViewById(R.id.empupcomingPrep);
            empUpcomingEstDuration = (RegularTextView)itemView.findViewById(R.id.empUpcomingEstDuration);
            empUpcomingPersonnel = (RegularTextView)itemView.findViewById(R.id.empUpcomingPersonnel);
            empupcomingLocation = (RegularTextView)itemView.findViewById(R.id.empupcomingLocation);
            empupcomingDate = (RegularTextView)itemView.findViewById(R.id.empupcomingDate);
            empupcomingClinic = (RegularTextView)itemView.findViewById(R.id.empupcomingClinic);
            empupcomingServiceProvided = (RegularTextView)itemView.findViewById(R.id.empupcomingServiceProvided);

        }



    }


}
