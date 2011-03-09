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
}
