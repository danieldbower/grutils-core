package com.ecommerce.grutils.dependencyinjection

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Class for simple di framework
 * @author daniel
 *
 */
abstract class AbstractApplication {

	private def dependencyBuilder
	private Config config
	
	private Logger logger
	private String logName
	private String appName
	private boolean appInitialized = false
	
	/**
	 * The application will initialize the dependencyBuilder (which should already be created).  
	 * Also, provide a name for the logger.
	 */
	void initialize(DependencyBuilder dependencyBuilder, String logName, String appName){
		//parameters must be present
		assert ( dependencyBuilder && logName && appName)
		this.dependencyBuilder = dependencyBuilder
		this.logName = logName
		this.appName = appName
		
		//initialize the dependency Builder
		dependencyBuilder.initialize()
		
		appInitialized = true
	}
	
	/**
	 * The application should ask the dependencyBuilder to shutdown before exiting.  
	 * This method can be overridden, but remember to call super.close()
	 */
	void close(){
		if(dependencyBuilder){
			try{
				dependencyBuilder.closeResources()
			}catch(Exception e){
				getLogger().error("$appName failed to close resources cleanly.", e)
			}
		}
		getLogger().info("$appName Exiting")
	}
	
	/**
	 * Read the Configuration file indicated by the environment variable
	 * @param environmentVariableName
	 * @return
	 */
	protected Config readConfiguration(String environmentVariableName){
		assert environmentVariableName
		config = new Config(environmentVariableName)
		return config
	}
	
	protected DependencyBuilder getDependencyBuilder(){
		assert dependencyBuilder
		return dependencyBuilder
	}
	
	protected Config getConfig(){
		assert config
		return config
	}
	
	protected Logger getLogger(){
		if(!logger){
			//Can only create the log if the app has been initialized
			assert logName
			logger = LoggerFactory.getLogger(logName)
		}
		return logger
	}
	
}
