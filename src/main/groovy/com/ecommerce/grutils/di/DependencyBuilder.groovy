package com.ecommerce.grutils.di

/**
 * By extending AbstractDependencyBuilder and AbstractApplication.  You can take
 * advantage of this ultra simple Dependency Injection Framework.
 * @author daniel
 *
 */
interface DependencyBuilder {

	/**
	 * Any Resources that need to be initialized when the app starts up here should have their init methods added here.
	 */
	void initialize()
	
	/**
	 * Any Resources that need to be closed before the app shuts down should have their close methods added here.
	 */
	void closeResources()
	
	/**
	 * Get the Configuration of this application
	 * @return
	 */
	Config getConfig()
}
