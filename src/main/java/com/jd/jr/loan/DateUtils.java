package com.jd.jr.loan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: 吴海旭
 * Date: 2017-08-10
 * Time: 下午3:18
 */
public class DateUtils {

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
		return days;
	}

	public static Date strToDateYYYYMMDDHHMMSS(String dateStr) {
		return strToDate(dateStr, "yyyy-MM-dd hh:mm:ss");
	}

	public static Date strToDate(String dateStr, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String dateToStr(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static void main(String[] args) {
		int days = differentDaysByMillisecond(new Date(), strToDateYYYYMMDDHHMMSS("2017-09-10 23:59:59"));
		System.out.println(days);
	}
}
