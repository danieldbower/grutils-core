package com.ecommerce.grutils.commandline

import static org.junit.Assert.*;
import org.junit.*;

import com.ecommerce.grutils.commandline.Menu;
import com.ecommerce.grutils.commandline.MenuItem;

class MenuTest {

	Menu menu
	int testCalled = 0
	int quitCalled = 0
	
	@Before
	void setup(){
		menu = new Menu()
		
		menu.menuItems << new MenuItem("t", "Test", 0, {testCalled++})
		menu.menuItems << new MenuItem("q", "quit", 100, {quitCalled++})
	}
	
	@Test
	void testToString(){
		assert "What would you like to do?\nt - Test\nq - quit\n"==menu.toString()
	}
	
	@Test
	void testSelectorsToString(){
		assert "[t, q]"==menu.selectorsToString()
	}
	
	@Test
	void testPerformMenuAction(){
		assert ! (menu.performMenuAction("x"))
		assert (0==testCalled && 0==quitCalled)
		
		assert (menu.performMenuAction("t"))
		assert (1==testCalled && 0==quitCalled)
	}
}
