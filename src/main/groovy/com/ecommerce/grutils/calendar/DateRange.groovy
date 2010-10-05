package com.ecommerce.grutils.calendar


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Calculate the beginning and end timestamps for a day/week/month/year
 * @author daniel
 *
 */
class DateRange {
	
	Date beg
	Date end
	
	public static DateRange dateRangeForDay(Date date){
		Date beg = date.clone()
		beg.clearTime()
		
		// 86399999 = (1000*60*60*24-1)  :not quite a day later
		Date end =  new Date(beg.getTime() + 86399999)
		
		return new DateRange(beg:beg, end:end)
	}
	
	public static DateRange dateRangeForWeek(Date date){
		return firstAndLastDayOfPeriod(date, Calendar.DAY_OF_WEEK)
	}
	
	public static DateRange dateRangeForMonth(Date date){
		return firstAndLastDayOfPeriod(date, Calendar.DAY_OF_MONTH)
	}
	
	public static DateRange dateRangeForYear(Date date){
		return firstAndLastDayOfPeriod(date, Calendar.DAY_OF_YEAR)
	}
	
	/**
	* DayOf parameter... see Calendar for acceptable fields (ex. DAY_OF_MONTH, DAY_OF_WEEK)
	*/
	public static DateRange firstAndLastDayOfPeriod(Date date, int dayOf){
		assert date 
		
		Date first = firstDayOfPeriod(date, dayOf)
		return new DateRange(beg:first, end:lastDayOfPeriod(first, dayOf))
	}
	
	/**
	 * DayOf parameter... see Calendar for acceptable fields (ex. DAY_OF_MONTH, DAY_OF_WEEK)
	 */
	public static Date firstDayOfPeriod(Date date, int dayOf){
		assert date
		
		Date first = date.clone()
		first.clearTime()
		first = first - date.getAt(dayOf) + 1
		return first
	}
	
	/**
	 * DayOf parameter... see Calendar for acceptable fields (ex. DAY_OF_MONTH, DAY_OF_WEEK)
	 */
	public static Date lastDayOfPeriod(Date first, int dayOf){
		assert first
		
		//week optimization
		if(Calendar.DAY_OF_WEEK == dayOf){
			return new Date(first.getTime() + 604799999)
		}
		
		Calendar cal = Calendar.getInstance()
		cal.setTime(first)
		int maxForPeriod = cal.getActualMaximum(dayOf)
		return new Date((first + maxForPeriod).getTime() -1)
	}
}
