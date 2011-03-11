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
	static final String HOUR = 'HOUR'
	static final String DAY = 'DAY'
	static final String WEEK = 'WEEK'
	static final String MONTH = 'MONTH'
	static final String YEAR = 'YEAR'
	//other would be setting type to null
	
	Date beg
	Date end
	String type
	
	static final RANGE_TYPES = [HOUR, DAY, WEEK, MONTH, YEAR]
	
	/**
	 * Number of day names included in the range 
	 */
	private Map dayNamesInRange
	
	/**
	 * How many of a given Day are included (at least one second) in the range
	 */
	int calcDayNamesInRange(int day){
		if(!dayNamesInRange){
			calcDayNamesInRange()
		}
		
		return dayNamesInRange[day]
	}
	
	Map calcDayNamesInRange(){
		if(!dayNamesInRange){
			def dayNamesInRangeLocal = [(Calendar.SUNDAY):0, (Calendar.MONDAY):0, (Calendar.TUESDAY):0, (Calendar.WEDNESDAY):0, 
					(Calendar.THURSDAY):0, (Calendar.FRIDAY):0, (Calendar.SATURDAY):0]
			
			(beg..end).each{Date time ->
				if(time<end){
					Calendar cal = Calendar.getInstance()
					cal.setTime(time)
					int dayForTime = cal.get(Calendar.DAY_OF_WEEK)
					dayNamesInRangeLocal[dayForTime]=dayNamesInRangeLocal[dayForTime] + 1
				}
			}
			
			dayNamesInRange = dayNamesInRangeLocal
		}
		
		dayNamesInRange
	}
	
	static List<DateRange> hoursBetween(Date start, Date end){
		//calculate all hours ranges between queryStart and queryEnd
		//needs to be in order.
		List<DateRange> hours = [DateRange.dateRangeForHour(start)]
		
		DateRange hour = hours[0]
		while (hour.end<end){
			hour = hour.nexthour()
			hours.add hour
		}
		
		hours
	}
	
	/**
	 * This range is completely before Range b, ie. its ending is befor b's beginning
	 */
	boolean beforeRange(DateRange b){
		this.end<b.beg
	}
	
	/**
	 * This range is completely after Range b, ie. its beginning is after b's end
	 */
	boolean afterRange(DateRange b){
		b.end<this.beg
	}
	
	/**
	 * This range is completely within range b
	 */
	boolean containedInRange(DateRange b){
		((this.beg>=b.beg) && (this.end<=b.end)) 
	}
	
	/**
	 * This range overlaps only the beginning of b
	 */
	boolean overlappingBeginningOfRange(DateRange b){
		((this.beg<b.beg) && (this.end<=b.end) && (this.end>b.beg))
	}
	
	/**
	 * This range overlaps only the ending of b
	 */
	boolean overlappingEndingOfRange(DateRange b){
		((this.beg>=b.beg) && (this.end>b.end) && (this.beg<b.end))
	}
	
	/**
	 * For a bunch of ranges, total the time within an ordered (exclusive) range
	 * @param ranges - A list of DateRanges to separate into "buckets"
	 * @param hoursInPeriod - An ordered list of DateRanges that are completely exclusive and have no gaps (These are the "Buckets")
	 */
	static def dropRangesIntoHours(Collection<DateRange> ranges, List<DateRange> hoursInPeriod){
		Map buckets = [:]
		hoursInPeriod.each{ hour ->
			buckets.put hour, 0L
		}
		
		for (DateRange range : ranges){
		   for(DateRange hour : hoursInPeriod){
			 //is the hour after the range? if so skip to the next range
			 if(hour.afterRange(range)){
			   break
			 }
			  
			 //is the hour before the date range? if so skip to next hour
			 if(hour.beforeRange(range)){
			   continue  
			 }  
			 
			 //is the hour overlapping the beginning and only the beginning?
			 if(hour.overlappingBeginningOfRange(range)){
			   // pull out overlapping bit
			   buckets[hour] = buckets[hour] + (hour.end.getTime() - range.beg.getTime()) +1
			   continue
			 }
			   
			 if(range.containedInRange(hour)){
			   //add entire range
			   buckets[hour] = buckets[hour] + (range.end.getTime() - range.beg.getTime())
			   continue
			 }
				  
			 if(hour.containedInRange(range)){
			   //add entire hour  
			   buckets[hour] = buckets[hour] + (hour.end.getTime()-hour.beg.getTime()) +1
			   continue
			 }
			
			 if(hour.overlappingEndingOfRange(range)){
			   //pull out overlapping bit  
			   buckets[hour] = buckets[hour] + (range.end.getTime() - hour.beg.getTime())
			   continue
			 }
			   
			 throw new Exception("This point should not have been reached.  There's a bug in the if's")
		   }
		}
		
		return buckets
	}
	
	/**
	 * Of this Sql type, what is the most immediate sub range, ie returns day for week.
	 */
    String sqlSubType(){
		switch(type){
			case HOUR:
				'minute'
				break
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
			case HOUR:
				'day'
				break
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
			case HOUR:
				dateRangeForHour(thedate)
				break
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
	
	static DateRange dateRangeForHour(Date date){
		Calendar cal = Calendar.getInstance()
		cal.setTime date
		cal.set(Calendar.MINUTE, 0)
		cal.set(Calendar.SECOND, 0)
		Date beg = cal.getTime()
		Date end = new Date(beg.getTime() + (1000*60*60 - 1))
		return new DateRange(beg:beg, end:end, type:HOUR)
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
	 * Create another DateRange object for the hour after this one.  Throws an exception when this DateRange is not of type 'HOUR'
	 */
	DateRange nexthour(){
		if(this.type==HOUR){
			return new DateRange(beg:new Date(beg.getTime() + 1000*60*60), end:new Date(end.getTime() + 1000*60*60), type:HOUR)
		}else{
			throw new Exception("This will only work on date ranges of type 'HOUR'")
		}
	}
	
	/**
	 * Get sql Timestamps for this range
	 */
	def sqlTimestampsFromRange(){
		[new Timestamp(beg.getTime()), new Timestamp(end.getTime())]
	}
	
	Long size(){
		(end.getTime() - beg.getTime())
	}
	
	String toString(){
		return "type: ${type} - beg: ${beg} - end: ${end}"
	}
}
