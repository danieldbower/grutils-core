package grutils.system

import org.apache.commons.lang.SystemUtils;

/**
 * Base Class for System Calls which allows for things like checking if the command is supported.
 */
abstract class SystemCall {
	boolean isCallAvailable(){
		supportsOs() && whereIsLocated()
	}
	
	/**
	 * Is this Operating System supported by this command?
	 */
	abstract boolean supportsOs()
	
	/**
	 * The command name - used in whereIs to determine if this command is available on the system
	 */
	abstract String commandName()
	
	/**
	 * Host is Linux/BSD/Unix Based
	 */
	boolean isNix(){
		SystemUtils.IS_OS_UNIX
	}
	
	/**
	 * Was the command found on the path by the "whereis" command
	 * <br />Requires whereis command to be available...
	 */
	private boolean whereIsLocated(){
		def command = "whereis " + commandName()
		def proc = command.execute()
		proc.waitFor()
		
		//def returnCode = proc.exitValue()
		//def stderr = proc.err.text.trim()
		def stdout = proc.in.text.trim()
		
		!stdout.equals(commandName()+':')
	}
}
