package com.ecommerce.grutils.calendar


import java.sql.Timestamp 
import java.text.DateFormat 
import java.util.Calendar;
import java.util.Date;

/**
 * Calculate the beginning and end timestamps for a day/week/month/year
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
	
	static DateRange dateRangeForDay(Date date){
		Date beg = date.clone()
		beg.clearTime()
		
		// 86399999 = (1000*60*60*24-1)  :not quite a day later
		Date end =  new Date(beg.getTime() + 86399999)
		
		return new DateRange(beg:beg, end:end, type:DAY)
	}
	
	static DateRange dateRangeForWeek(Date date){
		return firstAndLastDayOfPeriod(date, Calendar.DAY_OF_WEEK, WEEK)
	}
	
	static DateRange dateRangeForMonth(Date date){
		return firstAndLastDayOfPeriod(date, Calendar.DAY_OF_MONTH, MONTH)
	}
	
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
		
		Date first = date.clone()
		first.clearTime()
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
	
	Date dayInNextPeriod(){
		new Date(end.getTime() + 1L)
	}
	
	Date dayInPrevPeriod(){
		new Date(beg.getTime() - 1L)
	}
	
	def sqlTimestampsFromRange(){
		[new Timestamp(beg.getTime()), new Timestamp(end.getTime())]
	}
}
