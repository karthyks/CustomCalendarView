package com.github.karthyks.customcalendarview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private ViewFlipper viewFlipper;

  private List<CustomCalendarView> calendarViews;
  private CalendarHeaderViewHolder viewHolder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    viewHolder = new CalendarHeaderViewHolder(findViewById(
        R.id.layout_calendar_header), this);
    viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper_calendar);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    calendarViews = new LinkedList<>();
    for (int i = 0; i < 3; i++) {
      CustomCalendarView calendarView = new CustomCalendarView(this);
      calendarView.setLayoutParams(layoutParams);
      Calendar calendar = Calendar.getInstance();
      calendar.setFirstDayOfWeek(Calendar.SUNDAY);
      calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + i);
      calendar.set(Calendar.YEAR, 2017);
      calendarView.initCalendar(calendar);
      calendarViews.add(calendarView);
      viewFlipper.addView(calendarView);
    }
    viewHolder.setMonth(calendarViews.get(0).getMonth());
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_calendar_previous:
        if (viewFlipper.getDisplayedChild() != 0) {
          int displayIndex = viewFlipper.getDisplayedChild() - 1;
          viewHolder.setMonth(calendarViews.get(displayIndex).getMonth());
          viewFlipper.setDisplayedChild(displayIndex);
        }
        break;
      case R.id.btn_calendar_next:
        if (viewFlipper.getDisplayedChild() != (viewFlipper.getChildCount() - 1)) {
          int displayIndex = viewFlipper.getDisplayedChild() + 1;
          viewHolder.setMonth(calendarViews.get(displayIndex).getMonth());
          viewFlipper.setDisplayedChild(displayIndex);
        }
        break;
    }
  }

  private class CalendarHeaderViewHolder {

    private View view;
    private TextView tvMonth;
    private Button btnPrev;
    private Button btnNext;

    public CalendarHeaderViewHolder(View view, View.OnClickListener listener) {
      this.view = view;
      tvMonth = (TextView) view.findViewById(R.id.tv_calendar_month);
      btnPrev = (Button) view.findViewById(R.id.btn_calendar_previous);
      btnPrev.setOnClickListener(listener);
      btnNext = (Button) view.findViewById(R.id.btn_calendar_next);
      btnNext.setOnClickListener(listener);
    }

    public void setMonth(String month) {
      tvMonth.setText(month);
    }
  }
}
