package com.ecommerce.grutils.cl

import static org.junit.Assert.*;
import org.junit.*;

class CommandLineUtilsTest {
    
    CommandLineUtils util
    
	@Before
	void setup(){
		util = new CommandLineUtils()
	}
	
    @Test
    void argsMinusFirstArgumentRemovedCorrectly(){
        String[] args = ["a", "b", "c", "d"]
        assertEquals(["b", "c", "d"].toArray(), util.argsMinusFirstArgument(args))
    }
    
    @Test
    void argsMinusFirstArgumentWithArrayOfOneLength(){
        String[] args = ["d"]
        assertEquals(0, util.argsMinusFirstArgument(args).size())
    }
    
    @Test
    void argsMinusFirstArgumentWithArrayOfZeroLength(){
        String[] args = new String[0]
        assertEquals(0, util.argsMinusFirstArgument(args).size())
    }
    
    @Test
    void getArgCalledCorrectly(){
        String[] args = ["a", "b", "c", "d"]
        assertEquals("c", util.getArg(args, 2))
    }
    
    @Test
    void getArgNullArgs(){
        assertEquals(null, util.getArg(null, 2))
    }
    
    @Test
    void getArgEmptyArgs(){
        String[] args = []
        assertEquals(null, util.getArg(args, 2))
    }
    
    @Test
    void getArgWithArgLessThanArgsLength(){
        String[] args = ["a", "b", "c", "d"]
        assertEquals(null, util.getArg(args, 5))
    }
    
    @Test
    void getArgTrimIt(){
        String[] args = [" a ", " b ", " c ", " d "]
        assertEquals("c", util.getArg(args, 2))
    }
    
    @Test
    void getArgs(){
        String[] args = [" a ", " b ", " c ", " d "]
        assertEquals(["b", "c"].toArray(), util.getArgs(args, 1, 2))
    }
    
    @Test
    void getArgsNull(){
        String[] args = null
        assertEquals([].toArray(), util.getArgs(args, 1, 2))
    }
    
    @Test
    void getArgsReturnLength1(){
        String[] args = [" a ", " b ", " c ", " d "]
        assertEquals(["c",].toArray(), util.getArgs(args, 2, 2))
    }
    
    @Test
    void getArgsReversedRange(){
        String[] args = [" a ", " b ", " c ", " d "]
        assertEquals([].toArray(), util.getArgs(args, 2, 0))
    }
	
	@Test
	void testGetDateFromUser(){
		util.readChoice = { return "02/09/11" }
		
		Date collected = util.getDateFromUser("Test Get Date From User Title")
		
		assert collected
	}
	
	@Test
	void testGetDateFromUserFailed(){
		int attempts = 0
		def choiceToReturn = [0:"b", 1:"02/09/11"]
		util.readChoice = {return choiceToReturn[attempts++]}
		
		Date collected = util.getDateFromUser("Test Get Date From User Title")
		
		assert collected
		assert attempts==2
	}
	
	@Test
	void testDisplayMenuAndReadChoice(){
		String[] args = new String[1]
		args[0] = "L"
		assert "l".equals(util.displayMenuAndReadChoice(args, "Menu"))
	}
	
	@Test
	void testDisplayMenuAndReadChoiceWithReadVal(){
		int attempts = 0
		
		util.readChoice = {attempts++; return "L" }
		
		assert "l".equals(util.displayMenuAndReadChoice(new String[0], "Menu"))
		assert attempts ==1
	}
}
