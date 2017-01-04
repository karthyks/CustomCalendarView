package com.github.karthyks.customcalendarview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.github.karthyks.customcalendarview.utils.CalendarUtils.getDefaultWeekString;

public class CustomCalendarView extends FrameLayout {
  private static final String TAG = CustomCalendarView.class.getSimpleName();
  private View calendarView;

  private List<ViewGroup> weekLayout;
  private List<TextView> weekHeaderTextView;
  private Calendar calendar;

  private Map<Integer, String> weekDaysMap;

  public CustomCalendarView(Context context) {
    super(context);
    init();
  }

  public CustomCalendarView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  private void init() {
    weekLayout = new LinkedList<>();
    weekHeaderTextView = new LinkedList<>();
    calendarView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_calendar, this,
        true);
    weekLayout.add((ViewGroup) calendarView.findViewById(R.id.layout_week_1));
    weekHeaderTextView.add((TextView) calendarView.findViewById(R.id.tv_week_1));
    weekLayout.add((ViewGroup) calendarView.findViewById(R.id.layout_week_2));
    weekHeaderTextView.add((TextView) calendarView.findViewById(R.id.tv_week_2));
    weekLayout.add((ViewGroup) calendarView.findViewById(R.id.layout_week_3));
    weekHeaderTextView.add((TextView) calendarView.findViewById(R.id.tv_week_3));
    weekLayout.add((ViewGroup) calendarView.findViewById(R.id.layout_week_4));
    weekHeaderTextView.add((TextView) calendarView.findViewById(R.id.tv_week_4));
    weekLayout.add((ViewGroup) calendarView.findViewById(R.id.layout_week_5));
    weekHeaderTextView.add((TextView) calendarView.findViewById(R.id.tv_week_5));
    weekLayout.add((ViewGroup) calendarView.findViewById(R.id.layout_week_6));
    weekHeaderTextView.add((TextView) calendarView.findViewById(R.id.tv_week_6));
    weekLayout.add((ViewGroup) calendarView.findViewById(R.id.layout_week_7));
    weekHeaderTextView.add((TextView) calendarView.findViewById(R.id.tv_week_7));

    weekDaysMap = new LinkedHashMap<>();
    weekDaysMap.put(1, getDefaultWeekString(1));
    weekDaysMap.put(2, getDefaultWeekString(2));
    weekDaysMap.put(3, getDefaultWeekString(3));
    weekDaysMap.put(4, getDefaultWeekString(4));
    weekDaysMap.put(5, getDefaultWeekString(5));
    weekDaysMap.put(6, getDefaultWeekString(6));
    weekDaysMap.put(7, getDefaultWeekString(7));
  }

  public void initCalendar(Calendar calendar) {
    this.calendar = calendar;
    init();
    injectWeeks();
    initDates();
  }

  private void initDates() {
    int startWeekDay = getDayOneMapIndex();
    Log.d(TAG, "initDates: " + startWeekDay);
    int date = 0;
    for (int i = startWeekDay; i < 7; i++) {
      date = date + 1;
      injectDates((ViewGroup) weekLayout.get(i).findViewById(R.id.view_group_dates),
          date, true);
    }
    for (int j = 0; j < startWeekDay; j++) {
      date = date + 1;
      injectDates((ViewGroup) weekLayout.get(j).findViewById(R.id.view_group_dates),
          date, false);
    }
  }

  private int getDayOneMapIndex() {
    Calendar dayCalendar = (Calendar) calendar.clone();
    dayCalendar.set(Calendar.DATE, 1);
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
    List<String> weekList = new LinkedList<>(weekDaysMap.values());
    Log.d(TAG, "getDayOneMapIndex: " + dateFormat.format(dayCalendar.getTime()) + " : " +
        dayCalendar.getTime().toString());
    return weekList.indexOf(dateFormat.format(dayCalendar.getTime()));
  }

  private void injectWeeks() {
    setWeekStart(calendar.getFirstDayOfWeek());
    for (int i = 0; i < weekHeaderTextView.size(); i++) {
      weekHeaderTextView.get(i).setText(String.valueOf(weekDaysMap.get(i + 1).charAt(0)));
    }
  }

  public void setWeekStart(int startDay) {
    Map<Integer, String> reOrderedMap = new LinkedHashMap<>();
    int i = 1;
    reOrderedMap.put(i, weekDaysMap.get(startDay));
    for (int j = startDay + 1; j <= 7; j++) {
      i = i + 1;
      reOrderedMap.put(i, weekDaysMap.get(j));
    }
    for (int k = 1; k < startDay; k++) {
      i = i + 1;
      reOrderedMap.put(i, weekDaysMap.get(k));
    }
    weekDaysMap.clear();
    weekDaysMap.putAll(reOrderedMap);
  }

  private void injectDates(ViewGroup parent, int startDate, boolean firstWeek) {
    List<CustomDateView> children = new LinkedList<>();
    for (int i = 0; i < parent.getChildCount(); i++) {
      CustomDateView dateView = (CustomDateView) parent.getChildAt(i);
      children.add(dateView);
    }
    if (!firstWeek) {
      children.remove(0);
    }
    for (int i = 0; i < children.size(); i++) {
      if (startDate + (i * 7) > calendar.getActualMaximum(Calendar.DATE)) break;
      children.get(i).showDate(String.valueOf(startDate + (i * 7)), false);
    }
  }

  public String getMonth() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
    return dateFormat.format(calendar.getTime());
  }
}
