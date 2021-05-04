package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.Model.Certificate;
import professional.wellness.health.com.employeeapp.Model.PrepTime;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 04-08-2017.
 */

public class PrepTimeAdapter extends RecyclerView.Adapter<PrepTimeAdapter.ItemViewHolder>{


    private Context context;
    private ArrayList<PrepTime> preptime_default_list;
    private RegularTextView selected_preptime;
    /*    items*/
    public PrepTimeAdapter(Context context, ArrayList<PrepTime> preptime_default_list, RegularTextView selected_preptime) {
        this.context = context;
        this.selected_preptime =selected_preptime;
        this.preptime_default_list = preptime_default_list;
    }


    @Override
    public PrepTimeAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_preptime_row, parent, false);

        return new PrepTimeAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final PrepTimeAdapter.ItemViewHolder holder, final int position) {
        //holder.card_name.setText(mycards.get(position));
       holder.preptime_row.setText(preptime_default_list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return preptime_default_list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // private final TextView card_name;
        private RegularTextView preptime_row;




        public ItemViewHolder(View itemView) {
            super(itemView);

            preptime_row = (RegularTextView)itemView.findViewById(R.id.preptime_row);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            selected_preptime.setText(preptime_default_list.get(getPosition()).getName());
            //preptime_row.setTextColor(R.color.darkBlue);
        }
    }


}
