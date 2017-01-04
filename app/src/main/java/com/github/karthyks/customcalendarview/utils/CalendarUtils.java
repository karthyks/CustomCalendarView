package com.github.karthyks.customcalendarview.utils;

public class CalendarUtils {

  public static String getDefaultWeekString(int week) {
    switch (week) {
      case 1:
        return "Sunday";
      case 2:
        return "Monday";
      case 3:
        return "Tuesday";
      case 4:
        return "Wednesday";
      case 5:
        return "Thursday";
      case 6:
        return "Friday";
      case 7:
        return "Saturday";
    }
    return "Sunday";
  }

  public static int getDefaultWeekInt(String week) {
    switch (week) {
      case "Sunday":
        return 1;
      case "Monday":
        return 2;
      case "Tuesday":
        return 3;
      case "Wednesday":
        return 4;
      case "Thursday":
        return 5;
      case "Friday":
        return 6;
      case "Saturday":
        return 7;
    }
    return 1;
  }
}
