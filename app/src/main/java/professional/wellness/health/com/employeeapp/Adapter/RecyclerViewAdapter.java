package professional.wellness.health.com.employeeapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.AllAnnouncementList;
import professional.wellness.health.com.employeeapp.Model.HomeFeed;
import professional.wellness.health.com.employeeapp.Model.ViewListHomeFeed;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.Utils.ClinicService;
import professional.wellness.health.com.employeeapp.Utils.MyLocationService;
import professional.wellness.health.com.employeeapp.Utils.WebService;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.CompanyAnnouncementDetailActivity;
import professional.wellness.health.com.employeeapp.activity.MonthClinicWeekDetailActivity;
import professional.wellness.health.com.employeeapp.activity.SplashActivity;
import professional.wellness.health.com.employeeapp.fragment.HomeFragment;

public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {

    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    boolean lineIsClose = true;
    private double latitude;
    private double longitude;
    private double latitudecurrent;
    private double longitudecurrent;
    private String iduser = "";
    private String clinic_id = "";
    Timer timer = new Timer();
    private String encrypted = "";
    private Handler mHandler;
    private Handler handler;
    private Runnable run;
    private SimpleViewHolder holder;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private final RegularButton empPrefferedAcceptButton;
        private final RegularButton empPrefferedDeclineButton;
        private final RegularTextView empPrefferedClinicTime;
        private final RegularTextView empPrefferedPrepTime;
        private final RegularTextView empPrefferedPersonnel;
        private final RegularTextView empPrefferedLocation;
        private final RegularTextView empPrefferedContactName;
        private final RegularTextView empPrefferedDate;
        private final RegularTextView empPrefferedEstDuration;
        private final RegularButton empClockOut;
        private final RegularButton empClockIn;
        private final RegularTextView empClockClinic;
        private final RegularTextView empClockPrep;
        private final RegularTextView empClockPersonnel;
        private final RegularTextView empClockLocation;
        private final RegularTextView empClockContact;
        private final RegularTextView empClockDate;
        private final RegularTextView empClockServiceProvided;
        //private final RegularTextView empClockWalter;
        private final RegularTextView empClockPhone;
        private final CardView layout_clockin;

        private final RegularTextView empupcomingClinic;
        private final RegularTextView empupcomingPrep;
        private final RegularTextView empupcomingPersonnel;
        private final RegularTextView empupcomingLocation;
        private final RegularTextView empupcomingContact;
        private final RegularTextView empupcomingDate;
        private final RegularTextView empupcomingServiceProvided;
        private final RegularTextView empupcomingWalter;
        private final RegularTextView empupcomingPhone;
        private final CardView layout_upcoming;


        private LinearLayout hide_upcoming;

        private LinearLayout viewlist_open;
        SwipeLayout swipeLayout;
        RegularTextView empDate;
        RegularTextView empTitle;
        RegularTextView empDecription;
        ImageView buttonDelete;
        ImageView empImage;
        private LinearLayout botto;
        private SwipeLayout swipe_preffred;
        private RegularTextView txt_jaywalters;

        private LinearLayout dropdown_upcoming;
        private LinearLayout view_listupcoming;
        private LinearLayout serviceProvider_upcoming;
        private  RecyclerView recycler_upcoming_viewlist;
        private RegularTextView txt_jaywalters_hide;
        private RegularTextView txt_midtech_hide;

        private LinearLayout viewlist_open_clockin;
        private LinearLayout view_listclockin;
        private LinearLayout serviceProvider_clockin;
        private RegularTextView txt_jaywalters_hide_clockin;
        private RegularTextView txt_midtech_hide_clockin;
        private RegularTextView txt_jaywalters_clockin;
        private RecyclerView recycler_clockin_viewlist;
        private LinearLayout dropdown_clockin;
        private LinearLayout hide_clockin;
        private LinearLayout anouncement_layout;


        private SwipeLayout swipe_upcoming;
        private LinearLayout botto_upcoming;
        private ImageView delete_upcoming;

        //new clinic
        private SwipeLayout swipe_preffred_available_new;
        private LinearLayout botto_preffred_avaiable_new;
        private ImageView delete_prefrred_avauilable_new;
        private RegularTextView clinic_name;
        private RegularTextView empPrefferedPrepTime_availble_new;
        private RegularTextView empPrefferedEstDuration_available_new;
        private RegularTextView empPrefferedPersonnel_available_new;
        private RegularTextView empPrefferedLocation_available_new;
        private RegularTextView empPrefferedDate_available_new;
        private RegularTextView empPrefferedContactName_availble_new;
        private RegularTextView empPrefferedClinicTime_available_new;
        private RegularButton empPrefferedAcceptButton_available_new;


        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            swipe_preffred = (SwipeLayout) itemView.findViewById(R.id.swipe_preffred);
            empDate = (RegularTextView) itemView.findViewById(R.id.empDate);
            empTitle = (RegularTextView) itemView.findViewById(R.id.empTitle);
            empDecription = (RegularTextView) itemView.findViewById(R.id.empDecription);

            buttonDelete = (ImageView) itemView.findViewById(R.id.delete);
            empImage = (ImageView) itemView.findViewById(R.id.empImage);
            botto = (LinearLayout) itemView.findViewById(R.id.botto);
            anouncement_layout = (LinearLayout)itemView.findViewById(R.id.anouncement_layout);

            empPrefferedAcceptButton = (RegularButton) itemView.findViewById(R.id.empPrefferedAcceptButton);
            empPrefferedDeclineButton = (RegularButton) itemView.findViewById(R.id.empPrefferedDeclineButton);

            empPrefferedClinicTime = (RegularTextView) itemView.findViewById(R.id.empPrefferedClinicTime);
            empPrefferedPrepTime = (RegularTextView) itemView.findViewById(R.id.empPrefferedPrepTime);
            empPrefferedPersonnel = (RegularTextView) itemView.findViewById(R.id.empPrefferedPersonnel);
            empPrefferedLocation = (RegularTextView) itemView.findViewById(R.id.empPrefferedLocation);
            empPrefferedContactName = (RegularTextView) itemView.findViewById(R.id.empPrefferedContactName);
            empPrefferedDate = (RegularTextView) itemView.findViewById(R.id.empPrefferedDate);
            empPrefferedEstDuration = (RegularTextView) itemView.findViewById(R.id.empPrefferedEstDuration);

            txt_jaywalters_hide = (RegularTextView) itemView.findViewById(R.id.txt_jaywalters_hide);
            txt_midtech_hide = (RegularTextView) itemView.findViewById(R.id.txt_midtech_hide);


            empClockOut = (RegularButton) itemView.findViewById(R.id.empClockOut);
            empClockIn = (RegularButton) itemView.findViewById(R.id.empClockIn);

            empClockClinic = (RegularTextView) itemView.findViewById(R.id.empClockClinic);
            empClockPrep = (RegularTextView) itemView.findViewById(R.id.empClockPrep);
            empClockPersonnel = (RegularTextView) itemView.findViewById(R.id.empClockPersonnel);
            empClockLocation = (RegularTextView) itemView.findViewById(R.id.empClockLocation);
            empClockContact = (RegularTextView) itemView.findViewById(R.id.empClockContact);
            empClockDate = (RegularTextView) itemView.findViewById(R.id.empClockDate);
            empClockServiceProvided = (RegularTextView) itemView.findViewById(R.id.empClockServiceProvided);
           // empClockWalter = (RegularTextView) itemView.findViewById(R.id.empClockWalter);
            empClockPhone = (RegularTextView) itemView.findViewById(R.id.empClockPhone);
            layout_clockin = (CardView) itemView.findViewById(R.id.layout_clockin);

            txt_jaywalters = (RegularTextView) itemView.findViewById(R.id.txt_jaywalters);

            empupcomingClinic = (RegularTextView) itemView.findViewById(R.id.empupcomingClinic);
            empupcomingPrep = (RegularTextView) itemView.findViewById(R.id.empupcomingPrep);
            empupcomingPersonnel = (RegularTextView) itemView.findViewById(R.id.empUpcomingPersonnel);
            empupcomingLocation = (RegularTextView) itemView.findViewById(R.id.empupcomingLocation);
            empupcomingContact = (RegularTextView) itemView.findViewById(R.id.empupcomingContact);
            empupcomingDate = (RegularTextView) itemView.findViewById(R.id.empupcomingDate);
            empupcomingServiceProvided = (RegularTextView) itemView.findViewById(R.id.empupcomingServiceProvided);
            empupcomingWalter = (RegularTextView) itemView.findViewById(R.id.empupcomingWalter);
            empupcomingPhone = (RegularTextView) itemView.findViewById(R.id.empupcomingPhone);
            layout_upcoming = (CardView) itemView.findViewById(R.id.layout_upcoming);
            view_listupcoming = (LinearLayout) itemView.findViewById(R.id.view_listupcoming);
            dropdown_upcoming = (LinearLayout) itemView.findViewById(R.id.dropdown_upcoming);
            viewlist_open = (LinearLayout) itemView.findViewById(R.id.viewlist_open);
            hide_upcoming = (LinearLayout) itemView.findViewById(R.id.hide_upcoming);
            serviceProvider_upcoming = (LinearLayout) itemView.findViewById(R.id.serviceProvider_upcoming);
            recycler_upcoming_viewlist = (RecyclerView) itemView.findViewById(R.id.recycler_upcoming_viewlist);

            viewlist_open_clockin = (LinearLayout)itemView.findViewById(R.id.viewlist_open_clockin);
            view_listclockin = (LinearLayout)itemView.findViewById(R.id.view_listclockin);
            serviceProvider_clockin = (LinearLayout)itemView.findViewById(R.id.serviceProvider_clockin);
            txt_jaywalters_hide_clockin = (RegularTextView) itemView.findViewById(R.id.txt_jaywalters_hide_clockin);
            txt_midtech_hide_clockin = (RegularTextView) itemView.findViewById(R.id.txt_midtech_hide_clockin);
            txt_jaywalters_clockin = (RegularTextView) itemView.findViewById(R.id.txt_jaywalters_clockin);
            recycler_clockin_viewlist = (RecyclerView)itemView.findViewById(R.id.recycler_clockin_viewlist);
            dropdown_clockin = (LinearLayout)itemView.findViewById(R.id.dropdown_clockin);
            hide_clockin = (LinearLayout)itemView.findViewById(R.id.hide_clockin);
            swipe_upcoming = (SwipeLayout)itemView.findViewById(R.id.swipe_upcoming);
            botto_upcoming = (LinearLayout)itemView.findViewById(R.id.botto_upcoming);
            delete_upcoming = (ImageView)itemView.findViewById(R.id.delete_upcoming);

            //new clinic
            swipe_preffred_available_new = (SwipeLayout)itemView.findViewById(R.id.swipe_preffred_available_new);
            botto_preffred_avaiable_new = (LinearLayout)itemView.findViewById(R.id.botto_preffred_avaiable_new);
            delete_prefrred_avauilable_new = (ImageView)itemView.findViewById(R.id.delete_prefrred_avauilable_new);
            clinic_name = (RegularTextView)itemView.findViewById(R.id.clinic_name);
            empPrefferedPrepTime_availble_new = (RegularTextView)itemView.findViewById(R.id.empPrefferedPrepTime_availble_new);
            empPrefferedEstDuration_available_new = (RegularTextView)itemView.findViewById(R.id.empPrefferedEstDuration_available_new);
            empPrefferedPersonnel_available_new = (RegularTextView)itemView.findViewById(R.id.empPrefferedPersonnel_available_new);
            empPrefferedLocation_available_new = (RegularTextView)itemView.findViewById(R.id.empPrefferedLocation_available_new);
            empPrefferedDate_available_new = (RegularTextView)itemView.findViewById(R.id.empPrefferedDate_available_new);
            empPrefferedContactName_availble_new = (RegularTextView)itemView.findViewById(R.id.empPrefferedContactName_availble_new);
            empPrefferedClinicTime_available_new = (RegularTextView)itemView.findViewById(R.id.empPrefferedClinicTime_available_new);
            empPrefferedAcceptButton_available_new = (RegularButton)itemView.findViewById(R.id.empPrefferedAcceptButton_available_new);

        }
    }

    private Context mContext;
    private ArrayList<HomeFeed> mDataset;
    private ArrayList<ViewListHomeFeed> othername_list;
    private ArrayList<ViewListHomeFeed> othername_listtemp;

    //protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public RecyclerViewAdapter(Context context, ArrayList<HomeFeed> objects, ArrayList<ViewListHomeFeed> othername_list) {
        this.mContext = context;
        this.mDataset = objects;
        this.othername_list = othername_list;
        othername_listtemp = new ArrayList<>();
        encryptionClass = EncryptionClass.getInstance(mContext);
        restInteraction = RESTInteraction.getInstance(mContext);

        GPSTracker gps = new GPSTracker(context);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
        //    gps.showSettingsAlert();
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_announcement_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.SimpleViewHolder viewHolder, final int position) {
        holder = viewHolder;
        viewHolder.txt_jaywalters.setText(mDataset.get(position).getPrimary_name());
        viewHolder.txt_jaywalters_hide.setText(mDataset.get(position).getPrimary_name());
        viewHolder.txt_midtech_hide.setText(mDataset.get(position).getMedtech_name());

        viewHolder.txt_jaywalters_hide_clockin.setText(mDataset.get(position).getPrimary_name());
        viewHolder.txt_jaywalters_clockin.setText(mDataset.get(position).getPrimary_name());
        viewHolder.txt_midtech_hide_clockin.setText(mDataset.get(position).getMedtech_name());




        if (mDataset.get(position).getType().equals("announcement")) {
            viewHolder.swipe_preffred.setVisibility(View.GONE);
            viewHolder.layout_clockin.setVisibility(View.GONE);
            viewHolder.swipe_upcoming.setVisibility(View.GONE);
            viewHolder.swipe_preffred_available_new.setVisibility(View.GONE);

            viewHolder.swipeLayout.setVisibility(View.VISIBLE);
            String item = mDataset.get(position).getTitle();
            String itemDescription = mDataset.get(position).getDescription();
            String itemDate = "Date: " + mDataset.get(position).getDate();


            String itemimage = mDataset.get(position).getImage_path();

            if (itemimage.equals("")) {
                viewHolder.empImage.setVisibility(View.GONE);
            } else {
                viewHolder.empImage.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(itemimage)
                        .into(viewHolder.empImage);
            }


            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.botto));
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewWithTag("f"));

            viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {

                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(mContext));
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(mContext));
                        object.put("announcement_id", mDataset.get(position).getId());
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new announcementDeactiveAsync().execute(encrypted);
                        // mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                        mDataset.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mDataset.size());
                        mItemManger.closeAllItems();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //   YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                }
            });
            viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    //Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.empDecription.setText(itemDescription);
            viewHolder.empTitle.setText(item);
            viewHolder.empDate.setText(itemDate);
            mItemManger.bindView(viewHolder.itemView, position);


        } else if (mDataset.get(position).getType().equals("clock_in")) {
            viewHolder.swipeLayout.setVisibility(View.GONE);
            viewHolder.swipe_preffred.setVisibility(View.GONE);
            viewHolder.layout_clockin.setVisibility(View.VISIBLE);
            viewHolder.swipe_preffred_available_new.setVisibility(View.GONE);
            viewHolder.swipe_upcoming.setVisibility(View.GONE);
            viewHolder.empClockClinic.setText("Clinic time: " + mDataset.get(position).getTime());
            viewHolder.empClockPrep.setText("Prep time " + mDataset.get(position).getPrep_time());
            viewHolder.empClockPersonnel.setText("Personnel : " + mDataset.get(position).getPersonnel());
            viewHolder.empClockLocation.setText(mDataset.get(position).getLocation_name());
            viewHolder.empClockContact.setText(mDataset.get(position).getName());
            viewHolder.empClockDate.setText("Date: " + mDataset.get(position).getDate());
            viewHolder.empClockServiceProvided.setText(mDataset.get(position).getService_provider());
            //viewHolder.empClockWalter.setText(mDataset.get(position).getDate());
            viewHolder.empClockPhone.setText(mDataset.get(position).getPhone());
            if (mDataset.get(position).getClocked().equals("1")) {
                viewHolder.empClockIn.setVisibility(View.VISIBLE);
                viewHolder.empClockOut.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.empClockIn.setVisibility(View.INVISIBLE);
                viewHolder.empClockOut.setVisibility(View.VISIBLE);
            }


            viewHolder.empClockIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(AppUtills.isNetworkAvailable(mContext)){
                        try {
                            MainActivity.notification();
                            clinic_id = mDataset.get(position).getId();
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(mContext));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(mContext));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", mDataset.get(position).getId());
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new updateClockinAsync().execute(encrypted);
                            viewHolder.empClockIn.setVisibility(View.INVISIBLE);
                            viewHolder.empClockOut.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else{

                        alertDialogoffline();
                    }
                }
            });

            viewHolder.empClockOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(AppUtills.isNetworkAvailable(mContext)) {
                        MainActivity.clearNotifications(mContext);
                        try {
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(mContext));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(mContext));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", mDataset.get(position).getId());
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new updateClockOutAsync().execute(encrypted);
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            mDataset.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mDataset.size());
                            mItemManger.closeAllItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else{

                        alertDialogoffline();
                    }


                }
            });


        } else if (mDataset.get(position).getType().equals("preferd")) {
            viewHolder.swipeLayout.setVisibility(View.GONE);
            viewHolder.layout_clockin.setVisibility(View.GONE);
            viewHolder.swipe_preffred.setVisibility(View.VISIBLE);
            viewHolder.swipe_preffred_available_new.setVisibility(View.GONE);
            viewHolder.swipe_upcoming.setVisibility(View.GONE);
            viewHolder.empPrefferedClinicTime.setText("Clinic time: " + mDataset.get(position).getTime());
            viewHolder.empPrefferedPrepTime.setText("Prep time " + mDataset.get(position).getPrep_time());
            viewHolder.empPrefferedPersonnel.setText("Personnel : " + mDataset.get(position).getPersonnel());
            viewHolder.empPrefferedLocation.setText(mDataset.get(position).getLocation_name());
            viewHolder.empPrefferedContactName.setText(mDataset.get(position).getName());
            viewHolder.empPrefferedDate.setText("Date: " + mDataset.get(position).getDate());
            viewHolder.empPrefferedEstDuration.setText(" Estimate Duration : " + mDataset.get(position).getEstimated_duration());


            viewHolder.swipe_preffred.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.swipe_preffred.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipe_preffred.findViewWithTag("decline"));
            viewHolder.swipe_preffred.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipe_preffred.findViewWithTag("accept"));

            viewHolder.swipe_preffred.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {

                    if (layout.getDragEdge() == SwipeLayout.DragEdge.Left) {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(mContext));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(mContext));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", mDataset.get(position).getId());
                            object.put("status", "0");
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new RejectClinicAsync().execute(encrypted);
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            mDataset.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mDataset.size());
                            mItemManger.closeAllItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (layout.getDragEdge() == SwipeLayout.DragEdge.Right) {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(mContext));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(mContext));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", mDataset.get(position).getId());
                            object.put("status", "1");
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new acceptClinicAsync().execute(encrypted);
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            mDataset.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mDataset.size());
                            mItemManger.closeAllItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    /*try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", "waaedawedarffgsgsrgsrgg");
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(mContext));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("clinic_id", mDataset.get(position).getId());
                        object.put("status", "0");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new RejectClinicAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", "waaedawedarffgsgsrgsrgg");
                        object.put("platform_type", "android app");
                        object.put("user_id", SessionManager.getuserId(mContext));
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("clinic_id", mDataset.get(position).getId());
                        object.put("status", "1");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new acceptClinicAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/


                    //   YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                }
            });
        } else if (mDataset.get(position).getType().equals("upcoming")) {
            viewHolder.swipeLayout.setVisibility(View.GONE);
            viewHolder.swipe_preffred.setVisibility(View.GONE);
            viewHolder.layout_clockin.setVisibility(View.GONE);
            viewHolder.swipe_upcoming.setVisibility(View.VISIBLE);
            viewHolder.swipe_preffred_available_new.setVisibility(View.GONE);
            viewHolder.empupcomingClinic.setText("Clinic time: " + mDataset.get(position).getTime());
            viewHolder.empupcomingPrep.setText("Prep time " + mDataset.get(position).getPrep_time());
            viewHolder.empupcomingPersonnel.setText("Personnel : " + mDataset.get(position).getPersonnel());
            viewHolder.empupcomingLocation.setText(mDataset.get(position).getLocation_name());
            viewHolder.empupcomingContact.setText(mDataset.get(position).getName());
            viewHolder.empupcomingDate.setText("Date: " + mDataset.get(position).getDate());
            viewHolder.empupcomingServiceProvided.setText(mDataset.get(position).getService_provider());
            //    viewHolder.empupcomingWalter.setText(mDataset.get(position).getJay_walters());
            viewHolder.empupcomingPhone.setText(mDataset.get(position).getPhone());
            viewHolder.empPrefferedEstDuration.setText(" Estimate Duration : " + mDataset.get(position).getEstimated_duration());

            viewHolder.swipe_upcoming.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.swipe_upcoming.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipe_upcoming.findViewById(R.id.botto_upcoming));
            viewHolder.swipe_upcoming.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipe_upcoming.findViewWithTag("f"));



            viewHolder.swipe_upcoming.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {

                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", SessionManager.getDeviceId(mContext));
                        object.put("platform_type", "android app");
                        object.put("latitude", latitude);
                        object.put("longitude", longitude);
                        object.put("user_id", SessionManager.getuserId(mContext));
                        object.put("clinic_id", mDataset.get(position).getId());
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new upcomingDeactiveAsync().execute(encrypted);
                        // mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                        mDataset.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mDataset.size());
                        mItemManger.closeAllItems();

                      /*  notifyItemRangeChanged(position, mDataset.size());
                        mItemManger.closeAllItems();




                        mDataset.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mDataset.size());
                        mItemManger.closeAllItems();*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //   YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                 }
            });
            viewHolder.swipe_upcoming.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    //Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
                }
            });


            viewHolder.viewlist_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewHolder.view_listupcoming.setVisibility(View.GONE);
                    viewHolder.dropdown_upcoming.setVisibility(View.VISIBLE);
                    viewHolder.serviceProvider_upcoming.setVisibility(View.GONE);
                    iduser = mDataset.get(position).getId();
                    othername_listtemp.clear();
                    for (int i = 0; i < othername_list.size(); i++) {
                        if (othername_list.get(i).getId().equals(iduser)) {
                            othername_listtemp.add(new ViewListHomeFeed(othername_list.get(i).getId(), othername_list.get(i).getName()));
                        }
                    }
                    intializeRecyclerViewList(viewHolder);
                }
            });

            viewHolder.hide_upcoming.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.view_listupcoming.setVisibility(View.VISIBLE);
                    viewHolder.dropdown_upcoming.setVisibility(View.GONE);
                    viewHolder.serviceProvider_upcoming.setVisibility(View.VISIBLE);
                }
            });
        }else if(mDataset.get(position).getType().equals("available_clinics")){
            viewHolder.swipeLayout.setVisibility(View.GONE);
            viewHolder.swipe_preffred.setVisibility(View.GONE);
            viewHolder.layout_clockin.setVisibility(View.GONE);
            viewHolder.swipe_upcoming.setVisibility(View.GONE);
            viewHolder.swipe_preffred_available_new.setVisibility(View.VISIBLE);

            viewHolder.empPrefferedClinicTime_available_new.setText("Clinic time: " + mDataset.get(position).getTime());
            viewHolder.empPrefferedPrepTime_availble_new.setText("Prep time " + mDataset.get(position).getPrep_time());
            viewHolder.empPrefferedPersonnel_available_new.setText("Personnel : " + mDataset.get(position).getPersonnel());
            viewHolder.empPrefferedLocation_available_new.setText(mDataset.get(position).getLocation_name());
            viewHolder.empPrefferedContactName_availble_new.setText(mDataset.get(position).getName());
            viewHolder.empPrefferedDate_available_new.setText("Date: " + mDataset.get(position).getDate());
            viewHolder.empPrefferedEstDuration_available_new.setText(" Estimate Duration : " + mDataset.get(position).getEstimated_duration());


            viewHolder.swipe_preffred_available_new.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.swipe_preffred_available_new.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipe_preffred_available_new.findViewWithTag("decline"));
            viewHolder.swipe_preffred_available_new.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipe_preffred_available_new.findViewWithTag("accept"));




            viewHolder.swipe_preffred_available_new.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {

                    if (layout.getDragEdge() == SwipeLayout.DragEdge.Left) {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(mContext));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(mContext));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", mDataset.get(position).getId());
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new SwipeOtherClinicAsync().execute(encrypted);
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            mDataset.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mDataset.size());
                            mItemManger.closeAllItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else if (layout.getDragEdge() == SwipeLayout.DragEdge.Right) {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("device_id", SessionManager.getDeviceId(mContext));
                            object.put("platform_type", "android app");
                            object.put("user_id", SessionManager.getuserId(mContext));
                            object.put("latitude", latitude);
                            object.put("longitude", longitude);
                            object.put("clinic_id", mDataset.get(position).getId());
                            object.put("status", "1");
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new acceptClinicAsync().execute(encrypted);
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            mDataset.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mDataset.size());
                            mItemManger.closeAllItems();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            });




        }

        viewHolder.viewlist_open_clockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.serviceProvider_clockin.setVisibility(View.GONE);
                viewHolder.dropdown_clockin.setVisibility(View.VISIBLE);
                viewHolder.view_listclockin.setVisibility(View.GONE);

                iduser = mDataset.get(position).getId();
                othername_listtemp.clear();
                for (int i = 0; i < othername_list.size(); i++) {
                    if (othername_list.get(i).getId().equals(iduser)) {
                        othername_listtemp.add(new ViewListHomeFeed(othername_list.get(i).getId(), othername_list.get(i).getName()));
                    }
                }
                intializeRecyclerViewListClockin(viewHolder);

            }
        });

        viewHolder.hide_clockin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.serviceProvider_clockin.setVisibility(View.VISIBLE);
                viewHolder.dropdown_clockin.setVisibility(View.GONE);
                viewHolder.view_listclockin.setVisibility(View.VISIBLE);
            }
        });






    /*    if (mDataset.get(position).getStatus().equals("1")) {
            viewHolder.swipeLayout.setVisibility(View.VISIBLE);
            String item = mDataset.get(position).getTitle();
            String itemDescription = mDataset.get(position).getDescription();
            String itemDate = mDataset.get(position).getCreated_at();
            String itemimage = mDataset.get(position).getImage_path();

            Picasso.with(mContext)
                    .load(itemimage)
                    .into(viewHolder.empImage);

            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.botto));
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewWithTag("f"));

            viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {
                    //   YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                }
            });
            viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    //Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_id", "waaedawedarffgsgsrgsrgg");
                        object.put("platform_type", "android app");
                        object.put("announcement_id", mDataset.get(position).getId());
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new announcementDeactiveAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDataset.size());
                    mItemManger.closeAllItems();
                }
            });
            viewHolder.empDecription.setText(itemDescription);
            viewHolder.empTitle.setText(item);
            viewHolder.empDate.setText(itemDate);
            mItemManger.bindView(viewHolder.itemView, position);
        }
        else {

            viewHolder.swipeLayout.setVisibility(View.GONE);
        }*/


        viewHolder.anouncement_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDataset.get(position).getType().equals("announcement")) {
                    if (mDataset.get(position).getStatus().equals("1")) {

                            Intent intent = new Intent(mContext, CompanyAnnouncementDetailActivity.class);
                            intent.putExtra("image_path", mDataset.get(position).getImage_path());
                            intent.putExtra("date", mDataset.get(position).getDate());
                            intent.putExtra("description", mDataset.get(position).getDescription());
                            intent.putExtra("title", mDataset.get(position).getTitle());
                            mContext.startActivity(intent);



                    }
                }
            }
        });


        viewHolder.empPrefferedAcceptButton_available_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(mContext));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(mContext));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("clinic_id", mDataset.get(position).getId());
                    object.put("status", "1");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new acceptClinicAsync().execute(encrypted);
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDataset.size());
                    mItemManger.closeAllItems();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        viewHolder.empPrefferedAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(mContext));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(mContext));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("clinic_id", mDataset.get(position).getId());
                    object.put("status", "1");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new acceptClinicAsync().execute(encrypted);
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDataset.size());
                    mItemManger.closeAllItems();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });




        viewHolder.empPrefferedDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(mContext));
                    object.put("platform_type", "android app");
                    object.put("user_id", SessionManager.getuserId(mContext));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("clinic_id", mDataset.get(position).getId());
                    object.put("status", "0");
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new RejectClinicAsync().execute(encrypted);
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDataset.size());
                    mItemManger.closeAllItems();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

       // HomeFragment.progressDialog.dismiss();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    private class announcementDeactiveAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendDeactiveRequest(APIUrl.ANNOUCEREJECT, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {


                    } else if (object.has("status") && object.getString("status").equals("error")) {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
            }

        }
    }



    private class upcomingDeactiveAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendDeactiveUpcomingRequest(APIUrl.SWIPEUPCOMINGCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("status") && object.getString("status").equals("success")) {


                    } else if (object.has("status") && object.getString("status").equals("error")) {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
            }

        }
    }


    private class RejectClinicAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendAcceptRejectClinicRequest(APIUrl.AVAILABLEACCEPTREJECTCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                       /* notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, available_list.size());
                        notifyDataSetChanged();*/

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }

    private class SwipeOtherClinicAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendSwipeOthertClinicRequest(APIUrl.SWIPEOTHERCLINICDISMISS, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                       /* notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, available_list.size());
                        notifyDataSetChanged();*/

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }

    public void alertDialogoffline() {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.offline_dialog, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText("Please go to offline mode click on ok");
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         try {

             FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.view_container, new HomeFragment());
             fragmentTransaction.commit();

             dialog.dismiss();
         }catch (Exception e){

         }
            }
        });
    }




    private class acceptClinicAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendAcceptRejectClinicRequest(APIUrl.AVAILABLEACCEPTREJECTCLINIC, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                       /* notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, available_list.size());
                        notifyDataSetChanged();*/
                        try {
                            mContext.startService(new Intent(mContext,ClinicService.class));
                        }catch (Exception e){

                        }

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }

    private class updateClockinAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateClockinRequest(APIUrl.UPDATECLOCKIN, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        SessionManager.setClinicid(mContext, clinic_id);
                        SessionManager.setClockinTime(mContext, jsonObject.getString("data"));
                        SessionManager.setStopService(mContext, "aaa");

                        if(!SessionManager.getStopService(mContext).equals("stop")){
                            SessionManager.setClinicid(mContext,SessionManager.getClinicid(mContext));
                            mContext.startService(new Intent(mContext, MyLocationService.class));


                        }
                        try {
                            mContext.startService(new Intent(mContext,ClinicService.class));
                        }catch (Exception e){

                        }
                        alertDialogSucess(jsonObject.getString("message"));

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }
    private class locationUpdateAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdatelocationAsync(APIUrl.UPDATELOCATION , encrypted);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                    //    alertDialogSucess(jsonObject.getString("message"));

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                     //   alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }


    private class updateClockOutAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(mContext);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateClockinRequest(APIUrl.UPDATECLOCKOUT, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        SessionManager.setStopService(mContext, "stop");
                        mContext.stopService(new Intent(mContext, MyLocationService.class));

                        try {
                            mContext.startService(new Intent(mContext,ClinicService.class));
                        }catch (Exception e){

                        }
                        SessionManager.clearClinicid(mContext);
                        alertDialogSucess(jsonObject.getString("message"));

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }


    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public void alertDialogSucess(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.alert_dialog_sucess, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void intializeRecyclerViewList(SimpleViewHolder viewHolder) {
        viewHolder.recycler_upcoming_viewlist.setLayoutManager(new LinearLayoutManager(mContext));
        viewHolder.recycler_upcoming_viewlist.setItemAnimator(new DefaultItemAnimator());
        viewHolder.recycler_upcoming_viewlist.setAdapter(new ViewListAdapter(mContext, othername_listtemp, iduser));

    }


    private void intializeRecyclerViewListClockin(SimpleViewHolder viewHolder) {
        viewHolder.recycler_clockin_viewlist.setLayoutManager(new LinearLayoutManager(mContext));
        viewHolder.recycler_clockin_viewlist.setItemAnimator(new DefaultItemAnimator());
        viewHolder.recycler_clockin_viewlist.setAdapter(new ViewListAdapter(mContext, othername_listtemp, iduser));

    }
    public void removeHandler()
    {
        Log.i("Stop Handler ","Yes");
        handler.removeCallbacks(run);
    }

}
