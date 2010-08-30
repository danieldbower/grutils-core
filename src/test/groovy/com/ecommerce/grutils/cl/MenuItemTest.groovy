package com.ecommerce.grutils.cl

import static org.junit.Assert.*;
import org.junit.*;

class MenuItemTest {

	MenuItem item
	//item = new MenuItem("", "", 0){}
	
	@Test
	void testToString(){
		item = new MenuItem("t", "Test", 0, {return "test"})
		
		assert "t - Test"==item.toString()
	}
	
	@Test
	void testCompareToUsingSelector(){
		item = new MenuItem("t1", "Test", 0, {return "test"})
		MenuItem item2 = new MenuItem("t2", "Test", 0, {return "test"})
		
		assert (item.compareTo(item2)<0)
	}
	
	@Test
	void testCompareToUsingSelector2(){
		item = new MenuItem("z", "Test", 0, {return "test"})
		MenuItem item2 = new MenuItem("t2", "Test", 0, {return "test"})
		
		assert (item.compareTo(item2)>0)
	}
	
	@Test
	void testCompareToUsingSortOrder(){
		item = new MenuItem("t1", "Test", 1, {return "test"})
		MenuItem item2 = new MenuItem("t2", "Test", 0, {return "test"})
		
		assert (item.compareTo(item2)>0)
	}
	
	@Test
	void testCompareToUsingSortOrder2(){
		item = new MenuItem("z", "Test", 1, {return "test"})
		MenuItem item2 = new MenuItem("t2", "Test", 5, {return "test"})
		
		assert (item.compareTo(item2)<0)
	}
	
	@Test
	void testCompareToEqual(){
		item = new MenuItem("t2", "Test", 0, {return "test"})
		MenuItem item2 = new MenuItem("t2", "Test", 0, {return "test"})
		
		assert (item.compareTo(item2)==0)
	}
	
	@Test
	void testCompareToThrowsException(){
		item = new MenuItem("t1", "Test", 1, {return "test"})
		MenuItem item2 = null
		
		boolean caught = false
		
		try{
			item.compareTo(item2)
		}catch(Exception e){
			caught = true
		}
		
		assert caught
	}
}
