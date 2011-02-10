package grutils.commandline

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Simple Command Line Utilities to make reading user choices and getting parameters easier
 * @author Daniel
 *
 */
class CommandLineUtils {
	/**
	 * Read a users choice from the command line.
	 * Pass the given args as command line arguments.
	 * Display the given menu to the user.
	 * Will lowercase the users choice before returning
	 */
    String displayMenuAndReadChoice(String[] args, String menu){
 	    String choice = ""
 	    if(!args || args.size()==0){
		    println menu
		    choice = readChoice()
	    }else{
	 	   choice = args[0]
	    }
	   
	    return choice.toLowerCase()
    }
	
    /**
     * Read the user's choice from the command line
     * @return choice
     */
    Closure readChoice = {
        def reader = new BufferedReader(new InputStreamReader(System.in))
        return reader.readLine()
    }
    
    /**
     * Removes the first argument from a list of arguments
     * @param args
     * @return args
     */
    String[] argsMinusFirstArgument(String[] args){
        if(args && args.length>1){
            return args[1..(args.size()-1)]
        }else{
        	return []
        }
    }
    
    /**
     * Return the specified argument out of the list of arguments
     * <br /> Return null if there is no argument there
     * @param args
     * @param arg
     * @return arg
     */
    String getArg(String[] args, int arg){
        if(args
        	&& args.size()>0
            && args.size()>arg
        	&& args[arg].trim().size()>0){
            return args[arg].trim()
        }else{
        	return null
        }
    }
    
    /**
     * Get a new range of trimmed args
     * @param args
     * @param from
     * @param to
     * @return args
     */
    String[] getArgs(String[] args, int from, int to){
        List<String> newArgs = []
        
        if(null!=args){
            if(from==to){
                newArgs.add(args[from].trim())
            }else if(to<from){
                //do nothing
            }else{
                args[from..to].each{
                    newArgs.add(it.trim())
                }
            }
        }
        
        return newArgs.toArray()
    }
	
	/**
	 * Formatter for command line dates
	 */
    private DateFormat formatter 
   
    /**
	 * Repeatedly prompt the user at the command line for a date if they enter an improper format
	 * @param title
	 * @return
	 */
    Date getDateFromUser(String title){
	    if(!formatter){
		   formatter = DateFormat.getDateInstance(DateFormat.SHORT)
	    }
	    
	    Date choice
	    println title
	    try{
		   choice = formatter.parse(readChoice())
	    }catch(ParseException e){
		   println """Oops, that is an invalid format for a date.  In the U.S. try mm/dd/yy \n\n${title}"""
		   choice = formatter.parse (readChoice())
	    }
	    return choice
    }
	
	Date parseDate(String dateString){
		if(!formatter){
			formatter = DateFormat.getDateInstance(DateFormat.SHORT)
		 }
		
		try{
			formatter.parse(dateString)
		}catch(ParseException e){
			null
		}
	}
}
