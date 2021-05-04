package professional.wellness.health.com.employeeapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.Rest.SessionManager;

/**
 * Created by Admin on 26-07-2017.
 */

public class CompanyAnnouncementDetailActivity extends AppCompatActivity {
private LinearLayout ll_return;
private String image_path;
    private String date;
    private ImageView image_announcemnet;
    private TextView txt_date;
    private TextView txt_description;
    private String description;
    private TextView txt_title;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_announcement_detail);
        init();
    }

    private void init() {
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        image_announcemnet = (ImageView)findViewById(R.id.image_announcemnet);
        txt_date = (TextView)findViewById(R.id.txt_date);
        txt_description = (TextView)findViewById(R.id.txt_description);
        txt_title = (TextView)findViewById(R.id.txt_title);
        date = getIntent().getStringExtra("date");
        image_path = getIntent().getStringExtra("image_path");
        description = getIntent().getStringExtra("description");
        txt_date.setText("Date : " + date);
        title = getIntent().getStringExtra("title");
        txt_title.setText(title);
        txt_description.setText(description);
        if (!image_path.equals("")){
            Picasso.with(this)
                    .load(image_path)
                    .into(image_announcemnet);
        }

        if(image_path.equals("")){
            image_announcemnet.setVisibility(View.GONE);
        }else{
            image_announcemnet.setVisibility(View.VISIBLE);
        }


        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
