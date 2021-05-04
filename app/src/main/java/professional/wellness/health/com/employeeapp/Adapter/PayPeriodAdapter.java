package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import professional.wellness.health.com.employeeapp.Model.GraphdataList;
import professional.wellness.health.com.employeeapp.Model.PeriodGraph;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.fragment.TimeSheet;

/**
 * Created by Fujitsu on 25-09-2017.
 */

public class PayPeriodAdapter  extends RecyclerView.Adapter<PayPeriodAdapter.ItemViewHolder>  {

    private Context context;
    private ArrayList<PeriodGraph> graphdataLists;
    private int pos=0;

    public PayPeriodAdapter(Context context,ArrayList<PeriodGraph> graphdataLists) {
        this.context = context;
        this.graphdataLists = graphdataLists;
        pos = graphdataLists.size()-1;
    }


    @Override
    public PayPeriodAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.days_list_row, parent, false);


        return new PayPeriodAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final PayPeriodAdapter.ItemViewHolder holder, final int position) {

        if(pos==position){
            holder.title.setTextColor(context.getResources().getColor(R.color.white));
            holder.title.setText(graphdataLists.get(position).getPeriod());
        }else {
            holder.title.setTextColor(context.getResources().getColor(R.color.grey));
            holder.title.setText(graphdataLists.get(position).getPeriod());
        }

    }

    @Override
    public int getItemCount() {
        return graphdataLists.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final TextView title;




        public ItemViewHolder(View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.titleDay);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            try {
                String milage = graphdataLists.get(getPosition()).getMileage();
                String drivetime = graphdataLists.get(getPosition()).getDrive_time();
                String hourstime = graphdataLists.get(getPosition()).getHours_time();
             /*   String dady = graphdataLists.get(getPosition()).getDay();
                String status = graphdataLists.get(getPosition()).getStatus();*/
                String income = graphdataLists.get(getPosition()).getIncome();
                TimeSheet.drive_time.setText(drivetime);
                TimeSheet.hours_tracked.setText(hourstime);
                TimeSheet.milage_total.setText(milage);
                TimeSheet.total_earned .setText("$ "+income);
                TimeSheet.txt_date.setText(graphdataLists.get(getPosition()).getPeriod() +", " +  TimeSheet.monthname);
              //  TimeSheet.txt_date.setText(items.get(getPosition()) + " " +day.get(getPosition()) + " " + TimeSheet.monthname) ;
                pos=getPosition();
                notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}


