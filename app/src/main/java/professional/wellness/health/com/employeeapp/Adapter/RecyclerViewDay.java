package professional.wellness.health.com.employeeapp.Adapter;

/**
 * Created by Akshat Ajmera on 16-Aug-17.
 */
    import android.content.Context;

    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;


    import java.util.ArrayList;
    import java.util.List;

    import professional.wellness.health.com.employeeapp.Model.GraphdataList;
    import professional.wellness.health.com.employeeapp.R;
    import professional.wellness.health.com.employeeapp.View.RegularTextView;
    import professional.wellness.health.com.employeeapp.fragment.TimeSheet;

public class RecyclerViewDay  extends RecyclerView.Adapter<RecyclerViewDay.ItemViewHolder>  {

    private Context context;
    public static List<String> day;
    public static List<String> items;
    private ArrayList<GraphdataList> graphdataLists;
    private int pos=0;

    public RecyclerViewDay(Context context, List<String> savedPhotosLists, ArrayList<GraphdataList> graphdataLists, List<String> getData) {
        this.context = context;
        this.graphdataLists = graphdataLists;
        this.items = savedPhotosLists;
        this.day = getData;
        pos = graphdataLists.size()-1;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.days_list_row, parent, false);


        return new ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        if(pos==position){
            holder.title.setTextColor(context.getResources().getColor(R.color.white));

        }else {
            holder.title.setTextColor(context.getResources().getColor(R.color.grey));

        }
        holder.title.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
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
                String dady = graphdataLists.get(getPosition()).getDay();
                String status = graphdataLists.get(getPosition()).getStatus();
                String income = graphdataLists.get(getPosition()).getIncome();
                if (milage.equals("-1")){
                    milage = "0";
                }
                if (drivetime.equals("-1")){
                    drivetime = "0";
                }
                if (hourstime.equals("-1")){
                    hourstime = "0";
                }
                if (income.equals("-1")){
                    income = "0";
                }

                TimeSheet.drive_time.setText(drivetime);
                TimeSheet.hours_tracked.setText(hourstime);
                TimeSheet.milage_total.setText(milage);
                TimeSheet.total_earned .setText("$ "+income);
                TimeSheet.txt_date.setText(day.get(getPosition()) + " " + TimeSheet.monthname) ;
                pos=getPosition();
                notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}

