package professional.wellness.health.com.employeeapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.mukesh.permissions.AppPermissions;


import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import professional.wellness.health.com.employeeapp.Adapter.CertificateAdapter;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.Certificate;
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

import static android.app.Activity.RESULT_OK;
import static professional.wellness.health.com.employeeapp.Utils.AppUtills.ALL_PERMISSIONS;
import static professional.wellness.health.com.employeeapp.Utils.AppUtills.context;

/**
 * Created by Fujitsu on 30-06-2017.
 */

public class CertificateFragment extends Fragment {
    private CardView empAddNewCerti;
    private ImageView empCross;
    private FloatingActionButton fab;
    private RegularEditText subject;
    private RegularEditText submission_time;
    private RegularEditText description;
    private RegularButton submit;
    private EncryptionClass encryptionClass;
    private RESTInteraction restInteraction;
    private LinearLayout choose_camera;
    private AppPermissions mRuntimePermission;
    private static final int ALL_REQUEST_CODE = 0;
    private static final int ALL_REQUEST_CODE1 = 1;
    private String mImageCaptureUri = "";
    private LinearLayout choose_pdf;
    private String code = null;
    private File compressedImage;
    private RecyclerView recycler_certificate;
    private ArrayList<Certificate> certificate;
    private String outputDateStr1 = "";
    private RegularTextView file_upload;
    private Calendar calendar;
    private FrameLayout main_layout;
    private LinearLayout ll_nonetwork_message;
    private String date1= null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.certificates, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        main_layout = (FrameLayout)view.findViewById(R.id.main_layout);
        ll_nonetwork_message = (LinearLayout) view.findViewById(R.id.ll_nonetwork_message);
        if (!AppUtills.isNetworkAvailable(getActivity())) {
            main_layout.setVisibility(View.GONE);
            ll_nonetwork_message.setVisibility(View.VISIBLE);
        }else {

            main_layout.setVisibility(View.VISIBLE);
            ll_nonetwork_message.setVisibility(View.GONE);
        }

        mRuntimePermission = new AppPermissions();
        encryptionClass = EncryptionClass.getInstance(getActivity());
        restInteraction = RESTInteraction.getInstance(getActivity());
        empAddNewCerti = (CardView) view.findViewById(R.id.empAddNewCerti);
        empCross = (ImageView) view.findViewById(R.id.empCross);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        subject = (RegularEditText) view.findViewById(R.id.subject);
        submission_time = (RegularEditText) view.findViewById(R.id.submission_time);
        description = (RegularEditText) view.findViewById(R.id.description);
        submit = (RegularButton) view.findViewById(R.id.submit);
        choose_camera = (LinearLayout) view.findViewById(R.id.choose_camera);
        choose_pdf = (LinearLayout) view.findViewById(R.id.choose_pdf);
        recycler_certificate = (RecyclerView) view.findViewById(R.id.recycler_certificate);
        file_upload = (RegularTextView) view.findViewById(R.id.file_upload);
        certificate = new ArrayList<>();


        calendar = Calendar.getInstance();
        //date format is:  "Date-Month-Year Hour:Minutes am/pm"
        SimpleDateFormat sdf = new SimpleDateFormat("dd | MM | yyyy"); //Date and time
        final String currentDate = sdf.format(calendar.getTime());
        parseDateToddMMyyyy(currentDate);
        submission_time.setText(date1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empAddNewCerti.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
            }
        });

    /*    submission_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogWithoutDateField();
            }
        });*/
        choose_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                code = "camera";
            }
        });


        choose_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/msword,application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
                startActivityForResult(intent, ALL_REQUEST_CODE);
                code = "choosepdf";
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subject.getText().toString().length() == 0 && submission_time.getText().toString().length() == 0 && description.getText().toString().length() == 0) {
                    alertDialog("Enter All Field's");
                } else if (subject.getText().toString().length() == 0) {
                    alertDialog("Enter Subject");
                } else if (submission_time.getText().toString().length() == 0) {
                    alertDialog("Enter Submission Time");
                } else if (description.getText().toString().length() == 0) {
                    alertDialog("Enter Description");
                } else if (mImageCaptureUri.equals("")) {
                    alertDialog("Select image/pdf");
                } else {

                    try {

                        JSONObject object = new JSONObject();
                        object.put("user_id", SessionManager.getuserId(getActivity()));
                        object.put("device_id", SessionManager.getDeviceId(getActivity()));
                        object.put("platform_type", "android app");
                        object.put("subject", subject.getText().toString().trim());
                        object.put("description", description.getText().toString().trim());
                        object.put("date", currentDate);
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new UploadCertificateAsync().execute(encrypted, String.valueOf(mImageCaptureUri));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        empCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empAddNewCerti.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                subject.setText("");
                description.setText("");
                file_upload.setText("");
                file_upload.setText("");
            }
        });


        try {
            JSONObject object = new JSONObject();
            object.put("user_id", SessionManager.getuserId(getActivity()));
            object.put("device_id", "waaedawedarffgsgsrgsrgg");
            object.put("platform_type", "android app");
            String send_data = object.toString();
            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
            new getCertificateAsync().execute(encrypted);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
 /*       getCurrentWeek(calendar);*/

    }


    private class getCertificateAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.getCertificateRequest(APIUrl.GETCERTIFICATE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                        if (jsonObject.has("certificate")) {
                            certificate.clear();
                            JSONArray array = jsonObject.getJSONArray("certificate");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);

                                certificate.add(new Certificate(obj.getString("certificate_id"), obj.getString("user_id"), obj.getString("subject"),
                                        obj.getString("description"), obj.getString("type"), obj.getString("status"), obj.getString("date"),
                                        obj.getString("created_at"), obj.getString("updated_at"), obj.getString("file_path")));


                            }

                            intializeRecycler();

                        }

                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        // alertDialog(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }

        }
    }


    private class UploadCertificateAsync extends AsyncTask<String, Integer, String> {

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
            return restInteraction.sendUpoladCertificateRequest(APIUrl.CERTIFICATEUPLOAD, params[0], params[1]);
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
                        empAddNewCerti.setVisibility(View.GONE);
                        fab.setVisibility(View.VISIBLE);
                        mImageCaptureUri = "";
                        subject.setText("");
                        description.setText("");
                        file_upload.setText("");
                        file_upload.setText("");
                        try {
                            JSONObject object = new JSONObject();
                            object.put("user_id", SessionManager.getuserId(getActivity()));
                            object.put("device_id", SessionManager.getDeviceId(getActivity()));
                            object.put("platform_type", "android app");
                            String send_data = object.toString();
                            String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                            new getCertificateAsync().execute(encrypted);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
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


    private void createDialogWithoutDateField() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
                        + "-" + String.valueOf(dayOfMonth);
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd | MM | yyyy");
                DateFormat outputFormat1 = new SimpleDateFormat("dd-MM-yyyy");
                String inputDateStr = date;
                try {
                    Date datex = inputFormat.parse(inputDateStr);
                    String outputDateStr = outputFormat.format(datex);
                    outputDateStr1 = outputFormat1.format(datex);
                    submission_time.setText(outputDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //tfDate.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
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


    private void selectImage() {
        if (mRuntimePermission.hasPermission(getActivity(), ALL_PERMISSIONS)) {

            EasyImage.openCamera(getActivity(), 1);

        } else {
            mRuntimePermission.requestPermission(getActivity(), ALL_PERMISSIONS, ALL_REQUEST_CODE);
        }
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


        if (code.equals("choosepdf")) {
            if (!String.valueOf(data).equals("null")) {
                Uri fileuri = data.getData();
                mImageCaptureUri = getFileNameByUri(getActivity(), fileuri);
                file_upload.setText("File Uploded");
            }

        } else

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


                }


            });


    }

    private String getFileNameByUri(Context context, Uri uri) {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        } else if (uri.getScheme().compareTo("file") == 0) {
            try {
                file = new File(new URI(uri.toString()));
                if (file.exists())
                    filepath = file.getAbsolutePath();

            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            filepath = uri.getPath();
        }
        return filepath;
    }

    private void setCompressedImage(File compressedImagee) {
        mImageCaptureUri = String.valueOf(compressedImagee);
        file_upload.setText("Image Uploded");
    }

    private void intializeRecycler() {
        recycler_certificate.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_certificate.setItemAnimator(new DefaultItemAnimator());
        recycler_certificate.setAdapter(new CertificateAdapter(getActivity(), certificate));
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

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd | MM | yyyy";
        String outputPattern = "MM | dd | yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            date1 = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }



}