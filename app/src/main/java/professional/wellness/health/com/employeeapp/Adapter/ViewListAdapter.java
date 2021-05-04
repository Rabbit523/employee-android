package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.Model.HomeFeed;
import professional.wellness.health.com.employeeapp.Model.PrepTime;
import professional.wellness.health.com.employeeapp.Model.ViewListHomeFeed;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 17-08-2017.
 */

public class ViewListAdapter extends RecyclerView.Adapter<ViewListAdapter.ItemViewHolder>{
    private Context context;
    private ArrayList<ViewListHomeFeed> othername_list;
    private String iduser= "";


    /*    items*/
    public ViewListAdapter(Context context, ArrayList<ViewListHomeFeed> othername_list, String iduser) {
        this.context = context;
        this.othername_list = othername_list;
        this.iduser = iduser;

    }


    @Override
    public ViewListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_list, parent, false);

        return new ViewListAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewListAdapter.ItemViewHolder holder, final int position) {
        //holder.card_name.setText(mycards.get(position));
        //holder.preptime_row.setText(preptime_default_list.get(position).getName());


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
