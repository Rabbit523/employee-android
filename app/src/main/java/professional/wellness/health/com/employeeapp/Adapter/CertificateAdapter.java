package professional.wellness.health.com.employeeapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import professional.wellness.health.com.employeeapp.GPSTracker;
import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.Model.Certificate;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.APIUrl;
import professional.wellness.health.com.employeeapp.Rest.EncryptionClass;
import professional.wellness.health.com.employeeapp.Rest.RESTInteraction;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;
import professional.wellness.health.com.employeeapp.Utils.AppUtills;
import professional.wellness.health.com.employeeapp.View.RegularButton;
import professional.wellness.health.com.employeeapp.View.RegularTextView;
import professional.wellness.health.com.employeeapp.activity.LoginActivity;
import professional.wellness.health.com.employeeapp.fragment.CertificateFragment;

/**
 * Created by Admin on 13-07-2017.
 */

public class CertificateAdapter  extends RecyclerView.Adapter<CertificateAdapter.ItemViewHolder>{

    private Context context;
    private ArrayList<Certificate> certificate;
    private RESTInteraction restInteraction;
    private EncryptionClass encryptionClass;
    private double latitude;
    private double longitude;
    private int pos;

    /*    items*/
    public CertificateAdapter(Context context, ArrayList<Certificate> certificate) {
        this.context = context;

        this.certificate = certificate;
        encryptionClass = EncryptionClass.getInstance(context);
        restInteraction = RESTInteraction.getInstance(context);
        GPSTracker gps = new GPSTracker(context);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude(); // returns latitude
            longitude = gps.getLongitude(); // returns longitude
        } else {
            gps.showSettingsAlert();
        }
    }


    @Override
    public CertificateAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_certificate_row, parent, false);

        return new CertificateAdapter.ItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final CertificateAdapter.ItemViewHolder holder, final int position) {
        //holder.card_name.setText(mycards.get(position));
  if(certificate.get(position).getType().equals("doc")){

      holder.image.setImageResource(R.drawable.pdf_image);
   }else if(certificate.get(position).getType().equals("docx")){

      holder.image.setImageResource(R.drawable.pdf_image);
  }else  if(certificate.get(position).getType().equals("pdf")){

     holder.image.setImageResource(R.drawable.pdf_image);
  }else{
/*      Glide.with(context)
              .load(certificate.get(position).getFile_path())
              .into(holder.image);*/
      Picasso.with(context)
              .load(certificate.get(position).getFile_path())
              .into(holder.image);

      holder.date.setText(certificate.get(position).getDate());
      holder.subject.setText(certificate.get(position).getSubject());
      holder.description.setText(certificate.get(position).getDescription());
  }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(certificate.get(position).getType().equals("doc")){

                    // image.setImageResource(R.drawable.pdf_image);
                }else if(certificate.get(position).getType().equals("docx")){

                    //  image.setImageResource(R.drawable.pdf_image);
                }else  if(certificate.get(position).getType().equals("pdf")){

                    //image.setImageResource(R.drawable.pdf_image);
                }else{
                    String url = certificate.get(position).getFile_path();
                    alertDialog(url);
                }
            }
        });

        holder.delet_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                alertDialogRemoveCertificate();
            }
        });

    }


    private class deleteCertificateAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = AppUtills.createProgressDialog(context);
                progressDialog.show();
            } else {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return restInteraction.senddeleteCertificateRequest(APIUrl.DELETECERTIFICATE, params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (!result.equals("") ) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.has("status")&& jsonObject.getString("status").equals("success")){
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, certificate.size());
                            notifyDataSetChanged();
                           
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


    @Override
    public int getItemCount() {
        return certificate.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       // private final TextView card_name;
        private final ImageView image;
        private final RegularTextView date;
        private final RegularTextView subject;
        private final RegularTextView description;
        private final ImageView delet_certificate;



        public ItemViewHolder(View itemView) {
            super(itemView);
            //card_name = (TextView) itemView.findViewById(R.id.card_name);
            image = (ImageView)itemView.findViewById(R.id.image);
            date = (RegularTextView)itemView.findViewById(R.id.date);
            subject = (RegularTextView)itemView.findViewById(R.id.subject);
            description = (RegularTextView)itemView.findViewById(R.id.description);
            delet_certificate = (ImageView) itemView.findViewById(R.id.delet_certificate);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {


        }
    }
    public void alertDialog(String url) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.large_image_dialog, null);
        ImageView image_large = (ImageView)view.findViewById(R.id.image_large);

        Picasso.with(context)
                .load(url)
                .into(image_large);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();

    }


    public void alertDialogRemoveCertificate() {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_remove_certificate, null);
 /*       RegularTextView txtMessage = (RegularTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);*/
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(view, wrapInScrollView);
        dialog = builder.build();
        dialog.show();
        RegularButton btn_confirm = (RegularButton) view.findViewById(R.id.btn_confirm);
        RegularButton btn_cancil = (RegularButton) view.findViewById(R.id.btn_cancil);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject();
                    object.put("device_id", SessionManager.getDeviceId(context));
                    object.put("platform_type", "android app");
                    object.put("latitude", latitude);
                    object.put("longitude", longitude);
                    object.put("user_id", SessionManager.getuserId(context));
                    object.put("certificate_id", certificate.get(pos).getCertificate_id());
                    String send_data = object.toString();
                    String encrypted = encryptionClass.bytesToHex(encryptionClass.encrypt(send_data));
                    new deleteCertificateAsync().execute(encrypted);
                    certificate.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, certificate.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_cancil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }



}
