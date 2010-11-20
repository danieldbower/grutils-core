/**
 * 
 */
package com.ecommerce.grutils.filemanager

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecommerce.grutils.network.SshCommand;

/**
 * @author daniel
 *
 */
class RemoteViaSshFileManager implements FileManger {
	private String remoteHost
	private String remoteUser
	private String remoteKey
	
	SshCommand com
	
	RemoteViaSshFileManager(){}
	
	RemoteViaSshFileManager(String remoteHost, String remoteUser, String remoteKey){
		this.remoteHost = remoteHost
		this.remoteUser = remoteUser
		this.remoteKey = remoteKey
		
		com = new SshCommand(remoteHost, remoteUser, remoteKey)
	}

	public void copyFileToLocalPath(String remoteFileName, File newLocalFilePath){
		new AntBuilder().scp(
			remoteFile:"${remoteUser}@${remoteHost}:${remoteFileName}",
			localToDir:newLocalFilePath,
			keyfile:remoteKey)
	}
	
	/* (non-Javadoc)
	 * @see com.ecommerce.grutils.filemanager.FileManger#cp(java.lang.String, java.lang.String)
	 */
	@Override
	public void cp(String origName, String copyName, boolean overwriteExisting) {
		if(overwriteExisting){
			com.execute "rm -R $copyName; cp -R $origName $copyName"
		}else{
			com.execute "cp -R $origName $copyName"
		}
	}

	/* (non-Javadoc)
	 * @see com.ecommerce.grutils.filemanager.FileManger#ls(java.lang.String)
	 */
	@Override
	public List<String> ls(String directory) {
		String commandOutput = com.execute("ls $directory")
		
		if(null==commandOutput || commandOutput.trim().size()==0){
			throw new FileManagerException("Cannot read remote log Directory")
		}
		
		def fileList = []
		commandOutput.eachLine {fileList.add(it.trim()) }
		fileList
	}

	/* (non-Javadoc)
	 * @see com.ecommerce.grutils.filemanager.FileManger#mv(java.lang.String, java.lang.String)
	 */
	@Override
	public void mv(String origName, String newName, boolean overwriteExisting) {
		com.execute "mv $origName $newName"
	}

	/* (non-Javadoc)
	 * @see com.ecommerce.grutils.filemanager.FileManger#rm(java.lang.String)
	 */
	@Override
	public void rm(String location) {
		com.execute "rm -R $fileName"
	}

	/* (non-Javadoc)
	 * @see com.ecommerce.grutils.filemanager.FileManger#touch(java.lang.String)
	 */
	@Override
	public void touch(String fileName, boolean createIntermediateDirs) {
		com.execute "touch $fileName"
	}
	
	@Override
	void createDirectory(String dirName){
		throw new Exception("Not Implemented")
	}
	
	private static Logger logger
	
	protected static Logger getLogger(){
		if(!logger){
			logger = LoggerFactory.getLogger("com.ecommerce.grutils.filemanager.RemoteViaSshFileManager")
		}
		return logger
	}

}
