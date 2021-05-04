package professional.wellness.health.com.employeeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.R;

/**
 * Created by Navit on 29-06-2017.
 */

public class HomeFeedFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayout empShowAllAnnouncement;
    private ScrollView allFeed;
    private ScrollView announcement;
    private LinearLayout img_drop;
    private LinearLayout image3;
    private LinearLayout dropdown;
    private LinearLayout viewlistdrop;
    private LinearLayout down_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_row_item, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        viewlistdrop = (LinearLayout)view.findViewById(R.id.viewlistdrop);
        dropdown = (LinearLayout)view.findViewById(R.id.dropdown);
        down_view = (LinearLayout)view.findViewById(R.id.down_view);
        img_drop = (LinearLayout)view.findViewById(R.id.img_drop);
        image3 = (LinearLayout) view.findViewById(R.id.image3);
        empShowAllAnnouncement = (LinearLayout) view.findViewById(R.id.empShowAllAnnouncement);
        allFeed = (ScrollView) view.findViewById(R.id.allFeed);
        announcement = (ScrollView) view.findViewById(R.id.announcement);
        empShowAllAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.empReturn.setVisibility(View.VISIBLE);
                MainActivity.empTab.setVisibility(View.GONE);
                allFeed.setVisibility(View.GONE);
                announcement.setVisibility(View.VISIBLE);

            }
        });

        img_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown.setVisibility(View.VISIBLE);
                viewlistdrop.setVisibility(View.GONE);
                down_view.setVisibility(View.GONE);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdown.setVisibility(View.GONE);
                viewlistdrop.setVisibility(View.VISIBLE);
                down_view.setVisibility(View.VISIBLE);
            }
        });



    }

}
