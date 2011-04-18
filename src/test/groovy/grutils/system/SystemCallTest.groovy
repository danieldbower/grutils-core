package grutils.system

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

class SystemCallTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsCallAvailable() {
		assertTrue(new SystemCallImplForTest().isCallAvailable())
		assertFalse(new SystemCallImplInvalidForTest().isCallAvailable())
	}

	@Test
	public void testIsNix() {
		assertTrue (new SystemCallImplForTest().isNix())
	}

}

class SystemCallImplForTest extends SystemCall{
	 boolean supportsOs(){
		 isNix()
	 }
	 
	 String commandName(){
		 "ls"
	 }
}

class SystemCallImplInvalidForTest extends SystemCall{
	 boolean supportsOs(){
		 isNix()
	 }
	 
	 String commandName(){
		 "iwouldbecompletelysurprisedifthiscommandnameactuallyexisted"
	 }
}
