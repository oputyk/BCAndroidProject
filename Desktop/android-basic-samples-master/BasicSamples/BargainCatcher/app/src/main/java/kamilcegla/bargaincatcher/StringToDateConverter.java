package kamilcegla.bargaincatcher;

import java.util.Date;

public class StringToDateConverter {
	public static Date stringToDate(String fullDateText) throws Exception {		
		String timeText = parseTimeText(fullDateText);
		int hour = parseHour(timeText);
		int minute = parseMinute(timeText);
		
		String dateText = parseDateText(fullDateText);
		int day = parseDay(dateText);
		int month = parseMonth(dateText);
		int year = parseYear(dateText) - 1900;

		@SuppressWarnings("deprecation")
		Date date = new Date(year, month, day, hour, minute);
		return date;
	}

	private static String parseDateText(String fullDateText) {
		String dateText = fullDateText.substring(fullDateText.indexOf(",") + 2);
		return dateText;
	}

	private static String parseTimeText(String fullDateText) {
		String timeText = fullDateText.substring(0, fullDateText.indexOf(","));
		return timeText;
	}

	private static int parseYear(String dateText) {
		String yearText = dateText.substring(dateText.lastIndexOf(" ") + 1);
		int year = Integer.parseInt(yearText);
		return year;
	}

	private static int parseMonth(String dateText) throws Exception {
		String monthText = dateText.substring(dateText.indexOf(" ") + 1, dateText.lastIndexOf(" "));
		int month = stringToMonth(monthText);
		return month;
	}

	private static int parseDay(String dateText) {
		String dayText = dateText.substring(0, dateText.indexOf(" "));
		int day = Integer.parseInt(dayText);
		return day;
	}

	private static int parseMinute(String timeText) {
		String minuteText = timeText.substring(timeText.indexOf(":") + 1);
		int minute = Integer.parseInt(minuteText);
		return minute;
	}

	private static int parseHour(String timeText) {
		String hourText = timeText.substring(0, timeText.indexOf(":"));
		int hour = Integer.parseInt(hourText);
		return hour;
	}

	private static int stringToMonth(String monthText) throws Exception {
		String months[] = {"sty", "lut", "mar", "kwi", "maj", "cze", "lip", "sie", "wrz", "pa", "lis", "gru"};
		
		for(int i = 0; i < 12; i++) {
			if(monthText.contains(months[i]))
				return i;
		}
		
		throw new Exception("Month name is not correct");
	}
}