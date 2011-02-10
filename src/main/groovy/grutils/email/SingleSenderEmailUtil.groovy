package grutils.email

import groovy.util.AntBuilder;
import java.util.Properties;

/**
 * Simple class for sending emails from a configured sender to a configured set of receivers.  
 * Only the subj and message are modifiable.
 * @author daniel
 *
 */
class SingleSenderEmailUtil implements EmailUtil {
    
	SingleSenderEmailUtil(String mailhost, String port, String sender, String receivers){
		this.mailhost = mailhost
		this.port = port
		this.sender = sender
		this.receivers = receivers
	}
	
	private String mailhost
	private String port
	private String sender
	private String receivers
	
    void send(String subj, String mesg){
        //def config = 
        AntBuilder ant = new AntBuilder()
        ant.mail(mailhost:mailhost, mailport:port, subject:subj, tolist: receivers){
           from(address:sender)
           message(mesg)
       }
    }
}
