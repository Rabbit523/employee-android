package professional.wellness.health.com.employeeapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import professional.wellness.health.com.employeeapp.MainActivity;
import professional.wellness.health.com.employeeapp.R;
import professional.wellness.health.com.employeeapp.View.RegularButton;

/**
 * Created by Fujitsu on 30-06-2017.
 */

public class AvailableFragment extends Fragment {
    PopupWindow changeStatusPopUp;
    private ScrollView availableScroll;
    private CardView empPreferredAvailable;
    private RegularButton empAccepte;
    private RegularButton empAccepte1;
    private RegularButton empAccepte2;
    private RegularButton empAccepte3;
    private RegularButton empDone;
    private LinearLayout bottom_layout;
    private LinearLayout accept_layout;
    private RegularButton done;
    private RegularButton done1;
    private PopupMenu popup;
    private LinearLayout accept_layout1;
    private LinearLayout hide_provider;
    private boolean selectedWeek =false;
    private boolean selectedDay =false;
    private boolean selectedMonth =false;
    private boolean selectedUocoming =false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.available_row_tems, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        availableScroll = (ScrollView) view.findViewById(R.id.availableScroll);
        empPreferredAvailable = (CardView) view.findViewById(R.id.empPreferredAvailable);
        empAccepte = (RegularButton) view.findViewById(R.id.empAccepte);
        empAccepte1 = (RegularButton) view.findViewById(R.id.empAccepte1);
        empAccepte2 = (RegularButton) view.findViewById(R.id.empAccepte2);
        empAccepte3 = (RegularButton) view.findViewById(R.id.empAccepte3);
        bottom_layout = (LinearLayout)view.findViewById(R.id.bottom_layout);
        accept_layout = (LinearLayout)view.findViewById(R.id.accept_layout);
        done = (RegularButton)view.findViewById(R.id.done);
        done1 = (RegularButton)view.findViewById(R.id.done1);
        empDone = (RegularButton) view.findViewById(R.id.empDone);

        hide_provider = (LinearLayout)view.findViewById(R.id.hide_provider);
        accept_layout1 = (LinearLayout)view.findViewById(R.id.accept_layout1);


        empAccepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_layout.setVisibility(View.GONE);
                accept_layout.setVisibility(View.VISIBLE);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom_layout.setVisibility(View.VISIBLE);
                accept_layout.setVisibility(View.GONE);
            }
        });

        done1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept_layout1.setVisibility(View.GONE);
                hide_provider.setVisibility(View.VISIBLE);


            }
        });
        empAccepte1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accept_layout1.setVisibility(View.VISIBLE);
                hide_provider.setVisibility(View.GONE);

               /* availableScroll.setVisibility(View.GONE);
                empPreferredAvailable.setVisibility(View.VISIBLE);*/
            }
        });


        empAccepte2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availableScroll.setVisibility(View.GONE);
                empPreferredAvailable.setVisibility(View.VISIBLE);
            }
        });


        empAccepte3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availableScroll.setVisibility(View.GONE);
                empPreferredAvailable.setVisibility(View.VISIBLE);
            }
        });
        empDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availableScroll.setVisibility(View.VISIBLE);
                empPreferredAvailable.setVisibility(View.GONE);
            }
        });




        MainActivity.img_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.img_calander_blue.setSelected(true);
                Context wrapper = new ContextThemeWrapper(getActivity(), R.style.popupMenuStyle);
                popup = new PopupMenu(wrapper, MainActivity.ll_calander);

                // popup = new PopupMenu(getActivity(), MainActivity.ll_calander);

                try {
                    Field[] fields = popup.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(popup);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                popup.getMenuInflater()
                        .inflate(R.menu.poupup_menu, popup.getMenu());
                if (selectedWeek) {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(2).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(2).setTitle(s);
                    popup.getMenu().getItem(2).setIcon(getResources().getDrawable(R.mipmap.red_week_icon));
                } else {
                    SpannableString s = new SpannableString(popup.getMenu().getItem(2).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(2).setTitle(s);
                    popup.getMenu().getItem(2).setIcon(getResources().getDrawable(R.mipmap.week));
                }

                if (selectedDay) {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(3).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(3).setTitle(s);
                    popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.red_day_icon));
                } else {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(3).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(3).setTitle(s);
                    popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.dayview));
                }

                if (selectedMonth) {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(1).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(1).setTitle(s);
                    popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.red_month_icon));
                } else {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(1).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(1).setTitle(s);
                    popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.month));

                }

                if (selectedUocoming) {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(0).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    popup.getMenu().getItem(0).setTitle(s);
                    popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.red_upcoming_icon));
                } else {
                    SpannableString s = new SpannableString( popup.getMenu().getItem(0).getTitle().toString());
                    s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
                    popup.getMenu().getItem(0).setTitle(s);
                    popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.calendergrey));
                }
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {


                        if (item.getTitle().toString().equals("Week")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            selectedWeek = true;
                            selectedDay = false;
                            selectedMonth = false;
                            selectedUocoming = false;
                            popup.getMenu().getItem(2).setIcon(getResources().getDrawable(R.mipmap.red_week_icon));

                        } else if (item.getTitle().toString().equals("Day")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            selectedWeek = false;
                            selectedDay = true;
                            selectedMonth = false;
                            selectedUocoming = false;
                            popup.getMenu().getItem(3).setIcon(getResources().getDrawable(R.mipmap.red_day_icon));

                        } else if (item.getTitle().toString().equals("Upcoming")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            popup.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.red_upcoming_icon));
                            selectedWeek = false;
                            selectedDay = false;
                            selectedMonth = false;
                            selectedUocoming = true;

                        } else if (item.getTitle().toString().equals("Month")) {
                            SpannableString s = new SpannableString(item.getTitle().toString());
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            item.setTitle(s);
                            popup.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.red_month_icon));
                            selectedWeek = false;
                            selectedDay = false;
                            selectedMonth = true;
                            selectedUocoming = false;

                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

    }




}
