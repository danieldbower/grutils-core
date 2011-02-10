package grutils.calendar

import java.sql.Timestamp 
import java.text.DateFormat 
import java.util.Calendar;
import java.util.Date;

/**
 * Calculates the beginning and end timestamps for a day/week/month/year
 * @author daniel
 *
 */
class DateRange {
	static final String DAY = 'DAY'
	static final String WEEK = 'WEEK'
	static final String MONTH = 'MONTH'
	static final String YEAR = 'YEAR'
	//other would be setting type to null
	
	Date beg
	Date end
	String type
	
	static final RANGE_TYPES = [DAY, WEEK, MONTH, YEAR]
	
	/**
	 * Of this Sql type, what is the most immediate sub range, ie returns day for week.
	 */
    String sqlSubType(){
		switch(type){
			case DAY:
				'hour'
				break
			case WEEK:
				'day'
				break
			case MONTH:
				'week'
				break
			case YEAR:
				'month'
				break
			default:
				'day'
				break
		}
	}
	
	/**
	 * Of this Sql Type, what is the most immediate super range, ie returns week for day.
	 */
	String sqlSuperType(){
		switch(type){
			case DAY:
				'week'
				break
			case WEEK:
				'month'
				break
			case MONTH:
				'year'
				break
			case YEAR:
				'century'
				break
			default:
				'year'
				break
		}
	}
	
	/**
	 * Get a DateRange Object for the given date and Calendar period
	 * @param thedate
	 * @param type
	 */
	static DateRange dateRangeFor(Date thedate, String type){
		switch(type){
			case DAY:
				dateRangeForDay(thedate)
				break
			case WEEK:
				firstAndLastDayOfPeriod(thedate, Calendar.DAY_OF_WEEK, WEEK)
				break
			case MONTH:
				firstAndLastDayOfPeriod(thedate, Calendar.DAY_OF_MONTH, MONTH)
				break
			case YEAR:
				firstAndLastDayOfPeriod(thedate, Calendar.DAY_OF_YEAR, YEAR)
				break
			default:
				//nothing to do here
				null
				break
		}
	}
	
	private static Date clearTime(Date date){
		Calendar cal = Calendar.getInstance()
		cal.setTime date
		cal.set(Calendar.HOUR_OF_DAY, 0)
		cal.set(Calendar.MINUTE, 0)
		cal.set(Calendar.SECOND, 0)
		return cal.getTime()
	}
	
	/**
	 * Get a 1 day date range for the given date
	 * @param date
	 */
	static DateRange dateRangeForDay(Date date){
		Date beg = clearTime(date)
		
		// 86399999 = (1000*60*60*24-1)  :not quite a day later
		Date end =  new Date(beg.getTime() + 1000*60*60*24-1000L)
		
		return new DateRange(beg:beg, end:end, type:DAY)
	}
	
	/**
	 * Get a 1 week date range including the given date
	 * @param date
	 */
	static DateRange dateRangeForWeek(Date date){
		return firstAndLastDayOfPeriod(date, Calendar.DAY_OF_WEEK, WEEK)
	}
	
	/**
	 * Get a 1 month date range including the given date
	 * @param date
	 */
	static DateRange dateRangeForMonth(Date date){
		return firstAndLastDayOfPeriod(date, Calendar.DAY_OF_MONTH, MONTH)
	}
	
	/**
	 * Get a 1 year date range including the given date
	 * @param date
	 * @return
	 */
	static DateRange dateRangeForYear(Date date){
		return firstAndLastDayOfPeriod(date, Calendar.DAY_OF_YEAR, YEAR)
	}
	
	
	/**
	* DayOf parameter... see Calendar for acceptable fields (ex. DAY_OF_MONTH, DAY_OF_WEEK)
	*/
	static DateRange firstAndLastDayOfPeriod(Date date, int dayOf, String type){
		assert date 
		
		Date first = firstDayOfPeriod(date, dayOf)
		return new DateRange(beg:first, end:lastDayOfPeriod(first, dayOf), type:type)
	}
	
	/**
	 * DayOf parameter... see Calendar for acceptable fields (ex. DAY_OF_MONTH, DAY_OF_WEEK)
	 */
	static Date firstDayOfPeriod(Date date, int dayOf){
		assert date
		
		Date first = clearTime(date)
		first = first - date.getAt(dayOf) + 1
		return first
	}
	
	/**
	 * DayOf parameter... see Calendar for acceptable fields (ex. DAY_OF_MONTH, DAY_OF_WEEK)
	 */
	static Date lastDayOfPeriod(Date first, int dayOf){
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
	
	/**
	 * Get a date that is in the next date range from this one
	 */
	Date dayInNextPeriod(){
		new Date(end.getTime() + 1000L)
	}
	
	/**
	 * Get a date that is in the previous date range from this one
	 */
	Date dayInPrevPeriod(){
		new Date(beg.getTime() - 1000L)
	}
	
	/**
	 * Get sql Timestamps for this range
	 */
	def sqlTimestampsFromRange(){
		[new Timestamp(beg.getTime()), new Timestamp(end.getTime())]
	}
}
