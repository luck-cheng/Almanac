package com.almanac.lunar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	public static String timeStr = "yyyy年MM月dd日  HH时mm分ss秒 时区:Z 星期中的天数:E 年中的周数:w 月份中的周数:W 年中的天数:D 月份中的天数:d 月份中的星期:F ";

	public static Calendar dateToCalendar(Date date) {
		// System.out.println(dateFormat(date,timeStr));
		return timeInMillisToCalendar(date.getTime());
	}

	public static Calendar dateToCalendar2(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Calendar timeInMillisToCalendar(long TimeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(TimeInMillis);
		return calendar;
	}

	public static Calendar timeInMillisToCalendar2(long TimeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(TimeInMillis);
		return calendar;
	}

	public static Calendar instantToCalendar(Instant instant) {
		return timeInMillisToCalendar(instant.toEpochMilli());
	}

	/***
	 * new Date().toInstant()
	 * 
	 * @param instant
	 *            格式2018-09-07T08:16:03.206Z
	 * @return
	 */
	public static Calendar instantToCalendar(String instant) {
		String[] sqlits = instant.split("T|-|Z|:|\\.");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(sqlits[0]), (Integer.parseInt(sqlits[1])) - 1, Integer.parseInt(sqlits[2]),
				Integer.parseInt(sqlits[3]) + 8, Integer.parseInt(sqlits[4]), Integer.parseInt(sqlits[5]));
		return calendar;
	}

	public static Calendar strToCalendar(String str) {
		return dateToCalendar(getDate(str));
	}

	public static Calendar intToCalendar(int year, int month, int day, int hourOfDay, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, hourOfDay, minute, second);
		return calendar;
	}

	public static Calendar intToCalendar(int year, int month, int day, int hourOfDay, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, hourOfDay, minute);
		return calendar;
	}

	/***
	 * 老外用0-11，11就相当于12月
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static Calendar intToCalendar(int year, int month, int day, int hourOfDay, int minute, int second,
			int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return calendar;
	}

	public static String toUTC(Date date) {
		return date.toInstant().toString();
	}

	public static String dateFormat(Calendar calendar, String format) {
		return dateFormat(calendarToDate(calendar), format);
	}

	/***
	 * 时间格式处理
	 * 
	 * @param date
	 * @param format
	 *            yyyyMMddHHmmssZZZ
	 * @return
	 */
	public static String dateFormat(Date date, String format) {
		String result = null;
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 2018-09-07T09:24:05.350Z
	 * 
	 * @param instant
	 * @return
	 */
	public static Date getDate(String str) {
		String format = null;
		if (str.contains(".") || str.contains("T") || str.contains("Z")) {
			str = str.replace("T", " ").replaceAll("Z", "");
			format = "yyyy-MM-dd HH:mm:ss.SS";
		} else if (str.contains("年")) {
			format = "yyyy年MM月dd日 HH:mm:ss";
		} else {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/***
	 * 指定Date类时间 3个参数 http://blog.sina.com.cn/s/blog_4550f3ca0101t042.html
	 */
	public static Date getDate(int y, int M, int d, int h, int m) {
		String str = y + "-" + M + "-" + d + " " + h + ":" + m;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/***
	 * 指定Date类时间 6个参数
	 * 
	 * @return Date对象
	 */
	public static Date getDate(int y, int M, int d, int h, int m, int s) {
		String str = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/***
	 * 时间24小时化
	 * 
	 * @param str
	 * @return
	 */
	public static String getFormattingTime(String str) {
		String newStr = "";
		if (str.contains(":") && !str.contains("年") && !str.contains("-") && !str.contains("月")) {
			String[] strsArea = str.split(":");
			for (int i = 0; i < strsArea.length; i++) {
				if (Integer.valueOf(strsArea[i]) < 10) {
					strsArea[i] = "0" + strsArea[i];
				}
			}
			newStr = strsArea[0] + ":" + strsArea[1] + ":" + strsArea[2];
		} else {
			newStr = str;
		}
		return newStr;
	}

	public static int getTimZoneInt(Calendar calendar) {
		return getTimZoneInt(calendarToDate(calendar));
	};

	public static int getTimZoneInt(Date date) {
		String strDateFormat = formatDateByFormat(date, "Z");// +0800
		String str2 = strDateFormat.substring(1, 3);
		int j = Integer.valueOf(str2.substring(0));
		int timeZoneInt = 0;
		if (j > 0) {
			timeZoneInt = Integer.valueOf(str2);
		} else {
			timeZoneInt = Integer.valueOf(str2.substring(1));
		}
		return timeZoneInt;
	}

	public static String formatDateByFormat(Calendar calendar, String format) {
		return formatDateByFormat(calendarToDate(calendar), format);
	}

	/***
	 * 时间格式处理
	 * 
	 * @param date
	 * @param format
	 *            yyyyMMddHHmmssZZZ
	 * @return
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/***
	 * Calendar 转成 Date
	 * 
	 * @param c
	 * @return
	 */
	public static Date calendarToDate(Calendar calendar) {
		DataBean dataBean = new DataBean(calendar);
		String str = dataBean.getYear() + "-" + dataBean.getMonth() + "-" + dataBean.getDay() + " " + dataBean.getHour()
				+ ":" + dataBean.getMinute() + ":" + dataBean.getSecond();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormat1.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendarToDate(calendar));
	}

	public static String getWeek(Calendar calendar) {
		int y = calendar.get(Calendar.YEAR);
		int m = calendar.get(Calendar.MONTH) + 1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		String week[] = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		// String week[] = new String[] { "日", "一", "二", "三", "四", "五", "六" };
		// String week[] = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		if (m < 3) {
			m += 12;
			--y;
		}
		int w = (d + 1 + 2 * m + 3 * (m + 1) / 5 + y + (y >> 2) - y / 100 + y / 400) % 7;

		return week[w];
	}
	
	public static String getTime(double d) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date((long) d);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}