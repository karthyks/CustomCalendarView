package com.github.karthyks.customcalendarview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CustomDateView extends FrameLayout {

  private View view;
  private TextView tvDate;
  private View viewFilled;

  public CustomDateView(Context context) {
    super(context);
    init();
  }

  public CustomDateView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CustomDateView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomDateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    view = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_date, this, true);
    tvDate = (TextView) view.findViewById(R.id.tv_custom_date);
    viewFilled = view.findViewById(R.id.view_filled);
    tvDate.setVisibility(GONE);
    viewFilled.setVisibility(GONE);
    view.setClickable(false);
  }

  public void showDate(String date, boolean filled) {
    tvDate.setVisibility(VISIBLE);
    view.setClickable(true);
    tvDate.setText(date);
    if (filled)
      viewFilled.setVisibility(VISIBLE);
    else
      viewFilled.setVisibility(GONE);
  }
}
