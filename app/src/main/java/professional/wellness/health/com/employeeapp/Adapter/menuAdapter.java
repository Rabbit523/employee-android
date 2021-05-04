package professional.wellness.health.com.employeeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.LoginActivity;
import professional.wellness.health.com.employeeapp.fragment.AvailableFragment;
import professional.wellness.health.com.employeeapp.fragment.AvailableFragmentChange;
import professional.wellness.health.com.employeeapp.fragment.AvailableNewFragment;
import professional.wellness.health.com.employeeapp.fragment.CalenderFragmentNew;
import professional.wellness.health.com.employeeapp.fragment.CertificateFragment;
import professional.wellness.health.com.employeeapp.fragment.HomeFeedFragment;
import professional.wellness.health.com.employeeapp.fragment.HomeFragment;
import professional.wellness.health.com.employeeapp.fragment.ProfileFragment;
import professional.wellness.health.com.employeeapp.fragment.TimeSheet;

/**
 * Created by Admin on 05-06-2017.
 */

public class menuAdapter extends RecyclerView.Adapter<menuAdapter.ItemViewHolder>  {
    Context context;
    private List<String> _listDataHeader;
    Integer[] imageIDs;
    boolean is_open = false;
    private RecyclerView leftSlide;

    /*    items*/
    public menuAdapter(Context context, List<String> _listDataHeader, Integer[] imageIDs, RecyclerView leftSlide) {
        this.context = context;

        this._listDataHeader = _listDataHeader;
        this.imageIDs = imageIDs;
        this.leftSlide = leftSlide;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slide_menu_adpt, parent, false);

        return new ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.txtName.setText(_listDataHeader.get(position));
        holder.img_reside.setImageResource(imageIDs[position]);

        if (MainActivity.savepos==position){
            holder.positionjero.setVisibility(View.VISIBLE);
        }
        else holder.positionjero.setVisibility(View.INVISIBLE);

        leftSlide.addOnItemTouchListener(new AppUtills.RecyclerItemClickListener(context, leftSlide, new AppUtills.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                MainActivity.savepos = position;

                if (position == 0) {
                    SessionManager.setbackValue(context,"home_feed");
                    MainActivity.available_icon.setVisibility(View.GONE);
                    MainActivity.empReturn.setVisibility(View.GONE);
                    MainActivity.empTab.setVisibility(View.VISIBLE);
                    MainActivity.ll_calander.setVisibility(View.INVISIBLE);
                    MainActivity.home_feeds_view.setVisibility(View.VISIBLE);
                    MainActivity.available_view.setVisibility(View.INVISIBLE);
                    MainActivity.my_clinics_view.setVisibility(View.INVISIBLE);
                    MainActivity.resideMenu.closeMenu();
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new HomeFragment());
                    fragmentTransaction.commit();


                } else if (position == 1) {
                    SessionManager.setbackValue(context,"available");
                    MainActivity.empReturn.setVisibility(View.GONE);
                    MainActivity.available_icon.setVisibility(View.VISIBLE);
                    MainActivity.empTab.setVisibility(View.VISIBLE);
                    MainActivity.ll_calander.setVisibility(View.INVISIBLE);
                    MainActivity.home_feeds_view.setVisibility(View.INVISIBLE);
                    MainActivity.available_view.setVisibility(View.VISIBLE);
                    MainActivity.my_clinics_view.setVisibility(View.INVISIBLE);
                    MainActivity.resideMenu.closeMenu();
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new AvailableFragmentChange());
                    fragmentTransaction.commit();



                } else if (position == 2) {
                    SessionManager.setbackValue(context,"myclinic");
                    MainActivity.available_icon.setVisibility(View.GONE);
                    MainActivity.empReturn.setVisibility(View.GONE);
                    MainActivity.empTab.setVisibility(View.VISIBLE);
                    MainActivity.ll_calander.setVisibility(View.VISIBLE);
                    MainActivity.home_feeds_view.setVisibility(View.INVISIBLE);
                    MainActivity.available_view.setVisibility(View.INVISIBLE);
                    MainActivity.my_clinics_view.setVisibility(View.VISIBLE);
                    MainActivity.resideMenu.closeMenu();
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new CalenderFragmentNew());
                    fragmentTransaction.commit();

                } else if (position == 3) {
                    SessionManager.setbackValue(context,"time_sheet");
                    MainActivity.ll_calander.setVisibility(View.INVISIBLE);
                    MainActivity.available_icon.setVisibility(View.GONE);
                    MainActivity.empReturn.setVisibility(View.GONE);
                    MainActivity.empTab.setVisibility(View.GONE);
                    MainActivity.resideMenu.closeMenu();
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new TimeSheet());
                    fragmentTransaction.commit();

                } else if (position == 4) {
                    SessionManager.setbackValue(context,"certificate");
                    MainActivity.ll_calander.setVisibility(View.INVISIBLE);
                    MainActivity.available_icon.setVisibility(View.GONE);
                    MainActivity.empReturn.setVisibility(View.GONE);
                    MainActivity.empTab.setVisibility(View.GONE);
                    MainActivity.resideMenu.closeMenu();
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new CertificateFragment());
                    fragmentTransaction.commit();


                } else if (position == 5) {
                    SessionManager.setbackValue(context,"profile");
                    MainActivity.ll_calander.setVisibility(View.INVISIBLE);
                    MainActivity.available_icon.setVisibility(View.GONE);
                    MainActivity.resideMenu.closeMenu();
                    MainActivity.empReturn.setVisibility(View.GONE);
                    MainActivity.empTab.setVisibility(View.GONE);
                    MainActivity.resideMenu.closeMenu();
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new ProfileFragment());
                    fragmentTransaction.commit();


                } else if (position == 6) {
                    holder.positionjero.setVisibility(View.GONE);

                    if (AppUtills.isNetworkAvailable(context)){
                        alertDialog("Are you sure, want to logout?");
                    }else{
                        alertDialogNoInternet("No Internet Connection");
                    }


                }
                notifyDataSetChanged();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

        }));
    }

    @Override
    public int getItemCount() {
        return _listDataHeader.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private final RegularTextView txtName;
        private final ImageView img_reside;
        private final LinearLayout positionjero;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtName = (RegularTextView) itemView.findViewById(R.id.txtName);
            img_reside = (ImageView) itemView.findViewById(R.id.img_reside);
            positionjero = (LinearLayout)itemView.findViewById(R.id.positionjero);

        }



    }


    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.logout, null);
        TextView txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        Button btnOk = (Button) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.resideMenu.closeMenu();
                SessionManager.clearSession(context);
                ((MainActivity)context).finish();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);


                dialog.dismiss();
            }
        });

    }


    public void alertDialogNoInternet(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.logout, null);
        TextView txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        Button btnOk = (Button) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


}
