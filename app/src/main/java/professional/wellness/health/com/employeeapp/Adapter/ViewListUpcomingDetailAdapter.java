package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.Model.ViewList;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 29-08-2017.
 */

public class ViewListUpcomingDetailAdapter extends RecyclerView.Adapter<ViewListUpcomingDetailAdapter.ItemViewHolder>{


    private Context context;
    private ArrayList<ViewList> othername_list;
    private String iduser= "";


    /*    items*/
    public ViewListUpcomingDetailAdapter(Context context, ArrayList<ViewList> othername_list, String iduser) {
        this.context = context;
        this.othername_list = othername_list;
        this.iduser = iduser;

    }


    @Override
    public ViewListUpcomingDetailAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_list_month, parent, false);

        return new ViewListUpcomingDetailAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewListUpcomingDetailAdapter.ItemViewHolder holder, final int position) {

        holder.txt_name.setText(othername_list.get(position).getName());
/*
if (othername_list.get(position).getEid().equals(iduser)){

    holder.txt_name.setVisibility(View.VISIBLE);
}else{
    holder.txt_name.setVisibility(View.GONE);
}
*/


        holder.txt_name.setText(othername_list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return othername_list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // private final TextView card_name;
        private RegularTextView txt_name;




        public ItemViewHolder(View itemView) {
            super(itemView);

            txt_name = (RegularTextView)itemView.findViewById(R.id.txt_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            //preptime_row.setTextColor(R.color.darkBlue);
        }
    }


}
