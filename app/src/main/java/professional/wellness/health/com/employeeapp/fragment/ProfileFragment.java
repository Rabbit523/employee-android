package professional.wellness.health.com.employeeapp.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.kyleduo.switchbutton.SwitchButton;
import com.mukesh.permissions.AppPermissions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import professional.wellness.health.com.employeeapp.Adapter.AvailableAdapter;
import professional.wellness.health.com.employeeapp.Adapter.ClockOutAdapter;
import professional.wellness.health.com.employeeapp.Adapter.ClockinAdapter;
import professional.wellness.health.com.employeeapp.Adapter.PrepTimeAdapter;
import professional.wellness.health.com.employeeapp.Adapter.ViewPagerAdapter;
import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.ClockOutReminder;
import professional.wellness.health.com.employeeapp.Model.ClockinReminder;
import professional.wellness.health.com.employeeapp.Model.DesignationList;
import professional.wellness.health.com.employeeapp.Model.LeaveLocationPager;
import professional.wellness.health.com.employeeapp.Model.NotificationGroupby;
import professional.wellness.health.com.employeeapp.Model.PrepTime;
import professional.wellness.health.com.employeeapp.Model.SystemCalander;
import professional.wellness.health.com.employeeapp.Model.TimeFormat;
import professional.wellness.health.com.employeeapp.Model.TimeZone;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularEditText;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.LoginActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.R.attr.bitmap;
import static professional.wellness.health.com.employeeapp.Utils.AppUtills.ALL_PERMISSIONS;
import static professional.wellness.health.com.employeeapp.Utils.AppUtills.context;

/**
 * Created by Fujitsu on 04-07-2017.
 */

public class ProfileFragment extends Fragment {
    private LinearLayout show_before_open_layout;
    private LinearLayout show_before_preptime;
    private ImageView img_down;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private CircleImageView profile_image;
    private RegularEditText user_name;
    private RegularEditText user_email;
    private RegularEditText user_phone;
    private RegularEditText user_social_securitynumber;
    private RegularEditText user_description;
    private RegularEditText user_address;
    private RegularEditText user_desgination;
    private LinearLayout change_password_main;
    private ImageView change_password_icon;
    private LinearLayout change_password_layout;
    private LinearLayout change_password_view;
    private LinearLayout change_email_main;
    private ImageView change_email_icon;
    private LinearLayout change_email_layout;
    private LinearLayout change_email_view;
    private LinearLayout change_phone_number_main;
    private ImageView change_phone_number_icon;
    private LinearLayout change_phone_number_layout;
    private LinearLayout change_phone_number_view;
    private LinearLayout social_security_number_main;
    private ImageView social_security_number_icon;
    private LinearLayout social_security_number_layout;
    private LinearLayout social_security_number_view;
    private LinearLayout notification_setting_main;
    private ImageView notification_setting_icon;
    private LinearLayout notification_setting_layout;
    private LinearLayout notification_setting_view;
    private LinearLayout setting_main;
    private ImageView setting_icon;
    private LinearLayout setting_layout;
    private RegularEditText edt_oldpassword;
    private RegularEditText edt_newpassword;
    private RegularEditText edt_reenter_password;
    private RegularButton submit_changepassword;
    private LinearLayout edit;
    private LinearLayout upload_image;
    private static final int ALL_REQUEST_CODE = 0;
    private static final int ALL_REQUEST_CODE1 = 1;
    private AppPermissions mRuntimePermission;
    private File compressedImage;
    private String mImageCaptureUri = "";
    private RegularButton social_security_submit;
    private RegularButton submit_change_email;
    private RegularButton submit_change_phonenumber;
    private RegularEditText user_new_email;
    private SwitchButton email_switch;
    private SwitchButton pushnotification_switch;

    private RecyclerView show_preptime_recycler;
    private RegularTextView selected_preptime;
    private RegularEditText enter_preptime;
    private RegularButton submit_preptime;
    private ArrayList<PrepTime> preptime_default_list;
    private String preptime;
    private SwitchButton setting_switch;
    private String default_status = "0";

    private RecyclerView clockin_recycler;
    private ArrayList<ClockinReminder> clockin_reminder_list;
    private ArrayList<ClockOutReminder> clockout_reminder_list;
    private RecyclerView recycler_clockout;
    private ViewPager leave_location_pager;
    PagerAdapter adapter;
    private ArrayList<LeaveLocationPager> leave_location_pager_list;
    private ImageView pager_prev;
    private ImageView pager_next;
    private LinearLayout update_disable_email_confirmation;
    private ImageView img_update_disable_confimation;
    //check status
    private String emailNotificationStatus;
    private String pushNotificationStatus;
    private String clock_in_value = "";
    private String clock_out_value = "";
    private String leave_location_value ="" ;

    private String disable_status= "";
    private String prep_time ="";

    private LinearLayout additional_clockin_cloockout;
    private ImageView img_clocin_clockout;
    private LinearLayout additional_clockin_cloockout_close;
    private ImageView submit_leave_location;
    private EditText timezone;

    private ArrayList<TimeZone> timezone_list;
    private Boolean emailvalue = false;
    private Boolean pushNotificationvalue = false;
    private LinearLayout layout_default_password;
    private LinearLayout layout_sucess_password;
    private RegularEditText edt_sucess_social_number;
    private RegularButton btn_sucess_social_number;
    private  ArrayList<SystemCalander> system_calander_list;
    private LinearLayout btn_system_calnder;
    private RegularTextView txt_system_calander;
    private String system_calender = "";
    private String system_calender_status= "";
    private Boolean settingsystemcalanderstatus = false;
    private String timezone_selected = "";
    private LinearLayout clinic_grouped_by;
    private RegularTextView txt_clinic_grouped_by;
    private ArrayList<NotificationGroupby> notification_groupby_list;
    private String notification_groupBy = "";
    private ArrayList<DesignationList> designation_list;
    private EditText time_format;
    private ArrayList<TimeFormat> array_time_format;
    private double latitude;
    private double longitude;
    private String time_format_value = "";
    private LinearLayout ll_main_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRuntimePermission = new AppPermissions();
        clockin_reminder_list = new ArrayList<>();
        clockout_reminder_list = new ArrayList<>();
        leave_location_pager_list = new ArrayList<>();
        system_calander_list = new ArrayList<>();
        notification_groupby_list = new ArrayList<>();
        array_time_format = new ArrayList<>();
        GPSTracker gps = new GPSTracker(getActivity());

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }

        designation_list = new ArrayList<>();
        timezone_list = new ArrayList<>();
        timezone = (EditText)view.findViewById(R.id.timezone);
        ll_main_layout = (LinearLayout)view.findViewById(R.id.ll_main_layout);
        clockin_recycler = (RecyclerView) view.findViewById(R.id.clockin_recycler);
        recycler_clockout = (RecyclerView) view.findViewById(R.id.recycler_clockout);
        leave_location_pager = (ViewPager)view.findViewById(R.id.leave_location_pager);
        update_disable_email_confirmation = (LinearLayout)view.findViewById(R.id.update_disable_email_confirmation);
        img_update_disable_confimation = (ImageView)view.findViewById(R.id.img_update_disable_confimation);
        restInteraction = RESTInteraction.getInstance(getActivity());
        encryptionClass = EncryptionClass.getInstance(getActivity());
        pager_prev = (ImageView)view.findViewById(R.id.pager_prev);
        pager_next = (ImageView)view.findViewById(R.id.pager_next);
        show_before_preptime = (LinearLayout) view.findViewById(R.id.show_before_preptime);
        show_before_open_layout = (LinearLayout) view.findViewById(R.id.show_before_open_layout);
        img_down = (ImageView) view.findViewById(R.id.img_down);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        user_name = (RegularEditText) view.findViewById(R.id.user_name);
        user_email = (RegularEditText) view.findViewById(R.id.user_email);
        user_phone = (RegularEditText) view.findViewById(R.id.user_phone);
        user_social_securitynumber = (RegularEditText) view.findViewById(R.id.user_social_securitynumber);
        user_description = (RegularEditText) view.findViewById(R.id.user_description);
        user_address = (RegularEditText) view.findViewById(R.id.user_address);
        user_desgination = (RegularEditText) view.findViewById(R.id.user_desgination);
        change_password_main = (LinearLayout) view.findViewById(R.id.change_password_main);
        change_password_icon = (ImageView) view.findViewById(R.id.change_password_icon);
        change_password_layout = (LinearLayout) view.findViewById(R.id.change_password_layout);
        change_password_view = (LinearLayout) view.findViewById(R.id.change_password_view);
        change_email_main = (LinearLayout) view.findViewById(R.id.change_email_main);
        change_email_layout = (LinearLayout) view.findViewById(R.id.change_email_layout);
        change_email_view = (LinearLayout) view.findViewById(R.id.change_email_view);
        change_email_icon = (ImageView) view.findViewById(R.id.change_email_icon);
        change_phone_number_main = (LinearLayout) view.findViewById(R.id.change_phone_number_main);
        change_phone_number_layout = (LinearLayout) view.findViewById(R.id.change_phone_number_layout);
        change_phone_number_view = (LinearLayout) view.findViewById(R.id.change_phone_number_view);
        change_phone_number_icon = (ImageView) view.findViewById(R.id.change_phone_number_icon);
        social_security_number_main = (LinearLayout) view.findViewById(R.id.social_security_number_main);
        social_security_number_layout = (LinearLayout) view.findViewById(R.id.social_security_number_layout);
        social_security_number_view = (LinearLayout) view.findViewById(R.id.social_security_number_view);
        social_security_number_icon = (ImageView) view.findViewById(R.id.social_security_number_icon);
        notification_setting_main = (LinearLayout) view.findViewById(R.id.notification_setting_main);
        notification_setting_layout = (LinearLayout) view.findViewById(R.id.notification_setting_layout);
        notification_setting_view = (LinearLayout) view.findViewById(R.id.notification_setting_view);
        notification_setting_icon = (ImageView) view.findViewById(R.id.notification_setting_icon);
        setting_main = (LinearLayout) view.findViewById(R.id.setting_main);
        setting_layout = (LinearLayout) view.findViewById(R.id.setting_layout);
        setting_icon = (ImageView) view.findViewById(R.id.setting_icon);
        edt_oldpassword = (RegularEditText) view.findViewById(R.id.edt_oldpassword);
        edt_newpassword = (RegularEditText) view.findViewById(R.id.edt_newpassword);
        edt_reenter_password = (RegularEditText) view.findViewById(R.id.edt_reenter_password);
        submit_changepassword = (RegularButton) view.findViewById(R.id.submit_changepassword);
        edit = (LinearLayout) view.findViewById(R.id.edit);
        upload_image = (LinearLayout) view.findViewById(R.id.upload_image);
        social_security_submit = (RegularButton) view.findViewById(R.id.social_security_submit);
        submit_change_email = (RegularButton) view.findViewById(R.id.submit_change_email);
        submit_change_phonenumber = (RegularButton) view.findViewById(R.id.submit_change_phonenumber);
        user_new_email = (RegularEditText) view.findViewById(R.id.user_new_email);
        email_switch = (SwitchButton) view.findViewById(R.id.email_switch);
        pushnotification_switch = (SwitchButton) view.findViewById(R.id.pushnotification_switch);
        submit_leave_location = (ImageView)view.findViewById(R.id.submit_leave_location);
        //preptime setting
        show_preptime_recycler = (RecyclerView) view.findViewById(R.id.show_preptime_recycler);
        selected_preptime = (RegularTextView) view.findViewById(R.id.selected_preptime);
        enter_preptime = (RegularEditText) view.findViewById(R.id.enter_preptime);
        submit_preptime = (RegularButton) view.findViewById(R.id.submit_preptime);
        setting_switch = (SwitchButton) view.findViewById(R.id.setting_switch);
        layout_default_password =(LinearLayout)view.findViewById(R.id.layout_default_password);
        layout_sucess_password =(LinearLayout)view.findViewById(R.id.layout_sucess_password);
        edt_sucess_social_number = (RegularEditText)view.findViewById(R.id.edt_sucess_social_number);
        btn_sucess_social_number = (RegularButton)view.findViewById(R.id.btn_sucess_social_number);
        time_format = (EditText)view.findViewById(R.id.time_format);
        additional_clockin_cloockout = (LinearLayout)view.findViewById(R.id.additional_clockin_cloockout);
        img_clocin_clockout = (ImageView)view.findViewById(R.id.img_clocin_clockout);
        additional_clockin_cloockout_close = (LinearLayout)view.findViewById(R.id.additional_clockin_cloockout_close);
        btn_system_calnder = (LinearLayout)view.findViewById(R.id.btn_system_calnder);
        txt_system_calander = (RegularTextView)view.findViewById(R.id.txt_system_calander);
        clinic_grouped_by = (LinearLayout)view.findViewById(R.id.clinic_grouped_by);
        txt_clinic_grouped_by = (RegularTextView)view.findViewById(R.id.txt_clinic_grouped_by);



        additional_clockin_cloockout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (additional_clockin_cloockout_close.getVisibility() == View.GONE) {
                    additional_clockin_cloockout_close.setVisibility(View.VISIBLE);
                    img_clocin_clockout.setRotation(img_clocin_clockout.getRotation() + 180);
                } else {
                    additional_clockin_cloockout_close.setVisibility(View.GONE);
                    img_clocin_clockout.setRotation(img_clocin_clockout.getRotation() - 180);

                }
            }
        });

        preptime_default_list = new ArrayList<>();


        try {
            JSONObject object = new JSONObject();
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("device_id", SessionManager.getDeviceId(getActivity()));
            object.put("platform_type", "android app");
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new ProfileAsync().execute(encrypted);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!AppUtills.isNetworkAvailable(getActivity())){
            user_name.setText(SessionManager.getuserProfileFirstname(getActivity()) + " " + SessionManager.getuserProfilelastname(getActivity()));
            user_description.setText(SessionManager.getuserProfiledescription(getActivity()));
            user_desgination.setText(SessionManager.getuserProfiledesgignation(getActivity()));
            user_address.setText(SessionManager.getuserProfileaddress(getActivity()));
        }



        //get preptime array
        try {
            JSONObject object = new JSONObject();
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("device_id", SessionManager.getDeviceId(getActivity()));
            object.put("platform_type", "android app");
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new DefaultPrepTimeAsync().execute(encrypted);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        change_password_view.setVisibility(View.VISIBLE);
        change_email_view.setVisibility(View.VISIBLE);
        change_phone_number_view.setVisibility(View.VISIBLE);
        social_security_number_view.setVisibility(View.VISIBLE);
        notification_setting_view.setVisibility(View.VISIBLE);
        user_name.setEnabled(false);
        user_desgination.setEnabled(false);
        user_description.setEnabled(false);
        user_address.setEnabled(false);

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 selectImage();
            }
        });

        show_before_preptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show_before_open_layout.getVisibility() == View.GONE) {
                    show_before_open_layout.setVisibility(View.VISIBLE);
                    img_down.setRotation(img_down.getRotation() + 180);
                } else {
                    show_before_open_layout.setVisibility(View.GONE);
                    img_down.setRotation(img_down.getRotation() - 180);

                }

            }
        });

        change_password_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (change_password_layout.getVisibility() == View.GONE) {
                    change_password_layout.setVisibility(View.VISIBLE);
                    change_password_view.setVisibility(View.GONE);
                    change_password_icon.setRotation(change_password_icon.getRotation() + 180);
                } else {
                    change_password_layout.setVisibility(View.GONE);
                    change_password_view.setVisibility(View.VISIBLE);
                    change_password_icon.setRotation(change_password_icon.getRotation() - 180);

                }
            }
        });
        change_email_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (change_email_layout.getVisibility() == View.GONE) {
                    change_email_layout.setVisibility(View.VISIBLE);
                    change_email_view.setVisibility(View.GONE);
                    change_email_icon.setRotation(change_email_icon.getRotation() + 180);
                } else {
                    change_email_layout.setVisibility(View.GONE);
                    change_email_view.setVisibility(View.VISIBLE);
                    change_email_icon.setRotation(change_email_icon.getRotation() - 180);

                }
            }
        });

        change_phone_number_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (change_phone_number_layout.getVisibility() == View.GONE) {
                    change_phone_number_layout.setVisibility(View.VISIBLE);
                    change_phone_number_view.setVisibility(View.GONE);
                    change_phone_number_icon.setRotation(change_phone_number_icon.getRotation() + 180);
                } else {
                    change_phone_number_layout.setVisibility(View.GONE);
                    change_phone_number_view.setVisibility(View.VISIBLE);
                    change_phone_number_icon.setRotation(change_phone_number_icon.getRotation() - 180);

                }
            }
        });

        social_security_number_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (social_security_number_layout.getVisibility() == View.GONE) {
                    social_security_number_layout.setVisibility(View.VISIBLE);
                    social_security_number_view.setVisibility(View.GONE);
                    social_security_number_icon.setRotation(social_security_number_icon.getRotation() + 180);
                } else {
                    social_security_number_layout.setVisibility(View.GONE);
                    social_security_number_view.setVisibility(View.VISIBLE);
                    social_security_number_icon.setRotation(social_security_number_icon.getRotation() - 180);

                }
            }
        });

        notification_setting_main.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                emailvalue = true;
                pushNotificationvalue = true;

                intializeRecyclerClockIn();
                if (notification_setting_layout.getVisibility() == View.GONE) {
                    notification_setting_layout.setVisibility(View.VISIBLE);
                    notification_setting_view.setVisibility(View.GONE);
                    notification_setting_icon.setRotation(notification_setting_icon.getRotation() + 180);
                } else {
                    notification_setting_layout.setVisibility(View.GONE);
                    notification_setting_view.setVisibility(View.VISIBLE);
                    notification_setting_icon.setRotation(notification_setting_icon.getRotation() - 180);

                }
            }
        });

        setting_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsystemcalanderstatus = true;
                intializeRecycler();
                if (setting_layout.getVisibility() == View.GONE) {
                    setting_layout.setVisibility(View.VISIBLE);
                    setting_icon.setRotation(setting_icon.getRotation() + 180);
                } else {
                    setting_layout.setVisibility(View.GONE);
                    setting_icon.setRotation(setting_icon.getRotation() - 180);

                }
            }
        });

        social_security_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSecurityNumber();



            }
        });


        btn_sucess_social_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                       if (edt_sucess_social_number.getText().toString().length() == 0) {
                       alertDialog("Enter Social Security Number");
                } else {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("social_security_number", edt_sucess_social_number.getText().toString().trim());
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new ChangeSocialSecurityNumberAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        submit_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_email.getText().toString().length() == 0 && user_new_email.getText().toString().length() == 0) {
                    alertDialog("Enter Old Email and New Email");
                } else if (user_email.getText().toString().length() == 0) {
                    alertDialog("Enter Old Email");
                } else if (!AppUtills.isEmailValid(user_email.getText().toString().trim())) {
                    alertDialog("Enter Valid Email");
                } else if (user_new_email.getText().toString().length() == 0) {
                    alertDialog("Enter New Email");
                } else if (!AppUtills.isEmailValid(user_new_email.getText().toString().trim())) {
                    alertDialog("Enter Valid Email");
                } else {

                    try {
                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("old_email", user_email.getText().toString().trim());
                        object.put("new_email", user_new_email.getText().toString().trim());
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new ChangeEmailAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        submit_change_phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_phone.getText().toString().length() == 0) {
                    alertDialog("Enter Phone Number");
                } else {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("phone", user_phone.getText().toString().trim());
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new ChangePhoneNumberAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        submit_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtills.isNetworkAvailable(getActivity())){
                    if (edt_oldpassword.getText().toString().length() == 0 && edt_newpassword.getText().toString().length() == 0 && edt_reenter_password.getText().toString().length() == 0) {
                        alertDialog("Enter All Field's");
                    } else if (edt_oldpassword.getText().toString().length() == 0) {
                        alertDialog("Enter Current Password");
                    } else if (edt_newpassword.getText().toString().length() == 0) {
                        alertDialog("Enter New Password");
                    } else if (edt_reenter_password.getText().toString().length() == 0) {
                        alertDialog("Re-enter New Password ");
                    } else {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("user_id", SessionManager.getuserId(getActivity()));
                            object.put("old_password", edt_oldpassword.getText().toString().trim());
                            object.put("new_password", edt_newpassword.getText().toString().trim());
                            object.put("confirm_password", edt_reenter_password.getText().toString().trim());
                            object.put("device_id", SessionManager.getDeviceId(getActivity()));
                            object.put("platform_type", "android app");
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new ChangePasswordAsync().execute(encrypted);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }else{
                    final Snackbar snackbar = Snackbar.make(ll_main_layout, "No Internet Connection", (int) 5000L);
                    snackbar.setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

                    snackbar.show();
                }


            }
        });


        email_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if(emailvalue ==true){


                if (isChecked == true) {


                        try {
                            JSONObject object = new JSONObject();
                            object.put("user_id", SessionManager.getuserId(getActivity()));
                            object.put("device_id", SessionManager.getDeviceId(getActivity()));
                            object.put("platform_type", "android app");
                            object.put("notification_status", "1");
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new userEmailNotificationAsync().execute(encrypted);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                } else {

                        try {
                            JSONObject object = new JSONObject();
                            object.put("user_id", SessionManager.getuserId(getActivity()));
                            object.put("device_id", SessionManager.getDeviceId(getActivity()));
                            object.put("platform_type", "android app");
                            object.put("notification_status", "0");
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new userEmailNotificationAsync().execute(encrypted);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                }
                 }

            }

        });


        pushnotification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(pushNotificationvalue ==true){


                if (isChecked == true) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("notification_status", "1");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new userPushNotificationSettingAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    try {
                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("notification_status", "0");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new userPushNotificationSettingAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                }
            }

        });

        btn_system_calnder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenuSystemCalander(txt_system_calander, system_calander_list);

            }
        });


        clinic_grouped_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenuNotificationGroupby(txt_clinic_grouped_by, notification_groupby_list);
            }
        });

        setting_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(settingsystemcalanderstatus == true){


                if (isChecked == true) {

                    default_status = "1";
                    try {
                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                         object.put("status", default_status);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new snedUpdateSystemCalnderStatusAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                } else {

                    default_status = "0";

                    try {
                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("status", default_status);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new snedUpdateSystemCalnderStatusAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                }
            }

        });

        //SUBMIT PREPTIME
        submit_preptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enter_preptime.getText().toString().equals("")) {
                    preptime = selected_preptime.getText().toString().trim();
                } else {
                    preptime = enter_preptime.getText().toString().trim();
                }


                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("prep_time", preptime);
                    object.put("default_status", system_calender_status);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new userChangePrepTimeAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });



        pager_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                leave_location_pager.setCurrentItem(leave_location_pager.getCurrentItem()-1, true);

            }
        });
        pager_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leave_location_pager.setCurrentItem(leave_location_pager.getCurrentItem()+1, true);
            }
        });



        update_disable_email_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    img_update_disable_confimation*/
                String status = "";
                if (img_update_disable_confimation.getVisibility() == View.GONE) {
                    img_update_disable_confimation.setVisibility(View.VISIBLE);
                    status = String.valueOf(1);
                } else {
                    img_update_disable_confimation.setVisibility(View.GONE);
                    status = String.valueOf(0);
                }


                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("disable_status", status);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new userDisableEmailConfirmationAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*user_name.setEnabled(true);
                user_desgination.setEnabled(true);
                user_description.setEnabled(true);
                user_address.setEnabled(true);*/

                alertDialogUpdateProfile();
            }
        });

        timezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopupMenu(timezone, timezone_list);
            }
        });

        time_format.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showPopupMenuTimeFormat(time_format,array_time_format);
            }
        });


    }



    private void showPopupMenu(final View view, final ArrayList<TimeZone> items) {
        final PopupMenu menu = new PopupMenu(getActivity(), view);
        for (int i = 0; i < items.size(); i++) {

            menu.getMenu().add(items.get(i).getValue());
        }
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) view).setText(item.getTitle());

                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("timezone_value", item.getTitle());
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new updateTimeZoneAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }



                return true;
            }
        });
    }


    private void showPopupMenuTimeFormat(final View view, final ArrayList<TimeFormat> array_time_format) {
        final PopupMenu menu = new PopupMenu(getActivity(), view);
        for (int i = 0; i < array_time_format.size(); i++) {

            menu.getMenu().add(array_time_format.get(i).getName());
        }
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) view).setText(item.getTitle());

                String CurrentStringfirst = String.valueOf(item.getTitle());
                String[] separatedfirst = CurrentStringfirst.split(" ");
                String value = separatedfirst[0];
                String name = separatedfirst[1];

                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("platform_type", "android app");
                    object.put("timeformat_value", value);
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new updateTimeFormatAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }



                return true;
            }
        });
    }



    private void showPopupMenuSystemCalander(final View view, final ArrayList<SystemCalander> items) {
        final PopupMenu menu = new PopupMenu(getActivity(), view);
        for (int i = 0; i < items.size(); i++) {

            menu.getMenu().add(items.get(i).getName());
        }
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) view).setText(item.getTitle());

                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("system_calender_value", item.getTitle());
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new updateSystemCalanderAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }



                return true;
            }
        });
    }

    private void showPopupMenuNotificationGroupby(final View view, final ArrayList<NotificationGroupby> items) {
        final PopupMenu menu = new PopupMenu(getActivity(), view);
        for (int i = 0; i < items.size(); i++) {
            menu.getMenu().add(items.get(i).getName());
        }
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) view).setText(item.getTitle());

                try {
                    JSONObject object = new JSONObject();
                    object.put("user_id", SessionManager.getuserId(getActivity()));
                    object.put("device_id", SessionManager.getDeviceId(getActivity()));
                    object.put("platform_type", "android app");
                    object.put("notification_groupBy_value", item.getTitle());
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new updateClinicGroupedbyAsync().execute(encrypted);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }



                return true;
            }
        });
    }



    private class ProfileAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.getProfileRequest(APIUrl.PROFILE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                   JSONObject jsonObject = new JSONObject(result);
              if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        if (jsonObject.has("user")) {
                            JSONObject obj = jsonObject.getJSONObject("user");

                            Picasso.with(getActivity())
                                    .load(obj.getString("image"))
                                    .into(profile_image);


                            Picasso.with(getActivity())
                                    .load(obj.getString("image"))
                                    .into(MainActivity.user_image);


                            user_name.setText(obj.getString("first_name") + " " + obj.getString("last_name"));
                            user_email.setText(obj.getString("email"));
                            user_phone.setText(obj.getString("phone"));
                            user_social_securitynumber.setText(obj.getString("social_security_number"));
                            user_description.setText(obj.getString("user_description"));
                            user_desgination.setText(obj.getString("designation"));
                            user_address.setText(obj.getString("address"));
                            try {

                                SessionManager.setUserProfile(getActivity(),obj.getString("first_name"),obj.getString("last_name"),
                                        obj.getString("designation"),obj.getString("user_description"),
                                        obj.getString("address")
                                );
                            }catch (Exception e){

                            }

                            emailNotificationStatus = obj.getString("email_notification");
                            pushNotificationStatus = obj.getString("push_notification");
                            clock_in_value = obj.getString("clock_in_value");
                            clock_out_value = obj.getString("clock_out_value");
                            leave_location_value = obj.getString("leave_location_value");
                            disable_status = obj.getString("disable_status");
                            system_calender = obj.getString("system_calender");
                            timezone_selected = obj.getString("timezone");
                            timezone.setText(timezone_selected);
                            txt_system_calander.setText(system_calender);
                            prep_time = obj.getString("prep_time");
                            time_format_value = obj.getString("time_format");
                            time_format.setText(time_format_value);

                            notification_groupBy = obj.getString("notification_groupBy");
                            txt_clinic_grouped_by.setText(notification_groupBy);

                            if(prep_time.equals("12 Hours")){
                                selected_preptime.setText(prep_time);
                                enter_preptime.setText("");
                            }else if(prep_time.equals("1 Day")){
                                enter_preptime.setText("");
                                selected_preptime.setText(prep_time);
                            }else if(prep_time.equals("6 Hours")){
                                enter_preptime.setText("");
                                selected_preptime.setText(prep_time);
                            }else{
                                selected_preptime.setText("");
                                enter_preptime.setText(prep_time);
                            }

                            system_calender_status = obj.getString("system_calender_status");

                            if(emailNotificationStatus.equals("1")){
                                emailvalue = false;
                                email_switch.setChecked(true);

                            }else{
                                email_switch.setChecked(false);
                            }

                            if(pushNotificationStatus.equals("1")){
                                pushNotificationvalue =false;
                                pushnotification_switch.setChecked(true);
                            }else{
                                pushnotification_switch.setChecked(false);
                            }

                            if(disable_status.equals("1")){
                                img_update_disable_confimation.setVisibility(View.VISIBLE);
                            }else{
                                img_update_disable_confimation.setVisibility(View.GONE);
                            }

                            if(system_calender_status.equals("1")){
                                settingsystemcalanderstatus = false;
                                setting_switch.setChecked(true);
                            }else{

                                setting_switch.setChecked(false);
                            }
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





    private class updateTimeZoneAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateTimeZoneRequest(APIUrl.UPDATETIMEZONE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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



    private class updateTimeFormatAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateTimeFormatRequest(APIUrl.UPDATETIMEFORMAT, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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



    private class updateSystemCalanderAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateSystemCalanderRequest(APIUrl.UPDATESYSTEMCALANDER, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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



    private class updateClinicGroupedbyAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateGroupByNotificationRequest(APIUrl.UPDATEGROUPBYNOTIFICATION, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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




    public void alertDialogUpdateProfile() {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.update_profile_dialog, null);
        final LinearLayout main_dialog_layout = (LinearLayout)view.findViewById(R.id.main_dialog_layout);
        final   RegularEditText firstname1 = (RegularEditText)view.findViewById(R.id.firstname);
        final  RegularEditText lastname1 = (RegularEditText)view.findViewById(R.id.lastname);
        final RegularEditText user_desgination1 = (RegularEditText)view.findViewById(R.id.user_desgination);
        final RegularEditText user_description1 = (RegularEditText)view.findViewById(R.id.user_description);
        final   RegularEditText user_address1 = (RegularEditText)view.findViewById(R.id.user_address);
        try {
            String CurrentString = user_name.getText().toString();
            String[] separated = CurrentString.split(" ");
            String firstName =   separated[0]; // this will contain "Fruit"
            String lastName=  separated[1]; // this will contain " they taste good"
            firstname1.setText(firstName);
            lastname1.setText(lastName);
            user_desgination1.setText(user_desgination.getText().toString());
            user_description1.setText(user_description.getText().toString());
            user_address1.setText(user_address.getText().toString());
        }catch (Exception e){

        }


        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();

        RegularButton update_profile = (RegularButton) view.findViewById(R.id.update_profile);
        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtills.isNetworkAvailable(getActivity())){
                 /*   if(firstname1.getText().toString().trim().length()==0){
                        alertDialogError("Enter First Name");
                    }else if(lastname1.getText().toString().trim().length()==0){
                        alertDialogError("Enter Last Name");
                    }else if(user_description1.getText().toString().trim().length()==0){
                        alertDialogError("Enter Description");
                    }else if(user_address1.getText().toString().trim().length()==0){
                        alertDialogError("Enter Address");
                    }else{

                    }*/
                    try {
                        dialog.dismiss();
                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("first_name", firstname1.getText().toString().trim());
                        object.put("last_name", lastname1.getText().toString().trim());
                        object.put("user_description", user_description1.getText().toString().trim());
                        object.put("address", user_address1.getText().toString().trim());
                        object.put("provider_type", user_desgination1.getText().toString().trim());
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new UpdateProfileNewAsync().execute(encrypted, String.valueOf(mImageCaptureUri));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    final Snackbar snackbar = Snackbar.make(main_dialog_layout, "No Internet Connection", (int) 5000L);
                    snackbar.setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

                    snackbar.show();
                }


            }
        });

    }


    private void showPopupMenuUserDesignation(final View view, final ArrayList<DesignationList> items) {
        final PopupMenu menu = new PopupMenu(getActivity(), view);
        for (int i = 0; i < items.size(); i++) {

            menu.getMenu().add(items.get(i).getName());
        }
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) view).setText(item.getTitle());
                return true;
            }
        });
    }


    public void alertDialogSucess(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.alert_dialog_sucess, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
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


    public void dialogSecurityNumber() {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_security_number, null);
       final RegularEditText social_securitynumber = (RegularEditText) view.findViewById(R.id.social_securitynumber);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                if (AppUtills.isNetworkAvailable(getActivity())){
                    if (social_securitynumber.getText().toString().length() == 0) {
                        alertDialog("Enter password");
                    }else{
                        try {
                            JSONObject object = new JSONObject();
                            object.put("user_id", SessionManager.getuserId(getActivity()));
                            object.put("device_id", SessionManager.getDeviceId(getActivity()));
                            object.put("platform_type", "android app");
                            object.put("password", social_securitynumber.getText().toString().trim());
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new SendSecurityPasswordNewAsync().execute(encrypted, String.valueOf(mImageCaptureUri));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }else{
                    final Snackbar snackbar = Snackbar.make(ll_main_layout, "No Internet Connection", (int) 5000L);
                    snackbar.setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });

                    snackbar.show();
                }




            }
        });
    }


    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
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



    public void alertDialogError(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialogerror;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .customView(view, wrapInScrollView);
        dialogerror = builder.build();
        dialogerror.show();
        RegularButton btnOk = (RegularButton) view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogerror.dismiss();
            }
        });
    }

    private class UpdateProfileNewAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateProfileRequest(APIUrl.UPDATEPROFILENEW, params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        if(jsonObject.has("user")){

                            JSONObject obj= jsonObject.getJSONObject("user");
                            user_name.setText(obj.getString("first_name")+ " " + obj.getString("last_name"));
                            MainActivity.user_email.setText(obj.getString("first_name")+ " " + obj.getString("last_name"));
                            user_desgination.setText(obj.getString("provider_type"));
                            user_description.setText(obj.getString("user_description"));
                            user_address.setText(obj.getString("address"));
                            Picasso.with(getActivity())
                                    .load(obj.getString("image_path"))
                                    .into(profile_image);
/*
                            Picasso.with(getActivity())
                                    .load(obj.getString("image_path"))
                                    .into(MainActivity.user_image);*/
                            SessionManager.setfirstName(getActivity(),obj.getString("first_name") );
                            SessionManager.setlastName(getActivity(),obj.getString("last_name") );
                  /*          SessionManager.setImage(getActivity(),obj.getString("image_path") );
*/
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


    private class UpdateProfilePhotoAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpdateProfileRequest(APIUrl.UPDATEPROFILPHOTO, params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        if(jsonObject.has("user")){

                            JSONObject obj= jsonObject.getJSONObject("user");

                            Picasso.with(getActivity())
                                    .load(obj.getString("image_path"))
                                    .into(profile_image);

                            Picasso.with(getActivity())
                                    .load(obj.getString("image_path"))
                                    .into(MainActivity.user_image);

                            SessionManager.setImage(getActivity(),obj.getString("image_path") );

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



    private class SendSecurityPasswordNewAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendSecurityPasswordRequest(APIUrl.CHANGEPASSWORDSECURITY, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                       // alertDialogSucess(jsonObject.getString("message"));
                        layout_sucess_password.setVisibility(View.VISIBLE);
                        layout_default_password.setVisibility(View.GONE);
                        edt_sucess_social_number.setText(jsonObject.getString("data"));
                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {

                        alertDialog(jsonObject.getString("message"));

                        layout_sucess_password.setVisibility(View.GONE);
                        layout_default_password.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }



    private class userEmailNotificationAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUserEmailNotifcationRequest(APIUrl.USEREMAILNOTIFICATION, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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


    private class userPushNotificationSettingAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUserPushNotifcationSettingRequest(APIUrl.USERPUSHNOTIFICATIONSETTING, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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


    private class snedUpdateSystemCalnderStatusAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendUpadteSystemCalanderStatusRequest(APIUrl.UPDATESYSTEMCALANDERSTATUS, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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


    //SUBMIT PREPTIME
    private class userChangePrepTimeAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendChangePrepTimeRequest(APIUrl.USERCHANGEPREPTIME, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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


    private class userDisableEmailConfirmationAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendDisableEmailConfirmationRequest(APIUrl.UPDATEISABLEEMAILCONFIRMATION, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

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





    private class ChangePasswordAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendChangePasswordRequest(APIUrl.CHANGEPASSWORD, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        edt_oldpassword.setText("");
                        edt_newpassword.setText("");
                        edt_reenter_password.setText("");
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


    private class ChangeSocialSecurityNumberAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendSoicalSecuirtyNumberRequest(APIUrl.CHANGESOCIALSECURITYNUMBER, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                        alertDialogSucess(jsonObject.getString("message"));

                        layout_sucess_password.setVisibility(View.GONE);
                        layout_default_password.setVisibility(View.VISIBLE);
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

    //GET PREPTIME ARRAY
    private class DefaultPrepTimeAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendDefaultPrepTimeRequest(APIUrl.DEFAULTSETTINGVALUES, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                        if (jsonObject.has("data")) {
                          JSONObject obj = jsonObject.getJSONObject("data");

                            if(obj.has("prep_time")){
                                preptime_default_list.clear();
                             JSONArray arraypreptime = obj.getJSONArray("prep_time");
                                for (int i = 0; i <arraypreptime.length() ; i++) {
                                    JSONObject preptimeobj = arraypreptime.getJSONObject(i);
                                    preptime_default_list.add(new PrepTime(preptimeobj.getString("name")));
                                }


                            }

                            if(obj.has("clock_in")){
                                clockin_reminder_list.clear();
                                JSONArray arrayClockin = obj.getJSONArray("clock_in");
                                for (int i = 0; i <arrayClockin.length() ; i++) {
                                    JSONObject clockinobj = arrayClockin.getJSONObject(i);
                                    clockin_reminder_list.add(new ClockinReminder(clockinobj.getString("name")));
                                }

                            }


                            if(obj.has("clock_out")){
                                clockout_reminder_list.clear();
                                JSONArray arrayclockout = obj.getJSONArray("clock_out");
                                for (int i = 0; i <arrayclockout.length() ; i++) {
                                    JSONObject clockoutobj = arrayclockout.getJSONObject(i);
                                    clockout_reminder_list.add(new ClockOutReminder(clockoutobj.getString("name")));
                                }
                                intializeRecyclerClockOut();
                            }

                            if(obj.has("leave_location")){
                                JSONArray arrayleave_location = obj.getJSONArray("leave_location");
                                for (int i = 0; i <arrayleave_location.length() ; i++) {
                                    JSONObject leavelocationobj = arrayleave_location.getJSONObject(i);
                                    leave_location_pager_list.add(new LeaveLocationPager(leavelocationobj.getString("name")));
                                }

                                adapter = new ViewPagerAdapter(getActivity(),leave_location_pager_list,submit_leave_location);
                                leave_location_pager.setAdapter(adapter);

                            }

                            if(obj.has("system_calender")){
                                JSONArray arraycal = obj.getJSONArray("system_calender");
                                for (int i = 0; i <arraycal.length() ; i++) {
                                    JSONObject objcal = arraycal.getJSONObject(i);
                                    system_calander_list.add(new SystemCalander(objcal.getString("name")));
                                }

                            }

                            if(obj.has("timezone")){
                                JSONArray arraytimezone = obj.getJSONArray("timezone");
                                timezone_list.clear();
                                for (int i = 0; i <arraytimezone.length() ; i++) {
                                    JSONObject timezoneobj = arraytimezone.getJSONObject(i);
                                    timezone_list.add(new TimeZone(timezoneobj.getString("name"),timezoneobj.getString("value")));
                                }

                            }

                            if(obj.has("notification_groupBy")){
                                JSONArray arraynotification_groupby = obj.getJSONArray("notification_groupBy");
                                for (int i = 0; i <arraynotification_groupby.length() ; i++) {
                                    JSONObject objgroup = arraynotification_groupby.getJSONObject(i);
                                    notification_groupby_list.add(new NotificationGroupby(objgroup.getString("name")));

                                }
                            }

                            if(obj.has("designation")){
                                JSONArray desigarray = obj.getJSONArray("designation");

                                for (int i = 0; i <desigarray.length() ; i++) {

                                    JSONObject desigobj = desigarray.getJSONObject(i);
                                    designation_list.add(new DesignationList(desigobj.getString("name")));
                                }

                            }

                            if(obj.has("time_format")){
                                JSONArray arraytimeformat = obj.getJSONArray("time_format");
                                for (int i = 0; i <arraytimeformat.length() ; i++) {
                                    JSONObject objtimeformat = arraytimeformat.getJSONObject(i);
                                    array_time_format.add(new TimeFormat(objtimeformat.getString("name"),objtimeformat.getString("value")));
                                }
                            }

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

    private class ChangeEmailAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendChangeEmailRequest(APIUrl.PROFILECHANGEEMAIL, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        // user_email.setText("");
                        user_new_email.setText("");

                        if (jsonObject.has("user")) {
                            JSONObject obj = jsonObject.getJSONObject("user");
                            user_email.setText(obj.getString("email"));
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

    private class ChangePhoneNumberAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(getActivity());
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendChangePhoneNumberRequest(APIUrl.PROFILECHANGEPHONE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                        if (jsonObject.has("user")) {
                            JSONObject obj = jsonObject.getJSONObject("user");
                            user_phone.setText(obj.getString("phone"));
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


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    if (mRuntimePermission.hasPermission(getActivity(), ALL_PERMISSIONS)) {

                        EasyImage.openCamera(getActivity(), 1);

                    } else {
                        mRuntimePermission.requestPermission(getActivity(), ALL_PERMISSIONS, ALL_REQUEST_CODE);
                    }

                } else if (options[item].equals("Choose from Gallery")) {

                    if (mRuntimePermission.hasPermission(getActivity(), ALL_PERMISSIONS)) {

                        EasyImage.openGallery(getActivity(), 2);

                    } else {
                        mRuntimePermission.requestPermission(getActivity(), ALL_PERMISSIONS, ALL_REQUEST_CODE1);
                    }

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }
                if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    EasyImage.openCamera(getActivity(), 1);
                }
                break;

            case ALL_REQUEST_CODE1:
                List<Integer> permissionResults1 = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults1.add(grantResult);
                }

                if (permissionResults1.contains(PackageManager.PERMISSION_DENIED)) {
                } else {

                    EasyImage.openGallery(getActivity(), 2);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("data", String.valueOf(data));

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                Log.e("excepppp", "exceppp");
                e.printStackTrace();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Log.e("", "");


                //  mImageCaptureUri = String.valueOf(imageFile);
                Compressor.getDefault(getActivity())
                        .compressToFileAsObservable(imageFile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<File>() {
                            @Override
                            public void call(File file) {
                                compressedImage = file;
                                //  Toast.makeText(getActivity(), "Compressed image save in " + getReadableFileSize(compressedImage.length()), Toast.LENGTH_LONG).show();
                                setCompressedImage(compressedImage);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                //showError(throwable.getMessage());
                            }
                        });

                try {
                    FileInputStream is = new FileInputStream(new File(String.valueOf(imageFile)));

                    Bitmap bmp = BitmapFactory.decodeStream(is);

                    profile_image.setImageBitmap(bmp);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });


    }

    private void setCompressedImage(File compressedImagee) {
        mImageCaptureUri = String.valueOf(compressedImagee);

        try {
            JSONObject object = new JSONObject();
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("device_id", SessionManager.getDeviceId(getActivity()));
            object.put("platform_type", "android app");
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new UpdateProfilePhotoAsync().execute(encrypted, String.valueOf(mImageCaptureUri));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    // email validations
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        try {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    //PREPTIME RECYCLER
    private void intializeRecycler() {
        show_preptime_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        show_preptime_recycler.setItemAnimator(new DefaultItemAnimator());
        show_preptime_recycler.setAdapter(new PrepTimeAdapter(getActivity(), preptime_default_list, selected_preptime));
    }


    private void intializeRecyclerClockIn() {
        clockin_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        clockin_recycler.setItemAnimator(new DefaultItemAnimator());
        clockin_recycler.setAdapter(new ClockinAdapter(getActivity(), clockin_reminder_list,clock_in_value));
    }


    private void intializeRecyclerClockOut() {
        recycler_clockout.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_clockout.setItemAnimator(new DefaultItemAnimator());
        recycler_clockout.setAdapter(new ClockOutAdapter(getActivity(), clockout_reminder_list,clock_out_value));
    }



}