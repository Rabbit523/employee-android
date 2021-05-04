package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.Model.AllAnnouncementList;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.CompanyAnnouncementDetailActivity;


/**
 * Created by Fujitsu on 29-12-2016.
 */

public class AllAnnoucementAdapter extends RecyclerView.Adapter<AllAnnoucementAdapter.ItemViewHolder>  {

    private Context context;
    private ArrayList<AllAnnouncementList> items;

    public AllAnnoucementAdapter(Context context, ArrayList<AllAnnouncementList> savedPhotosLists) {
        this.context = context;

        this.items = savedPhotosLists;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_announcement_row_item, parent, false);


        return new ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.empDate.setText(items.get(position).getCreated_at());
        holder.empDecription.setText(items.get(position).getDescription());
        holder.empTitle.setText(items.get(position).getTitle());


        if(items.get(position).getImage_path().equals("")){
            holder.empImage.setVisibility(View.GONE);
        }else{
            holder.empImage.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(items.get(position).getImage_path())
                    .into(holder.empImage);
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView empImage;
        private final RegularTextView empDecription;
        private final RegularTextView empDate;
        private final RegularTextView empTitle;



        public ItemViewHolder(View itemView) {
            super(itemView);

            empImage = (ImageView)itemView.findViewById(R.id.empImage);
            empDecription = (RegularTextView) itemView.findViewById(R.id.empDecription);
            empDate = (RegularTextView) itemView.findViewById(R.id.empDate);
            empTitle = (RegularTextView) itemView.findViewById(R.id.empTitle);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, CompanyAnnouncementDetailActivity.class);
            intent.putExtra("image_path", items.get(getPosition()).getImage_path());
            intent.putExtra("date", items.get(getPosition()).getCreated_at());
            intent.putExtra("description",  items.get(getPosition()).getDescription());
            intent.putExtra("title",  items.get(getPosition()).getTitle());
            context.startActivity(intent);
        }
    }
}

