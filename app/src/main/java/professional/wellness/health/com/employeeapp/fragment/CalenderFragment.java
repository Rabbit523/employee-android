package professional.wellness.health.com.employeeapp.fragment;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import io.blackbox_vision.materialcalendarview.view.CalendarView;
import professional.wellness.health.com.employeeapp.R;

/**
 * Created by Fujitsu on 30-06-2017.
 */

public class CalenderFragment extends Fragment {

    private CalendarView calendarView;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calender_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }



}
