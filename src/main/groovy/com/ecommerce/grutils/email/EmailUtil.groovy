package com.ecommerce.grutils.email

/**
 * Simple Interface for Sending Emails with a preconfigured sender/receiver 
 * 
 */
public interface EmailUtil {
	public void send(String subj, String mesg);
}