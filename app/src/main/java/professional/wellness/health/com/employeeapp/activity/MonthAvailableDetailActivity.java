package professional.wellness.health.com.employeeapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import professional.wellness.health.com.employeeapp.Adapter.AvailableAdapter;
import professional.wellness.health.com.employeeapp.Adapter.AvailableDetailAdapter;
import professional.wellness.health.com.employeeapp.Model.AvailableNew;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularTextView;

/**
 * Created by Admin on 10-08-2017.
 */

public class MonthAvailableDetailActivity extends AppCompatActivity {

    private LinearLayout empReturn;
    public static  MonthAvailableDetailActivity monthAvailableDetailActivity;

    private ArrayList<AvailableNew> available_detail;
    private RecyclerView availableRecycler;
    private String date1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_detail);
        monthAvailableDetailActivity = this;
        init();

    }

    private void init() {
        empReturn = (LinearLayout)findViewById(R.id.empReturn);
        available_detail = new ArrayList<>();
        availableRecycler = (RecyclerView)findViewById(R.id.availableRecycler);
        parseDateToddMMyyyy(getIntent().getStringExtra("date"));

        available_detail.add(new AvailableNew(getIntent().getStringExtra("id"),
                getIntent().getStringExtra("provider_id"),getIntent().getStringExtra("name"),
                getIntent().getStringExtra("phone"),getIntent().getStringExtra("location_name"),
                getIntent().getStringExtra("latitude"),getIntent().getStringExtra("longitude"),
                getIntent().getStringExtra("time"),getIntent().getStringExtra("prep_time"),
                date1,getIntent().getStringExtra("estimated_duration"),
                getIntent().getStringExtra("personnel"),getIntent().getStringExtra("service_provider"),
                getIntent().getStringExtra("number_of_provider"),getIntent().getStringExtra("jay_wallters"),
                getIntent().getStringExtra("status"),getIntent().getStringExtra("created_at"),
                getIntent().getStringExtra("updated_at"),getIntent().getStringExtra("type"),
                getIntent().getStringExtra("status_name")
                ));


        intializeRecyclerAvailable();

        empReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


    }

    private void intializeRecyclerAvailable() {
        availableRecycler.setLayoutManager(new LinearLayoutManager(MonthAvailableDetailActivity.this));
        availableRecycler.setItemAnimator(new DefaultItemAnimator());
        availableRecycler.setAdapter(new AvailableDetailAdapter(this, available_detail));
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "MM-dd-yyyy";
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
