package com.ecommerce.grutils.network

/**
 * Run ssh commands for a given server/user
 * 
 * <br /> requires at least: compile 'org.apache.ant:ant-jsch:1.8.1', 'com.jcraft:jsch:0.1.42'
 */
class SshCommand {
	String remoteHost
	String remoteUser
	String remoteKey
	
	SshCommand(final String remoteHost, final String remoteUser, final String remoteKey){
		this.remoteHost = remoteHost
		this.remoteUser = remoteUser
		this.remoteKey = remoteKey
	}
	
	String execute(String command){
		def ant = new AntBuilder()
		
		ant.sshexec(host:remoteHost,
				 keyfile:remoteKey,
				 username:remoteUser,
				 outputproperty:"result",
				 trust:true,
				 command:command)
		
		ant.getProject().getProperty("result")
	}
}
