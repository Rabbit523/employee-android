package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import professional.wellness.health.com.employeeapp.Model.AllAnnouncementList;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 28-07-2017.
 */

public class DayClinicCloseAdapter extends RecyclerView.Adapter<DayClinicCloseAdapter.ItemViewHolder>  {

    private Context context;
    private String[] days;

    public DayClinicCloseAdapter(Context context, String[] days) {
        this.context = context;
        this.days = days;
    }


    @Override
    public DayClinicCloseAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dayview_close_row, parent, false);


        return new DayClinicCloseAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final DayClinicCloseAdapter.ItemViewHolder holder, final int position) {

        holder.txt_close_day.setText(Arrays.toString(days));
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final RegularTextView txt_close_day;

        public ItemViewHolder(View itemView) {
            super(itemView);

            txt_close_day = (RegularTextView)itemView.findViewById(R.id.txt_close_day);


        }


    }


}
