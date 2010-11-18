package com.ecommerce.grutils.calendar

import static org.junit.Assert.*;
import org.junit.*;

class DurationTest {
	
	Duration duration
	
	@Test
	void construtor62seconds(){
		duration = new Duration(62)
		
		assertEquals 0, duration.years
		assertEquals 0, duration.weeks
		assertEquals 0, duration.days
		assertEquals 0, duration.hours
		assertEquals 1, duration.minutes.intValue()
		assertEquals 2, duration.seconds.intValue()
		
		assertEquals "1m 2s", duration.toString()
	}
	
	@Test
	void construtor3662seconds(){
		duration = new Duration(3662)
		
		assertEquals 0, duration.years
		assertEquals 0, duration.weeks
		assertEquals 0, duration.days
		assertEquals 1, duration.hours.intValue()
		assertEquals 1, duration.minutes.intValue()
		assertEquals 2, duration.seconds.intValue()
		
		assertEquals "1h 1m 2s", duration.toString()
	}
	
	@Test
	void construtor90062seconds(){
		duration = new Duration(90062)
		
		assertEquals 0, duration.years
		assertEquals 0, duration.weeks
		assertEquals 1, duration.days.intValue()
		assertEquals 1, duration.hours.intValue()
		assertEquals 1, duration.minutes.intValue()
		assertEquals 2, duration.seconds.intValue()
		
		assertEquals "1d 1h 1m 2s", duration.toString()
	}
	
	@Test
	void construtor694862seconds(){
		duration = new Duration(694862)
		
		assertEquals 0, duration.years
		assertEquals 1, duration.weeks.intValue()
		assertEquals 1, duration.days.intValue()
		assertEquals 1, duration.hours.intValue()
		assertEquals 1, duration.minutes.intValue()
		assertEquals 2, duration.seconds.intValue()
		
		assertEquals "1w 1d 1h 1m 2s", duration.toString()
	}
	
	@Test
	void construtor32230862seconds(){
		duration = new Duration(32230862)
		
		assertEquals 1, duration.years.intValue()
		assertEquals 1, duration.weeks.intValue()
		assertEquals 1, duration.days.intValue()
		assertEquals 1, duration.hours.intValue()
		assertEquals 1, duration.minutes.intValue()
		assertEquals 2, duration.seconds.intValue()
		
		assertEquals "1y 1w 1d 1h 1m 2s", duration.toString()
	}
	
	@Test
	void construtor31626062seconds(){
		duration = new Duration(31626062)
		
		assertEquals 1, duration.years.intValue()
		assertEquals 0, duration.weeks
		assertEquals 1, duration.days.intValue()
		assertEquals 1, duration.hours.intValue()
		assertEquals 1, duration.minutes.intValue()
		assertEquals 2, duration.seconds.intValue()
		
		assertEquals "1y 1d 1h 1m 2s", duration.toString()
	}
	
	
}
