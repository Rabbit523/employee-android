package professional.wellness.health.com.employeeapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.Constant;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularEditText;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Navit on 29-06-2017.
 */

public class LoginActivity extends AppCompatActivity {
    private RegularButton welLogin;
    private RESTInteraction restInteraction;
    private RegularEditText wellUserName;
    private RegularEditText wellPassword;
    private RegularTextView forgot_password;
    private EncryptionClass encryptionClass;
     MaterialDialog dialoga;
    private LinearLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        encryptionClass = EncryptionClass.getInstance(LoginActivity.this);
        restInteraction = RESTInteraction.getInstance(LoginActivity.this);
        welLogin = (RegularButton) findViewById(R.id.welLogin);
        wellUserName = (RegularEditText) findViewById(R.id.wellUserName);
        wellPassword = (RegularEditText) findViewById(R.id.wellPassword);
        forgot_password = (RegularTextView) findViewById(R.id.forgot_password);
        main_layout = (LinearLayout) findViewById(R.id.main_layout);

        welLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wellUserName.getText().toString().trim().length() == 0 && wellPassword.getText().toString().trim().length() == 0) {
                    alertDialog("Enter Email and Password");
                } else if (wellUserName.getText().toString().trim().length() == 0) {
                    alertDialog("Enter Email");
                } else if (wellPassword.getText().toString().trim().length() == 0) {
                    alertDialog("Enter Password");
                } else if (!AppUtills.isEmailValid(wellUserName.getText().toString().trim())) {
                    alertDialog("Enter Valid Email");
                } else {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("email", wellUserName.getText().toString().trim());
                        object.put("password", wellPassword.getText().toString().trim());
                        object.put("device_id", SessionManager.getDeviceId(LoginActivity.this));
                        object.put("platform_type", "android app");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        if (AppUtills.isNetworkAvailable(LoginActivity.this)){
                             new LoginAsync().execute(encrypted);
//                            new LoginAsync().execute(send_data);
                        }else{
                            final Snackbar snackbar = Snackbar.make(main_layout, "No Internet Connection", (int) 5000L);
                            snackbar.setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            });

                            snackbar.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();

            }
        });

    }

    private class LoginAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(LoginActivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("Tag: ", params[0]);
            return restInteraction.sendLoginRequest(APIUrl.LOGIN, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d("Recevied: ", result);
//            alertDialog(result);
            if (!result.equals("") ) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.has("status")&& jsonObject.getString("status").equals("success")){
                        if(jsonObject.has("user")){
                            JSONObject obj = jsonObject.getJSONObject("user");
                            Log.d("Recevied user: ", obj.getString("user_id"));
                            SessionManager.setUserInfo(LoginActivity.this, obj.getString("user_id"),
                                    obj.getString("title"),obj.getString("email"),obj.getString("phone"),obj.getString("address"),
                                    obj.getString("social_security_number"),obj.getString("provider_type"),obj.getString("hourly_rate"),obj.getString("max_hours"),
                                    obj.getString("status")
                                    );
                            SessionManager.setfirstName(LoginActivity.this,obj.getString("first_name") );
                            SessionManager.setlastName(LoginActivity.this,obj.getString("last_name") );
                            SessionManager.setImage(LoginActivity.this,obj.getString("image") );
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("type", "");
                            intent.putExtra("clinic_id", "");
                            startActivity(intent);
                            finish();
                        }
                    }
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                        alertDialog(jsonObject.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("Recevied none: ", "none");
            }

        }
    }

    public void alertDialog(String message) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
        View view = inflater.inflate(R.layout.valid_email_dialog_layout, null);
        RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(LoginActivity.this)
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
    public void alertDialog() {
        boolean wrapInScrollView = true;
        LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
        View view = inflater.inflate(R.layout.dialog_forgot_password, null);
        final RegularEditText edt_email = (RegularEditText)view.findViewById(R.id.edt_email);
        RegularButton reset_password = (RegularButton)view.findViewById(R.id.reset_password);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(LoginActivity.this)
                .customView(view, wrapInScrollView);
        dialoga = builder.build();
        dialoga.show();

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_email.getText().toString().length() ==0){
                    alertDialog("Enter Email");
                }else if(!AppUtills.isEmailValid(edt_email.getText().toString().trim())){
                    alertDialog("Enter Valid Email");
                }else{
                    try {
                        JSONObject object = new JSONObject();
                        object.put("email", edt_email.getText().toString().trim());
                        object.put("device_id", SessionManager.getDeviceId(LoginActivity.this));
                        object.put("platform_type", "android app");
                        String send_data = object.toString();
                        String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                        new ForgotPasswordAsync().execute(encrypted);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

    private class ForgotPasswordAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(LoginActivity.this);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.sendForget(APIUrl.FORGOTPASSWORD, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("") ) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.has("status")&& jsonObject.getString("status").equals("success")){
                        dialoga.dismiss();
                        alertDialog(jsonObject.getString("message"));
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



}
