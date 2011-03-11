package grutils.calendar;

import static org.junit.Assert.*;
import org.junit.*;

public class DateRangeTest {
	DateRange range
	
	@Before
	void setup(){
		range = new DateRange(beg:Date.parse('yyyy-MM-dd', '2011-02-27'), end:Date.parse('yyyy-MM-dd', '2011-03-08'))
	}
	
	@Test
	void twoSundaysInRange(){
		assertEquals 2, range.calcDayNamesInRange(Calendar.SUNDAY)
	}
	
	@Test
	void twoMondaysInRange(){
		assertEquals 2, range.calcDayNamesInRange(Calendar.MONDAY)
	}
	
	@Test
	void oneTuesdayInRange(){
		assertEquals 1, range.calcDayNamesInRange(Calendar.TUESDAY)
	}
	
	@Test
	void oneThursdayInRange(){
		assertEquals 1, range.calcDayNamesInRange(Calendar.THURSDAY)
	}
	
	@Test
	void twoTuesdayInRange(){
		range = new DateRange(beg:Date.parse('yyyy-MM-dd', '2011-02-27'), end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-03-08 00:00:01'))
		assertEquals 2, range.calcDayNamesInRange(Calendar.TUESDAY)
	}
	
	@Test
	void dateRangeForHour(){
		def range = DateRange.dateRangeForHour(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:27:22'))
		assertEquals(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:00:00'), range.beg)
		assertEquals(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:59:59').toString(), range.end.toString())
		assertEquals(DateRange.HOUR, range.type)
	}
	
	@Test
	void nextHour(){
		def range = DateRange.dateRangeForHour(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:27:22'))
		def nextRange = range.nexthour()
		assertEquals(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:00:00'), nextRange.beg)
		assertEquals(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:59:59').toString(), nextRange.end.toString())
		assertEquals(DateRange.HOUR, nextRange.type)
	}
	
	@Test
	void hoursBetween(){
		def hours = DateRange.hoursBetween(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-28 16:32:21'), 
				Date.parse('yyyy-MM-dd HH:mm:ss', '2011-03-01 04:27:29'))
		assertEquals 13, hours.size()
		assertEquals(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-28 16:00:00'), hours[0].beg)
		assertEquals(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-03-01 04:59:59').toString(), hours[12].end.toString())
	}
	
	DateRange hour1 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:00:00'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:59:59'))
	
	DateRange range1 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 02:27:22'), 
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:48:22'))
	
	DateRange range2 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 02:27:22'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 02:48:22'))
	
	DateRange range3 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 02:27:22'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 02:59:59'))
	
	DateRange range4 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 02:27:22'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:48:22'))
	
	DateRange range5 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 02:27:22'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:59:59'))
	
	DateRange range6 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:00:00'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:48:22'))
	
	DateRange range7 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:00:00'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:59:59'))
	
	DateRange range8 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:00:00'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:48:22'))
	
	DateRange range9 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:27:22'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:48:22'))
	
	DateRange range10 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:27:22'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:59:59'))
	
	DateRange range11 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 03:27:22'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:48:22'))
	
	DateRange range12 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:00:00'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:48:22'))
	
	DateRange range13 = new DateRange(beg:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:27:22'),
		end:Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 04:48:22'))
	
	@Test
	void beforeRange(){
		assertFalse range1.beforeRange(hour1)
		assert range2.beforeRange(hour1)
		assert range3.beforeRange(hour1)
		assertFalse range4.beforeRange(hour1)
		assertFalse range5.beforeRange(hour1)
		assertFalse range6.beforeRange(hour1)
		assertFalse range7.beforeRange(hour1)
		assertFalse range8.beforeRange(hour1)
		assertFalse range9.beforeRange(hour1)
	}
	
	@Test
	void afterRange(){
		assertFalse range7.afterRange(hour1)
		assertFalse range8.afterRange(hour1)
		assertFalse range9.afterRange(hour1)
		assertFalse range10.afterRange(hour1)
		assertFalse range11.afterRange(hour1)
		assert range12.afterRange(hour1)
		assert range13.afterRange(hour1)
	}
	
	@Test
	void containedInRange(){
		assertFalse range1.containedInRange(hour1)
		assertFalse range2.containedInRange(hour1)
		assertFalse range3.containedInRange(hour1)
		assertFalse range4.containedInRange(hour1)
		assertFalse range5.containedInRange(hour1)
		assert range6.containedInRange(hour1)
		assert range7.containedInRange(hour1)
		assertFalse range8.containedInRange(hour1)
		assert range9.containedInRange(hour1)
		assert range10.containedInRange(hour1)
		assertFalse range11.containedInRange(hour1)
		assertFalse range12.containedInRange(hour1)
		assertFalse range13.containedInRange(hour1)
	}
	
	@Test
	void overlappingBeginningOfRange(){
		assertFalse range1.overlappingBeginningOfRange(hour1)
		assertFalse range2.overlappingBeginningOfRange(hour1)
		assertFalse range3.overlappingBeginningOfRange(hour1)
		assert range4.overlappingBeginningOfRange(hour1)
		assert range5.overlappingBeginningOfRange(hour1)
		assertFalse range6.overlappingBeginningOfRange(hour1)
		assertFalse range7.overlappingBeginningOfRange(hour1)
		assertFalse range8.overlappingBeginningOfRange(hour1)
		assertFalse range9.overlappingBeginningOfRange(hour1)
		assertFalse range10.overlappingBeginningOfRange(hour1)
		assertFalse range11.overlappingBeginningOfRange(hour1)
		assertFalse range12.overlappingBeginningOfRange(hour1)
		assertFalse range13.overlappingBeginningOfRange(hour1)
	}
	
	@Test
	void overlappingEndingOfRange(){
		assertFalse range1.overlappingEndingOfRange(hour1)
		assertFalse range2.overlappingEndingOfRange(hour1)
		assertFalse range3.overlappingEndingOfRange(hour1)
		assertFalse range4.overlappingEndingOfRange(hour1)
		assertFalse range5.overlappingEndingOfRange(hour1)
		assertFalse range6.overlappingEndingOfRange(hour1)
		assertFalse range7.overlappingEndingOfRange(hour1)
		assert range8.overlappingEndingOfRange(hour1)
		assertFalse range9.overlappingEndingOfRange(hour1)
		assertFalse range10.overlappingEndingOfRange(hour1)
		assert range11.overlappingEndingOfRange(hour1)
		assertFalse range12.overlappingEndingOfRange(hour1)
		assertFalse range13.overlappingEndingOfRange(hour1)
	}
	
	@Test
	void dropRangesIntoHours(){
		testRangeInDropRangesIntoHours(range1)
		testRangeInDropRangesIntoHours(range2)
		testRangeInDropRangesIntoHours(range3)
		testRangeInDropRangesIntoHours(range4)
		testRangeInDropRangesIntoHours(range5)
		testRangeInDropRangesIntoHours(range6)
		testRangeInDropRangesIntoHours(range7)
		testRangeInDropRangesIntoHours(range8)
		testRangeInDropRangesIntoHours(range9)
		testRangeInDropRangesIntoHours(range10)
		testRangeInDropRangesIntoHours(range11)
		testRangeInDropRangesIntoHours(range12)
		testRangeInDropRangesIntoHours(range13)
	}
	
	void testRangeInDropRangesIntoHours(range){
		def hours = DateRange.hoursBetween(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 00:00:00'), Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 12:00:00'))
		def ranges = [range]
		def bucketed = DateRange.dropRangesIntoHours(ranges, hours)
		assert bucketed
		
		def total = calcTotalTime(bucketed)
		
		assertEquals((range.size()), total)
	}
	
	def calcTotalTime(bucketed){
		def total = 0L
		bucketed.each{
			println it
			total +=it.value
		}
		return total
	}
	
	@Test
	void testRangesInDropRangesIntoHours(){
		def hours = DateRange.hoursBetween(Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 00:00:00'), Date.parse('yyyy-MM-dd HH:mm:ss', '2011-02-27 12:00:00'))
		def ranges = [range1, range2, range3, range4, range5, range6, range7, range8, range9, range10, range11, range12, range13]
		def bucketed = DateRange.dropRangesIntoHours(ranges, hours)
		assert bucketed
		
		def total = calcTotalTime(bucketed)
		
		def total2 = 0L
		ranges.each{
			total2 += it.size()
		}
		
		assertEquals(total2, total)
	}
	
}
